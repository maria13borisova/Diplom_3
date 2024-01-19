package steps;

import io.qameta.allure.Step;
import pageobjects.LoginPage;
import testdata.UserData;

public class StepsForLogin {

    @Step("Login with correct credentals")
    public void loginWithCorrectCredentals(LoginPage loginPage){
        loginPage.setFieldsAndClickLoginButton(UserData.CORRECT_EMAIL, UserData.CORRECT_PASSWORD);
    }
}
