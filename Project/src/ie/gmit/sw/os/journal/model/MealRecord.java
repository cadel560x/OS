package ie.gmit.sw.os.journal.model;




public class MealRecord extends Record {
//  Constants
    private static final int MAX_LENGTH = 100;
    
//  Fields
    private MealType mealType;
    private String description;
    
    
    
    
//  Constructors
    public MealRecord() {
    }
    
    public MealRecord(int id, MealType mealType, String description) {
        super(id);
        this.mealType = mealType;
        this.description = description;
    }
    
    
    
    
//  Accessors and mutators
    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.substring(0, MAX_LENGTH); // Requirement 4.b
    }
    
    
    
    
//  Methods
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MealRecord [");
        builder.append(super.toString());        
        builder.append(", mealType=");
        builder.append(mealType);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        
        return builder.toString();
    }
    
} // class MealRecord
