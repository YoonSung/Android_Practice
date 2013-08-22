package com.example.practicegcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class RegisterReceive extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("       registerreceive        ", "called ");
		GCMRegistrar.register(context, MainActivity.senderId);
	}

}
