package ie.gmit.sw.os.journal.model;

import java.util.LinkedList;
import java.util.List;




public class Journal {
//  Fields
    private int id;
    private int userId;
    private List<Record> records;
    
    
    
    
//  Constructors
    public Journal() {
        
    }
    
    public Journal(int id, int userId) {
        this.id = id;
        this.userId = userId;
        records = new LinkedList<>();
    }
    
    
    
    
//  Accessors and mutators
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
    
    
//  Methods
    public boolean add(Record arg0) {
        return records.add(arg0);
    }

    public int size() {
        return records.size();
    }
    
    
} // class Journal
