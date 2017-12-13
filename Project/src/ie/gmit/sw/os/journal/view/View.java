package ie.gmit.sw.os.journal.view;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Scanner;




public abstract class View {
//  Data members
    private Scanner scanner;
    private PrintStream out;
    
    
    
    
//  Constructors
    public View() {
        
    }
    
    public View(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }




//  Accessors and mutators
    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }
    
    
    
    
//  Methods
    public void printTitle(String title) {
        StringBuilder underline = new StringBuilder();
        
        out.println(title);
        
        for ( int i = 0; i < title.length(); i++ ) {
            underline.append('=');
        }
        
        out.println(underline);
        
    } // printTitle
    
} // abstract class View
