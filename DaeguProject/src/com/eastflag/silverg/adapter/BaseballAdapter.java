package com.eastflag.silverg.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastflag.silverg.R;
import com.eastflag.silverg.dto.BaseballVO;

public class BaseballAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<BaseballVO> mBaseballList;
	
	public BaseballAdapter(Context context, List<BaseballVO> baseballList) {
		mContext = context;
		mBaseballList = baseballList;
	}

	@Override
	public int getCount() {
		return mBaseballList.size();
	}

	@Override
	public Object getItem(int position) {
		return mBaseballList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = View.inflate(mContext, R.layout.fragment_11_record_head, null);
		}
		
		TextView tvRank = (TextView) convertView.findViewById(R.id.tvRank);
		TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
		TextView tvHit = (TextView) convertView.findViewById(R.id.tvHit);
		TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
		
		tvRank.setText(String.valueOf(position+1));
		int time = mBaseballList.get(position).getTime();
		tvTime.setText(String.format("%02d:%02d", time/60, time%60));
		tvHit.setText(String.valueOf(mBaseballList.get(position).getHit()));
		tvDate.setText(mBaseballList.get(position).getCreated_date());
		
		return convertView;
	}

}
