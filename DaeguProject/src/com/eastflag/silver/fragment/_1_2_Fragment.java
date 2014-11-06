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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eastflag.silver.R;

public class _1_2_Fragment extends Fragment {
	
	private static final int MSG_TIMER = 1;
	
	private static final int STAGE_1 = 6;
	private static final int STAGE_2 = 8;
	private static final int STAGE_3 = 10;
	private static final int STAGE_4 = 12;
	
	private int mWidth = 1080;
	
	private View mView;
	private TextView tvTimer, tvLimit, tvScore, tvStage;
	private GridView mGridView;
	
	private int mTime;
	private int mLimit = 10; //10회까지 시도
	private int mScore;
	private int mStage = 1; //최초 1단계
	
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_12, null);
		
		tvTimer = (TextView) mView.findViewById(R.id.tvTimer);
		tvLimit = (TextView) mView.findViewById(R.id.tvLimit);
		tvScore = (TextView) mView.findViewById(R.id.tvScore);
		tvStage = (TextView) mView.findViewById(R.id.tvStage);
		mGridView = (GridView) mView.findViewById(R.id.gridview);
		
		mGridView.setAdapter(new ImageAdapter(STAGE_1));
		mGridView.setNumColumns(4);
		
		//타이머 작동
		mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
		
		return mView;
	}
	
	private void gameOver() {
		//초기화
	
	}
	
	private void restartGame(int stage) {
		//초기화
		mTime = 0;
		mHandler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
		mLimit = 10;
		mScore = 0;
		
	}

	@Override
	public void onDestroyView() {
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
	            int width = 0;
	            if(mPair == 6 || mPair == 8) {
	            	width = mWidth/4;
	            } else if (mPair == 10 || mPair == 12){
	            	width = mWidth/5;
	            } else {
	            	width = mWidth/4;
	            }
	            imageView.setLayoutParams(new GridView.LayoutParams(width, width));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(2, 2, 2, 2);

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


	                // FIXME: UI update
	                // http://developer.android.com/resources/articles/timed-ui-updates.html
	                if (piece_up == -1 || piece_up == pos) {
	                    // first click
	                    piece_up = pos;
	                } else {
	                    // second click
	                    if (pieces[pos] == pieces[piece_up]) {
	                        // ok, it's equal
	                        Toast.makeText(getActivity(), "good!", 2).show();
	                        mScore += 10 * mStage;
	                        tvScore.setText(String.valueOf(mScore));
	                        ++mCorrectPair;
	                        // remove click handler
	                        removeClick(pos);
	                        removeClick(piece_up);
	                        //check game over
                    		if (mCorrectPair == mPair) {
                    			Log.d("LDK", "game over");
                    			gameOver();
                    		}
	                    } else {
	                        // try again
	                    	//시도횟수 감소
	                    	--mLimit;
	                    	tvLimit.setText(String.valueOf(mLimit));
	                        int aux[] = {piece_up, pos};
	                        SleepHide update = new SleepHide(getActivity(), self, aux);
	                        Handler mHandler = new Handler();
	                        mHandler.postDelayed(update, 2000);
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
	            R.drawable.american_gangster_icon,
	            R.drawable.a_moment_icon,
	            R.drawable.angels_and_demons_icon,
	            R.drawable.apocalypto_icon,
	            R.drawable.i300_icon,
	            R.drawable.b13_u_icon,
	            R.drawable.baby_and_me_icon,
	            R.drawable.bangkok_dangerous_icon,
	            R.drawable.batman_begins_1_icon,
	            R.drawable.batman_begins_2_icon,
	            R.drawable.batman_begins_3_icon,
	            R.drawable.batman_dark_knight_icon,
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