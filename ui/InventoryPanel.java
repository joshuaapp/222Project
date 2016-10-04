package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class InventoryPanel extends JPanel {
	JTextField text = new JTextField();
	JPanel buttonPanel = new JPanel();
	 public InventoryPanel() {
	        setBorder(BorderFactory.createLineBorder(Color.black));
			text.setText("Select inventory item");
			text.setBackground(Color.green);
			this.setLayout(new BorderLayout());
			text.setBorder(null);
			text.setHorizontalAlignment(JTextField.CENTER);
	       this.add(buttonPanel, BorderLayout.CENTER);
	        this.add(text, BorderLayout.PAGE_END);
	        int x = 0;
	        while(x < 5){
	        addButton();
	        x++;
	        }
	    }

		public Dimension getPreferredSize() {
		
	        return new Dimension(800,150);
	        
	    }
		
		public void paintComponent(Graphics g) {
		   
	        super.paintComponent(g);
	        this.setBackground(Color.green);
	       
	        
		}
		public void addButton(){
			JButton button = new JButton();
			button.setPreferredSize(new Dimension(125, 125));
			 try {
				    Image img = ImageIO.read(new File("pro_img.png"));
				    img = img.getScaledInstance(100, 120, Image.SCALE_SMOOTH);
				    button.setIcon(new ImageIcon(img));
				  } catch (IOException ex) {
					  System.out.println("Image Button issue");

				  }
			buttonPanel.add(button);
		}
}
