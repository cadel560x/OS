package ie.gmit.sw.os.server;

import java.io.IOException;
import java.net.ServerSocket;




public class Server {
//  Constants
    private static final int SERVER_PORT = 8080;
    
//  Fields
    private ServerSocket ss;
    
    
    
    
//  Constructors
    public Server() {
        try {
            
            ss = new ServerSocket(SERVER_PORT);
            // Give the 'server' instance its own thread
            Thread server = new Thread(new Listener(), "Server Listener");
            server.setPriority(Thread.MAX_PRIORITY);
            server.start();
            
            System.out.println("Server started and listening on port " + SERVER_PORT);
            
        } catch (IOException e) {
            e.printStackTrace();
        } // try - catch
    }
    
    
    
    
//  Entry point
    public static void main(String[] args) {
        new Server(); 
    }
    
} // class Server