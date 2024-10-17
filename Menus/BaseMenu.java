package Menus;

import Service.UserService;

import java.util.Scanner;

public abstract class BaseMenu<T extends UserService> {
    Scanner sc = new Scanner(System.in);

    public void ChangePassword(){
        System.out.println("Type new passwd");
        String passwd = sc.next();
        getUserService().ChangePassword(passwd);
        System.out.println("new passwd: " + passwd);
    }

    public void UpdatePersonalInfo(){

    }

    public abstract T getUserService();
}
