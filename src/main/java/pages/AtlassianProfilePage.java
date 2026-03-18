package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.io.File;

public class AtlassianProfilePage extends BasePage{
    public AtlassianProfilePage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,10), this);
    }

    @FindBy(xpath = "//div[@data-test-selector='profile-hover-info']")
    WebElement btnProfilePhoto;
    @FindBy(xpath = "//button[@data-testid='change-avatar']")
    WebElement btnChangeAvatar;
    @FindBy(xpath = "//input[@data-testid='image-navigator-input-file']")
    WebElement inputUploadPhoto;
    @FindBy(xpath = "//button[@class='css-u9eekp']")
    WebElement btnUploadPhoto;
    @FindBy(xpath = "//div[@class = '_11c8fhey _1reo1wug _18m91wug _c71l53f4 _1i4qfg65']")
    WebElement popupMessage;
    @FindBy(xpath = "//h2[@class= '_1mouidpf _1dyz4jg8 _1p1dglyw _11c8140y _syaz15cr']")
    WebElement popupWrongFormatFile;

    public void changeMyProfilePhoto(String photoPath){
        Actions actions = new Actions(driver);
        actions.moveToElement(btnProfilePhoto).pause(2000).click().perform();
        clickWait(btnChangeAvatar, 5);
        File photo = new File(photoPath);
        inputUploadPhoto.sendKeys(photo.getAbsolutePath());
        clickWait(btnUploadPhoto, 5);
    }

    public boolean validateMessage(String text){
        return validateTextInElementWait(popupMessage, text, 10);
    }

    public boolean validateWrongFormatFileMessage(String text){
        return validateTextInElementWait(popupWrongFormatFile, text, 10);
    }
}
