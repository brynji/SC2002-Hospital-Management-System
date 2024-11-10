package Service;

import Data.BaseRepository;
import Users.User;

public abstract class UserService<T extends User, U extends BaseRepository> implements IService<T, U>{
    public void changePassword(String newPassword){
        getCurrentUser().setPassword(newPassword);
        getRepository().save();
    }

    public void updateName(String newName){
        getCurrentUser().setName(newName);
        getRepository().save();
    }

    public void updateEmail(String newEmail){
        getCurrentUser().setEmail(newEmail);
        getRepository().save();
    }

    public void updateContactNumber(String newContactNumber){
        getCurrentUser().setContactNumber(newContactNumber);
        getRepository().save();
    }

    public String getEmail() {
        return getCurrentUser().getEmail();
    }

    public String getContactNumber() {
        return getCurrentUser().getContactNumber();
    }

    public abstract T getCurrentUser();
    public abstract U getRepository();
}