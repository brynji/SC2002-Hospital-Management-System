package Misc;

import java.io.Serializable;

public class ReplenishmentRequest implements Serializable {
    private final String medicationName;
    private final String pharmacistId;
    private final int requestedAmount;
    private String requestState = "pending";

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
}
