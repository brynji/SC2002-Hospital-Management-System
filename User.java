package Project.Users;
import java.util.Scanner;

public abstract class User{

    private String userID;
    private String password;
    private String name;
    private String email;
    private String contactNumber; 
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

    public void changePassword() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your new password:");
        String newPassword = sc.nextLine();
        this.password = newPassword;
        System.out.println("Password has been changed.");
        sc.close();
    }

    public boolean login (String inputUserID, String inputPassword) {
        
        boolean authenticated = false;
        
        if (this.userID == inputUserID && this.password == inputPassword) {
            authenticated = true;
        }

        if (authenticated && firstLogin) {
            System.out.println("This is your first login. Please change your password.");
            changePassword();
            firstLogin = false;
        }

        return authenticated;

    }

    public void updateContactInfo (String newEmail, String newContactNumber) {

        setEmail(newEmail);
        setContactNumber(newContactNumber);
        System.out.println("Contact information has been updated.");
    }


    

}


