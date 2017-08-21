package ua.dp.michaellang.gitsurf.view.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.*;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ua.dp.michaellang.gitsurf.GitHubConstants;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.presenter.login.LoginView;

import static ua.dp.michaellang.gitsurf.GitHubConstants.CODE_URL;

/**
 * Date: 22.02.17
 *
 * @author Michael Lang
 */
public class GitHubAuthDialog extends DialogFragment {
    public static final String TAG = "GitHubDialog";

    private LoginView mView;
    private ProgressDialog mProgressDialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_oauth2, container, false);
        WebView webView = (WebView) view.findViewById(R.id.dialog_oauth2_webview);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setMessage(getString(R.string.loading));

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        v.requestFocusFromTouch();
                        break;
                }
                return false;
            }

        });

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new OAuthWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(CODE_URL);

        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mView = (LoginView) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Class must implement LoginView");
        }
    }

    public static GitHubAuthDialog newInstance() {
        return new GitHubAuthDialog();
    }

    private class OAuthWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "Loading URL: " + url);

            super.onPageStarted(view, url, favicon);
            mProgressDialog.show();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            Log.d(TAG, "Page error: " + description);
            mView.onLoadFail();
            dismiss();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (url.startsWith(GitHubConstants.CALLBACK_URL)) {
                String urls[] = url.split("=");
                mView.onDialogComplete(urls[1]);//urls[1] - code
                dismiss();
            }

            mProgressDialog.dismiss();
        }
    }
}
