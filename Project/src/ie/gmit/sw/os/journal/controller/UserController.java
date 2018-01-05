package ie.gmit.sw.os.journal.controller;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import ie.gmit.sw.os.journal.dao.UserDao;
import ie.gmit.sw.os.journal.model.User;




public class UserController {
//  Fields
    private List<User> users;
    
    
    
    
//  Constructors
    public UserController () {
        
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
            if (users.isEmpty()) {
                createDefaultUser();
            }
        } catch (EOFException e) {
            // Empty users file
            // Instantiate the 'users' list
            users = new LinkedList<>();
            createDefaultUser();
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block, create a catch block for EOFException, the file is just created and empty
            e.printStackTrace();
        } // try - catch - catch
        
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
    
    
    public void createDefaultUser() {
        User user = new User();
        
        user.setId(this.users.size() + 1);
        
        user.setUserName("demo");
        user.setPassword("test");
        user.setName("Demo account");
        user.setAddress("The outer space");
        user.setPpsNumber("99999CA");
        user.setAge(99);
        user.setWeight(999);
        user.setHeight(999);
        
        this.users.add(user);
        this.saveUsers();
        
    } // createDefaultUser
    
    
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
        this.saveUsers();
        
    } // createUser()
    
    
    public User getUser(int userId) {
        for (User user: users) {
            if (userId == user.getId()) {
                return user;
            }
        }
        
        return null;
        
    } // getUser
    
    
    public User getUser(String userName) {
//        Iterator<User> listIterator = users.iterator(); 
        
        for (User user: users) {
            if ( userName.equals(user.getUserName()) ) {
                return user;
            }
        }
        
        return null;
        
    } // getUser
    
    
    public void removeUser(int userId) {
        User user = this.getUser(userId);
        
        if (user != null ) {
            users.remove(user);
            this.saveUsers();
        }
        
    } // removeUser
    
    
    public void updateUser(int userId, User userStub) {
        User user = this.getUser(userId);
        
        if (user != null) {
            if (userStub.getName() != null) {
                user.setName(userStub.getName());
            }
            
            if (userStub.getAddress() != null) {
                user.setAddress(userStub.getAddress());
            }
            
            if (userStub.getPpsNumber() != null) {
                user.setPpsNumber(userStub.getPpsNumber());
            }
            
            if (userStub.getAge() != 0) {
                user.setAge(userStub.getAge());
            }
            
            if (userStub.getWeight() != 0 ) {
                user.setWeight(userStub.getWeight());
            }
            
            if (userStub.getHeight() != 0 ) {
                user.setHeight(userStub.getHeight());
            }
            
            this.saveUsers();
        }
        
    } // updateUser
    
    
    public boolean authenticateUser(User user, String password) {
        if ( user != null && password.equals(user.getPassword()) ) {
            return true;
        }
        
        return false;
        
    } // authenticateUser
    
} // class UserController
