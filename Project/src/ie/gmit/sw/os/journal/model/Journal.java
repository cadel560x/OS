package ie.gmit.sw.os.journal.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;




public class Journal implements Serializable {
//  Constants
    private static final long serialVersionUID = 2L;
    
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

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
    
    
    
    
    //  Methods
    public boolean add(Record arg0) {
        if (records == null) {
            records = new LinkedList<>();
        }
        
        return records.add(arg0);
    } // add

    public int size() {
        if (records == null) {
            return 0;
        }
        
        return records.size();
    } // size
    
    
} // class Journal
