package ua.prog.java.lesson11b;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		int port= 8080; 
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			int connectedClientNumber =1;
		for(;;) {
			System.out.println();
			Socket c = serverSocket.accept();
			Client newClient = new Client(c);
			System.out.println("Client " + (connectedClientNumber++) + " is connected to the server.");
		}
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
