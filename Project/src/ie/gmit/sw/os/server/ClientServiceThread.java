package ie.gmit.sw.os.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;




//class ClientServiceThread extends Thread {
public class ClientServiceThread implements Runnable {
//	Fields
    private Socket clientSocket;
    private String message;
    private int clientID;
    private ObjectOutputStream out;
    private ObjectInputStream in;
	  
	  
	  
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
    
    
    
    
//	Methods
	public void sendMessage(String msg) {
    	try{
    		out.writeObject(msg);
    		out.flush();
    		System.out.println("server> " + msg);
    	}
    	catch(IOException ioException){
    		ioException.printStackTrace();
    	}
			
	} // sendMessage
	
	
	public void initClientServiceThread() throws IOException {
	    out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(clientSocket.getInputStream());
        
	} // initClientServiceThread
	
	
	
	
//	Abstract methods implementation
	public void run() {
	    System.out.println("Accepted client-" + clientID + ": Address - "
	        + clientSocket.getInetAddress().getHostName()  + ", Client port - "
            + clientSocket.getPort());
	    try 
	    {
			sendMessage("Connection successful"); // optional
			do{
				try
				{
				    
				    
				    
				    
				    // TODO Change 'message' from 'String' to 'StringBuilder'
				    message = (String)in.readObject();
					System.out.println("server> client-" + clientID + " sent:  " + message);
					System.out.println("server> " + Thread.currentThread().getName() + " sends: " + message);
					sendMessage(message);
					
					
					
					
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				} // try - catch in.readObject()
				
	    	} while ( ! message.equalsIgnoreCase("bye") );
	      
			System.out.println("Ending client-" + clientID + ": Address - "
			        + clientSocket.getInetAddress().getHostName() + ", Client port - "
			        + clientSocket.getPort());
	    } catch (IOException e) {
	        // I/O error
	        e.printStackTrace();
	    } // try - catch in.readObject()
	    finally{
            // Closing connection
            try{
                in.close();
                out.close();
                clientSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            } // try - catch in.close() out.close() clientSocket.close()
        } // try - catch IOException - finally in.readObject()
	    
	} // run
	
} // class ClientServiceThread
