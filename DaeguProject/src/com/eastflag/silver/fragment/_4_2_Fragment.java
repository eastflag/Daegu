package com.eastflag.silver.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.eastflag.silver.Constant;
import com.eastflag.silver.R;
import com.eastflag.silver.util.MyWebViewClient;

public class _4_2_Fragment extends Fragment {
	
	private View mView;

	private AQuery mAq;
	private WebView mWebView;
	
	public _4_2_Fragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_41, null);
		
		mAq = new AQuery(mView);
		
		mWebView = (WebView) mView.findViewById(R.id.webview);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("http://m.airkorea.or.kr/sub/sub11.jsp");
		
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
}
