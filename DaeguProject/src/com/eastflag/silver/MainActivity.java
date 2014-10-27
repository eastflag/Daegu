package com.eastflag.silver;

import java.util.ArrayList;
import com.eastflag.silver.R;
import com.lgcns.wd.fragment._1_1_Fragment;
import com.lgcns.wd.fragment._2_1_Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int FRAGMENT_AUTO = 1;
	private static final int FRAGMENT_11 = 11;
	private static final int FRAGMENT_21 = 21;

	private FragmentManager mFm;
	private Fragment mFragment;

	private LinearLayout mMainMenu;
	private TextView mSubTab;
	
	private ArrayList<String> mGroupList = null;
	private ArrayList<ArrayList<String>> mChildList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMainMenu = (LinearLayout) findViewById(R.id.main_menu);
		mSubTab = (TextView) findViewById(R.id.tv_tab);
		mFm = getFragmentManager();
		
		mListView = (ExpandableListView) findViewById(R.id.elv_list);

		mGroupList = new ArrayList<String>();
		mChildList = new ArrayList<ArrayList<String>>();

		mGroupList.add("1. 게임");
		mGroupList.add("2. 유용한 앱 정보");
		mGroupList.add("3. 스마트폰 사용 설명");
		mGroupList.add("4. 외출정보");
		
		ArrayList<String> mChildListContent1 = new ArrayList<String>();
		mChildListContent1.add("1) 야구 게임");
		mChildListContent1.add("2) 기억력 게임");
		mChildList.add(mChildListContent1);
		
		ArrayList<String> mChildListContent2 = new ArrayList<String>();
		mChildListContent2.add("1) 교통");
		mChildListContent2.add("2) 지도, 길찾기");
		mChildListContent2.add("3) 건강관리");
		mChildListContent2.add("4) 추천게임");
		mChildList.add(mChildListContent2);
		
		ArrayList<String> mChildListContent3 = new ArrayList<String>();
		mChildListContent3.add("1) 인터넷 사용법");
		mChildListContent3.add("2) 카카오톡 사용법");
		mChildListContent3.add("3) 다운로드 방법");
		mChildList.add(mChildListContent3);
		
		ArrayList<String> mChildListContent4 = new ArrayList<String>();
		mChildListContent4.add("1) 방사능 지수 확인");
		mChildListContent4.add("2) 미세먼지 확인");
		mChildListContent4.add("3) 자외선 지수 확인");
		mChildList.add(mChildListContent4);

		mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));

		// 그룹 클릭 했을 경우 이벤트
/*		mListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				Toast.makeText(getApplicationContext(), "g click = " + groupPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		});*/

		// 차일드 클릭 했을 경우 이벤트
		mListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
				Log.d("LDK", "g:" + groupPosition + "c:" + childPosition);
				switch(groupPosition) {
				case 0:
					showSubmenu(FRAGMENT_11);
					break;
				case 1:
					switch(childPosition) {
					case 0:
						showSubmenu(FRAGMENT_21);
						break;
					}
				}
				return false;
			}
		});

/*		// 그룹이 닫힐 경우 이벤트
		mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						"g Collapse = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
			}
		});

		// 그룹이 열릴 경우 이벤트
		mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						"g Expand = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
			}
		});*/
	}

	/*
	 * Layout
	 */
	private ExpandableListView mListView;

	private void setLayout() {
		mListView = (ExpandableListView) findViewById(R.id.elv_list);
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
		mSubTab.setVisibility(View.VISIBLE);

		switch (tab) {
		case FRAGMENT_11:
			mFragment = new _1_1_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_11)).commit();
			mSubTab.setText("");
			break;

		case FRAGMENT_21:
			mFragment = new _2_1_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_21)).commit();
			mSubTab.setText("");
			break;
		}
	}

	private void hideSubmenu() {
		mMainMenu.setVisibility(View.VISIBLE);
		mSubTab.setVisibility(View.GONE);
	}
}
