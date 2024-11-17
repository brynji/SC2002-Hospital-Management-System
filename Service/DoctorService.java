package Service;

import Data.DoctorRepository;
import Misc.*;
import Users.Doctor;
import Users.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class DoctorService extends UserService<Doctor, DoctorRepository> {
    private final DoctorRepository repository;
    private Doctor currentUser;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public Collection<Patient> getAllPatientsInCare(){
        var appointments = repository.getAllAppointmentsFromIds(currentUser.getAppointments());
        return appointments.stream().map(appointment->repository.<Patient>findUserById(appointment.getPatientId(),RoleType.Patient))
                .distinct().toList();
    }

    public Collection<Appointment> getAllPendingAppointments(){
        return repository.getAllAppointmentsFromIds(currentUser.getAppointments()).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.PENDING).sorted().toList();
    }

    public Collection<Appointment> getConfirmedAppointments(){
        return repository.getAllAppointmentsFromIds(currentUser.getAppointments()).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.CONFIRMED).sorted().toList();
    }

    public Collection<Medication> getAllMedications(){
        return repository.getInventory().getAllMedications();
    }

    public void acceptAppointment(String appointmentId){
        if(!currentUser.getAppointments().contains(appointmentId))
            throw new IllegalArgumentException("No appointment with given id for current user");
        Appointment appointment =  repository.getAppointmentById(appointmentId);
        if(appointment.getStatus() != AppointmentStatus.PENDING)
            throw new IllegalStateException("Appointment is not pending - state cannot be changed");
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        repository.save();
    }

    public void declineAppointment(String appointmentId){
        if(!currentUser.getAppointments().contains(appointmentId))
            throw new IllegalArgumentException("No appointment with given id for current user");
        Appointment appointment =  repository.getAppointmentById(appointmentId);
        if(appointment.getStatus() != AppointmentStatus.PENDING)
            throw new IllegalStateException("Appointment is not pending - state cannot be changed");
        appointment.setStatus(AppointmentStatus.REJECTED);
        repository.save();
    }

    public void completeAppointment(String appointmentId, AppointmentOutcomeRecord aor){
        if(!currentUser.getAppointments().contains(appointmentId))
            throw new IllegalArgumentException("No appointment with given id for current user");

        if(aor.getPrescriptions().isEmpty()){
            aor.setStatus("dispensed");
        }

        Appointment appointment = repository.getAppointmentById(appointmentId);
        appointment.setAOR(aor);
        appointment.setStatus(AppointmentStatus.COMPLETED);

        Patient patient = repository.findUserById(appointment.getPatientId(), RoleType.Patient);
        patient.getMedicalRecord().AddPastAppointment(aor.getRecordID());

        repository.save();
    }

    public void addNewDiagnosis(String patientId, String diagnosis){
        repository.<Patient>findUserById(patientId, RoleType.Patient)
                .getMedicalRecord().AddDiagnosisAndTreatment(diagnosis);
        repository.save();
    }

    public Collection<AppointmentOutcomeRecord> getAppointmentOutcomesFromPatient(Patient patient){
        var ids = patient.getMedicalRecord().getPastAppointmentRecordsIds();
        return repository.getAllAppointmentsFromIds(ids).stream().map(Appointment::getAOR).toList();
    }

    public Collection<DateTimeslot> getUpcomingSchedule(){
        ArrayList<DateTimeslot> slots = new ArrayList<>();
        var appointments = getConfirmedAppointments();
        for(var ap : appointments){
            if(ap.getDate().isBefore(LocalDate.now())){
                continue;
            }
            slots.add(new DateTimeslot(ap.getDate(),ap.getTime()));
        }
        return slots.stream().sorted().toList();
    }

    public void changeAvailability(){
        currentUser.setAvailableForNewAppointments(!currentUser.isAvailableForNewAppointments());
    }

    public boolean isValidMedication(String medicationName){
        return repository.getInventory().getMedication(medicationName)!=null;
    }

    public String getNewPrescriptionId(){
        return repository.generateNewPrescriptionId();
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Doctor);
    }

    @Override
    public Doctor getCurrentUser() {
        return currentUser;
    }

    @Override
    public DoctorRepository getRepository() {
        return repository;
    }
}
