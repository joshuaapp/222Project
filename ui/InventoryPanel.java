package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Client;
import items.Crystal;
import items.Item;
import items.Key;

public class InventoryPanel extends JPanel {
	DungeonCanvas gameCanvas;
	int buttonSize = 100;
	JPanel buttonPanel = new JPanel();
	JButton[] itemButtons = new JButton[5];
	boolean gotInventoryBag = false;
	private Client client;
	private Icon placeholderKey;
	private Icon activeKey;
	private Icon activeCrystal;
	private Icon placeholderCrystal;
	
	public InventoryPanel(Client client, DungeonCanvas gameCanvas) {
		this.client = client;
		this.gameCanvas = gameCanvas;
		setBorder(BorderFactory.createLineBorder(Color.black));
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonPanel.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.CENTER);
		setupIcons();
		updateInventoryPanel();
	}

	public void foundChest(){
		if(client.getPlayer().gotBag == true){
			if(gotInventoryBag == false){
				this.gotInventoryBag = true;
				int x = 0;
				while(x < 5){
					itemButtons[x] = addButton(x);
					x++;
				}
				this.revalidate();
			}
		}
	}

	private void setupIcons() {
		Image img;
		try {
			img = ImageIO.read(new File("placeholderkeybutton.png"));
			img = img.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
			this.placeholderKey = new ImageIcon(img);
			Image image;

			image = ImageIO.read(new File("keybuttonicon.png"));
			image = image.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
			this.activeKey = new ImageIcon(image);
			
			image = ImageIO.read(new File("activeCrystal.png"));
			image = image.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
			this.activeCrystal = new ImageIcon(image);
			
			image = ImageIO.read(new File("placeholderCrystal.png"));
			image = image.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
			this.placeholderCrystal = new ImageIcon(image);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Dimension getPreferredSize() {

		return new Dimension(600,125);

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		this.setBackground(Color.green);


	}
	public JButton addButton(int i){
		JButton button = new JButton();
		button.setActionCommand(i+"");
		button.setPreferredSize(new Dimension(buttonSize, buttonSize));
		button.setFocusable(false);
		button.setIcon(placeholderKey);

		button.addActionListener(new ButtonListener());
		buttonPanel.add(button);
		return button;
	}
	public class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			try {
				switch(command){
				case "0":
					client.tellServerAction("DROP", "Key1");
					break;
				case "1":
					client.tellServerAction("DROP", "Key2");

					break;
				case "2":
					client.tellServerAction("DROP", "Key3");

					break;
				case "3":
					client.tellServerAction("DROP", "Key4");

					break;
				case "4":
					client.tellServerAction("DROP", "Key5");

					break;
				default:
				}

				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			updateInventoryPanel();
			gameCanvas.repaint();
		}
	}

	public void updateInventoryPanel(){
		if(gotInventoryBag){
			ArrayList<Item> inventory = client.getPlayer().inven;
			for (int i = 0; i < itemButtons.length; i++) {
				JButton button = itemButtons[i];
				if(i == 4){
					button.setIcon(placeholderCrystal);
				}
				else{
					button.setIcon(placeholderKey);
					button.setText("");
				}
			}
			int bagCount = 0;
			for(Item m : inventory){
				JButton button = itemButtons[bagCount];
				if(m instanceof Crystal){
					//Crystal c = (Crystal)m;
					itemButtons[4].setIcon(activeCrystal);
				}
				if(m instanceof Key){
					Key key = (Key)m;
						button.setText(key.getCode()+1+"");
						button.setHorizontalTextPosition(JButton.CENTER);
						button.setVerticalTextPosition(JButton.CENTER);
						button.setIcon(activeKey);
						if(bagCount < 3){bagCount++;}
					//	break;
				//	default:
				//	}

				}
			}
		}
	}
}