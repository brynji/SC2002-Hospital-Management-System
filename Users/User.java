package Users;
import Misc.DateHelper;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * Base class of all users in the system
 */
public abstract class User implements Serializable {
    private final String userID;
    private String password;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String email;
    private String contactNumber;
    private boolean firstLogin;

    /**
     * Initializes User class
     * @throws IllegalArgumentException for invalid date of birth
     * @param userID Hospital-unique ID, used for login
     * @param name Full Name
     * @param gender Gender of the user
     * @param dateOfBirth Date of birth in dd/mm/yyyy format
     * @param email Email address
     * @param contactNumber Contact number, can include country prefix
     */
    public User(String userID, String name, String gender, String dateOfBirth, String email, String contactNumber) {
        if(!DateHelper.isValidDateOfBirth(dateOfBirth)) throw new IllegalArgumentException("Date of Birth is invalid");
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

    /**
     * Counts age based on current date and date of birth
     * @return Age of the user
     */
    public int getAge(){
        LocalDate now = LocalDate.now();
        LocalDate birthDate = DateHelper.parseDate(dateOfBirth);
        return Period.between(birthDate,now).getYears();
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Indicates if user logged in at least once and changed his password
     * @return true if user already logged in at least once, false otherwise
     */
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

    /**
     * Verifies if given UserId and password is correct, this is the only way to verify password
     * @param userID Id used for login
     * @param password Password used for login
     * @return true if userId and password match given arguments, false otherwise
     */
    public boolean validateCredentials (String userID, String password) {
        return this.userID.equals(userID) && this.password.equals(password);
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