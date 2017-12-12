package ie.gmit.sw.os.journal.model;

import java.io.Serializable;
//import java.util.List;




public class User implements Serializable {
//  Constants
    private static final long serialVersionUID = 1L;
    
//  Fields
    private int id;
    private String userName;
    private String password;
    private String name;
    private String address;
    private String ppsNumber;
    private int age;
    private double weight;
    private double height;
    
//    private List<Record> journal;
    
    
    
    
//  Constructors
    public User() {
        
    }
    
    public User(int id, String userName, String password, String name, String address, String ppsNumber, int age, double weight, double height) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.ppsNumber = ppsNumber;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }
    
    
    
    
//  Accessors and mutators
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPpsNumber() {
        return ppsNumber;
    }

    public void setPpsNumber(String ppsNumber) {
        this.ppsNumber = ppsNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if ( age >= 0 ) {
            this.age = age;
        }
        else {
            this.age = 0;
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if ( weight >= 0 ) {
            this.weight = weight;
        }
        else {
            this.weight = 0;
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if ( height >= 0 ) {
            this.height = height;
        }
        else {
            this.height = 0;
        }
    }
    
    
    
    
//  Methods
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=");
        builder.append(id);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", password=");
        builder.append(password);
        builder.append(", name=");
        builder.append(name);
        builder.append(", address=");
        builder.append(address);
        builder.append(", ppsNumber=");
        builder.append(ppsNumber);
        builder.append(", age=");
        builder.append(age);
        builder.append(", weight=");
        builder.append(weight);
        builder.append(", height=");
        builder.append(height);
        builder.append("]");
        return builder.toString();
    }
    
} // class User
