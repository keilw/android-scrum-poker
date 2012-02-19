package com.rmgtug.scrumpoker.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;

public class ASPBroadcastService extends Service {

	String data;

	Thread myBroadcastingThread;

	DatagramSocket socket;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		data = "ScrumPokerServer_" + Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
		try {
			socket = new DatagramSocket(6000);
			socket.setBroadcast(true);
			WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
			DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), getBroadcastAddress(wifi), 6000);
			while (true) {
				Log.d("com.rmgtug.scrumpoker", "Sending Broadcast");
				socket.send(packet);
				wait(100);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * get a WifiManager in your Activity:
	 * WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
	 * 
	 * @param wifiManager
	 * @return
	 * @throws IOException
	 */
	public static InetAddress getBroadcastAddress(WifiManager wifiManager) throws UnknownHostException {
		
		DhcpInfo dhcp = wifiManager.getDhcpInfo(); // handle null somehow
		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		return ipFromInt(broadcast);
	}
 
	public static InetAddress getLocalIPAddress(WifiManager wifiManager) throws UnknownHostException {
		DhcpInfo dhcp = wifiManager.getDhcpInfo(); // handle null somehow
		return ipFromInt(dhcp.ipAddress);
	}
	
	private static InetAddress ipFromInt(int ipFromDHCP) throws UnknownHostException {
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((ipFromDHCP >> k * 8) & 0xFF);
		return InetAddress.getByAddress(quads);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		socket.close();
		super.onDestroy();
	}

	/**
	 * Binder class for this service
	 */
	public class ASPBroadcastServiceBinder extends Binder {

		public ASPBroadcastService getService() {
			return ASPBroadcastService.this;
		}

	}

	public static class ASPBroadcastServiceConnection implements ServiceConnection {

		/**
		 * Track if we are connected to the service
		 */
		public boolean connected;

		/**
		 * The service we are connected to
		 */
		public ASPBroadcastService service;

		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			// Use explicit class as we can be sure that the right class is used
			service = ((ASPBroadcastService.ASPBroadcastServiceBinder) binder).getService();
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
