package Menus;

import Misc.Prescription;
import Service.PharmacistService;
import Misc.AppointmentOutcomeRecord;
import Misc.Medication;
import Users.Patient;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InsufficientResourcesException;

import java.util.Scanner;

public class PharmacistMenu extends BaseMenu<PharmacistService> {
    private final PharmacistService pharmacistService;

    public PharmacistMenu(PharmacistService pharmacistService) {
        this.pharmacistService = pharmacistService;
    }

    @Override
    public void baseMenu(String currentUserId, Scanner sc) {
        super.baseMenu(currentUserId,sc);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""
                Pharmacist Menu
                ---------------
                1. View Appointment Outcome Record
                2. Update Prescription Status
                3. View Medication Inventory
                4. Submit Replenishment Request
                5. Logout""");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecord();
                    break;
                case 2:
                    updatePrescriptionStatus();
                    break;
                case 3:
                    viewMedicationInventory();
                    break;
                case 4:
                    submitReplenishmentRequest();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private String chooseOrInputPatientId(){
        String patientId;
        System.out.println("""
            Do you want to choose your patient from list or input patient Id?
            1 choose from list
            2 input id""");
        int choice = sc.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid choice. Please try again.");
            choice = sc.nextInt();
        }
        if (choice == 1) {
            ArrayList<Patient> patients = new ArrayList<>(pharmacistService.getAllPatients());
            int chosenPatient = printAllAndChooseOne(patients);
            patientId = patients.get(chosenPatient).getUserID();
        } else {
            patientId = sc.nextLine();
            if(!pharmacistService.isValidPatientId(patientId)) {
                System.out.println("Invalid patientId");
                patientId = null;
            }
        }
        return patientId;
    }

    public void viewAppointmentOutcomeRecord() {
        String patientId = chooseOrInputPatientId();
        if(patientId == null) {
            return;
        }

        Collection<AppointmentOutcomeRecord> aors = pharmacistService.getAppointmentOutcomeRecords(patientId);
        for (AppointmentOutcomeRecord aor: aors) {
            System.out.println(aor.toString());
        }
    }

    public void updatePrescriptionStatus() {
        String patientId = chooseOrInputPatientId();
        if(patientId == null) {
            return;
        }

        System.out.println("Please choose the prescription you want to update:\n");
        ArrayList<Prescription> prescriptions = new ArrayList<>(pharmacistService.getAllPatientsPendingPrescriptions(patientId));
        String presId = prescriptions.get(printAllAndChooseOne(prescriptions)).getPrescriptionID();

        try {
            pharmacistService.dispenseMedication(patientId, presId);
        } catch (InsufficientResourcesException e) {
            System.out.println("Not enough medication in stock. Please try again later.");
            return;
        }
        System.out.println("Medication successfully dispensed.");
    }

    public void viewMedicationInventory() {
        Collection<Medication> meds = pharmacistService.getAllMedication();
        for (Medication med: meds) {
            System.out.println(med.getDetails());
        }
    }

    public void submitReplenishmentRequest() {
        System.out.println("What medication do you want to request?:\n");
        ArrayList<Medication> medicationsWithLowStock = new ArrayList<>(pharmacistService.getAllMedicationWithLowAlert());
        String medName = medicationsWithLowStock.get(printAllAndChooseOne(medicationsWithLowStock)).getMedicationName();

        System.out.println("Enter medication amount needed:\n");
        int medAmount = sc.nextInt();
        while(medAmount<0){
            System.out.println("Invalid amount, try again");
            medAmount = sc.nextInt();
        }
        sc.nextLine();

        pharmacistService.submitReplenishmentRequest(medName, medAmount);
        System.out.println("Replenishment request submitted");
    }


    @Override
    public PharmacistService getUserService() {
        return pharmacistService;
    }
}
