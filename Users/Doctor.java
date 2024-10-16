package Project.Users;

public class Doctor extends User{
    //List<int> for consistency?
    private List<Appointment> appointments;

    public Doctor(String userID, String name, String email, String contactNumber){
        super(userID,name,email,contactNumber);
        appointments = new ArrayList<>();
    }
}
