package com.rmgtug.scrumpoker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rmgtug.scrumpoker.adapter.SessionListAdapter;

public class ASPListFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//String[] q = { "Stefan", "Mario", "Norbert" };
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.session_list, container, false);
		ListView lv = (ListView) v.findViewById(R.id.sessionlist);
		SessionListAdapter sla = SessionListAdapter.getInstance(getActivity().getBaseContext());
		lv.setAdapter(sla);

		return v;
	}

}