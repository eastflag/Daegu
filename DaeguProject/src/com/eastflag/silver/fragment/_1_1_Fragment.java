package com.eastflag.silver.fragment;

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
import com.eastflag.silver.R;

public class _1_1_Fragment extends Fragment {
	
	private View mView;
	private NumberPicker picker1, picker2, picker3;
	private Button btnSubmit;
	
	private String mAnswer; 
	private int mStrike, mBall;
	
	public _1_1_Fragment () {
		
	}
	
	View.OnClickListener mClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.btnSubmit:
				mStrike = 0;
				mBall = 0;
				String a = String.valueOf(picker1.getValue());
				String b = String.valueOf(picker2.getValue());
				String c = String.valueOf(picker3.getValue());
				if(mAnswer.substring(0, 1).equals(a)) {
					//strike
					++mStrike;
				} else if (mAnswer.contains(a)) {
					//ball
					++mBall;
				}
				if(mAnswer.substring(1, 2).equals(b)) {
					//strike
					++mStrike;
				} else if (mAnswer.contains(b)) {
					//ball
					++mBall;
				}
				if(mAnswer.substring(2, 3).equals(c)) {
					//strike
					++mStrike;
				} else if (mAnswer.contains(c)) {
					//ball
					++mBall;
				}
				Log.d("LDK", "a:" + a + ", b:" + b + ", c:" + c);
				Log.d("LDK", "" + mStrike + " strike, " + mBall + " ball");
				
				checkEnd();
				
				break;
			}
		}
	};
	
	private void checkEnd() {
		if(mStrike == 3) {
			
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_11, null);
		
		picker1 = (NumberPicker) mView.findViewById(R.id.numberPicker1);
		picker2 = (NumberPicker) mView.findViewById(R.id.numberPicker2);
		picker3 = (NumberPicker) mView.findViewById(R.id.numberPicker3);
		btnSubmit = (Button) mView.findViewById(R.id.btnSubmit);
		
		picker1.setMaxValue(9);
		picker1.setMinValue(1);
		picker2.setMaxValue(9);
		picker2.setMinValue(1);
		picker3.setMaxValue(9);
		picker3.setMinValue(1);
		btnSubmit.setOnClickListener(mClick);
		
		//Random 3자리 생성
		int a = getRandomOne();
		int b = getRandomOne();
		while(b == a) {
			b = getRandomOne();
		}
		int c = getRandomOne();
		while ( c==b || c==a) {
			c = getRandomOne();
		}
		
		mAnswer = String.valueOf(a) + String.valueOf(b) + String.valueOf(c);
		Log.d("LDK", "mAnswer:" + mAnswer);
		
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	private int getRandomOne() {
		return (int) (Math.random() * 9 + 1);
	}
}