package Menus;

import Service.PatientService;
import java.util.Scanner;


public class PatientMenu extends BaseMenu<PatientService>{

    PatientService patientService = new PatientService();

    public void BaseMenu(String userId){
        System.out.println("Patient menu \n --------------");


    }


    @Override
    public PatientService getUserService() {
        return patientService;
    }
}
