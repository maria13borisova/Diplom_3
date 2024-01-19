package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    private WebDriver driver;

    public By getPersonalAccountButton() {
        return personalAccountButton;
    }

    private By personalAccountButton = By.xpath("//*[@id=\"root\"]/div/header/nav/a/p");
    private By loginButton = By.xpath("//*[@id=\"root\"]/div/main/section[2]/div/button");

    private By burgerConstructorHeader = By.xpath("//*[@id=\"root\"]/div/main/section[1]/h1");

    private By bunSelectDiv = By.xpath("//*[@id=\"root\"]/div/main/section[1]/div[1]/div[1]");
    private By sauceSelectDiv = By.xpath("//*[@id=\"root\"]/div/main/section[1]/div[1]/div[2]");
    private By fillingSelectDiv = By.xpath("//*[@id=\"root\"]/div/main/section[1]/div[1]/div[3]");

    private String currentSelectDivClass = "tab_tab_type_current__2BEPc";

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public By getBurgerConstructorHeader() {
        return burgerConstructorHeader;
    }

    public By getBunSelectDiv() {
        return bunSelectDiv;
    }

    public By getSauceSelectDiv() {
        return sauceSelectDiv;
    }

    public By getFillingSelectDiv() {
        return fillingSelectDiv;
    }
    @Step("Click personal account button")
    public void clickPersonalAccountButton(){
        WebElement element = driver.findElement(personalAccountButton);
        element.click();
    }

    @Step("Click login button")
    public void clickLoginButton(){
        WebElement element = driver.findElement(loginButton);
        element.click();
    }

    @Step("Click bun section")
    public void clickBunSection(){
        driver.findElement(bunSelectDiv).click();
    }


    @Step("Click sauce section")
    public void clickSauceSection(){
        driver.findElement(sauceSelectDiv).click();
    }


    @Step("Click filling section")
    public void clickFillingSection(){
        driver.findElement(fillingSelectDiv).click();
    }


    @Step("Check ingredient tab is active")
    public boolean checkIngredientTabIsActive(By by){
        WebElement ingredientTabElement = driver.findElement(by);
        String classAttributeValue = ingredientTabElement.getAttribute("class");
        boolean hasActiveClass = classAttributeValue.contains(currentSelectDivClass);
        System.out.println(classAttributeValue);
        System.out.println(hasActiveClass);
        return hasActiveClass;
    }

}
