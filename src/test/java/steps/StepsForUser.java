package steps;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Assert;
import constants.Api;
import constants.ContentType;
import pojo.user.UserLogin;
import pojo.user.UserRegister;
import pojo.user.UserResponse;
import testdata.UserData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StepsForUser {

    public StepsForUser(){

    }
    /* Получить ответ от сервиса Регистрации */
    @Step("Get response from API Register user")
    public Response getResponseFromApiCreate(UserRegister user){
        Response response = given()
                .header("Content-type", ContentType.CONTENT_TYPE)
                .and()
                .body(user)
                .when()
                .post(Api.USER_REGISTER_API_ENDPOINT);
        return response;
    }

    /* Получить ответ от сервиса Логина */
    @Step("Get response from API Login user")
    public Response getResponseFromApiLogin(UserLogin user){
        Response response = given()
                .header("Content-type", ContentType.CONTENT_TYPE)
                .and()
                .body(user)
                .when()
                .post(Api.USER_LOGIN_API_ENDPOINT);
        return response;
    }

    /* Создать UserRegister с корректными значениями */
    @Step("Create correct user register object")
    public UserRegister createCorrectUserRegister(){
        return new UserRegister(UserData.CORRECT_EMAIL, UserData.CORRECT_PASSWORD, UserData.CORRECT_USER_NAME);
    }

    /* Создать UserLogin с корректными значениями */
    @Step("Create correct user login object")
    public UserLogin createCorrectUserLogin(){
        return new UserLogin(UserData.CORRECT_EMAIL, UserData.CORRECT_PASSWORD);
    }

    /* получить токен авторизации */
    @Step("Get Bearer token from response")
    public String getBearerTokenFromResponse(Response response){
        String responseBody = response.getBody().asString();
        Gson gson = new Gson();
        UserResponse userResponse = gson.fromJson(responseBody, UserResponse.class);
        String accessToken = userResponse.getAccessToken();
        return accessToken;
    }

    /* Удаление пользователя после тестов */
    @Step("Remove user after test")
    public void removeUserByBearer(String userBearer){
        given()
                .header("Authorization", userBearer)
                .when()
                .delete(Api.USER_DELETE_OR_PATCH_API_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(202)
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));
    }

    /* Регистрация пользователя для тестов не на регистрацию */
    @Step("Create user before test")
    public void createUser(){
        UserRegister user = createCorrectUserRegister();
        getResponseFromApiCreate(user);
        System.out.println("User created");
    }

    @Step("Remove user after test")
    public void removeUser(){
        UserLogin user = createCorrectUserLogin();
        Response response = getResponseFromApiLogin(user);
        String userBearer = getBearerTokenFromResponse(response);
        removeUserByBearer(userBearer);
        System.out.println("User removed");
    }
}
