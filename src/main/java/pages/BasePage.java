package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    static WebDriver driver;
    public static void setDriver(WebDriver wd){
        driver = wd;
    }

    public void clickWait(WebElement webElement, int time){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    public void pause(int time){
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean validateURL(String fraction){
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(fraction));
    }

    public boolean validateTextInElementWait(WebElement element, String text, int time){
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.textToBePresentInElement(element, text));
        }catch (NoSuchElementException| TimeoutException exception){
            System.out.println("create exception" + exception.getMessage());
            return false;
        }
    }
}
