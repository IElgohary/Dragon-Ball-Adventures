package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dragonball.controller.GameGUI;
import dragonball.model.battle.BattleEvent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;

public class BattlePanel extends JPanel{

	private int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	private JPanel cont;
	private JPanel menu;
	private JPanel foeInfo = new JPanel();
	private JPanel info = new JPanel();
	private JPanel attacks = new JPanel(new GridLayout(3,0));
	private JLabel myH;
	private JLabel myMH;
	private JLabel myS;
	private JLabel myMS;
	private JLabel myK;
	private JLabel myMK;
	private JLabel foeH;
	private JLabel foeMH;
	private JLabel foeS;
	private JLabel foeMS;
	private JLabel foeK;
	private JLabel foeMK;
	private JLabel foeIcon;
	private JLabel myIcon;
	private JLabel currentState = new JLabel("Foe Encountered");
	private JLabel myAction = new JLabel("Battle begins");
	private JPanel turn = new JPanel();
	private JLabel turnu = new JLabel("Your Turn");
	private JLabel HPI;
	private JLabel KII;
	private JLabel STI;
	private JLabel HPI1;
	private JLabel KII1;
	private JLabel STI1;
	private JButton PA = new JButton("Physical Attack");
	private JPanel superAttacks = new JPanel(new GridLayout(0,1));
	private JPanel ultimateAttacks = new JPanel(new GridLayout(0,1));
	private JPanel actions = new JPanel(new GridLayout(0,1));
	private JButton[] superAButtons= new JButton[4];
	private JButton[] ultimateAButtons= new JButton[2];
	private JButton use = new JButton("Use SenzuBeans"); 
	private JButton attack = new JButton("Attack");
	private JButton block = new JButton("Block");
	private JButton superAttack = new JButton("Super Attack");
	private JButton ultimateAttack = new JButton("Ultimate Attack");
	private JButton back = new JButton("Back");
	private JButton go = new JButton("Continue");
	private JLabel turnf = new JLabel("Oponent's turn");
	private JLabel level = new JLabel(new ImageIcon(((new ImageIcon(
			"Images/icons/scouter.png").getImage()
            .getScaledInstance(w/27, h/20,
                    java.awt.Image.SCALE_SMOOTH)))));
	private JLabel level2 = new JLabel(new ImageIcon(((new ImageIcon(
			"Images/icons/scouter.png").getImage()
            .getScaledInstance(w/27, h/20,
                    java.awt.Image.SCALE_SMOOTH)))));
	private JLabel lvl;
	private JLabel lvl2;
	private JLabel name1;
	private JLabel name2;
	private JLabel myHealth = new JLabel();
	private JLabel foeHealth = new JLabel();
	private JPanel myKiBars = new JPanel(new GridLayout(1,0,10,0));
	private JPanel foeKiBars = new JPanel(new GridLayout(1,0,10,0));
	private JPanel myStamina = new JPanel(new GridLayout(1,0,10,0));
	private JPanel foeStamina = new JPanel(new GridLayout(1,0,10,0));
	
