package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.player.Player;

public class SwitchFighterPanel extends JPanel {
	
	private JLabel currentFighter = new JLabel("Current Fighter");
	private JLabel currentFighterName;
	private JButton choose = new JButton("Choose");
	private JButton back = new JButton("Back");
	
	private int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	
	private Image backgroundImage;
	private Image logo;
	private ImageIcon selected;
	
	private JComboBox fighters;
	private JPanel fighterInfo;
	private JPanel fighterInfo2;
	private JLabel selectedFighter;
	private JLabel HP;
	private JLabel BD;
	private JLabel PD;
	private JLabel KI;
	private JLabel ST;
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
	private JLabel desc;
	
	
	public SwitchFighterPanel(Player player) throws IOException{
		
		backgroundImage = ImageIO.read(new File("Images/menus/sky.jpg"));
	    logo = ImageIO.read(new File("Images/menus/logo.png"));
	    
		this.setLayout(null);
		
		currentFighterName = new JLabel(player.getActiveFighter().getName());
		currentFighterName.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 50));
		currentFighterName.setForeground(Color.WHITE);
		
		char currentFighterRace = player.getActiveFighter().getRace();
		switch(currentFighterRace){
		case 'E':
			selected = new ImageIcon(((new ImageIcon(
					"Images/fighters/earthling.png").getImage()
		            .getScaledInstance(440, 440,
		                    java.awt.Image.SCALE_SMOOTH))));
			desc = new JLabel("+1 stamina/turn and +1 ki/my turn");
			break;
		case 'S':
			selected = new ImageIcon(((new ImageIcon(
		            "Images/fighters/goku.png").getImage()
		            .getScaledInstance(440, 440,
		                    java.awt.Image.SCALE_SMOOTH))));
			desc = new JLabel("Can transform to Super Saiyan and gains 1 stamina/turn");
			break;
		case 'N':
			selected = new ImageIcon(((new ImageIcon(
		            "Images/fighters/namekianView.png").getImage()
		            .getScaledInstance(440, 440,
		                    java.awt.Image.SCALE_SMOOTH))));
			desc = new JLabel("+1 stamina/turn and +50 health/turn");
			break;
		case 'F':
			selected = new ImageIcon(((new ImageIcon(
		            "Images/fighters/frieza.png").getImage()
		            .getScaledInstance(440, 440,
		                    java.awt.Image.SCALE_SMOOTH))));
			desc = new JLabel("+1 stamina/turn");
			break;
		case 'M':
			selected = new ImageIcon(((new ImageIcon(
		            "Images/fighters/majin.png").getImage()
		            .getScaledInstance(440, 440,
		                    java.awt.Image.SCALE_SMOOTH))));
			desc = new JLabel("+1 stamina/foe turn");
			break;
		}
		
		ArrayList<String> fightersNames = new ArrayList<String>();
		for(int i = 0; i<player.getFighters().size(); i++)
			fightersNames.add(player.getFighters().get(i).getName());
		
		fighters = new JComboBox(fightersNames.toArray());
		fighters.setSelectedItem(currentFighterName.getText());
		fighters.setVisible(true);
		choose.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		back.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		currentFighter.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		
		selectedFighter = new JLabel(selected);
		HP = new JLabel("" + player.getActiveFighter().getMaxHealthPoints());
		BD = new JLabel("" + player.getActiveFighter().getBlastDamage());
		PD = new JLabel("" + player.getActiveFighter().getPhysicalDamage());
		KI = new JLabel("" + player.getActiveFighter().getMaxKi());
		ST = new JLabel("" + player.getActiveFighter().getMaxStamina());
		
		fighterInfo = new JPanel();
		fighterInfo2 = new JPanel();
		fighterInfo.setLayout(new GridLayout(0,2));
		fighterInfo2.setLayout(new GridLayout(0,1));
		
		
		add(currentFighter);
		add(currentFighterName);
		add(choose);
		add(back);
		add(selectedFighter);
		add(fighterInfo);
		add(fighterInfo2);
		add(fighters);
		
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
		
		currentFighter.setOpaque(false);
		currentFighterName.setOpaque(false);
		fighters.setOpaque(false);
		fighterInfo.setOpaque(false);
		fighterInfo2.setOpaque(false);
		
		Insets insets = this.getInsets();
	    Dimension size = currentFighterName.getPreferredSize();
	    currentFighterName.setBounds(180 ,250+ insets.top, size.width, size.height);
	    size = currentFighter.getPreferredSize();
	    currentFighter.setBounds(180 ,230+ insets.top, size.width, size.height);
		size = choose.getPreferredSize();
	    choose.setBounds(w - w/7 ,h- h/9, size.width, size.height);
	    size = choose.getPreferredSize();
	    back.setBounds(w/30,h- h/9, size.width, size.height);
	    size = fighters.getPreferredSize();
	    fighters.setBounds(150, h-470, 270, size.height);
	   	    
	    selectedFighter.setBounds(w/2, h/6, 500, 500);
	    fighterInfo.setBounds(w/10 , h/2 - h/25, fighterInfo.getPreferredSize().width, fighterInfo.getPreferredSize().height);
	    fighterInfo2.setBounds(w/4 , h/2 - h/30, w/3, fighterInfo2.getPreferredSize().height);
		repaint();
		validate();
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

	public static void main(String[] args) throws IOException {
		GameView g = new GameView();
		ArrayList<PlayableFighter> f = new ArrayList<PlayableFighter>();
		PlayableFighter af = new Saiyan("Goku");
		PlayableFighter af2 = new Frieza("Frieza guy");
		f.add(af);
		f.add(af2);
		SwitchFighterPanel a = new SwitchFighterPanel(new Player("Hamada", f, null, null, 0, 0, af2, 0));
		g.setContentPane(a);
		g.repaint();
		g.validate();
	}

	public JButton getChoose() {
		return choose;
	}

	public JButton getBack() {
		return back;
	}

	public JComboBox getFighters() {
		return fighters;
	}

	public void setSelected(ImageIcon selected) {
		this.selected = selected;
		selectedFighter.setIcon(selected);
		selectedFighter.repaint();
		selectedFighter.validate();
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


	
	
}
