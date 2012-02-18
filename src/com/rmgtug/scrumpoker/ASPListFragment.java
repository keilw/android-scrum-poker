package com.rmgtug.scrumpoker;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ASPListFragment extends Fragment {

	protected ArrayAdapter<String> aa;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//ArrayList<String> fusel = new ArrayList<String>();
		String[] q = {"Stefan","Mario", "Norbert"};
		aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, q);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.session_list, container, false);
		ListView lv = (ListView) v.findViewById(R.id.sessionlist);
		lv.setAdapter(aa);
		return v;
	}
}