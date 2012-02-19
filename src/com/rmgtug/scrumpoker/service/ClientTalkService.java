package com.rmgtug.scrumpoker.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ClientTalkService extends Service {

	/**
	 * Provide a static binder for this service
	 */
	private final IBinder mBinder = new ClientTalkServiceBinder();

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("Service", "onBind ...");
		sendStuff();
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("Service", "onUnbind intent = " + intent.toString());
		return true;
	}

	private void sendStuff() {
		String cmd = "my command";
		try {
			InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6000);
			DatagramPacket request = new DatagramPacket(cmd.getBytes(), cmd.length(), address);
			DatagramSocket socket = new DatagramSocket();
			socket.send(request);
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRebind(Intent intent) {
		Log.d("Service", "onRebind intent = " + intent.toString());
		super.onRebind(intent);
	}

	/**
	 * Binder class for this service
	 */
	public class ClientTalkServiceBinder extends Binder {

		public ClientTalkService getService() {
			return ClientTalkService.this;
		}

	}

	public static class ClientTalkServiceConnection implements
			ServiceConnection {

		/**
		 * Track if we are connected to the service
		 */
		public boolean connected;

		/**
		 * The service we are connected to
		 */
		public ClientTalkService service;

		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			// Use explicit class as we can be sure that the right class is used
			service = ((ClientTalkService.ClientTalkServiceBinder) binder)
					.getService();
			connected = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// Should never happen as the service runs local
			service = null;
			connected = false;
		}

	}

}
