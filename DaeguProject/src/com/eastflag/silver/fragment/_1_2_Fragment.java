package com.eastflag.silver.fragment;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eastflag.silver.MainActivity;
import com.eastflag.silver.R;
import com.eastflag.silver.SilverApplication;
import com.eastflag.silver.util.Utils;

public class _1_2_Fragment extends Fragment {
	
	private static final int MSG_TIMER = 1;
	private static final int MSG_COUNT_DOWN = 2;
	private static final int MSG_WAIT_FOR_WRONG_CLICK = 3;
	
	private static final int STAGE_1 = 6;
	private static final int STAGE_2 = 8;
	private static final int STAGE_3 = 10;
	private static final int STAGE_FINAL = 12;
	
	//private int mWidth = 1080;
	
	private View mView;
	private TextView tvTimer, tvLimit, tvScore, tvStage;
	private GridView mGridView;
	private LinearLayout rootVictory;
	private TextView tvResult;  //메시지
	private TextView tvVictory; //Victory or Fail
	private Button btnRestart;
	private TextView tvCountDown;
	
	private int mTime;
	private int mLimit = 5; //5회까지 시도
	private int mScore;
	private int mStage = 1; //최초 1단계
	private boolean mVictory;
	private int mCountDown = 6; //게임시작 카운트 다운
	private boolean isWrongClicked; //쌍이 안 맞는 그림을 클릭했을 경우, 1초 딜레이를 주고 다음그림을 클릭하게 한다.
	
	private int mPair; //1단계:6쌍, 2단계 8쌍, 3단계: 10쌍, 4단계:12쌍
	private int mCorrectPair;
    private Integer[] pieces;
    private ArrayList<ImageView> imageViews;
    private int piece_up = -1;
    
    private MainActivity mMainActivity;
    
    private ImageAdapter mImageAdapter;
	
