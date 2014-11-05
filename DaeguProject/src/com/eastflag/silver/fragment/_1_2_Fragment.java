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

public class _1_2_Fragment extends Fragment {
	
	private View mView;
	
	public _1_2_Fragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_11, null);
		
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
