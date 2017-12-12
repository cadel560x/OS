package ie.gmit.sw.os.journal.controller;

import java.io.IOException;

import ie.gmit.sw.os.journal.dao.JournalDao;
import ie.gmit.sw.os.journal.model.Journal;




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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    } // loadJournal
    
} // class JournalController
