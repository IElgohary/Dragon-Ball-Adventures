package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dragonball.model.character.fighter.PlayableFighter;

public class Upgrade extends JPanel{
	private JPanelWithBackground cont;
	private JPanel upgradable;
	private JLabel maxH;
	private JLabel maxKi;
	private JLabel maxS;
	private JLabel pD;
	private JLabel bD;
	private JLabel maxHIcon;
	private JLabel maxKiIcon;
	private JLabel maxSIcon;
	private JLabel pDIcon;
	private JLabel bDicon;
	private JLabel ability;
	private JLabel abilityIcon;
	private JButton H;
	private JButton Ki;
	private JButton S;
	private JButton P;
	private JButton B;
	private JButton back = new JButton("Back");
	

	private transient int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private transient int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	
	public Upgrade(PlayableFighter F) throws IOException
	{
		cont = new JPanelWithBackground("Images/menus/sky.jpg");
		cont.setLayout(null);
		upgradable = new JPanel(new GridLayout(5,3));
		upgradable.setOpaque(false);
		
		maxH = new JLabel(""+F.getMaxHealthPoints());
		maxKi = new JLabel(""+F.getMaxKi());
		maxS = new JLabel(""+F.getMaxStamina());
		pD = new JLabel(""+F.getPhysicalDamage());
		bD = new JLabel(""+F.getBlastDamage());
		ability = new JLabel(""+ F.getAbilityPoints());
		maxH.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		maxKi.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		maxS.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		pD.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		bD.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		ability.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 50));
		ability.setForeground(Color.ORANGE);
		ability.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		maxHIcon =new JLabel(new ImageIcon("Images/icons/heartlarge.gif"));
		maxKiIcon=new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/ki.png").getImage()
	            .getScaledInstance(w/20, h/13,
	                    java.awt.Image.SCALE_SMOOTH)))));
		maxSIcon = new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/Stamina.png").getImage()
	            .getScaledInstance(w/20, h/13,
	                    java.awt.Image.SCALE_SMOOTH)))));
		pDIcon = new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/punch.png").getImage()
	            .getScaledInstance(w/20, h/13,
	                    java.awt.Image.SCALE_SMOOTH)))));
		bDicon = new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/blast.png").getImage()
	            .getScaledInstance(w/18, h/11,
	                    java.awt.Image.SCALE_SMOOTH)))));
		abilityIcon = new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/Ability-point.png").getImage()
	            .getScaledInstance(w/4, h/2,
	                    java.awt.Image.SCALE_SMOOTH)))));
		H = new JButton("Upgrade Max Health");
		Ki = new JButton("Upgrade Max Ki");
		S = new JButton("Upgrade Max Stamina");
		P = new JButton("Upgrade Physical Damage");
		B = new JButton("Upgrade Blast Damage");
		back.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		
		cont.add(ability);
		cont.add(abilityIcon);
		cont.add(upgradable);
		cont.add(back);
		upgradable.add(maxHIcon);
		upgradable.add(maxH);
		upgradable.add(H);
		upgradable.add(maxKiIcon);
		upgradable.add(maxKi);
		upgradable.add(Ki);
		upgradable.add(maxSIcon);
		upgradable.add(maxS);
		upgradable.add(S);
		upgradable.add(pDIcon);
		upgradable.add(pD);
		upgradable.add(P);
		upgradable.add(bDicon);
		upgradable.add(bD);
		upgradable.add(B);
		
		abilityIcon.setBounds(w*2/3, h/6 -h /8, abilityIcon.getPreferredSize().width, abilityIcon.getPreferredSize().height);
		ability.setBounds(w*4/5 - w/35 , h/6 + abilityIcon.getPreferredSize().height - h/7, ability.getPreferredSize().width, ability.getPreferredSize().height);
		upgradable.setBounds(w/10, h / 9, upgradable.getPreferredSize().width, upgradable.getPreferredSize().height);
		Dimension size = back.getPreferredSize();
	    back.setBounds(w/30,h- h/9,
                size.width, size.height);
	}
	
	public JPanelWithBackground getCont() {
		return cont;
	}

	public JPanel getUpgradable() {
		return upgradable;
	}

	public JLabel getMaxH() {
		return maxH;
	}

	public JLabel getMaxKi() {
		return maxKi;
	}

	public JLabel getMaxS() {
		return maxS;
	}

	public JLabel getpD() {
		return pD;
	}

	public JLabel getbD() {
		return bD;
	}

	public JLabel getMaxHIcon() {
		return maxHIcon;
	}

	public JLabel getMaxKiIcon() {
		return maxKiIcon;
	}

	public JLabel getMaxSIcon() {
		return maxSIcon;
	}

	public JLabel getpDIcon() {
		return pDIcon;
	}

	public JLabel getbDicon() {
		return bDicon;
	}

	public JLabel getAbility() {
		return ability;
	}

	public JLabel getAbilityIcon() {
		return abilityIcon;
	}

	public JButton getH() {
		return H;
	}

	public JButton getKi() {
		return Ki;
	}

	public JButton getS() {
		return S;
	}

	public JButton getP() {
		return P;
	}

	public JButton getB() {
		return B;
	}

	public JButton getBack() {
		return back;
	}

	public int getW() {
		return w;
	}
}
