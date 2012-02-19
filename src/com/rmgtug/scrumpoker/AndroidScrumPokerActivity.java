package com.rmgtug.scrumpoker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.rmgtug.scrumpoker.service.ASPBroadcastService;

public class AndroidScrumPokerActivity extends Activity implements ServiceConnection {

	ASPBroadcastService broadCastService = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterboard);
	}

	@Override
	protected void onDestroy() {
		getApplicationContext().unbindService(this);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		Log.d("com.rmgtug.scrumpoker", "binding ASPBroadcastServer");
		getApplicationContext().bindService(new Intent(getBaseContext(), ASPBroadcastService.class), this,
				Context.BIND_AUTO_CREATE);
		super.onResume();

	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder binder) {
		Log.d("com.rmgtug.scrumpoker", "onServiceConnected ... binder = " + binder.getClass().getSimpleName());
		broadCastService = ((ASPBroadcastService.ASPBroadcastServiceBinder) binder).getService();

	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub

	}

}