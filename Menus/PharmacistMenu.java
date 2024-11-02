package Menus;

import Service.PharmacistService;

public class PharmacistMenu extends BaseMenu<PharmacistService> {
    private final PharmacistService pharmacistService;

    public PharmacistMenu(String userId) {
        pharmacistService = new PharmacistService(userId);
    }

    @Override
    public void baseMenu() {
        super.baseMenu();
        System.out.println("Pharmacist Menu\n--------------");
    }

    @Override
    public PharmacistService getUserService() {
        return pharmacistService;
    }
}
