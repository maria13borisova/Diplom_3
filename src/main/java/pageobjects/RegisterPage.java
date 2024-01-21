package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage {

    private WebDriver driver;

    public RegisterPage(WebDriver driver){
        this.driver = driver;
    }

    private By nameInput = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[1]/div/div/input");
    private By emailInput = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input");
    private By passwordInput = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/div/input");

    private By registerButton = By.xpath("//*[@id=\"root\"]/div/main/div/form/button");

    private By incorrectPasswordMessage = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/p[text()=\"Некорректный пароль\"]");

    private By loginLink = By.xpath("//*[@id=\"root\"]/div/main/div/div/p/a[text()=\"Войти\"]");

    /* ввести данные в поле */
    @Step("Set value to input")
    public void setValueToNameInput(By inputName, String value){
        WebElement element = driver.findElement(inputName);
        element.sendKeys(value);
    }

    /* ввести все данные для регистрации */
    @Step("Set value to all inputs")
    public void setValuesToAllInputs(String name, String email, String password){
        setValueToNameInput(getNameInput(), name);
        setValueToNameInput(getEmailInput(), email);
        setValueToNameInput(getPasswordInput(), password);
    }

    /* кликнуть кнопку регистрации */
    public void clickRegisterButton(){
        driver.findElement(registerButton).click();
    }


    /* геттеры для локаторов полей ввода */
    @Step("Get name input")
    public By getNameInput() {
        return nameInput;
    }

    @Step("Get email input")
    public By getEmailInput() {
        return emailInput;
    }

    @Step("Get password input")
    public By getPasswordInput() {
        return passwordInput;
    }

    @Step("Get incorrect password message")
    public By getIncorrectPasswordMessage() {
        return incorrectPasswordMessage;
    }

    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    @Step("Get incorrect password message text")
    public String getIncorrectPasswordMessageText(){
        WebElement element = driver.findElement(incorrectPasswordMessage);
        return element.getText();
    }

}
