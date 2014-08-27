package com.rokru.experiment_x_launcher;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class About extends Launcher{
	private static final long serialVersionUID = 1L;

	private JButton OK;
	protected static int menuID = 2;
	
	public About(Point point){
		super(point, menuID);
		setTitle("About - " + launcherVersionFormatted);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		drawText();
		drawButtons();
		
		addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                dispose();
                new Launcher(getLocation(), Launcher.menuID);
            }
        });
	}
	
	private void drawText() {
		mainContentLabel.setLayout(null);
		JLabel title = new JLabel("About", JLabel.CENTER);
		title.setFont(Launcher.getDefaultFont(Font.BOLD, 20));
		title.setBounds(40, 10, 820, 45);
		mainContentLabel.add(title);
		
		JLabel version = new JLabel(launcherVersionFormatted, JLabel.CENTER);
		version.setFont(Launcher.getDefaultFont(Font.PLAIN, 14));
		version.setBounds(40, 50, 820, 20);
		mainContentLabel.add(version);
		
		JLabel textBody = new JLabel("<html><center>Experiment X is a 2D game where you choose your own path. "
				+ "Will you excel as a secret agent? A pirate? An astronaut? A sailor? A robot? An explorer? Or just an ordinary (wo)man?"
				+ "<br>It's all up to you.<br><br>"
				+ "(once all that is added in lmfaooo)</center></html>", JLabel.CENTER);
		textBody.setBounds(40, 30, 820, height - 60);
		textBody.setFont(new Font(this.getFont().getFontName(), Font.PLAIN, 15));
		mainContentLabel.add(textBody);
	}

	private void drawButtons(){
		OK = new JButton("OK");
		OK.setBounds(width - 60 - 20, (height - 40 - 40), 60, 40);
		mainContentLabel.add(OK);
		OK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.logButtonEvent("OK");
				dispose();
				new Launcher(getLocation(), Launcher.menuID);
			}
		});
	}
}
