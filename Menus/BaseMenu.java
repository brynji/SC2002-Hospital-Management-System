package Menus;

import Service.UserService;

import java.util.Scanner;

public abstract class BaseMenu<T extends UserService> {
    Scanner sc = new Scanner(System.in);

    public void baseMenu(){
        if(getUserService().getCurrentUser().getFirstLogin()){
            System.out.println("This is your first login, please change your password");
            changePassword();
            getUserService().getCurrentUser().setFirstLogin(false);
            getUserService().getRepository().save();
        }
    }

    public abstract T getUserService();

    public void changePassword(){
        System.out.println("Enter new password");
        String passwd = sc.next();
        getUserService().changePassword(passwd);
        System.out.println("Your new password is '" + passwd + '\'');
    }

    public void updatePersonalInfo(){
        String name = "name";
        String email = "mail";
        String contactNumber = "1";
        // get info

        getUserService().updatePersonalInfo(name,email,contactNumber);
    }
}
