package Project.Users;
import java.util.Scanner;

public abstract class User{

    private String userID;
    private String password;
    private String name;
    private String email;
    private String contactNumber;
    //Is it necessary?
    private boolean firstLogin;
    
    /* note that we initialise the contact number as a string to store symbols (e.g. "+65") 
    and to store longer international numbers. Java int can only hold up to 10 digits. */
    
    // Constructor 
    public User(String userID, String name, String email, String contactNumber) {
       
        this.userID = userID;
        this.password = "password"; // Standard password for all first time logins
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.firstLogin = true;
    }

    // Getters
    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // TODO: Improve change password function to check password history

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean validateCredentials (String inputUserID, String inputPassword) {
        return this.userID == inputUserID && this.password == inputPassword;
    }

    public void updateContactInfo (String newEmail, String newContactNumber) {
        setEmail(newEmail);
        setContactNumber(newContactNumber);
    }
}