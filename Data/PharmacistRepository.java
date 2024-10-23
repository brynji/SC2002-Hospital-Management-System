package Data;

import Misc.Inventory;

public class PharmacistRepository extends BaseRepository{

    // --- ADD ---

    // --- GET ---

    public Inventory getInventory(){
        return inventory.get("");
    }

    // --- UPDATE ---

    // --- DELETE ---
}
