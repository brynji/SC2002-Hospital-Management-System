package Menus;

import Service.PatientService;
import Users.Doctor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import Misc.*;

public class PatientMenu extends BaseMenu<PatientService>{

    private final PatientService patientService;

    public PatientMenu(String currentUserId) {
        patientService = new PatientService(currentUserId);
    }

    @Override
    public void baseMenu(){
        super.baseMenu();
        boolean isRunning = true;
        while (isRunning){
            System.out.println("Patient menu \n --------------");
            System.out.println("1 Change passwd");
            System.out.println("Patient Menu:");
            System.out.println("2. View Medical Record");
            System.out.println("3. Update Personal Information");
            System.out.println("4. View Available Appointment Slots");
            System.out.println("5. Schedule an Appointment");
            System.out.println("6. Reschedule an Appointment");
            System.out.println("7. Cancel an Appointment");
            System.out.println("8. View Scheduled Appointments");
            System.out.println("9. View Past Appointment Outcome Records");
            System.out.println("10. Logout");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consumes newline character

            switch (choice) {
                case 1: 
                    changePassword(); // TODO: implement logic
                case 2:
                    viewMedicalRecord();
                    break;
                case 3:
                    updatePersonalInformation();
                    break;
                case 4:
                    viewAvailableAppointmentSlots();
                    break;
                case 5:
                    scheduleAppointment();
                    break;
                case 6:
                    rescheduleAppointment();
                    break;
                case 7:
                    cancelAppointment();
                    break;
                case 8:
                    viewUpcomingAppointments();
                    break;
                case 9:
                    viewPastAppointmentOutcomeRecords();
                    break;
                case 10:
                    System.out.println("Logging out...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    public void viewMedicalRecord() {
        System.out.println("Your medical record:");
        String details = patientService.getCurrentUser().getMedicalRecord().getDetails();
        System.out.println(details);
    }


    public void updatePersonalInformation() {
        System.out.printf("Your current email is %s\n", patientService.getEmail());
        System.out.println("Do you want to update your email?\n 1. Yes\n 2. No");
        if (sc.nextInt() == 1) {
            System.out.println("Please enter your new email address:"); 
            String newEmail = sc.nextLine();
            sc.nextLine(); // Clears newline character
            if (newEmail.equals(patientService.getEmail())) {
                System.out.println("Your email is the same. No change is required.");
            }
            else {
                patientService.updateEmail(newEmail);
                System.out.println("Your email has been updated");
            }
        }

        System.out.printf("Your current contact number is %s\n", patientService.getContactNumber());
        System.out.println("Do you want to update your contact number?\n 1. Yes\n 2. No");
        if (sc.nextInt() == 1) {
            System.out.println("Please enter your new contact number"); 
            String newContactNumber = sc.nextLine();
            sc.nextLine();
            if (newContactNumber.equals(patientService.getContactNumber())) {
                System.out.println("Your contact number is the same. No change is required.");
            }
            else {
                patientService.updateContactNumber(newContactNumber);
                System.out.println("Your contact number has been updated");
            }

        }

        System.out.printf("Your email is %s and your contact number is %s", patientService.getEmail(), patientService.getContactNumber());

    }

    // TODO: Change to ask for doctor's name instead of ID
    // The method asks for doc ID, which patient may not know. Should we change it to ask for doctor's name?
    public void viewAvailableAppointmentSlots() {
        System.out.println("Please enter the ID of your doctor:\n");
        String docId = sc.nextLine();
        sc.nextLine();

        Collection <DateTimeslot> availableTimeslots = patientService.getFreeTimeslots(docId);
        for (DateTimeslot timeslot: availableTimeslots) {
            System.out.println(timeslot.getDate());
            System.out.println(timeslot.getTimeslot());
        }

    }

    public void scheduleAppointment(){
        System.out.println("Choose doctor you want to visit");
        ArrayList<Doctor> doctors = new ArrayList<>(patientService.getAllDoctors());
        for(int i=0; i<doctors.size(); i++){
            System.out.println(i + ": " + doctors.get(i).getName());
        }
        int doctorChoice = sc.nextInt();
        System.out.println("Available dates and times:");
        ArrayList<DateTimeslot> times = new ArrayList<>(patientService.getFreeTimeslots(doctors.get(doctorChoice).getUserID()));
        for(int i=0; i<times.size(); i++){
            System.out.println(i + ": " + times.get(i).getDate() + " " + times.get(i).getTimeslot());
        }
        int timeslotChoice = sc.nextInt();
        patientService.addNewAppointment(doctors.get(doctorChoice).getUserID(),
                times.get(timeslotChoice).getDate(),times.get(timeslotChoice).getTimeslot());
        System.out.println("Successfully booked doctor "+doctors.get(doctorChoice).getName()+" on "
                +times.get(timeslotChoice).getDate() + ", time " + times.get(timeslotChoice).getTimeslot());
    }

    // TODO: Check how the user can get their new appt ID
    public void rescheduleAppointment() {
        System.out.println("Please enter your current appointment's ID:\n");
        String apptID = sc.nextLine();
        sc.nextLine();

        System.out.println("Please enter the date of your new appointment in the format of YYYY-MM-DD:\n");
        String newDateString = sc.nextLine();
        sc.nextLine();
        LocalDate newDate = LocalDate.parse(newDateString);

        System.out.println("Please choose the start time of your new appointment (in 24hr format):\n");
        int newTimeslotInt = sc.nextInt();
        sc.nextLine();
        Timeslot newTimeslot = null;
        
        switch (newTimeslotInt) {
            case 900: newTimeslot = Timeslot.SLOT_0900;
            case 1000: newTimeslot =  Timeslot.SLOT_1000;
            case 1100: newTimeslot =  Timeslot.SLOT_1100;
            case 1200: newTimeslot =  Timeslot.SLOT_1200;
            case 1300: newTimeslot =  Timeslot.SLOT_1300;
            case 1400: newTimeslot =  Timeslot.SLOT_1400;
            case 1500: newTimeslot =  Timeslot.SLOT_1500;
            case 1600: newTimeslot =  Timeslot.SLOT_1600;
            case 1700: newTimeslot =  Timeslot.SLOT_1700;
            default: newTimeslot =  null; // If input is not a valid time
        }

        patientService.rescheduleAppointment(apptID, newDate, newTimeslot);
        System.out.println("Your appointment has been rescheduled.\n");
    }

    public void cancelAppointment() {
        System.out.println("Please enter your appointment ID:\n");
        String apptID = sc.nextLine();
        sc.nextLine();

        patientService.removeAppointment(apptID);
        System.out.println("Your appointment has been removed.");
    }

    public void viewUpcomingAppointments(){
        System.out.println("Your upcoming appointments:");
        for(var app : patientService.getUpcomingAppointments()){
            System.out.println(patientService.getDoctorName(app.getDoctorId())+" "+app.getDate()+" "+app.getTime());
        }
    }

    public void viewPastAppointmentOutcomeRecords() {
        System.out.println("All past appointment records:");
        Collection <AppointmentOutcomeRecord> AORs = patientService.getAppointmentOutcomeRecords();
        for (AppointmentOutcomeRecord AOR: AORs) {
            System.out.println(AOR.getDetails());
        }
    }

    @Override
    public PatientService getUserService() {
        return patientService;
    }
}
