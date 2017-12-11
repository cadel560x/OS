package ie.gmit.sw.os.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;




public class Listener implements Runnable {
//  Fields
    private volatile boolean keepRunning = true;
    private ServerSocket m_ServerSocket;
    private Socket clientSocket;
    
    
    
    
//  Constructors
    public Listener() {
        try {
            m_ServerSocket = new ServerSocket(8080,10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
//  Abstract methods implementation
    @Override
    public void run() {
        int id = 0;
        try {
            while (keepRunning) {
                clientSocket = m_ServerSocket.accept();
                
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
                cliThread.start();
            } // while
        } catch (IOException e) {
            e.printStackTrace();
        } // try - catch
    
    } // run

} // class Listener
