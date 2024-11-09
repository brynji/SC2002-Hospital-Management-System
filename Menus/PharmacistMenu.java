package Menus;

import Service.PharmacistService;

import java.util.Scanner;

public class PharmacistMenu extends BaseMenu<PharmacistService> {
    private final PharmacistService pharmacistService;

    public PharmacistMenu(PharmacistService pharmacistService) {
        this.pharmacistService = pharmacistService;
    }

    @Override
    public void baseMenu(String currentUserId, Scanner sc) {
        super.baseMenu(currentUserId,sc);
        while(true){
            System.out.println("Pharmacist Menu\n--------------");

        }
    }

    @Override
    public PharmacistService getUserService() {
        return pharmacistService;
    }
}
