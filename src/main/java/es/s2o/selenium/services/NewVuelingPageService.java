package es.s2o.selenium.services;

import es.s2o.selenium.interfaces.VuelingService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewVuelingPageService implements VuelingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private WebDriver driver;

    public NewVuelingPageService(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String getParentElementCss() {
        return ".vy-flight-selector_journey";
    }

    @Override
    public String getOrigin(WebElement parent) {
        String originSelector = ".journey--origin .vy-flight-journey_iata";
        WebElement originSpan = parent.findElement(By.cssSelector(originSelector));

        return originSpan.getText();
    }

    @Override
    public String getDestination(WebElement parent) {
        String destinationSelector = ".journey--destination .vy-flight-journey_iata";
        WebElement destinationSpan = parent.findElement(By.cssSelector(destinationSelector));

        return destinationSpan.getText();
    }

    @Override
    public String getIsOneWayTrip(WebElement parent) {
        String flightTypeSelector = ".select-fligth-route .select-fligth-subtitle>.bodyM";
        boolean twoWayTrip = driver.findElement(By.cssSelector(flightTypeSelector)).getText().toLowerCase().contains("vuelta");

        return String.valueOf(!twoWayTrip);
    }

    @Override
    public String getTravelers(WebElement parent) {
        String numberTravelersSelector = ".select-fligth-route .select-fligth-subtitle :nth-child(2) .bodyM";
        String travelers = driver.findElement(By.cssSelector(numberTravelersSelector)).getText().toLowerCase();

        int totalTravelers = 0;
        String[] matches = Pattern.compile("[0-9]")
                .matcher(travelers)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);
        for (String match : matches) {
            totalTravelers += Integer.parseInt(match);
        }

        return String.valueOf(totalTravelers);
    }

    @Override
    public String getDeparture(WebElement parent) {
        String selectedDateSelector = ".week-selector_list_item--active";

        WebElement selectedDateSpan = driver.findElement(By.cssSelector(selectedDateSelector));

        String selectedDate = selectedDateSpan.getText() + " " + Calendar.getInstance().get(Calendar.YEAR); // Fecha en español
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
