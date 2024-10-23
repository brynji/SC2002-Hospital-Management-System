package Service;

import Data.BaseRepository;
import Users.User;

public abstract class UserService<T extends User, U extends BaseRepository> {

    public abstract void setCurrentUser(String userId);

    public void changePassword(String newPassword){
        getCurrentUser().setPassword(newPassword);
        getRepository().save();
    }

    public void updatePersonalInfo(String name, String email, String contactNumber){
        getCurrentUser().setName(name);
        getCurrentUser().updateContactInfo(email, contactNumber);
        getRepository().save();
    }

    public void updateAllData(){
        String currentUserId = getCurrentUser().getUserID();
        getRepository().update();
        setCurrentUser(currentUserId);
    }

    public abstract T getCurrentUser();
    public abstract U getRepository();
}