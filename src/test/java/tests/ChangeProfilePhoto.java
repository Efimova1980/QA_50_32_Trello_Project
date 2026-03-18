package tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AtlassianProfilePage;
import pages.BoardsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestNGListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Listeners(TestNGListener.class)

public class ChangeProfilePhoto extends AppManager {
    BoardsPage boardsPage;

    @BeforeMethod(alwaysRun = true)
    public void login(){
        User user = User.builder()
                .email("efimovaqa2025@gmail.com")
                .password("ae8nrw2a")
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        new LoginPage(getDriver()).login(user);
        boardsPage = new BoardsPage(getDriver());
    }

    @Test(groups = "smoke")
    public void changeProfilePhotoPositiveTest(){
        boardsPage.openMyAccount();
        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(1));
        AtlassianProfilePage atlassianProfilePage = new AtlassianProfilePage(getDriver());
        atlassianProfilePage.changeMyProfilePhoto("src/main/resources/img.png");
        Assert.assertTrue(atlassianProfilePage.validateMessage("We've uploaded your new avatar. It may take a few minutes to display everywhere."));
    }

    @Test
    public void changeProfilePhotoNegativeTest_WrongFormatFile(Method method){
        boardsPage.openMyAccount();
        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(1));
        AtlassianProfilePage atlassianProfilePage = new AtlassianProfilePage(getDriver());
        atlassianProfilePage.changeMyProfilePhoto("src/main/resources/Board1.csv");
        logger.info("upload file csv " + method.getName());
        Assert.assertTrue(atlassianProfilePage.validateWrongFormatFileMessage("Upload a photo or select from some default options"));
    }

}
