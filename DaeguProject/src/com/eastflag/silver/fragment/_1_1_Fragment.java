package com.eastflag.silver.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eastflag.silver.MainActivity;
import com.eastflag.silver.R;
import com.eastflag.silver.SilverApplication;
import com.eastflag.silver.adapter.BaseballAdapter;
import com.eastflag.silver.database.MySqlController;
import com.eastflag.silver.dto.BaseballVO;

public class _1_1_Fragment extends Fragment {
	private final static int MSG_RESET_ANSWER = 1;
	private final static int MSG_RESET_ANSWER_RESULT = 2;
	private final static int MSG_TIMER = 3;
	
	private View mView;
	private TextView tvTimer; //타이머
	private ImageView ivRecord; //기록
	private TextView answerOne, answerTwo, answerThree;
	private TextView answerResult;
	private TextView tvVictory;
	private TextView number0, number1, number2, number3, number4;
	private TextView number5, number6, number7, number8, number9;
	private Button btnDelete, btnSubmit;
	private Button btnRestart;
	private LinearLayout rootHistory, rootVictory;
	
	private ListView mListView;
	private ArrayList<History> mHistoryList = new ArrayList<History>(); 
	private MyAdapter mAdapter;
	
	private String mAnswer; 
	private int mStrike, mBall;
	private int mHit;
	private int mTime;
	
	private MainActivity mMainActivity;
	
	public _1_1_Fragment () {
		
	}
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MSG_RESET_ANSWER:
				//answerResult.setText("");
				answerOne.setText("?");
				answerTwo.setText("?");
				answerThree.setText("?");
				break;
				
			case MSG_RESET_ANSWER_RESULT:
				answerResult.setText("");
				break;
				
