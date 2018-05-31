package ua.prog.java.lesson11b;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

public class Client implements Runnable {
	private Socket clientSocket;
	private String responseHeader = 			
			"HTTP/1.1 200 OK\r\n" 
			+ "Server: My_Server\r\n" 
			+ "Content-Type:text/html\r\n"
			+ "Content-Length: " 
			+ "\r\n" 
			+ "Connection: close\r\n\r\n";
	private StringBuilder answer = new StringBuilder();

	public Client(Socket socket) {
		super();
		this.clientSocket = socket;
		getServerParameters();
		Thread newThread = new Thread(this);
		newThread.start();
	}

	public Client() {
		super();
	}

	public StringBuilder getAnswer() {
		return answer;
	}

	public void setAnswer(StringBuilder answer) {
		this.answer = answer;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public String getResponseHeader() {
		return responseHeader;
	}

	public void getServerParameters() {
		answer.append("<H1><b> System Parameters: </b></H1><br>");
		Runtime.getRuntime().gc();
		Long freeMem = Runtime.getRuntime().freeMemory();
		answer.append("Free memory available for Java Machine: " + freeMem / 1000000 + "MB");
		answer.append("<br>");
		Long maxMem = Runtime.getRuntime().maxMemory();
		answer.append("Maximum amount of memory available for Java Machine: " + maxMem / 1000000 + "MB");
		answer.append("<br>");
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		answer.append("Amount of Available Processors: " + availableProcessors);
		answer.append("<br>");
		Properties pro = System.getProperties();
		for (Object obj : pro.keySet()) {
			answer.append(" System  " + (String) obj + "     :  " + System.getProperty((String) obj));
			answer.append("<br>");
		}
	}

	@Override
	public void run() {
		try (InputStream inputStream = clientSocket.getInputStream();
				OutputStream outputStream = clientSocket.getOutputStream();
				PrintWriter pw = new PrintWriter(outputStream)) {
			byte[] bytesForReading = new byte[inputStream.available()];
			inputStream.read(bytesForReading);
			pw.print(responseHeader + answer.toString());
			pw.flush();
		} catch (IOException e) {
			System.out.println(e.toString());
		}

	}

}
