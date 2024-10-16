package Service;

public abstract class UserService {
    public abstract void SetCurrentUser(String userId);
    public abstract void ChangePassword(String newPassword);
    public abstract void UpdatePersonalInfo(String name, String email, String contactNumber);
}
