import constants.Api;
import constants.BurgerUrl;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.UserAccountPage;
import steps.StepsForLogin;
import steps.StepsForUser;
import testdata.UserData;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class UserAccountTest {
    private WebDriver driver;
    private LoginPage loginPage;
    UserAccountPage userAccountPage;
    HomePage homePage;

    StepsForLogin stepsForLogin = new StepsForLogin();
    StepsForUser stepsForUser = new StepsForUser();
    Duration timeout = Duration.ofSeconds(3);

    private String expectBurgerHeaderText = "Соберите бургер";

    @Before
    public void createUserAndLogin(){
        RestAssured.baseURI= Api.BASE_URI;

        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.silentOutput", "true");
        //options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        stepsForUser.createUser();

        loginPage = new LoginPage(driver);
        userAccountPage = new UserAccountPage(driver);
        homePage = new HomePage(driver);

        driver.get(BurgerUrl.LOGIN_PAGE);
        stepsForLogin.loginWithCorrectCredentals(loginPage);

        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(homePage.getPersonalAccountButton()));

        homePage.clickPersonalAccountButton();
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(userAccountPage.getExitButton()));
    }

    @Test
    @Description("Check user account page is opening")
    public void checkUserPageIsOpening(){
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, is(BurgerUrl.USER_ACCOUNT_PAGE));
        userAccountPage.checkExpectedValues(UserData.CORRECT_EMAIL, UserData.CORRECT_USER_NAME);
    }


    @Test
    @Description("Click to logo link from user profile")
    public void clickLogoFromUserProfile(){
        userAccountPage.clickBurgerLogo();

        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(homePage.getBurgerConstructorHeader()));

        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, is(BurgerUrl.HOME_PAGE + "/"));

        String actualBurgerHeaderText = driver.findElement(homePage.getBurgerConstructorHeader()).getText();
        assertEquals(expectBurgerHeaderText, actualBurgerHeaderText);
    }


    @Test
    @Description("Go to logo link from user profile")
    public void clickBurgerConstructorFromUserProfile(){
        userAccountPage.clickBurgerConstructor();

        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(homePage.getBurgerConstructorHeader()));

        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, is(BurgerUrl.HOME_PAGE + "/"));

        String actualBurgerHeaderText = driver.findElement(homePage.getBurgerConstructorHeader()).getText();
        assertEquals(expectBurgerHeaderText, actualBurgerHeaderText);
    }





    @Test
    @Description("Check exit button at user account page")
    public void checkExitButton(){
        driver.findElement(userAccountPage.getExitButton()).click();

        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(loginPage.getLoginHeader()));

        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, is(BurgerUrl.LOGIN_PAGE));
    }



    @After
    public void closeBrowser(){
        driver.quit();
        stepsForUser.removeUser();
    }
}
