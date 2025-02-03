package es.s2o.selenium.pages;

import es.s2o.selenium.domain.VuelingDTO;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

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
    private WebElementFacade calendarDaysTable202551;

    private WebElementFacade btnSubmitHomeSearcher;

    public void addFlightDetails(VuelingDTO flight) {
        LOGGER.debug("addFlightDetails starts, flight: [{}]", flight);
        btnRejectCookies.click();

        originInput.click();
        typeInto(originInput, flight.getOrigin());
        btnOriginDropdownConfirmation.click();

        typeInto(destinationInput, flight.getDestination());
        btnDestinationDropdownConfirmation.click();

        btnOneWayTrip.click();
        while (!txtMonth.getText().equals("Junio")) {
            LOGGER.debug(outboundDate.getValue());
            nextButtonCalendar.click();
        }
        calendarDaysTable202551.click();

        btnSubmitHomeSearcher.click();
    }
}
