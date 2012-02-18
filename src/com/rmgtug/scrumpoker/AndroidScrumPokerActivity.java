package com.rmgtug.scrumpoker;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidScrumPokerActivity extends Activity {

	ArrayAdapter<String> aa;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterboard);
		ArrayList<String> fusel = new ArrayList<String>();
		fusel.add("Test123");
		fusel.add("Test123");
		fusel.add("Test123");
		fusel.add("Test123");
		fusel.add("Test123");
		fusel.add("Test123");
		aa = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, fusel);
		ListView lv = (ListView) findViewById(R.id.cardboard);
		lv.setAdapter(aa);

	}
}