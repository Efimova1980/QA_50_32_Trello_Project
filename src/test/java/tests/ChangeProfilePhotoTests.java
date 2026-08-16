package tests;

import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AtlassianProfilePage;
import pages.BoardsPage;
import utils.TestNGListener;

import java.util.ArrayList;
import java.util.List;

@Listeners(TestNGListener.class)

public class ChangeProfilePhotoTests extends AppManager {
    BoardsPage boardsPage;

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeTest(){
        loginTrello();
        boardsPage = new BoardsPage(getDriver());
    }

    @Test(groups = "smoke")
    public void changeProfilePhotoPositiveTest(){
        AtlassianProfilePage atlassianProfilePage = changeProfilePhoto("src/main/resources/img.png");
        Assert.assertTrue(atlassianProfilePage
                .validateMessage("We've uploaded your new avatar. It may take a few minutes to display everywhere."));
    }

    @Test
    public void changeProfilePhotoNegativeTest_WrongFormatFile(){
        AtlassianProfilePage atlassianProfilePage = changeProfilePhoto("src/main/resources/Board1.csv");
        Assert.assertTrue(atlassianProfilePage
                .validateWrongFormatFileMessage("Upload a photo or select from some default options"));
    }

    private AtlassianProfilePage changeProfilePhoto(String photoPath){
        boardsPage.openMyAccount();
        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(1));
        AtlassianProfilePage atlassianProfilePage = new AtlassianProfilePage(getDriver());
        atlassianProfilePage.changeMyProfilePhoto(photoPath);
        return atlassianProfilePage;
    }
}
