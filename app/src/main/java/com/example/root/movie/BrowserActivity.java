package com.example.root.movie;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.root.movie.constant.MovieConstant;
import com.example.root.movie.net.DBAPI;
import com.example.root.movie.net.MovieOkhttp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowserActivity extends AppCompatActivity {

    public static final String TAG = BrowserActivity.class.getSimpleName();

    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.login)
    WebView webView;
    String Token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //noinspection deprecation
            CookieManager.getInstance().removeAllCookie();
        } else {
            CookieManager.getInstance().removeAllCookies(null);
        }
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new LoginWebViewClient(this));
        webView.clearCache(true);
        new TokenAsyncTask(this).execute();
    }

    public void setRequestToken(final String TMDBToken){
        Token = TMDBToken;
    }

    private static  class TokenAsyncTask extends AsyncTask<Void,Void,String>{
        WebView webView;

        private final BrowserActivity mActivity;
        public TokenAsyncTask(final BrowserActivity activity) {
            super();
            this.webView = activity.webView;
            mActivity = activity;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return MovieOkhttp.getToken();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(TAG,s);
            mActivity.setRequestToken(s);
            webView.loadUrl(DBAPI.ASK_PREMISSION+s);
        }
    }

    private class LoginWebViewClient extends WebViewClient{
        ProgressBar mprogressBar;
        private final BrowserActivity mActivity;
        public LoginWebViewClient(final BrowserActivity activity) {
            super();
            this.mprogressBar = activity.loading;
            mActivity = activity;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            IsShowLoading(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            IsShowLoading(false);
            if (url.contains("allow")){
                //获得授权
                Intent i = new Intent();
                i.putExtra(MovieConstant.EXTRA_TOKEN,mActivity.Token);
                mActivity.setResult(Activity.RESULT_OK,i);
                mActivity.finish();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mActivity.finish();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri.getPath().contains("account")){
                //重定向到授权页面
                view.loadUrl(DBAPI.ASK_PREMISSION+mActivity.Token);
            }else {
                view.loadUrl(url);
            }
            return true;
        }

        private void IsShowLoading(final boolean shown){
            mprogressBar.setVisibility((shown? View.VISIBLE:View.GONE));
        }
    }

}
