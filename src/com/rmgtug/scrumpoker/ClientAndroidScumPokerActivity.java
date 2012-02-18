package com.rmgtug.scrumpoker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.rmgtug.scrumpoker.adapter.CardAdapter;

public class ClientAndroidScumPokerActivity extends Activity implements OnClickListener, OnItemClickListener {

	private GridView cardGrid = null;
	
	private EditText userName = null;
	
	private Button btnUserName = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_main);
        
        cardGrid = (GridView) findViewById(R.id.cardView);
        cardGrid.setAdapter(new CardAdapter(getBaseContext()));
        cardGrid.setOnItemClickListener(this);
        
        userName = (EditText)findViewById(R.id.username);
        
        btnUserName = (Button)findViewById(R.id.btn_username);
        btnUserName.setOnClickListener(this);
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
	}

	private void saveAndSendUserName(String userName) {
		
	}
	
}
