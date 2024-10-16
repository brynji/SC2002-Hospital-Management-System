package Menus;

import Data.LoginRepository;
import Misc.RoleType;
import java.util.Scanner;


public class MainMenu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("LOGIN");
        System.out.println("Type userID");
        String userId = sc.nextLine();
        System.out.println("Type password");
        String password = sc.nextLine();
        RoleType roles = LoginRepository.CheckCredentials(userId, password);
        switch (roles) {
            case None:
                System.out.println("invalid userId");
                return;
            case Patient:
                PatientMenu patientMenu = new PatientMenu();
                patientMenu.BaseMenu(userId);
            case Doctor:
        }
    }
}