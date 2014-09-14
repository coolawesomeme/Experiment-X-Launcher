package com.rokru.experiment_x_launcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalButtonUI;

public class XButtonUI extends MetalButtonUI{

	private String button_unpressed;
	private String button_rollover;
	private String button_pressed;
	
	public XButtonUI(String type){
		super();
		if(type.equalsIgnoreCase("launch")){
			button_unpressed = "/images/buttons/launch_unpressed.png";
			button_rollover = "/images/buttons/launch_rollover.png";
			button_pressed = "/images/buttons/launch_pressed.png";
		}else if(type.equalsIgnoreCase("options")){
			button_unpressed = "/images/buttons/options_unpressed.png";
			button_rollover = "/images/buttons/options_rollover.png";
			button_pressed = "/images/buttons/options_pressed.png";
		}else if(type.equalsIgnoreCase("about") || type.equalsIgnoreCase("quit")){
			button_unpressed = "/images/buttons/button_unpressed.png";
			button_rollover = "/images/buttons/button_rollover.png";
			button_pressed = "/images/buttons/button_pressed.png";
		}else if(type.equalsIgnoreCase("ok")){
			button_unpressed = "/images/buttons/ok_unpressed.png";
			button_rollover = "/images/buttons/ok_rollover.png";
			button_pressed = "/images/buttons/ok_pressed.png";
		}else{
			button_unpressed = "/images/buttons/button_unpressed.png";
			button_rollover = "/images/buttons/button_rollover.png";
			button_pressed = "/images/buttons/button_pressed.png";
		}
	}
	
	/*public void paint(Graphics g, JComponent c){
		((AbstractButton) c).setBorderPainted(false);
    	g.setColor(new Color(0xff003E85));
    	g.fillRect(0, 0, c.getWidth(), c.getHeight());
		Image img = new ImageIcon(this.getClass().getResource("/images/button_unpressed.png")).getImage();
        g.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), null);
        super.paint(g, c);
	}*/
	
    protected void paintButtonPressed(Graphics g, AbstractButton b)
    {
    	b.setBorderPainted(false);
    	g.setColor(new Color(0xff003E85));
    	g.fillRect(0, 0, b.getWidth(), b.getHeight());
    	Image img = new ImageIcon(this.getClass().getResource(button_pressed)).getImage();
        g.drawImage(img, 0, 0, b.getWidth(), b.getHeight(), null);
        b.setForeground(Color.WHITE);
    }

    public void update(Graphics g, JComponent c) {
    	AbstractButton button = (AbstractButton)c;
    	ButtonModel bm = button.getModel();
    	button.setBorderPainted(false);
    	g.setColor(new Color(0xff003E85));
    	g.fillRect(0, 0, c.getWidth(), c.getHeight());
    	if(bm.isRollover()){
    		Image img = new ImageIcon(this.getClass().getResource(button_rollover)).getImage();
            g.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), null);
            c.setForeground(Color.WHITE);
            super.paint(g, c);
    	}else{
    		Image img = new ImageIcon(this.getClass().getResource(button_unpressed)).getImage();
            g.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), null);
            c.setForeground(Color.BLACK);
            super.paint(g, c);
    	}
    }
}
