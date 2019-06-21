package stepDefinition;

import com.codeborne.selenide.Configuration;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import runner.Proper;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Initial {

    private final static Logger log = LoggerFactory.getLogger(Initial.class);

    @Before
    public static void opens(Scenario scenarioName) throws IOException {
        System.setProperty("webdriver.chrome.driver",
                "chromedriver.exe");
        Configuration.browser = Proper.getProps().getProperty("driver");
        Configuration.timeout = 10000;
        Configuration.startMaximized = true;
        getWebDriver().manage().deleteAllCookies();
        open("https://ru.wikipedia.org/wiki");
        log.info("Сценарий: " + scenarioName.getName());
    }

    @After
    public void browserQuit() throws Throwable {
        getWebDriver().quit();
    }
}