package Menus;

import Misc.RoleType;
import java.io.IOException;
import java.util.Scanner;

import static Data.BaseRepository.CheckCredentials;

public class MainMenu {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("LOGIN");
        System.out.println("Type userID");
        String userId = sc.nextLine();
        System.out.println("Type password");
        String password = sc.nextLine();
        RoleType roles = CheckCredentials(userId, password);
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