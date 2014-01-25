package com.rokru.experiment_x_launcher;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Options extends Launcher{
	private static final long serialVersionUID = 1L;

	private JButton OK;
	protected static int menuID = 1;
	
	public Options(Point point){
		super(point, menuID);
		setTitle("Options - " + launcherVersionFormatted);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		drawButtons();
		drawText();
		
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
		JLabel title = new JLabel("Options", JLabel.CENTER);
		title.setFont(Launcher.getDefaultFont(Font.BOLD, 20));
		title.setBounds(40, 10, 820, 45);
		mainContentLabel.add(title);
	}
	
	private void drawButtons(){
		OK = new JButton("OK");
		OK.setBounds(width - 60 - 20, (height - 40 - 40), 60, 40);
		mainContentLabel.add(OK);

		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.logButtonEvent("OK");
				dispose();
				new Launcher(getLocation(), Launcher.menuID);
			}
		});
	}
}
