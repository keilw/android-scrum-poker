package com.rmgtug.scrumpoker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AndroidScrumPokerActivity extends Activity {

	ArrayAdapter<String> aa;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterboard);
	}
}