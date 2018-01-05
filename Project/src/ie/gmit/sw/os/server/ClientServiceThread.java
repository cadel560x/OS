package ie.gmit.sw.os.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import ie.gmit.sw.os.journal.controller.JournalController;
import ie.gmit.sw.os.journal.controller.UserController;
import ie.gmit.sw.os.journal.model.FitnessMode;
import ie.gmit.sw.os.journal.model.FitnessRecord;
import ie.gmit.sw.os.journal.model.MealRecord;
import ie.gmit.sw.os.journal.model.MealType;
import ie.gmit.sw.os.journal.model.Record;
import ie.gmit.sw.os.journal.model.RecordType;
import ie.gmit.sw.os.journal.model.User;
import ie.gmit.sw.os.journal.view.Heading;
import ie.gmit.sw.os.journal.view.HeadingType;
import ie.gmit.sw.os.journal.view.MainMenuOption;




//class ClientServiceThread extends Thread {
public class ClientServiceThread implements Runnable {
//	Fields
    private Socket clientSocket;
    private String message;
    private int clientID;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private UserController userController = new UserController();
    private JournalController journalController;
    private int userId;
	  
	  
	  
//	Constructors
    public ClientServiceThread() throws IOException {
        this.clientSocket = new Socket();
        this.clientID = -1;
        
        initClientServiceThread();
        
    }
    
