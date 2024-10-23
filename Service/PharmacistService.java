package Service;

import Data.PharmacistRepository;
import Misc.*;
import Users.Patient;
import Users.Pharmacist;

import javax.naming.InsufficientResourcesException;
import java.util.Collection;

public class PharmacistService extends UserService<Pharmacist, PharmacistRepository> {
    PharmacistRepository repository;
    Pharmacist currentUser;

    public PharmacistService(String userId) {
        repository = new PharmacistRepository();
        setCurrentUser(userId);
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

    public void dispenseMedication(String userId, String prescriptionId) throws InsufficientResourcesException {
        //Find data
        MedicalRecord medicalRecord = ((Patient)repository.findUserById(userId,RoleType.Patient)).getMedicalRecord();
        AppointmentOutcomeRecord appointmentOutcomeRecord = null;
        Prescription prescription = null;
        for (var aor : medicalRecord.getPastRecords()){
            for(var p : aor.getPrescriptions()){
                if(p.getPrescriptionID().equals(prescriptionId)){
                    appointmentOutcomeRecord = aor;
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
    }

    public Collection<Medication> getAllMedication(){
        return repository.getInventory().getAllMedications();
    }

    public Collection<Medication> getAllMedicationWithLowAlert(){
        return repository.getInventory().getAllMedications().stream().filter(Medication::isStockLow).toList();
    }
}