			case MSG_TIMER:
				++mTime;
				tvTimer.setText(String.format("%02d:%02d", mTime/60, mTime%60));
				mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
				break;
			}
		}
		
	};
	
	View.OnClickListener mClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SilverApplication.sApp.soundButton();
			switch(v.getId()) {
			case R.id.numberZero:
			case R.id.numberOne:
			case R.id.numberTwo:
			case R.id.numberThree:
			case R.id.numberFour:
			case R.id.numberFive:
			case R.id.numberSix:
			case R.id.numberSeven:
			case R.id.numberEight:
			case R.id.numberNine:
				inputNumber(((TextView)v).getText().toString());
				break;
				
			case R.id.btnDelete:
				deleteNumber();
				break;
				
			case R.id.btnSubmit:
				checkSubmit();
				break;
				
			case R.id.btnRestart:
				restartGame();
				break;
				
			case R.id.ivRecord:
				showRecord();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_11, null);
		
		tvTimer = (TextView) mView.findViewById(R.id.tvTimer);
		ivRecord = (ImageView) mView.findViewById(R.id.ivRecord);
		answerOne = (TextView) mView.findViewById(R.id.answerOne);
		answerTwo = (TextView) mView.findViewById(R.id.answerTwo);
		answerThree = (TextView) mView.findViewById(R.id.answerThree);
		answerResult = (TextView) mView.findViewById(R.id.tvResult);
		number0 = (TextView) mView.findViewById(R.id.numberZero);
		number1 = (TextView) mView.findViewById(R.id.numberOne);
		number2 = (TextView) mView.findViewById(R.id.numberTwo);
		number3 = (TextView) mView.findViewById(R.id.numberThree);
		number4 = (TextView) mView.findViewById(R.id.numberFour);
		number5 = (TextView) mView.findViewById(R.id.numberFive);
		number6 = (TextView) mView.findViewById(R.id.numberSix);
		number7 = (TextView) mView.findViewById(R.id.numberSeven);
		number8 = (TextView) mView.findViewById(R.id.numberEight);
		number9 = (TextView) mView.findViewById(R.id.numberNine);
		btnDelete = (Button) mView.findViewById(R.id.btnDelete);
		btnSubmit = (Button) mView.findViewById(R.id.btnSubmit);
		btnRestart = (Button) mView.findViewById(R.id.btnRestart);
		rootHistory = (LinearLayout) mView.findViewById(R.id.rootHistory);
		rootVictory = (LinearLayout) mView.findViewById(R.id.rootVictory);
		tvVictory = (TextView) mView.findViewById(R.id.tvVictory);
		mListView = (ListView) mView.findViewById(R.id.listHistory);
		
		ivRecord.setOnClickListener(mClick);
		number0.setOnClickListener(mClick);
		number1.setOnClickListener(mClick);
		number2.setOnClickListener(mClick);
		number3.setOnClickListener(mClick);
		number4.setOnClickListener(mClick);
		number5.setOnClickListener(mClick);
		number6.setOnClickListener(mClick);
		number7.setOnClickListener(mClick);
		number8.setOnClickListener(mClick);
		number9.setOnClickListener(mClick);
		btnDelete.setOnClickListener(mClick);
		btnSubmit.setOnClickListener(mClick);
		btnRestart.setOnClickListener(mClick);
		
		mAdapter = new MyAdapter();
		mListView.setAdapter(mAdapter);
		
		//Timer start
		mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
		
		getRandomAnswer();
		
		mMainActivity = (MainActivity) getActivity();
		
		return mView;
	}
	
	private void getRandomAnswer() {
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
		Log.e("LDK", "mAnswer:" + mAnswer);
	}

	@Override
	public void onDestroyView() {
		mHandler.removeMessages(MSG_TIMER);
		super.onDestroyView();
	}
	
	private int getRandomOne() {
		return (int) (Math.random() * 10);
	}
	
	private void inputNumber(String number) {
		//중복입력 방지
		if(answerOne.getText().toString().contains(number) ||
				answerTwo.getText().toString().contains(number) ||
				answerThree.getText().toString().contains(number)) {
			String text = "중복된 숫자입니다";
			//Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
			//answerResult.setText(text);
			mMainActivity.speakOut(text);
			//mHandler.sendEmptyMessageDelayed(MSG_RESET_ANSWER_RESULT, 2000);
			return;
		}
			
		if(answerOne.getText().toString().equals("?")) {
			answerOne.setText(number);
		} else if(answerTwo.getText().toString().equals("?")) {
			answerTwo.setText(number);
		} else {
			answerThree.setText(number);
		}
	}
	
	private void deleteNumber() {
		if (!answerThree.getText().toString().equals("?")) {
			answerThree.setText("?");
		} else if (!answerTwo.getText().toString().equals("?")) {
			answerTwo.setText("?");
		} else if (!answerOne.getText().toString().equals("?")) {
			answerOne.setText("?");
		}
	}
	
	private void checkSubmit() {
		String a = String.valueOf(answerOne.getText().toString());
		String b = String.valueOf(answerTwo.getText().toString());
		String c = String.valueOf(answerThree.getText().toString());
		
		if(a.equals("?") || b.equals("?") || c.equals("?")) {
			String text = "3자리를 모두 입력하세요";
			//Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
			//answerResult.setText(text);
			mMainActivity.speakOut(text);
			//mHandler.sendEmptyMessageDelayed(MSG_RESET_ANSWER_RESULT, 2000);
			return;
		}

		mStrike = 0;
		mBall = 0;
		
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
	}
	
	private void checkEnd() {
		String input = answerOne.getText().toString() + answerTwo.getText().toString() + answerThree.getText().toString();
		String result = String.format("%d<font color='red'>S</font> %d<font color='blue'>B</font>", mStrike, mBall);
		answerResult.setText(Html.fromHtml(result), TextView.BufferType.SPANNABLE);
		
		String text = String.format("%s 스트라이크 %s 볼", getEnglishString(mStrike), getEnglishString(mBall));
		mMainActivity.speakOut(text);
		
		mHistoryList.add(new History(++mHit, input, result));
		mAdapter.notifyDataSetChanged();
		//리스트뷰의 맨 마지막으로 스크롤
		mListView.post(new Runnable() {
	        @Override
	        public void run() {
	            mListView.setSelection(mAdapter.getCount() - 1);
	        }
	    });
		
		if(mStrike != 3) {
			//answerResult 삭제 및 초기화
			mHandler.sendEmptyMessageDelayed(MSG_RESET_ANSWER, 1000);
		} else {
			//게임 오버 처리
			SilverApplication.sApp.soundClap();
			
			mHandler.removeMessages(MSG_TIMER);
			rootHistory.setVisibility(View.INVISIBLE);
			rootVictory.setVisibility(View.VISIBLE);
			
			//승리 메시지
			String textVictory = "";
			if(mHit == 1) {
				textVictory += "운이 좋으시네요! 다시 시도해주세요";
			} else if(mHit == 2) {
				textVictory += "실력이 아니라 운입니다! 다시 시도해주세요";
			} else if(mHit == 3) {
				textVictory += "실력과 운을 같이 가지고 계시네요";
			} else if(mHit == 4 || mHit == 5) {
				textVictory += "대단하십니다! 당신은 20대의 머리를 가지고 있습니다";
			} else if(mHit == 6 || mHit == 7) {
				textVictory += "축하합니다. 아직 정정하시군요!";
			} else if(mHit == 8 || mHit == 9 || mHit == 10) {
				textVictory += "좀 더 노력이 필요하지만 좋은 편이군요!";
			} else if(mHit > 10 && mTime < 600) {
				textVictory += "머리를 쓰셔야할 것 같습니다";
			} else if(mHit > 10 && mTime >= 600) {
				textVictory += "너무 오래걸립니다 치매가 걱정됩니다";
			}
			
			tvVictory.setText(textVictory);
			mMainActivity.speakOut(textVictory);
			
			//랭킹스코어 기록
	        //DB에 해당 내용 저장
	        MySqlController controller = new MySqlController(getActivity());
	        controller.open();
	        
	        BaseballVO baseball = new BaseballVO();
	        baseball.setTime(mTime);
	        baseball.setHit(mHit);
		    Date dt = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a"); 
		    baseball.setCreated_date(sdf.format(dt).toString());
	        
		    controller.insertBaseball(baseball);
		    controller.close();
		}
	}
	
	private void restartGame() {
		mMainActivity.speakStop();
		answerResult.setText("");
		answerOne.setText("?");
		answerTwo.setText("?");
		answerThree.setText("?");
		tvTimer.setText("00:00");
		
		mHit = 0;
		mTime = 0;
		mHistoryList.clear();
		mAdapter.notifyDataSetChanged();
		rootHistory.setVisibility(View.VISIBLE);
		rootVictory.setVisibility(View.INVISIBLE);
		
		getRandomAnswer();
		
		mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
	}
	
	private void showRecord() {
		MySqlController controller = new MySqlController(getActivity());
		controller.open();
		
		ArrayList<BaseballVO> baseballList = controller.selectBasebal();
		BaseballAdapter adapter = new BaseballAdapter(getActivity(), baseballList);
		
		View view = View.inflate(getActivity(), R.layout.fragment_11_record, null);
		ListView listview = (ListView) view.findViewById(R.id.listRecord);
		listview.setAdapter(adapter);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			})
			.create().show();
	}
	
	private String getEnglishString(int s) {
		String str = "노";
		switch (s) {
		case 0:
			str = "노";
			break;
			
		case 1:
			str = "원";
			break;
			
		case 2:
			str = "투";
			break;
			
		case 3:
			str = "쓰리";
			break;
		}
		return str;
	}
	
	class History {
		int hit;
		String input;
		String result;
		
		public History(int hit, String input, String result) {
			this.hit = hit;
			this.input = input;
			this.result = result;
		}
	}
	
	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mHistoryList.size();
		}

		@Override
		public Object getItem(int position) {
			return mHistoryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.fragment_11_history, null);
			}
			TextView tvHit = (TextView) convertView.findViewById(R.id.tvHit);
			TextView tvInput = (TextView) convertView.findViewById(R.id.tvInput);
			TextView tvResult = (TextView) convertView.findViewById(R.id.tvResult);
			
			tvHit.setText(String.valueOf(mHistoryList.get(position).hit));
			tvInput.setText(mHistoryList.get(position).input);
			tvResult.setText(Html.fromHtml(mHistoryList.get(position).result), TextView.BufferType.SPANNABLE);
			
			return convertView;
		}
	}
}
