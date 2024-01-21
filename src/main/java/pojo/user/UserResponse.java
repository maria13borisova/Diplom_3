package pojo.user;

public class UserResponse {
    private boolean success;
    private pojo.user.UserLogin user;
    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

}