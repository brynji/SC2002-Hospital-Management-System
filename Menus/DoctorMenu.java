package Menus;

import Service.DoctorService;

import java.util.Scanner;

public class DoctorMenu extends BaseMenu<DoctorService> {
    private final DoctorService doctorService;

    public DoctorMenu(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public void baseMenu(String currentUserId,Scanner sc) {
        super.baseMenu(currentUserId,sc);
        int choice;
        while(true){
            System.out.println("Doctor Menu\n--------------");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // To consume the newline character
            /*
            switch (choice) {
                case 1:
                    System.out.println("Please enter the name of the patient whose medical record you want to access:");
                    String patient = sc.nextLine();
                    for ()
                        doctorService.getPatientMedicalRecord(patient);
                    break;
                case 2:
                    doctor.updatePatientMedicalRecords();
                    break;
                case 3:
                    viewPersonalSchedule();
                    break;
                case 4:
                    setAvailability();
                    break;
                case 5:
                    acceptOrDeclineAppointments();
                    break;
                case 6:
                    viewUpcomingAppointments();
                    break;
                case 7:
                    recordAppointmentOutcome();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            */
        }
    }

    @Override
    public DoctorService getUserService() {
        return doctorService;
    }
}
