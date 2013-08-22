package com.example.practicealarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Common {

	Context context;
	AlarmManager alarmManager;
	
	public Common(Context context) {
		this.context = context;
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	public void setNoticeAlarm(Calendar calendar, int requestId) {
		// alarm repeating everyday(24*60*60*1000)
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 3000, getNoticeAlarmPendingIntent(requestId));// 24*60*60*1000
		Log.i("Common setAlarm called", calendar.getTime().toString());
	}

	public PendingIntent getNoticeAlarmPendingIntent(int requestId) {
		Intent intent = new Intent(context, AlarmService.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}
	
	private void cancelNoticeAlarm(int requestId) {
		alarmManager.cancel(getNoticeAlarmPendingIntent(requestId));
	}
}
