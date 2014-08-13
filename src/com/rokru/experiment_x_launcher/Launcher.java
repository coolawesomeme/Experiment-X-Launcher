package com.rokru.experiment_x_launcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class Launcher extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//Will this ever change?
	public static String launcherVersion = "0.0.2";
	public static String launcherVersionFormatted = "Experiment X Launcher - v" + launcherVersion;

	protected JLabel mainContentLabel = new JLabel();
	private JButton play, options, about, quit;

	protected int width = 900;
	protected int height = 504;

	@SuppressWarnings("unused")
	private static boolean running = false;
	protected static int menuID = 0;
	
	public Launcher(int menuId) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		new Launcher(new Point(screenSize.width/2 - width/2, screenSize.height/2 - height/2), menuId);
	}

	public Launcher(Point point, int menuId) {
		if(menuId == menuID)
			setUndecorated(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		drawBackground(menuId);
		setTitle(launcherVersionFormatted);
		setIconImage(new ImageIcon(this.getClass().getResource("/images/app_icon.png")).getImage());
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(mainContentLabel);
		setLocation(point);
		setResizable(false);
		setVisible(true);
		mainContentLabel.setLayout(null);
		
		if(menuId == menuID)
			drawButtons();
		
		ComponentMover cm = new ComponentMover();
		cm.registerComponent(this);
		cm.setEdgeInsets(new Insets(-1000, -1000, -1000, -1000));
	}
	
	private void drawBackground(int menuId) {
		if(menuId == menuID){
			mainContentLabel = new JLabel(new ImageIcon(Launcher.class.getResource("/images/launcher_bg.png")));
			JLabel version = new JLabel("Launcher v" + launcherVersion, JLabel.RIGHT);
			version.setForeground(new Color(0f, 0f, 0f, 0.7f));
			version.setFont(getDefaultFont(Font.BOLD, 15, 1));
			version.setBounds(0, height - 15 - 7, width - 8, 15);
			mainContentLabel.add(version);
		}else if(menuId == Options.menuID || menuId == About.menuID){
			mainContentLabel = new JLabel(new ImageIcon(Launcher.class.getResource("/images/launcher_bg_2.png")));
		}
	}

	private void drawButtons() {
		play = new JButton("Play!");
		play.setBounds((width / 2 - 390 / 2), (height - 50 - 40), 390,
				50);
		mainContentLabel.add(play);

		int normalButtonHeight = 40;

		options = new JButton("Options");
		options.setBounds((width / 2 - 390 / 2) - 20 - 220, (height - normalButtonHeight - 45),
				220, normalButtonHeight);
		mainContentLabel.add(options);
		
		quit = new JButton("Quit");
		quit.setBounds((width / 2 + 390 / 2) + 140,
				(height - normalButtonHeight - 45), 100, normalButtonHeight);
		mainContentLabel.add(quit);

		about = new JButton("About");
		about.setBounds((width / 2 + 390 / 2) + 20, (height
				- normalButtonHeight - 45), 100, normalButtonHeight);
		mainContentLabel.add(about);
		
		validate();
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.logButtonEvent("Play");
				dispose();
				new GameLoader(getLocation());
			}
		});
		
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.logButtonEvent("Options");
				dispose();
				new Options(getLocation());
			}
		});
		
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.logButtonEvent("About");
				dispose();
				new About(getLocation());
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.logButtonEvent("Quit");
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		running = true;
		List<String> parameters = new ArrayList<String>();
		for(String s : args){
			parameters.add(s);
		}
		if(parameters.contains("-v") || parameters.contains("-version")){
			System.out.println(launcherVersion);
			System.exit(0);
		}
		makeDirectories();
		new Config();
		if(parameters.contains("-skiplauncher") || parameters.contains("-nogui")){
			new GameLoader();
		}else{
			Logger.logInfo("Launcher v" + launcherVersion + " initializing...");
			new Launcher(menuID);
		}
	}
	
	@Override
	public void run() {
		/*while(running){
			
		}*/
	}
	
	private static void makeDirectories() {
		File f = new File(getDirectory());
		if(f.mkdirs()){
			Logger.logInfo("Launcher File Folder created at:");
			Logger.logInfo(f.getAbsolutePath());
		}
	}

	public static String getDirectory(){
		if(System.getProperty("user.home") != null){
			return System.getProperty("user.home") + "/.experimentx/launcher";
		}else{
			return ".experimentx/launcher";
		}
	}
	
	public static String getGameDirectory(){
		if(System.getProperty("user.home") != null){
			return System.getProperty("user.home") + "/.experimentx/game";
		}else{
			return ".experimentx/game";
		}
	}
	
	public static Font getDefaultFont(int fontType, int fontSize){
		try{
			return new Font("Arial", fontType, fontSize);
		}catch(Exception e){
			return new Font(UIManager.getFont("Label.font").getFontName(), fontType, fontSize);
		}
	}
	
	public static Font getDefaultFont(int fontType, int fontSize, int reformatIfDefault){
		boolean flag = false;
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String []fonts=g.getAvailableFontFamilyNames();
            for (int i = 0; i < fonts.length; i++) {
            	if(fonts[i].equals("Arial")){
            		flag = true;
            		break;
            	}
            }
		if(flag){
			return new Font("Arial", fontType, fontSize);
		}else{
			if(reformatIfDefault <= 1)
				return new Font(UIManager.getFont("Label.font").getFontName(), Font.PLAIN, fontSize);
			else if(reformatIfDefault == 2)
				return new Font(UIManager.getFont("Label.font").getFontName(), Font.BOLD, fontSize);
			else if(reformatIfDefault == 3)
				return new Font(UIManager.getFont("Label.font").getFontName(), Font.ITALIC, fontSize);
			else
				return new Font(UIManager.getFont("Label.font").getFontName(), Font.PLAIN, fontSize);
		}
	}
}
