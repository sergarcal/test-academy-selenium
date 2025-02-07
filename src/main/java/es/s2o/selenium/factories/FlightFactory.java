package es.s2o.selenium.factories;

import es.s2o.selenium.interfaces.VuelingPageI;
import es.s2o.selenium.pages.NewVuelingPage;
import es.s2o.selenium.pages.OldVuelingPage;

public class FlightFactory {
    private final String newVuelingSiteUrl = "https://tickets.vueling.com/booking/selectFlight";
    private NewVuelingPage newVuelingPage;
    private OldVuelingPage oldVuelingPage;

    public FlightFactory() {}

    public VuelingPageI getVuelingPage(String baseUrl) {
        switch (baseUrl) {
            case newVuelingSiteUrl:
                return newVuelingPage;
            default:
                return oldVuelingPage;
        }
    }
}
