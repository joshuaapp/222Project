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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Client;
import items.Crystal;
import items.Item;
import items.Key;
/**
 * Inventory Panel contains a JPanel for the JButtons, 
 * stores the icons for the buttons and methods to
 * update the panel for the players' viewing.
 * @author Anna Henderson
 *
 */
public class InventoryPanel extends JPanel {
	DungeonCanvas gameCanvas;
	int buttonSize = 100;

	private static final long serialVersionUID = -4794236279260239484L;
	//JTextField text = new JTextField();

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
		this.activeCrystal = setupIcons("src/assets/activeCrystal.png");					//Button Icons
		this.placeholderCrystal = setupIcons("src/assets/placeholderCrystal.png");
		this.activeKey = setupIcons("src/assets/keybuttonicon.png");
		this.placeholderKey = setupIcons("src/assets/placeholderkeybutton.png");
		updateInventoryPanel();
	}
	/**
	 * FoundChest is called when the player opens the chest object on the map, if the 
	 * player has stored the bag from the chest then all buttons are created in 
	 * their inactive state.
	 * 
	 */
	public void foundChest(){
		if(client.getPlayer().isGotBag() == true){
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
	
	/**
	 * Takes an image fileName and creates a scaled Icon version of it.
	 * @param inputImage - image file to be converted
	 * @return Icon
	 */
	private Icon setupIcons(String inputImage) {
		Image img;
		try {
			img = ImageIO.read(new File(inputImage));
			img = img.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
			return new ImageIcon(img);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Dimension getPreferredSize() {
		return new Dimension(600,125);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.green);
	}
	
	/**
	 * addButton creates a new JButton instance with the action command set to 
	 * be equal to input i
	 * @param i - set to JButton's action command
	 * @return - JButton
	 */
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
	
	/**
	 * ButtonListener is tuned into the buttons on the button panel (contained inside
	 * the Inventory Panel). Mainly passes through to the server a "Drop" action
	 * @author anna
	 *
	 */
	public class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			try {
				switch(command){
				case "0":
					client.tellServerAction("DROP", "KEY");
					break;
				case "1":
					client.tellServerAction("DROP", "KEY");
					System.out.println("Key2");

					break;
				case "2":
					client.tellServerAction("DROP", "KEY");
					System.out.println("Key3");

					break;
				case "3":
					client.tellServerAction("DROP", "KEY");
					System.out.println("Key4");

					break;
				case "4":
					client.tellServerAction("DROP", "CRYSTAL");
					System.out.println("Key5");

					break;
				default:
					break;
				}
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			updateInventoryPanel();									//refresh the inventory panel
			gameCanvas.repaint();									
		}
	}
/**
 * Depending on if player is in possession of the bag (found in the chest), 
 * then the inventory panel is revealed to the player. As items are collected,
 * used or discarded, they are displayed as either active or inactive
 */
	public void updateInventoryPanel(){
		if(gotInventoryBag){
			ArrayList<Item> inventory = client.getPlayer().getInven();
			for (int i = 0; i < itemButtons.length; i++) {
				JButton button = itemButtons[i];
				if(i == 4){											//Unique case of crystal
					button.setIcon(placeholderCrystal);				//Set to be inactive crystal image
				}
				else{												//Setting text to nothing and image to inactive for the keys
					button.setIcon(placeholderKey);
					button.setText("");
				}
			}
			int bagCount = 0;										//Bag count keeps count of where the next key needs to be added from the array
			for(Item m : inventory){
				JButton button = itemButtons[bagCount];
				if(m instanceof Crystal){							//If the item found is a crystal it must be placed in a specific place in the inventory panel
					itemButtons[4].setIcon(activeCrystal);
				}
				if(m instanceof Key){								//The keys can be added in the order they were collected in
					Key key = (Key)m;
					button.setText(key.getCode()+1+"");				//Set the text of the button to equal the code of the key
					button.setHorizontalTextPosition(JButton.CENTER);
					button.setVerticalTextPosition(JButton.CENTER);
					button.setIcon(activeKey);
					if(bagCount < 3){bagCount++;}
				}
			}
		}
	}
}