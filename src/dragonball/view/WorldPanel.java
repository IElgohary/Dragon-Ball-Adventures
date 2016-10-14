package dragonball.view;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.javafx.collections.MappingChange.Map;

import dragonball.model.player.Player;

public class WorldPanel extends JPanel{
	private int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	private JPanel grid;
	private JPanel map;
	private JPanel info;
	private JPanel info2;
	private JPanelWithBackground cont;
	private JLabel playerName;
	private String icon;
	private JLabel health =new JLabel(new ImageIcon("Images/icons/heart.gif"));
	

	private Image movingSenzu = ImageIO.read(new File("Images/icons/senzuBean.gif"));
	private JLabel senzu = new JLabel(new ImageIcon(((new ImageIcon(
			"Images/icons/senzuBean.gif").getImage()
            .getScaledInstance(w/27, h/20,
                    java.awt.Image.SCALE_SMOOTH)))));
	private JLabel fighterName = new JLabel("Fighter Name");
	private JLabel level = new JLabel(new ImageIcon(((new ImageIcon(
			"Images/icons/scouter.png").getImage()
            .getScaledInstance(w/27, h/20,
                    java.awt.Image.SCALE_SMOOTH)))));
	private Image MovingBall = ImageIO.read(new File("Images/icons/zball.gif"));
	private JLabel balls = new JLabel(new ImageIcon(
			"Images/icons/zball.gif"));
	private JButton pause = new JButton("Pause");
	private JLabel boss = new JLabel(new ImageIcon(((new ImageIcon(
            "Images/battle/boss2.png").getImage()
            .getScaledInstance(w/25, h/12,
                    java.awt.Image.SCALE_SMOOTH)))));
	private JLabel character;
	private JLabel ballst = new JLabel();
	private JLabel healtht = new JLabel();
	private JLabel levelt = new JLabel();
	private JLabel senzut = new JLabel();
	private int height = 0, width = 0;
	private int x=0 , y=0;
	private int velx = 0 , vely = 0;
	private JButton[][] cells;
	private ImageIcon position = new ImageIcon("Images/map/black-circle.png");
	private int rows = 9, cols = 9;

	
	public WorldPanel(String iname, String iconPath, int x, int y,int width, int height) throws IOException
	{
		this.icon = iconPath;
		this.height = height;
		this.width = width;
		this.x = x - w/15;
		this.y = y;
		cont = new JPanelWithBackground("Images/map/arena3.jpg");
		grid = new JPanel(new GridLayout(10,10));
		grid.setOpaque(false);
		this.playerName = new JLabel(iname);
		cont.setLayout(null);
		character = new JLabel(new ImageIcon(((new ImageIcon(
	            iconPath).getImage()
	            .getScaledInstance(width, height,
	                    java.awt.Image.SCALE_SMOOTH)))));
	    info = new JPanel();
	    info.setLayout(new GridLayout(2,4));
	    info.setOpaque(false);
	    info2 = new JPanel();
	    info2.setLayout(new GridLayout(0,1));
	    info2.setOpaque(false);
	    pause = new JButton();
		pause.setIcon(new ImageIcon(((new ImageIcon(
				"Images/icons/pause.png").getImage()
	            .getScaledInstance(w/27, h/20,
	                    java.awt.Image.SCALE_SMOOTH)))));
		pause.setOpaque(false);
		pause.setBorderPainted(false);
		
		cells = new JButton[10][10];
		for(int i = 0 ; i < 10; i++)
		{
			for (int j = 0 ; j < 10 ; j++)
			{
				JButton cell = new JButton();
				cell.setOpaque(false);
				cell.setEnabled(false);
				cells[i][j] = cell;
				grid.add(cell);
			}
		}
		cells[9][9].setIcon(position);
		cells[0][0].setIcon(position);
		
		cont.add(grid);
	    cont.add(info);
	    cont.add(info2);
	    cont.add(character);
	    cont.add(boss);
		cont.add(pause);
		info2.add(playerName);
		info2.add(fighterName);
		info.add(health);
		info.add(healtht);
		info.add(level);
		info.add(levelt);
		info.add(senzu);
		info.add(senzut);
		info.add(balls);
		info.add(ballst);
		
		boss.setBounds(w/4 + w/30, h/2 + h/20, boss.getPreferredSize().width,boss.getPreferredSize().height);
		character.setBounds(this.x, y, width, height);
		pause.setBounds(0,0, pause.getPreferredSize().width, pause.getPreferredSize().height);
		info.setBounds(w - info.getPreferredSize().width * 2,0, info.getPreferredSize().width * 2, info.getPreferredSize().height);
		info2.setBounds(pause.getPreferredSize().width, pause.getPreferredSize().height/4, info2.getPreferredSize().width, info2.getPreferredSize().height);
		grid.setBounds(w/30,h * 5/7, w/8, h/5);
	}
	
