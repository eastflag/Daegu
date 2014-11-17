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

import com.eastflag.silver.R;
import com.eastflag.silver.SilverApplication;
import com.eastflag.silver.util.Utils;

public class _1_2_Fragment extends Fragment {
	
	private static final int MSG_TIMER = 1;
	
	private static final int STAGE_1 = 6;
	private static final int STAGE_2 = 8;
	private static final int STAGE_3 = 10;
	private static final int STAGE_FINAL = 12;
	
	//private int mWidth = 1080;
	
	private View mView;
	private TextView tvTimer, tvLimit, tvScore, tvStage;
	private GridView mGridView;
	private LinearLayout rootVictory;
	private TextView tvResult;
	private Button btnRestart;
	
	private int mTime;
	private int mLimit = 10; //10회까지 시도
	private int mScore;
	private int mStage = 1; //최초 1단계
	private boolean mVictory;
	
	private int mPair; //1단계:6쌍, 2단계 8쌍, 3단계: 10쌍, 4단계:12쌍
	private int mCorrectPair;
    private Integer[] pieces;
    private ArrayList<ImageView> imageViews;
    private int piece_up = -1;
	
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
		btnRestart = (Button) mView.findViewById(R.id.btnRestart);
		
		btnRestart.setOnClickListener(mClick);
		
		mGridView.setAdapter(new ImageAdapter(STAGE_1));
		mGridView.setNumColumns(4);
		
		//타이머 작동
		mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
		
		return mView;
	}
	
	private void gameOver() {
		//초기화
		rootVictory.setVisibility(View.VISIBLE);
		mHandler.removeMessages(MSG_TIMER);
		if(mVictory) {
			tvResult.setText("Victory!!!");
			SilverApplication.sApp.soundClap();
			
			if(mPair == STAGE_FINAL) {
				btnRestart.setText("다시하기");
			} else {
				btnRestart.setText("다음 단계");
			}
		} else {
			tvResult.setText("Fail!!!");
			btnRestart.setText("다시하기");
		}
		
	}
	
	private void restartGame() {
		//Next Stage
		if(mVictory) {
			if(mStage != STAGE_FINAL) {
				++mStage;
				mPair += 2;
			}
		} 
		mGridView.setAdapter(new ImageAdapter(mPair));
		int num = mPair<=8 ? 4 : 5;
		mGridView.setNumColumns(num);
		//초기화
		rootVictory.setVisibility(View.GONE);
		mTime = 0;
		mCorrectPair = 0;
		mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
		mLimit = 5 + 5 * mStage;
		mScore = 0;
		tvTimer.setText(String.format("%02d:%02d", mTime/60, mTime%60));
		tvLimit.setText(String.valueOf(mLimit));
		tvScore.setText(String.valueOf(mScore));
		tvStage.setText(String.format("%d단계", mStage));
	}

	@Override
	public void onDestroyView() {
		mHandler.removeMessages(MSG_TIMER);
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

	            imageView.setImageResource(R.drawable.back);
	            imageViews.add(imageView);

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
	                    if (pieces[pos] == pieces[piece_up]) {
	                        // ok, it's equal
	                        //Toast.makeText(getActivity(), "good!", 2).show();
	                    	SilverApplication.sApp.soundCardSuccess();
	                    	
	                    	//한쌍당 10 곱하기 스테이지
	                        mScore += 10 * mStage;
	                        tvScore.setText(String.valueOf(mScore));
	                        ++mCorrectPair;
	                        // remove click handler
	                        removeClick(pos);
	                        removeClick(piece_up);
	                        //check game over
                    		if (mCorrectPair == mPair) {
                    			Log.d("LDK", "game over");
                    			mVictory = true;
                    			//시간 점수 더하기 : 1단계 기준 60초가 넘으면 시간점수 없음
                    			mScore += (60 * mStage - mTime) > 0 ? (60 * mStage - mTime) * 50 : 0;
                    			gameOver();
                    		}
	                    } else {
	                        // try again
	                    	//시도횟수 감소
	                    	SilverApplication.sApp.soundCardFail();
	                    	--mLimit;
	                    	tvLimit.setText(String.valueOf(mLimit));
	                        int aux[] = {piece_up, pos};
	                        SleepHide update = new SleepHide(getActivity(), self, aux);
	                        Handler mHandler = new Handler();
	                        mHandler.postDelayed(update, 1000);
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
	            R.drawable.cen,
	            R.drawable.forest,
	            R.drawable.gladiator,
	            R.drawable.kawnhae,
	            R.drawable.leon,
	            R.drawable.matrix,
	            R.drawable.memory,
	            R.drawable.radio,
	            R.drawable.ring,
	            R.drawable.three,
	            R.drawable.yeobgi,
	            R.drawable.movie_image
	        };
	}
	
	class SleepHide implements Runnable {
	    private int[] positions;
	    private ImageAdapter adapter;

	    public SleepHide(Context c, ImageAdapter b, int[] pos) {
	        adapter = b;
	        positions = pos;

	        Log.d("SleepHide", "click!");
	        adapter.removeClick(pos[0]);
	        adapter.removeClick(pos[1]);
	    }

	    public void run() {
	        Log.d("SleepHide", "run!");
	        adapter.hide(positions[0]);
	        adapter.hide(positions[1]);
	        adapter.installClick(positions[0]);
	        adapter.installClick(positions[1]);
	    }
	}
}
