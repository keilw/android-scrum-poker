package com.rmgtug.scrumpoker.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rmgtug.scrumpoker.R;
import com.rmgtug.scrumpoker.SessionInfo;

public class SessionListAdapter extends BaseAdapter {

	protected static class ViewHolder {
		TextView tv1;
		TextView tv2;
		static int instances = 0;
		public ViewHolder() {
			instances++;
		}
	}
	
	protected ArrayList<SessionInfo> sessions;
	protected LayoutInflater inflater;
	
	public SessionListAdapter(Context ctx) {
		super();
		sessions = new ArrayList<SessionInfo>();
		inflater = LayoutInflater.from(ctx);
	}
	@Override
	public int getCount() {
		return sessions.size();
	}

	@Override
	public Object getItem(int position) {
		return sessions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	//TODO: how to get a laoyut inflater without setting it to the adapter ?
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		SessionInfo si = (SessionInfo)getItem(position);
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.session_list_item, null);
			vh.tv1  = (TextView)convertView.findViewById(R.id.session_list_item_image);
			vh.tv2  = (TextView)convertView.findViewById(R.id.session_list_item_name);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder)convertView.getTag();
		}
		
		vh.tv1.setText(si.getImgDummy());
		vh.tv2.setText(si.getSessionName());	
	//	Log.d("stefan", ""+ViewHolder.instances);
		
		return convertView;
	}
	
	public void addSessionItem(SessionInfo item) {
		this.sessions.add(item);
	}

}
