package Menus;

import Service.UserService;

import java.util.Scanner;

public abstract class BaseMenu<T extends UserService> {
    Scanner sc = new Scanner(System.in);

    public abstract void baseMenu();
    public abstract T getUserService();

    public void ChangePassword(){
        System.out.println("Type new passwd");
        String passwd = sc.next();
        getUserService().ChangePassword(passwd);
        System.out.println("new passwd: " + passwd);
    }

    public void UpdatePersonalInfo(){
        String name = "name";
        String email = "mail";
        String contactNumber = "1";
        // get info

        getUserService().UpdatePersonalInfo(name,email,contactNumber);
    }
}
