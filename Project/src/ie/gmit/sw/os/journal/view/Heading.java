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
        StringBuilder builder = new StringBuilder();
        
        if ( underline == HeadingType.TTITLE.getUnderline() ) {
            builder.append(text.toUpperCase() + '\n');
        } else {
            builder.append(text + '\n' );
        }
        
        for (char c : text.toCharArray()) {
            builder.append(underline);
        }
        builder.append("\n");
        
        return builder.toString();
        
    } // toString
   
} // class Heading
