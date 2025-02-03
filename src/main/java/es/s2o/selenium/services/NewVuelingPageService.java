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

    String originSelector = ".journey--origin .vy-flight-journey_iata";
    String destinationSelector = ".journey--destination .vy-flight-journey_iata";
    String flightTypeSelector = ".select-fligth-route .select-fligth-subtitle>.bodyM";
    String numberAdults = ".select-fligth-route .select-fligth-subtitle :nth-child(2) .bodyM";
    String selectedDateSelector = ".week-selector_list_item--active";

    @Override
    public Map<String, String> extractPageData(WebElement parent, WebDriver driver) {
        boolean twoWayTrip = driver.findElement(By.cssSelector(flightTypeSelector)).getText().toLowerCase().contains("vuelta");

        String passangers = driver.findElement(By.cssSelector(numberAdults)).getText().toLowerCase();
        int totalPassangers = 0;
        String[] matches = Pattern.compile("[0-9]")
                .matcher(passangers)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);
        for (String match : matches) {
            totalPassangers += Integer.parseInt(match);
        }

        WebElement originSpan = parent.findElement(By.cssSelector(originSelector));
        WebElement destinationSpan = parent.findElement(By.cssSelector(destinationSelector));
        WebElement selectedDateSpan = driver.findElement(By.cssSelector(selectedDateSelector));

        String fechaEntrada = selectedDateSpan.getText() + " " + Calendar.getInstance().get(Calendar.YEAR); // Fecha en español
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
        return ".vy-flight-selector_journey";
    }
}
