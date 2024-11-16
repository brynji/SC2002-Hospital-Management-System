package Misc;

import javax.naming.InsufficientResourcesException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements Serializable {
    private final Map<String,Medication> medications;
    private final Map<String, ArrayList<ReplenishmentRequest>> replenishmentRequests;

    public Inventory(Map<String,Medication> medications, Map<String,ArrayList<ReplenishmentRequest>> replenishmentRequests) {
        this.medications = medications;
        this.replenishmentRequests = replenishmentRequests;
    }

    public Inventory(){
        medications = new HashMap<>();
        replenishmentRequests = new HashMap<>();
    }

    public Collection<Medication> getAllMedications() {
        return medications.values();
    }

    public Medication getMedication(String medName) {
        return medications.get(medName);
    }

    public Collection<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentRequests.values().stream().flatMap(Collection::stream).toList();
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
        replenishmentRequests.get(replenishmentRequest.getMedicationName()).add(replenishmentRequest);
    }

    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        request.setRequestState("Approved");
        refillMedication(request.getMedicationName(),request.getRequestedAmount());
    }

    public void denyReplenishmentRequest(ReplenishmentRequest request) {
        request.setRequestState("Denied");
    }

    public void addNewMedication(Medication m){
        medications.put(m.getMedicationName(), m);
        replenishmentRequests.put(m.getMedicationName(), new ArrayList<>());
    }
}



