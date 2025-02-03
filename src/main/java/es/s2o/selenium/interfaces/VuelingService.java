package es.s2o.selenium.interfaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public interface VuelingService {
    public String getParentElementCss();

    public String getOrigin(WebElement parent);
    public String getDestination(WebElement parent);
    public String getIsOneWayTrip(WebElement parent);
    public String getTravelers(WebElement parent);
    public String getDeparture(WebElement parent);
}
