package ua.dp.michaellang.gitsurf.view.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GithubAuthProvider;
import ua.dp.michaellang.gitsurf.Constants;
import ua.dp.michaellang.gitsurf.GitSurfApplication;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.presenter.login.LoginPresenter;
import ua.dp.michaellang.gitsurf.presenter.login.LoginPresenterImpl;
import ua.dp.michaellang.gitsurf.presenter.login.LoginView;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

import java.util.regex.Pattern;

import static ua.dp.michaellang.gitsurf.GitHubConstants.FORGOT_URL;
import static ua.dp.michaellang.gitsurf.GitHubConstants.SIGN_UP_URL;

/**
 * A login screen that offers login via email/password.
 * <p>
 * Date: 23.02.17
 *
 * @author Michael Lang
 */
public class LoginActivity extends ToolbarActivity implements LoginView {
    private final String TAG = getClass().getName();

    private static final String DIALOG_GITHUB_OAUTH2 = "DialogGitHubAuth";

    @BindView(R.id.content_login_username) AutoCompleteTextView mUsernameView;
    @BindView(R.id.content_login_password) EditText mPasswordView;

    private LoginPresenter mPresenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GitSurfApplication.isAuthorized()) {
            goToTopLevelActivity();
        }

        setContent(R.layout.content_login);
        showHomeButton();
        mPresenter = new LoginPresenterImpl(this);
        ButterKnife.bind(LoginActivity.this);
    }

    @Override
    protected boolean isSwipeRefreshEnabled() {
        return false;
    }

    @OnEditorAction(R.id.content_login_password)
    boolean passwordOnEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.content_login_password || id == EditorInfo.IME_NULL) {
            attemptOauthLogin();
            return true;
        }
        return false;
    }

    @OnClick(R.id.content_login_sign_in_button)
    void attemptOauthLogin() {
        hideSnackBar();

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mPasswordView.setText("");
            mPresenter.auth(username, password);
        }
    }

    @OnClick(R.id.content_login_oauth2_button)
    void attemptLoginWithGitHub() {
        hideSnackBar();
        GitHubAuthDialog authDialog = GitHubAuthDialog.newInstance();

        boolean isLargeLayout = getResources().getBoolean(R.bool.large_layout);

        if (isLargeLayout) {
            authDialog.show(getSupportFragmentManager(), DIALOG_GITHUB_OAUTH2);
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(android.R.id.content, authDialog)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @OnClick(R.id.content_login_forgot_password_button)
    void onForgotPasswordClick() {
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(FORGOT_URL));
        startActivity(launchBrowser);
    }

    private boolean isUsernameValid(String username) {
        return Pattern.matches("^[a-zA-Z-]{3,32}$", username);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    @Override
    public void onLoginSuccess(final String token) {
        Log.d(TAG, "Auth Complete.");
        AuthCredential credential = GithubAuthProvider.getCredential(token);
        FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String username = task.getResult()
                                .getAdditionalUserInfo()
                                .getUsername();

                        SPUtil.putString(LoginActivity.this, Constants.Prefs.User.TOKEN, token);
                        SPUtil.putString(LoginActivity.this, Constants.Prefs.User.LOGIN, username);

                        goToTopLevelActivity();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onAuthError();
                    }
                });
    }

    @Override
    public void onError(@StringRes int resId) {
        showProgress(false);
        Snackbar.make(mContentRoot, resId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public LoaderManager getLoader() {
        return getSupportLoaderManager();
    }

    @Override
    public void onDialogComplete(String code) {
        mPresenter.auth(code);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_login_sign_up:
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(SIGN_UP_URL));
                startActivity(launchBrowser);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

