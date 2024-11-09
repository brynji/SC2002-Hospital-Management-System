package Service;

import Misc.AppointmentOutcomeRecord;
import Data.PharmacistRepository;
import Misc.*;
import Users.Patient;
import Users.Pharmacist;

import javax.naming.InsufficientResourcesException;
import java.util.Collection;

public class PharmacistService extends UserService<Pharmacist, PharmacistRepository> {
    private final PharmacistRepository repository;
    private Pharmacist currentUser;

    public PharmacistService(PharmacistRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Pharmacist);
    }

    @Override
    public Pharmacist getCurrentUser() {
        return currentUser;
    }

    @Override
    public PharmacistRepository getRepository() {
        return repository;
    }

    public Collection<AppointmentOutcomeRecord> getAppointmentOutcomeRecords(String userId){
        return repository.getAllAppointmentsFromIds(repository.<Patient>findUserById(userId,RoleType.Patient).getAppointments())
                .stream().map(Appointment::getAOR).toList();
    }

    public void dispenseMedication(String userId, String prescriptionId) throws IllegalArgumentException, InsufficientResourcesException {
        //Find data
        MedicalRecord medicalRecord = ((Patient)repository.findUserById(userId,RoleType.Patient)).getMedicalRecord();
        AppointmentOutcomeRecord appointmentOutcomeRecord = null;
        Prescription prescription = null;

        for (var appointment : repository.getAllAppointmentsFromIds(medicalRecord.getPastAppointmentRecordsIds())){
            for(var p : appointment.getAOR().getPrescriptions()){
                if(p.getPrescriptionID().equals(prescriptionId)){
                    appointmentOutcomeRecord = appointment.getAOR();
                    prescription = p;
                }
            }
        }
        if(prescription == null)
            throw new IllegalArgumentException("Prescription not found");
        //Dispense
        try{
            Inventory inventory = repository.getInventory();
            inventory.dispenseMedication(prescription.getMedicationName(),prescription.getQuantity());
            appointmentOutcomeRecord.setStatus("dispensed");
        } catch (InsufficientResourcesException e){
            throw new InsufficientResourcesException("Not enough medication in stock");
        }
        repository.save();
    }

    public Collection<Medication> getAllMedication(){
        return repository.getInventory().getAllMedications();
    }

    public Collection<Medication> getAllMedicationWithLowAlert(){
        return repository.getInventory().getAllMedications().stream().filter(Medication::isStockLow).toList();
    }

    public void submitReplenishmentRequest(String medicationName, int amount){
        repository.getInventory().addReplenishmentRequest(new
                ReplenishmentRequest(medicationName, currentUser.getUserID(), amount));
        repository.save();
    }
}
