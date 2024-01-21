package pageobjects;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserAccountPage {
    private WebDriver driver;

    private By userName = By.xpath("//*[@id=\"root\"]/div/main/div/div/div/ul/li[1]/div/div/input");
    private By userEmail = By.xpath("//*[@id=\"root\"]/div/main/div/div/div/ul/li[2]/div/div/input");

    private By burgerLogo = By.xpath("//*[@id=\"root\"]/div/header/nav/div/a");
    private By burgerConstructor = By.xpath("//*[@id=\"root\"]/div/header/nav/ul/li[1]/a");

    private By exitButton = By.xpath("//*[@id=\"root\"]/div/main/div/nav/ul/li[3]/button");

    public UserAccountPage(WebDriver driver){
        this.driver = driver;
    }

    public By getExitButton() {
        return exitButton;
    }

    @Step("Check expected email and name values at user account page")
    public void checkExpectedValues(String email, String name){
        WebElement emailElement = driver.findElement(userEmail);
        WebElement nameElement = driver.findElement(userName);

        assertThat(emailElement.getAttribute("value"), is(email));
        assertThat(nameElement.getAttribute("value"), is(name));
    }


    @Step("Click burger logo")
    public void clickBurgerLogo(){
        WebElement element = driver.findElement(burgerLogo);
        element.click();
    }

    @Step("Click burger constructor")
    public void clickBurgerConstructor(){
        WebElement element = driver.findElement(burgerConstructor);
        element.click();
    }
}
