package user.com.openproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    private WebView browser;
    private final Handler handler = new Handler();
    private TextView daum_result;
    private String mData = "hello";

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        public void processDATA(String data) {

            mData = data;

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Bundle extra = new Bundle();
                    Intent intent = new Intent();
                    extra.putString("address", mData);
                    intent.putExtras(extra);
                    setResult(GlobalVariable.REGISTER_ADDRESS_RESULT_CODE, intent);
                    finish();
                }
            });
        }

        @JavascriptInterface
        public void showToast(String data) {
            //Toast.makeText(WebViewActivity.this, data, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("address", data);
            setResult(GlobalVariable.REGISTER_ADDRESS_RESULT_CODE, intent);
            finish();
        }
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WebViewActivity.this, "run", Toast.LENGTH_SHORT).show();
                    daum_result.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    init_webView();
                    Intent intent = getIntent();
                    Toast.makeText(WebViewActivity.this, daum_result.getText().toString(), Toast.LENGTH_SHORT).show();
                    setResult(GlobalVariable.REGISTER_ADDRESS_RESULT_CODE, intent.putExtra("address", daum_result.getText().toString()));
                    finish();
                }
            });
        }

        @JavascriptInterface
        public void setAddr(String addr) {
            Intent intent = new Intent();
            intent.putExtra("address", addr);
            setResult(GlobalVariable.REGISTER_ADDRESS_RESULT_CODE, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        browser = (WebView) findViewById(R.id.webView);
        init_webView();
    }

    private void init_webView() {
        browser.getSettings().setJavaScriptEnabled(true);
        //browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.addJavascriptInterface(new AndroidBridge(), "AddressApp");
        browser.setWebChromeClient(new WebChromeClient());
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                browser.loadUrl("javascript:sample6_execDaumPostcode();");
            }
        });
        browser.loadUrl("http://oseyo2018.000webhostapp.com/address.html");
    }
}