    public ClientServiceThread(Socket clientSocket, int clientID) throws IOException {
	    this.clientSocket = clientSocket;
	    this.clientID = clientID;
	    
	    userController.loadUsers();
	    
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

    public void setOutStream(ObjectOutputStream outStream) {
        this.out = outStream;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    
    
    
//	Methods
	public void sendMessage(Object msg) {
        	try{
        		out.writeObject(msg);
        		out.flush();
        	}
        	catch(IOException ioException){
        		ioException.printStackTrace();
        	} // try - catch
			
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
			do{  
			    sendMessage(new Heading("WELCOME TO JAVIER'S FITNESS & FOOD JOURNAL", HeadingType.TITLE.getUnderline()).toString());
			    
			    this.userId = this.login();
			    
			    this.journalController = new JournalController(userId);
			    journalController.loadJournal();
			    
			    this.mainMenu();
                
                message = (String)in.readObject();
                System.out.println(message);
                if(message.equalsIgnoreCase("ADD"))
                {
                    System.out.println("In the add sequence");
                    sendMessage("Please enter num1");
                    message = (String)in.readObject();
                    int num1 = Integer.parseInt(message);
                    
                    sendMessage("Please enter num2");
                    message = (String)in.readObject();
                    int num2 = Integer.parseInt(message);
                    
                    sendMessage(""+(num1+num2));
                }
                else if(message.equalsIgnoreCase("SQUARE"))
                {
                    
                }
                else
                {
                    sendMessage(message);
                }
				
	    	} while ( ! message.equalsIgnoreCase("bye") );
	      
	        
			System.out.println("Ending client-" + clientID + ": Address - "
			        + clientSocket.getInetAddress().getHostName() + ", Client port - "
			        + clientSocket.getPort());
	    } catch (IOException e) {
	        // I/O error
	        e.printStackTrace();
	    } // try - catch in.readObject()
	    catch(ClassNotFoundException classnot){
            System.err.println("Data received in unknown format");
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
	
	
	public int login() throws ClassNotFoundException, IOException {
//	    UserController userController = new UserController();
        String userName, password;
        User user;
        
//        userController.loadUsers();
        
        sendMessage("\nLogin: ");
        userName = (String)in.readObject();
        
        user = userController.getUser(userName);
        
        sendMessage("Password: ");
        password = (String)in.readObject();
        
        while ( ! userController.authenticateUser(user, password) ) {
            sendMessage("\nIncorrect user or password\n");
            
            
            sendMessage("\nLogin: ");
            userName = (String)in.readObject();
            
            user = userController.getUser(userName);
            
            sendMessage("Password: ");
            password = (String)in.readObject();
            
        } // while
        
        sendMessage("Login OK\n");
        
        return user.getId();
        
	} // login
	
	
	public void mainMenu() throws NumberFormatException, ClassNotFoundException, IOException {
        StringBuilder msg = new StringBuilder();
        MainMenuOption option;
        
        msg.append('\n');
        msg.append(new Heading("Main Menu", HeadingType.TITLE.getUnderline()));
        msg.append('\n');
        
        do {
            msg.append("Select option:\n");
            
            for ( MainMenuOption mainMenuOption: MainMenuOption.values() ) {
                if ( mainMenuOption.ordinal() != 0 ) {
                    msg.append("[" + mainMenuOption.ordinal() + "] " + mainMenuOption + '\n');
                }
            }
            msg.append("[" + MainMenuOption.Exit.ordinal() + "] " + MainMenuOption.Exit + '\n');
            msg.append("\nOption: ");
            sendMessage(msg.toString());
            
            String optionString = (String)in.readObject();
            option = MainMenuOption.values()[Integer.parseInt(optionString)];
            sendMessage('\n');
            
            switch ( option ) {
                case UpdateProfile:
                    this.updateProfile();
                    break;
                    
                case CreateRecord:
                    this.createRecord();
                    break;
                    
                case ViewLastRecords:
                    this.viewLastRecords();
                    break;
                    
                case ViewLastFitnessRecords:
                    this.viewLastFitnessRecords();
                    break;
                    
                case DeleteRecord:
                    this.deleteRecord();
                    break;
                    
                case Exit:
                    // Do nothing
                    break;
                    
                default:
                    sendMessage("Invalid option\n");
            
            } // switch
            
        } while ( option != MainMenuOption.Exit );
        
    } // mainMenu
	
	
    public void updateProfile() throws ClassNotFoundException, IOException {
        UserController userController = new UserController();
        StringBuilder msg = new StringBuilder();
        String option;
        
        userController.loadUsers();
        User userStub = userController.getUser(userId);
        
        msg.append(new Heading("Update User Profile", HeadingType.SUBTITLE.getUnderline()));
        msg.append('\n');
        
        msg.append(userStub.toString());
        msg.append('\n');
        
        do {
            msg.append("Select option:\n");
            msg.append("[1] Update name\n");
            msg.append("[2] Update address\n");
            msg.append("[3] Update PPS number\n");
            msg.append("[4] Update age\n");
            msg.append("[5] Update weight\n");
            msg.append("[6] Update height\n");
            msg.append("[0] Exit\n");
            
            msg.append("\nOption: ");
            sendMessage(msg.toString());
            
            option = (String)in.readObject();
            
            switch ( option ) {
                case "1":
//                    sendMessage(msg.toString());
                    sendMessage("\nNew name: ");
                    userStub.setName((String)in.readObject());
                    sendMessage('\n');
                    break;
                    
                case "2":
                    sendMessage("\nNew address: ");
                    userStub.setAddress((String)in.readObject());
                    sendMessage('\n');
                    break;
                    
                case "3":
                    sendMessage("\nNew PPS number: ");
                    userStub.setPpsNumber((String)in.readObject());
                    sendMessage('\n');
                    break;
                    
                case "4":
                    sendMessage("\nNew age: ");
                    userStub.setAge(Integer.parseInt((String)in.readObject()));
                    sendMessage('\n');
                    break;
                    
                case "5":
                    sendMessage("\nNew weight: ");
                    userStub.setWeight(Double.parseDouble((String)in.readObject()));
                    sendMessage('\n');
                    break;
                    
                case "6":
                    sendMessage("\nNew height: ");
                    userStub.setHeight(Double.parseDouble((String)in.readObject()));
                    sendMessage('\n');
                    break;
                    
                case "0":
                    // Do nothing
                    break;
                
                default:
                    sendMessage("Invalid option\n");
            
            } // switch
        } while ( ! option.equals("0") );
        
        sendMessage(userStub.toString() + '\n');
        
        userController.updateUser(userId, userStub);
        
    } // updateProfile
    
    
    public void createRecord() throws NumberFormatException, ClassNotFoundException, IOException {
        Record record = null;
        RecordType option;
        StringBuilder msg = new StringBuilder();
        
        msg.append(new Heading("Create Record", HeadingType.SUBTITLE.getUnderline()));
        msg.append('\n');
        
        msg.append("Select option:\n");
        
        for ( RecordType recordType: RecordType.values() ) {
            if ( recordType.ordinal() != 0 ) {
                msg.append("[" + recordType.ordinal() + "] " + recordType + '\n');
            }
        }
        msg.append("\nOption: ");
        sendMessage(msg.toString());
        
        option = RecordType.values()[Integer.parseInt((String)in.readObject())];
        // function RecordType createRecordView()
        sendMessage('\n');
        
        switch ( option ) {
            case FitnessRecord:
                record = createFitnessRecord();
                break;
                
            case MealRecord:
                record = createMealRecord();
                break;
                
            default:
                sendMessage("Invalid option\n");
            
        } // switch
        
        record.setId(journalController.getJournal().size() + 1);
        journalController.add(record);
        journalController.saveJournal();

    } // createRecord
    
    
    public FitnessRecord createFitnessRecord() throws NumberFormatException, ClassNotFoundException, IOException {
        FitnessRecord fitnessRecord = new  FitnessRecord();
        StringBuilder msg = new StringBuilder();
        
        msg.append(new Heading("New Fitness Record", HeadingType.SUBTITLE.getUnderline()));
        msg.append('\n');
        
        msg.append("Select option:\n");
        
        for ( FitnessMode fitnessMode: FitnessMode.values() ) {
            if ( fitnessMode.ordinal() != 0 ) {
                msg.append("[" + fitnessMode.ordinal() + "] " + fitnessMode + '\n');
            }
        }
        msg.append("\nOption: ");
        
        sendMessage(msg.toString());
        fitnessRecord.setMode(FitnessMode.values()[Integer.parseInt((String)in.readObject())]);
        
        msg.setLength(0);
        
        msg.append('\n');
        msg.append("Enter duration (minutes): ");
        
        sendMessage(msg.toString());
        fitnessRecord.setDuration(Integer.parseInt((String)in.readObject()));
        
        return fitnessRecord;
        
    } // createFitnessRecord
    
    
    public MealRecord createMealRecord() throws NumberFormatException, ClassNotFoundException, IOException {
        MealRecord mealRecord = new  MealRecord();
        StringBuilder msg = new StringBuilder();
        
        msg.append(new Heading("New Meal Record", HeadingType.SUBTITLE.getUnderline()));
        msg.append('\n');
        
        msg.append("Select option:\n");
        
        for ( MealType mealtype: MealType.values() ) {
            if ( mealtype.ordinal() != 0 ) {
                msg.append("[" + mealtype.ordinal() + "] " + mealtype + '\n');
            }
        }
        msg.append("\nOption: ");
        
        sendMessage(msg.toString());
        mealRecord.setMealType(MealType.values()[Integer.parseInt(((String) in.readObject()).trim())]);
        
        msg.setLength(0);
        
        msg.append('\n');
        msg.append("Enter description: ");
        
        sendMessage(msg.toString());
        mealRecord.setDescription(((String)in.readObject()));
        
        return mealRecord;
        
    } // createMealRecord
    
    
    public void viewLastRecords() {
        StringBuilder msg = new StringBuilder();
        List<Record> records = journalController.getJournal().getRecords();
        FitnessRecord fitnessRecord;
        MealRecord mealRecord;
        int i = 0;
        
        msg.append(new Heading("Last 10 Records", HeadingType.SUBTITLE.getUnderline()));
        msg.append('\n');
        
        if (records != null ) {
            for (Record record: records) {
                record = records.get(i);
                if (record instanceof FitnessRecord) {
                    fitnessRecord = (FitnessRecord)record;
                    msg.append(fitnessRecord.toString() + '\n');
                }
                else if (record instanceof MealRecord) {
                    mealRecord = (MealRecord)record;
                    msg.append(mealRecord.toString() + '\n');
                }
                else {
                    msg.append(record.toString() + '\n');
                }
                
                sendMessage(msg.toString());
                i++;
                if ( i > 9 ) {
                    break;
                }
                
            } // for
            
        }
        else {
            sendMessage("No records found\n");
        } // if - else
        
    } // viewLastRecords
    
    
    public void viewLastFitnessRecords() {
        StringBuilder msg = new StringBuilder();
        List<Record> records = journalController.getJournal().getRecords();
        FitnessRecord fitnessRecord;
        int i = 0;
        
        msg.append(new Heading("Last 10 Fitness Records", HeadingType.SUBTITLE.getUnderline()));
        msg.append('\n');
        
        if (records != null ) {
            for (Record record: records) {
                record = records.get(i);
                if (record instanceof FitnessRecord) {
                    fitnessRecord = (FitnessRecord)record;
                    msg.append(fitnessRecord.toString() + '\n');
                }
                
                sendMessage(msg.toString());
                i++;
                if ( i > 9 ) {
                    break;
                }
                
            } // for
            
        }
        else {
            sendMessage("No records found\n");
        } // if - else
        
    } // viewLastFitnessRecords
    
    
    public void deleteRecord() throws NumberFormatException, ClassNotFoundException, IOException {
        StringBuilder msg = new StringBuilder();
        int recordId;
        
        msg.append(new Heading("Delete Record", HeadingType.SUBTITLE.getUnderline()));
        msg.append('\n');
        
        msg.append("Input record Id: ");
        sendMessage(msg.toString());
        
        recordId = Integer.parseInt((String)in.readObject());
        journalController.removeRecord(recordId);
        
    } // deleteRecord
	
} // class ClientServiceThread
