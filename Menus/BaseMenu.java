package Menus;

import Service.UserService;

import java.util.Arrays;
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
        String[] options = {"name","email","contact number"};
        System.out.println("What do you want to update");
        for(int i = 0; i < options.length; i++)
            System.out.println(i+1 + " " +options[i]);
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new " + options[choice] );
        String newField = sc.nextLine();
        switch(choice){
            case 1 -> getUserService().updateName(newField);
            case 2 -> getUserService().updateEmail(newField);
            case 3 -> getUserService().updateContactNumber(newField);
        }
    }
}
