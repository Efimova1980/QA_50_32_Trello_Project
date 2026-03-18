package tests;

import dto.Board;
import dto.User;
import manager.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BoardsPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MyBoardPage;

//@Listeners(TestNGListener.class)

public class DeleteBoardTests extends AppManager {

    @BeforeMethod(alwaysRun = true)
    public void login(){
        User user = User.builder()
                .email("efimovaqa2025@gmail.com")
                .password("ae8nrw2a")
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        new LoginPage(getDriver()).login(user);
        Board board = Board.builder()
                .boardTitle("1234").build();
        new BoardsPage(getDriver()).createNewBoard(board);
    }

    @Test(groups = "smoke")
    public void deleteBoardPositiveTest(){
        new MyBoardPage(getDriver()).validateBoardName("1234", 5);
        new MyBoardPage(getDriver()).deleteBoard();
    }
}
