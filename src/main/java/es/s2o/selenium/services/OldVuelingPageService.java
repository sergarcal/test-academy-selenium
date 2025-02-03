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

    String originSelector = ".vy-journey_origin .vy-journey_country";
    String destinationSelector = ".vy-journey_destination .vy-journey_country";
    String flightTypeSelector = ".booking_data-flow_content .booking_data-flow_content_text:nth-child(2)";
    String numberAdults = ".booking_data-flow_content .booking_data-flow_content_text:nth-child(2)";
    String selectedDateHeaderSelector = "li.selected .vy-date-tabs-selector_item_header";
    String selectedDateContentSelector = "li.selected .vy-date-tabs-selector_item_content";

    @Override
    public Map<String, String> extractPageData(WebElement parent, WebDriver driver) {
        boolean twoWayTrip = driver.findElement(By.cssSelector(flightTypeSelector)).getText().toLowerCase().contains("vuelta");

        String pasangers = driver.findElement(By.cssSelector(numberAdults)).getText().toLowerCase();
        String[] matches = Pattern.compile("[0-9]")
                .matcher(pasangers)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);

        int totalPassangers = Integer.parseInt(matches[0]);

        WebElement originSpan = parent.findElement(By.cssSelector(originSelector));
        WebElement destinationSpan = parent.findElement(By.cssSelector(destinationSelector));
        WebElement selectedDateHeaderSpan = driver.findElement(By.cssSelector(selectedDateHeaderSelector));
        WebElement selectedDateContentSpan = driver.findElement(By.cssSelector(selectedDateContentSelector));

        String fechaEntrada = selectedDateContentSpan.getText() + " " + selectedDateHeaderSpan.getText() + " " + Calendar.getInstance().get(Calendar.YEAR); // Fecha en español
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("EEE d MMM yyyy", new Locale("es", "ES"));
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");

        String fechaFormateada = "";
        try {
            Date fecha = formatoEntrada.parse(fechaEntrada);
            fechaFormateada = formatoSalida.format(fecha);
            System.out.println(fechaFormateada);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, String> details = new HashMap<>();
        details.put("origin", originSpan.getText());
        details.put("destination", destinationSpan.getText());
        details.put("oneWayTrip", String.valueOf(!twoWayTrip));
        details.put("travelers", String.valueOf(totalPassangers));
        details.put("departure", fechaFormateada);

        return details;
    }

    @Override
    public String getParentElementCss() {
        return ".trip-selector";
    }
}
