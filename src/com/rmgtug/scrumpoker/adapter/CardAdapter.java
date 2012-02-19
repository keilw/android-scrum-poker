package com.rmgtug.scrumpoker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rmgtug.scrumpoker.R;

public class CardAdapter extends BaseAdapter {

	private final String[] cardValues;
	
	private final LayoutInflater inflater;
	
	public CardAdapter(String[] values, Context ctx) {
		cardValues = values;
		inflater = LayoutInflater.from(ctx);
	}
	
	@Override
	public int getCount() {
		return cardValues!=null ? cardValues.length : 0;
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
		
		holder.cardNumber.setText(cardValues[position]);
		
		return convertView;
	}
	
	class ViewHolder {
		TextView cardNumber;
	}

}
