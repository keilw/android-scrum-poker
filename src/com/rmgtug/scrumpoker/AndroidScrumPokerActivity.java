package com.rmgtug.scrumpoker;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.jetty.server.Server;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rmgtug.scrumpoker.service.ASPBroadcastService;
import com.rmgtug.scrumpoker.service.RestServiceHandler;

public class AndroidScrumPokerActivity extends Activity implements ServiceConnection {

	ArrayAdapter<String> aa;
	protected Server jetty = null;
	ASPBroadcastService broadCastService = null;
	
	private final static int serverPort = 8081; //TODO: make configurable 
	
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
		// Use explicit class as we can be sure that the right class is used
		broadCastService = ((ASPBroadcastService.ASPBroadcastServiceBinder) binder).getService();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void onStop() {
		
		if (jetty != null) {
			try {
				jetty.stop();
				jetty = null;
			} catch (Exception e) {
				Log.e("stefan", "Jetty couldn't stop:" + e);
			}
		}
		super.onStop();
	}
	/**
	 * handler for server Jetty button
	 * @param v
	 */
	public void jettyBtnClicked(View v) {
		
		if (jetty == null)
			jetty = new Server(serverPort);
		try {
			WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
			InetAddress myIp = ASPBroadcastService.getLocalIPAddress(wifi);
			jetty.setHandler(new RestServiceHandler());
			jetty.start();
			
			Toast.makeText(this, "started Web server @ " + myIp, Toast.LENGTH_LONG).show();
			Log.i("com.rmgtug", "started jetty server " + myIp);
        } catch (UnknownHostException e1) {
        	Log.e("com.rmgtug", "couldnt get local IP address: " + e1);
			e1.printStackTrace();
		} catch (Exception e) {
			Log.e("com.rmgtug", "exception starting Web server: " + e);
		}
	} 
	
	
	/*
	 * the right but complicated way
	 * 
	private String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e("stefan", ex.toString());
	    }
	    return null;
	}
	
	*/
	
}