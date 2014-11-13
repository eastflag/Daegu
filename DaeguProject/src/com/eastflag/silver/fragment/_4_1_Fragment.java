package com.eastflag.silver.fragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.eastflag.silver.Constant;
import com.eastflag.silver.MainActivity;
import com.eastflag.silver.R;
import com.eastflag.silver.util.GPSTracker;
import com.eastflag.silver.util.MyWebViewClient;

public class _4_1_Fragment extends Fragment {
	
	private String URL_GEOCODER = "http://maps.google.com/maps/api/geocode/json?address=";
	
	private View mView;

	//private AQuery mAq;
	private WebView mWebView;
	
/*	private LocationManager mLocMan;
	private String mProvider;
	private Location mCurrentLocation;
	private GPSTracker mGPS;*/
	
	public _4_1_Fragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_41, null);
		
		//mAq = new AQuery(mView);
		
		mWebView = (WebView) mView.findViewById(R.id.webview);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("http://iernet.kins.re.kr/");
		
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false); // javascript가 window.open()을 사용 유무
		mWebView.getSettings().setSupportMultipleWindows(false); // 여러개의 윈도우를 사용 유무
		mWebView.getSettings().setSupportZoom(true); //확대, 축소 기능을 사용할수 있도록 설정
		mWebView.getSettings().setBuiltInZoomControls(true);
		
		/*mLocMan = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		mProvider = mLocMan.getBestProvider(new Criteria(), true);
		mCurrentLocation = mLocMan.getLastKnownLocation(mProvider);
		
		mGPS = new GPSTracker(getActivity());
	    if(mGPS.canGetLocation ){
		    mGPS.getLocation();
		    Log.d("LDK", "mGPS:" + mGPS.getLatitude() + "," + mGPS.getLongitude());
		    getMyLocationAddress();
	    } else{
	    	mGPS.showSettingsAlert();
	    }
		
		String url = "http://203.247.66.146/iros/RetrieveLifeIndexService/getUltrvLifeList?AreaNo=1100000000&serviceKey=";
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
	
	private void getMyLocationAddress() {
		/*Geocoder geocoder = new Geocoder(getActivity());

		try {
			// Place your latitude and longitude
			List<Address> addresses = geocoder.getFromLocation(mGPS.getLatitude(), mCurrentLocation.getLongitude(), 1);

			if (addresses != null) {
				Address fetchedAddress = addresses.get(0);
				StringBuilder strAddress = new StringBuilder();

				for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
					strAddress.append(fetchedAddress.getAddressLine(i)).append(
							"\n");
				}

				Log.d("LDK", "I am at: " + strAddress.toString());
			} else {
				Log.d("LDK", "No location found..!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
