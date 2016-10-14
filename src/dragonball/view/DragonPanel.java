package dragonball.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DragonPanel extends JPanel {
	private transient int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private transient int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	private JPanelWithBackground cont;
	private JLabel desc = new JLabel("What Do You Wish For?!!");
	private JButton senzuBeans;
	private JButton abilityPoints;
	private JButton superAttack;
	private JButton UltimateAttack;
	private JButton confirm = new JButton ("Choose"); 
	
	public DragonPanel() throws IOException
	{
		cont = new JPanelWithBackground("Images/menus/shenron.jpg");
		cont.setLayout(null);
		
		senzuBeans = new JButton("Senzu Beans");
		abilityPoints = new JButton("Ability Points");
		superAttack = new JButton("Super Attack");
		UltimateAttack = new JButton("Ultimate Attack");

		confirm.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		desc.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		desc.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		desc.setForeground(Color.WHITE);
		cont.add(senzuBeans);
		cont.add(abilityPoints);
		cont.add(superAttack);
		cont.add(UltimateAttack);
		cont.add(desc);
		cont.add(confirm);
		
		senzuBeans.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		abilityPoints.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		superAttack.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		UltimateAttack.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		confirm.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		
		senzuBeans.setBounds(w/10,h*7/10 + h/50,w/5, senzuBeans.getPreferredSize().height);
		abilityPoints.setBounds(w/10 + w/5,h*7/10+ h/50,w/5, senzuBeans.getPreferredSize().height);
		superAttack.setBounds(w/10 + w* 2/5,h*7/10+ h/50,w/5, senzuBeans.getPreferredSize().height);
		UltimateAttack.setBounds(w/10+ w*3/5,h*7/10+ h/50,w/5, senzuBeans.getPreferredSize().height);
		desc.setBounds(w/4 ,h*8/10, w/2, h/30);
		confirm.setBounds(w/2 -w/18 ,h*7/8, confirm.getPreferredSize().width, confirm.getPreferredSize().height);
	}
	
	public JPanelWithBackground getCont() {
		return cont;
	}

	public JButton getSenzuBeans() {
		return senzuBeans;
	}

	public JButton getAbilityPoints() {
		return abilityPoints;
	}

	public JButton getSuperAttack() {
		return superAttack;
	}

	public JButton getUltimateAttack() {
		return UltimateAttack;
	}

	public JButton getConfirm() {
		return confirm;
	}

	public JLabel getDesc() {
		return desc;
	}

	public void setDesc(JLabel desc) {
		this.desc = desc;
	}

}
