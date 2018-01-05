package ie.gmit.sw.os.server;

import java.util.Scanner;




public class Server {
//  Constants
    private static final int SERVER_PORT = 8080;
    private static final int THREAD_NUMBER = 10;
    
//  Fields
//  TODO Make 'portNumber' and 'threadNumber' 16 bit word
    private int serverPort;
    private int threadNumber;
    
    
    
    
//  Constructors
    public Server() {
        this.serverPort = SERVER_PORT;
        this.threadNumber = THREAD_NUMBER;
        
        initServerThread();
        
    }

    public Server(int serverPort, int threadNumber) {
        this.serverPort = serverPort;
        this.threadNumber = threadNumber;
        
        initServerThread();
        
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

    
    
    
//  Methods
    public Server start(int serverPort, int threadNumber) {
     // TODO Make the server port number set by command args or Java directives/declarations
        Scanner scanner = new Scanner(System.in);
        String portNumberString;
        String threadNumberString;
    
        
        System.out.print("Enter server port number: ");
        portNumberString = scanner.nextLine();
        
        System.out.print("Enter number of threads: ");
        threadNumberString = scanner.nextLine();
        
        if ( portNumberString.equals("") ) {
            this.serverPort = serverPort;
        }
        else {
            setServerPort(Integer.parseInt(portNumberString));
        }
        
        if ( threadNumberString.equals("") ) {
            this.threadNumber = threadNumber;
        }
        else {
            setThreadNumber(Integer.parseInt(threadNumberString));
        }
        
        scanner.close();
        
        return new Server(this.serverPort, this.threadNumber);
        
    } // start
    
    
    public void initServerThread() {
        // Give the 'listener' instance its own thread
        Thread listener = new Thread(new Listener(serverPort, threadNumber), "Server Listener");
        listener.setPriority(Thread.MAX_PRIORITY);
        listener.start();
        
    } // initServerThread()
    
    
    
    
//  Entry point
    public static void main(String[] args) {
//        new Server();
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the port number to listen on: ");
        int portNumber = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the number of threads: ");
        int threadNumber = Integer.parseInt(scanner.nextLine());
        
        scanner.close();
        
        new Server(portNumber,threadNumber);
        
    } // main
    
} // class Server