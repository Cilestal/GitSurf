package ua.dp.michaellang.gitsurf.view.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.protectsoft.webviewcode.Codeview;
import com.protectsoft.webviewcode.Settings;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.DownloadUtil;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 14.07.2017
 *
 * @author Michael Lang
 */
public class FileActivity extends ToolbarActivity {

    public static final String EXTRA_OWNER = "ua.dp.michaellang.gitsurf.view.repos.EXTRA_OWNER";
    public static final String EXTRA_REPO = "ua.dp.michaellang.gitsurf.view.repos.EXTRA_REPO";
    public static final String EXTRA_REF = "ua.dp.michaellang.gitsurf.view.repos.EXTRA_REF";
    public static final String EXTRA_PATH = "ua.dp.michaellang.gitsurf.view.repos.EXTRA_PATH";
    public static final String EXTRA_CONTENT = "ua.dp.michaellang.gitsurf.view.repos.EXTRA_CONTENT";

    @BindView(R.id.content_file_webview) WebView mWebView;

    private String mOwner;
    private String mRepoName;
    private String mRef;
    private String mPath;
    private String mContent;

    private String mUrl;
    private String mFileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.content_file);
        ButterKnife.bind(this);
        initExtra();

        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

        String subtitle = getString(R.string.repo_title, mOwner, mRepoName);

        mFileName = StringUtil.getFileName(mPath, '/');
        setToolbarTitle(mFileName);
        mToolbar.setSubtitle(subtitle);
        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_close);
        }

        setContent();
    }

    private void setContent() {
        if (mContent != null) {
            setCodeView();
        } else {
            setImageView();
        }
    }

    private void setImageView() {
        if (StringUtil.isImageFile(mPath)) {
            mUrl = DownloadUtil.getImageUrl(mOwner, mRepoName, mRef, mPath);
            String body = "<img src='" + mUrl + "' />";

            mWebView.loadDataWithBaseURL(null, body,
                    "text/html", "utf-8", null);
        }
    }

    private void setCodeView() {
        mUrl = DownloadUtil.getFileUrl(mOwner, mRepoName, mRef, mPath);

        String extension = StringUtil.getExtension(mPath.toLowerCase());
        String language;

        switch (extension) {
            case ".java":
                language = Settings.Lang.JAVA;
                break;
            case ".c":
            case ".cpp":
            case ".h":
                language = Settings.Lang.CPLUSPLUS;
                break;
            case ".js":
                language = Settings.Lang.JAVASCRIPT;
                break;
            case ".cs":
                language = Settings.Lang.CSHARP;
                break;
            case ".py":
                language = Settings.Lang.PYTHON;
                break;
            case ".rb":
                language = Settings.Lang.RUBY;
                break;
            case ".php":
                language = Settings.Lang.PHP;
                break;
            case ".xml":
            case ".html":
                mContent = mContent.replaceAll("<", "&lt;");
                mContent = mContent.replaceAll(">", "&gt;");
                mContent = "<div class=\"plain\"><pre>" + mContent + "</pre></div>";
                language = Settings.MimeType.TEXT_HTML;
                break;
            default:
                language = Settings.MimeType.TEXT_PLAIN;
                break;
        }

        Codeview.with(this)
                .setStyle(SPUtil.getCodeStyle(this))
                .withCode(mContent)
                .setLang(language)
                .into(mWebView);
    }

    private void initExtra() {
        mOwner = getIntent().getStringExtra(EXTRA_OWNER);
        mContent = getIntent().getStringExtra(EXTRA_CONTENT);
        mRepoName = getIntent().getStringExtra(EXTRA_REPO);
        mPath = getIntent().getStringExtra(EXTRA_PATH);
        mRef = getIntent().getStringExtra(EXTRA_REF);
    }

    public static Intent newIntent(Context context, String owner,
            String repoName, String ref, String content, String path) {
        Intent intent = new Intent(context, FileActivity.class);
        intent.putExtra(EXTRA_OWNER, owner);
        intent.putExtra(EXTRA_REPO, repoName);
        intent.putExtra(EXTRA_REF, ref);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_PATH, path);

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_file_download:
                download();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void download() {
        if(mUrl != null) {
            DownloadUtil.download(this, mUrl, mFileName);
        }
    }

    @Override
    protected boolean isSwipeRefreshEnabled() {
        return false;
    }
}
