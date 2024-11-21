package Misc;

/**
 * Timeslots available for appointments
 */
public enum Timeslot {
    SLOT_0800,
    SLOT_0900,
    SLOT_1000,
    SLOT_1100,
    SLOT_1200,
    SLOT_1300,
    SLOT_1400,
    SLOT_1500,
    SLOT_1600;
    
    @Override
    public String toString() {
        return name().substring(5);
    }
}
