package Data;

import Misc.Inventory;

public class PharmacistRepository extends BaseRepository{
    public PharmacistRepository(DataSource dataSource) {
        super(dataSource);
    }

    public Inventory getInventory(){
        return dataSource.getInventory();
    }
}