	public BattlePanel(String path, String foePath) throws IOException {
		
		int width = 10 , height = 10;
		foeInfo.setLayout(new GridLayout(4,3));
		info.setLayout(new GridLayout(4,3));
		
		cont = new JPanelWithBackground("Images/map/battle.jpg");
		cont.setLayout(null);
		menu = new JPanel();
		myH = new JLabel();
		myMH = new JLabel();
		myS = new JLabel();
		myMS = new JLabel();
		myK = new JLabel();
		myMK = new JLabel();
		foeH = new JLabel();
		foeMH = new JLabel();
		foeS = new JLabel();
		foeMS = new JLabel();
		foeK = new JLabel();
		foeMK = new JLabel();
		lvl = new JLabel();
		lvl2 = new JLabel();
		name1 = new JLabel();
		name2 = new JLabel();
		
		myIcon = new JLabel(new ImageIcon(path));
		foeIcon = new JLabel(new ImageIcon(foePath));
		
		HPI= new JLabel(new ImageIcon("Images/icons/heart.gif"));
		KII= new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/ki.png").getImage()
	            .getScaledInstance(w/27, h/20,
	                    java.awt.Image.SCALE_SMOOTH)))));
		STI= new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/Stamina.png").getImage()
	            .getScaledInstance(w/27, h/20,
	                    java.awt.Image.SCALE_SMOOTH)))));
		HPI1= new JLabel(new ImageIcon("Images/icons/heart.gif"));
		KII1= new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/ki.png").getImage()
	            .getScaledInstance(w/27, h/20,
	                    java.awt.Image.SCALE_SMOOTH)))));
		STI1= new JLabel(new ImageIcon(((new ImageIcon(
				"Images/icons/Stamina.png").getImage()
	            .getScaledInstance(w/27, h/20,
	                    java.awt.Image.SCALE_SMOOTH)))));
		
		myStamina.setOpaque(false);
		foeStamina.setOpaque(false);
		foeKiBars.setOpaque(false);
		myKiBars.setOpaque(false);
		myHealth.setOpaque(true);
		myHealth.setForeground(Color.GREEN);
		myHealth.setBackground(Color.GREEN);
		foeHealth.setOpaque(true);
		foeHealth.setForeground(Color.GREEN);
		foeHealth.setBackground(Color.GREEN);
		attacks.setOpaque(false);
		superAttacks.setOpaque(false);
		ultimateAttacks.setOpaque(false);
		actions.setOpaque(false);
		info.setOpaque(false);
		foeInfo.setOpaque(false);
		menu.setOpaque(false);
		turn.setOpaque(false);
		attacks.setVisible(false);
		actions.setVisible(false);
		go.setVisible(false);
		
		name1.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 20));
		name2.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 20));
		name1.setForeground(Color.BLUE);
		name2.setForeground(Color.RED);
		turnu.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 20));
		turnf.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 20));
		attack.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		use.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		block.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		superAttack.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		ultimateAttack.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		back.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		PA.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		lvl.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		lvl2.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		myH.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		myH.setForeground(Color.BLACK);
		myMH.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		myMH.setForeground(Color.BLACK);
		myS.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		myS.setForeground(Color.BLACK);
		myMS.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		myMS.setForeground(Color.BLACK);
		myK.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		myK.setForeground(Color.BLACK);
		myMK.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		myMK.setForeground(Color.BLACK);
		foeH.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		foeH.setForeground(Color.BLACK);
		foeMH.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		foeMH.setForeground(Color.BLACK);
		foeK.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		foeK.setForeground(Color.BLACK);
		foeMK.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		foeMK.setForeground(Color.BLACK);
		foeS.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		foeS.setForeground(Color.BLACK);
		foeMS.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		foeMS.setForeground(Color.BLACK);
		currentState.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 20));
		currentState.setForeground(Color.RED);
		currentState.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		myAction.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 20));
		myAction.setForeground(Color.BLUE);
		myAction.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		turnu.setForeground(Color.BLUE);
		turnf.setForeground(Color.RED);
		go.setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
		turnu.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		turnf.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		
		turn.add(turnu);
		turn.add(turnf);
		turnf.setVisible(false);
		
		foeInfo.add(HPI);
		foeInfo.add(foeH);
		foeInfo.add(foeMH);
		foeInfo.add(STI);
		foeInfo.add(foeS);
		foeInfo.add(foeMS);
		foeInfo.add(KII);
		foeInfo.add(foeK);
		foeInfo.add(foeMK);
		foeInfo.add(level2);
		foeInfo.add(lvl2);
		
		info.add(HPI1);
		info.add(myH);
		info.add(myMH);
		info.add(STI1);
		info.add(myS);
		info.add(myMS);
		info.add(KII1);
		info.add(myK);
		info.add(myMK);
		info.add(level);
		info.add(lvl);
		
		cont.add(foeStamina);
		cont.add(myStamina);
		cont.add(foeKiBars);
		cont.add(myKiBars);
		cont.add(myHealth);
		cont.add(foeHealth);
		cont.add(name1);
		cont.add(name2);
		cont.add(menu);
		cont.add(foeInfo);
		cont.add(info);
		cont.add(myIcon);
		cont.add(foeIcon);
		cont.add(turn);
		cont.add(back);
		cont.add(currentState);
		cont.add(myAction);
		cont.add(go);
		
		menu.add(attacks);
		menu.add(actions);
		menu.add(superAttacks);
		menu.add(ultimateAttacks);
		
		actions.add(attack);
		actions.add(use);
		actions.add(block);
		
		name1.setBounds(w - w/6, h /8,w/4, h/10);
		name2.setBounds(w/15, h/8,w/4, h/10);
		menu.setBounds(w * 10/20, h*3/4, w/2, h/4);
		myIcon.setBounds(w* 8/20, h*4/7, w/4,h/4);
		foeIcon.setBounds(w*6/20, h*4/7, w/4,h/4);
		currentState.setBounds(w/4 + w/11, h*4/10 , w/3, currentState.getPreferredSize().height);
		myAction.setBounds(w/4 + w/11, h*4/10 , w/3, myAction.getPreferredSize().height);
		turn.setBounds(w/4 + w/11, h*3/10, w/3, turn.getPreferredSize().height);
		back.setBounds(w- w/10, h - back.getPreferredSize().height- h/30, back.getPreferredSize().width, back.getPreferredSize().height);
		go.setBounds(w- w/3 + w/60, h - go.getPreferredSize().height- h/10, go.getPreferredSize().width, go.getPreferredSize().height);
		myHealth.setBounds(w - w/5 - 20, h/4 + h/10, w/5, 10);
		foeHealth.setBounds(20, h/4 + h/10, w/5, 10);
		
	}
	
	public void updateInfo(Fighter me , Fighter foe,BattleEvent e)
	{
		myH.setText(""+me.getHealthPoints());
		myMH.setText("/"+me.getMaxHealthPoints());
		myS.setText(""+ me.getStamina());
		myMS.setText("/"+ me.getMaxStamina());
		myK.setText(""+ me .getKi());
		myMK.setText("/"+ me .getMaxKi());
		foeH.setText(""+foe.getHealthPoints());
		foeMH.setText("/"+foe.getMaxHealthPoints());
		foeS.setText(""+ foe.getStamina());
		foeMS.setText("/"+ foe.getMaxStamina());
		foeK.setText(""+ foe .getKi());
		foeMK.setText("/"+ foe .getMaxKi());
		lvl.setText(""+me.getLevel());
		lvl2.setText(""+foe.getLevel());
		name1.setText(me.getName());
		name2.setText(foe.getName());
		if(me instanceof Saiyan)
		{
			if(((Saiyan) me).isTransformed())
			{
				myIcon.setIcon(new ImageIcon("Images/map/gokuSS.gif"));
			}
			else
			{
				myIcon.setIcon(new ImageIcon("Images/map/gokuN.gif"));
			}
		}
		foeInfo.setBounds(0, h/4,w/4, foeInfo.getPreferredSize().height);
		info.setBounds(w - w/4, h /4,w/4, info.getPreferredSize().height);
		turn.setBounds(w/4 + w/11, h*3/10, w/3, turn.getPreferredSize().height);
		float factor = ((float)me.getHealthPoints()/(float)me.getMaxHealthPoints()) * w/5;
		if (((float)me.getHealthPoints()/(float)me.getMaxHealthPoints()) < 0.3)
			myHealth.setBackground(Color.RED);
		if (((float)me.getHealthPoints()/(float)me.getMaxHealthPoints()) > 0.3 && 
				((float)me.getHealthPoints()/(float)me.getMaxHealthPoints())<0.5)
			myHealth.setBackground(Color.YELLOW);
		if (((float)me.getHealthPoints()/(float)me.getMaxHealthPoints()) > 0.5)
			myHealth.setBackground(Color.GREEN);
		myHealth.setBounds(w - w/5 - 20, h/4- h / 35 , (int)factor , 10);
		
		factor = ((float)foe.getHealthPoints()/(float)foe.getMaxHealthPoints()) * w/5;
		if (((float)foe.getHealthPoints()/(float)foe.getMaxHealthPoints()) < 0.3)
			foeHealth.setBackground(Color.RED);
		if (((float)foe.getHealthPoints()/(float)foe.getMaxHealthPoints()) > 0.3 && 
				((float)foe.getHealthPoints()/(float)foe.getMaxHealthPoints())<0.5)
			foeHealth.setBackground(Color.YELLOW);
		if (((float)foe.getHealthPoints()/(float)foe.getMaxHealthPoints()) > 0.5)
			foeHealth.setBackground(Color.GREEN);
		foeHealth.setBounds(w/20, h/4- h / 35 , (int)factor , 10);
		
		myKiBars.removeAll();
		for(int i = 0 ; i < me.getKi() ;i++)
		{
			JLabel tmp = new JLabel();
			
			tmp.setOpaque(true);
			tmp.setForeground(Color.YELLOW);
			tmp.setBackground(Color.YELLOW);
			myKiBars.add(tmp);
		}
		factor = ((float)me.getKi()/(float) me.getMaxKi()) * w/6;
		myKiBars.setBounds(w-w/5 + 20, h/4 + h/10,(int)factor , 10);
		
		foeKiBars.removeAll();
		for(int i = 0 ; i < foe.getKi() ;i++)
		{
			JLabel tmp = new JLabel();
			
			tmp.setOpaque(true);
			tmp.setForeground(Color.YELLOW);
			tmp.setBackground(Color.YELLOW);
			foeKiBars.add(tmp);
		}
		factor = ((float)foe.getKi()/(float) foe.getMaxKi()) * w/6;
		foeKiBars.setBounds(w/15, h/4 + h/10,(int)factor , 10);
		
		myStamina.removeAll();
		for(int i = 0 ; i < me.getStamina() ;i++)
		{
			JLabel tmp = new JLabel();
			
			tmp.setOpaque(true);
			tmp.setForeground(Color.BLUE);
			tmp.setBackground(Color.BLUE);
			myStamina.add(tmp);
		}
		factor = ((float)me.getStamina()/(float) me.getMaxStamina()) * w/6;
		myStamina.setBounds(w-w/5 + 20, h/4+ h/18,(int)factor , 10);
		
		foeStamina.removeAll();
		for(int i = 0 ; i < foe.getStamina() ;i++)
		{
			JLabel tmp = new JLabel();
			
			tmp.setOpaque(true);
			tmp.setForeground(Color.BLUE);
			tmp.setBackground(Color.BLUE);
			foeStamina.add(tmp);
		}
		factor = ((float)foe.getStamina()/(float) foe.getMaxStamina()) * w/6;
		foeStamina.setBounds(w/15, h/4+ h/18 ,(int)factor , 10);
		
		repaint();
		validate();
	}

	public void icon(String i)
	{
		myIcon.setIcon(new ImageIcon (i));
	}
	
	public void foe(String i)
	{
		foeIcon.setIcon(new ImageIcon (i));
	}
	
	public void fillAttacks(Fighter f)
	{
		attacks.add(PA);
		attacks.add(superAttack);
		attacks.add(ultimateAttack);
		
		for(int i = 0 ; i < f.getSuperAttacks().size() ; i++)
		{
			superAButtons[i]= new JButton(f.getSuperAttacks().get(i).getName());
			superAButtons[i].setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
			superAttacks.add(superAButtons[i]);
		}
		for( int i = 0 ; i < f.getUltimateAttacks().size() ; i++)
		{
			ultimateAButtons[i] = new JButton(f.getUltimateAttacks().get(i).getName());
			ultimateAButtons[i].setFont(new Font(java.awt.Font.SANS_SERIF, Font.BOLD, 25));
			ultimateAttacks.add(ultimateAButtons[i]);
		}
	}
	
	
	public void setListeners(GameGUI g)
	{
		PA.addActionListener(g);
		for(int i = 0 ;  i < 4 &&superAButtons[i] != null ; i++)
			superAButtons[i].addActionListener(g);	
		for(int i = 0 ; i < 2 && ultimateAButtons[i]!= null  ; i++)
			ultimateAButtons[i].addActionListener(g);
		use.addActionListener(g);
		attack.addActionListener(g);
		block.addActionListener(g);
		superAttack.addActionListener(g);
		ultimateAttack.addActionListener(g);
		back.addActionListener(g);
		go.addActionListener(g);
		
	}
	
	public void back()
	{	
		if(attacks.isVisible())
			showActions();
		
		if(superAttacks.isVisible() || ultimateAttacks.isVisible())
			showAttacks();
		
		
	}
	
	public void switchTurn()
	{
		if(turnu.isVisible())
		{
			turnu.setVisible(false);
			turnf.setVisible(true);
		}
		else
		{
			turnu.setVisible(true);
			turnf.setVisible(false);
		}
	}
	
	
	
	public JLabel getMyAction() {
		return myAction;
	}

	public void showAttacks() {
		
		attacks.setVisible(true);
		actions.setVisible(false);
		superAttacks.setVisible(false);
		ultimateAttacks.setVisible(false);
		back.setVisible(true);
		go.setVisible(false);
		
	}
	public void showActions()
	{
		attacks.setVisible(false);
		actions.setVisible(true);
		superAttacks.setVisible(false);
		ultimateAttacks.setVisible(false);
		back.setVisible(false);
		go.setVisible(false);
		currentState.setVisible(false);
		myAction.setVisible(false);
	}
	
	public void showSuperAttacks()
	{
		attacks.setVisible(false);
		actions.setVisible(false);
		superAttacks.setVisible(true);
		ultimateAttacks.setVisible(false);
		back.setVisible(true);
		go.setVisible(false);
	}
	public void showGo()
	{
		attacks.setVisible(false);
		actions.setVisible(false);
		superAttacks.setVisible(false);
		ultimateAttacks.setVisible(false);
		back.setVisible(false);
		go.setVisible(true);
	
	}
	
	public void showUltimateAttacks()
	{
		attacks.setVisible(false);
		actions.setVisible(false);
		superAttacks.setVisible(false);
		ultimateAttacks.setVisible(true);
		back.setVisible(true);
		go.setVisible(false);
	}
	
	public void showmyAction()
	{
		attacks.setVisible(false);
		actions.setVisible(false);
		superAttacks.setVisible(false);
		ultimateAttacks.setVisible(false);
		back.setVisible(false);
		currentState.setVisible(false);
		go.setVisible(true);
		myAction.setVisible(true);
	}
	
	public void showState()
	{
		attacks.setVisible(false);
		actions.setVisible(false);
		superAttacks.setVisible(false);
		ultimateAttacks.setVisible(false);
		back.setVisible(false);
		currentState.setVisible(true);
		myAction.setVisible(false);
		go.setVisible(true);
	}
	
	public JButton getPA() {
		return PA;
	}

	public JPanel getSuperAttacks() {
		return superAttacks;
	}

	public JPanel getUltimateAttacks() {
		return ultimateAttacks;
	}

	public JButton[] getSuperAButtons() {
		return superAButtons;
	}

	public JButton[] getUltimateAButtons() {
		return ultimateAButtons;
	}

	public JButton getUse() {
		return use;
	}

	public JButton getAttack() {
		return attack;
	}

	public JButton getBlock() {
		return block;
	}
	public JPanel getInfo() {
		return info;
	}

	public JLabel getTurnu() {
		return turnu;
	}

	public JLabel getHPI() {
		return HPI;
	}

	public JLabel getKII() {
		return KII;
	}

	public JLabel getSTI() {
		return STI;
	}

	public JLabel getHPI1() {
		return HPI1;
	}

	public JLabel getKII1() {
		return KII1;
	}

	public JLabel getSTI1() {
		return STI1;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public JPanel getCont() {
		return cont;
	}

	public JPanel getMenu() {
		return menu;
	}

	
	public JPanel getFoeInfo() {
		return foeInfo;
	}

	public JPanel getAttacks() {
		return attacks;
	}

	public JLabel getMyH() {
		return myH;
	}

	public JLabel getMyMH() {
		return myMH;
	}

	public JLabel getMyS() {
		return myS;
	}

	public JLabel getMyMS() {
		return myMS;
	}

	public JLabel getMyK() {
		return myK;
	}

	public JLabel getMyMK() {
		return myMK;
	}

	public JLabel getFoeH() {
		return foeH;
	}

	public JLabel getFoeMH() {
		return foeMH;
	}

	public JLabel getFoeS() {
		return foeS;
	}

	public JLabel getFoeMS() {
		return foeMS;
	}

	public JLabel getFoeK() {
		return foeK;
	}

	public JLabel getFoeMK() {
		return foeMK;
	}

	public JLabel getFoeIcon() {
		return foeIcon;
	}

	public JLabel getMyIcon() {
		return myIcon;
	}

	public JLabel getCurrentState() {
		return currentState;
	}
	public JButton getGo() {
		return go;
	}

	public JLabel getTurnf() {
		return turnf;
	}


	public JPanel getTurn() {
		return turn;
	}

	public JPanel getActions() {
		return actions;
	}

	public JButton getSuperAttack() {
		return superAttack;
	}

	public JButton getUltimateAttack() {
		return ultimateAttack;
	}

	public JButton getBack() {
		return back;
	}

	
}
