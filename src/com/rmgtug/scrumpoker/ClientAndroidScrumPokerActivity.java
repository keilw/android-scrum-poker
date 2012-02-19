package com.rmgtug.scrumpoker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.rmgtug.scrumpoker.adapter.CardAdapter;
import com.rmgtug.scrumpoker.net.ServerPacketListener;

public class ClientAndroidScrumPokerActivity extends Activity implements OnClickListener, OnItemClickListener, ServerPacketListener {

	private GridView cardGrid = null;
	
	private EditText userName, serverAddress = null;
	
	private Button btnUserName, btnServer = null;
	
	private String serverHostAddress = null;
	
	private int serverPort = 0;
	
	private ProgressDialog dialog = null;
	
	private ReceiveServerTask rst = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_main);
        
        cardGrid = (GridView) findViewById(R.id.cardView);
        cardGrid.setAdapter(new CardAdapter(getBaseContext()));
        cardGrid.setOnItemClickListener(this);
        
        userName = (EditText)findViewById(R.id.username);
        serverAddress = (EditText)findViewById(R.id.server);
        
        btnUserName = (Button)findViewById(R.id.btn_username);
        btnUserName.setOnClickListener(this);
        
        btnServer = (Button)findViewById(R.id.btn_server);
        btnServer.setOnClickListener(this);
        
    }

	@Override
	public void onItemClick(AdapterView<?> av, View view, int position, long id) {
		Toast.makeText(getBaseContext(), CardAdapter.CARD_VALUES[position], Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == btnUserName.getId()) {
			if (userName.getText().toString().isEmpty()) {
				userName.setError(getString(R.string.error_enter_username));
			} else {
				saveAndSendUserName(userName.getText().toString());
			}
		}
		
		if (v.getId() == btnServer.getId()) {
			dialog = ProgressDialog.show(ClientAndroidScrumPokerActivity.this, "", "Looking for Poker-Servers ...", true);
			dialog.setCancelable(true);
			dialog.show();
			rst = new ReceiveServerTask(this);
			rst.execute(1l);
		}
	}

	@Override
	public void OnReceivedDataPackage(DatagramPacket dataPackage) {
		if (rst != null) {
			rst = null;
		}
		if (dialog != null) {
			dialog.dismiss();
		}
		if (dataPackage != null) {
			serverHostAddress = dataPackage.getAddress().getHostAddress();
			serverPort = dataPackage.getPort();
			
			serverAddress.setText(serverHostAddress + ":" + serverPort);
		} else {
			serverAddress.setError("Coulnd't find any PokerServer!");
		}
	}
	
	
	private void saveAndSendUserName(String userName) {
		
	}
	
	class ReceiveServerTask extends AsyncTask<Long, Long, DatagramPacket> {

		private String data;
		
		private DatagramSocket socket;
		
		private ServerPacketListener listener;
		
		public ReceiveServerTask(ServerPacketListener listener) {
			data = "ScrumPokerServer_" + Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
			this.listener = listener;
		}
		
		@Override
		protected DatagramPacket doInBackground(Long... params) {
			DatagramPacket serverPacket = null;
			try {
				Log.d("SocketAddress", "Listening for Poker Server ...");
				
				socket = new DatagramSocket(6000);
				socket.setSoTimeout(60000);
				serverPacket = new DatagramPacket(data.getBytes(), data.length());
				socket.receive(serverPacket);
				InetAddress addr = serverPacket.getAddress();
				serverHostAddress = addr.getHostAddress();
				serverPort = serverPacket.getPort();
				
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return serverPacket;
		}
		
		@Override
		protected void onPostExecute(DatagramPacket serverPacket) {
			socket.close();
			listener.OnReceivedDataPackage(serverPacket);
		}
	}
	
}
