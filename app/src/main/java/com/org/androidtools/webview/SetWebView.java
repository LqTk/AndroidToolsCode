package com.org.androidtools.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.org.androidtools.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SetWebView extends AppCompatActivity {

    @BindView(R.id.web)
    WebView webView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Unbinder unBind;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_web_view);
        unBind = ButterKnife.bind(this);
        context = this;
        initWeb();
    }

    private void initWeb() {
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // 支持localStorage
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        WebSettings ws = webView.getSettings();
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = webView.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(webView.getSettings(), true);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        webView.setWebChromeClient(new MyWebViewClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //                if (hud == null)
                //                    hud = ProgressHUD.show(getContext(), "加载中...", true, true, null);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //                super.onReceivedSslError(view, handler, error);
                handler.proceed();
                //                wvReportDetail.setVisibility(View.GONE);
                //                empty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webView.setVisibility(View.GONE);
            }

            //web页面内点击事件
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                return true;
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        //        loadWebPage();
        webView.addJavascriptInterface(new JsInterface(), "Android");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind.unbind();
    }

    /**
     * JS调用 web页面点击和app交互事件，方法名为事件名
     */
    public class JsInterface {
        @JavascriptInterface
        public void showShareReportBtn() {

        }

        @JavascriptInterface
        public void buyReportAnalysisSvc(String reportId) {

        }
    }

    class MyWebViewClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title == null) {
                return;
            }
            if (TextUtils.isEmpty(title)) {
                tvTitle.setText("");
            } else {
                tvTitle.setText(title);
                isShowProgress = false;
            }
            Log.e("viewTitle:", title);
        }

        boolean isShowProgress = true;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (tvTitle == null) {
                return;
            }
            Log.d("viewTitle:", view.getTitle());
            if (isShowProgress) {
                tvTitle.setText("正在加载 " + newProgress + "%");
            }
            if (newProgress == 100) {
                if (TextUtils.isEmpty(view.getTitle())) {
                    tvTitle.setText("测量报告");
                    tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                } else {
                    tvTitle.setText(view.getTitle());
                    Log.d("viewTitle:", view.getTitle());
                    tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                }
                return;
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            // 对alert的简单封装
            new AlertDialog.Builder(context).setTitle("提示").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    System.out.println("测试");
                }
            }).create().show();
            result.confirm(); // 处理来自用户的确认回复。
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            // 对alert的简单封装
            new AlertDialog.Builder(context).setTitle("提示").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    System.out.println("测试1");
                }
            }).create().show();
            result.confirm(); // 处理来自用户的确认回复。
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            // 对alert的简单封装
            new AlertDialog.Builder(context).setTitle("提示" + defaultValue).setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            System.out.println("测试");
                        }
                    }).create().show();
            result.confirm(); // 处理来自用户的确认回复。
            return true;
        }

        @Override
        public void onRequestFocus(WebView view) {
            super.onRequestFocus(view);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
        }
    }

}
