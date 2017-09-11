package ua.dp.michaellang.gitsurf;

/**
 * Date: 22.02.17
 *
 * @author Michael Lang
 */
public interface GitHubConstants {
    String CLIENT_ID = "aacdededd237796b1096";
    String CLIENT_SECRET = "3789f46836c6d8ea2e47ecb0527ed93e20d45673";
    String CALLBACK_URL = "https://testproject-4b7c0.firebaseapp.com/__/auth/handler";

    String APP_TITLE = "GitSurf";
    String APP_URL = "https://github.com/Cilestal/";

    String SCOPES = "gist user repo";

    String FORGOT_URL = "https://github.com/password_reset";
    String SIGN_UP_URL = "https://github.com/join";

    String AUTH_URL = "https://github.com/login/oauth/authorize?";
    String TOKEN_URL = "https://github.com/login/oauth/access_token";

    String CODE_URL = AUTH_URL + "client_id=" + CLIENT_ID + "&redirect_uri="
            + CALLBACK_URL + "&scope=" + SCOPES;

    String AVATAR_URL = "https://avatars1.githubusercontent.com/u/";
    String IMAGE_URL = "https://raw.githubusercontent.com/";
}
