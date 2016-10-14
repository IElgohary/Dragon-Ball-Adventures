package dragonball.controller;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import dragonball.model.attack.MaximumCharge;
import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.dragon.DragonWishType;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.MapIndexOutOfBoundsException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.model.game.GameState;
import dragonball.model.player.Player;
import dragonball.view.AssignAttack;
import dragonball.view.BattlePanel;
import dragonball.view.CreateFighterPanel;
import dragonball.view.CreateNewFighterPanel;
import dragonball.view.DragonPanel;
import dragonball.view.GameView;
import dragonball.view.MenuPanel;
import dragonball.view.PauseMenu;
import dragonball.view.SwitchFighterPanel;
import dragonball.view.Upgrade;
import dragonball.view.WorldPanel;


public class GameGUI implements ActionListener,GameListener,KeyListener{
	
	private Game game;
	private  GameView view;
	private  DragonPanel dragonPanel;
	private  BattlePanel battlePanel;
	private  Upgrade upgrade;
	private  WorldPanel worldPanel;
	private  MenuPanel menuPanel;
	private  PauseMenu pauseMenu;
	private  CreateFighterPanel createFighter;
	private  CreateNewFighterPanel createNewFighter;
	private SwitchFighterPanel switchFighterPanel;
	private  String race = "S";
	private  String iconPath = "Images/fighters/";
	private int we = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private int he = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	private int h= he/10, w = we/20, x=we *8/11 , y= he * 8/11 ;
	private DragonWish wish;
	private Dragon dragon;
	private DragonWish[] wishes;
	private Timer t = new Timer(5,this);
	private Sound theme = new Sound("sounds/Mystic 2.wav");
	private Sound battle = new Sound("sounds/Mystic 1.wav");
	private Sound world = new Sound("sounds/Cell.wav");
	private Sound heal = new Sound("sounds/heal.wav");
	private Sound physical = new Sound("sounds/punch.wav");
	private int level =0;
	public GameGUI() throws IOException
	{
		view = new GameView();

		game = new Game();
		theme.play();
		theme.loop();
		game.setListener(this);
		view.addKeyListener(this);
		view.setFocusable(true);
		menuPanel = new MenuPanel();	
		view.setContentPane(menuPanel);
		t.start();
		menuPanel.getStart().addActionListener(this);
		menuPanel.getLoad().addActionListener(this);
		menuPanel.getExit().addActionListener(this);
		
		view.repaint();
		view.validate();
		
	}


