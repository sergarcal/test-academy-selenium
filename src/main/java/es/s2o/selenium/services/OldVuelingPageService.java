package es.s2o.selenium.services;

import es.s2o.selenium.interfaces.VuelingService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class OldVuelingPageService implements VuelingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    String originSelector = ".vy-journey_origin .vy-journey_country";
    String destinationSelector = ".vy-journey_destination .vy-journey_country";
    String flightTypeSelector = ".booking_data-flow_content .booking_data-flow_content_text:nth-child(2)";
    String numberAdults = ".booking_data-flow_content .booking_data-flow_content_text:nth-child(2)";

    @Override
    public Map<String, String> extractPageData(WebElement parent, WebDriver driver) {
        boolean twoWayTrip = driver.findElement(By.cssSelector(flightTypeSelector)).getText().toLowerCase().contains("vuelta");
        boolean onlyOneTraveler = driver.findElement(By.cssSelector(numberAdults)).getText().toLowerCase().matches("(1 pasajero)");

        WebElement originSpan = parent.findElement(By.cssSelector(originSelector));
        WebElement destinationSpan = parent.findElement(By.cssSelector(destinationSelector));

        Map<String, String> details = new HashMap<>();
        details.put("origin", originSpan.getText());
        details.put("destination", destinationSpan.getText());
        details.put("oneWayTrip", String.valueOf(!twoWayTrip));
        details.put("travelers", String.valueOf(!twoWayTrip));

        return details;
    }

    @Override
    public String getParentElementCss() {
        return ".trip-selector";
    }
}
