package es.s2o.selenium.services;

import es.s2o.selenium.interfaces.VuelingService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class OldVuelingPageService implements VuelingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private WebDriver driver;

    public OldVuelingPageService(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String getParentElementCss() {
        return ".trip-selector";
    }

    @Override
    public String getOrigin(WebElement parent) {
        String originSelector = ".vy-journey_origin .vy-journey_country";
        WebElement originSpan = parent.findElement(By.cssSelector(originSelector));

        return originSpan.getText();
    }

    @Override
    public String getDestination(WebElement parent) {
        String destinationSelector = ".vy-journey_destination .vy-journey_country";
        WebElement destinationSpan = parent.findElement(By.cssSelector(destinationSelector));

        return destinationSpan.getText();
    }

    @Override
    public String getIsOneWayTrip(WebElement parent) {
        String flightTypeSelector = ".booking_data-flow_content .booking_data-flow_content_text:nth-child(2)";
        boolean twoWayTrip = driver.findElement(By.cssSelector(flightTypeSelector)).getText().toLowerCase().contains("vuelta");

        return String.valueOf(!twoWayTrip);
    }

    @Override
    public String getTravelers(WebElement parent) {
        String numberTravelersSelector = ".booking_data-flow_content .booking_data-flow_content_text:nth-child(2)";
        String travelers = driver.findElement(By.cssSelector(numberTravelersSelector)).getText().toLowerCase();

        String[] matches = Pattern.compile("[0-9]")
                .matcher(travelers)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);

        return matches[0];
    }

    @Override
    public String getDeparture(WebElement parent) {
        String selectedDateHeaderSelector = "li.selected .vy-date-tabs-selector_item_header";
        String selectedDateContentSelector = "li.selected .vy-date-tabs-selector_item_content";

        WebElement selectedDateHeaderSpan = driver.findElement(By.cssSelector(selectedDateHeaderSelector));
        WebElement selectedDateContentSpan = driver.findElement(By.cssSelector(selectedDateContentSelector));

        String selectedDate = selectedDateContentSpan.getText() + " " + selectedDateHeaderSpan.getText() + " " + Calendar.getInstance().get(Calendar.YEAR); // Fecha en español
        SimpleDateFormat selectedDateFormat = new SimpleDateFormat("EEE d MMM yyyy", new Locale("es", "ES"));
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String formatedDate = "";
        try {
            Date date = selectedDateFormat.parse(selectedDate);
            formatedDate = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatedDate;
    }
}
