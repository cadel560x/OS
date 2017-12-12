package ie.gmit.sw.os.journal.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import ie.gmit.sw.os.journal.model.Journal;
import ie.gmit.sw.os.journal.model.User;




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
        
        file = new File(JOURNAL_FILE + this.userId);
        file.createNewFile();
    }




 //  Methods
    public Journal getJournal() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream  = new ObjectInputStream(new FileInputStream(file));
        Journal journal = new Journal();
        Object object;
        
        try {
            object = inputStream.readObject();
            if ( object instanceof Journal ) {
                journal = (Journal)object;
            }
            
            if (journal.getUserId() != userId) {
                throws Exception e;
            }
        } catch (EOFException e) {
            // End of file reached.
            return users;
            
        } finally {
            inputStream.close();
        }
        
        return users; // This is unlikely to get reached, but anyway...
        
    } // getUsers
    
    
    public void writeUsers(List<User> users) throws FileNotFoundException, IOException {
        // 'false' in 'FileOutputStream' overwrites the output file
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file, false));
        
        for ( User user: users ) {
            outputStream.writeObject(user);
        }
        
        outputStream.close();
        
    } // writeUsers


    public void createUsersFile() {
        
    } // createUsersFile
    
} // class UserDao
