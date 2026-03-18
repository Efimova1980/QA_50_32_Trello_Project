package pages;

import dto.Board;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BoardsPage extends BasePage{
    public BoardsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//button[@data-testid='create-board-tile']")
    WebElement btnCreateNewBoard;
    @FindBy(xpath = "//input[@data-testid='create-board-title-input']")
    WebElement inputBoardTitle;
    @FindBy(xpath = "//button[@data-testid='create-board-submit-button']")
    WebElement btnCreateNewBoardSubmit;
    @FindBy(xpath = "//span[@title='Olga Efimova (efimovaqa2025)']")
    WebElement btnAccaunt;
    @FindBy(xpath = "//a[@data-testid='manage-account-link']")
    WebElement btnManageAccountLink;

    public void createNewBoard(Board board){
        btnCreateNewBoard.click();
        inputBoardTitle.sendKeys(board.getBoardTitle());
        clickWait(btnCreateNewBoardSubmit, 5);
    }

    public void openMyAccount(){
        clickWait(btnAccaunt, 5);
        clickWait(btnManageAccountLink,5);
    }


}
