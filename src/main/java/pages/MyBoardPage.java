package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class MyBoardPage extends BasePage{
    public MyBoardPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@data-testid='board-name-input']")
    WebElement boardName;
    @FindBy(xpath = "//span[@data-testid='OverflowMenuHorizontalIcon']")
    WebElement btnDots;
    @FindBy(xpath = "//ul/li[last()]/button")
    WebElement btnCloseBoard;
    @FindBy(xpath = "//button[@data-testid='popover-close-board-confirm']")
    WebElement btnCloseBoardConfirm;
    //@FindBy(xpath ="//button[@data-testid='close-board-delete-board-button']")
    @FindBy(xpath = "//ul/li[last()]/button")
    WebElement btnDeleteBoard;
    @FindBy(xpath = "//button[@data-testid='close-board-delete-board-confirm-button']" )
    WebElement btnDeleteBoardConfirm;

    public void deleteBoard(){
        btnDots.click();
        btnCloseBoard.click();
        btnCloseBoardConfirm.click();
        //clickWait(btnDots, 5);
        //driver.navigate().refresh();
        pause(3);
        btnDots.click();
        pause(3);
        btnDeleteBoard.click();
        btnDeleteBoardConfirm.click();
    }


    public boolean validateBoardName(String text, int time){
        return validateTextInElementWait(boardName,text,time);
    }
}
