package Misc;

import java.io.Serializable;

public class Prescription implements Serializable {

    private final String prescriptionID;
    private String medicationName;
    private String status;
    private int quantity;

    public Prescription(String prescriptionID, String medicationName, int quantity) {
        this.prescriptionID = prescriptionID;
        this.medicationName = medicationName;
        this.status = "pending";
        this.quantity = quantity;
    }

    // Setters
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getDetails() {

        StringBuilder info = new StringBuilder();

        info.append("Prescription ID: ").append(prescriptionID).append("\n")
        .append("Medication: ").append(medicationName).append("\n")
        .append("Status: ").append(status).append("\n")
        .append("Quantity: ").append(quantity);

        return info.toString();
    }

    public String toString(){
        return "Id: " + prescriptionID + ", Medication: " + medicationName + ", Status: " + status + ", Quantity: " + quantity;
    }
}
