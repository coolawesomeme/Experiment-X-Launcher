package com.rokru.experiment_x_launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class GameLoader extends Launcher{

	private static final long serialVersionUID = 1L;

	private JProgressBar progress = new JProgressBar();
	private JLabel loadingLabel = new JLabel();
	protected static int menuID = 3;
	
	public GameLoader() {
		super(menuID);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		loadingLabel = new JLabel("Loading... 0%", JLabel.CENTER);
		loadingLabel.setBounds(40, 180, 820, 25);
		loadingLabel.setFont(new Font(this.getFont().getName(), Font.BOLD, 20));
		mainContentLabel.add(loadingLabel);
		
		progress.setMaximum(100);
		progress.setMinimum(0);
		progress.setBounds(40, 220, 820 , 25);
		mainContentLabel.add(progress);
		
		JLabel protip = new JLabel(RandomMessage.getRandomMessage(), JLabel.CENTER);
		protip.setBounds(40, 180, 820, height - 10);
		protip.setFont(new Font(this.getFont().getName(), Font.PLAIN, 15));
		mainContentLabel.add(protip);
		
		load();
	}
	
	public GameLoader(Point point) {
		super(point, menuID);
		loadingLabel = new JLabel("Loading... 0%", JLabel.CENTER);
		loadingLabel.setBounds(40, 180, 820, 25);
		loadingLabel.setFont(new Font(this.getFont().getName(), Font.BOLD, 20));
		loadingLabel.setForeground(new Color(38, 38, 38));
		mainContentLabel.add(loadingLabel);
		
		progress.setMaximum(100);
		progress.setMinimum(0);
		progress.setBounds(40, 220, 820 , 25);
		mainContentLabel.add(progress);
		
		JLabel rMessage = new JLabel(RandomMessage.getRandomMessage(), JLabel.CENTER);
		rMessage.setBounds(40, 180, 820, height - 10);
		rMessage.setFont(new Font(this.getFont().getName(), Font.BOLD, 15));
		rMessage.setForeground(new Color(38, 38, 38));
		mainContentLabel.add(rMessage);
		
		load();
	}

	private void load() {
		Thread t = new Thread(){
	        public void run(){
	            for(int i = 0 ; i < 100 ; i++){
	                final int percent = i;
	                SwingUtilities.invokeLater(new Runnable() {
	                    public void run() {
	                        progress.setValue(percent);
	                        if(percent % 5 == 0){
	                        	loadingLabel.setText("Loading... " + percent + "%");
	                        	Logger.logInfo("Loaded " + percent + "%");
	                        }
	                    }
	                  });
	                	try {
	                    	Thread.sleep(100);
	                	} catch (InterruptedException e) {}
	        	}
	            if(startGame()){
	            	;
	            }else{
	            	try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            	System.exit(1);
	            }
	        }
	    };
	    t.start();
	}

	private boolean startGame() {
		File f = new File("Experiment X.jar");
		if(f.exists()){
			try {
				File q = new File(Launcher.getGameDirectory() + "/lastplayed.time");
				if(!q.exists() && Config.username.startsWith("Player")){
					Config.setValue("username", JOptionPane.showInputDialog("Please enter a username:"));
				}
				dispose();
				Runtime.getRuntime().exec("java -jar \"" + f.getName() + "\" -user:" + Config.username);
				createLastPlayedFile(q);
				Runtime.getRuntime().exit(0);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				Logger.logError("Game failed to start.");
				JOptionPane.showMessageDialog(null, new JLabel("The game has failed to start.", JLabel.CENTER), "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}else{
			//Obviously doesn't work when you debug/ run in Eclipse because it can't find the jar
			Logger.logError("Game failed to start because jar was not found.");
			JOptionPane.showMessageDialog(null, new JLabel("<html>The game has failed to start.<br><center>Error code: 1 (No jar found)</center></html>", JLabel.CENTER), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	private void createLastPlayedFile(File lastPlayedFile){
		File q = new File(Launcher.getGameDirectory());
		if(!q.exists())
			q.mkdirs();
		try {
			lastPlayedFile.createNewFile();
			FileWriter fwrite = new FileWriter(lastPlayedFile);
			fwrite.write(System.currentTimeMillis() + "");
			fwrite.flush();
			fwrite.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
