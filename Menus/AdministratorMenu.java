package Menus;

import Service.AdministratorService;

public class AdministratorMenu extends BaseMenu<AdministratorService> {
    AdministratorService administratorService;

    public AdministratorMenu(String userId) {
        administratorService = new AdministratorService(userId);
    }

    @Override
    public void baseMenu() {
        System.out.println("Administrator Menu\n---------------");
        administratorService.getRepository().save();
    }

    @Override
    public AdministratorService getUserService() {
        return administratorService;
    }
}
