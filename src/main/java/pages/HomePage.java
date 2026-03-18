package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        setDriver(driver);
        driver.get("https://trello.com/");
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }


    @FindBy(xpath = "//a[@data-uuid='MJFtCCgVhXrVl7v9HA7EH_login']")
    WebElement btnLogin;



    public void clickBtnLogin(){
        btnLogin.click();
    }
}
