package Users;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public abstract class User implements Serializable {

    private final String userID;
    private String password;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String email;
    private String contactNumber;
    private boolean firstLogin;
    
    /* note that we initialise the contact number as a string to store symbols (e.g. "+65") 
    and to store longer international numbers. Java int can only hold up to 10 digits. */
    
    // Constructor 


    public User(String userID, String name, String gender, String dateOfBirth, String email, String contactNumber) {
        this.userID = userID;
        this.password = "password";
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.contactNumber = contactNumber;
        firstLogin = true;
    }

    // Getters
    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {return gender;}

    public String getDateOfBirth() {return dateOfBirth;}

    public int getAge(){
        LocalDate now = LocalDate.now();
        LocalDate birthDate = LocalDate.parse(dateOfBirth);
        return Period.between(birthDate,now).getYears();
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public boolean getFirstLogin(){ return firstLogin; }

    // Setters
    public void setName(String name){ this.name = name; }

    public void setGender(String gender){ this.gender = gender; }

    public void setDateOfBirth(String dateOfBirth){ this.dateOfBirth = dateOfBirth; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setPassword(String newPassword) { this.password = newPassword; }

    public void setFirstLogin(boolean firstLogin) { this.firstLogin = firstLogin; }

    public boolean validateCredentials (String inputUserID, String inputPassword) {
        return this.userID.equals(inputUserID) && this.password.equals(inputPassword);
    }

    public void updateContactInfo (String newEmail, String newContactNumber) {
        setEmail(newEmail);
        setContactNumber(newContactNumber);
    }

    @Override
    public String toString() {
        return  "userID: '" + userID + '\'' +
                ", name: '" + name + '\'' +
                ", gender: " + gender + '\'' +
                ", dateOfBirth: '" + dateOfBirth + '\'' +
                ", email: '" + email + '\'' +
                ", contactNumber: '" + contactNumber + '\'';
    }
}