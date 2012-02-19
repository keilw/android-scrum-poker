package com.rmgtug.scrumpoker;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ServerClientSelectActivityList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_client_select);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent();
		if (position == 0) {
			try {
				Class.forName("android.app.Fragment");
				intent.setClass(getApplicationContext(), AndroidScrumPokerActivity.class);
				startActivity(intent);
			} catch (ClassNotFoundException exception) {
				Toast.makeText(getBaseContext(), "Your device does not support fragments!", Toast.LENGTH_LONG).show();
			}
		} else if (position == 1) {
			intent.setClass(getApplicationContext(), ClientAndroidScrumPokerActivity.class);
			startActivity(intent);
		}
	}
}
