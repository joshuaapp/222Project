package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import control.Client;
import items.InteractableItem;
import items.Item;
import items.Key;

public class InventoryPanel extends JPanel {
	JTextField text = new JTextField();
	JPanel buttonPanel = new JPanel();
	JButton[] itemButtons = new JButton[5];
	private Client client;
	Icon placeholderKey;
	private Icon activeKey;
	public InventoryPanel(Client client) {
		this.client = client;
		setBorder(BorderFactory.createLineBorder(Color.black));
		text.setText("Select inventory item");
		text.setBackground(Color.green);
		this.setLayout(new BorderLayout());
		text.setBorder(null);
		text.setHorizontalAlignment(JTextField.CENTER);
		this.add(buttonPanel, BorderLayout.CENTER);
		this.add(text, BorderLayout.PAGE_END);
		setupIcons();
		int x = 0;
		while(x < 5){
			//addButton();
			itemButtons[x] = addButton(x);
			x++;
		}
		updateInventoryPanel();
		//1 ring
		//2 key
		//3 key
		//4 key
		//5 key
	}

	private void setupIcons() {
		Image img;
		try {
			img = ImageIO.read(new File("placeholderkeybutton.png"));
			img = img.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
			this.placeholderKey = new ImageIcon(img);
			Image image;

			image = ImageIO.read(new File("keybuttonicon.png"));
			image = image.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
			this.activeKey = new ImageIcon(image);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Dimension getPreferredSize() {

		return new Dimension(800,150);

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		this.setBackground(Color.green);


	}
	public JButton addButton(int i){
		JButton button = new JButton();
		button.setActionCommand(i+"");
		button.setPreferredSize(new Dimension(125, 125));

		button.setIcon(placeholderKey);

		button.addActionListener(new ButtonListener());
		buttonPanel.add(button);
		return button;
	}
	public class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			System.out.println(command);
			switch(command){
			case "0":
				client.tellServerAction("DROP", "Key1");
				System.out.println("Key1");
				break;
			case "1":
				client.tellServerAction("DROP", "Key2");
				System.out.println("Key2");
				break;
			case "2":
				client.tellServerAction("DROP", "Key3");
				System.out.println("Key3");
				break;
			case "3":
				client.tellServerAction("DROP", "Key4");
				System.out.println("Key4");
				break;
			case "4":
				client.tellServerAction("DROP", "Key5");
				System.out.println("Key5");
				break;
			default:
			}
			System.out.println("LE FUCKING BUTTON PRESSED");
		}
	}

	public void updateInventoryPanel(){
		System.out.println("FountUpdate");
		ArrayList<Item> inventory = client.getPlayer().inven;
		for(JButton button: itemButtons){
			button.setIcon(placeholderKey);
		}
		for(Item m : inventory){
			System.out.println(m.getName());
			if(m instanceof Key){
				System.out.println("instance of key");
				Key key = (Key)m;
				switch(key.getColor()){
				case "YELLOW":
					System.out.println("Change button");
					itemButtons[0].setIcon(activeKey);
					break;
				default:
					System.out.println("YOU found DEFAULT");
				}
			}
		}
	
	}
}