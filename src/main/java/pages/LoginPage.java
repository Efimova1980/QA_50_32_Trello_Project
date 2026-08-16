package pages;

import com.atlassian.onetime.core.TOTP;
import com.atlassian.onetime.core.TOTPGenerator;
import com.atlassian.onetime.model.TOTPSecret;
import dto.User;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(id = "username-uid1")
    WebElement inputEmail;
    @FindBy(id = "login-submit")
    WebElement loginSubmit;
    @FindBy(id = "password")
    WebElement inputPassword;
    @FindBy(id = "two-step-verification-otp-code-input")
    WebElement inputTotpCode;
    @FindBy(xpath = "//input[@id='two-step-verification-otp-code-input']/ancestor::form//button[@type='submit']")
    WebElement totpSubmit;

    @FindBy(id = "ProductHeadingSuffix")
    WebElement textIncorectEmail;
    @FindBy(xpath = "//div[contains(text(), 'Incorrect email address and / or password')]")
    WebElement textIncorectPassword;
    @FindBy(xpath = "//div[text() ='You entered an incorrect verification code.']")
    WebElement errorWarning;

    public void login(User user) {
        submitEmail(user.getEmail());
        submitPassword(user.getPassword());
        submitTopSecret(user.getTotpSecret());
    }

    public void submitTopSecret(String topSecret){
        if (topSecret != null) {
            enterTotpCode(topSecret);
        }
    }

    public void submitPassword(String password){
        clickWait(inputPassword, 5);
        inputPassword.sendKeys(password);
        loginSubmit.click();
    }

    public void submitEmail(String email) {
        inputEmail.sendKeys(email);
        loginSubmit.click();
    }

    public boolean validateIncorrectEmailError(int time) {
        return validateElementVisible(textIncorectEmail, time);
    }

    public boolean validateIncorrectPasswordError(int time) {
        return validateElementVisible(textIncorectPassword, time);
    }

    public boolean validateIncorrectTotpError(int time) {
        return validateElementVisible(errorWarning, time);
    }

    private void enterTotpCode(String totpSecret) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(inputTotpCode));
        } catch (TimeoutException e) {
            return;
        }
        TOTPSecret secret = TOTPSecret.Companion.fromBase32EncodedString(totpSecret);
        TOTP totp = new TOTPGenerator().generateCurrent(secret);
        inputTotpCode.sendKeys(totp.getValue());
        totpSubmit.click();
    }
}
