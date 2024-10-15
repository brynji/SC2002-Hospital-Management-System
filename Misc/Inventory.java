package Project.Misc;

public class Inventory {
    
    private String medicationName;
    private int stockLevel;
    private int lowStockAlert;

    public Inventory(String medicationName, int stockLevel, int lowStockAlert) {
        this.medicationName = medicationName;
        this.stockLevel = stockLevel;
        this.lowStockAlert = lowStockAlert;
    }

    // Getters
    public String getMedicationName() {
        return medicationName;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    // Setters
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    public void updateStockLevel(int newLevel) {
        this.stockLevel += newLevel;
    }

    public boolean isStockLow() {
        if (this.stockLevel < this.lowStockAlert) {
            return true;
        }
        return false;
    }
    
    public String getDetails() {
        
        StringBuilder info = new StringBuilder();
        
        info.append("Medication: ").append(medicationName).append("\n")
            .append("Stock Level: ").append(stockLevel).append("\n")
            .append("Alert Level: ").append(lowStockAlert).append("\n");

        return info.toString();
    }

}



