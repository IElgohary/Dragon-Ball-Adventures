package dragonball.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CellButton extends JButton {
	private JLabel character = new JLabel();
	
	public CellButton()
	{
		super();
		add(character);
		
		
	}
	
	public JLabel getCharacter() {
		return character;
	}

	public void setCharacter(ImageIcon character) {
		this.character = new JLabel(character);
		
	}
}
