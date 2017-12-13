package ie.gmit.sw.os.journal.controller;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import ie.gmit.sw.os.journal.dao.UserDao;
import ie.gmit.sw.os.journal.model.User;




public class UserController {
//  Fields
    private List<User> users;
    
//  Data members
//    private UserDao userDao;
    
    
    
    
//  Constructors
    public UserController () {
//        try {
//            userDao = new UserDao();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
    }
    
    
    
    
//  Accessors and mutators
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
    
    
//  Methods
    public void loadUsers() {
        try {
            UserDao userDao = new UserDao();
            
            setUsers(userDao.getUsers());
        } catch (EOFException e) {
            // Empty users file
            // Instantiate the 'users' list
            users = new LinkedList<>();
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block, create a catch block for EOFException, the file is just created and empty
            e.printStackTrace();
        }
        
    } // loadUsers
    
    
    public void saveUsers() {
        try {
            UserDao userDao = new UserDao();
            
            userDao.writeUsers(users);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    } // saveUsers
    
    
    public void createUser(Scanner scanner, PrintStream out) {
//        Scanner scanner = new Scanner(System.in); // Replace 'System.in' for other InputStream
        User user = new User();
        
        user.setId(this.users.size() + 1);
        
        out.print("Username: ");
        user.setUserName(scanner.nextLine());
        out.print("Password: ");
        user.setPassword(scanner.nextLine());
        out.print("Name: ");
        user.setName(scanner.nextLine());
        out.print("Address: ");
        user.setAddress(scanner.nextLine());
        out.print("PPS number: ");
        user.setPpsNumber(scanner.nextLine());
        out.print("Age (years): ");
        user.setAge(Integer.parseInt(scanner.nextLine()));
        out.print("Weight (kg): ");
        user.setWeight(Double.parseDouble(scanner.nextLine()));
        out.print("Height (meters): ");
        user.setHeight(Double.parseDouble(scanner.nextLine()));        
        
//        scanner.close();
        
        this.users.add(user);
        
    } // createUserView()
    
    
    public User getUser(int userId) {
        
        return null;
        
    } // getUser
    
    
    public User getUser(String username) {
        
        return null;
        
    } // getUser
    
    
    public void removeUser(int userId) {
        
    } // removeUser
    
    
    public void updateUser(User user) {
        
    } // updateUser
    
    
    public boolean authenticateUser(User user, String password) {
        if ( password.equals(user.getPassword()) ) {
            return true;
        }
        
        return false;
        
    } // authenticateUser
    
} // class UserController
