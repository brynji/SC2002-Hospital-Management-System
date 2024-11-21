package Users;

/**
 * Pharmacist takes care of dispensing medications, can be added by administrator
 */
public class Pharmacist extends User{
    /**
     * Initializes Pharmacist class
     * @throws IllegalArgumentException for invalid date of birth
     * @param userID Hospital-unique ID, used for login
     * @param name Full Name
     * @param gender Gender of the user
     * @param dateOfBirth Date of birth in dd/mm/yyyy format
     * @param email Email address
     * @param contactNumber Contact number, can include country prefix
     */
    public Pharmacist(String userID, String name, String gender, String dateOfBirth, String email, String contactNumber) {
        super(userID, name, gender, dateOfBirth, email, contactNumber);
    }

    @Override
    public String toString() {
        return "Pharmacist - " + super.toString();
    }
}
