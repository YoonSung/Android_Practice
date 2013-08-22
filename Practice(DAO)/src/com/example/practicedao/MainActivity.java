package com.example.practicedao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button btnUser, btnSafetyZone, btnSafetyTime, btnMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnUser = (Button)findViewById(R.id.btnUser);
		btnUser.setOnClickListener(this);
		
		btnSafetyZone = (Button)findViewById(R.id.btnSafetyZone);
		btnSafetyZone.setOnClickListener(this);
		
		btnSafetyTime = (Button)findViewById(R.id.btnSafetyTime);
		btnSafetyTime.setOnClickListener(this);
		
		btnMessage = (Button)findViewById(R.id.btnMsg);
		btnMessage.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnUser:
			startActivity(new Intent(MainActivity.this, TestUser.class));	
			break;
		case R.id.btnSafetyZone:
			startActivity(new Intent(MainActivity.this, TestSafetyZone.class));
			break;
		case R.id.btnSafetyTime:
			startActivity(new Intent(MainActivity.this, TestSafetyTime.class));
			break;
		case R.id.btnMsg:
			startActivity(new Intent(MainActivity.this, TestMessage.class));
			break;
		}
		
	}

}
