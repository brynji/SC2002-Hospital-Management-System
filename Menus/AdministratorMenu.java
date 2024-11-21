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

import java.util.List;
import java.util.Scanner;

/**
 * Menu class for admin-specific operations.
 * Extends BaseMenu to provide a menu-driven interface for administrator to manage staff and inventory.
 */
public class AdministratorMenu extends BaseMenu<AdministratorService> {
    /** The AdministratorService instance used for handling business logic and data operations. */
    private final AdministratorService service;

    /**
     * Constructs an AdministratorMenu with the given AdministratorService.
     *
     * @param service the AdministratorService instance to be used for admin-specific operations.
     */
    public AdministratorMenu(AdministratorService service) {
        this.service = service;
    }

    /**
     * Displays the main menu for administrators and handles user input to perform the corresponding operations.
     *
     * @param currentUserId the ID of the currently logged-in admin.
     * @param sc the Scanner instance for reading user input.
     */
    @Override
    public void baseMenu(String currentUserId, Scanner sc) {
        super.baseMenu(currentUserId,sc);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println();
            System.out.println("""
                Administrator Menu
                ------------------
                1. View and Manage Hospital Staff
                2. View Appointments Details
                3. View and Manage Medication Inventory
                4. Approve Replenishment Requests
                5. Log Out""");
            System.out.print("Enter your choice: ");
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

    /**
     * Displays details of selected appointment
     */
    private void viewAppointmentDetails() {
        Collection<String> appointments = service.viewAllAppointmentsInformation();
        if(appointments.isEmpty()){
            System.out.println("No appointments found");
            return;
        }
        System.out.println("Appointment details:");
        for (String appointment: appointments) {
            System.out.println(appointment);
            System.out.println();
        }
    }

    /**
     * Adds new medication to the inventory
     */
    private void addNewMedication(){
        System.out.print("Enter Medication Name: ");
        String medName = sc.nextLine();
        if(service.getMedication(medName) != null){
            System.out.println("Medication is already in inventory");
            return;
        }
        System.out.print("Enter Medication Stock Level: ");
        int stockLevel = sc.nextInt();
        System.out.print("Enter Medication Low Level Alert: ");
        int lowLevelAlert = sc.nextInt();
        sc.nextLine(); //consume new line

        service.addMedication(new Medication(medName, stockLevel, lowLevelAlert));
        System.out.println("Medication successfully added");
    }

    /**
     * Updates stock or low level alert of selected medication
     */
    private void updateMedication(){
        ArrayList<Medication> medications = new ArrayList<>(service.getAllMedications());
        if(medications.isEmpty()){
            System.out.println("No medications found");
            return;
        }
        int choice = printAllAndChooseOne(medications);
        String medicationName = medications.get(choice).getMedicationName();
        System.out.println("What do you want to update?");
        int choice2 = printAllAndChooseOne(List.of("Stock level", "Low level alert"));
        switch (choice2) {
            case 0:
                System.out.println("Enter new Stock Level: ");
                int newStockLevel = sc.nextInt();
                sc.nextLine();
                if(newStockLevel<0){
                    System.out.println("Invalid Stock Level");
                    return;
                }
                service.updateMedicationStock(medicationName, newStockLevel);
                break;
            case 1:
                System.out.print("Enter new Low Level Alert: ");
                int newLowLevelAlert = sc.nextInt();
                sc.nextLine();
                if(newLowLevelAlert<0){
                    System.out.println("Invalid Low Level Alert");
                    return;
                }
                service.updateMedicationAlertLevel(medicationName, newLowLevelAlert);
                break;
        }
        System.out.println("Medication successfully updated");
    }

    /**
     * Displays all medications in inventory
     */
    public void viewAllMedications(){
        Collection<Medication> medications = service.getAllMedications();
        if(medications.isEmpty()){
            System.out.println("No medications found");
            return;
        }
        System.out.println("Medication list:");
        for(Medication medication : medications){
            System.out.println(medication);
        }
    }

    /**
     * Displays selected replenishment request
     */
    public void viewReplenishmentRequests() {
        ArrayList<ReplenishmentRequest> requests = new ArrayList<>(service.getPendingReplenishmentRequests());
        if(requests.isEmpty()){
            System.out.println("No pending requests");
            System.out.println("Show all requests?");
            int choice = printAllAndChooseOne(List.of("yes","no"));
            if(choice == 0){
                System.out.println("All requests:");
                for(ReplenishmentRequest request : service.getReplenishmentRequests()){
                    System.out.println(request);
                }
            }
            return;
        }
        for(int i=0; i<requests.size(); i++){
            System.out.println("Request "+(i+1)+" of "+requests.size());
            System.out.println(requests.get(i));
            System.out.println("Approve?");
            int choice = printAllAndChooseOne(List.of("yes","no","back to menu"));
            if(choice == 0){
                service.approveReplenishmentRequest(requests.get(i));
                System.out.println("Replenishment request approved");
            } else if(choice == 1){
                service.denyReplenishmentRequest(requests.get(i));
                System.out.println("Replenishment request denied");
            } else {
                return;
            }
        }
    }

    /**
     * Lets user manage medication, this includes adding, updating, displaying all, accepting replenishment requests
     */
    private void manageHospitalMedication() {
        boolean goBack = false;
        while(!goBack) {
            System.out.println();
            System.out.println("""
                    Medication inventory
                    --------------------
                    1 Add new Medication
                    2 Update Medication
                    3 View all Medications
                    4 View Pending Replenishment Requests
                    5 Return to Main Menu""");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> addNewMedication();
                case 2 -> updateMedication();
                case 3 -> viewAllMedications();
                case 4 -> viewReplenishmentRequests();
                case 5 -> goBack = true;
            }
        }
    }

