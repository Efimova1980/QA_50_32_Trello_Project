package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.WDListener;

import java.lang.reflect.Method;
import java.time.Duration;

public class AppManager {
    public Logger logger = LoggerFactory.getLogger(AppManager.class);
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }
    public AppManager()
    {}

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--lang=en");
//        chromeOptions.addArguments("--disable-features=Translate");
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("intl.accept_languages", "en-US,en");
//        chromeOptions.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        WebDriverListener webDriverListener = new WDListener();
        driver = new EventFiringDecorator<>(webDriverListener)
                .decorate(driver);
        logger.info("start testing with method --> " + method.getName());
    }

    @AfterMethod(alwaysRun = true, enabled = true)
    public void tearDown(Method method){
        if (driver != null)
            driver.quit();
        logger.info("stop testing with method --> " + method.getName());
    }

}
