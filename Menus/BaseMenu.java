package Menus;

import Service.IService;

import java.util.Collection;
import java.util.Scanner;

public abstract class BaseMenu<T extends IService> implements IMenu<T> {
    protected Scanner sc;

    public void baseMenu(String currentUserId, Scanner sc) {
        this.sc = sc;
        getUserService().setCurrentUser(currentUserId);
        if(getUserService().getCurrentUser().getFirstLogin()){
            System.out.println("This is your first login, please change your password");
            changePassword();
            getUserService().getCurrentUser().setFirstLogin(false);
            getUserService().getRepository().save();
        }
    }

    public void changePassword(){
        System.out.println("Enter new password");
        String passwd = sc.next();
        getUserService().changePassword(passwd);
        System.out.println("Your new password is '" + passwd + '\'');
    }

    public void updatePersonalInformation(){
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

    protected int printAllAndChooseOne(Collection<?> objects){
        int i=1;
        for(Object o : objects){
            System.out.println(i+" "+o);
        }
        int choice = sc.nextInt() - 1;
        while(choice<0 || choice>=objects.size()){
            System.out.println("Invalid Choice, try again");
            choice = sc.nextInt() - 1;
        }
        sc.nextLine(); //consume newline
        return choice;
    }
}
