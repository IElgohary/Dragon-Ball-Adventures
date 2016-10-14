package dragonball.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	private JButton start;
	private JButton exit;
	private JButton load;
	private Image backgroundImage;
	private Image logo;
	private int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	
	  public MenuPanel() throws IOException {
	    backgroundImage = ImageIO.read(new File("Images/menus/menu2.jpg"));
	    logo = ImageIO.read(new File("Images/menus/logo.png"));
	    
	    this.setLayout(null);
	    start = new JButton("New Game");
	    load = new JButton("Load Game");
	    exit = new JButton("Exit");
	    
	    start.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		load.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		exit.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
 
	    add(start);
	    add(load);
	    add(exit);
	  
		start.setOpaque(false);		
		load.setOpaque(false);
		exit.setOpaque(false);
		
	    Insets insets = this.getInsets();
	    
	    Dimension size = load.getPreferredSize();
	    start.setBounds(w/5 ,250 + insets.top,
	                 size.width, size.height);
	    
	    load.setBounds(w/5,300 + insets.top,
                size.width, size.height);
	    
	    exit.setBounds(w/5 ,350 + insets.top,
                size.width, size.height);
		
		
		

	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage,0,0,
	    		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
	    		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight(), null);
	    g.drawImage(logo,50,20,
	    		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth()/2,
	    		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()/4, null);
	  }
	

	public JButton getStart() {
		return start;
	}
	public JButton getExit() {
		return exit;
	}


	public void setStart(JButton start) {
		this.start = start;
	}

	public JButton getLoad() {
		return load;
	}

	public void setLoad(JButton load) {
		this.load = load;
	}
	
}
