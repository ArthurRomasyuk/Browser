package com.tricks4speed.skbrowser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Proxy;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.HttpAuthHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

public class MySimpleBrowserActivity extends Activity implements
		OnClickListener {

	private WebView webView;
	private ImageButton bAddress, bKeyword;
	private Button bBack, bForward, bOption, bRefresh;
	private EditText addr, keyword;
	private String[] agents = {
			"Mozilla/5.0 (Android 4.0.4; Mobile; rv:55.0) Gecko/55.0 Firefox/55.0",
			"Mozilla/5.0 (Android 4.0.4; Tablet; rv:42.0) Gecko/42.0 Firefox/42.0",
			"Mozilla/5.0 (Android 4.1.2; Mobile; rv:42.0) Gecko/42.0 Firefox/42.0",
			"Mozilla/5.0 (Android 4.2.2; Mobile; rv:58.0) Gecko/58.0 Firefox/58.0",
			"Mozilla/5.0 (Android 4.2.2; Tablet; rv:44.0) Gecko/44.0 Firefox/44.0",
			"Mozilla/5.0 (Android 4.3; Mobile; rv:42.0) Gecko/42.0 Firefox/42.0",
			"Mozilla/5.0 (Android 4.4.2; Mobile; rv:41.0) Gecko/41.0 Firefox/41.0",
			"Mozilla/5.0 (Android 4.4.2; Mobile; rv:42.0) Gecko/42.0 Firefox/42.0",
			"Mozilla/5.0 (Android 4.4.2; Mobile; rv:44.0) Gecko/44.0 Firefox/44.0",
			"Mozilla/5.0 (Android 4.4.2; Mobile; rv:45.0) Gecko/45.0 Firefox/45.0",
			"Mozilla/5.0 (Android 4.4.2; Tablet; rv:45.0) Gecko/45.0 Firefox/45.0",
			"Mozilla/5.0 (Android 4.4.2; Tablet; rv:56.0) Gecko/56.0 Firefox/56.0",
			"Mozilla/5.0 (Android 4.4.4; Mobile; rv:43.0) Gecko/43.0 Firefox/43.0",
			"Mozilla/5.0 (Android 4.4.4; Mobile; rv:44.0) Gecko/44.0 Firefox/44.0",
			"Mozilla/5.0 (Android 5.0; Mobile; rv:42.0) Gecko/42.0 Firefox/42.0",
			"Mozilla/5.0 (Android 5.0; Mobile; rv:43.0) Gecko/43.0 Firefox/43.0",
			"Mozilla/5.0 (Android 5.0; Mobile; rv:45.0) Gecko/45.0 Firefox/45.0",
			"Mozilla/5.0 (Android 5.0; Mobile; rv:57.0) Gecko/57.0 Firefox/57.0",
			"Mozilla/5.0 (Android 5.0.1; Mobile; rv:53.0) Gecko/53.0 Firefox/53.0",
			"Mozilla/5.0 (Android 5.0.2; Mobile; rv:42.0) Gecko/42.0 Firefox/42.0",
			"Mozilla/5.0 (Android 5.0.2; Mobile; rv:44.0) Gecko/44.0 Firefox/44.0",
			"Mozilla/5.0 (Android 5.0.2; Mobile; rv:57.0) Gecko/57.0 Firefox/57.0",
			"Mozilla/5.0 (Android 5.1; Mobile; rv:41.0) Gecko/41.0 Firefox/41.0",
			"Mozilla/5.0 (Android 5.1; Mobile; rv:42.0) Gecko/42.0 Firefox/42.0",
			"Mozilla/5.0 (Android 5.1; Mobile; rv:43.0) Gecko/43.0 Firefox/43.0",
			"Mozilla/5.0 (Android 5.1; Mobile; rv:44.0) Gecko/44.0 Firefox/44.0",
			"Mozilla/5.0 (Android 5.1; Mobile; rv:53.0) Gecko/53.0 Firefox/53.0",
			"Mozilla/5.0 (Android 5.1; Mobile; rv:57.0) Gecko/57.0 Firefox/57.0",
			"Mozilla/5.0 (Android 5.1.1; Mobile; rv:41.0) Gecko/41.0 Firefox/41.0",
			"Mozilla/5.0 (Android 5.1.1; Mobile; rv:42.0) Gecko/42.0 Firefox/42.0",
			"Mozilla/5.0 (Android 5.1.1; Mobile; rv:43.0) Gecko/43.0 Firefox/43.0",
			"Mozilla/5.0 (Android 5.1.1; Mobile; rv:44.0) Gecko/44.0 Firefox/44.0",
			"Mozilla/5.0 (Android 5.1.1; Mobile; rv:45.0) Gecko/45.0 Firefox/45.0",
			"Mozilla/5.0 (Android 5.1.1; Mobile; rv:46.0) Gecko/46.0 Firefox/46.0",
			"Mozilla/5.0 (Android 5.1.1; Mobile; rv:56.0) Gecko/56.0 Firefox/56.0",
			"Mozilla/5.0 (Android 5.1.1; Mobile; rv:57.0) Gecko/57.0 Firefox/57.0",
			"Mozilla/5.0 (Android 6.0; Mobile; rv:44.0) Gecko/44.0 Firefox/44.0",
			"Mozilla/5.0 (Android 6.0; Mobile; rv:46.0) Gecko/46.0 Firefox/46.0",
			"Mozilla/5.0 (Android 6.0; Mobile; rv:54.0) Gecko/54.0 Firefox/54.0",
			"Mozilla/5.0 (Android 6.0; Mobile; rv:55.0) Gecko/55.0 Firefox/55.0",
			"Mozilla/5.0 (Android 6.0; Mobile; rv:56.0) Gecko/56.0 Firefox/56.0",
			"Mozilla/5.0 (Android 6.0; Mobile; rv:57.0) Gecko/57.0 Firefox/57.0",
			"Mozilla/5.0 (Linux; Android 6.0; Lenovo A2016a40 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/60.0.3112.116 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 6.0; Lenovo A2016a40 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/62.0.3202.84 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 6.0; Lenovo A2016a40 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.111 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 6.0; Lenovo A2016a40 Build/MRA58K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.111 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 6.0; Lenovo A2016b30 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/48.0.2564.106 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 6.0; Lenovo A2016b30 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.83 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 6.0; Lenovo A6600a40 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/51.0.2704.81 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 6.0; Lenovo A6600d40 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/51.0.2704.81 Mobile Safari/537.36"
	};

	private WebSettings webSettings;

	private class MyWebViewClient extends WebViewClient {

		@Override
		public void onReceivedHttpAuthRequest(WebView view,
											  HttpAuthHandler handler, String host, String realm) {
			handler.proceed("jdon", "password");
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//66.82.144.29 8080 for test
		if (Build.VERSION.SDK_INT >= 23) {
			ProxySettings.setProxy(getApplicationContext(), "23.106.70.22", 29842);
		}
		// progresss Bar initialisation
		getWindow().requestFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.main);
		initialise();


		// to set go back and go forward
		webView.canGoBack();
		webView.canGoForward();

		// enable javascript for webview and flash
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
//		webSettings.setPluginsEnabled(true);
		webSettings.setUserAgentString(agents[49]);
		//webSettings.setUserAgentString("AppleWebKit/537.36");
		//webSettings.setUserAgentString("Chrome/60.0.3112.116");
		//webSettings.setUserAgentString("Mobile Safari/537.36");

		// setting new WebViewClient so at to prevent opera from opening the
		// site
        webView.setWebViewClient(new MyWebViewClient());
		if (Build.VERSION.SDK_INT < 23) {
			ProxySettings.setProxyForAPILower23(webView,"23.106.70.22", 29842);
		}
		bAddress.setOnClickListener(this);
		bKeyword.setOnClickListener(this);

		bBack.setOnClickListener(this);
		bForward.setOnClickListener(this);
		bOption.setOnClickListener(this);
		bRefresh.setOnClickListener(this);

		// setTitle(webView.getTitle());

		Intent intent = getIntent();

		String site = intent.getStringExtra("Site");
		if(site != null) {
			addr.setText(site);
			bAddress.performClick();
		}

	}

	public void initialise() {
		webView = (WebView) findViewById(R.id.wvContent);

		bAddress = (ImageButton) findViewById(R.id.bAddr);
		bKeyword = (ImageButton) findViewById(R.id.bKeyword);
		bBack = (Button) findViewById(R.id.Back);
		bForward = (Button) findViewById(R.id.Forward);
		bOption = (Button) findViewById(R.id.Options);
		bRefresh = (Button) findViewById(R.id.Refresh);

		addr = (EditText) findViewById(R.id.etAddr);
		keyword = (EditText) findViewById(R.id.etKeyword);

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		try {

			// <<<<<<<<<<<<<<<<<PROGRESS BAR>>>>>>>>>>>>
			// set a webChromeClient to track progress
			webView.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
					// update the progressBar
					MySimpleBrowserActivity.this.setTitle("Loading...");
					MySimpleBrowserActivity.this.setProgress(progress * 100);

					//set the title of the layout
					if(progress == 100)
						MySimpleBrowserActivity.this.setTitle(R.string.app_name);
				}
			});
			
			

			switch (v.getId()) {
			case R.id.bAddr:
				String url = addr.getText().toString();
				if (url != "") {
					url = URLUtil.isValidUrl(url) ? addr.getText().toString()   : "http://" + addr.getText().toString();
					webView.loadUrl(url);
					addr.setText(url);
					webView.requestFocus();
				}
				break;
			case R.id.bKeyword:
				String key = keyword.getText().toString();
				if (key != "") {
					key = "http://www.google.com/search?q="+ keyword.getText().toString();
					webView.loadUrl(key);
					addr.setText(key);
					webView.requestFocus();
				}
				break;

			case R.id.Back:
				webView.goBack();
				break;

			case R.id.Forward:
				webView.goForward();
				break;

			case R.id.Options:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setCancelable(true);

				builder.setItems(agents, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						webSettings.setUserAgentString(agents[i]);
						Toast.makeText(getApplicationContext(), "Set user agent: " + agents[i], Toast.LENGTH_SHORT).show();
						webView.reload();
					}
				});
				builder.show();
				break;

			case R.id.Refresh:
				webView.reload();
				break;

			}

		} catch (Exception e) {
			// TODO: handle exception]
			e.printStackTrace();
		}

	}
}