package ie.gmit.sw.os.journal.model;




public class FitnessRecord extends Record {
//  Fields
    private FitnessMode mode;
    private int duration;
    
    
    
    
//  Constructors
    public FitnessRecord() {
        
    }

    public FitnessRecord(int id, FitnessMode mode, int duration) {
        super(id);
        this.mode = mode;
        this.duration = duration;
    }
    
    
    
    
//  Accessors and mutators
    public FitnessMode getMode() {
        return mode;
    }

    public void setMode(FitnessMode mode) {
        this.mode = mode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    
    
    
//  Methods
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FitnessRecord [");
        builder.append(super.toString());
        builder.append(", mode=");
        builder.append(mode);
        builder.append(", duration=");
        builder.append(duration);
        builder.append(", toString()=");
        builder.append("]");
        return builder.toString();
    }

} // class FitnessRecord
