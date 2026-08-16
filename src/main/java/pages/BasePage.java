package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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

    // scrolls the element's own scrollable ancestor (e.g. a dropdown menu) into view, not just the page
    public void scrollTo(WebElement element){
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void clickWait(WebElement webElement, int time){
        // retries on staleness: the element can be found clickable and still get swapped out
        // by a re-render before the click command reaches the browser (e.g. after closing a board)
        for (int attempt = 1; ; attempt++) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(webElement)).click();
                return;
            } catch (StaleElementReferenceException e) {
                if (attempt >= 3) {
                    throw e;
                }
            }
        }
    }

    public void pause(int time){
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean validateURL(String fraction){
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains(fraction));
        } catch (TimeoutException exception){
            return false;
        }
    }

    public boolean validateTextInElementWait(WebElement element, String text, int time){
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.textToBePresentInElement(element, text));
        }catch (NoSuchElementException| TimeoutException exception){
            System.out.println("create exception" + exception.getMessage());
            return false;
        }
    }

    public boolean validateElementVisible(WebElement element, int time){
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.visibilityOf(element)) != null;
        } catch (NoSuchElementException | TimeoutException exception){
            return false;
        }
    }
}
