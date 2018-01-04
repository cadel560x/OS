package ie.gmit.sw.os.journal.view;

public enum HeadingType {
    TITLE('='),
    SUBTITLE('-');
    
    private char underline;
    
    HeadingType(char underline) {
        this.underline = underline;
    }

    public char getUnderline() {
        return underline;
    }
    
} // enum HeadingType
