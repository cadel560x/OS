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
	private int port;
	private Scanner scanner;
 	
 	
 	
 	
//  Constructors
	public Client() {
	    this.scanner = new Scanner(System.in);
	}
	
	
	
	
//	Methods
    public void sendMessage(String msg) throws IOException
    {
//        try{
            out.writeObject(msg);
            out.flush();
//            System.out.println("client> " + msg);
//            System.out.println(msg);
//        }
//        catch(IOException ioException){
//            ioException.printStackTrace();
//        }
        
    } // sendMessage
    
	public void init()
	{
		
		try{
			//1. creating a socket to connect to the server
//			System.out.print("Please enter remote IP address: ");
//			ipaddress = scanner.next();
//          System.out.print("Please enter port number: ");
//          port = Integer.parseInt(scanner.next());
		    ipaddress = "127.0.0.1";
		    port = 8080;
			
			requestSocket = new Socket(ipaddress, port);
			System.out.println("Connected to " + ipaddress + " in port " + port);
			
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			//3: Communicating with the server
			do {
				try
				{   
//				    Welcome banner
					message = (String)in.readObject();
                    System.out.println(message);
                    
//	                Login process
                    this.login();
                    
//	                    Main menu
                    this.mainMenu();
//                    
//                    message = (String)in.readObject();
//                    System.out.println(message);
//                    
//                    
//                    message = scanner.nextLine();
//                    sendMessage(message);
//                    
//                    if(message.equalsIgnoreCase("ADD"))
//                    {
//                        message = (String)in.readObject();
//                        System.out.println(message);
//                        message = scanner.nextLine();
//                        sendMessage(message);
//                        
//                        message = (String)in.readObject();
//                        System.out.println(message);
//                        message = scanner.nextLine();
//                        sendMessage(message);
//                        
//                        message = (String)in.readObject();
//                        System.out.println(message);
//                        
//                    }
//                    
//                    else if(message.equalsIgnoreCase("SQUARE"))
//                    {
//                        
//                    }
//                    else
//                    {
//                        
//                    }
				    
	
				}
				catch(ClassNotFoundException classNot)
				{
					System.err.println("data received in unknown format");
				} // try - catch
			} while ( !message.equals("bye") );
			
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
	
	
    private void login() throws ClassNotFoundException, IOException {
	    String username, password;
	    
	    do {
    //	    'Login:' from server
    	    message = (String)in.readObject();
            System.out.print(message);
            
    //      Send username
            username = scanner.nextLine();
            sendMessage(username);
            
    //      'Password:' from server
            message = (String)in.readObject();
            System.out.print(message);
            
    //      Send password
            password = scanner.nextLine();
            sendMessage(password);
            
//          Next string will tell us if we pass login or not
            message = (String)in.readObject();
            System.out.print(message);
	    } while ( ! message.equals("Login OK\n") );
        
    } // login
    
    
    private void mainMenu() throws ClassNotFoundException, IOException {
        message = (String)in.readObject();
        System.out.print(message);
        
        message = scanner.nextLine();
        sendMessage(message);
    }

		
//	Entry point
	public static void main(String args[])
	{
		new Client().init();
	}
	
} // class Client