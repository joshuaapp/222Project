package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import control.Client;

public class ApplicationWindow extends JFrame{

	
	private InventoryPanel inventoryPanel; 

	private DungeonCanvas gameCanvas;
	//private Console console;
	private StartMenu start;
	private Client client;

	public ApplicationWindow(String title, Client user) {
		super(title);
		gameCanvas = new DungeonCanvas();
		
		//this.messagePanel = new MessagePanel();
		
		this.inventoryPanel = new InventoryPanel(user, gameCanvas);
		//this.console = new Console();
		//console.setArea(new JLabel("Image and Text"));
		//this.messagePanel.makeMessagePanel(console);
		this.start = new StartMenu();
		this.start.addMenuListeners( new GameListener());
		gameCanvas.setFocusable(true);
		this.client = user;
	}

	public void createAndShowGUI() {

		JFrame f = new JFrame(this.getTitle());
		f.setSize(800, 900);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		//console.setBackground(Color.CYAN);
		//console.setPreferredSize(getPreferredSize());
		//console.setOpaque(false);
		
		//gameCanvas.add(console);
		//gamePanel.setPreferredSize(getPreferredSize());
		//messagePanel.setPreferredSize(getPreferredSize());
		inventoryPanel.setPreferredSize(getPreferredSize());
		//f.add(messagePanel, BorderLayout.LINE_END);
		f.add(inventoryPanel, BorderLayout.PAGE_END);
		f.add(gameCanvas, BorderLayout.CENTER);
		gameCanvas.addKeyListener(new GameKeyListener());

		//  messagePanel.setMaximumSize(getPreferredSize());
		//  messagePanel.setPreferredSize(getPreferredSize());
		// f.setLayout(new GridLayout(1,2));
		//f.setLayout(new BorderLayout());
		f.setJMenuBar(start);
		//gameCanvas.add(gameCanvas.label);
		f.pack();
		f.setVisible(true);
		//this.writeOut("Player messages go here :)");
	}

	public DungeonCanvas getGameCanvas() {
		return gameCanvas;
	}

	/**
	 * Creates a basic text panel
	 * @return
	 */
	protected static JComponent makeTextPanel() {
		JPanel panel = new JPanel(false);
		JTextArea textPane = new JTextArea();
		panel.setLayout(new GridLayout(1, 1));
		panel.add(textPane);
		return panel;
	}
//	/**
//	 * Put a message to the 'console' for the player to see
//	 * @param string
//	 */
//	public void writeOut(String string) {
//
//	}

	public class GameKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			try{
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_W) {
				System.out.println("telling "+client+" to move");
				client.tellServerImMoving("UP");
			} else if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_S) {
				client.tellServerImMoving("DOWN");
			}
			else if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_D) {
				client.tellServerImMoving("RIGHT");
			}
			else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_A) {
				client.tellServerImMoving("LEFT");
			}
			else if(code == KeyEvent.VK_SPACE) {
				client.tellServerAction("PICK", null);
				Thread.sleep(200);
				inventoryPanel.foundChest();
				inventoryPanel.updateInventoryPanel();
				//update game text
				List<String> textToUpdate = Arrays.asList("You found a bag! You can now pick up keys. Keys unlock doors. I heard there is a magic crystal floating around in a room somewhere....".split(" "));	
				gameCanvas.updateCanvasText(textToUpdate);
			}
			//Need an action here where when a button is pressed it calls client.tellServerAction("DROP", a string called itemName);
			//for now pressing d will drop an 'item'
			else if(code == KeyEvent.VK_D) {
				client.tellServerAction("DROP", "Key");
				Thread.sleep(100);
				inventoryPanel.updateInventoryPanel();
			}
			Thread.sleep(100);
			inventoryPanel.updateInventoryPanel();
			gameCanvas.repaint();
			}
			catch(NullPointerException ee){
				System.out.println(ee.getMessage());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			gameCanvas.repaint();

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class GameListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String pushed = e.getActionCommand();
			if(pushed.equals("Restart")){
				System.out.println("Reset");
				//client.resetLevel();
			}
			else if(pushed.equals("Exit")){
				System.exit(0);
			}
			
			

			
		}
		
	}
	/**
	 * Put a message to the 'console' for the player to see
	 * @param string
	 */
	public void writeOut(String string) {
	}
	/**Method to connect a client to a certain window so the window can send requests
	 * from client to server.
	 * @param client
	 */
	public void attatchClientToWindow(Client client) {
		if(client != null){
			this.client = client;
		}
	}

	public void updateAll() {
		this.gameCanvas.repaint();
		this.inventoryPanel.repaint();
	}

}
