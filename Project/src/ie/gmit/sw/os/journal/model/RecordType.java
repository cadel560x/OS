package ie.gmit.sw.os.journal.model;

public enum RecordType {
//    FitnessRecord,
//    MealRecord;
    Record("Generic Record"),
    
    FitnessRecord("Fitness Record"),
    MealRecord("Meal Record");
    
    private String name;
    
    RecordType(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
} // enum RecordType
