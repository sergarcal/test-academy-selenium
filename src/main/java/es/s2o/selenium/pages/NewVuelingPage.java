package es.s2o.selenium.pages;

import es.s2o.selenium.domain.VuelingDTO;
import es.s2o.selenium.interfaces.VuelingPageI;
import es.s2o.selenium.services.NewVuelingPageService;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

public class NewVuelingPage extends PageObject implements VuelingPageI {

    @FindBy(css = ".vy-flight-selector_journey")
    private WebElementFacade flightsListComponent;

    @FindBy(css = ".select-fligth-route .select-fligth-subtitle>.bodyM")
    private WebElementFacade flightType;

    @FindBy(css = ".select-fligth-route .select-fligth-subtitle")
    private WebElementFacade numberTravelers;

    @FindBy(css = ".week-selector_list_item--active")
    private WebElementFacade selectedDate;

    @Override
    public List<VuelingDTO> getFlightsList() {
        flightsListComponent.waitUntilVisible();

        List<WebElement> parentElements = getDriver().findElements(By.cssSelector(".vy-flight-selector_journey"));

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
        String originSelector = ".journey--origin .vy-flight-journey_iata";
        WebElement originSpan = parent.findElement(By.cssSelector(originSelector));

        return originSpan.getText();
    }

    private String extractDestination(WebElement parent) {
        String destinationSelector = ".journey--destination .vy-flight-journey_iata";
        WebElement destinationSpan = parent.findElement(By.cssSelector(destinationSelector));

        return destinationSpan.getText();
    }

    private String parseFlightType() {
        String flightTypeContent = flightType.getText().toLowerCase();

        return NewVuelingPageService.parseFlightType(flightTypeContent);
    }

    private String parseNumberTravelers() {
        String numberTravelersContent = numberTravelers.getText().toLowerCase();

        return NewVuelingPageService.parseNumberTravelers(numberTravelersContent);
    }

    private String parseSelectedDate() {
        String selectedDateContent = selectedDate.getText();

        return NewVuelingPageService.parseSelectedDate(selectedDateContent);
    }

    private VuelingDTO mapFlight(Map<String, String> rowMap) {
        return NewVuelingPageService.mapFlight(rowMap);
    }
}
