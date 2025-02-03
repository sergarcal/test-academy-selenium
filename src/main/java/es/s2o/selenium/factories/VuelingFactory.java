package es.s2o.selenium.factories;

import es.s2o.selenium.interfaces.VuelingService;
import es.s2o.selenium.services.NewVuelingPageService;
import es.s2o.selenium.services.OldVuelingPageService;
import org.openqa.selenium.WebDriver;

public class VuelingFactory {
    private final static String newVuelingSiteUrl = "https://tickets.vueling.com/booking/selectFlight";

    public static VuelingService getVuelingService(String baseUrl, WebDriver driver) {
        switch (baseUrl) {
            case newVuelingSiteUrl:
                return new NewVuelingPageService(driver);
            default:
                return new OldVuelingPageService(driver);
        }
    }
}
