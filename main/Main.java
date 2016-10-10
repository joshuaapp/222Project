package main;

import java.io.IOException;

import control.Client;

public class Main {
	public static void main(String[] args) {
		Client client = new Client();
		new Thread(client).start();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				client.shutdown();
				return;
			}
		}, "Shutdown-thread"));
	}
}