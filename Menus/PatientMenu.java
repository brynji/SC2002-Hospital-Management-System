package Menus;

import Service.PatientService;
import java.util.Scanner;


public class PatientMenu extends BaseMenu<PatientService>{

    PatientService patientService;

    public PatientMenu(String currentUserId) {
        patientService = new PatientService(currentUserId);
    }

    @Override
    public void baseMenu(){
        while(true){
            System.out.println("Patient menu \n --------------");
            System.out.println("1 Change passwd");
            System.out.println("2 Logout");

            switch (sc.nextInt()){
                case 1:
                    ChangePassword();
                    break;
                case 2:
                    return;
            }
        }
    }


    @Override
    public PatientService getUserService() {
        return patientService;
    }
}
