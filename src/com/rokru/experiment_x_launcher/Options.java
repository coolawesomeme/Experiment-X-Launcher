package com.rokru.experiment_x_launcher;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Options extends Launcher{
	private static final long serialVersionUID = 1L;

	private JButton OK;
	private JTextField username;
	protected static int menuID = 1;
	
	public Options(Point point){
		super(point, menuID);
		setTitle("Options - " + launcherVersionFormatted);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		drawComponents();
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
		
		JLabel userLabel = new JLabel("Username:");
		userLabel.setFont(Launcher.getDefaultFont(Font.PLAIN, 15));
		userLabel.setBounds(40, 65, 80, 45);
		mainContentLabel.add(userLabel);
	}
	
	private void drawComponents(){
		OK = new JButton("OK");
		OK.setBounds(width - 60 - 20, (height - 40 - 40), 60, 40);
		mainContentLabel.add(OK);
		OK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		username = new TransparentTextField("Player");
		username.setBounds(120, 72, 165, 30);
		username.setDocument(new JTextFieldLimit(18));
		Config.reload(true);
		username.setText(Config.getProperty("username"));
		username.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.7f));
		mainContentLabel.add(username);
		
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Config.setValue("username", username.getText());
				Logger.logPropertyChange("username", username.getText());
				Logger.logButtonEvent("OK");
				dispose();
				new Launcher(getLocation(), Launcher.menuID);
			}
		});
	}
}

class JTextFieldLimit extends PlainDocument {
	private static final long serialVersionUID = 1L;
	private int limit;

	  JTextFieldLimit(int limit) {
	   super();
	   this.limit = limit;
	   }

	  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
	    if (str == null) return;

	    if(str.matches("[^a-zA-Z0-9]")){return;}
	    
	    if ((getLength() + str.length()) <= limit) {
	      super.insertString(offset, str, attr);
	    }
	  }
}

class TransparentTextField extends JTextField {

	private static final long serialVersionUID = 1L;

	public TransparentTextField(String text) {
        super(text);
        init();
    }

    public TransparentTextField(int columns) {
        super(columns);
        init();
    }

    public TransparentTextField(String text, int columns) {
        super(text, columns);
        init();
    }

    protected void init() {
        setOpaque(false);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.7f));
        super.paint(g2d);
        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g2d);
        g2d.dispose();
    }

}
