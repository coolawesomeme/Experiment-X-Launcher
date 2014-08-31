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
	private JLabel loadingStatusLabel = new JLabel();
	protected static int menuID = 2;
	
	public GameLoader(Point point, boolean skippedLauncher) {
		super(point, menuID);
		
		if(skippedLauncher)
			createLauncherPathFile();
		
		loadingLabel = new JLabel("Loading... 0%", JLabel.CENTER);
		loadingLabel.setBounds(40, 180, 820, 25);
		loadingLabel.setFont(new Font(this.getFont().getName(), Font.BOLD, 20));
		loadingLabel.setForeground(new Color(38, 38, 38));
		mainContentLabel.add(loadingLabel);
		
		progress.setMaximum(100);
		progress.setMinimum(1);
		progress.setBounds(40, 220, 820, 30);
		mainContentLabel.add(progress);
		
		loadingStatusLabel = new JLabel("", JLabel.CENTER);
		loadingStatusLabel.setBounds(40, 260, 820, 25);
		loadingStatusLabel.setFont(new Font(this.getFont().getName(), Font.PLAIN, 16));
		mainContentLabel.add(loadingStatusLabel);
		
		JLabel rMessage = new JLabel(RandomMessage.getRandomMessage(), JLabel.CENTER);
		rMessage.setBounds(40, 180, 820, height - 10);
		rMessage.setFont(new Font(this.getFont().getName(), Font.BOLD, 15));
		rMessage.setForeground(new Color(38, 38, 38));
		mainContentLabel.add(rMessage);
		
		load();
	}

	private boolean launchGame() {
		File f;
		if(GameAutoUpdater.latestVersion != null){
			f = new File(Launcher.getGameDirectory() + "/versions/Experiment X " + GameAutoUpdater.latestVersion + "/Experiment X.jar");
		}else{
			f = new File("Experiment X.jar");}
		if(f.exists()){
			try {
				File q = new File(Launcher.getGameDirectory() + "/lastplayed.time");
				if(!q.exists()){
					Config.setValue("username", JOptionPane.showInputDialog("Please enter a username:"));
				}
				dispose();
				Runtime.getRuntime().exec("java -jar \"" + f.getAbsolutePath() + "\" -user:" + Config.getProperty("username"));
				createLastPlayedFile(q);
				System.exit(0);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				Logger.logError("Game failed to start.");
				JOptionPane.showMessageDialog(null, new JLabel("The game has failed to start.", JLabel.CENTER), "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}else{
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
	
	private void load() {
		Thread t = new Thread("GameLoadThread"){
	        public void run(){
	        	boolean updateFailed = false;
	        	final GameAutoUpdater updater = new GameAutoUpdater();
	            for(int i = 0 ; i < 100 ; i++){
	                final int percent = i;
	                SwingUtilities.invokeLater(new Runnable() {
	                    public void run() {
	                        progress.setValue(percent);
	                        if(percent % 5 == 0){
	                        	loadingLabel.setText("Loading... " + percent + "%");
	                        	Logger.logInfo("Loaded " + percent + "%");
	                        	if(percent == 0){
	        	                	loadingStatusLabel.setText("Checking for existing versions...");
	        	                }else if(percent == 50){
	        	                	if(updater.internetConnection && updater.updatesAvailable)
	        	                		loadingStatusLabel.setText("Downloading & launching v" + GameAutoUpdater.latestVersion + "...");
	        	                	else if(updater.internetConnection && !updater.updatesAvailable && updater.versionsOnDisk)
	        	                		loadingStatusLabel.setText("Launching game (v" + updater.highestVersion + ")...");
	        	                	else if(!updater.internetConnection && updater.versionsOnDisk)
	        	                		loadingStatusLabel.setText("No internet connection. Launching v" + updater.highestVersion + "...");
	        	                	else
	        	                		loadingStatusLabel.setText("");
	        	                }
	                        }
	                    }
	                  });
	                if(percent == 0){
	                	
	                }else if(percent == 50){
	                	if(updater.internetConnection && updater.updatesAvailable){
	        	        		if(updater.update()){}else{
	        	        			updateFailed = true;
	        	        		}
	                	}
	                	else if(updater.internetConnection && !updater.updatesAvailable && updater.versionsOnDisk)
	                	{}
	                	else if(!updater.internetConnection && !updater.versionsOnDisk)
	                	{JOptionPane.showMessageDialog(null, new JLabel("The game cannot be downloaded without an internet connection.", JLabel.CENTER), "Error", JOptionPane.ERROR_MESSAGE);}
	                }else{
	                	try {
	                   		Thread.sleep(100);
	                	} catch (InterruptedException e) {}
	                }	
	            }
	            if(updateFailed){
	            	JOptionPane.showMessageDialog(null, new JLabel("The game has failed to update.", JLabel.CENTER), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            if(updater.getHighestDownloadedVersion() != null){
	            	launchGame();
	            }
	            try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            System.exit(1);
	        }
	    };
	    t.start();
	}
}
