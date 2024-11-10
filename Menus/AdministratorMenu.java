package Menus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Misc.Appointment;
import Misc.DateHelper;
import Misc.MedicalRecord;
import Misc.Medication;
import Misc.ReplenishmentRequest;
import Misc.RoleType;
import Service.AdministratorService;
import Users.Doctor;
import Users.Pharmacist;
import Users.User;

import java.util.Scanner;

public class AdministratorMenu extends BaseMenu<AdministratorService> {
    private final AdministratorService service;

    public AdministratorMenu(AdministratorService service) {
        this.service = service;
    }

    @Override
    public void baseMenu(String currentUserId, Scanner sc) {
        super.baseMenu(currentUserId,sc);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Administrator Menu\n---------------");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Log Out");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manageHospitalStaff();
                    break;

                case 2:
                    viewAppointmentDetails();
                    break;

                case 3:
                    manageHospitalMedication();
                    break;

                case 4:
                    approveReplenishmentRequests();
                    break;

                case 5:
                    System.out.println("Logging out...");
                    isRunning = false;
                    break;
                default:
                    break;
            }
        }

    }

    private void viewAppointmentDetails() {
        ArrayList<String> appts = service.viewAllAppointments();

        // Functionality for method, awaiting adminService viewAllAppts method to be completed

        // for (Appointment appt: appts) {
        //     System.out.println(appt.getDetails());
        // }
    }

    public void manageHospitalMedication() {
        Collection<Medication> meds = service.viewAllMedications();
        for (Medication med: meds) {
            System.out.println(med.getDetails());
        }
    }

    public void approveReplenishmentRequests() {
        System.out.println("Pending replenishment requests:\n");
        Collection<ReplenishmentRequest> requests = service.getReplenishmentRequests();

        if (requests.isEmpty()) {
            System.out.println("No pending replenishment requests");
            return;
        }

        List<ReplenishmentRequest> requestList = new ArrayList<>(requests);

        int index = 1;
        for (ReplenishmentRequest req : requestList) {
            System.out.printf("%d. %s\n", index++, req.getDetails());
        }

        System.out.print("Enter the number of the request you want to approve (or 0 to cancel): ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        // Validate the choice
        if (choice == 0) {
            System.out.println("Approval process canceled.");
            return;
        }

        if (choice < 1 || choice > requestList.size()) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        ReplenishmentRequest selectedReplenishmentRequest = requestList.get(choice-1);
        service.approveReplenishmentRequest(selectedReplenishmentRequest.getMedicationName());
        System.out.println("Replenishment request approved.");
    }

    private void addUser(RoleType roleToAdd){
        System.out.println("Adding " + roleToAdd);
        String id;
        while(true){
            System.out.println("Enter ID:");
            id = sc.nextLine();
            if(service.isIdAvailable(id))
                break;
            System.out.println("Id is already used, choose another one");
        }
        System.out.println("Enter Name:");
        String name = sc.nextLine();
        System.out.println("Enter Gender:");
        String gender = sc.nextLine();
        String dateOfBirth;
        while(true){
            System.out.println("Enter Date of Birth in format dd/MM/yyyy:");
            dateOfBirth = sc.nextLine();
            if(DateHelper.isValidDate(dateOfBirth)){
                break;
            }
            System.out.println("Invalid date");
        }
        System.out.println("Enter Email:");
        String email = sc.nextLine();
        System.out.println("Enter Contact Number:");
        String contact = sc.nextLine();
        switch(roleToAdd){
            case Doctor -> service.addNewUser(new Doctor(id,name,gender,dateOfBirth,email,contact));
            case Pharmacist -> service.addNewUser(new Pharmacist(id,name,gender,dateOfBirth,email,contact));
        }
        System.out.println(roleToAdd + " added successfully");
    }

    private void manageHospitalStaff() {
        System.out.println("Manage Hospital Staff\n---------------");
        System.out.println("1. Add Doctor");
        System.out.println("2. Add Pharmacist");
        System.out.println("3. Update Doctor");
        System.out.println("4. Update Pharmacist");
        System.out.println("5. Delete Doctor");
        System.out.println("6. Delete Pharmacist");
        System.out.println("7. Display Filtered Staff");
        System.out.println("8. Return to Main Menu");

        int staffChoice = sc.nextInt();
        sc.nextLine();  // Consume newline left-over

        switch (staffChoice) {
            case 1: // Add Doctor
                addUser(RoleType.Doctor);
                break;
            case 2: // Add Pharmacist
                addUser(RoleType.Pharmacist);
                break;
            case 3: // Update Doctor
                System.out.println("Enter Doctor ID to update:");
                String updateDocId = sc.nextLine();
                System.out.println("Enter updated Name:");
                String updateDocName = sc.nextLine();
                System.out.println("Enter updated Gender:");
                String updateDocGender = sc.nextLine();
                System.out.println("Enter updated Age:");
                String updateDocDateOfBirth = sc.nextLine();
                System.out.println("Enter updated Email:");
                String updateDocEmail = sc.nextLine();
                System.out.println("Enter updated Contact Number:");
                String updateDocContact = sc.nextLine();

                Doctor updatedDoctor = new Doctor(updateDocId, updateDocName, updateDocGender, updateDocDateOfBirth, updateDocEmail, updateDocContact);
                //administratorService.updateDoctor(updateDocId, updatedDoctor);
                System.out.println("Doctor updated successfully.");
                break;

            case 4: // Update Pharmacist
                System.out.println("Enter Pharmacist ID to update:");
                String updatePhId = sc.nextLine();
                System.out.println("Enter updated Name:");
                String updatePhName = sc.nextLine();
                System.out.println("Enter updated Gender:");
                String updatePhGender = sc.nextLine();
                System.out.println("Enter updated Age:");
                int updatePhAge = sc.nextInt();
                sc.nextLine();  // Consume newline left-over
                System.out.println("Enter updated Email:");
                String updatePhEmail = sc.nextLine();
                System.out.println("Enter updated Contact Number:");
                String updatePhContact = sc.nextLine();

                //Pharmacist updatedPharmacist = new Pharmacist(updatePhId, updatePhName, updatePhGender, updatePhAge, updatePhEmail, updatePhContact);
                //administratorService.updatePharmacist(updatePhId, updatedPharmacist);
                System.out.println("Pharmacist updated successfully.");
                break;

            case 5: // Delete Doctor
                System.out.println("Enter Doctor ID to delete:");
                String deleteDocId = sc.nextLine();
                service.removeUser(deleteDocId);
                System.out.println("Doctor deleted successfully.");
                break;

            case 6: // Delete Pharmacist
                System.out.println("Enter Pharmacist ID to delete:");
                String deletePhId = sc.nextLine();
                service.removeUser(deletePhId);
                System.out.println("Pharmacist deleted successfully.");
                break;

            case 7: // Display Filtered Staff
                System.out.println("Enter Role (doctor/pharmacist):");
                String role = sc.nextLine();
                System.out.println("Enter Gender (or leave blank for no filter):");
                String gender = sc.nextLine();
                System.out.println("Enter Name (or leave blank for no filter):");
                String name = sc.nextLine();
                System.out.println("Enter Minimum Age (or enter -1 for no minimum age filter):");
                int minAge = sc.nextInt();
                System.out.println("Enter Maximum Age (or enter -1 for no maximum age filter):");
                int maxAge = sc.nextInt();
                sc.nextLine();  // Consume the leftover newline

                for(User u : service.getFilteredStaff(role, gender, minAge, maxAge, name)){
                    System.out.println(u.toString());
                }
                break;

            case 8: // Return to Main Menu
                return;

            default:
                System.out.println("Invalid option, returning to Main Menu.");
                break;
        }
    }

    @Override
    public AdministratorService getUserService() {
        return service;
    }
}
