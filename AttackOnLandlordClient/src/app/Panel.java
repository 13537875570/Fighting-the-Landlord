//0change

package app;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel{
	
	public Panel() {
		this.setLayout(null);
	}
	
	
	protected void paintComponent(Graphics g) {
		
		Image image=new ImageIcon("images/back1.jpg").getImage();
		
		g.drawImage(image,0,0,this.getWidth(),this.getHeight(),null);
		
		
	}

}
