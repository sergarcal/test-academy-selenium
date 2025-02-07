package es.s2o.selenium.stepsdefs.vueling;

import es.s2o.selenium.domain.FlightDTO;
import es.s2o.selenium.factories.FlightFactory;
import es.s2o.selenium.interfaces.VuelingPageI;
import es.s2o.selenium.pages.VuelingPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.serenitybdd.annotations.Steps;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

import static net.serenitybdd.core.Serenity.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class VuelingStepdefs {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private VuelingPage vuelingPage;
    @Steps
    private FlightFactory flightFactory;

    private FlightDTO flight;

    @Before
    public void beforeScenario() {
        LOGGER.debug("beforeScenario starts");
    }

    @After
    public void afterScenario() {
        LOGGER.debug("afterScenario starts");
    }

    @Given("^I'm in the home page$")
    public void iMInTheHomePage() throws Throwable {
        LOGGER.debug("iMInTheHomePage starts");
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        String baseUrl = variables.getProperty("WEB_ROOT");
        vuelingPage.openAt(baseUrl);
    }

    @When("^I specify the journey details:$")
    public void iRegisterTheFollowingBooking(FlightDTO flightDTO) throws Throwable {
        LOGGER.debug("iRegisterTheFollowingBooking starts, flightDTO:{}", flightDTO);
        flight = flightDTO;
        vuelingPage.addFlightDetails(flight);
    }

    @Then("^I should see available flights matching my criteria$")
    public void iGetTheFlightInTheFlightsList() throws Throwable {
        LOGGER.debug("iGetTheFlightInTheFlightsList starts");

        changeToNewTab();
        String currentUrl = getDriver().getCurrentUrl();

        VuelingPageI vuelingPage = flightFactory.getVuelingPage(currentUrl);

        List<FlightDTO> actualFlights = vuelingPage.getFlightsList();

        LOGGER.debug("Flights: ");
        for (FlightDTO flight : actualFlights) {
            LOGGER.debug(flight.toString());
        }

        assertThat(actualFlights).as("Flights list")
                .usingRecursiveFieldByFieldElementComparator()
                .containsOnly(flight);
    }

    private void changeToNewTab() {
        WebDriver driver = getDriver();

        String currHandle = driver.getWindowHandle();
        Set<String> allHandles = driver.getWindowHandles();

        for (String handle : allHandles) {
            if (!handle.contentEquals(currHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }
}
