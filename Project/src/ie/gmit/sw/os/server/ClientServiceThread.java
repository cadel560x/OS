package ie.gmit.sw.os.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import ie.gmit.sw.os.journal.view.JournalView;
import ie.gmit.sw.os.journal.view.LoginView;




//class ClientServiceThread extends Thread {
public class ClientServiceThread implements Runnable {
//	Fields
    private Socket clientSocket;
//    private String message;
    private int clientID;
    private ObjectOutputStream outStream;
    
//    private OutputStream outStream;
    
//    private ObjectInputStream in;
//    private PrintStream out;
    
    private PrintWriter out;
    private BufferedReader in;
    
	  
	  
	  
//	Constructors
    public ClientServiceThread() throws IOException {
        this.clientSocket = new Socket();
        this.clientID = -1;
        
        initClientServiceThread();
        
    }
    
    public ClientServiceThread(Socket clientSocket, int clientID) throws IOException {
	    this.clientSocket = clientSocket;
	    this.clientID = clientID;
	    
	    initClientServiceThread();
	    
	}
	  
	  
	  
	
//  Accessors and mutators
    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public ObjectOutputStream getOutStream() {
        return outStream;
    }

    public void setOutStream(ObjectOutputStream outStream) {
        this.outStream = outStream;
    }

//    public ObjectInputStream getIn() {
//        return in;
//    }
//
//    public void setIn(ObjectInputStream in) {
//        this.in = in;
//    }
//
//    public PrintStream getOut() {
//        return out;
//    }
//
//    public void setOut(PrintStream out) {
//        this.out = out;
//    }

    
    
    
//	Methods
	public void sendMessage(String msg) {
        	try{
        		outStream.writeObject(msg);
        		out.flush();
        		System.out.println("server> " + msg);
        	}
        	catch(IOException ioException){
        		ioException.printStackTrace();
        	}
			
	} // sendMessage
	
	
	public void initClientServiceThread() throws IOException {
	    outStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outStream.flush();
//        in = new ObjectInputStream(clientSocket.getInputStream());
        
//        BufferedReader in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
//        out = new PrintStream(clientSocket.getOutputStream());
//        out = new PrintStream(outStream, true);
        
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        
        out.flush();
        
	} // initClientServiceThread
	
	
	
	
//	Abstract methods implementation
	public void run() {
	    System.out.println("Accepted client-" + clientID + ": Address - "
	        + clientSocket.getInetAddress().getHostName()  + ", Client port - "
            + clientSocket.getPort());
//	    try 
//	    {
//			sendMessage("Connection successful"); // optional
//			do{
//				try
//				{
//				    // TODO Change 'message' from 'String' to 'StringBuilder'
//				    message = (String)in.readObject();
//					System.out.println("server> client-" + clientID + " sent:  " + message);
//					System.out.println("server> " + Thread.currentThread().getName() + " sends: " + message);
//					sendMessage(message);
//				}
//				catch(ClassNotFoundException classnot){
//					System.err.println("Data received in unknown format");
//				} // try - catch in.readObject()
//				
//	    	} while ( ! message.equalsIgnoreCase("bye") );
	      
	        Scanner scanner = new Scanner(in);
	        int userId;
	        
	        
	        userId = new LoginView(scanner, out).login();
//	        new JournalView(scanner, out, userId);
	        
			System.out.println("Ending client-" + clientID + ": Address - "
			        + clientSocket.getInetAddress().getHostName() + ", Client port - "
			        + clientSocket.getPort());
//	    } catch (IOException e) {
//	        // I/O error
//	        e.printStackTrace();
//	    } // try - catch in.readObject()
//	    finally{
//            // Closing connection
            try{
                in.close();
                out.close();
                clientSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            } // try - catch in.close() out.close() clientSocket.close()
//        } // try - catch IOException - finally in.readObject()
	    
	} // run
	
} // class ClientServiceThread
