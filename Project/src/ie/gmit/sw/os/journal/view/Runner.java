package ie.gmit.sw.os.journal.view;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userId;
        
        userId = new LoginView(scanner, System.out).login();
        new JournalView(scanner, System.out, userId);
    }
}
