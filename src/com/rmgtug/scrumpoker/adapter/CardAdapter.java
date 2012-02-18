package com.rmgtug.scrumpoker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rmgtug.scrumpoker.R;

public class CardAdapter extends BaseAdapter {

	public static final String[] CARD_VALUES = new String[]{"K","?","0","½","1","2","3","5","8","13","20","40","100"};
	
	private LayoutInflater inflater;
	
	public CardAdapter(Context ctx) {
		inflater = LayoutInflater.from(ctx);
	}
	
	@Override
	public int getCount() {
		return CARD_VALUES.length;
	}

	@Override
	public Object getItem(int pos) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.card, null);
			
			holder.cardNumber = (TextView) convertView.findViewById(R.id.cardContent);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.cardNumber.setText(CARD_VALUES[position]);
		
		return convertView;
	}
	
	class ViewHolder {
		TextView cardNumber;
	}

}
