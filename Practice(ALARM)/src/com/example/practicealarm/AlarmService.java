package com.example.practicealarm;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class AlarmService extends BroadcastReceiver {

	Context _context;
	
	@Override
	public void onReceive(Context context, Intent intent) { 
		_context = context;
		
		//MidnightCallee mc = new MidnightCallee();
		
		ContactDB db = new ContactDB(context);
		db.open();
		
		try{
		Cursor cs = db.selectAllAlarm();
		
		if(cs!=null){
			if(cs.getCount()==0)
				return;
			Log.e("AlarmService", "count : "+cs.getCount());
			cs.moveToFirst();
	/*	
			{ALARM_ID,
					ALARM_REQUEST, 
					ALARM_GROUP, 
					ALARM_ACTIVATE,
					ALARM_HOUR,
					ALARM_MINUTE,
					ALARM_MON,
					ALARM_TUE,
					ALARM_WED,
					ALARM_THU,
					ALARM_FRI,
					ALARM_SAT,
					ALARM_SUN
		*/
			Calendar day_of_Week = Calendar.getInstance();
				
				int hour = day_of_Week.get(Calendar.HOUR_OF_DAY);
				int day = day_of_Week.get(Calendar.DAY_OF_WEEK);
				Log.e("AlarmService", "hour : "+hour+" / day : "+day);
				if(hour==cs.getInt(3)){
					
				do{
					switch(day){
					case 1:
						if(cs.getInt(3)==hour)
							showMsg();
						Log.e("AlarmService", ""+cs.getInt(11));
						continue;
					case 2:
						if(cs.getInt(3)==hour)
							showMsg();
						Log.e("AlarmService", ""+cs.getInt(5));
						continue;
					case 3:
						if(cs.getInt(3)==hour)
							showMsg();
						Log.e("AlarmService", ""+cs.getInt(6));
						continue;
					case 4:
						if(cs.getInt(3)==hour)
							showMsg();
						Log.e("AlarmService", ""+cs.getInt(7));
						continue;
					case 5:
						if(cs.getInt(3)==hour)
							showMsg();
						Log.e("AlarmService", ""+cs.getInt(8));
						continue;
					case 6:
						if(cs.getInt(3)==hour)
							showMsg();
						Log.e("AlarmService", ""+cs.getInt(9));
						continue;
					case 7:
						if(cs.getInt(3)==hour)
							showMsg();
						Log.e("AlarmService", ""+cs.getInt(10));
						continue;
					}
				}while(cs.moveToNext());
				}
		}
		}catch(Exception e){
			Log.e("AlarmService", ""+e);
		}
			//type1
			//Intent intents = new Intent(context, Noticeuser.class);
			Toast.makeText(context, "AlarmService Called End ", 2000).show();
//			intent.putExtra("TYPE", 1);
//			PendingIntent pintent = PendingIntent.getActivity(context, 2, intents, PendingIntent.FLAG_CANCEL_CURRENT);
//			showMsg();
			
			db.close();
	}

	
	public void showMsg() {
		
		Toast.makeText(_context, "AlarmService showMSg called ", 2000).show();
//		NotificationManager nm = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
//		Intent intent = new Intent(_context, Noticeuser.class);
//		PendingIntent pintent = PendingIntent.getActivity(_context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//		
//		Notification notification = new Notification(R.drawable.icon,
//				"????????????", System.currentTimeMillis());
//		notification.setLatestEventInfo(_context, "???????? ?? ?????? ????????????.",
//				"?????? ?????? ???? ????????.", pintent);
//
//			notification.defaults |= Notification.DEFAULT_ALL;
//		
//		notification.flags = Notification.FLAG_AUTO_CANCEL;
//		int notiId = (int)System.currentTimeMillis();//0xffff0008;
//		nm.notify(notiId, notification);
//		
//		/*
//		Notification noti = new Notification.Builder(_context)
//				.setContentTitle("?????? ????")
//				.setContentText("?????????")
//				.setSmallIcon(R.drawable.ic_launcher)
//				.build();
//		*/
//		
//		
	}
	
}
