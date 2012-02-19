package com.rmgtug.scrumpoker;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.rmgtug.scrumpoker.service.ClientTalkService;

public class ServerClientSelectActivityList extends ListActivity implements ServiceConnection {

	private ClientTalkService clientTalkService = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_client_select);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getApplicationContext().bindService(new Intent(getBaseContext(), ClientTalkService.class), this, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getApplicationContext().unbindService(this);
	}
	
	@Override
	public void onServiceConnected(ComponentName name, IBinder binder) {
		Log.d("Service", "onServiceConnected ... binder = " + binder.getClass().getSimpleName());
		// Use explicit class as we can be sure that the right class is used
		clientTalkService = ((ClientTalkService.ClientTalkServiceBinder) binder).getService();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		
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
