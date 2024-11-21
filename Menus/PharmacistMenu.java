package Menus;

import Misc.Prescription;
import Service.PharmacistService;
import Misc.AppointmentOutcomeRecord;
import Misc.Medication;
import Users.Patient;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InsufficientResourcesException;

import java.util.List;
import java.util.Scanner;

/**
 * Menu class for pharmacist-specific operations.
 * Extends BaseMenu to provide a menu-driven interface for pharmacists to manage medications, prescriptions, and inventory.
 */
public class PharmacistMenu extends BaseMenu<PharmacistService> {

    /** The PharmacistService instance used for handling pharmacist-specific business logic and data operations. */
    private final PharmacistService pharmacistService;

    /**
     * Constructs a PharmacistMenu with the given PharmacistService.
     *
     * @param pharmacistService the PharmacistService instance to be used for pharmacist-specific operations.
     */
    public PharmacistMenu(PharmacistService pharmacistService) {
        this.pharmacistService = pharmacistService;
    }

    /**
     * Displays the main menu for pharmacists and handles user input to perform corresponding operations.
     *
     * @param currentUserId the ID of the currently logged-in pharmacist.
     * @param sc the Scanner instance for reading user input.
     */
    @Override
    public void baseMenu(String currentUserId, Scanner sc) {
        super.baseMenu(currentUserId,sc);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println();
            System.out.println("""
                Pharmacist Menu
                ---------------
                1. View Appointment Outcome Record
                2. Update Prescription Status
                3. View Medication Inventory
                4. Submit Replenishment Request
                5. Logout""");

            System.out.print("Enter your choice: ");
            int choice = nextInt();

            switch (choice) {
                case 1 -> viewAppointmentOutcomeRecord();
                case 2 -> updatePrescriptionStatus();
                case 3 -> viewMedicationInventory();
                case 4 -> submitReplenishmentRequest();
                case 5 -> {
                    System.out.println("Logging out...");
                    isRunning = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Allows the pharmacist to choose or input a patient ID.
     *
     * @return the selected or entered patient ID, or null if no valid ID is provided.
     */
    private String chooseOrInputPatientId() {
        String patientId;
        System.out.println("Do you want to choose your patient from list or input patient Id?");
        int choice = printAllAndChooseOne(List.of("choose from list","input"));
        if (choice == 0) {
            ArrayList<Patient> patients = new ArrayList<>(pharmacistService.getAllPatients());
            if(patients.isEmpty()) {
                System.out.println("No patients found.");
                return null;
            }
            int chosenPatient = printAllAndChooseOne(patients);
            patientId = patients.get(chosenPatient).getUserID();
        } else {
            System.out.print("Enter patient ID: ");
            patientId = sc.nextLine();
            if(!pharmacistService.isValidPatientId(patientId)) {
                System.out.println("Invalid patientId");
                patientId = null;
            }
        }
        return patientId;
    }

    /**
     * Displays appointment outcome records for a selected patient.
     */
    public void viewAppointmentOutcomeRecord() {
        String patientId = chooseOrInputPatientId();
        if(patientId == null) {
            return;
        }

        Collection<AppointmentOutcomeRecord> aors = pharmacistService.getAppointmentOutcomeRecords(patientId);
        if(aors.isEmpty()) {
            System.out.println("No appointment outcome records found");
            return;
        }
        for (AppointmentOutcomeRecord aor: aors) {
            System.out.println(aor.toString()+"\n");
        }
    }

    /**
     * Updates the prescription status for a selected patient.
     */
    public void updatePrescriptionStatus() {
        String patientId = chooseOrInputPatientId();
        if(patientId == null) {
            return;
        }
        ArrayList<Prescription> prescriptions = new ArrayList<>(pharmacistService.getAllPatientsPendingPrescriptions(patientId));
        if(prescriptions.isEmpty()) {
            System.out.println("No prescriptions for this patient");
            return;
        }
        System.out.println("Please choose the prescription you want to update:");
        String presId = prescriptions.get(printAllAndChooseOne(prescriptions)).getPrescriptionID();

        try {
            pharmacistService.dispenseMedication(patientId, presId);
        } catch (InsufficientResourcesException e) {
            System.out.println("Not enough medication in stock. Please try again later.");
            return;
        }
        System.out.println("Medication successfully dispensed.");
    }

    /**
     * Displays the medication inventory.
     */
    public void viewMedicationInventory() {
        Collection<Medication> meds = pharmacistService.getAllMedication();
        if(meds.isEmpty()) {
            System.out.println("No medications found");
            return;
        }
        System.out.println("Medication list:");
        for (Medication med: meds) {
            System.out.println(med.toString());
        }
    }

    /**
     * Submits a replenishment request for medications with low stock.
     */
    public void submitReplenishmentRequest() {
        ArrayList<Medication> medicationsWithLowStock = new ArrayList<>(pharmacistService.getAllMedicationWithLowAlert());
        if(medicationsWithLowStock.isEmpty()) {
            System.out.println("No medications with low stock found, nothing to replenish");
            return;
        }
        System.out.println("What medication do you want to request?:");
        String medName = medicationsWithLowStock.get(printAllAndChooseOne(medicationsWithLowStock)).getMedicationName();

        System.out.print("Enter medication amount needed: ");
        int medAmount = nextInt();
        while(medAmount<0){
            System.out.println("Invalid amount, try again");
            medAmount = nextInt();
        }

        pharmacistService.submitReplenishmentRequest(medName, medAmount);
        System.out.println("Replenishment request submitted.");
    }

    /**
     * Retrieves the PharmacistService associated with the menu.
     *
     * @return the PharmacistService instance.
     */
    @Override
    public PharmacistService getUserService() {
        return pharmacistService;
    }
}