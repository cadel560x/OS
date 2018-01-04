package ie.gmit.sw.os.journal.view;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import ie.gmit.sw.os.journal.controller.JournalController;
import ie.gmit.sw.os.journal.controller.UserController;
import ie.gmit.sw.os.journal.model.FitnessMode;
import ie.gmit.sw.os.journal.model.FitnessRecord;
import ie.gmit.sw.os.journal.model.MealRecord;
import ie.gmit.sw.os.journal.model.MealType;
import ie.gmit.sw.os.journal.model.Record;
import ie.gmit.sw.os.journal.model.RecordType;
import ie.gmit.sw.os.journal.model.User;




public class JournalView extends View {
//  Fields
    private int userId;
    
//  Data members
    private JournalController journalController;
    
    
    
    
//  Constructors
    public JournalView() {

    }
    
    public JournalView(int userId) {
        initJournalView(userId);
        
        this.mainMenu();
    }
    
    public JournalView(Scanner scanner, PrintStream out, int userId) {
        super(scanner, out);
        
        initJournalView(userId);
        
        this.mainMenu();
    }
    
    
    
    
//  Accessors and mutators
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public JournalController getJournalController() {
        return journalController;
    }

    public void setJournalController(JournalController journalController) {
        this.journalController = journalController;
    }
    
    
    
    
    //  Methods
    public void initJournalView(int userId) {
        this.userId = userId;
        
        journalController = new JournalController(userId);
        journalController.loadJournal();
        
    } // initJournalView
    
    
    public void mainMenu() {
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
            out.print(msg);
            
            option = MainMenuOption.values()[Integer.parseInt(scanner.nextLine())];
            out.println();
            
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
                    out.println("Invalid option");
            
            } // switch
            
        } while ( option != MainMenuOption.Exit );
        
    } // mainMenu
    

    public void updateProfile() {
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
            out.print(msg);
            
            option = scanner.nextLine();
            
            switch ( option ) {
                case "1":
                    out.print("\nNew name: ");
                    userStub.setName(scanner.nextLine());
                    out.println("");
                    break;
                    
                case "2":
                    out.print("\nNew address: ");
                    userStub.setAddress(scanner.nextLine());
                    out.println("");
                    break;
                    
                case "3":
                    out.print("\nNew PPS number: ");
                    userStub.setPpsNumber(scanner.nextLine());
                    out.println("");
                    break;
                    
                case "4":
                    out.print("\nNew age: ");
                    userStub.setAge(Integer.parseInt(scanner.nextLine()));
                    out.println("");
                    break;
                    
                case "5":
                    out.print("\nNew weight: ");
                    userStub.setWeight(Double.parseDouble(scanner.nextLine()));
                    out.println("");
                    break;
                    
                case "6":
                    out.print("\nNew height: ");
                    userStub.setHeight(Double.parseDouble(scanner.nextLine()));
                    out.println("");
                    break;
                    
                case "0":
                    // Do nothing
                    break;
                
                default:
                    out.println("Invalid option");
            
            } // switch
        } while ( ! option.equals("0") );
        
        out.println(userStub);
        
        userController.updateUser(userId, userStub);
        
    } // updateProfile
    
    
    public void createRecord() {
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
        out.print(msg);
        
        option = RecordType.values()[Integer.parseInt(scanner.nextLine())];
        // function RecordType createRecordView()
        out.println();
        
        switch ( option ) {
            case FitnessRecord:
                record = createFitnessRecord();
                out.print("");
                break;
                
            case MealRecord:
                record = createMealRecord();
                break;
                
            default:
                out.println("Invalid option");
            
        } // switch
        
        record.setId(journalController.getJournal().size() + 1);
        journalController.add(record);
        journalController.saveJournal();

    } // createRecord
    
    
    public FitnessRecord createFitnessRecord() {
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
        
        out.print(msg);
        fitnessRecord.setMode(FitnessMode.values()[Integer.parseInt(scanner.nextLine())]);
        
        msg.setLength(0);
        
        msg.append('\n');
        msg.append("Enter duration (minutes): ");
        
        out.print(msg);
        fitnessRecord.setDuration(Integer.parseInt(scanner.nextLine()));
        
        return fitnessRecord;
        
    } // createFitnessRecord
    
    
    public MealRecord createMealRecord() {
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
        
        out.print(msg);
        mealRecord.setMealType(MealType.values()[Integer.parseInt(scanner.nextLine().trim())]);
        
        msg.setLength(0);
        
        msg.append('\n');
        msg.append("Enter description: ");
        
        out.print(msg);
        mealRecord.setDescription((scanner.nextLine()));
        
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
                
                out.println(msg);
                i++;
                if ( i > 9 ) {
                    break;
                }
                
            } // for
            
        }
        else {
            out.println("No records found");
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
                
                out.println(msg);
                i++;
                if ( i > 9 ) {
                    break;
                }
                
            } // for
            
        }
        else {
            out.println("No records found");
        } // if - else
        
    } // viewLastFitnessRecords
    
    
    public void deleteRecord() {
        StringBuilder msg = new StringBuilder();
        int recordId;
        
        msg.append(new Heading("Delete Record", HeadingType.SUBTITLE.getUnderline()));
        msg.append('\n');
        
        msg.append("Input record Id: ");
        out.print(msg);
        
        recordId = Integer.parseInt(scanner.nextLine());
        journalController.removeRecord(recordId);
        
    } // deleteRecord
    
} // class JournalView
