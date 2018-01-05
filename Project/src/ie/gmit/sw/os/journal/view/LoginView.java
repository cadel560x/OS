package ie.gmit.sw.os.journal.view;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import ie.gmit.sw.os.journal.controller.UserController;
import ie.gmit.sw.os.journal.model.User;




public class LoginView extends View {
//  Data members
    private UserController userController = new UserController();
    
    
    
    
//  Contructors
    public LoginView() {
        userController.loadUsers();
        
        out.println(new Heading("WELCOME TO JAVIER'S FITNESS & FOOD JOURNAL", HeadingType.TITLE.getUnderline()));
//        this.login();
    }
    
    public LoginView(Scanner scanner, PrintStream out) {
        super(scanner, out);
        userController.loadUsers();
        
        out.println(new Heading("WELCOME TO JAVIER'S FITNESS & FOOD JOURNAL", HeadingType.TITLE.getUnderline()));
//        this.login();
    }
     
    public LoginView(Scanner scanner, PrintWriter out) {
        super(scanner, out);
        userController.loadUsers();
        
        out.println(new Heading("WELCOME TO JAVIER'S FITNESS & FOOD JOURNAL", HeadingType.TITLE.getUnderline()));
//      this.login();
    }

//  Methods
    public int login() {
        String userName, password;
        int loginCounter;
        User user;
        
        loginCounter = 0;
        
        do {
            out.print("\nLogin: ");
//            out.flush();
            userName = scanner.nextLine();
            
            out.print("Password: ");
            password = scanner.nextLine();
            
            user = userController.getUser(userName);
            
            if ( loginCounter == 2 ) {
                loginCounter = 0;
                
                out.println("Too many login failures. Wait 15 seconds.");
                try {
                    Thread.sleep(15000);
                    continue;
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // try - catch 
            } else if ( user == null ) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                out.println("\nIncorrect user or password");
                loginCounter++;
                continue;
            } // if - else if
        
        } while ( ! userController.authenticateUser(user, password) );
        
        return user.getId();
//            return 2;
        
    } // login
    
} // class LoginView
