package Misc;

import java.io.Serializable;

public class Prescription implements Serializable {

    private final String prescriptionID;
    private final String medicationName;
    private String status;
    private final int quantity;

    /**
     * Initializes Prescription class
     * @param prescriptionID unique ID
     * @param medicationName name of prescribed medication
     * @param quantity prescribed amount of the medication
     */
    public Prescription(String prescriptionID, String medicationName, int quantity) {
        this.prescriptionID = prescriptionID;
        this.medicationName = medicationName;
        this.status = "pending";
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    // Getters 
    public String getPrescriptionID() {
        return prescriptionID;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString(){
        return "PrescriptionId: " + prescriptionID + ", Medication: " + medicationName + ", Status: " + status + ", Quantity: " + quantity;
    }
}
