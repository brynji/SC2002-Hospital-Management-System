package Misc;

import java.io.Serializable;

/**
 * Request for restock of certain medication
 */
public class ReplenishmentRequest implements Serializable {
    private final String medicationName;
    private final String pharmacistId;
    private final int requestedAmount;
    private String requestState = "pending";

    /**
     * Initializes Replenishment Request class
     * @param medicationName name of the medication to be restocked
     * @param pharmacistId ID of pharmacist who is asking for restocked
     * @param requestedAmount requested amount
     */
    public ReplenishmentRequest(String medicationName, String pharmacistId, int requestedAmount) {
        this.medicationName = medicationName;
        this.pharmacistId = pharmacistId;
        this.requestedAmount = requestedAmount;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public String getRequestState() {
        return requestState;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestState(String requestState) {
        this.requestState = requestState;
    }

    public String toString() {
        return "Replenishment Request: "+
                "Medication Name: " + medicationName +
                ", PharmacistID: " + pharmacistId +
                ", Requested Amount: " + requestedAmount +
                ", Status: " + requestState;
    }
}
