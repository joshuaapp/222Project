package ui;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * The start menu implemented here is a drop down that could hand many more options,
 * at this stage it only hold the exit option.
 * @author anna
 *
 */
public class StartMenu extends JMenuBar{

		JMenu start = new JMenu("Menu");
		JMenuItem exit = new JMenuItem("Exit");
		
		
		
		public StartMenu(){
			this.add(start);
			start.add(exit);
		}
	/**
	 * Adds ActionListeners to player menu items	
	 * @param ActionListener - connected to the JMenuItems
	 */
		public void addMenuListeners(ActionListener gameListener){
			exit.addActionListener(gameListener);
		}
}
