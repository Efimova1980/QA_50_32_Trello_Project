package manager;

import dto.User;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;
import utils.WDListener;

import java.lang.reflect.Method;
import java.time.Duration;

public class AppManager {
    public Logger logger = LoggerFactory
            .getLogger(AppManager.class);

    @Getter
    private WebDriver driver;

    public AppManager(){}

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--lang=en");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        WebDriverListener webDriverListener = new WDListener();
        driver = new EventFiringDecorator<>(webDriverListener)
                .decorate(driver);

        logger.info("start testing with method --> {}", method.getName());
    }

    public void loginTrello(){
        new HomePage(getDriver()).clickBtnLogin().login(User.getValidUser());
    }

    @AfterMethod(alwaysRun = true, enabled = true)
    public void tearDown(Method method){
        if (driver != null)
            driver.quit();
        logger.info("stop testing with method --> {}", method.getName());
    }
}
