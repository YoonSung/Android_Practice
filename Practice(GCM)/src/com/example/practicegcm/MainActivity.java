package com.example.practicegcm;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class MainActivity extends Activity {
//	private static final String SENDER_ID = "563625196391";
//	private static String 		API_KEY = "AIzaSyA3mnsAOL9vsGZv8GNhr2QXQQkZgKHLHbE";//"자신이 발급받은 API KEY를 입력하세요";
	
	static String senderId = "563625196391";
	static String apiKey = "AIzaSyA3mnsAOL9vsGZv8GNhr2QXQQkZgKHLHbE";
	
	Button btnSendGcmMessage;
	
	//토스트 팝업을 띄우기 위한 핸들러 
	static final Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		String regId = GCMRegistrar.getRegistrationId(this);
		
		Log.d("@@@@", "regId : "+regId);
		
		btnSendGcmMessage = (Button)findViewById(R.id.btnSend);
		btnSendGcmMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Sender sender = new Sender(apiKey);
						Message message = new Message.Builder().addData("title", "welcom")
															   .addData("msg", "introduce me!").build();
						
						try {
							String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
							Result result = sender.send(message, regId, 5);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		
		if(regId.equals("")) {
			GCMRegistrar.register(this, senderId);
			Log.d("@@@@", "register execute : "+regId);
		} else {
			Log.d("@@@@", "regId already register : "+regId);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar  if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
