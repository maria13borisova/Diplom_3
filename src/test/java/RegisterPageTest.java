import constants.Api;
import constants.BurgerUrl;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.LoginPage;
import pageobjects.RegisterPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.StepsForUser;
import testdata.UserData;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterPageTest {
    private WebDriver driver;
    private RegisterPage registerPage;
    StepsForUser steps = new StepsForUser();
    private boolean userIsCreated = false;
    Duration timeout = Duration.ofSeconds(3);


    @Before
    public void openHomePage() {

        RestAssured.baseURI= Api.BASE_URI;

        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.silentOutput", "true");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get(BurgerUrl.REGISTER_PAGE);
        registerPage = new RegisterPage(driver);

        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(registerPage.getEmailInput()));
    }


    @Test
    @Description("Register user with correct values")
    public void registerUserIsSuccess(){
        registerPage.setValuesToAllInputs(
                UserData.CORRECT_USER_NAME,
                UserData.CORRECT_EMAIL,
                UserData.CORRECT_PASSWORD);
        registerPage.clickRegisterButton();

        LoginPage loginPage = new LoginPage(driver);

        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(loginPage.getLoginHeader()));

        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, is(BurgerUrl.LOGIN_PAGE));
        userIsCreated = true;
    }


    @Test
    @Description("Short password register is failed")
    public void registerUserIsFailedIncorrectPassword(){
        registerPage.setValuesToAllInputs(
                UserData.CORRECT_USER_NAME,
                UserData.CORRECT_EMAIL,
                UserData.SHORT_PASSWORD);
        registerPage.clickRegisterButton();

        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(registerPage.getIncorrectPasswordMessage()));
        String expectMessageText = "Некорректный пароль";
        assertThat(registerPage.getIncorrectPasswordMessageText(), is(expectMessageText));
    }

    @After
    public void closeBrowser(){
        driver.quit();
        if(userIsCreated){
            steps.removeUser();
            userIsCreated = false;
        }
    }

}
