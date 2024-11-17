package Data;

import Misc.Inventory;

/**
 * Repository class for pharmacist-specific operations.
 * Extends BaseRepository to provide shared functionality while adding methods tailored to pharmacists.
 */
public class PharmacistRepository extends BaseRepository {

    /**
     * Constructs a PharmacistRepository with the given data source.
     *
     * @param dataSource the data source to be used for data operations.
     */
    public PharmacistRepository(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Retrieves the inventory for pharmacist-specific access.
     *
     * @return the Inventory object representing the current inventory data.
     */
    public Inventory getInventory() {
        return dataSource.getInventory();
    }
}