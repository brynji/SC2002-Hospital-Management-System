package Service;

import Data.BaseRepository;
import Users.User;

public interface IService <T extends User, U extends BaseRepository> {
    T getCurrentUser();
    void setCurrentUser(String userId);
    U getRepository();

    void changePassword(String newPassword);
    void updateName(String newName);
    void updateEmail(String newEmail);
    void updateContactNumber(String newContactNumber);
}
