package ie.gmit.sw.os.journal.view;

import java.io.PrintStream;
import java.util.Scanner;

import ie.gmit.sw.os.journal.controller.JournalController;
import ie.gmit.sw.os.journal.model.FitnessMode;
import ie.gmit.sw.os.journal.model.FitnessRecord;
import ie.gmit.sw.os.journal.model.MealRecord;
import ie.gmit.sw.os.journal.model.MealType;
import ie.gmit.sw.os.journal.model.Record;
import ie.gmit.sw.os.journal.model.RecordType;




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
    }
    
    public JournalView(Scanner scanner, PrintStream out, int userId) {
        super(scanner, out);
        
        initJournalView(userId);
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
    
    
    private MealRecord createMealRecord() {
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
        msg.append("Enter description:\n ");
        
        out.print(msg);
        mealRecord.setDescription((scanner.nextLine()));
        
        return mealRecord;
        
    } // createMealRecord
    
} // class JournalView
