package com.eastflag.silver.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.eastflag.silver.R;
import com.eastflag.silver.adapter.BaseballAdapter;
import com.eastflag.silver.database.MySqlController;
import com.eastflag.silver.dto.BaseballVO;

public class _1_1_Fragment extends Fragment {
	private final static int MSG_RESET_ANSWER = 1;
	private final static int MSG_TIMER = 2;
	
	private View mView;
	private TextView tvTimer; //타이머
	private ImageView ivRecord; //기록
	private ImageView ivHelp;   //도움말
	private TextView answerOne, answerTwo, answerThree;
	private TextView answerResult;
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
	
	public _1_1_Fragment () {
		
	}
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MSG_RESET_ANSWER:
				answerResult.setText("");
				answerOne.setText("?");
				answerTwo.setText("?");
				answerThree.setText("?");
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
		ivHelp = (ImageView) mView.findViewById(R.id.ivHelp);
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
		mListView = (ListView) mView.findViewById(R.id.listHistory);
		
		ivRecord.setOnClickListener(mClick);
		ivHelp.setOnClickListener(mClick);
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
		mHandler.removeMessages(MSG_TIMER);
		super.onDestroyView();
	}
	
	private int getRandomOne() {
		return (int) (Math.random() * 10);
	}
	
	private void inputNumber(String number) {
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
			answerResult.setText("3자리를 모두 입력하세요");
			return;
		}
		
		if(a.equals(b) || a.equals(c) || b.equals(c)) {
			answerResult.setText("각각 다른 3자리를 입력하세요.");
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
		String result = String.format("%dS %dB", mStrike, mBall);
		answerResult.setText(result);
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
			mHandler.removeMessages(MSG_TIMER);
			rootHistory.setVisibility(View.INVISIBLE);
			rootVictory.setVisibility(View.VISIBLE);
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
			tvResult.setText(mHistoryList.get(position).result);
			
			return convertView;
		}
	}
}
