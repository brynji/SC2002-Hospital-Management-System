package Menus;

import Service.PatientService;
import Users.Doctor;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collection;
import Misc.*;

public class PatientMenu extends BaseMenu<PatientService>{

    private final PatientService patientService;

    public PatientMenu(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public void baseMenu(String currentUserId, Scanner sc){
        super.baseMenu(currentUserId,sc);
        boolean isRunning = true;
        while (isRunning){
            System.out.println("""
                Patient menu
                ------------
                1 Change passwd
                Patient Menu:
                2. View Medical Record
                3. Update Personal Information
                4. View Available Appointment Slots
                5. Schedule an Appointment
                6. Reschedule an Appointment
                7. Cancel an Appointment
                8. View Scheduled Appointments
                9. View Past Appointment Outcome Records
                10. Logout""");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consumes newline character

            switch (choice) {
                case 1:
                    changePassword();
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
        for(AppointmentOutcomeRecord aor : patientService.getAppointmentOutcomeRecords()){
            System.out.println(aor);
        }
    }

    public void viewAvailableAppointmentSlots() {
        System.out.println("Choose doctor you want to visit");
        ArrayList<Doctor> doctors = new ArrayList<>(patientService.getAllDoctors());
        int doctorChoice = printAllAndChooseOne(doctors);
        System.out.println("Available dates and times:");
        for(DateTimeslot dt : patientService.getFreeTimeslots(doctors.get(doctorChoice).getUserID())){
            System.out.println(dt);
        }
    }

    public void scheduleAppointment(){
        System.out.println("Choose doctor you want to visit");
        ArrayList<Doctor> doctors = new ArrayList<>(patientService.getAllDoctors());
        int doctorChoice = printAllAndChooseOne(doctors);
        System.out.println("Available dates and times:");
        ArrayList<DateTimeslot> times = new ArrayList<>(patientService.getFreeTimeslots(doctors.get(doctorChoice).getUserID()));
        int timeslotChoice = printAllAndChooseOne(times);

        patientService.addNewAppointment(doctors.get(doctorChoice).getUserID(),
                times.get(timeslotChoice).getDate(),times.get(timeslotChoice).getTimeslot());
        System.out.println("Successfully booked doctor "+doctors.get(doctorChoice).getName()+" on "
                +times.get(timeslotChoice).getDate() + ", time " + times.get(timeslotChoice).getTimeslot());
    }

    public void rescheduleAppointment() {
        ArrayList<Appointment> appointments = new ArrayList<>(patientService.getUpcomingAppointments());
        if(appointments.isEmpty()){
            System.out.println("No upcoming appointments to reschedule.");
            return;
        }
        System.out.println("Choose appointment to reschedule:");
        Appointment appointment = appointments.get(printAllAndChooseOne(appointments));

        ArrayList<DateTimeslot> availableSlots = new ArrayList<>(patientService.getFreeTimeslots(appointment.getDoctorId()));
        if(availableSlots.isEmpty()){
            System.out.println("Doctor doesn't have any other free timeslots in the following 7 days");
            return;
        }
        DateTimeslot newDateTimeslot = availableSlots.get(printAllAndChooseOne(availableSlots));

        patientService.rescheduleAppointment(appointment.getAppointmentID(), newDateTimeslot);
        System.out.println("Your appointment has been rescheduled.\n");
    }

    public void cancelAppointment() {
        ArrayList<Appointment> appointments = new ArrayList<>(patientService.getUpcomingAppointments());
        if(appointments.isEmpty()){
            System.out.println("No upcoming appointments, nothing to cancel");
            return;
        }
        System.out.println("Choose appointment you want to cancel");
        int choice = printAllAndChooseOne(appointments);
        patientService.removeAppointment(appointments.get(choice).getAppointmentID());
        System.out.println("Your appointment has been cancelled.");
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
            System.out.println(AOR.toString());
        }
    }

    @Override
    public PatientService getUserService() {
        return patientService;
    }
}
