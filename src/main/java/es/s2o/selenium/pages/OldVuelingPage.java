package es.s2o.selenium.pages;

import es.s2o.selenium.domain.FlightDTO;
import es.s2o.selenium.interfaces.VuelingPageI;
import es.s2o.selenium.services.OldVuelingPageService;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OldVuelingPage extends PageObject implements VuelingPageI {

    @FindBy(css = ".trip-selector")
    private WebElementFacade flightsListComponent;

    @FindBy(css = ".booking_data-flow_content .booking_data-flow_content_text:nth-child(2)")
    private WebElementFacade flightType;

    @FindBy(css = ".booking_data-flow_content .booking_data-flow_content_text:nth-child(2)")
    private WebElementFacade numberTravelers;

    @FindBy(css = "li.selected .vy-date-tabs-selector_item_header")
    private WebElementFacade selectedDateHeader;
    @FindBy(css = "li.selected .vy-date-tabs-selector_item_content")
    private WebElementFacade selectedDateContent;

    @Override
    public List<FlightDTO> getFlightsList() {
        flightsListComponent.waitUntilVisible();

        List<WebElement> parentElements = getDriver().findElements(By.cssSelector(".trip-selector"));

        List<Map<String, String>> journeyDetails = new ArrayList<>();
        for (WebElement parent : parentElements) {
            Map<String, String> details = new HashMap<>();
            details.put("origin", extractOrigin(parent));
            details.put("destination", extractDestination(parent));
            details.put("oneWayTrip", parseFlightType());
            details.put("travelers", parseNumberTravelers());
            details.put("departure", parseSelectedDate());
            journeyDetails.add(details);
        }

        return journeyDetails.stream().map(this::mapFlight).toList();
    }

    private String extractOrigin(WebElement parent) {
        String originSelector = ".vy-journey_origin .vy-journey_country";
        WebElement originSpan = parent.findElement(By.cssSelector(originSelector));

        return originSpan.getText();
    }

    private String extractDestination(WebElement parent) {
        String destinationSelector = ".vy-journey_destination .vy-journey_country";
        WebElement destinationSpan = parent.findElement(By.cssSelector(destinationSelector));

        return destinationSpan.getText();
    }

    private String parseFlightType() {
        String flightTypeContent = flightType.getText().toLowerCase();

        return OldVuelingPageService.parseFlightType(flightTypeContent);
    }

    private String parseNumberTravelers() {
        String numberTravelersContent = numberTravelers.getText().toLowerCase();

        return OldVuelingPageService.parseNumberTravelers(numberTravelersContent);
    }

    private String parseSelectedDate() {
        String selectedDateHeaderTest = selectedDateHeader.getText();
        String selectedDateContentText = selectedDateContent.getText();
        String selectedDateText = selectedDateContentText + " " + selectedDateHeaderTest;

        return OldVuelingPageService.parseSelectedDate(selectedDateText);
    }

    private FlightDTO mapFlight(Map<String, String> rowMap) {
        return OldVuelingPageService.mapFlight(rowMap);
    }
}
