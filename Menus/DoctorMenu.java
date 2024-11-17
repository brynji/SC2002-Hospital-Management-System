package Menus;

import Misc.*;
import Service.DoctorService;
import Users.Patient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Menu class for doctor-specific operations.
 * Extends BaseMenu to provide a menu-driven interface for doctors to manage patients, appointments, and schedules.
 */
public class DoctorMenu extends BaseMenu<DoctorService> {

    /** The DoctorService instance used for handling business logic and data operations. */
    private final DoctorService doctorService;

    /**
     * Constructs a DoctorMenu with the given DoctorService.
     *
     * @param doctorService the DoctorService instance to be used for doctor-specific operations.
     */
    public DoctorMenu(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Displays the main menu for doctors and handles user input to perform the corresponding operations.
     *
     * @param currentUserId the ID of the currently logged-in doctor.
     * @param sc the Scanner instance for reading user input.
     */
    @Override
    public void baseMenu(String currentUserId, Scanner sc) {
        super.baseMenu(currentUserId, sc);
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
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> viewPatientMedicalRecords();
                case 2 -> updatePatientMedicalRecords();
                case 3 -> viewPersonalSchedule();
                case 4 -> setAvailability();
                case 5 -> acceptOrDeclineAppointments();
                case 6 -> viewConfirmedAppointments();
                case 7 -> recordAppointmentOutcome();
                case 8 -> {
                    System.out.println("Logging out...");
                    isRunning = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the medical records of a selected patient.
     */
    public void viewPatientMedicalRecords() {
        ArrayList<Patient> patients = new ArrayList<>(doctorService.getAllPatientsInCare());
        if (patients.isEmpty()) {
            System.out.println("No patients in care, nothing to view.");
            return;
        }
        System.out.print("Choose patient: ");
        Patient patient = patients.get(printAllAndChooseOne(patients));
        Collection<AppointmentOutcomeRecord> aors = doctorService.getAppointmentOutcomesFromPatient(patient);

        System.out.println("Patient's medical records:");
        System.out.println(patient.getMedicalRecord());
        System.out.println("Past appointment outcome records:");
        for (AppointmentOutcomeRecord aor : aors) {
            System.out.println(aor);
        }
    }

    /**
     * Updates the medical records of a selected patient.
     */
    public void updatePatientMedicalRecords() {
        ArrayList<Patient> patients = new ArrayList<>(doctorService.getAllPatientsInCare());
        if (patients.isEmpty()) {
            System.out.println("No patients in care, nothing to update.");
            return;
        }
        System.out.println("Choose patient:");
        Patient patient = patients.get(printAllAndChooseOne(patients));

        System.out.println("Do you want to view medical record and past appointments records?");
        int choice = printAllAndChooseOne(List.of("view", "just add diagnosis and treatment"));
        if (choice == 0) {
            System.out.println("Medical record:");
            System.out.println(patient.getMedicalRecord());
            for (var aor : doctorService.getAppointmentOutcomesFromPatient(patient)) {
                System.out.println(aor);
            }
        }

        System.out.print("Enter your new diagnosis and treatment: ");
        String diagnosis = sc.nextLine();

        doctorService.addNewDiagnosis(patient.getUserID(), diagnosis);
        System.out.println("Diagnosis and treatment added successfully.");
    }

    /**
     * Displays the doctor's personal schedule for the next 7 days.
     */
    public void viewPersonalSchedule() {
        System.out.println("All appointments in next 7 days:");
        for (DateTimeslot dateTimeslot : doctorService.getUpcomingSchedule()) {
            System.out.println(dateTimeslot);
        }
    }

    /**
     * Allows the doctor to set or update their availability for appointments.
     */
    public void setAvailability() {
        System.out.println("Current availability for new appointments: "
                + (doctorService.getCurrentUser().isAvailableForNewAppointments() ? "Available" : "Not Available"));
        System.out.println("Do you want to change your availability?");

        if (printAllAndChooseOne(List.of("yes", "no")) == 0) {
            doctorService.changeAvailability();
            System.out.println("Availability changed to " +
                    (doctorService.getCurrentUser().isAvailableForNewAppointments() ? "Available" : "Not Available"));
        } else {
            System.out.println("Availability not changed.");
        }
    }

    /**
     * Handles accepting or declining pending appointment requests.
     */
    public void acceptOrDeclineAppointments() {
        Collection<Appointment> pendingAppts = doctorService.getAllPendingAppointments();
        if (pendingAppts.isEmpty()) {
            System.out.println("No pending appointment requests, nothing to accept.");
            return;
        }
        System.out.println("Appointment requests:");
        int i = 1;
        for (Appointment appt : pendingAppts) {
            System.out.println("Request " + i + " of " + pendingAppts.size());
            System.out.println(appt);
            System.out.println("Do you want to accept this appointment?");
            if (printAllAndChooseOne(List.of("yes", "no")) == 0) {
                doctorService.acceptAppointment(appt.getAppointmentID());
                System.out.println("Appointment has been accepted.");
            } else {
                doctorService.declineAppointment(appt.getAppointmentID());
                System.out.println("Appointment has been declined.");
            }
            i++;
        }
    }

    /**
     * Displays all confirmed appointments for the doctor.
     */
    public void viewConfirmedAppointments() {
        Collection<Appointment> appts = doctorService.getConfirmedAppointments();
        if (appts.isEmpty()) {
            System.out.println("No upcoming appointments, nothing to view.");
            return;
        }
        System.out.println("Upcoming appointments:");
        for (Appointment appt : appts) {
            System.out.println(appt);
        }
    }

    /**
     * Records the outcome of a completed appointment, including service type, prescriptions, and consultation notes.
     */
    public void recordAppointmentOutcome() {
        ArrayList<Appointment> appointments = new ArrayList<>(doctorService.getConfirmedAppointments());
        if (appointments.isEmpty()) {
            System.out.println("No appointments completed.");
            return;
        }
        System.out.println("Select completed appointment:");
        Appointment appointment = appointments.get(printAllAndChooseOne(appointments));

        System.out.println("Filling in details for the Appointment Outcome Record:");
        System.out.print("Enter service type: ");
        String serviceType = sc.nextLine();

        ArrayList<Prescription> prescriptions = new ArrayList<>();
        ArrayList<Medication> medications = new ArrayList<>(doctorService.getAllMedications());
        while (printAllAndChooseOne(List.of("Add prescriptions", "Continue")) == 0) {
            if (medications.isEmpty()) {
                System.out.println("No medications available.");
                break;
            }
            System.out.println("Prescription - Choose medication:");
            String medication = medications.get(printAllAndChooseOne(medications)).getMedicationName();
            while (!doctorService.isValidMedication(medication)) {
                System.out.println("Invalid medication name, try again.");
                medication = sc.nextLine();
            }
            System.out.print("Enter medication amount: ");
            int amount = Integer.parseInt(sc.nextLine());
            while (amount < 0) {
                System.out.println("Invalid medication amount, try again.");
                amount = Integer.parseInt(sc.nextLine());
            }
            prescriptions.add(new Prescription(doctorService.getNewAORId(), medication, amount));
            System.out.println("Prescription added.");
        }
        System.out.print("Enter consultation notes: ");
        String consultationNotes = sc.nextLine();

        AppointmentOutcomeRecord aor = new AppointmentOutcomeRecord(appointment.getAppointmentID(), appointment.getDate(), serviceType, prescriptions, consultationNotes);
        doctorService.completeAppointment(appointment.getAppointmentID(), aor);
        System.out.println("Appointment Outcome Record created.");
        System.out.println("Appointment completed.");
    }

    /**
     * Retrieves the DoctorService associated with the menu.
     *
     * @return the DoctorService instance.
     */
    @Override
    public DoctorService getUserService() {
        return doctorService;
    }
}
