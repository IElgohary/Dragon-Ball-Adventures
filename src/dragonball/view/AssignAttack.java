package dragonball.view;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AssignAttack extends JPanel{
	private int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	private int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	private JComboBox sa;
	private JComboBox ua;
	private JComboBox Asa;
	private JComboBox Aua;
	private	JLabel saL;
	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public JComboBox getSa() {
		return sa;
	}

	public JComboBox getUa() {
		return ua;
	}

	public JComboBox getAsa() {
		return Asa;
	}

	public JComboBox getAua() {
		return Aua;
	}

	public JLabel getSaL() {
		return saL;
	}

	public JLabel getUaL() {
		return uaL;
	}

	public JLabel getAsaL() {
		return AsaL;
	}

	public JLabel getAuaL() {
		return AuaL;
	}

	private	JLabel uaL;
	private	JLabel AsaL;
	private	JLabel AuaL;
	
	
	public AssignAttack(String [] sa, String[] ua, String[] Asa, String[] Aua ) throws IOException
	{
		
		
		this.setLayout(new GridLayout(2,4));
		this.sa = new JComboBox(sa);
		this.ua = new JComboBox(ua);
		this.Asa = new JComboBox(Asa);
		this.Aua = new JComboBox(Aua);
		saL = new JLabel("New Super Attack");
		uaL = new JLabel("New Ultimate Attack");
		AsaL = new JLabel("Assigned Super Attack");
		AuaL = new JLabel("Assigned Ultimate Attack");
		
		add(saL);
		add(this.sa);
		add(uaL);
		add(this.ua);
		
		add(AsaL);
		add(this.Asa);
		add(AuaL);
		add(this.Aua);
		
		

	}
	
	public static void main(String[] args) throws IOException {
		GameView g = new GameView();
		String[] b = {"dasd","dasdasda"};
		AssignAttack a = new AssignAttack(b,b,b,b);
		g.setContentPane(a);
		g.repaint();
		g.validate();
	}

	
	
}
