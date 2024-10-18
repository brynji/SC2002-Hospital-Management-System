package Menus;

import Service.DoctorService;

public class DoctorMenu extends BaseMenu<DoctorService> {
    DoctorService doctorService;

    public DoctorMenu(String userId) {
        doctorService = new DoctorService(userId);
    }

    @Override
    public void baseMenu() {
        System.out.println("Doctor Menu\n--------------");
    }

    @Override
    public DoctorService getUserService() {
        return doctorService;
    }
}
