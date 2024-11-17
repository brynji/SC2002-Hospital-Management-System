package Data;

import Misc.Inventory;

/**
 * Repository with all methods user logged in as Pharmacist needs
 */
public class PharmacistRepository extends BaseRepository{
    public PharmacistRepository(DataSource dataSource) {
        super(dataSource);
    }

    public Inventory getInventory(){
        return dataSource.getInventory();
    }
}
