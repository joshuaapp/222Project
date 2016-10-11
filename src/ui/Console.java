package ui;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
public class Console extends JPanel{
		StringBuilder mainString = new StringBuilder();
		JLabel area;
		private JLabel label;
		public Console(){
			//this.setText("I'm a fucking label");
			this.label = new JLabel("I'm a fucking label");
			label.setOpaque(true);
	        label.setBackground(Color.blue);
	        label.setForeground(Color.green);
	        add(label);
		}
		public Dimension getPrefferedSize(){
			return new Dimension(100,100);
		}
		
//		public void setArea(JLabel givenArea){
//		    area = givenArea;
//		    area.setBackground(Color.cyan);
//		  }
	/**
	 * Prints a message to the player on a text field in the game panel
	 * @param msg
	 */
//		  public void print(String msg){
//			mainString = mainString.append(msg);
//		    area.setText(mainString.toString()); 
//		    area.paint(area.getGraphics());
//		  }
//		
}
