package Users;

public class Pharmacist extends User{
    public Pharmacist(String userID, String name, String gender, String dateOfBirth, String email, String contactNumber) {
        super(userID, name, gender, dateOfBirth, email, contactNumber);
    }

    @Override
    public String toString() {
        return "Pharmacist - " + super.toString();
    }
}