	public void update(Player p)
	{
		this.getFighterName().setText(p.getActiveFighter().getName());
		this.getLevelt().setText(""+p.getActiveFighter().getLevel());
		this.getBallst().setText(""+p.getDragonBalls());
		this.getSenzut().setText("" + p.getSenzuBeans());
		this.getHealtht().setText(""+ p.getActiveFighter().getMaxHealthPoints());
	}

	public void moveUp()
	{
		vely -= h/50 ;
		cells[rows][cols].setIcon(null);
		cells[--rows][cols].setIcon(position);
		width -= width/20;
		height-= height/20;
		character.setIcon(new ImageIcon(((new ImageIcon(
	            icon).getImage()
	            .getScaledInstance(width, height,
	                    java.awt.Image.SCALE_SMOOTH)))));
		character.setBounds(x+velx, y+vely, width, height);
		// TODO
	}
	public void moveDown()
	{	
		
		vely += h/50;
		cells[rows][cols].setIcon(null);
		cells[++rows][cols].setIcon(position);
		width += width/20;
		height+= height/20;
		character.setIcon(new ImageIcon(((new ImageIcon(
	            icon).getImage()
	            .getScaledInstance(width, height,
	                    java.awt.Image.SCALE_SMOOTH)))));
		character.setBounds(x+velx, y+vely, width, height);
	}

	public void moveLeft()
	{
		velx -= w/23;
		cells[rows][cols].setIcon(null);
		cells[rows][--cols].setIcon(position);
		character.setBounds(x+velx, y+vely, width, height);
	}

	public void moveRight()
	{
		velx += w/23;
		cells[rows][cols].setIcon(null);
		cells[rows][++cols].setIcon(position);
		character.setBounds(x+velx, y+vely, width, height);
	}
	
	public void position(int r, int c) {
		for(int i = 9 ; i > c ; i--)
			moveLeft();
		
		for (int i = 9 ; i > r ; i-- )
			moveUp();
		
	}
	

	public JLabel getCharacter()
	{
		return character;
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
	public JLabel getPlayerName() {
		return playerName;
	}
	public JLabel getHealth() {
		return health;
	}
	
	public JLabel getSenzu() {
		return senzu;
	}
	public JLabel getFighterName() {
		return fighterName;
	}
	public JLabel getLevel() {
		return level;
	}
	public JLabel getBalls() {
		return balls;
	}
	public JPanel getCont() {
		return cont;
	}
	public void setCont(JPanelWithBackground cont) {
		this.cont = cont;
	}
	
	public JPanel getMap() {
		return map;
	}
	public JPanel getInfo() {
		return info;
	}
	public JButton getPause() {
		return pause;
	}
	public void setPause(JButton pause) {
		this.pause = pause;
	}
	public JLabel getBallst() {
		return ballst;
	}
	public JLabel getHealtht() {
		return healtht;
	}
	public JLabel getLevelt() {
		return levelt;
	}
	public JLabel getSenzut() {
		return senzut;
	}

	public int getVelx() {
		return velx;
	}

	public int getVely() {
		return vely;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}

	public void setVely(int vely) {
		this.vely = vely;
	}

	public JButton[][] getCells()
	{
		return cells;
	}
}
