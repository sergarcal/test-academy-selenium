package es.s2o.selenium.interfaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public interface VuelingService {
    public Map<String, String> extractPageData(WebElement parentElement, WebDriver driver);
    public String getParentElementCss();
}
