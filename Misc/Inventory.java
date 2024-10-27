package Misc;

import javax.naming.InsufficientResourcesException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Map;

public class Inventory implements Serializable {
    private Map<String,Medication> medications;
    private Map<String,ReplenishmentRequest> replenishmentRequests;

    public Inventory(Map<String,Medication> medications, Map<String,ReplenishmentRequest> replenishmentRequests) {
        this.medications = medications;
        this.replenishmentRequests = replenishmentRequests;
    }

    public Inventory(){}

    public Collection<Medication> getAllMedications() {
        return medications.values();
    }

    public Medication getMedication(String medName) {
        return medications.get(medName);
    }

    public Collection<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentRequests.values();
    }

    public void dispenseMedication(String medicationName, int amount) throws InsufficientResourcesException {
        Medication medication = medications.get(medicationName);
        if(medication == null) throw new InvalidParameterException("Invalid medication name");
        if(medication.getStockLevel()<amount) throw new InsufficientResourcesException("Not enough stock level");
        medication.removeStock(amount);
    }

    public void refillMedication(String medName, int amount){
        medications.get(medName).addStock(amount);
    }

    public void addReplenishmentRequest(ReplenishmentRequest replenishmentRequest) {
        if(!medications.containsKey(replenishmentRequest.getMedicationName()))
            throw new InvalidParameterException("Invalid medication name");
        replenishmentRequests.put(replenishmentRequest.getMedicationName(),replenishmentRequest);
    }

    public void approveReplenishmentRequest(String requestId) {
        ReplenishmentRequest request = replenishmentRequests.get(requestId);
        if(request == null) throw new InvalidParameterException("Invalid request id");
        request.setRequestState("Approved");
        refillMedication(request.getMedicationName(),request.getRequestedAmount());
    }

    public void addNewMedication(Medication m){
        medications.put(m.getMedicationName(), m);
    }

    public void removeMedication(String medicationName){
        medications.remove(medicationName);
        replenishmentRequests.remove(medicationName);
    }
}



