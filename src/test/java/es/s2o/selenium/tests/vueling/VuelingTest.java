package es.s2o.selenium.tests.vueling;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/vueling",
        glue = "es.s2o.selenium.stepsdefs.vueling")
public class VuelingTest {


}
