package Users;

public class Administrator extends User{
    public Administrator(String userID, String name, String gender, String dateOfBirth, String email, String contactNumber) {
        super(userID, name, gender, dateOfBirth, email, contactNumber);
    }

    @Override
    public String toString() {
        return "Administrator - " + super.toString();
    }
}
