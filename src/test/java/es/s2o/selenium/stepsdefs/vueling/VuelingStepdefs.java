package es.s2o.selenium.stepsdefs.vueling;

import es.s2o.selenium.domain.VuelingDTO;
import es.s2o.selenium.pages.VuelingListPage;
import es.s2o.selenium.pages.VuelingPage;
import es.s2o.selenium.services.NewVuelingPageService;
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

public class VuelingStepdefs {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private VuelingListPage vuelingListPage;
    private VuelingPage vuelingPage;

    private List<VuelingDTO> flights;

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
    public void iRegisterTheFollowingBooking(List<VuelingDTO> vuelingDTOList) throws Throwable {
        LOGGER.debug("iRegisterTheFollowingBooking starts, list size:[{}]", vuelingDTOList.size());
        flights = vuelingDTOList;
        flights.forEach(flight -> vuelingPage.addFlightDetails(flight));
    }

    @Then("^I should see available flights matching my criteria$")
    public void iGetTheFlightInTheFlightsList() throws Throwable {
        LOGGER.debug("iGetTheFlightInTheFlightsList starts");

        changeToNewTab();
        String currentUrl = getDriver().getCurrentUrl();

        List<VuelingDTO> actualFlights = vuelingListPage.getFlightsList(currentUrl);
        LOGGER.debug("Flights: ");
        for (VuelingDTO flight : actualFlights) {
            LOGGER.debug(flight.toString());
        }
        // assertThat(actualReservations).as("Reservation list")
        //        .usingRecursiveFieldByFieldElementComparator()
        //        .containsExactlyElementsOf(reservations);
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
