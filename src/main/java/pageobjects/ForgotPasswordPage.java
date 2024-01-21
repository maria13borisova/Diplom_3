package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPage {
    private WebDriver driver;



    private By loginLink = By.xpath("//*[@id=\"root\"]/div/main/div/div/p/a");

    public ForgotPasswordPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Get login link")
    public By getLoginLink() {
        return loginLink;
    }

    @Step("Click to login link at forgot password page")
    public void clickToLoginLink(){
        WebElement element = driver.findElement(loginLink);
        element.click();
    }

}
