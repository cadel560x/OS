package ie.gmit.sw.os.journal.controller;

import java.util.Scanner;

import ie.gmit.sw.os.journal.view.JournalView;

public class Runner {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UserController userController = new UserController();
        JournalController journalController;
        Scanner scanner = new Scanner(System.in);
        String option = "";
        int userId;
        
        userController.loadUsers();
        
        
//        while ( ! option.equalsIgnoreCase("no") ) {
//            userController.createUser(scanner, System.out);
//            System.out.println("Keep adding users? ");
//            option = scanner.nextLine();
//        }
        
        userId = userController.getUsers().get(0).getId();
//        journalController = new JournalController(userId);
//        journalController.loadJournal();
//        journalController.createRecord(scanner, System.out);
        
        JournalView journalView = new JournalView(scanner, System.out, userId);
        
//        journalController.saveJournal();
        journalController = journalView.getJournalController();
        journalController.saveJournal();
        
        userController.saveUsers();
        
        scanner.close();
        
    }

}
