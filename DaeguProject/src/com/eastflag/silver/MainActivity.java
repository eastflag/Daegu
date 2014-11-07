package com.eastflag.silver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eastflag.silver.fragment._1_1_Fragment;
import com.eastflag.silver.fragment._1_2_Fragment;
import com.eastflag.silver.fragment._2_1_Fragment;
import com.eastflag.silver.fragment._3_1_Fragment;
import com.eastflag.silver.fragment._4_1_Fragment;
import com.eastflag.silver.fragment._4_2_Fragment;
import com.eastflag.silver.fragment._4_3_Fragment;

public class MainActivity extends Activity {

	private static final int FRAGMENT_11 = 11;
	private static final int FRAGMENT_12 = 12;
	private static final int FRAGMENT_21 = 21;
	private static final int FRAGMENT_31 = 31;
	private static final int FRAGMENT_41 = 41;
	private static final int FRAGMENT_42 = 42;
	private static final int FRAGMENT_43 = 43;

	private FragmentManager mFm;
	private Fragment mFragment;

	private LinearLayout mMainMenu;
	private LinearLayout mTabGroup;
	private TextView tab_1, tab_2;
	
	View.OnClickListener mClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.ll_1:
				showSubmenu(FRAGMENT_11);
				tab_1.setTag(FRAGMENT_11);
				tab_2.setTag(FRAGMENT_12);
				break;
				
			case R.id.ll_2:
				showSubmenu(FRAGMENT_21);
				tab_1.setTag(FRAGMENT_21);
				break;
				
			case R.id.ll_3:
				showSubmenu(FRAGMENT_31);
				tab_1.setTag(FRAGMENT_31);
				break;
				
			case R.id.ll_4:
				showSubmenu(FRAGMENT_41);
				tab_1.setTag(FRAGMENT_41);
				tab_2.setTag(FRAGMENT_42);
				break;
			}
		}
	};
	
	View.OnClickListener mTabClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int type = (Integer) v.getTag();
			switch(type) {
			case FRAGMENT_11:
				showSubmenu(FRAGMENT_11);
				break;
			case FRAGMENT_12:
				showSubmenu(FRAGMENT_12);
				break;
			case FRAGMENT_21:
				showSubmenu(FRAGMENT_21);
				break;
			case FRAGMENT_31:
				showSubmenu(FRAGMENT_31);
				break;
			case FRAGMENT_41:
				showSubmenu(FRAGMENT_41);
				break;
			case FRAGMENT_42:
				showSubmenu(FRAGMENT_42);
				break;
			case FRAGMENT_43:
				showSubmenu(FRAGMENT_43);
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMainMenu = (LinearLayout) findViewById(R.id.main_menu);
		mTabGroup = (LinearLayout) findViewById(R.id.tab_group);
		tab_1 = (TextView) findViewById(R.id.tab_1);
		tab_2 = (TextView) findViewById(R.id.tab_2);
		
		mFm = getFragmentManager();
		
		findViewById(R.id.ll_1).setOnClickListener(mClick);
		findViewById(R.id.ll_2).setOnClickListener(mClick);
		findViewById(R.id.ll_3).setOnClickListener(mClick);
		findViewById(R.id.ll_4).setOnClickListener(mClick);
		tab_1.setOnClickListener(mTabClick);
		tab_2.setOnClickListener(mTabClick);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (mFragment == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.app_name))
					.setMessage("종료하시겠습니까?")
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									dialog.dismiss();
									finish();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
		} else {
			mFm.beginTransaction().remove(mFragment).commit();
			mFragment = null;
			hideSubmenu();
		}
	}

	// show sub menu
	private void showSubmenu(int tab) {
		mMainMenu.setVisibility(View.GONE);
		mTabGroup.setVisibility(View.VISIBLE);

		switch (tab) {
		case FRAGMENT_11:
			mFragment = new _1_1_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_11)).commit();
			refreshTab(0, "사고력 게임", "기억력 게임");
			break;
			
		case FRAGMENT_12:
			mFragment = new _1_2_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_12)).commit();
			refreshTab(1, "사고력 게임", "기억력 게임");
			break;

		case FRAGMENT_21:
			mFragment = new _2_1_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_21)).commit();
			refreshTab(0, "유용한 앱정보");
			break;
			
		case FRAGMENT_31:
			mFragment = new _3_1_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_31)).commit();
			refreshTab(0, "스마트폰 사용설명");
			break;
			
		case FRAGMENT_41:
			mFragment = new _4_1_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_41)).commit();
			tab_1.setText("");
			break;
			
		case FRAGMENT_42:
			mFragment = new _4_2_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_42)).commit();
			tab_1.setText("");
			break;
			
		case FRAGMENT_43:
			mFragment = new _4_3_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_43)).commit();
			tab_1.setText("");
			break;
		}
	}

	private void hideSubmenu() {
		mMainMenu.setVisibility(View.VISIBLE);
		mTabGroup.setVisibility(View.GONE);
	}
	
	//선택된 탭의 위치(인덱스는 0부터), 모든 탭의 스트링
	private void refreshTab(int positionOfOn, String ... str) {
		if(str.length == 2) {
			tab_1.setText(str[0]);
			tab_2.setText(str[1]);
			if(positionOfOn == 0) {
				tab_1.setBackgroundResource(R.drawable.tab_s_on);
				tab_1.setVisibility(View.VISIBLE);
			} else {
				tab_1.setBackgroundResource(R.drawable.tab_s_off);
				tab_2.setVisibility(View.VISIBLE);
			}
		} else {
			tab_1.setText(str[0]);
			tab_1.setBackgroundResource(R.drawable.tab_s_on);
			tab_1.setVisibility(View.VISIBLE);
			tab_2.setBackgroundResource(R.drawable.tab_s_off);
			tab_2.setVisibility(View.GONE);
		}
	}
}
