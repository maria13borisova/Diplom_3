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
import pageobjects.HomePage;
import steps.StepsForUser;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class HomePageTest {

    private WebDriver driver;
    HomePage homePage;

    StepsForUser stepsForUser = new StepsForUser();
    Duration timeout = Duration.ofSeconds(3);

    @Before
    public void createUserAndLogin(){
        RestAssured.baseURI= Api.BASE_URI;

        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.silentOutput", "true");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        homePage = new HomePage(driver);
        driver.get(BurgerUrl.HOME_PAGE);
    }


    @Test
    @Description("Check bun section click")
    public void checkBun(){
        /* булки изначально выбраны, поэтому сначала переключимся на другой раздел */
        homePage.clickFillingSection();

        homePage.clickBunSection();

        boolean checkBunSectionIsActive = homePage.checkIngredientTabIsActive(homePage.getBunSelectDiv());

        assertTrue(checkBunSectionIsActive);
    }


    @Test
    @Description("Check filling section click")
    public void checkFilling(){
        homePage.clickFillingSection();
        boolean checkFillingSectionIsActive = homePage.checkIngredientTabIsActive(homePage.getFillingSelectDiv());

        assertTrue(checkFillingSectionIsActive);
    }

    @Test
    @Description("Check sauce section click")
    public void checkSauce(){
        homePage.clickSauceSection();
        boolean checkSauceSectionIsActive = homePage.checkIngredientTabIsActive(homePage.getSauceSelectDiv());

        assertTrue(checkSauceSectionIsActive);
    }


    @After
    public void closeBrowser(){
        driver.quit();
    }
}
