package Service;

import Data.BaseRepository;
import Users.User;

public abstract class UserService<T extends User, U extends BaseRepository> {

    public abstract void setCurrentUser(String userId);

    public void ChangePassword(String newPassword){
        getCurrentUser().setPassword(newPassword);
        getRepository().Save();
    }

    public void UpdatePersonalInfo(String name, String email, String contactNumber){
        getCurrentUser().setName(name);
        getCurrentUser().updateContactInfo(email, contactNumber);
        getRepository().Save();
    }

    public abstract T getCurrentUser();
    public abstract U getRepository();
}