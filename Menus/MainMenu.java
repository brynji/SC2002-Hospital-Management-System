package Menus;

import Data.AdministratorRepository;
import Data.LoginRepository;
import Misc.*;
import Users.Administrator;
import Users.Doctor;
import Users.Patient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;


public class MainMenu {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //AdministratorRepository admin = new AdministratorRepository();
        //admin.addNew(new Administrator("admin","new admin","jedi","1212-12-10","mail","56"));
        //admin.addNew(new Doctor("doctor","doc","snail","1086-01-29","sa","468"));
        //admin.addNew(new Patient("patient","sdf","a@d.s","156","5",new MedicalRecord("5","sdf","20002-06-12","","")));
        //admin.save();

        Scanner sc = new Scanner(System.in);
        while(true) {
            String date = sc.nextLine();

            if(date=="s") break;
        }

        while (true) {
            System.out.println("HOSPITAL SYSTEM");
            System.out.println("1 LOGIN");
            System.out.println("2 QUIT");
            int choice = sc.nextInt();
            if(choice == 2) { return;}
            System.out.println("Type userID");
            String userId = sc.next();
            System.out.println("Type password");
            String password = sc.next();
            RoleType roles = LoginRepository.CheckCredentials(userId, password);
            switch (roles) {
                case None:
                    System.out.println("invalid credentials");
                    break;
                case Patient:
                    PatientMenu patientMenu = new PatientMenu(userId);
                    patientMenu.baseMenu();
                    break;
                case Doctor:
                    DoctorMenu doctorMenu = new DoctorMenu(userId);
                    doctorMenu.baseMenu();
                    break;
                case Pharmacist:
                    PharmacistMenu pharmacistMenu = new PharmacistMenu(userId);
                    pharmacistMenu.baseMenu();
                    break;
                case Administrator:
                    AdministratorMenu adminMenu = new AdministratorMenu(userId);
                    adminMenu.baseMenu();
                    break;
            }
        }
    }
}