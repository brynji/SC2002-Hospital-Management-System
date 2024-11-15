package Menus;

import java.util.ArrayList;
import java.util.Collection;

import Misc.DateHelper;
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
            System.out.println("""
                Administrator Menu
                ------------------
                1. View and Manage Hospital Staff
                2. View Appointments Details
                3. View and Manage Medication Inventory
                4. Approve Replenishment Requests
                5. Log Out""");

            int choice = sc.nextInt();
            sc.nextLine(); //get the newline

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
                    viewReplenishmentRequests();
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
        Collection<String> appointments = service.viewAllAppointmentsInformation();
        for (String appointment: appointments) {
            System.out.println(appointment);
        }
    }

    private void addNewMedication(){
        System.out.println("Enter Medication Name: ");
        String medName = sc.nextLine();
        if(service.getMedication(medName) != null){
            System.out.println("Medication is already in inventory");
            return;
        }
        System.out.println("Enter Medication Stock Level: ");
        int stockLevel = sc.nextInt();
        System.out.println("Enter Medication Low Level Alert: ");
        int lowLevelAlert = sc.nextInt();
        sc.nextLine(); //consume new line

        service.addMedication(new Medication(medName, stockLevel, lowLevelAlert));
        System.out.println("Medication successfully added");
    }

    private void updateMedication(){
        ArrayList<Medication> medications = new ArrayList<>(service.getAllMedications());
        int choice = printAllAndChooseOne(medications);
        String medicationName = medications.get(choice).getMedicationName();
        System.out.println("""
                What do you want to update?
                1 Stock level
                2 Low level alert""");
        int choice2 = sc.nextInt();
        switch (choice2) {
            case 1:
                System.out.println("Enter new Stock Level: ");
                int newStockLevel = sc.nextInt();
                if(newStockLevel<0){
                    System.out.println("Invalid Stock Level");
                    break;
                }
                service.updateMedicationStock(medicationName, newStockLevel);
                break;
            case 2:
                System.out.println("Enter new Low Level Alert: ");
                int newLowLevelAlert = sc.nextInt();
                if(newLowLevelAlert<0){
                    System.out.println("Invalid Low Level Alert");
                    break;
                }
                service.updateMedicationAlertLevel(medicationName, newLowLevelAlert);
                break;
        }
        sc.nextLine();
        System.out.println("Medication successfully updated");
    }

    public void viewAllMedications(){
        for(Medication medication : service.getAllMedications()){
            System.out.println(medication);
        }
    }

    public void viewReplenishmentRequests() {
        ArrayList<ReplenishmentRequest> requests = new ArrayList<>(service.getPendingReplenishmentRequests());
        if(requests.isEmpty()){
            System.out.println("No pending requests");
            System.out.println("Show all requests? y/n");
            char choice = sc.next().charAt(0);
            if(choice == 'y'){
                System.out.println("All requests:");
                for(ReplenishmentRequest request : service.getReplenishmentRequests()){
                    System.out.println(request);
                }
            }
            return;
        }
        for(int i=0; i<requests.size(); i++){
            System.out.println("Request "+i+" of "+requests.size());
            System.out.println(requests.get(i));
            System.out.println("Approve? y/n (q for quit)");
            char choice = sc.next().charAt(0);
            while(choice != 'y' && choice != 'n' && choice != 'q'){
                System.out.println("Invalid Choice, try again");
                choice = sc.next().charAt(0);
            }
            if(choice == 'y'){
                service.approveReplenishmentRequest(requests.get(i));
            } else if(choice == 'n'){
                service.denyReplenishmentRequest(requests.get(i));
            } else {
                return;
            }
        }
    }

    private void manageHospitalMedication() {
        boolean goBack = false;
        while(!goBack) {
            System.out.println("""
                    Medication inventory
                    --------------------
                    1 Add new Medication
                    2 Update Medication
                    3 View all Medications
                    4 View Pending Replenishment Requests
                    5 Return to Main Menu""");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> addNewMedication();
                case 2 -> updateMedication();
                case 3 -> viewAllMedications();
                case 4 -> viewReplenishmentRequests();
                case 5 -> goBack = true;
            }
        }
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
            case Doctor -> service.addNewUser(new Doctor(id,name,gender,dateOfBirth,email,contact,true));
            case Pharmacist -> service.addNewUser(new Pharmacist(id,name,gender,dateOfBirth,email,contact));
        }
        System.out.println(roleToAdd + " added successfully");
    }

    private void updateUser(RoleType roleToUpdate){
        ArrayList<User> users = new ArrayList<>(service.getAllUsersWithRole(roleToUpdate));
        System.out.println("Which "+roleToUpdate+" do you want to update:");
        int choice = printAllAndChooseOne(users);
        User userToUpdate = users.get(choice);
        boolean updatingFinished = false;
        while(!updatingFinished){
            System.out.println("Updating "+roleToUpdate+" "+userToUpdate.toString());
            System.out.println("""
                    What do you want to update?
                    1 Name
                    2 Gender
                    3 Date of Birth
                    4 Email
                    5 Contact Number
                    6 Save changes""");
            choice = sc.nextInt();
            sc.nextLine();  // Consume newline left-over
            switch(choice){
                case 1:
                    System.out.println("Enter new Name:");
                    String name = sc.nextLine();
                    userToUpdate.setName(name);
                    break;
                case 2:
                    System.out.println("Enter new Gender:");
                    String gender = sc.nextLine();
                    userToUpdate.setGender(gender);
                    break;
                case 3:
                    String dateOfBirth;
                    while(true){
                        System.out.println("Enter new Date of Birth in format dd/MM/yyyy:");
                        dateOfBirth = sc.nextLine();
                        if(DateHelper.isValidDate(dateOfBirth)){
                            break;
                        }
                        System.out.println("Invalid date");
                    }
                    userToUpdate.setDateOfBirth(dateOfBirth);
                    break;
                case 4:
                    System.out.println("Enter new Email:");
                    String email = sc.nextLine();
                    userToUpdate.setEmail(email);
                    break;
                case 5:
                    System.out.println("Enter new Contact Number:");
                    String contact = sc.nextLine();
                    userToUpdate.setContactNumber(contact);
                    break;
                default:
                    updatingFinished = true;
            }
        }
        service.getRepository().save();
        System.out.println(roleToUpdate + " updated successfully");
    }

    private void deleteUser(RoleType roleToDelete){
        ArrayList<User> users = new ArrayList<>(service.getAllUsersWithRole(roleToDelete));
        System.out.println("Which "+roleToDelete+" do you want to delete:");
        int choice = printAllAndChooseOne(users);
        User userToDelete = users.get(choice);
        service.removeUser(userToDelete.getUserID());
        System.out.println(roleToDelete + " removed successfully");
    }

    private void displayFilteredStaff(){
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
    }

    private void manageHospitalStaff() {
        boolean goBack = false;
        while(!goBack){
            System.out.println("""
                Manage Hospital Staff
                ---------------------
                1. Add Doctor
                2. Add Pharmacist
                3. Update Doctor
                4. Update Pharmacist
                5. Delete Doctor
                6. Delete Pharmacist
                7. Display Filtered Staff
                8. Return to Main Menu""");

            int staffChoice = sc.nextInt();
            sc.nextLine();  // Consume newline left-over

            switch (staffChoice) {
                case 1 -> addUser(RoleType.Doctor);
                case 2 -> addUser(RoleType.Pharmacist);
                case 3 -> updateUser(RoleType.Doctor);
                case 4 -> updateUser(RoleType.Pharmacist);
                case 5 -> deleteUser(RoleType.Doctor);
                case 6 -> deleteUser(RoleType.Pharmacist);
                case 7 -> displayFilteredStaff();
                case 8 -> goBack = true;
            }
        }
    }

    @Override
    public AdministratorService getUserService() {
        return service;
    }
}
