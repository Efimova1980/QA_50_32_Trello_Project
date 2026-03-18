package tests;

import data_providers.BoardDP;
import dto.Board;
import dto.User;
import manager.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.BoardsPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MyBoardPage;
import utils.TestNGListener;

@Listeners(TestNGListener.class)

public class BoardTests extends AppManager {
    @BeforeMethod(alwaysRun = true)
    public void login(){
        User user = User.builder()
                .email("efimovaqa2025@gmail.com")
                .password("ae8nrw2a")
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        new LoginPage(getDriver()).login(user);
    }

    @Test(groups = "smoke")
    public void createNewBoardPositiveTest(){
        Board board = Board.builder()
                .boardTitle("12345").build();
        new BoardsPage(getDriver()).createNewBoard(board);
        new MyBoardPage(getDriver()).validateBoardName("12345", 5);
    }

    @Test
    public void check(){
        new BoardsPage(getDriver()).openMyAccount();
    }

    @Test(dataProvider = "dataProviderBoards", dataProviderClass = BoardDP.class)
    public void createNewBoardPositiveTestFromDP(Board board){
        new BoardsPage(getDriver()).createNewBoard(board);
    }
}
