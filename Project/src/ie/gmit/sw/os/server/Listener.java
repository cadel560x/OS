package ie.gmit.sw.os.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;




public class Listener implements Runnable {
//  Constants
    private final int SERVER_PORT = 8080;
    private final int THREAD_NUMBER = 10;
    
//  Volatile flags
    private volatile boolean keepRunning = true;
    
//  Fields
//  TODO Make 'portNumber' and 'threadNumber' 16 bit word
    private int serverPort;
    private int threadNumber;
    private ServerSocket serverSocket;
    
    
    
    
//  Constructors
    public Listener() {
        initServerSocket();
        
    }
 
    public Listener(int serverPort, int threadNumber) {
        this.serverPort = serverPort;
        this.threadNumber = threadNumber;
        
        initServerSocket();
        
    }
    
    
    
    
//  Accessors and mutators
    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public boolean isKeepRunning() {
        return keepRunning;
    }

    public void setKeepRunning(boolean keepRunning) {
        this.keepRunning = keepRunning;
    }
    
    
    
    
    //  Methods
    public void initServerSocket() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT, THREAD_NUMBER);
            System.out.println("Server started and listening on port " + SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    } // initServerSocket
    
    
    
    
//  Abstract methods implementation
    @Override
    public void run() {
        int id = 0;
        // TODO Stop the server from command line like: 'server-ctl stop' style
        while (keepRunning) {
            // Try - catch for 'serverSocket' problems
            try {
                Socket clientSocket = serverSocket.accept();
                
                // Try - catch for 'cliThread' problems
                try {
                    // 'cliThread' as a thread 
                    //  ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
                    // 'cliThread' as a worker with a job 'ClientServiceThread'
                    Thread cliThread = new Thread(new ClientServiceThread(clientSocket, ++id), "thread-" + id);
                    cliThread.start();
                } catch (IOException e) {
                    // TODO send to the server's log the reason why cannot create a new thread
                    e.printStackTrace();
                } // try - catch ClientServiceThread
                
            } catch (IOException e) {
                // TODO send the exception to the server's log
                e.printStackTrace();
                
                // If cannot open the 'serverSocket' don't run
                keepRunning = false;
            } // try - catch serverSocket.accept()
            
        } // while
    
    } // run

} // class Listener
