package ie.gmit.sw.os.journal.model;

import java.io.Serializable;

public abstract class Record implements Serializable {
//  Fields
    private static final long serialVersionUID = 2L;
    private int id;
    
    
    
    
//  Constructors
    public Record() {
        
    }
    
    public Record(int id) {
        this.id = id;
    }
    
    
    
    
//  Accessors and mutators
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
//  Methods
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Record id=");
        builder.append(id);
        return builder.toString();
    }

} // abstract class Record
