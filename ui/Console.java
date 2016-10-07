package ui;
import java.awt.Color;

import javax.swing.JTextArea;

public class Console{

		StringBuilder mainString = new StringBuilder();
		JTextArea area;
		
		public void setArea(JTextArea givenArea){
		    area = givenArea;
		    area.setBackground(Color.cyan);
		  }
	/**
	 * Prints a message to the player on a text field in the game panel
	 * @param msg
	 */
		  public void print(String msg){
			mainString = mainString.append(msg);
			System.out.println(mainString.toString());
		    area.setText(mainString.toString()); 
		    area.paint(area.getGraphics());
		  }
		
}

