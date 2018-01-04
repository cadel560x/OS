package ie.gmit.sw.os.journal.view;

public class Heading {
//  Fields
    private String text;
    private char underline;
    
    
    
    
//  Constructors
    public Heading(String text, char underline) {
        super();
        this.text = text;
        this.underline = underline;
    }
    
    
    
    
//  Accessors and mutators
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public char getUnderline() {
        return underline;
    }
    public void setUnderline(char underline) {
        this.underline = underline;
    }
    
    
    
    
//  Methods
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        
        if ( underline == HeadingType.TITLE.getUnderline() ) {
            stringBuilder.append(text.toUpperCase() + '\n');
        } else {
            stringBuilder.append(text + '\n' );
        }
        
        for (char c : text.toCharArray()) {
            stringBuilder.append(underline);
        }
        stringBuilder.append("\n");
        
        return stringBuilder.toString();
        
    } // toString
   
} // class Heading
