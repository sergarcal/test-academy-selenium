package es.s2o.selenium.pages;

import es.s2o.selenium.domain.VuelingDTO;
import es.s2o.selenium.factories.VuelingFactory;
import es.s2o.selenium.interfaces.VuelingService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VuelingListPage extends PageObjectBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public List<VuelingDTO> getFlightsList(String currentUrl) {
        LOGGER.debug("getReservationList starts");

        VuelingService vuelingService = VuelingFactory.getVuelingService(currentUrl);
        String parentElementSelector = vuelingService.getParentElementCss();

        waitFor(parentElementSelector);

        List<WebElement> parentElements = getDriver().findElements(By.cssSelector(parentElementSelector));

        List<Map<String, String>> journeyDetails = new ArrayList<>();
        for (WebElement parent : parentElements) {
            Map<String, String> details = vuelingService.extractPageData(parent, getDriver());

            journeyDetails.add(details);
        }

        return journeyDetails.stream().map(this::mapFlight).toList();
    }

    private VuelingDTO mapFlight(Map<String, String> rowMap) {
        VuelingDTO flight = new VuelingDTO();
        flight.setOrigin(rowMap.get("origin"));
        flight.setDestination(rowMap.get("destination"));
        flight.setOneWayTrip(rowMap.get("oneWayTrip"));
        flight.setTravelers(rowMap.get("travelers"));
        flight.setDeparture(rowMap.get("departure"));
        return flight;
    }
}
