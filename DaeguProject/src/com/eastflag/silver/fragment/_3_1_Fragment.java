package com.eastflag.silver.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.androidquery.AQuery;
import com.eastflag.silver.R;
import com.eastflag.silver.fragment._4_1_Fragment.MyWebViewClient;
import com.eastflag.silver.util.HTML5WebView;

public class _3_1_Fragment extends Fragment {
	
	private View mView;
	private WebView mWebView;
	
	private ProgressBar mProgressBar;
	
	public _3_1_Fragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_21, null);
		
		mProgressBar = (ProgressBar) mView.findViewById(R.id.progressbar);
		
		mWebView = (WebView) mView.findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.loadUrl("file:///android_asset/3main.html");
		
		//HTML5WebView webview = new HTML5WebView(getActivity());
		//webview.loadUrl("file:///android_asset/3main.html");
        //return webview.getLayout();
		
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	class MyWebViewClient extends WebViewClient {
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			mProgressBar.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			mProgressBar.setVisibility(View.INVISIBLE);
			super.onPageFinished(view, url);
		}
	}
}
