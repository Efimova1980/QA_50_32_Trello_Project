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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

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
        if (!validateURL("boards")) {
            logger.warn("Did not land on boards page within timeout after login");
        }
    }

    public void submitTopSecret(String topSecret){
        if (topSecret != null) {
            enterTotpCode(topSecret);
        }
    }

    public void submitPassword(String password){
        clickWait(inputPassword, 10);
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
            logger.warn("TOTP input field did not appear within 10s - assuming 2FA was not requested", e);
            return;
        }

        waitForFreshTotpWindow();

        TOTPSecret secret = TOTPSecret.Companion.fromBase32EncodedString(totpSecret);
        TOTP totp = new TOTPGenerator().generateCurrent(secret);
        logger.info("TOTP secret length: {}", totpSecret.length());
        inputTotpCode.sendKeys(totp.getValue());
        totpSubmit.click();
    }

    // A TOTP code is valid for a 30-second window. If we generate it near the end of that
    // window, network/browser latency (more noticeable on CI runners) can make it arrive
    // after the window has already rolled over, so the server rejects a code that was
    // technically correct when generated. Waiting for a fresh window avoids that race.
    private void waitForFreshTotpWindow() {
        long secondsIntoWindow = (System.currentTimeMillis() / 1000) % 30;
        long secondsLeft = 30 - secondsIntoWindow;
        if (secondsLeft < 5) {
            logger.info("Only {}s left in current TOTP window, waiting for a fresh one", secondsLeft);
            pause((int) secondsLeft);
        }
    }
}
