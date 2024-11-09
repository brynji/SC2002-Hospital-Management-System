package Menus;

import Misc.DateTimeslot;
import Service.PatientService;
import Users.Doctor;

import java.util.ArrayList;
import java.util.Scanner;

public class PatientMenu extends BaseMenu<PatientService>{

    private final PatientService patientService;

    public PatientMenu(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public void baseMenu(String currentUserId, Scanner sc){
        super.baseMenu(currentUserId,sc);
        while(true){
            System.out.println("Patient menu \n --------------");
            System.out.println("1 Change passwd");
            System.out.println("2 Schedule an appointment");
            System.out.println("3 View upcoming appointments");
            System.out.println("3 Logout");

            switch (sc.nextInt()){
                case 1:
                    changePassword();
                    break;
                case 2:
                    scheduleAppointment();
                    break;
                case 3:
                    viewUpcomingAppointments();
                    break;
                default:
                    return;
            }
        }
    }

    public void scheduleAppointment(){
        System.out.println("Choose doctor you want to visit");
        ArrayList<Doctor> doctors = new ArrayList<>(patientService.getAllDoctors());
        for(int i=0; i<doctors.size(); i++){
            System.out.println(i + ": " + doctors.get(i).getName());
        }
        int doctorChoice = sc.nextInt();
        System.out.println("Available dates and times:");
        ArrayList<DateTimeslot> times = new ArrayList<>(patientService.getFreeTimeslots(doctors.get(doctorChoice).getUserID()));
        for(int i=0; i<times.size(); i++){
            System.out.println(i + ": " + times.get(i).getDate() + " " + times.get(i).getTimeslot());
        }
        int timeslotChoice = sc.nextInt();
        patientService.addNewAppointment(doctors.get(doctorChoice).getUserID(),
                times.get(timeslotChoice).getDate(),times.get(timeslotChoice).getTimeslot());
        System.out.println("Successfully booked doctor "+doctors.get(doctorChoice).getName()+" on "
                +times.get(timeslotChoice).getDate() + ", time " + times.get(timeslotChoice).getTimeslot());
    }

    public void viewUpcomingAppointments(){
        System.out.println("Your upcoming appointments:");
        for(var app : patientService.getUpcomingAppointments()){
            System.out.println(patientService.getDoctorName(app.getDoctorId())+" "+app.getDate()+" "+app.getTime());
        }
    }


    @Override
    public PatientService getUserService() {
        return patientService;
    }
}
