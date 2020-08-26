package com.app.tools;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.tool.Tools;

import java.util.Map;

import static com.app.tools.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_LOG_CONTENT_SIZE =1024 * 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

    }

    private static class BaseClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //获取html
            view.loadUrl("javascript:window.java_obj.showSource("+
                    "document.getElementsByTagName('html')[0].innerHTML);");
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                LOGV("noah",request.getMethod());
                LOGV("noah",request.getUrl().toString());
                Map<String, String> requestHeaders = request.getRequestHeaders();
                for (String key : requestHeaders.keySet()) {
                    LOGV("noah", Tools.Strings.join("key:",key," Headers:",requestHeaders.get(key)));
                }
            }
            return super.shouldInterceptRequest(view, request);
        }
    }

    public final class InJavaScriptLocalObj{

        @JavascriptInterface
        public void showSource(String html){
            LOGE("noah",html);
        }
    }

    public static final void LOGE(String TAG,String msg){
        String message = msg;
        while(message.length() > MAX_LOG_CONTENT_SIZE){
            Log.v(TAG,message.substring(0, MAX_LOG_CONTENT_SIZE));
            message = message.substring(MAX_LOG_CONTENT_SIZE);
        }
        Log.e(TAG,message);
    }
    public static final void LOGV(String TAG,String msg){
        String message = msg;
        while(message.length() > MAX_LOG_CONTENT_SIZE){
            Log.v(TAG,message.substring(0, MAX_LOG_CONTENT_SIZE));
            message = message.substring(MAX_LOG_CONTENT_SIZE);
        }
        Log.v(TAG,message);
    }
}
