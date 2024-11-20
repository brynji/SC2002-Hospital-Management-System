package Menus;

import Service.IService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    protected int nextInt(){
        int in;
        try{
            in = Integer.parseInt(sc.nextLine());
        } catch(Exception e){
            System.out.println("Invalid input, please try again");
            return nextInt();
        }
        return in;
    }

    public void changePassword(){
        System.out.print("Enter new password: ");
        String passwd = sc.nextLine();
        getUserService().changePassword(passwd);
        System.out.println("Your new password is " + passwd);
    }

    public void updatePersonalInformation(){
        ArrayList<String> options = new ArrayList<>(List.of("name","email","contact number"));
        System.out.println("Update of personal information");
        int choice = printAllAndChooseOne(options);
        System.out.print("Enter your new " + options.get(choice)+": ");
        String newField = sc.nextLine();
        switch(choice){
            case 0 -> getUserService().updateName(newField);
            case 1 -> getUserService().updateEmail(newField);
            case 2 -> getUserService().updateContactNumber(newField);
        }
        System.out.println("Your new "+options.get(choice)+" is "+newField);
    }

    protected int printAllAndChooseOne(Collection<?> objects){
        if(objects.isEmpty())
            return -1;
        int i=1;
        for(Object o : objects){
            System.out.println(i+" "+o);
            i++;
        }
        System.out.print("Enter your choice: ");
        int choice = nextInt() - 1;
        while(choice<0 || choice>=objects.size()){
            System.out.println("Invalid choice, try again");
            choice = nextInt() - 1;
        }
        return choice;
    }
}
