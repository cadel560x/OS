package ie.gmit.sw.os.journal.view;

import java.io.PrintStream;
import java.util.Scanner;




public abstract class View {
//  Data members
    protected Scanner scanner;
    protected PrintStream out;
    
    
    
    
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

    public PrintStream getOut() {
        return out;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }
    
    
    
    
//  Methods
//    public void printTitle(String title) {
//        StringBuilder underline = new StringBuilder();
//        
//        out.println(title);
//        
//        for ( int i = 0; i < title.length(); i++ ) {
//            underline.append('=');
//        }
//        
//        out.println(underline);
//        
//    } // printTitle
    
} // abstract class View
