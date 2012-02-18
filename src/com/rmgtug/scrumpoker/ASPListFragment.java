package com.rmgtug.scrumpoker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rmgtug.scrumpoker.adapter.SessionListAdapter;

public class ASPListFragment extends Fragment {

	protected SessionListAdapter sla;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		String[] q = {"Stefan","Mario", "Norbert"};
		sla = new SessionListAdapter(getActivity().getBaseContext());
		for(String s :q) {
			sla.addSessionItem(new SessionInfo(s, "dummyImage"));
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.session_list, container, false);
		ListView lv = (ListView) v.findViewById(R.id.sessionlist);
		lv.setAdapter(sla);
		return v;
	}
}