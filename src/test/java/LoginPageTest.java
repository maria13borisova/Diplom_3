import constants.Api;
import constants.BurgerUrl;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.*;
import steps.StepsForLogin;
import steps.StepsForUser;
import testdata.UserData;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginPageTest {

    private WebDriver driver;
    private LoginPage loginPage;
    UserAccountPage userAccountPage;
    HomePage homePage;

    StepsForLogin stepsForLogin = new StepsForLogin();
    StepsForUser stepsForUser = new StepsForUser();
    Duration timeout = Duration.ofSeconds(3);


    @Before
    public void openHomePage() {

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
    }

    @Test
    @Description("Check login from main page, user profile button")
    public void loginFromMainPageUserProfileButton(){
        driver.get(BurgerUrl.HOME_PAGE);
        homePage.clickPersonalAccountButton();
        loginAndCheckUserData();
    }


    @Test
    @Description("Check login from main page, login button")
    public void loginFromMainPageLoginButton(){
        driver.get(BurgerUrl.HOME_PAGE);
        homePage.clickLoginButton();
        loginAndCheckUserData();
    }

    @Test
    @Description("Check login from register page, login link")
    public void loginFromRegisterPage(){
        RegisterPage registerPage = new RegisterPage(driver);
        driver.get(BurgerUrl.REGISTER_PAGE);
        registerPage.clickLoginLink();
        loginAndCheckUserData();
    }


    @Test
    @Description("Check login from register page, login link")
    public void loginFromForgotPasswordPage(){
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        driver.get(BurgerUrl.FORGOT_PASSWORD_PAGE);
        forgotPasswordPage.clickToLoginLink();
        loginAndCheckUserData();
    }

    @After
    public void closeBrowser(){
        driver.quit();
        stepsForUser.removeUser();
    }

    @Step("Login with correct credentals and check user data")
    public void loginAndCheckUserData(){
        stepsForLogin.loginWithCorrectCredentals(loginPage);


        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(homePage.getPersonalAccountButton()));

        homePage.clickPersonalAccountButton();
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(userAccountPage.getExitButton()));

        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, is(BurgerUrl.USER_ACCOUNT_PAGE));

        userAccountPage.checkExpectedValues(UserData.CORRECT_EMAIL, UserData.CORRECT_USER_NAME);

    }
}
