package ie.gmit.sw.os.journal.model;

public enum FitnessMode {
    ZERO(""),
    WALKING("Walking"),
    RUNNING("Running"),
    CYCLING("Cycling"),
    SWIMMING("Swimming");
    
    private String sport;

    private FitnessMode(String sport) {
        this.sport = sport;
    }
    
    public String toString() {
        return sport;
    }
    
} // enum FitnessMode
