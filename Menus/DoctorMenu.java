package Menus;

import Service.DoctorService;
import Users.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import Misc.Appointment;
import Misc.AppointmentOutcomeRecord;
import Misc.Prescription;

import java.util.List;

public class DoctorMenu extends BaseMenu<DoctorService> {
    private final DoctorService doctorService;

    public DoctorMenu(String userId) {
        doctorService = new DoctorService(userId);
    }

    @Override
    public void baseMenu() {
        super.baseMenu();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Doctor Menu\n--------------");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests"); // PENDING appts only
            System.out.println("6. View Confirmed Appointments"); // CONFIRMED appts only
            System.out.println("7. Record Appointment Outcome"); // Set appts to COMPLETE, record AOR
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // To consume the newline character

            switch (choice) {
                case 1:
                    viewPatientMedicalRecords();
                    break;
                case 2:
                    updatePatientMedicalRecords();    
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
                    viewConfirmedAppointments();
                    break;
                case 7:
                    recordAppointmentOutcome();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public void viewPatientMedicalRecords() {
        System.out.println("Enter patient name:\n");
        String patientName = sc.nextLine();
        sc.nextLine();

        Collection<Patient> patients = doctorService.getPatientsInCareByName(patientName);
        List <AppointmentOutcomeRecord> aors = doctorService.getPatientAppointmentOutcomeRecord(patientName);
        
        for (Patient patient: patients) {
            String medRec = patient.getMedicalRecord().getDetails();
            System.out.printf("Medical Record for patient %s", patient);
            System.out.println(medRec);
            
            for (AppointmentOutcomeRecord aor : aors) {
                System.out.println(aor.getDetails());
            }
        }
    }

    // public void updatePatientMedicalRecords() {
    //     System.out.println("Enter patient name:\n");
    //     String patientName = sc.nextLine();
    //     sc.nextLine();

    //     List<AppointmentOutcomeRecord> aors = doctorService.getPatientAppointmentOutcomeRecord(patientName);
    //     for(AppointmentOutcomeRecord aor: aors) {
    //         String details = aor.getDetails();
    //         System.out.println(details);
    //     }
    //     System.out.println("Which appointment outcome record do you want to edit?");

    // }

    public void updatePatientMedicalRecords() {
        System.out.println("Enter patient name:");
        String patientName = sc.nextLine();
        sc.nextLine();
    
        List<AppointmentOutcomeRecord> aors = doctorService.getPatientAppointmentOutcomeRecord(patientName);
    
        // Display all appointment outcome records with an index
        for (int i = 0; i < aors.size(); i++) {
            System.out.println((i + 1) + ". " + aors.get(i).getDetails());
        }
    
        // Prompt user to select which record to edit
        System.out.println("Which appointment outcome record do you want to edit? Enter the number:");
        int choice = sc.nextInt() - 1; // Subtract 1 to match list index
        sc.nextLine();
    
        if (choice >= 0 && choice < aors.size()) {
            AppointmentOutcomeRecord selectedAOR = aors.get(choice);
            System.out.println("Do you want to change the prescriptions?\n 1. Yes\n 2. No");
            if(sc.nextInt() == 1) {
                System.out.println("Enter new prescription ");
                //TODO: IMPLEMENT LOGIC (IMPORTANT)
                selectedAOR.setPrescriptions(null);
            } 
            
            System.out.println("Do you want to change the consultation notes?\n 1. Yes\n 2. No");
            if (sc.nextInt() == 1) {
                System.out.println("Please enter new consultation notes:");
                String newNotes = sc.nextLine();
                selectedAOR.setConsultationNotes(newNotes);
            }

        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    public void viewPersonalSchedule() {
        // TODO: implement functionality 
    }

    public void setAvailability() {
        // TODO: implement functionality 
    }

    public void acceptOrDeclineAppointments() {
        System.out.println("Appointment requests:\n");
        Collection<Appointment> pendingAppts = doctorService.getAllPendingAppointments();

        for(Appointment appt: pendingAppts) {
            System.out.println(appt.getDetails());
            System.out.println("Do you want to accept this appointment?\n 1. Yes\n 2. No");
            if (sc.nextInt() == 1) {
                doctorService.acceptAppointment(appt.getAppointmentID());
                System.out.println("Appointment has been accepted.");
            }
            else {
                doctorService.declineAppointment(appt.getAppointmentID());
                System.out.println("Appointment has been declined.");
            }
        }
    }

    public void viewConfirmedAppointments() {
        System.out.println("Upcoming appointments:\n");
        Collection<Appointment> appts = doctorService.getConfirmedAppointments();
        
        if (appts.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }

        for (Appointment appt: appts) {
            System.out.println(appt.getDetails());
        }
    }

    public void recordAppointmentOutcome() {
        System.out.println("Select completed appointment:");
        Collection<Appointment> appointmentCollection = doctorService.getConfirmedAppointments();
        
        if (appointmentCollection.isEmpty()) {
            System.out.println("No appointments completed.");
            return;
        }

        List<Appointment> appointmentList = new ArrayList<>(appointmentCollection);
        
        int index = 1;
        for (Appointment appt : appointmentList) {
            System.out.printf("%d. %s\n", index++, appt.getDetails());
        }

        // Prompt user to select an appointment by number
        System.out.print("Enter the number of the appointment to complete: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (choice < 1 || choice > appointmentList.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Appointment selectedAppointment = appointmentList.get(choice - 1);
        String appointmentId = selectedAppointment.getAppointmentID();

        System.out.println("Filling in details for the Appointment Outcome Record:");
        LocalDate appointmentDate = selectedAppointment.getDate();

        System.out.print("Enter service type: ");
        String serviceType = sc.nextLine();

        // System.out.print("Enter prescription: ");
        // List<Prescription> prescription = ???

        System.out.print("Enter consultation notes: ");
        String consultationNotes = sc.nextLine();

        AppointmentOutcomeRecord aor = new AppointmentOutcomeRecord(appointmentId, appointmentDate, serviceType, null, consultationNotes);
        doctorService.completeAppointment(appointmentId, aor);
        System.out.println("Appointment Outcome Record created.");
        System.out.println("Appointment completed.");
    }


    @Override
    public DoctorService getUserService() {
        return doctorService;
    }
}
