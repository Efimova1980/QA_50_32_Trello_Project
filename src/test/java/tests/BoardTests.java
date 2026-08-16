package tests;

import dto.Board;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.BoardsPage;
import pages.MyBoardPage;
import utils.TestNGListener;

@Listeners(TestNGListener.class)

public class BoardTests extends AppManager {

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeTest(){
        loginTrello();
    }

    @Test(groups = "smoke")
    public void createAndDeleteNewBoardPositiveTest(){
        String boardTitle = "12345-" + System.currentTimeMillis();
        Board board = Board.builder()
                .boardTitle(boardTitle).build();
        BoardsPage boardsPage = new BoardsPage(getDriver());
        MyBoardPage myBoardPage = boardsPage.createNewBoard(board);
        Assert.assertTrue(myBoardPage.validateBoardName(boardTitle, 15));

        myBoardPage.deleteBoard();
        Assert.assertTrue(boardsPage.validateURL("boards"));
        Assert.assertTrue(boardsPage.validateBoardNotPresent(boardTitle, 10));
    }
}
