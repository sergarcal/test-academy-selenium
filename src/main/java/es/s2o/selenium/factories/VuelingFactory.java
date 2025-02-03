package es.s2o.selenium.factories;

import es.s2o.selenium.interfaces.VuelingService;
import es.s2o.selenium.services.NewVuelingPageService;
import es.s2o.selenium.services.OldVuelingPageService;

public class VuelingFactory {
    private final static String newVuelingSiteUrl = "https://tickets.vueling.com/booking/selectFlight";

    public static VuelingService getVuelingService(String baseUrl) {
        switch (baseUrl) {
            case newVuelingSiteUrl:
                return new NewVuelingPageService();
            default:
                return new OldVuelingPageService();
        }
    }
}
