package ie.gmit.sw.os.journal.view;

public enum MainMenuOption {
    Exit("Exit"),
    UpdateProfile("Update user profile"),
    CreateRecord("Create record"),
    ViewLastRecords("View last 10 records"),
    ViewLastFitnessRecords("View last 10 fitness records"),
    DeleteRecord("Delete record");
    
    private String name;
    
    MainMenuOption(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
} // enum MainMenuOption
