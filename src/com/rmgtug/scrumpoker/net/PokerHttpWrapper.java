package com.rmgtug.scrumpoker.net;

import android.os.AsyncTask;
import android.util.Log;

public class PokerHttpWrapper {

	private HttpConnection connection = null;
	
	private String serverAddress, username = null;
	
	private OnServerDataListener listener = null;
	
	private SubmitTask st = null;
	
	public PokerHttpWrapper(String serverAddress, String username, OnServerDataListener listener) {
		this.serverAddress = serverAddress;
		this.username = username;
		this.listener = listener;
		connection = HttpConnection.getInstance();
	}
	
	public void submitPokerCard(String cardContent) {
		String url = "http://" + serverAddress + "/card/"+cardContent;
		st = null;
		st = new SubmitTask();
		st.execute(url);
	}
	
	public void submitUser() {
		String url = "http://" + serverAddress + "/user/"+username;
		st = null;
		st = new SubmitTask();
		st.execute(url);
	}
	
	public void submitLogout() {
		String url = "http://" + serverAddress + "/logout/"+username;
		st = null;
		st = new SubmitTask();
		st.execute(url);
	}
	
	class SubmitTask extends AsyncTask<String, Long, String> {
		
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			
			for (String url : params) {
				Log.d("HTTP", url);
				result = connection.getData(url);
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			listener.onDataReceived(connection.getLastStatusCode(), result);
		}
		
	}
	
}
