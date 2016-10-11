package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import control.Client;

public class ApplicationWindow extends JFrame{
	//private static BoardPanel boardPanel;
	//private MessagePanel messagePanel;
	private InventoryPanel inventoryPanel; 
	//private Console console;
	private DungeonCanvas gameCanvas;
	private StartMenu start;
	private Client client;

	public ApplicationWindow(String title, Client user) {
		super(title);	
		gameCanvas = new DungeonCanvas();
		
		//this.messagePanel = new MessagePanel();
		
		this.inventoryPanel = new InventoryPanel(user, gameCanvas);
		//this.console = new Console();
		//this.messagePanel.makeMessagePanel(console);
		this.start = new StartMenu();
		this.start.addMenuListeners( new GameListener());
		
		//this.pack(); // pack components tightly together
		//this.setResizable(false); // prevent us from being resizeable
		//this.setVisible(true); // make sure we are visible!
		gameCanvas.setFocusable(true);
	}

	public void createAndShowGUI() {

		JFrame f = new JFrame(this.getTitle());
		f.setSize(800, 900);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		
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
			if(code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
				client.tellServerImMoving("UP");
			} else if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
				client.tellServerImMoving("DOWN");
			}
			else if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				client.tellServerImMoving("RIGHT");
			}
			else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				client.tellServerImMoving("LEFT");
			}
			else if(code == KeyEvent.VK_SPACE) {
				client.tellServerAction("PICK", null);
				Thread.sleep(100);
				inventoryPanel.foundChest();
				inventoryPanel.updateInventoryPanel();
				
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
			}
			else if(pushed.equals("Exit")){
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
