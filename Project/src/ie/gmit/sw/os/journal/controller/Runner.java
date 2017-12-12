package ie.gmit.sw.os.journal.controller;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UserController userController = new UserController();
        Scanner scanner = new Scanner(System.in);
        String option = "";
        
        userController.loadUsers();
        
        while ( ! option.equalsIgnoreCase("no") ) {
            userController.createUser(scanner, System.out);
            System.out.println("Keep adding users? ");
            option = scanner.nextLine();
        }
        
        userController.saveUsers();
        
        scanner.close();
        
    }

}
