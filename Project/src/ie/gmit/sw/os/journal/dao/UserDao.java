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

import ie.gmit.sw.os.journal.model.User;




public class UserDao {
//  Constants
    private static String USERS_FILE = "users.dat";
    
//  Data members
    private File file;
    
    
    
    
//  Constructors
    public UserDao() throws IOException {
        file = new File(USERS_FILE);
        file.createNewFile();
    }
    
    
    
    
//  Methods
    public List<User> getUsers() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream  = new ObjectInputStream(new FileInputStream(file));
        List<User> users = new LinkedList<>();
        Object object;
        
        try {
            do {
                object = inputStream .readObject();
                if ( object instanceof User ) {
                    users.add( (User)object );
                }
            } while ( object != null ); // This loop will be interrupted with an exception when the EOF is reached.
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
