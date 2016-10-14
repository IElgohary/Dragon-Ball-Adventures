package dragonball.view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenu extends JPanel{
	private JPanelWithBackground cont;
	private JButton create = new JButton("Create New Fighter");
	private JButton chooseFighter = new JButton("Switch Fighters");
	private JButton assign = new JButton("Assign Attacks");
	private JButton upgrade = new JButton("Upgrade Active Fighter");
	private JButton save = new JButton ("Save");
	private JButton back = new JButton("Back");
	private JButton exit = new JButton ("Exit");
	private transient int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private transient int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	public PauseMenu() throws IOException
	{
		cont = new JPanelWithBackground("Images/menus/sky.jpg");
		cont.setLayout(null);
		create.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		save.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		back.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		exit.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		chooseFighter.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		upgrade.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		assign.setFont(new java.awt.Font(Font.SANS_SERIF, Font.BOLD, 25));
		
		cont.add(save);
		cont.add(create);
		cont.add(back);
		cont.add(exit);
		cont.add(chooseFighter);
		cont.add(upgrade);
		cont.add(assign);
		
		save.setBounds(w/4, h/3 , w/2, create.getPreferredSize().height);
		create.setBounds(w/4, h/3+create.getPreferredSize().height , w/2, create.getPreferredSize().height);
		chooseFighter.setBounds(w/4, h/3+create.getPreferredSize().height*2 , w/2, create.getPreferredSize().height);
		upgrade.setBounds(w/4, h/3 +create.getPreferredSize().height*3, w/2, create.getPreferredSize().height);
		assign.setBounds(w/4, h/3 + create.getPreferredSize().height*4 , w/2, create.getPreferredSize().height);
		back.setBounds(w/4, h/3 +create.getPreferredSize().height*5, w/2, create.getPreferredSize().height);
		exit.setBounds(w/4, h/3 + create.getPreferredSize().height*6 , w/2, create.getPreferredSize().height);
		
		 
	}
	public JButton getChooseFighter()
	{
		return chooseFighter;
	}
	public JButton getUpgrade()
	{
		return upgrade;
	}
	
	public JButton getCreate() {
		return create;
	}

	public JButton getSave() {
		return save;
	}
	public JButton getExit() {
		return exit;
	}
	public JButton getAssign()
	{
		return assign;
	}

	public JPanelWithBackground getCont()
	{
		return cont;
	}

	public JButton getBack() {
		return back;
	}

}
