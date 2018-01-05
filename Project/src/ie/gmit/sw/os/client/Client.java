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
	private Scanner scanner;
 	
 	
 	
 	
//  Constructors
	public Client() {
	    this.scanner = new Scanner(System.in);
	}
	
	
	
	
//	Methods
	public void init()
	{
		
		try{
			//1. creating a socket to connect to the server
//			System.out.print("Please enter remote IP address: ");
//			ipaddress = scanner.next();
		    ipaddress = "127.0.0.1";
			
			requestSocket = new Socket(ipaddress, 8080);
//			requestSocket = new Socket("127.0.0.1", 8080);
			
			System.out.println("Connected to " + ipaddress + " in port 8080");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
//			PrintWriter out = new PrintWriter(this.requestSocket.getOutputStream(), true);
//			PrintWriter out = new PrintWriter(this.requestSocket.getOutputStream(), true);
			out.flush();
//			in = new ObjectInputStream(requestSocket.getInputStream());
			
//			PrintStream out = new PrintStream( requestSocket.getOutputStream(), true );
            BufferedReader in = new BufferedReader( new InputStreamReader( requestSocket.getInputStream() ) );
			
			
//			System.out.println("Hello");
			//3: Communicating with the server
			do {
//				try
//				{	    
//						message = (String)in.readObject();
//						System.out.println("server> " + message);
//						System.out.print("Please enter the message to send> ");
//						message = stdin.next();
//						sendMessage(message);
				    
//				    message = (String)in.readObject();
//				    System.out.print(message);
//				    message = stdin.nextLine();
//				    sendMessage(message);
				    
				    String line = in.readLine();
//				    String line = in.readUTF();
//			    String line = (String) in.readObject();
                    while( line != null )
                    {
                        System.out.println( line );
                        line = in.readLine();
//                        line = in.readUTF();
//                        line = (String) in.readObject();
                    }
                    
                    message = scanner.nextLine();
//                    out.print(message);
                    out.writeUTF(message);
	
//				}
//				catch(ClassNotFoundException classNot)
//				{
//					System.err.println("data received in unknown format");
//				} // try - catch
//			} while ( !message.equals("bye") );
			} while ( true );
			
		} //try - catch(UnknownHostException unknownHost)
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