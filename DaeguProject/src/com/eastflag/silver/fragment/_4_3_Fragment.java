package com.eastflag.silver.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.eastflag.silver.Constant;
import com.eastflag.silver.R;

public class _4_3_Fragment extends Fragment {
	
	private View mView;
	private WebView mWebView;
	private ProgressBar mProgressBar;
	
	private AQuery mAq;
	
	
	public _4_3_Fragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_41, null);
		mProgressBar = (ProgressBar) mView.findViewById(R.id.progressbar);
		
		mWebView = (WebView) mView.findViewById(R.id.webview);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("http://m.kma.go.kr/m/forecast/forecast_03.jsp");
		
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
		mWebView.getSettings().setSupportMultipleWindows(false);
		
		/*mAq = new AQuery(mView);
		
		String url = Constant.API_ULTRV + "?AreaNo=1100000000&serviceKey=";
		try {
			url +=  URLEncoder.encode(Constant.key, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("LDK", "url:" + url);
		
		mAq.ajax(url, XmlDom.class, new AjaxCallback<XmlDom>(){
			@Override
			public void callback(String url, XmlDom object, AjaxStatus status) {
				Log.d("LDK", object.toString(1));
			}
		});*/
		
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
