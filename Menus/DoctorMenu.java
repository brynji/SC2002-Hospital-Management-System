package Menus;

import Misc.DateTimeslot;
import Misc.Prescription;
import Service.DoctorService;
import Users.Patient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import Misc.Appointment;
import Misc.AppointmentOutcomeRecord;

import java.util.List;

public class DoctorMenu extends BaseMenu<DoctorService> {
    private final DoctorService doctorService;

    public DoctorMenu(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public void baseMenu(String currentUserId,Scanner sc) {
        super.baseMenu(currentUserId,sc);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println();
            System.out.println("""
            Doctor Menu
            -----------
            1. View Patient Medical Records
            2. Update Patient Medical Records
            3. View Personal Schedule
            4. Set Availability for Appointments
            5. Accept or Decline Appointment Requests
            6. View Confirmed Appointments
            7. Record Appointment Outcome
            8. Logout""");

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
        ArrayList<Patient> patients = new ArrayList<>(doctorService.getAllPatientsInCare());
        if(patients.isEmpty()){
            System.out.println("No patients in care, nothing to view");
            return;
        }
        System.out.print("Choose patient: ");
        Patient patient = patients.get(printAllAndChooseOne(patients));
        Collection<AppointmentOutcomeRecord> aors = doctorService.getAppointmentOutcomesFromPatient(patient);

        System.out.println("Patient's medical records:");
        System.out.println(patient.getMedicalRecord().toString());
        System.out.println("Past appointment outcome records:");
        for (AppointmentOutcomeRecord aor : aors) {
            System.out.println(aor);
        }
    }

    public void updatePatientMedicalRecords() {
        ArrayList<Patient> patients = new ArrayList<>(doctorService.getAllPatientsInCare());
        if(patients.isEmpty()){
            System.out.println("No patients in care, nothing to update");
            return;
        }
        System.out.println("Choose patient:");
        Patient patient = patients.get(printAllAndChooseOne(patients));

        System.out.println("Do you want to view medical record and past appointments records?");
        int choice = printAllAndChooseOne(List.of("view","just add diagnosis and treatment"));
        if(choice==0){
            System.out.println("Medical record:");
            System.out.println(patient.getMedicalRecord().toString());
            for(var aor : doctorService.getAppointmentOutcomesFromPatient(patient)){
                System.out.println(aor);
            }
        }

        System.out.print("Enter your new diagnosis and treatment: ");
        String diagnosis = sc.nextLine();

        doctorService.addNewDiagnosis(patient.getUserID(),diagnosis);
        System.out.println("Diagnosis and treatment added successfully.");
    }

    public void viewPersonalSchedule() {
        System.out.println("All appointments in next 7 days:");
        for(DateTimeslot dateTimeslot : doctorService.getUpcomingSchedule()){
            System.out.println(dateTimeslot);
        }
    }

    public void setAvailability() {
        System.out.println("Current availability for new appointments: "
                + (doctorService.getCurrentUser().isAvailableForNewAppointments() ? "Available" : "Not Available"));
        System.out.println("Do you want to change your availability?");

        if (printAllAndChooseOne(List.of("yes","no"))==0) {
            doctorService.changeAvailability();
            System.out.println("Availability changed to "+
                    (doctorService.getCurrentUser().isAvailableForNewAppointments() ? "Available" : "Not Available"));
        } else {
            System.out.println("Availability not changed");
        }
    }

    public void acceptOrDeclineAppointments() {
        Collection<Appointment> pendingAppts = doctorService.getAllPendingAppointments();
        if(pendingAppts.isEmpty()){
            System.out.println("No pending appointment requests, nothing to accept");
            return;
        }
        System.out.println("Appointment requests:");

        for(Appointment appt: pendingAppts) {
            System.out.println(appt.toString());
            System.out.println("Do you want to accept this appointment?");
            if (printAllAndChooseOne(List.of("yes","no"))==0) {
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
        Collection<Appointment> appts = doctorService.getConfirmedAppointments();
        if(appts.isEmpty()){
            System.out.println("No upcoming appointments, nothing to view");
            return;
        }
        System.out.println("Upcoming appointments:");
        for (Appointment appt: appts) {
            System.out.println(appt.toString());
        }
    }

    public void recordAppointmentOutcome() {
        ArrayList<Appointment> appointments = new ArrayList<>(doctorService.getConfirmedAppointments());
        if(appointments.isEmpty()){
            System.out.println("No appointments completed");
            return;
        }
        System.out.println("Select completed appointment:");
        Appointment appointment = appointments.get(printAllAndChooseOne(appointments));

        System.out.println("Filling in details for the Appointment Outcome Record:");
        System.out.print("Enter service type: ");
        String serviceType = sc.nextLine();

        ArrayList<Prescription> prescriptions = new ArrayList<>();
        do {
            System.out.print("Prescription - Enter medication name: ");
            String medication = sc.nextLine();
            while (!doctorService.isValidMedication(medication)) {
                System.out.println("Invalid medication name, try again");
                medication = sc.nextLine();
            }
            System.out.print("Enter medication amount: ");
            int amount = Integer.parseInt(sc.nextLine());
            while (amount < 0) {
                System.out.println("Invalid medication amount, try again");
                amount = Integer.parseInt(sc.nextLine());
            }
            prescriptions.add(new Prescription(doctorService.getNewAORId(), medication, amount));
        } while (printAllAndChooseOne(List.of("Add more prescriptions", "Continue")) == 0);

        System.out.print("Enter consultation notes: ");
        String consultationNotes = sc.nextLine();

        AppointmentOutcomeRecord aor = new AppointmentOutcomeRecord(appointment.getAppointmentID(), appointment.getDate(), serviceType, prescriptions, consultationNotes);
        doctorService.completeAppointment(appointment.getAppointmentID(), aor);
        System.out.println("Appointment Outcome Record created.");
        System.out.println("Appointment completed.");
    }


    @Override
    public DoctorService getUserService() {
        return doctorService;
    }
}
