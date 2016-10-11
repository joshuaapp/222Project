package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import control.Client;
/**
 * Application window is the main class that handles Swing
 *  and the JFrame that contains all the related Swing components.
 *  Also contains useful Button and Key Listeners
 * @author anna
 *
 */
public class ApplicationWindow extends JFrame{

	private InventoryPanel inventoryPanel; 
	private DungeonCanvas gameCanvas;
	private StartMenu start;
	private Client client;

	public ApplicationWindow(String title, Client user) {
		super(title);
		gameCanvas = new DungeonCanvas();
		this.inventoryPanel = new InventoryPanel(user, gameCanvas);
		this.start = new StartMenu();
		this.start.addMenuListeners( new GameListener());
		gameCanvas.setFocusable(true);			//make sure you return focus to canvas after clicking buttons
		this.client = user;
	}
	
	/**
	 * createAndShowGui essentially loads up the main part of the GUI 
	 * including creating the JFrame and adding all necessary components, setting layout
	 * and making it all visable
	 */
	public void createAndShowGUI() {

		JFrame f = new JFrame(this.getTitle());
		f.setSize(800, 900);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		inventoryPanel.setPreferredSize(getPreferredSize());
		f.add(inventoryPanel, BorderLayout.PAGE_END);
		f.add(gameCanvas, BorderLayout.CENTER);
		gameCanvas.addKeyListener(new GameKeyListener());
		f.setJMenuBar(start);
		f.pack();
		f.setVisible(true);
	}

	public DungeonCanvas getGameCanvas() {
		return gameCanvas;
	}


	/**
	 * GameKeyListener is a KeyListener that gives direction for when certain keys are pressed. 
	 * The main ones handled here are the arrow keys, WSDA and Space. 
	 * Each one has its own personal call to the server.
	 * @author anna
	 *
	 */
	public class GameKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			try{
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_W) {
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
				Thread.sleep(200);								//Thread sleep is added in many places 
				inventoryPanel.foundChest();					//due to a lag in some data returning from the server
				inventoryPanel.updateInventoryPanel();
			}
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
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			gameCanvas.repaint();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

	}
	/**
	 * GameListener is specifically for the menu bar, 
	 * which in this case holds only the exit option but could hold any other options 
	 * such as a level reset
	 * @author anna
	 *
	 */
	public class GameListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String pushed = e.getActionCommand();
			if(pushed.equals("Exit")){
				System.exit(0);
			}
		}

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
}
