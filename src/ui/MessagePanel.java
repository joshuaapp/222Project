package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MessagePanel extends JPanel{

    public MessagePanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

	public Dimension getPreferredSize() {
		
        return new Dimension(200,600);
    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.cyan);
	}
	
	public void makeMessagePanel(Console console) {
        JTextArea textPane = new JTextArea();
      //  console.setArea(textPane);					
        this.add(textPane);
	}
}
