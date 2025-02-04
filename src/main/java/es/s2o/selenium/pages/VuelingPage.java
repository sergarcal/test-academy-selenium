package es.s2o.selenium.pages;

import es.s2o.selenium.domain.VuelingDTO;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Month;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VuelingPage extends PageObjectBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(id = "onetrust-reject-all-handler")
    private WebElementFacade btnRejectCookies;

    private WebElementFacade originInput;
    @FindBy(css = "li.vy-list-dropdown_item")
    private WebElementFacade btnOriginDropdownConfirmation;

    private WebElementFacade destinationInput;
    @FindBy(css = "li.vy-list-dropdown_item")
    private WebElementFacade btnDestinationDropdownConfirmation;

    @FindBy(className = "vy-switch_button")
    private WebElementFacade btnOneWayTrip;
    @FindBy(className = "ui-datepicker-month")
    private WebElementFacade txtMonth;
    private WebElementFacade outboundDate;
    private WebElementFacade nextButtonCalendar;

    private WebElementFacade btnSubmitHomeSearcher;

    public void addFlightDetails(VuelingDTO flight) {
        LOGGER.debug("addFlightDetails starts, flight: [{}]", flight);
        btnRejectCookies.click();

        originInput.click();
        typeInto(originInput, flight.getOrigin());
        btnOriginDropdownConfirmation.click();

        destinationInput.waitUntilVisible();
        typeInto(destinationInput, flight.getDestination());
        btnDestinationDropdownConfirmation.click();

        btnOneWayTrip.click();

        String departure = flight.getDeparture();
        String monthName = getMonthName(departure);
        while (!txtMonth.getText().toLowerCase().equals(monthName)) {
            LOGGER.debug(outboundDate.getValue());
            nextButtonCalendar.click();
        }
        WebElement calendarDayElement = getDriver().findElement(By.id(transformDateToId(departure)));
        calendarDayElement.click();

        btnSubmitHomeSearcher.click();
    }

    private String transformDateToId(String date) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(date);

        int[] dateParts = new int[3];
        int index = 0;

        while (matcher.find() && index < 3) {
            dateParts[index] = Integer.parseInt(matcher.group());
            index++;
        }

        int day = dateParts[0];
        int month = dateParts[1] - 1;
        int year = dateParts[2];

        return "calendarDaysTable" + year + month + day;
    }

    private String getMonthName(String date) {
        String[] parts = date.split("/");
        int month = Integer.parseInt(parts[1]);

        return Month.of(month).getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "ES"));
    }
}
