package pages;

import dto.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(id = "username-uid1")
    WebElement inputEmail;
    @FindBy(id="login-submit")
    WebElement loginSubmit;
    @FindBy(id="password")
    WebElement inputPassword;

    public void login(User user){
        inputEmail.sendKeys(user.getEmail());
        loginSubmit.click();
        clickWait(inputPassword, 5);
        inputPassword.sendKeys(user.getPassword());
        loginSubmit.click();
    }

}
