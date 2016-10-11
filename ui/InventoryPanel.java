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

import control.Client;
import items.Item;
import items.Key;

public class InventoryPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4794236279260239484L;
	//JTextField text = new JTextField();
	int buttonSize = 115;
	JPanel buttonPanel = new JPanel();
	JButton[] itemButtons = new JButton[3];
	boolean gotInventoryBag = false;
	private Client client;
	Icon placeholderKey;
	DungeonCanvas gameCanvas;
	private Icon activeKey;
	public InventoryPanel(Client client, DungeonCanvas gameCanvas) {
		this.client = client;
		this.gameCanvas = gameCanvas;
		setBorder(BorderFactory.createLineBorder(Color.black));
		//buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		//text.setText("Find the Chest to gain your Backpack");
		//text.setBackground(Color.green);
		buttonPanel.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());
		//text.setBorder(null);
		//text.setHorizontalAlignment(JTextField.CENTER);
		this.add(new JLabel("Le Label"));
		this.add(buttonPanel, BorderLayout.CENTER);
		//this.add(text, BorderLayout.PAGE_END);
		setupIcons();

		updateInventoryPanel();

	}

	public void foundChest(){
		if(client.getPlayer().isGotBag() == true){
			if(gotInventoryBag == false){
				this.gotInventoryBag = true;
				int x = 0;
				while(x < 3){
					//addButton();
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			System.out.println("Here");
			//text.setText("Select Inventory Item to drop");
			ArrayList<Item> inventory = client.getPlayer().getInven();
			for(JButton button: itemButtons){
				button.setIcon(placeholderKey);
				button.setText("");
			}
			int bagCount = 0;
			for(Item m : inventory){
				if(m instanceof Key){
					Key key = (Key)m;
					//switch(key.getColor()){
					//case "YELLOW":
						//itemButtons[bagCount].set;
						itemButtons[bagCount].setText(key.getCode()+1+"");
						itemButtons[bagCount].setHorizontalTextPosition(JButton.CENTER);
						itemButtons[bagCount].setVerticalTextPosition(JButton.CENTER);
						itemButtons[bagCount].setIcon(activeKey);
						if(bagCount < 2){bagCount++;}
					//	break;
				//	default:
				//	}

				}
			}
		}
	}
}