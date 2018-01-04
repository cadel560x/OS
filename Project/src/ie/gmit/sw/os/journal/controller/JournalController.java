package ie.gmit.sw.os.journal.controller;

import java.io.IOException;

import ie.gmit.sw.os.journal.dao.JournalDao;
import ie.gmit.sw.os.journal.model.Journal;
import ie.gmit.sw.os.journal.model.Record;
import ie.gmit.sw.os.journal.model.User;




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
            this.journal.setUserId(userId);
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
    
    
    public void add(Record record) {
        journal.add(record);

    } // add
    

    public Record getRecord(int recordId) {
        for (Record record: journal.getRecords()) {
            if (recordId == record.getId()) {
                return record;
            }
        }
        return null;
        
    } // getRecord
    
    
    public void removeRecord(int recordId) {
        Record record = this.getRecord(recordId);
        
        if (record != null ) {
            this.journal.getRecords().remove(record);
            this.saveJournal();
        }
        
    } // removeRecord
    
    
    public void updateRecord(int recordId) {
        
    } // updateRecord
    
} // class JournalController
