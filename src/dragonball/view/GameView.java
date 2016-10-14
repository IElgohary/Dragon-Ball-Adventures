package dragonball.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameView extends JFrame{

	public GameView()
	{
		super("Dragon Ball Adventures");
		setUndecorated(true);
		setVisible(true);
		getContentPane().setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize());
		pack();
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
	}	

}
