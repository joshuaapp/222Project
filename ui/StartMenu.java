package ui;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class StartMenu extends JMenuBar{

		JMenu start = new JMenu("Menu");
		JMenuItem exit = new JMenuItem("Exit");
		
		
		
		public StartMenu(){
			System.out.println("In start");
			this.add(start);
			start.add(exit);
		}
	/**
	 * Adds ActionListeners to player menu items	
	 * @param cluedoListener
	 */
		public void addMenuListeners(ActionListener gameListener){
			exit.addActionListener(gameListener);
		}
}
