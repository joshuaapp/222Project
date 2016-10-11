package main;

import control.Client;

/**Main method to run a client. Client will only run providing the server is running.
 *
 * @author apperljosh
 *
 */
public class ClientMain {

	public static void main(String args[]){
		//create a new client
		Client client = new Client();
		//start the client thread
		new Thread(client).start();
		//add shutdown hook to properly close down client and disconnect from server when application is exited
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				client.shutdown();
				return;
			}
		}, "Shutdown-thread"));
	}

}
