package Menus;

import Misc.RoleType;
import Service.PatientService;

import java.util.Scanner;

import static Data.BaseRepository.CheckCredentials;

public class PatientMenu {
    Scanner sc = new Scanner(System.in);
    PatientService patientService = new PatientService();

    public void BaseMenu(String userId){
        System.out.println("Patient menu \n --------------");


    }

    public void ChangePasswd(){
        System.out.println("Type new passwd");
        String passwd = sc.nextLine();
        patientService.ChangePassword(passwd);
        System.out.println("new passwd: " + passwd);
    }

    public void Login(String userId){

    }
}
