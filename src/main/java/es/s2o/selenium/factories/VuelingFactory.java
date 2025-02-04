package es.s2o.selenium.factories;

import es.s2o.selenium.interfaces.VuelingPageI;
import es.s2o.selenium.pages.NewVuelingPage;
import es.s2o.selenium.pages.OldVuelingPage;

public class VuelingFactory {
    private final String newVuelingSiteUrl = "https://tickets.vueling.com/booking/selectFlight";
    private NewVuelingPage newVuelingPage;
    private OldVuelingPage oldVuelingPage;

    public VuelingFactory() {}

    public VuelingPageI getVuelingPage(String baseUrl) {
        if (baseUrl.equals(newVuelingSiteUrl)) {
            return newVuelingPage;
        }
        return oldVuelingPage;
    }
}