    /**
     * Adds new User
     * @param roleToAdd role of the user we want to add
     */
    private void addUser(RoleType roleToAdd){
        System.out.println("Adding " + roleToAdd);
        String id;
        while(true){
            System.out.print("Enter ID: ");
            id = sc.nextLine();
            if(service.isIdAvailable(id))
                break;
            System.out.println("Id is already used, choose another one");
        }
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Gender: ");
        String gender = sc.nextLine();
        String dateOfBirth;
        while(true){
            System.out.print("Enter Date of Birth in format dd/MM/yyyy: ");
            dateOfBirth = sc.nextLine();
            if(DateHelper.isValidDateOfBirth(dateOfBirth)){
                break;
            }
            System.out.println("Invalid date, try again");
        }
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine();
        switch(roleToAdd){
            case Doctor -> service.addNewUser(new Doctor(id,name,gender,dateOfBirth,email,contact,true));
            case Pharmacist -> service.addNewUser(new Pharmacist(id,name,gender,dateOfBirth,email,contact));
        }
        System.out.println(roleToAdd + " added successfully");
    }

    /**
     * Updates user
     * @param roleToUpdate role of the user to update
     */
    private void updateUser(RoleType roleToUpdate){
        ArrayList<User> users = new ArrayList<>(service.getAllUsersWithRole(roleToUpdate));
        if(users.isEmpty()){
            System.out.println("No users found");
            return;
        }
        System.out.println("Which "+roleToUpdate+" do you want to update?");
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
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Consume newline left-over
            switch(choice){
                case 1:
                    System.out.print("Enter new Name: ");
                    String name = sc.nextLine();
                    userToUpdate.setName(name);
                    break;
                case 2:
                    System.out.print("Enter new Gender: ");
                    String gender = sc.nextLine();
                    userToUpdate.setGender(gender);
                    break;
                case 3:
                    String dateOfBirth;
                    while(true){
                        System.out.print("Enter new Date of Birth in format dd/MM/yyyy: ");
                        dateOfBirth = sc.nextLine();
                        if(DateHelper.isValidDateOfBirth(dateOfBirth)){
                            break;
                        }
                        System.out.println("Invalid date");
                    }
                    userToUpdate.setDateOfBirth(dateOfBirth);
                    break;
                case 4:
                    System.out.print("Enter new Email: ");
                    String email = sc.nextLine();
                    userToUpdate.setEmail(email);
                    break;
                case 5:
                    System.out.print("Enter new Contact Number: ");
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

    /**
     * Deletes selected staff member
     * @param roleToDelete role of the member to delete
     */
    private void deleteUser(RoleType roleToDelete){
        ArrayList<User> users = new ArrayList<>(service.getAllUsersWithRole(roleToDelete));
        if(users.isEmpty()){
            System.out.println("No users found");
            return;
        }
        System.out.println("Which "+roleToDelete+" do you want to delete?");
        int choice = printAllAndChooseOne(users);
        User userToDelete = users.get(choice);
        service.removeUser(userToDelete.getUserID());
        System.out.println(roleToDelete + " removed successfully");
    }

    /**
     * Displays all staff with selected filters
     */
    private void displayFilteredStaff(){
        System.out.print("Enter Role (doctor/pharmacist/all): ");
        String role = sc.nextLine();
        System.out.print("Enter Gender (or leave blank for no filter): ");
        String gender = sc.nextLine();
        System.out.print("Enter Name (or leave blank for no filter): ");
        String name = sc.nextLine();
        System.out.print("Enter Minimum Age (or -1 for no minimum age filter): ");
        int minAge = sc.nextInt();
        System.out.print("Enter Maximum Age (or -1 for no maximum age filter): ");
        int maxAge = sc.nextInt();
        sc.nextLine();  // Consume the leftover newline

        Collection<User> filteredStaff = service.getFilteredStaff(role, gender, minAge, maxAge, name);
        if(filteredStaff.isEmpty()){
            System.out.println("No staff found");
            return;
        }
        System.out.println("Staff list:");
        for(User u : filteredStaff){
            System.out.println(u.toString());
        }
    }

    /**
     * Allows user to manage hospital staff, this includes adding new, updating, deleting and displaying
     */
    private void manageHospitalStaff() {
        boolean goBack = false;
        while(!goBack){
            System.out.println();
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
            System.out.print("Enter your choice: ");
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

    /**
     * Retrieves the DoctorService associated with the menu
     * @return the DoctorService instance.
     */
    @Override
    public AdministratorService getUserService() {
        return service;
    }
}
