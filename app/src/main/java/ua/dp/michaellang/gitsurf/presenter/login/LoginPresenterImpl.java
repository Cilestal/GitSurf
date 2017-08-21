package ua.dp.michaellang.gitsurf.presenter.login;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.os.AsyncTaskCompat;
import com.google.gson.Gson;
import okhttp3.*;
import org.eclipse.egit.github.core.Authorization;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OAuthService;
import ua.dp.michaellang.gitsurf.GitHubConstants;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.loader.callbacks.UserLoaderCallbacks;
import ua.dp.michaellang.gitsurf.presenter.BasePresenterImpl;
import ua.dp.michaellang.gitsurf.services.entity.Token;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

import static ua.dp.michaellang.gitsurf.GitHubConstants.*;

/**
 * Date: 05.03.17
 *
 * @author Michael Lang
 */
public class LoginPresenterImpl extends BasePresenterImpl
        implements LoginPresenter {

    public static final String TAG = LoginPresenterImpl.class.toString();

    private static final int LOADER_USER = 0;

    private final LoginView mView;
    private final UserLoaderCallbacks mCallbacks;

    public LoginPresenterImpl(Context context, LoginView view) {
        super(view);
        mView = view;
        mCallbacks = new UserLoaderCallbacks(this, context, null) {
            @Override
            protected void onResultReady(int id, User result) {
                mView.onUserLoaded(result);
            }
        };
    }

    @Override
    public void auth(String login, String password) {
        mView.showProgress(true);
        AsyncTaskCompat.executeParallel(new AuthTask(), login, password);
    }

    @Override
    public void auth(String code) {
        mView.showProgress(true);
        AsyncTaskCompat.executeParallel(new AuthTask(), code);
    }

    private String getToken(String login, String pass) throws IOException {
        String appName = GitHubConstants.APP_TITLE;

        GitHubClient client = new GitHubClient();
        client.setCredentials(login, pass);
        client.setUserAgent(appName);

        String description = String.format("%s: %s %s", appName, Build.MANUFACTURER, Build.MODEL);

        OAuthService authService = new OAuthService(client);
        for (Authorization authorization : authService.getAuthorizations()) {
            String note = authorization.getNote();

            if (note != null && note.equals(description)) {
                authService.deleteAuthorization(authorization.getId());
            }
        }

        Authorization auth = new Authorization();

        auth.setNote(description);
        auth.setUrl(GitHubConstants.APP_URL);
        String[] scopes = GitHubConstants.SCOPES.split(" ");
        auth.setScopes(Arrays.asList(scopes));

        return authService.createAuthorization(auth).getToken();
    }

    private String getToken(final String code) throws IOException {
        RequestBody body = new FormBody.Builder()
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .add("code", code)
                .build();

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .post(body)
                .build();

        final OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) throw new AccessTokenException();

        String jsonData = response.body().string();
        Gson gson = new Gson();
        Token token = gson.fromJson(jsonData, Token.class);

        return token.getAccess_token();
    }

    @Override
    public void loadUser() {
        mView.getLoader()
                .initLoader(LOADER_USER, null, mCallbacks);
    }

    private class AuthTask extends AsyncTask<String, Void, String> {
        private Exception mException;

        @Override
        protected String doInBackground(String... params) {
            try {
                if (params.length == 1) {
                    return getToken(params[0]);
                } else if (params.length == 2) {
                    return getToken(params[0], params[1]);
                }
            } catch (Exception e) {
                mException = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String accessToken) {
            if (mException != null) {
                if (mException instanceof UnknownHostException) {
                    mView.onLoadFail();
                } else if (mException instanceof AccessTokenException) {
                    mView.onError(R.string.error_failed_get_access_token);
                } else {
                    mView.onError(R.string.error_incorrect_password);
                }
            } else {
                mView.onLoginSuccess(accessToken);
            }
        }
    }
}
