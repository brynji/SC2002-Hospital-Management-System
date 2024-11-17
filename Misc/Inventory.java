package Misc;

import javax.naming.InsufficientResourcesException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of all medications and replenishment requests
 */
public class Inventory implements Serializable {
    private final Map<String,Medication> medications;
    private final Map<String, ArrayList<ReplenishmentRequest>> replenishmentRequests;

    /**
     * Initializes Inventory class
     * @param medications Map of all medication in inventory
     * @param replenishmentRequests Map of all replenishmentRequest by the medicationName of requested medication
     */
    public Inventory(Map<String,Medication> medications, Map<String,ArrayList<ReplenishmentRequest>> replenishmentRequests) {
        this.medications = medications;
        this.replenishmentRequests = replenishmentRequests;
    }

    /**
     * Initializes Inventory class with empty containers
     */
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

    /**
     * Subtracts amount from medication with medicationName, if the stock is high enough
     * @param medicationName name of the medication to be dispensed
     * @param amount amount to be dispensed
     * @throws InsufficientResourcesException when there in not enough medication in stock to dispense amount
     */
    public void dispenseMedication(String medicationName, int amount) throws InsufficientResourcesException {
        Medication medication = medications.get(medicationName);
        if(medication == null) throw new InvalidParameterException("Invalid medication name");
        if(medication.getStockLevel()<amount) throw new InsufficientResourcesException("Not enough stock level");
        medication.removeStock(amount);
    }

    /**
     * Add amount to medication with medication name
     * @param medName medication to be refilled
     * @param amount amount to be added
     */
    public void refillMedication(String medName, int amount){
        medications.get(medName).addStock(amount);
    }

    /**
     * Saved replenishment request
     * @param replenishmentRequest request to be saved
     * @throws InvalidParameterException requested medication does not exist
     */
    public void addReplenishmentRequest(ReplenishmentRequest replenishmentRequest) {
        if(!medications.containsKey(replenishmentRequest.getMedicationName()))
            throw new InvalidParameterException("Invalid medication name");
        replenishmentRequests.get(replenishmentRequest.getMedicationName()).add(replenishmentRequest);
    }

    /**
     * Sets state  of the request to "Approved" and adds requested amount of medication to stock
     * @param request request to accept
     */
    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        request.setRequestState("Approved");
        refillMedication(request.getMedicationName(),request.getRequestedAmount());
    }

    /**
     * Sets state of the request to "Denied"
     * @param request
     */
    public void denyReplenishmentRequest(ReplenishmentRequest request) {
        request.setRequestState("Denied");
    }

    public void addNewMedication(Medication m){
        medications.put(m.getMedicationName(), m);
        replenishmentRequests.put(m.getMedicationName(), new ArrayList<>());
    }
}



