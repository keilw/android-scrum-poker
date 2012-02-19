package com.rmgtug.scrumpoker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class AndroidScrumPokerActivity extends Activity implements OnClickListener {

	ArrayAdapter<String> aa;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterboard);
		Button myButton = (Button) findViewById(R.id.btnBroadcast);
		myButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Broadcast like there is no tomorrow!
		ASyncDing myDing = new ASyncDing();
		myDing.execute(1L);
		Log.d("com.rmgtug.scrumpoker", "Broadcast, my ass!");

	}

	InetAddress getBroadcastAddress() throws IOException {
		WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		DhcpInfo dhcp = wifi.getDhcpInfo();
		// handle null somehow

		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		return InetAddress.getByAddress(quads);
	}

	class ASyncDing extends AsyncTask<Long, Long, Long> {

		@Override
		protected Long doInBackground(Long... params) {
			DatagramSocket socket;
			String data = "test1343";
			try {
				socket = new DatagramSocket(1337);

				socket.setBroadcast(true);
				DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), getBroadcastAddress(), 1338);
				socket.send(packet);
				socket.close();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 1L;
		}

	}
}