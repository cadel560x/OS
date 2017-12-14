package ie.gmit.sw.os.journal.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ie.gmit.sw.os.journal.model.Journal;




public class JournalDao {
//  Constants
    private static String JOURNAL_FILE = "journal-";
    
//  Data members
    private int userId;
    private File file;
    
    
    
    
//  Constructors
    public JournalDao() throws IOException {
        
    }

    public JournalDao(int userId) throws IOException {
        this.userId = userId;
        
        file = new File(JOURNAL_FILE + this.userId + ".dat");
        file.createNewFile();
    }




 //  Methods
    public Journal getJournal() throws IOException, ClassNotFoundException {
//        ObjectInputStream inputStream  = new ObjectInputStream(new FileInputStream(file));
        ObjectInputStream inputStream  = null;
        Journal journal = new Journal();
        Object object;
        
        try {
            inputStream  = new ObjectInputStream(new FileInputStream(file));
            
            object = inputStream.readObject();
            if ( object instanceof Journal ) {
                journal = (Journal)object;
            }
            
            if ( object != null && journal.getUserId() != userId) {
                throw new InvalidObjectException("Incorrect journal for user " + userId);
            }
            
        } catch (EOFException e) {
            // End of file reached.
            return journal;
            
        } finally {
            if ( inputStream != null ) {
                inputStream.close();
            }
        }
        
        return journal; // 'return' when file is empty with an empty instance of 'journal'
        
    } // getJournal
    
    
    public void writeJournal(Journal journal) throws FileNotFoundException, IOException {
        // 'false' in 'FileOutputStream' overwrites the output file
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file, false));
        	outputStream.writeObject(journal);
        
        outputStream.close();
        
    } // writeJournal
    
} // class UserDao
