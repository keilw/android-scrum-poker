package com.rmgtug.scrumpoker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.rmgtug.scrumpoker.adapter.CardAdapter;
import com.rmgtug.scrumpoker.net.OnServerDataListener;
import com.rmgtug.scrumpoker.net.PokerHttpWrapper;
import com.rmgtug.scrumpoker.net.ServerPacketListener;

public class ClientAndroidScrumPokerActivity extends Activity implements OnClickListener, OnItemClickListener, ServerPacketListener, OnServerDataListener {
	private String[] cardArray = null;
	
	private GridView cardGrid = null;
	
	private EditText userName, serverAddress = null;
	
	private Button btnSaveUser, btnServer = null;
	
	private String serverHostAddress = null;
	
	private int serverPort = 0;
	
	private ProgressDialog dialog = null;
	
	private ReceiveServerTask rst = null;
	
	private PokerHttpWrapper httpWrapper = null;  
	
	private SharedPreferences settings = null;
	
	private static final String SHARED_PREFERENCES_USERNAME = "com.rmgtug.scrumpoker.SHARED_PREFERENCES.username";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_main);
        cardArray = getResources().getStringArray(R.array.card_values);
        settings = getPreferences(MODE_PRIVATE);
        String uName = settings.getString(SHARED_PREFERENCES_USERNAME, "");
        
        cardGrid = (GridView) findViewById(R.id.cardView);
        cardGrid.setAdapter(new CardAdapter(cardArray, getBaseContext()));
        cardGrid.setOnItemClickListener(this);
        
        userName = (EditText)findViewById(R.id.username);
        userName.setText(uName);
        serverAddress = (EditText)findViewById(R.id.server);
        
        btnSaveUser = (Button)findViewById(R.id.btn_username);
        btnSaveUser.setOnClickListener(this);
        
        btnServer = (Button)findViewById(R.id.btn_server);
        btnServer.setOnClickListener(this);
        
        checkConnectEnabled();
    }
    
    private void checkConnectEnabled() {
    	btnServer.setEnabled((userName.getText().toString().isEmpty() == false));
    }
    
    private boolean isUsernameSet() {
    	boolean userSet = (userName.getText().toString().isEmpty() == false);
    	if (userSet == false) {
			userName.setError(getString(R.string.error_enter_username));
		}
    	return userSet;
    }
    
	@Override
	public void onItemClick(AdapterView<?> av, View view, int position, long id) {
		Toast.makeText(getBaseContext(), getResources().getStringArray(R.array.card_values)[position], Toast.LENGTH_SHORT).show();
		if (isUsernameSet()) {
			httpWrapper.submitPokerCard(cardArray[position]);
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == btnSaveUser.getId()) {
			String uName = userName.getText().toString();
			if (isUsernameSet()) {
				Editor edit = settings.edit();
				edit.putString(SHARED_PREFERENCES_USERNAME, uName).commit();
				edit.apply();
				
				checkConnectEnabled();
			}
		}
		
		if (v.getId() == btnServer.getId()) {
			if (isUsernameSet()) {
				dialog = ProgressDialog.show(ClientAndroidScrumPokerActivity.this, "", "Looking for Poker-Servers ...", true);
				dialog.setCancelable(true);
				dialog.show();
				rst = new ReceiveServerTask(this);
				rst.execute(1l);
			}
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
		if (dataPackage != null && dataPackage.getAddress() != null) {
			serverHostAddress = dataPackage.getAddress().getHostAddress();
			serverPort = dataPackage.getPort();
			
			serverAddress.setText(serverHostAddress); // + serverPort  + ":8081"
			String username = getPreferences(MODE_PRIVATE).getString(SHARED_PREFERENCES_USERNAME, "");
			httpWrapper = new PokerHttpWrapper(serverHostAddress + ":8081", username, this);
			httpWrapper.submitUser();
		} else {
			serverAddress.setError("Couldn't find any Poker-Servers!");
		}
	}
	
	@Override
	public void onDataReceived(int statusCode, String data) {
		Log.d("HTTP", "StatusCode = " + statusCode);
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
			if (socket != null) {
				socket.close();
			}
			listener.OnReceivedDataPackage(serverPacket);
		}
	}

}
