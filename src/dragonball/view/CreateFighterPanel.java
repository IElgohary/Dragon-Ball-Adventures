package dragonball.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateFighterPanel extends JPanel {
	private JTextField playerName;
	private JTextField fighterName;
	
	private JButton saiyan = new JButton("Saiyan");
	private JButton earthling= new JButton("Earthling");
	private JButton freiza = new JButton("Frieza");
	private JButton namekian = new JButton("Namekian");
	private JButton majin = new JButton("Majin");
	private JButton back = new JButton("Back");
	private JButton create;
	
	private Image backgroundImage;
	private Image logo;
	private ImageIcon selected = new ImageIcon(((new ImageIcon(
            "Images/fighters/goku.png").getImage()
            .getScaledInstance(440, 440,
                    java.awt.Image.SCALE_SMOOTH))));
	private int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	private JLabel fighter;
	private JPanel fighterInfo;
	private JLabel HP= new JLabel("1000");
	private JLabel BD= new JLabel("150");
	private JLabel PD= new JLabel("100");
	private JLabel KI= new JLabel("5");
	private JLabel ST= new JLabel("3");
	private JLabel HPI= new JLabel(new ImageIcon("Images/icons/heart.gif"));
	private JLabel BDI= new JLabel(new ImageIcon(((new ImageIcon(
			"Images/icons/blast.png").getImage()
            .getScaledInstance(w/27, h/20,
                    java.awt.Image.SCALE_SMOOTH)))));
	private JLabel PDI= new JLabel(new ImageIcon(((new ImageIcon(
			"Images/icons/punch.png").getImage()
            .getScaledInstance(w/27, h/20,
                    java.awt.Image.SCALE_SMOOTH)))));
	private JLabel KII= new JLabel(new ImageIcon(((new ImageIcon(
			"Images/icons/ki.png").getImage()
            .getScaledInstance(w/27, h/20,
                    java.awt.Image.SCALE_SMOOTH)))));
	private JLabel STI= new JLabel(new ImageIcon(((new ImageIcon(
			"Images/icons/Stamina.png").getImage()
            .getScaledInstance(w/27, h/20,
                    java.awt.Image.SCALE_SMOOTH)))));
	private JLabel desc = new JLabel("Can transform to Super Saiyan and gains 1 stamina/turn");
	private JPanel fighterInfo2;
	public CreateFighterPanel() throws IOException
	{
		backgroundImage = ImageIO.read(new File("Images/menus/sky.jpg"));
	    logo = ImageIO.read(new File("Images/menus/logo.png"));
	    	    
		this.setLayout(null);
		JPanel races = new JPanel(new FlowLayout());
		create = new JButton("Create");
		create.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		back.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		playerName = new JTextField("Player's Name");
		fighterName = new JTextField("Fighter's Name");
		fighter = new JLabel(selected);
		fighterInfo = new JPanel();
		fighterInfo2 = new JPanel();
		fighterInfo.setLayout(new GridLayout(0,2));
		fighterInfo2.setLayout(new GridLayout(0,1));
		
		
		add(playerName);
		add(create);
		add(back);
		add(fighterName);
		add(fighter);
		add(fighterInfo);
		add(fighterInfo2);
		races.add(saiyan);
		races.add(earthling);
		races.add(majin);
		races.add(freiza);
		races.add(namekian);
		add(races);

		fighterInfo.add(HPI);
		fighterInfo.add(HP);
		fighterInfo.add(BDI);
		fighterInfo.add(BD);
		fighterInfo.add(PDI);
		fighterInfo.add(PD);
		fighterInfo.add(KII);
		fighterInfo.add(KI);
		fighterInfo.add(STI);
		fighterInfo.add(ST);
		fighterInfo2.add(new JLabel("Other abilities: "));
		fighterInfo2.add(desc);
		
		create.setOpaque(false);
		saiyan.setOpaque(false);
		earthling.setOpaque(false);
		freiza.setOpaque(false);
		namekian.setOpaque(false);
		majin.setOpaque(false);
		races.setOpaque(false);
		fighterInfo.setOpaque(false);
		fighterInfo2.setOpaque(false);
		saiyan.setSelected(true);
		
		Insets insets = this.getInsets();
	    Dimension size = playerName.getPreferredSize();
	    playerName.setBounds(250 ,210+ insets.top,
                200, size.height);
	    
	    size = fighterName.getPreferredSize();
	    fighterName.setBounds(250,260+ insets.top,
                200, size.height);
	    
	    size = create.getPreferredSize();
	    create.setBounds(w - w/7 ,h- h/9,
                size.width, size.height);
	    size = create.getPreferredSize();
	    back.setBounds(w/30,h- h/9,
                size.width, size.height);
	    size = races.getPreferredSize();
	    races.setBounds(100, h-470,
	    		size.width , size.height);
	    
	    fighter.setBounds(w/2, h/6, 
	    		500, 500);
	    fighterInfo.setBounds(w/10 , h/2 - h/25, fighterInfo.getPreferredSize().width, fighterInfo.getPreferredSize().height);
	    fighterInfo2.setBounds(w/10 + fighterInfo.getPreferredSize().width, h/2 - h/30, fighterInfo2.getPreferredSize().width, fighterInfo2.getPreferredSize().height);
		
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
	 public JButton getCreate() {
		return create;
	}
	public JTextField getFighterName() {
		return fighterName;
	}
	public JTextField getPlayerName() {
		return playerName;
	}
	public JButton getSaiyan() {
		return saiyan;
	}
	public JButton getEarthling() {
		return earthling;
	}
	public JButton getFreiza() {
		return freiza;
	}
	public JButton getNamekian() {
		return namekian;
	}
	public JButton getMajin() {
		return majin;
	}
	public void setSelected(ImageIcon selected) {
		this.selected = selected;
		fighter.setIcon(selected);
		fighter.repaint();
		fighter.validate();
	}
	public JLabel getHP() {
		return HP;
	}
	public JLabel getBD() {
		return BD;
	}
	public JLabel getPD() {
		return PD;
	}
	public JLabel getKI() {
		return KI;
	}
	public JLabel getST() {
		return ST;
	}
	public JLabel getDesc() {
		return desc;
	}
	public JButton getBack() {
		return back;
	}
	
	
}
