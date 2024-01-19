package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private WebDriver driver;

    private By loginHeader = By.xpath("//*[@id=\"root\"]/div/main/div/h2[text()=\"Вход\"]");

    private By emailInput = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[1]/div/div/input");
    private By passwordInput = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input");

    private By loginButton = By.xpath("//*[@id=\"root\"]/div/main/div/form/button");



    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Get login header")
    public By getLoginHeader() {
        return loginHeader;
    }

    @Step("Set fields and click login button")
    public void setFieldsAndClickLoginButton(String email, String password){
        WebElement emailElement = driver.findElement(emailInput);
        WebElement passwordElement = driver.findElement(passwordInput);

        emailElement.sendKeys(email);
        passwordElement.sendKeys(password);

        driver.findElement(loginButton).click();

    }

}