	@Override
	public void onDragonCalled(Dragon dragon) {
		
		this.dragon = dragon;
		ImageIcon item = new ImageIcon("Images/map/balls.gif");
		try {
			String soundName = "sounds/dragon.wav";    
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			javax.sound.sampled.Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
	        } catch (Exception e1) {
	          System.err.println(e1.getMessage());
	        }
		JOptionPane.showMessageDialog(null, "", "You Have collected 7 Dragon Balls.", JOptionPane.INFORMATION_MESSAGE, item);
		wishes = dragon.getWishes();
		try {
			dragonPanel = new DragonPanel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dragonPanel.getConfirm().addActionListener(this);
		dragonPanel.getSenzuBeans().addActionListener(this);
		dragonPanel.getAbilityPoints().addActionListener(this);
		dragonPanel.getSuperAttack().addActionListener(this);
		dragonPanel.getUltimateAttack().addActionListener(this);
		view.setContentPane(dragonPanel.getCont());
		view.repaint();
		view.validate();
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		ImageIcon item = new ImageIcon("Images/icons/senzuBean.gif");
		if(collectible == Collectible.SENZU_BEAN )
		{
			try {
			String soundName = "sounds/1up.wav";    
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			javax.sound.sampled.Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
	        } catch (Exception e1) {
	          System.err.println(e1.getMessage());
	        }
		}
		else
		{
			item = new ImageIcon("Images/icons/zball.gif");
			try {
				String soundName = "sounds/db.wav";    
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
				javax.sound.sampled.Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
		        } catch (Exception e1) {
		          System.err.println(e1.getMessage());
		        }
		}
		JOptionPane.showMessageDialog(null, "Found: "+ collectible, "Item Found", JOptionPane.INFORMATION_MESSAGE, item);
		worldPanel.update(game.getPlayer());
		
	}

	@Override
	public void onBattleEvent(BattleEvent e) {
		NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource())
				.getFoe();
		if(e.getType() == BattleEventType.STARTED){
			level = game.getPlayer().getActiveFighter().getLevel();
			try {
				String soundName = "sounds/battle.wav";    
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
				javax.sound.sampled.Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
		        } catch (Exception e1) {
		          System.err.println(e1.getMessage());
		        }
			ImageIcon item = new ImageIcon("Images/icons/radar.png");
			JOptionPane.showMessageDialog(null, "Foe Encountered", "Foe Encountered", JOptionPane.INFORMATION_MESSAGE, item);
			world.stop();
			battle.play();
			battle.loop();
			String me = "" , enemy = "";
			Player p = game.getPlayer();
			if(p.getActiveFighter() instanceof Saiyan) 
			{me = "Images/map/gokuN.gif";}
			if(p.getActiveFighter() instanceof Earthling) 
				{me = "Images/map/krillin.gif"; }
			if(p.getActiveFighter() instanceof Namekian) 
				{me = "Images/map/Piccolo.gif";}
			if(p.getActiveFighter() instanceof Frieza)
				{ me =  "Images/map/frieza.gif";}
			if(p.getActiveFighter() instanceof Majin) 
				{ me = "Images/map/buu.gif";}
			
			if(foe.isStrong())
			{
				enemy = "Images/map/cell.gif";
			}
			else
			{
				
				enemy = "Images/map/android.gif";
				
			}
			
			try {
				battlePanel = new BattlePanel(me, enemy);
				battlePanel.updateInfo(game.getPlayer().getActiveFighter(), foe,e);
				view.setContentPane(battlePanel.getCont());
				battlePanel.fillAttacks(game.getPlayer().getActiveFighter());
				battlePanel.setListeners(this);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getType() == BattleEventType.ATTACK)
		{
			if(e.getAttack() instanceof PhysicalAttack)
				physical.play();
			if(e.getCurrentOpponent() == foe)
			{
				battlePanel.getCurrentState().setText(((Fighter) e.getCurrentOpponent()).getName()+ " Used "+ e.getAttack().getName());
					if(e.getAttack() instanceof SuperAttack)
					{
						if(foe.isStrong())
							battlePanel.foe("Images/map/cellSa.gif");
						else
							battlePanel.foe("Images/map/androidSa.gif");
					}
					else if(e.getAttack() instanceof PhysicalAttack)
					{
						if(foe.isStrong())
							battlePanel.foe("Images/map/cellPa.gif");
						else
							battlePanel.foe("Images/map/androidPa.gif");
					}
					else if(e.getAttack() instanceof UltimateAttack)
					{
						if(foe.isStrong())
							battlePanel.foe("Images/map/cellua.gif");
						else
							battlePanel.foe("Images/map/androidUa.gif");
					}
			}
			else
				battlePanel.getMyAction().setText(((Fighter) e.getCurrentOpponent()).getName()+ " Used "+ e.getAttack().getName());
			battlePanel.updateInfo(game.getPlayer().getActiveFighter(), foe,e);
		}
		if(e.getType() == BattleEventType.BLOCK)
		{
			if(e.getCurrentOpponent() == foe)
				battlePanel.getCurrentState().setText(((Fighter) e.getCurrentOpponent()).getName()+" Bolcked");
			else
				battlePanel.getMyAction().setText(((Fighter) e.getCurrentOpponent()).getName()+" Bolcked");
			battlePanel.updateInfo((Fighter)game.getBattle().getMe(),(Fighter) game.getBattle().getFoe(),e);
			
		}
		if(e.getType() == BattleEventType.NEW_TURN)
		{
			
			battlePanel.updateInfo(game.getPlayer().getActiveFighter(), foe,e);
			if(e.getCurrentOpponent().equals(game.getPlayer().getActiveFighter()))
			{
				
				battlePanel.showmyAction();
				
			}
			else
			{	
				
				try {
					game.getBattle().play();
					battlePanel.showGo();
				} catch (NotEnoughKiException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		if(e.getType() == BattleEventType.USE)
		{
			if(e.getCurrentOpponent() == foe)
				battlePanel.getCurrentState().setText(((Fighter) e.getCurrentOpponent()).getName()+" Used "+ e.getCollectible());
			else
				battlePanel.getMyAction().setText(((Fighter) e.getCurrentOpponent()).getName()+" Used "+ e.getCollectible());
			battlePanel.updateInfo(game.getPlayer().getActiveFighter(), foe,e);
		}
		
		if(e.getType() == BattleEventType.ENDED)
		{
			battle.stop();
			if(e.getWinner().equals(game.getPlayer().getActiveFighter()))
			{
				try {
					String soundName = "sounds/FF7win.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				JOptionPane.showMessageDialog(null, "You Won.");
				if(level < game.getPlayer().getActiveFighter().getLevel())
					JOptionPane.showMessageDialog(null, "Gained "+ ((NonPlayableFighter)game.getBattle().getFoe()).getLevel() * 5+" exp and " +(game.getPlayer()
						.getActiveFighter().getAbilityPoints()-(level/2))+" abilityPoints."
						+ "\nYou are now Level "+ game.getPlayer().getActiveFighter().getLevel()+ ". Target exp: "+ game.getPlayer().getActiveFighter().getTargetXp());
				
				String attacks = "";
				for(int i = 0 ; i < foe.getSuperAttacks().size();i++)
				{
					attacks += " "+foe.getSuperAttacks().get(i).getName()+ "\n";
				}
				for(int i = 0 ; i < foe.getUltimateAttacks().size();i++)
				{
					attacks += " "+foe.getUltimateAttacks().get(i).getName()+ "\n";
				}
				JOptionPane.showMessageDialog(null, "Learned :" + attacks);
				if(((NonPlayableFighter)game.getBattle().getFoe()).isStrong())
				{
					JOptionPane.showMessageDialog(null, "You beat this map. Number of explored maps is now: "+ game.getPlayer().getExploredMaps()+". Moving to new Map.");
					
				}
				
				
			}
			else
			{
				try {
					String soundName = "sounds/lose.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				JOptionPane.showMessageDialog(null, "You Lost. Loading last saved game.");
				Fighter input = game.getPlayer().getActiveFighter();
				if (input instanceof Earthling)
				{
					iconPath = "Images/fighters/earthlingIcon.png";
					w = we/20;
					h = he/10;
					x = we/2 + we/3 - we/12; 
					y = he/2 + he/3 - he/11;
				}
				else if( input instanceof Saiyan)
				{
					iconPath = "Images/fighters/Goku_standing_tall.png";
					y = he/2 + he/4 - he/50 ;
					x = we/2 + we/4 - we/37;
					w=we/13 ; 
					h = he/8;
				}
				else if (input instanceof Namekian)
				{
					iconPath = "Images/fighters/namekian.png";
					y = he/2 + he/4 - he/50 ; 
					x = we/2 + we/4 - we/60;
					w=we/15 ; 
					h = he/7;
				}
				else if(input instanceof Frieza)
				{
					iconPath =  "Images/fighters/friezaView.png";
					y = he/2 + he/4 - he/50 ;
					x = we/2 + we/4 - we/70;
					w=we/15 ;
					h = he/7;
				}
				else if (input instanceof Majin)
				{
					iconPath = "Images/fighters/majinIcon.png";
					y = he/2 + he/4 - he/50 ; 
					x = we/2 + we/4 - we/70;
					w=we/15 ; 
					h = he/7;
				}
				
			}
			try {
				createWorld(game.getPlayer().getName());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			worldPanel.position(game.getWorld().getPlayerRow(), game.getWorld().getPlayerColumn());
			worldPanel.update(game.getPlayer());
			view.setContentPane(worldPanel.getCont());
			view.repaint();
			view.validate();
			
		}	
		
			
			view.repaint();
			view.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
//Menu Buttons
		if(menuPanel != null)
			if(e.getSource() == menuPanel.getStart())
			{
				game = new Game();
				game.setListener(this);
				try {
					createFighter = new CreateFighterPanel();
					createFighter.getSaiyan().addActionListener(this);
					createFighter.getEarthling().addActionListener(this);
					createFighter.getFreiza().addActionListener(this);
					createFighter.getNamekian().addActionListener(this);
					createFighter.getMajin().addActionListener(this);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				view.setContentPane(createFighter);
				createFighter.getCreate().addActionListener(this);
				createFighter.getBack().addActionListener(this);
				view.repaint();
				view.validate();
			}else if(e.getSource() == menuPanel.getExit())
			{
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	view.dispose();
		        	System.exit(0);
		        }
			}
			else if( e.getSource() == menuPanel.getLoad())
			{
				try {
					
					game.load("saved/player.ser");
					game.setListener(this);
					theme.stop();
					world.play();
					world.loop();
					Player p = game.getPlayer();					

					int r = game.getWorld().getPlayerRow();
					int c = game.getWorld().getPlayerColumn();
					
					if(p.getActiveFighter() instanceof Saiyan) 
						{iconPath += "Goku_standing_tall.png"; y = he/2 + he/4 - he/50 ; x = we/2 + we/4 - we/37;w=we/13 ; h = he/8; }
					if(p.getActiveFighter() instanceof Earthling) 
						{ iconPath+= "earthlingIcon.png";w = we/20;h = he/10;x = we/2 + we/3 - we/12; y = he/2 + he/3 - he/15 ; }
					if(p.getActiveFighter() instanceof Namekian) 
						{iconPath += "namekian.png";y = he/2 + he/4 - he/50 ; x = we/2 + we/4 - we/60;w=we/15 ; h = he/7;}
					if(p.getActiveFighter() instanceof Frieza)
						{  iconPath +=  "friezaView.png";y = he/2 + he/4 - he/50 ; x = we/2 + we/4 - we/70;w=we/15 ; h = he/7;}
					if(p.getActiveFighter() instanceof Majin) 
						{ iconPath += "majinIcon.png";y = he/2 + he/4 - he/50 ; x = we/2 + we/4 - we/70;w=we/15 ; h = he/7;}
					
					worldPanel = new WorldPanel(p.getName(),iconPath,x,y,w,h);
					worldPanel.position(r,c);
					worldPanel.getPause().addActionListener(this);
					view.setContentPane(worldPanel.getCont());
					worldPanel.update(game.getPlayer());
		
					view.repaint();
					view.validate();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(view, "There is no saved file");
				}catch (ClassNotFoundException e2)
				{
					JOptionPane.showMessageDialog(view, "There was an error loading the file");
				}
			}
		
//World Buttons
		if(worldPanel != null )
		{
			if(e.getSource() == worldPanel.getPause())
			{
				world.stop();
				theme.play();
				theme.loop();
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				try {
					pauseMenu = new PauseMenu();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				pauseMenu.getSave().addActionListener(this);
				pauseMenu.getCreate().addActionListener(this);
				pauseMenu.getBack().addActionListener(this);
				pauseMenu.getExit().addActionListener(this);
				pauseMenu.getChooseFighter().addActionListener(this);
				pauseMenu.getUpgrade().addActionListener(this);
				pauseMenu.getAssign().addActionListener(this);
				view.setContentPane(pauseMenu.getCont());
				view.repaint();
				view.validate();
			}
			
		}		
		
//New game Buttons
		if(createFighter != null)
		{	
			if(e.getSource() == createFighter.getBack())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				view.setContentPane(menuPanel);
				view.repaint();
				view.validate();
			}
			if(e.getSource() == createFighter.getCreate() )
			{
				try {
					theme.stop();
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				String pName = createFighter.getPlayerName().getText();
				String fName = createFighter.getFighterName().getText();
				game.getPlayer().setName(pName);
				

				switch(race.charAt(0))
				{
				case 'S' : iconPath += "Goku_standing_tall.png"; y = he/2 + he/4 - he/50 ; x = we/2 + we/4 - we/37;w=we/13 ; h = he/8 ;break;
				case 'E' : iconPath+= "earthlingIcon.png";w = we/20;h = he/10;x = we/2 + we/3 - we/12; y = he/2 + he/3 - he/11 ; break;
				case 'N' : iconPath += "namekian.png";y = he/2 + he/4 - he/50 ; x = we/2 + we/4 - we/60;w=we/15 ; h = he/7;break;
				case 'F' : iconPath +=  "friezaView.png";y = he/2 + he/4 - he/50 ; x = we/2 + we/4 - we/70;w=we/15 ; h = he/7;break;
				case 'M' : iconPath += "majinIcon.png";y = he/2 + he/4 - he/50 ; x = we/2 + we/4 - we/70;w=we/15 ; h = he/7;break;
				}
				
				try {
					createWorld(pName);
				} catch (IOException e1) {
				
					e1.printStackTrace();
				}
				game.getPlayer().createFighter(race.charAt(0), fName);
				worldPanel.getFighterName().setText(fName);
				worldPanel.update(game.getPlayer());
				 
				
			}
			if(e.getSource() == createFighter.getSaiyan())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				createFighter.getSaiyan().setSelected(true);
				createFighter.getFreiza().setSelected(false);
				createFighter.getEarthling().setSelected(false);
				createFighter.getNamekian().setSelected(false);
				createFighter.getMajin().setSelected(false);
				race = "S";
				createFighter.getHP().setText("1000");
				createFighter.getKI().setText("5");
				createFighter.getPD().setText("100");
				createFighter.getST().setText("3");
				createFighter.getBD().setText("150");
				createFighter.getDesc().setText("Can transform to Super Saiyan and +1 stamina/turn");
				createFighter.setSelected(new ImageIcon(((new ImageIcon(
            "Images/fighters/goku.png").getImage()
            .getScaledInstance(440, 440,
                    java.awt.Image.SCALE_SMOOTH)))));
				
			}
			if(e.getSource() == createFighter.getFreiza())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				createFighter.getSaiyan().setSelected(false);
				createFighter.getFreiza().setSelected(true);
				createFighter.getEarthling().setSelected(false);
				createFighter.getNamekian().setSelected(false);
				createFighter.getMajin().setSelected(false);
				race = "F";
				createFighter.getHP().setText("1100");
				createFighter.getKI().setText("4");
				createFighter.getPD().setText("75");
				createFighter.getST().setText("4");
				createFighter.getBD().setText("75");
				createFighter.getDesc().setText("+1 stamina/turn");
				createFighter.setSelected(	new ImageIcon(((new ImageIcon(
			            "Images/fighters/frieza.png").getImage()
			            .getScaledInstance(430, 430,
			                    java.awt.Image.SCALE_SMOOTH)))));
				
			}
			if(e.getSource() == createFighter.getEarthling())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				createFighter.getSaiyan().setSelected(false);
				createFighter.getFreiza().setSelected(false);
				createFighter.getEarthling().setSelected(true);
				createFighter.getNamekian().setSelected(false);
				createFighter.getMajin().setSelected(false);
				race = "E";
				createFighter.getHP().setText("1250");
				createFighter.getKI().setText("4");
				createFighter.getPD().setText("50");
				createFighter.getST().setText("4");
				createFighter.getBD().setText("50");
				createFighter.getDesc().setText("+1 stamina/turn and +1 ki/my turn");
				createFighter.setSelected(new ImageIcon(((new ImageIcon(
            "Images/fighters/earthling.png").getImage()
            .getScaledInstance(340, 440,
                    java.awt.Image.SCALE_SMOOTH)))));
				
				
			}
			if(e.getSource() == createFighter.getNamekian())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				createFighter.getSaiyan().setSelected(false);
				createFighter.getFreiza().setSelected(false);
				createFighter.getEarthling().setSelected(false);
				createFighter.getNamekian().setSelected(true);
				createFighter.getMajin().setSelected(false);
				race = "N";
				createFighter.getHP().setText("1350");
				createFighter.getKI().setText("3");
				createFighter.getPD().setText("50");
				createFighter.getST().setText("5");
				createFighter.getBD().setText("0");
				createFighter.getDesc().setText("+1 stamina/turn and +50 health/turn");
				createFighter.setSelected(	new ImageIcon(((new ImageIcon(
			            "Images/fighters/namekianView.png").getImage()
			            .getScaledInstance(450, 500,
			                    java.awt.Image.SCALE_SMOOTH)))));
				
				
			}
			if(e.getSource() == createFighter.getMajin())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				createFighter.getSaiyan().setSelected(false);
				createFighter.getFreiza().setSelected(false);
				createFighter.getEarthling().setSelected(false);
				createFighter.getNamekian().setSelected(false);
				createFighter.getMajin().setSelected(true);
				race = "M";
				createFighter.getHP().setText("1500");
				createFighter.getKI().setText("3");
				createFighter.getPD().setText("50");
				createFighter.getST().setText("6");
				createFighter.getBD().setText("50");
				createFighter.getDesc().setText("+1 stamina/foe turn");
				createFighter.setSelected(new ImageIcon(((new ImageIcon(
            "Images/fighters/majin.png").getImage()
            .getScaledInstance(460, 500,
                    java.awt.Image.SCALE_SMOOTH)))));
			}
	}
//World Pause Menu Buttons
		if(pauseMenu != null)
		{
			
			if(e.getSource() == pauseMenu.getSave())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				int reply = JOptionPane.showConfirmDialog(null, "This will overwrite any previously saved games. Continue?", "Save", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	try {
		        		
						game.save("saved/player.ser");
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        	JOptionPane.showMessageDialog(null, "Game saved successfully.");
		        }
				
			}
			
			if(e.getSource() == pauseMenu.getChooseFighter())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				
				
				try {
					switchFighterPanel = new SwitchFighterPanel(game.getPlayer());
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				view.setContentPane(switchFighterPanel);
				switchFighterPanel.getBack().addActionListener(this);
				switchFighterPanel.getChoose().addActionListener(this);
				switchFighterPanel.getFighters().addActionListener(this);
				view.repaint();
				view.validate();
			}
			
			if(e.getSource() == pauseMenu.getAssign())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				String[] sa  , ua, Asa , Aua;
				ArrayList<SuperAttack> s = game.getPlayer().getSuperAttacks();
				ArrayList<UltimateAttack> u = game.getPlayer().getUltimateAttacks();
				ArrayList<SuperAttack> As = game.getPlayer().getActiveFighter().getSuperAttacks();
				ArrayList<UltimateAttack> Au = game.getPlayer().getActiveFighter().getUltimateAttacks();
				
				String tmp;
				sa= new String[s.size()+1];
				sa[0] = "none";
				for (int i = 1 ; i <= s.size() ; i++)
				{	
					tmp = s.get(i-1).getName();
					sa[i]=tmp;
				}
				ua= new String[u.size()+1];
				ua[0] = "none";
				for (int i = 1 ; i <= u.size() ; i++)
				{	
					tmp = u.get(i-1).getName();
					ua[i]=tmp;
				}
				
				Asa= new String[As.size()+1];
				Asa[0] = "none";
				for (int i = 1 ; i <= As.size() ; i++)
				{	
					tmp = As.get(i-1).getName();
					Asa[i]=tmp;
				}
				
				Aua= new String[Au.size()+1];
				Aua[0] = "none";
				for (int i = 1 ; i <= Au.size() ; i++)
				{	
					tmp = Au.get(i-1).getName();
					Aua[i]=tmp;
				}
				
				AssignAttack a = null;
				try {
					 a = new AssignAttack(sa,ua,Asa,Aua);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				int result = JOptionPane.showConfirmDialog(null, a, 
			               "Assign Attacks", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) 
				{
					if(!a.getSa().getSelectedItem().equals("none"))
					{
						SuperAttack old = null;
						SuperAttack newA = null;
						
						for ( int i = 0 ; i < s.size();i++)
						{
							if(s.get(i).getName().equals(a.getSa().getSelectedItem()))
							{
								newA = s.get(i);
								break;
							}
						}
						if(!a.getAsa().getSelectedItem().equals("none"))
						{
							for ( int i = 0 ; i < As.size();i++)
							{
								if(As.get(i).getName().equals(a.getAsa().getSelectedItem()))
								{
									old = As.get(i);
									break;
								}
							}
						}
						
						try {
							game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), newA, old);
						} catch (MaximumAttacksLearnedException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						} catch (DuplicateAttackException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						} catch (NotASaiyanException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					if(!a.getUa().getSelectedItem().equals("none"))
					{
						UltimateAttack old = null;
						UltimateAttack newA = null;
						
						for ( int i = 0 ; i < u.size();i++)
						{
							if(u.get(i).getName().equals(a.getUa().getSelectedItem()))
							{
								newA = u.get(i);
								break;
							}
						}
						if(!a.getAua().getSelectedItem().equals("none"))
						{
							for ( int i = 0 ; i < Au.size();i++)
							{
								if(Au.get(i).getName().equals(a.getAua().getSelectedItem()))
								{
									old = Au.get(i);
									break;
								}
							}
						}
						
						try {
							game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), newA, old);
						} catch (MaximumAttacksLearnedException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						} catch (DuplicateAttackException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						} catch (NotASaiyanException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
				}
			}
			
			if(e.getSource() == pauseMenu.getUpgrade())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				try {
					upgrade = new Upgrade(game.getPlayer().getActiveFighter());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				view.setContentPane(upgrade.getCont());
				upgrade.getBack().addActionListener(this);
				upgrade.getB().addActionListener(this);
				upgrade.getH().addActionListener(this);
				upgrade.getS().addActionListener(this);
				upgrade.getP().addActionListener(this);
				upgrade.getKi().addActionListener(this);
				view.repaint();
				view.validate();
			}
			
			if(e.getSource() == pauseMenu.getBack())
			{
				theme.stop();
				world.play();
				world.loop();
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				worldPanel.update(game.getPlayer());
				view.setContentPane(worldPanel.getCont());
				view.repaint();
				view.validate();
			}
			if(e.getSource() == pauseMenu.getExit())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				int reply = JOptionPane.showConfirmDialog(null, "Any unsaved Progress will be lost, Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	view.dispose();
		        	System.exit(0);
		        }
		       
			}
			
			if(e.getSource() == pauseMenu.getCreate())
			{
				
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				try {
					race = "S";
					createNewFighter = new CreateNewFighterPanel();
					createNewFighter.getSaiyan().addActionListener(this);
					createNewFighter.getEarthling().addActionListener(this);
					createNewFighter.getFreiza().addActionListener(this);
					createNewFighter.getNamekian().addActionListener(this);
					createNewFighter.getMajin().addActionListener(this);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				view.setContentPane(createNewFighter);
				createNewFighter.getCreate().addActionListener(this);
				createNewFighter.getBack().addActionListener(this);
				view.repaint();
				view.validate();
			}
			
		}

//Create New Fighter
		if(createNewFighter != null)
		{	
				if(e.getSource() == createNewFighter.getBack())
				{
					try {
						String soundName = "sounds/click.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
					view.setContentPane(pauseMenu.getCont());
					view.repaint();
					view.validate();
				}
				if(e.getSource() == createNewFighter.getCreate() )
				{
					try {
						String soundName = "sounds/click.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
					
					boolean exists = false;
					String fName = createNewFighter.getFighterName().getText();
					ArrayList<PlayableFighter> f = game.getPlayer().getFighters();
					for (int i = 0 ; i < f.size(); i++)
					{
						if(f.get(i).getName().equals(fName))
						{
							exists = true;
							break;
						}
					}
					if(exists)
					{
						JOptionPane.showMessageDialog(null, "Name Already taken");
					}else
					{
						theme.stop();
						world.play();
						world.loop();
						game.getPlayer().createFighter(race.charAt(0), fName);
						view.setContentPane(worldPanel.getCont());
						view.repaint();
						view.validate();
						
					}
					
				}
				if(e.getSource() == createNewFighter.getSaiyan())
				{
					try {
						String soundName = "sounds/click.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
					createNewFighter.getSaiyan().setSelected(true);
					createNewFighter.getFreiza().setSelected(false);
					createNewFighter.getEarthling().setSelected(false);
					createNewFighter.getNamekian().setSelected(false);
					createNewFighter.getMajin().setSelected(false);
					race = "S";
					createNewFighter.getHP().setText("1000");
					createNewFighter.getKI().setText("5");
					createNewFighter.getPD().setText("100");
					createNewFighter.getST().setText("3");
					createNewFighter.getBD().setText("150");
					createNewFighter.getDesc().setText("Can transform to Super Saiyan and +1 stamina/turn");
					createNewFighter.setSelected(new ImageIcon(((new ImageIcon(
	            "Images/fighters/goku.png").getImage()
	            .getScaledInstance(440, 440,
	                    java.awt.Image.SCALE_SMOOTH)))));
					
				}
				if(e.getSource() == createNewFighter.getFreiza())
				{
					try {
						String soundName = "sounds/click.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
					createNewFighter.getSaiyan().setSelected(false);
					createNewFighter.getFreiza().setSelected(true);
					createNewFighter.getEarthling().setSelected(false);
					createNewFighter.getNamekian().setSelected(false);
					createNewFighter.getMajin().setSelected(false);
					race = "F";
					createNewFighter.getHP().setText("1100");
					createNewFighter.getKI().setText("4");
					createNewFighter.getPD().setText("75");
					createNewFighter.getST().setText("4");
					createNewFighter.getBD().setText("75");
					createNewFighter.getDesc().setText("+1 stamina/turn");
					createNewFighter.setSelected(	new ImageIcon(((new ImageIcon(
				            "Images/fighters/frieza.png").getImage()
				            .getScaledInstance(430, 430,
				                    java.awt.Image.SCALE_SMOOTH)))));
					
				}
				if(e.getSource() == createNewFighter.getEarthling())
				{
					try {
						String soundName = "sounds/click.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
					createNewFighter.getSaiyan().setSelected(false);
					createNewFighter.getFreiza().setSelected(false);
					createNewFighter.getEarthling().setSelected(true);
					createNewFighter.getNamekian().setSelected(false);
					createNewFighter.getMajin().setSelected(false);
					race = "E";
					createNewFighter.getHP().setText("1250");
					createNewFighter.getKI().setText("4");
					createNewFighter.getPD().setText("50");
					createNewFighter.getST().setText("4");
					createNewFighter.getBD().setText("50");
					createNewFighter.getDesc().setText("+1 stamina/turn and +1 ki/my turn");
					createNewFighter.setSelected(new ImageIcon(((new ImageIcon(
	            "Images/fighters/earthling.png").getImage()
	            .getScaledInstance(340, 440,
	                    java.awt.Image.SCALE_SMOOTH)))));
					
					
				}
				if(e.getSource() == createNewFighter.getNamekian())
				{
					try {
						String soundName = "sounds/click.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
					createNewFighter.getSaiyan().setSelected(false);
					createNewFighter.getFreiza().setSelected(false);
					createNewFighter.getEarthling().setSelected(false);
					createNewFighter.getNamekian().setSelected(true);
					createNewFighter.getMajin().setSelected(false);
					race = "N";
					createNewFighter.getHP().setText("1350");
					createNewFighter.getKI().setText("3");
					createNewFighter.getPD().setText("50");
					createNewFighter.getST().setText("5");
					createNewFighter.getBD().setText("0");
					createNewFighter.getDesc().setText("+1 stamina/turn and +50 health/turn");
					createNewFighter.setSelected(	new ImageIcon(((new ImageIcon(
				            "Images/fighters/namekianView.png").getImage()
				            .getScaledInstance(450, 500,
				                    java.awt.Image.SCALE_SMOOTH)))));
					
					
				}
				if(e.getSource() == createNewFighter.getMajin())
				{
					try {
						String soundName = "sounds/click.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
					createNewFighter.getSaiyan().setSelected(false);
					createNewFighter.getFreiza().setSelected(false);
					createNewFighter.getEarthling().setSelected(false);
					createNewFighter.getNamekian().setSelected(false);
					createNewFighter.getMajin().setSelected(true);
					race = "M";
					createNewFighter.getHP().setText("1500");
					createNewFighter.getKI().setText("3");
					createNewFighter.getPD().setText("50");
					createNewFighter.getST().setText("6");
					createNewFighter.getBD().setText("50");
					createNewFighter.getDesc().setText("+1 stamina/foe turn");
					createNewFighter.setSelected(new ImageIcon(((new ImageIcon(
	            "Images/fighters/majin.png").getImage()
	            .getScaledInstance(460, 500,
	                    java.awt.Image.SCALE_SMOOTH)))));
				}
		}
		
//Switch fighters
		if(switchFighterPanel != null){
			if(e.getSource() == switchFighterPanel.getBack()){
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				view.setContentPane(pauseMenu.getCont());
				view.repaint();
				view.validate();
			}
			if(e.getSource() == switchFighterPanel.getChoose()){
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				ArrayList<PlayableFighter> fighters = game.getPlayer().getFighters();

				PlayableFighter input = null;
				for(int i= 0 ; i < fighters.size(); i++)
					if(fighters.get(i).getName().equals(switchFighterPanel.getFighters().getSelectedItem()))
						input = fighters.get(i);
				
				game.getPlayer().setActiveFighter(input);
		
				if (input instanceof Earthling){
					iconPath = "Images/fighters/earthlingIcon.png";
					w = we/20;
					h = he/10;
					x = we/2 + we/3 - we/12; 
					y = he/2 + he/3 - he/11;
				}
				else if( input instanceof Saiyan){
					iconPath = "Images/fighters/Goku_standing_tall.png";
					y = he/2 + he/4 - he/50 ;
					x = we/2 + we/4 - we/37;
					w=we/13 ; 
					h = he/8;
				}
				else if (input instanceof Namekian){
					iconPath = "Images/fighters/namekian.png";
					y = he/2 + he/4 - he/50 ; 
					x = we/2 + we/4 - we/60;
					w=we/15 ; 
					h = he/7;
				}
				else if(input instanceof Frieza){
					iconPath =  "Images/fighters/friezaView.png";
					y = he/2 + he/4 - he/50 ;
					x = we/2 + we/4 - we/70;
					w=we/15 ;
					h = he/7;
				}
				else if (input instanceof Majin){
					iconPath = "Images/fighters/majinIcon.png";
					y = he/2 + he/4 - he/50 ; 
					x = we/2 + we/4 - we/70;
					w=we/15 ; 
					h = he/7;
				}
				theme.stop();
				try {
					world.play();
					world.loop();
					worldPanel = new WorldPanel(game.getPlayer().getName(),iconPath,x,y,w,h);
					worldPanel.getPause().addActionListener(this);
					view.setContentPane(worldPanel.getCont());
					worldPanel.position(game.getWorld().getPlayerRow(), game.getWorld().getPlayerColumn());
					worldPanel.update(game.getPlayer());
					view.repaint();
					view.validate();
				} catch (IOException e1) {
			
					e1.printStackTrace();
				}		
			}
			if(e.getSource() == switchFighterPanel.getFighters()){
				
				String selectedFighter = (String) switchFighterPanel.getFighters().getSelectedItem();
				ArrayList<PlayableFighter> fighters = game.getPlayer().getFighters();

				PlayableFighter input = null;
				for(int i= 0 ; i < fighters.size(); i++)
					if(fighters.get(i).getName().equals(selectedFighter))
						input = fighters.get(i);
				
				switchFighterPanel.getHP().setText("" + input.getMaxHealthPoints());
				switchFighterPanel.getBD().setText("" + input.getBlastDamage());
				switchFighterPanel.getPD().setText("" + input.getPhysicalDamage());
				switchFighterPanel.getKI().setText("" + input.getMaxKi());
				switchFighterPanel.getST().setText("" + input.getMaxStamina());
				
				if (input instanceof Earthling){
					switchFighterPanel.setSelected(new ImageIcon(((new ImageIcon(
					"Images/fighters/earthling.png").getImage()
		            .getScaledInstance(440, 440,
		                    java.awt.Image.SCALE_SMOOTH)))));
					switchFighterPanel.getDesc().setText("+1 stamina/turn and +1 ki/my turn");
				}
				else if( input instanceof Saiyan){
					switchFighterPanel.setSelected(new ImageIcon(((new ImageIcon(
							"Images/fighters/goku.png").getImage()
				            .getScaledInstance(440, 440,
				                    java.awt.Image.SCALE_SMOOTH)))));
					switchFighterPanel.getDesc().setText("Can transform to Super Saiyan and gains 1 stamina/turn");
				}
				else if (input instanceof Namekian){
					switchFighterPanel.setSelected(new ImageIcon(((new ImageIcon(
							"Images/fighters/namekianView.png").getImage()
				            .getScaledInstance(440, 440,
				                    java.awt.Image.SCALE_SMOOTH)))));
					switchFighterPanel.getDesc().setText("+1 stamina/turn and +50 health/turn");
				}
				else if(input instanceof Frieza){
					switchFighterPanel.setSelected(new ImageIcon(((new ImageIcon(
							"Images/fighters/frieza.png").getImage()
				            .getScaledInstance(440, 440,
				                    java.awt.Image.SCALE_SMOOTH)))));
					switchFighterPanel.getDesc().setText("+1 stamina/turn");
				}
				else if (input instanceof Majin){
					switchFighterPanel.setSelected(new ImageIcon(((new ImageIcon(
							"Images/fighters/majin.png").getImage()
				            .getScaledInstance(440, 440,
				                    java.awt.Image.SCALE_SMOOTH)))));
					switchFighterPanel.getDesc().setText("+1 stamina/foe turn");
				}

			}
		}
// Dragon		
		if(dragonPanel != null)
		{
			
			if(e.getSource() == dragonPanel.getConfirm())
			{
				world.play();
				world.loop();
				game.getPlayer().chooseWish(wish);
				if(wish.getType() == DragonWishType.ABILITY_POINTS)
				{
					JOptionPane.showMessageDialog(null, "You have been granted "+ wish.getAbilityPoints()+" Ability Points.");
				}
				if(wish.getType() == DragonWishType.SENZU_BEANS)
				{
					JOptionPane.showMessageDialog(null, "You have been granted "+ wish.getSenzuBeans()+" Senzu Beans.");
				}
				if(wish.getType() == DragonWishType.SUPER_ATTACK)
				{
					JOptionPane.showMessageDialog(null, "You have been granted "+ wish.getSuperAttack().getName()+" Super Attack.");
				}
				if(wish.getType() == DragonWishType.ULTIMATE_ATTACK)
				{
					JOptionPane.showMessageDialog(null, "You have been granted "+ wish.getUltimateAttack().getName()+" Ultimate Attack.");
				}
				view.setContentPane(worldPanel.getCont());
				worldPanel.update(game.getPlayer());
				view.repaint();
				view.validate();
			}
			
			if(e.getSource() == dragonPanel.getSenzuBeans())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				wish = new DragonWish(this.dragon, DragonWishType.SENZU_BEANS, dragon.getSenzuBeans());
				dragonPanel.getDesc().setText("Get "+ dragon.getSenzuBeans()+ " Senzu Beans.");	
			}
			if(e.getSource() == dragonPanel.getAbilityPoints())
			{
				try {
				String soundName = "sounds/click.wav";    
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
				javax.sound.sampled.Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
		        } catch (Exception e1) {
		          System.err.println(e1.getMessage());
		        }
				wish = new DragonWish(this.dragon, DragonWishType.ABILITY_POINTS, dragon.getAbilityPoints());
				dragonPanel.getDesc().setText("Get "+ dragon.getAbilityPoints()+ " Ability Points.");
				
			}
			if(e.getSource() == dragonPanel.getSuperAttack())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				wish = wishes[2];
				dragonPanel.getDesc().setText("Get A Random Super Attack.");
				
			}
			if(e.getSource() == dragonPanel.getUltimateAttack())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				wish =  wishes[3];
				dragonPanel.getDesc().setText("Get A Raandom Ultimate Attack.");
				
			}
		}
// Upgrade Fighter		
		if(upgrade != null)
		{
			PlayableFighter f = game.getPlayer().getActiveFighter();
			Player p = game.getPlayer();
			if(e.getSource() == upgrade.getBack())
			{
				try {
					String soundName = "sounds/click.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
				view.setContentPane(pauseMenu.getCont());
				view.repaint();
				view.validate();
			}
			if(e.getSource() == upgrade.getH())
			{

				try {
					p.upgradeFighter(f, 'H');
					upgrade.getMaxH().setText(""+f.getMaxHealthPoints());
					upgrade.getAbility().setText(""+f.getAbilityPoints());
					view.repaint();
					view.validate();
				} catch (NotEnoughAbilityPointsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			
			}
			if(e.getSource() == upgrade.getKi())
			{
				try {
					p.upgradeFighter(f, 'K');
					upgrade.getMaxKi().setText(""+f.getMaxKi());
					upgrade.getAbility().setText(""+f.getAbilityPoints());
					view.repaint();
					view.validate();
				} catch (NotEnoughAbilityPointsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			
			}
			if(e.getSource() == upgrade.getS())
			{
				try {
					p.upgradeFighter(f, 'S');
					upgrade.getMaxS().setText(""+f.getMaxStamina());
					upgrade.getAbility().setText(""+f.getAbilityPoints());
					view.repaint();
					view.validate();
				} catch (NotEnoughAbilityPointsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			
			}
			if(e.getSource() == upgrade.getP())
			{
				try {
					p.upgradeFighter(f, 'P');
					upgrade.getpD().setText(""+f.getPhysicalDamage());
					upgrade.getAbility().setText(""+f.getAbilityPoints());
					view.repaint();
					view.validate();
				} catch (NotEnoughAbilityPointsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			
			}
			if(e.getSource() == upgrade.getB())
			{
				try {
					p.upgradeFighter(f, 'B');
					upgrade.getbD().setText(""+f.getBlastDamage());
					upgrade.getAbility().setText(""+f.getAbilityPoints());
					view.repaint();
					view.validate();
				} catch (NotEnoughAbilityPointsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			
			}
		}
//	Battle	
		if(battlePanel!= null && game.getState() == GameState.BATTLE)
		{
			
			if(e.getSource() == battlePanel.getPA())
			{
				try {
					
					game.getBattle().attack(new PhysicalAttack());
					Fighter f = game.getPlayer().getActiveFighter();
					if(f instanceof Saiyan)
					{
						if(((Saiyan) f).isTransformed())
						{
							battlePanel.icon("Images/map/gokuPa.gif");
						}
						else
						{
							battlePanel.icon("Images/map/gokuNPa.gif");
						}
					}
					if(f instanceof Frieza)
					{
						battlePanel.icon("Images/map/friezaPa.gif");
					}
					if(f instanceof Earthling)
					{
						battlePanel.icon("Images/map/krillinPa.gif");
					}
					if(f instanceof Majin)
					{
						battlePanel.icon("Images/map/buuPa.gif");
					}
					if(f instanceof Namekian)
					{
						battlePanel.icon("Images/map/piccoloPa.gif");
					}
				} catch (NotEnoughKiException e1) {
					e1.printStackTrace();
				}
				
				
			}
			if (e.getSource() == battlePanel.getAttack())
			{
				battlePanel.showAttacks();
				
			}
			if (e.getSource() == battlePanel.getSuperAttack())
			{
				battlePanel.showSuperAttacks();
			}
			if (e.getSource() == battlePanel.getUltimateAttack())
			{
				battlePanel.showUltimateAttacks();
			}
			if(e.getSource() == battlePanel.getBack())
			{
				battlePanel.back();
			}
			if(battlePanel.getSuperAButtons()[0] != null && e.getSource() == battlePanel.getSuperAButtons()[0])
			{
				
				try {
					game.getBattle().attack(game.getPlayer().getActiveFighter().getSuperAttacks().get(0));
					Fighter f = game.getPlayer().getActiveFighter();
					if(f instanceof Saiyan)
					{
						if(battlePanel.getSuperAButtons()[0].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/gokuCharge.gif");
						
						else if(((Saiyan) f).isTransformed())
						{
							battlePanel.icon("Images/map/gokuSa.gif");
						}
						else
						{
							battlePanel.icon("Images/map/gokuNSa.gif");
						}
					}
					if(f instanceof Frieza)
					{
						if(battlePanel.getSuperAButtons()[0].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/friezaCharge.gif");
						else 
							battlePanel.icon("Images/map/frieza attack.gif");
					}
					if(f instanceof Earthling)
					{
						if(battlePanel.getSuperAButtons()[0].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/krillinCharge.gif");
						else
							battlePanel.icon("Images/map/krillinSa.gif");
					}
					if(f instanceof Majin)
					{
						if(battlePanel.getSuperAButtons()[0].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/buuCharge.gif");
						else
						battlePanel.icon("Images/map/buuSa.gif");
					}
					if(f instanceof Namekian)
					{
						if(battlePanel.getSuperAButtons()[0].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/piccoloCharge.gif");
						else
						battlePanel.icon("Images/map/picoloSa.gif");
					}
					
					if(((Fighter)game.getBattle().getFoe()).getHealthPoints() > 0 && ((Fighter) game.getBattle().getMe()).getHealthPoints() >0)
					try {
						String soundName = "sounds/friezadeathball.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
				} catch (NotEnoughKiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			if(battlePanel.getSuperAButtons()[1] != null && e.getSource() == battlePanel.getSuperAButtons()[1])
			{
				
				try {
					game.getBattle().attack(game.getPlayer().getActiveFighter().getSuperAttacks().get(1));
					Fighter f = game.getPlayer().getActiveFighter();
					if(f instanceof Saiyan)
					{
						if(battlePanel.getSuperAButtons()[1].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/gokuCharge.gif");
						
						else if(((Saiyan) f).isTransformed())
						{
							battlePanel.icon("Images/map/gokuSa.gif");
						}
						else
						{
							battlePanel.icon("Images/map/gokuNSa.gif");
						}
					}
					if(f instanceof Frieza)
					{
						if(battlePanel.getSuperAButtons()[1].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/friezaCharge.gif");
						else 
							battlePanel.icon("Images/map/frieza attack.gif");
					}
					if(f instanceof Earthling)
					{
						if(battlePanel.getSuperAButtons()[1].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/krillinCharge.gif");
						else
							battlePanel.icon("Images/map/krillinSa.gif");
					}
					if(f instanceof Majin)
					{
						if(battlePanel.getSuperAButtons()[1].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/buuCharge.gif");
						else
						battlePanel.icon("Images/map/buuSa.gif");
					}
					if(f instanceof Namekian)
					{
						if(battlePanel.getSuperAButtons()[1].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/piccoloCharge.gif");
						else
						battlePanel.icon("Images/map/picoloSa.gif");
					}
					if(((Fighter)game.getBattle().getFoe()).getHealthPoints() > 0 && ((Fighter) game.getBattle().getMe()).getHealthPoints() >0)
					try {
						
						String soundName = "sounds/friezadeathball.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
				} catch (NotEnoughKiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			if(battlePanel.getSuperAButtons()[2] != null && e.getSource() == battlePanel.getSuperAButtons()[2])
			{
				
				try {
					game.getBattle().attack(game.getPlayer().getActiveFighter().getSuperAttacks().get(2));
					Fighter f = game.getPlayer().getActiveFighter();
					if(f instanceof Saiyan)
					{
						if(battlePanel.getSuperAButtons()[2].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/gokuCharge.gif");
						
						else if(((Saiyan) f).isTransformed())
						{
							battlePanel.icon("Images/map/gokuSa.gif");
						}
						else
						{
							battlePanel.icon("Images/map/gokuNSa.gif");
						}
					}
					if(f instanceof Frieza)
					{
						if(battlePanel.getSuperAButtons()[2].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/friezaCharge.gif");
						else 
							battlePanel.icon("Images/map/frieza attack.gif");
					}
					if(f instanceof Earthling)
					{
						if(battlePanel.getSuperAButtons()[2].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/krillinCharge.gif");
						else
							battlePanel.icon("Images/map/krillinSa.gif");
					}
					if(f instanceof Majin)
					{
						if(battlePanel.getSuperAButtons()[2].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/buuCharge.gif");
						else
						battlePanel.icon("Images/map/buuSa.gif");
					}
					if(f instanceof Namekian)
					{
						if(battlePanel.getSuperAButtons()[2].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/piccoloCharge.gif");
						else
						battlePanel.icon("Images/map/picoloSa.gif");
					}
					if(((Fighter)game.getBattle().getFoe()).getHealthPoints() > 0 && ((Fighter) game.getBattle().getMe()).getHealthPoints() >0)
					try {
						String soundName = "sounds/friezadeathball.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
				} catch (NotEnoughKiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			if(battlePanel.getSuperAButtons()[3] != null && e.getSource() == battlePanel.getSuperAButtons()[3])
			{
				
				try {
					game.getBattle().attack(game.getPlayer().getActiveFighter().getSuperAttacks().get(3));
					Fighter f = game.getPlayer().getActiveFighter();
					if(f instanceof Saiyan)
					{
						if(battlePanel.getSuperAButtons()[3].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/gokuCharge.gif");
						
						else if(((Saiyan) f).isTransformed())
						{
							battlePanel.icon("Images/map/gokuSa.gif");
						}
						else
						{
							battlePanel.icon("Images/map/gokuNSa.gif");
						}
					}
					if(f instanceof Frieza)
					{
						if(battlePanel.getSuperAButtons()[3].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/friezaCharge.gif");
						else 
							battlePanel.icon("Images/map/frieza attack.gif");
					}
					if(f instanceof Earthling)
					{
						if(battlePanel.getSuperAButtons()[3].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/krillinCharge.gif");
						else
							battlePanel.icon("Images/map/krillinSa.gif");
					}
					if(f instanceof Majin)
					{
						if(battlePanel.getSuperAButtons()[3].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/buuCharge.gif");
						else
						battlePanel.icon("Images/map/buuSa.gif");
					}
					if(f instanceof Namekian)
					{
						if(battlePanel.getSuperAButtons()[3].getText().equals("Maximum Charge"))
							battlePanel.icon("Images/map/piccoloCharge.gif");
						else
						battlePanel.icon("Images/map/picoloSa.gif");
					}
					if(((Fighter)game.getBattle().getFoe()).getHealthPoints() > 0 && ((Fighter) game.getBattle().getMe()).getHealthPoints() >0)
					try {
						String soundName = "sounds/friezadeathball.wav";    
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						javax.sound.sampled.Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
				        } catch (Exception e1) {
				          System.err.println(e1.getMessage());
				        }
				} catch (NotEnoughKiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			if(battlePanel.getUltimateAButtons()[0] != null  && e.getSource() == battlePanel.getUltimateAButtons()[0])
			{
				
				try { 
					game.getBattle().attack(game.getPlayer().getActiveFighter().getUltimateAttacks().get(0));
					Fighter f = game.getPlayer().getActiveFighter();
					if(f instanceof Saiyan)
					{
						if(battlePanel.getUltimateAButtons()[0].getText().equals("Super Saiyan"))
						{
							battlePanel.icon("Images/map/gokuTransform.gif");
						}
						else if(((Saiyan) f).isTransformed())
						{
							battlePanel.icon("Images/map/gokuUa.gif");
						}
						else
						{
							battlePanel.icon("Images/map/gokuNUa.gif");
						}
					}
					if(f instanceof Frieza)
					{
						battlePanel.icon("Images/map/friezaUa.gif");
					}
					if(f instanceof Earthling)
					{
						battlePanel.icon("Images/map/krillinUa.gif");
					}
					if(f instanceof Majin)
					{
						battlePanel.icon("Images/map/buuUa.gif");
					}
					if(f instanceof Namekian)
					{
						battlePanel.icon("Images/map/piccoloUa.gif");
					}
					if(battlePanel.getUltimateAButtons()[0].getText().equals("Super Saiyan") )
					{
						try {
							String soundName = "sounds/transformed.wav";    
							AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
							javax.sound.sampled.Clip clip = AudioSystem.getClip();
							clip.open(audioInputStream);
							clip.start();
					        } catch (Exception e1) {
					          System.err.println(e1.getMessage());
					        }
					}
					else
					{
						if(((Fighter)game.getBattle().getFoe()).getHealthPoints() > 0 && ((Fighter) game.getBattle().getMe()).getHealthPoints() >0)
						try {
							String soundName = "sounds/friezadeathball.wav";    
							AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
							javax.sound.sampled.Clip clip = AudioSystem.getClip();
							clip.open(audioInputStream);
							clip.start();
					        } catch (Exception e1) {
					          System.err.println(e1.getMessage());
					        }
					}
				} catch (NotEnoughKiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}if(battlePanel.getUltimateAButtons()[1] != null && e.getSource() == battlePanel.getUltimateAButtons()[1])
			{
				
				try {
					game.getBattle().attack(game.getPlayer().getActiveFighter().getUltimateAttacks().get(1));
					Fighter f = game.getPlayer().getActiveFighter();
					if(f instanceof Saiyan)
					{
						if(battlePanel.getUltimateAButtons()[1].getText().equals("Super Saiyan"))
						{
							battlePanel.icon("Images/map/gokuTransform.gif");
						}
						else 
						if(((Saiyan) f).isTransformed())
						{
							battlePanel.icon("Images/map/gokuUa.gif");
						}
						else
						{
							battlePanel.icon("Images/map/gokuNUa.gif");
						}
					}
					if(f instanceof Frieza)
					{
						battlePanel.icon("Images/map/friezaUa.gif");
					}
					if(f instanceof Earthling)
					{
						battlePanel.icon("Images/map/krillinUa.gif");
					}
					if(f instanceof Majin)
					{
						battlePanel.icon("Images/map/buuUa.gif");
					}
					if(f instanceof Namekian)
					{
						battlePanel.icon("Images/map/piccoloUa.gif");
					}
					if(battlePanel.getUltimateAButtons()[0].getText().equals("Super Saiyan") )
					{
						if(((Fighter)game.getBattle().getFoe()).getHealthPoints() > 0 && ((Fighter) game.getBattle().getMe()).getHealthPoints() >0)
						try {
							String soundName = "sounds/transformed.wav";    
							AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
							javax.sound.sampled.Clip clip = AudioSystem.getClip();
							clip.open(audioInputStream);
							clip.start();
					        } catch (Exception e1) {
					          System.err.println(e1.getMessage());
					        }
					}
					else
					{
						try {
							String soundName = "sounds/friezadeathball.wav";    
							AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
							javax.sound.sampled.Clip clip = AudioSystem.getClip();
							clip.open(audioInputStream);
							clip.start();
					        } catch (Exception e1) {
					          System.err.println(e1.getMessage());
					        }
					}
				} catch (NotEnoughKiException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			
			if(e.getSource() == battlePanel.getUse())
			{
				try {
					game.getBattle().use( game.getPlayer(), Collectible.SENZU_BEAN);
					heal.play();
				} catch (NotEnoughSenzuBeansException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			if(e.getSource() == battlePanel.getBlock())
			{
				game.getBattle().block();
			}
			
			if(e.getSource() == battlePanel.getGo())
			{
				if(battlePanel.getCurrentState().isVisible())
				{
					battlePanel.showActions();
					if(((NonPlayableFighter)game.getBattle().getFoe()).isStrong())
						battlePanel.foe("Images/map/cell.gif");
					else
						battlePanel.foe("Images/map/android.gif");
				}
				else if(battlePanel.getMyAction().isVisible())
				{
					Fighter f = game.getPlayer().getActiveFighter();
					if(f instanceof Saiyan)
					{
						if(((Saiyan) f).isTransformed())
						{
							battlePanel.icon("Images/map/gokuSS.gif");
						}
						else
						{
							battlePanel.icon("Images/map/gokuN.gif");
						}
					}
					if(f instanceof Frieza)
					{
						battlePanel.icon("Images/map/frieza.gif");
					}
					if(f instanceof Earthling)
					{
						battlePanel.icon("Images/map/krillin.gif");
					}
					if(f instanceof Majin)
					{
						battlePanel.icon("Images/map/buu.gif");
					}
					if(f instanceof Namekian)
					{
						battlePanel.icon("Images/map/piccolo.gif");
					}
					battlePanel.showState();
				}
				battlePanel.switchTurn();
				
			}
			
		}
		
		
	}
	
	
	private void createWorld(String in) throws IOException
	{
		
		worldPanel = new WorldPanel(in,iconPath,x,y,w,h);
		worldPanel.getPause().addActionListener(this);
		view.setContentPane(worldPanel.getCont());
		world.play();
		world.loop();
		view.repaint();
		view.validate();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}




	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
		if(game != null && game.getState() == GameState.WORLD && worldPanel!= null )
		{
			try{
				if(e.getKeyCode() == KeyEvent.VK_UP)
				{
					game.getWorld().moveUp();
					worldPanel.moveUp();	
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					game.getWorld().moveDown();
					worldPanel.moveDown();
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT)
				{
					game.getWorld().moveLeft();
					worldPanel.moveLeft();	
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					game.getWorld().moveRight();
					worldPanel.moveRight();	
				}
				worldPanel.update(game.getPlayer());
			} catch(MapIndexOutOfBoundsException mE)
			{
				try {
					String soundName = "sounds/beep-03.wav";    
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					javax.sound.sampled.Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
			        } catch (Exception e1) {
			          System.err.println(e1.getMessage());
			        }
			}
		}
			
		view.repaint();
		view.validate();
		
	}


	public static void main(String[] args) throws IOException {
		GameGUI g = new GameGUI();
		
				
	}

	



}
	

