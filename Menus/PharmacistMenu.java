package Menus;

import Service.PharmacistService;
import Misc.AppointmentOutcomeRecord;
import Misc.Medication;

import java.util.Collection;

import javax.naming.InsufficientResourcesException;

public class PharmacistMenu extends BaseMenu<PharmacistService> {
    private final PharmacistService pharmacistService;

    public PharmacistMenu(String userId) {
        pharmacistService = new PharmacistService(userId);
    }

    @Override
    public void baseMenu() {
        super.baseMenu();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Pharmacist Menu\n--------------");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");

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

    public void viewAppointmentOutcomeRecord() {
        System.out.println("Please enter the patient's ID:\n");
        String patientId = sc.nextLine();
        sc.nextLine();

        Collection<AppointmentOutcomeRecord> aors = pharmacistService.getAppointmentOutcomeRecords(patientId);
        for (AppointmentOutcomeRecord aor: aors) {
            System.out.println(aor.getDetails());
        }
    }

    public void updatePrescriptionStatus() {
        System.out.println("Please enter the patient's ID:\n");
        String patientId = sc.nextLine();
        sc.nextLine();

        System.out.println("Please enter the prescription's ID:\n");
        String presId = sc.nextLine();
        sc.nextLine();

        try {
            pharmacistService.dispenseMedication(patientId, presId);
        } catch (IllegalArgumentException | InsufficientResourcesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Medication successfully dispensed.");
    }

    public void viewMedicationInventory() {
        Collection<Medication> meds = pharmacistService.getAllMedication();
        for (Medication med: meds) {
            String details = med.getDetails();
            System.out.println(details);
        }
    }

    public void submitReplenishmentRequest() {
        System.out.println("Enter medication name:\n");
        String medName = sc.nextLine();
        sc.nextLine();

        System.out.println("Enter medication amount needed:\n");
        int medAmount = sc.nextInt();
        sc.nextLine();

        pharmacistService.submitReplenishmentRequest(medName, medAmount);
        System.out.println("Replenishment request submitted");
    }


    @Override
    public PharmacistService getUserService() {
        return pharmacistService;
    }
}
