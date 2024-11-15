package Misc;

import java.io.Serializable;

public class Medication implements Serializable {
    private String medicationName;
    private int stockLevel;
    private int lowStockAlert;

    public Medication(String medicationName, int stockLevel, int lowStockAlert) {
        this.medicationName = medicationName;
        this.stockLevel = stockLevel;
        this.lowStockAlert = lowStockAlert;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    public void addStock(int amount){
        stockLevel += amount;
    }

    public void removeStock(int amount){
        stockLevel -= amount;
    }

    public boolean isStockLow(){
        return stockLevel <= lowStockAlert;
    }

    public String getDetails() {
        return "Medication:\n" +
                "Medication Name: " + medicationName + "\n" +
                "Stock Level: " + stockLevel + "\n" +
                "Low Stock Alert Level: " + lowStockAlert + "\n";
    }
}
