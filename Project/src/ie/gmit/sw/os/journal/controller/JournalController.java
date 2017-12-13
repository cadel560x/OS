package ie.gmit.sw.os.journal.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import ie.gmit.sw.os.journal.dao.JournalDao;
import ie.gmit.sw.os.journal.model.FitnessMode;
import ie.gmit.sw.os.journal.model.FitnessRecord;
import ie.gmit.sw.os.journal.model.Journal;
import ie.gmit.sw.os.journal.model.MealRecord;
import ie.gmit.sw.os.journal.model.MealType;
import ie.gmit.sw.os.journal.model.Record;
import ie.gmit.sw.os.journal.model.RecordType;




public class JournalController {
//  Fields
    private Journal journal;
    private int userId;
    
//  Data members
//    private JournalDao journalDao;
    
    
    
    
//  Constructors
    public JournalController() {
        
    }
    
    public JournalController(int userId) {
        this.userId = userId;
//        try {
//            journalDao = new JournalDao(this.userId);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
    }




//  Accessors and mutators
    public Journal getJournal() {
        return journal;
    }
    
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
    
    
    //  Methods
    public void loadJournal() {
        try {
            JournalDao journalDao = new JournalDao(this.userId);
            
            setJournal(journalDao.getJournal());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    } // loadJournal
    
    
    public void saveJournal() {
        try {
            JournalDao journalDao = new JournalDao(this.userId);
            
            journalDao.writeJournal(journal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    } // saveJournal
    
    
    public FitnessRecord createFitnessRecord(Scanner scanner, PrintStream out) {
        FitnessRecord fitnessRecord = new  FitnessRecord();
        
        out.println("New Fitness Record");
        out.println("------------------\n");
        
        out.println("Select record type:");
        
        for ( FitnessMode fitnessMode: FitnessMode.values() ) {
            if ( fitnessMode.ordinal() != 0 ) {
                out.println("[" + fitnessMode.ordinal() + "] " + fitnessMode);
            }
        }
        out.print("Option: ");
        fitnessRecord.setMode(FitnessMode.values()[Integer.parseInt(scanner.nextLine())]);
        
        out.println();
        
        out.print("Enter duration (minutes): ");
        fitnessRecord.setDuration(Integer.parseInt(scanner.nextLine()));
        
        return fitnessRecord;
        
    } // FitnessRecord createFitnessRecordView
    
    
    private MealRecord createMealRecord(Scanner scanner, PrintStream out) {
        MealRecord mealRecord = new  MealRecord();
        
        out.println("New Fitness Record");
        out.println("------------------\n");
        
        out.println("Select record type:");
        
        for ( MealType mealtype: MealType.values() ) {
            if ( mealtype.ordinal() != 0 ) {
                out.println("[" + mealtype.ordinal() + "] " + mealRecord);
            }
        }
        out.print("Option: ");
        mealRecord.setMealType(MealType.values()[Integer.parseInt(scanner.nextLine())]);
        
        out.println();
        
        out.print("Enter description:\n ");
        mealRecord.setDescription((scanner.nextLine()));
        
        return mealRecord;
        
    } // createMealRecordView()
    
    
    public void createRecord(Scanner scanner, PrintStream out) {
        Record record = null;
        RecordType option;
        
        out.println("Create Record");
        out.println("-------------\n");
        
        out.println("Select record type:");
        
        for ( RecordType recordType: RecordType.values() ) {
            if ( recordType.ordinal() != 0 ) {
                out.println("[" + recordType.ordinal() + "] " + recordType);
            }
        }
        out.print("Option: ");
        option = RecordType.values()[Integer.parseInt(scanner.nextLine())];
        // function RecordType createRecordView()
        out.println();
        
        switch ( option ) {
            case FitnessRecord:
                record = createFitnessRecord(scanner, out);
                out.print("");
                break;
                
            case MealRecord:
                record = createMealRecord(scanner, out);
                break;
                
            default:
                out.println("Invalid option");
            
        } // switch
        
        journal.add(record);

    } // createRecord
    

    public Record getRecord(int recordId) {
        
        return null;
        
    } // getUser
    
    
    public void removeRecord(int recordId) {
        
    } // removeUser
    
    
    public void updateRecord(int recordId) {
        
    } // updateUser
    
} // class JournalController