	public _1_2_Fragment () {
		
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MSG_TIMER:
				++mTime;
				tvTimer.setText(String.format("%02d:%02d", mTime/60, mTime%60));
				mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
				break;
				
			case MSG_COUNT_DOWN:
				if(mCountDown == 1) {
					tvCountDown.setVisibility(View.GONE);
					SilverApplication.sApp.soundCount_0();
					//game start
					mImageAdapter.startGame();
					//타이머 작동
					mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
				} else {
					--mCountDown;
					//사운드
					SilverApplication.sApp.soundCount_1();
					tvCountDown.setText(String.valueOf(mCountDown));
					mHandler.sendEmptyMessageDelayed(MSG_COUNT_DOWN, 1000);
				}
				break;
				
			case MSG_WAIT_FOR_WRONG_CLICK:
				isWrongClicked = false;
				int piece_first = msg.arg1;
				int piece_second = msg.arg2;
				mImageAdapter.hide(piece_first);
				mImageAdapter.hide(piece_second);
				break;
			}
		}
	};
	
	View.OnClickListener mClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.btnRestart:
				restartGame();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_12, null);
		
		tvTimer = (TextView) mView.findViewById(R.id.tvTimer);
		tvLimit = (TextView) mView.findViewById(R.id.tvLimit);
		tvScore = (TextView) mView.findViewById(R.id.tvScore);
		tvStage = (TextView) mView.findViewById(R.id.tvStage);
		mGridView = (GridView) mView.findViewById(R.id.gridview);
		rootVictory = (LinearLayout) mView.findViewById(R.id.rootVictory);
		tvResult = (TextView) mView.findViewById(R.id.tvResult);
		tvVictory = (TextView) mView.findViewById(R.id.tvVictory);
		btnRestart = (Button) mView.findViewById(R.id.btnRestart);
		tvCountDown = (TextView) mView.findViewById(R.id.tvCountDown);
		
		btnRestart.setOnClickListener(mClick);
		
		mMainActivity = (MainActivity) getActivity();
		
		//게임 레디
		readyGame(STAGE_1);
		
		return mView;
	}
	
	private void readyGame(int pair) {
		tvCountDown.setVisibility(View.VISIBLE);
		
		mImageAdapter = new ImageAdapter(pair);
		mGridView.setAdapter(mImageAdapter);
		int num = pair <= 8 ? 4 : 5;
		mGridView.setNumColumns(num);
		mHandler.sendEmptyMessage(MSG_COUNT_DOWN);
	}
	
	private void gameOver() {
		//초기화
		rootVictory.setVisibility(View.VISIBLE);
		mHandler.removeMessages(MSG_TIMER);
		if(mVictory) {
			String textVictory = String.valueOf(mStage) + "단계를 통과하였습니다!";
			tvVictory.setText("Victory!!!");
			tvResult.setText(textVictory);
			mMainActivity.speakOut(textVictory);
			SilverApplication.sApp.soundClap();
			
			if(mPair == STAGE_FINAL) {
				btnRestart.setText("처음부터 다시하기");
			} else {
				btnRestart.setText("다음 단계");
			}
		} else {
			tvVictory.setText("Fail!!!");
			String failText = "";
			if(mStage == 1) {
				failText = "지능이 물고기와 비슷한 수준입니다. 얼른 자녀에게 연락하셔서 병원을 예약해두세요";
			} else if (mStage == 2) {
				failText = "건망증이 심하십니까?";
			} else if (mStage == 3) {
				failText = "좀 더 노력하세요, 이대로 치매가 올 수도 있습니다";
			} else if (mStage == 4) {
				failText = "좀 더 노력하셔서 치매 예방에 도움되길 바랍니다";
			}
			tvResult.setText(failText);
			mMainActivity.speakOut(failText);
			btnRestart.setText("다시하기");
		}
		
	}
	
	private void restartGame() {
		mMainActivity.speakStop();
		//Next Stage
		if(mVictory) {
			if(mStage < 4) { //마지막단계는 4단계
				++mStage;
				mPair += 2;
			} else { //처음부터 다시 하기
				mStage = 1;
				mPair = STAGE_1;
			}
		} 

		//초기화
		rootVictory.setVisibility(View.GONE);
		mTime = 0;
		mCountDown = 6;
		mCorrectPair = 0;
		mLimit = 1 + 4 * mStage;
		mScore = 0;
		tvTimer.setText(String.format("%02d:%02d", mTime/60, mTime%60));
		tvLimit.setText(String.valueOf(mLimit));
		tvScore.setText(String.valueOf(mScore));
		tvStage.setText(String.format("%d단계", mStage));
		
		readyGame(mPair);
	}

	@Override
	public void onDestroyView() {
		mHandler.removeMessages(MSG_TIMER);
		mHandler.removeMessages(MSG_COUNT_DOWN);
		super.onDestroyView();
	}
	
	class ImageAdapter extends BaseAdapter {

	    public ImageAdapter(int pair) {
	        mPair = pair;
	        ArrayList<Integer> ipieces = new ArrayList<Integer>();
	        for(int i=0; i<mPair; i++) {
	            ipieces.add(i);
	            ipieces.add(i);
	        }
	        Collections.shuffle(ipieces);
	        pieces = (Integer[]) ipieces.toArray(new Integer[0]);
	        _createImageViews();
	    }

	    private void _createImageViews() {
	        imageViews = new ArrayList<ImageView>();
	        for(int position = 0; position < getCount(); position++) {
	            ImageView imageView;

	            imageView = new ImageView(getActivity());
	            int width = Utils.getDisplayWidth(getActivity());
	            if(mPair == 6 || mPair == 8) {
	            	width = width/4;
	            } else if (mPair == 10 || mPair == 12){
	            	width = width/5;
	            } else {
	            	width = width/4;
	            }
	            imageView.setLayoutParams(new GridView.LayoutParams(width, width));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(10, 10, 10, 10);

	            //imageView.setImageResource(R.drawable.back);
	            int piece = pieces[position];
	            imageView.setImageResource(mThumbIds[piece]);
		        
	            imageViews.add(imageView);

	            //installClick(position);
	        }
	    }
	    
	    public void startGame() {
	    	for(int position = 0; position < getCount(); position++) {
	    		ImageView imageView = imageViews.get(position);
	    		imageView.setImageResource(R.drawable.back);
	    		installClick(position);
	    	}
	    }

	    public int getCount() {
	        return mPair*2; //mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return imageViews.get(position);
	    }

	    public long getItemId(int position) {
	        return pieces[position].longValue();
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public synchronized View getView(int position, View convertView, ViewGroup parent) {
	        return (ImageView) imageViews.get(position);
	    }

	    public void installClick(int position) {
	        // final int pos = position;
	        final ImageAdapter self = this;
	        Log.d("ImageAdapter", "click *" + Integer.toString(position));
	        ImageView imageView = imageViews.get(position);
	        imageView.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	//두장의 그림을 잘못 클릭했을 경우 1초동안 기다린다.
	            	if(isWrongClicked) 
	            		return;
	            	
	                int pos = imageViews.indexOf((ImageView) v);
	                Log.d("ImageAdapter", "click!" + Integer.toString(pos));
	                show(pos);

	                SilverApplication.sApp.soundCardClick();

	                // FIXME: UI update
	                // http://developer.android.com/resources/articles/timed-ui-updates.html
	                if (piece_up == -1 || piece_up == pos) {
	                    // first click
	                    piece_up = pos;
	                } else {
	                    // second click
	                    if (pieces[pos] == pieces[piece_up]) { //정답
	                        // ok, it's equal
	                        //Toast.makeText(getActivity(), "good!", 2).show();
	                    	SilverApplication.sApp.soundCardSuccess();
	                    	
	                    	//한쌍당 10 곱하기 스테이지
	                        mScore += 100 * mStage;
	                        tvScore.setText(String.valueOf(mScore));
	                        ++mCorrectPair;
	                        // remove click handler, 정답이된 그림은 더이상 클릭이 안된다.
	                        removeClick(pos);
	                        removeClick(piece_up);

	                        //check game over
                    		if (mCorrectPair == mPair) {
                    			Log.d("LDK", "game over");
                    			mVictory = true;
                    			//시간 점수 더하기 : 1단계 기준 60초가 넘으면 시간점수 없음
                    			mScore += (60 * mStage - mTime) > 0 ? (60 * mStage - mTime) * 50 : 0;
                    			tvScore.setText(String.valueOf(mScore));
                    			gameOver();
                    		}
	                    } else { //실패
	                        // try again
	                    	//시도횟수 감소
	                    	SilverApplication.sApp.soundCardFail();
	                    	--mLimit;
	                    	tvLimit.setText(String.valueOf(mLimit));

	                        Message msg = mHandler.obtainMessage();
	                        msg.arg1 = piece_up;
	                        msg.arg2 = pos;
	                        msg.what = MSG_WAIT_FOR_WRONG_CLICK;
	                        isWrongClicked = true;
	                        mHandler.sendMessageDelayed(msg, 1000);
	                        //시도횟수가 0이면 game over
	                        if(mLimit == 0) {
	                        	mVictory = false;
	                        	SilverApplication.sApp.soundCardAh();
	                        	gameOver();
	                        }
	                    }

	                    piece_up = -1;
	                }

	            }
	        });
	    }

	    public void removeClick(int position) {
	        ImageView aux;
	        aux = (ImageView) imageViews.get(position);
	        aux.setOnClickListener(null);
	    }
	    
	    public void removeAllClick() {
	    	for(int i=0; i < imageViews.size(); ++i) {
	    		ImageView aux;
	 	        aux = (ImageView) imageViews.get(i);
	 	        aux.setOnClickListener(null);
	    	}
	    }

	    public void hide(int position) {
	        ImageView img;
	        img = (ImageView) imageViews.get(position);
	        img.setImageResource(R.drawable.back);
	    }

	    public void show(int position) {
	        ImageView img;
	        img = (ImageView) imageViews.get(position);
	        int piece = pieces[position];
	        img.setImageResource(mThumbIds[piece]);

	    }
	    //
	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable._1,
	            R.drawable._2,
	            R.drawable._3,
	            R.drawable._4,
	            R.drawable._5,
	            R.drawable._6,
	            R.drawable._7,
	            R.drawable._8,
	            R.drawable._9,
	            R.drawable._10,
	            R.drawable._11,
	            R.drawable._12
	        };
	}
}
