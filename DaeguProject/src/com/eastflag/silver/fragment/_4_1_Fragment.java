package com.eastflag.silver.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class _4_1_Fragment extends Fragment {
	
	private View mView;

	private AQuery mAq;
	
	
	public _4_1_Fragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_41, null);
		
		mAq = new AQuery(mView);
		
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
		});
		
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
