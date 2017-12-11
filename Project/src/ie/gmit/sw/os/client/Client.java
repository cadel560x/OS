package ie.gmit.sw.os.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;




public class Client{
//  Data members
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message="";
	private String ipaddress;
	private Scanner stdin;
 	
 	
 	
 	
//  Constructors
	public Client() {
	    
	}
	
	
	
	
//	Methods
	public void init()
	{
		stdin = new Scanner(System.in);
		try{
			//1. creating a socket to connect to the server
			System.out.print("Please enter remote IP address: ");
			ipaddress = stdin.next();
			requestSocket = new Socket(ipaddress, 8080);
			System.out.println("Connected to "+ipaddress+" in port 8080");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			System.out.println("Hello");
			//3: Communicating with the server
			do{
				try
				{
						
				    
				    
				    
						message = (String)in.readObject();
						System.out.println("server> " + message);
						System.out.print("Please enter the message to send> ");
						message = stdin.next();
						sendMessage(message);
						
						
						
				}
				catch(ClassNotFoundException classNot)
				{
					System.err.println("data received in unknown format");
				} // try - catch
			} while ( !message.equals("bye") );
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			} // try - catch
		} // try - catch - catch - finally
		
	} // init
	
	
	public void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
//			System.out.println("client> " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		
	} // sendMessage
	
	
	
	
//	Entry point
	public static void main(String args[])
	{
		new Client().init();
	}
	
} // class Client