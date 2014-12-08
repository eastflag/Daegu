package com.eastflag.silverg.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.eastflag.silverg.R;

public class _4_2_Fragment extends Fragment {
	
	private View mView;

	private AQuery mAq;
	public WebView mWebView;
	private ProgressBar mProgressBar;
	
	public _4_2_Fragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_41, null);
		
		mAq = new AQuery(mView);
		mProgressBar = (ProgressBar) mView.findViewById(R.id.progressbar);
		
		mWebView = (WebView) mView.findViewById(R.id.webview);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("http://m.airkorea.or.kr/sub/sub21.jsp");
		
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
		mWebView.getSettings().setSupportMultipleWindows(false);
		
		/*String url = Constant.API_FINE_DUST + "?numOfRows=100&pageNo=1&sidoName=대구&serviceKey=";
		try {
			url +=  URLEncoder.encode(Constant.key, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.d("LDK", "url:" + url);
		
		mAq.ajax(url, XmlDom.class, new AjaxCallback<XmlDom>(){
			@Override
			public void callback(String url, XmlDom object, AjaxStatus status) {
				Log.d("LDK", object.toString(1));
			}
		});
		
		String url2 = Constant.API_FINEDUST_FORCAST + "?numOfRows=100&pageNo=1&searchDate=2014=10-29&informCode=pm10&serviceKey=";
		try {
			url2 +=  URLEncoder.encode(Constant.key, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.d("LDK", "url2:" + url2);
		
		mAq.ajax(url2, XmlDom.class, new AjaxCallback<XmlDom>(){
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
