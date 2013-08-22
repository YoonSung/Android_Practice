package com.example.practicealarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class AlarmSetDetail extends Activity implements OnDateChangedListener,
		OnTimeChangedListener, OnClickListener, OnCheckedChangeListener {
	private AlarmManager mManager;
	private GregorianCalendar mCalendar;
	private DatePicker mDate;
	private TimePicker mTime;
	private NotificationManager mNotification;
	
	Button btn_set, btn_reset, mon, tue, wed, thu, fri, sat, sun;
	TextView time;
	ArrayList<Integer> week;
	Cursor cs;
	boolean isupdate = false;
	ContactDB db;
	int req;
	
	int current_hour;
	int current_minute;
	CheckBox checkbox;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		
		Intent intent = getIntent();
		isupdate = intent.getBooleanExtra("isupdate",false);
		req = intent.getIntExtra("id", -1);
		//exception is not making
		
		time = (TextView)findViewById(R.id.time);
		btn_reset = (Button) findViewById(R.id.reset);
		btn_set = (Button) findViewById(R.id.set);
		mDate = (DatePicker) findViewById(R.id.date_picker);
		mTime = (TimePicker) findViewById(R.id.time_picker);
		
		mon = (Button)findViewById(R.id.mon);
		tue = (Button)findViewById(R.id.tue);
		wed = (Button)findViewById(R.id.wed);
		thu = (Button)findViewById(R.id.thu);
		fri = (Button)findViewById(R.id.fri);
		sat = (Button)findViewById(R.id.sat);
		sun = (Button)findViewById(R.id.sun);
		
		checkbox = (CheckBox)findViewById(R.id.checkbox);
		checkbox.setOnCheckedChangeListener(this);
		
		mon.setOnClickListener(this);
		tue.setOnClickListener(this);
		wed.setOnClickListener(this);
		thu.setOnClickListener(this);
		fri.setOnClickListener(this);
		sat.setOnClickListener(this);
		sun.setOnClickListener(this);
		
		
		btn_set.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
		
		
		//Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
		
		mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		mCalendar = new GregorianCalendar();
		
		mDate.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
				   mCalendar.get(Calendar.DAY_OF_MONTH), this);
		
		mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
		mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
		mTime.setOnTimeChangedListener(this);
		
		
		week = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0));
		
		db = new ContactDB(this);
		db.open();
		
		Toast.makeText(this, "isupdate : "+isupdate + "  \\  request : "+req, 2000).show();
		
		if(isupdate==true){
			cs = db.selectIDAlarm(""+req);
			cs.moveToFirst();
			req = cs.getInt(1);
			
			
			mTime.setCurrentHour(cs.getInt(3));
			mTime.setCurrentMinute(cs.getInt(4));
			
			week.set(0, cs.getInt(5));
			week.set(1, cs.getInt(6));
			week.set(2, cs.getInt(7));
			week.set(3, cs.getInt(8));
			week.set(4, cs.getInt(9));
			week.set(5, cs.getInt(10));
			week.set(6, cs.getInt(11));
			
			if(	week.get(0)==1 &&
				week.get(1)==1 &&
				week.get(2)==1 &&
				week.get(3)==1 &&
				week.get(4)==1 &&
				week.get(5)==1 &&
				week.get(6)==1) {
				checkbox.setChecked(true);
				System.out.println("need to check !! ");
			}
			
			for (int i = 0; i < 7; i++) {
				System.out.println("week.get("+i+")= "+week.get(i));
			}
			
			checkAllButton();
			/*
	public Cursor selectAllAlarm(){
		return mDB.query(ALARM_TABLE, new String[]
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
				}, null, null, null, null, null);
	} 
			 */
		}else{
			cs = db.selectAllAlarm();
			req = cs.getCount()+1;
		}
	}

	private void setAlarm() {
		//alarm repeating everyday(24*60*60*1000)
		mManager.setRepeating(AlarmManager.RTC_WAKEUP,
				mCalendar.getTimeInMillis(), 10 * 1000, pendingIntent());//24*60*60*1000
		Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
	}

	private void resetAlarm() {
		mManager.cancel(pendingIntent());
	}	
	
	private PendingIntent pendingIntent() {

		Intent i = new Intent(this, AlarmService.class);
		
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
		return pi;
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		mCalendar.set(year, monthOfYear, dayOfMonth, mTime.getCurrentHour(),
				mTime.getCurrentMinute());
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		
		current_hour = hourOfDay;
		current_minute = minute;
		
		String half = "오전";
		String hour = ""+hourOfDay;
		String min = ""+minute;
		
		
		if (hourOfDay>12){
			half = "오후";
			//hour = ""+hourOfDay/2;
		}
		
		if (minute<10){
			min = "0"+minute;
		}

		time.setText(half+" "+hour+":"+min);
		mCalendar.set(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),
				hourOfDay, minute);
	}

	@Override
	protected void onStop() {
		super.onStop();
		db.close();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.reset :
			week.set(0, 0);
			week.set(1, 0);
			week.set(2, 0);
			week.set(3, 0);
			week.set(4, 0);
			week.set(5, 0);
			week.set(6, 0);
			uncheckButton(mon);
			uncheckButton(tue);
			uncheckButton(wed);
			uncheckButton(thu);
			uncheckButton(fri);
			uncheckButton(sat);
			uncheckButton(sun);
			checkbox.setChecked(false);
			
			mCalendar = new GregorianCalendar();
			mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
			mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
			
			resetAlarm();
			break;
		case R.id.set :
			if(!isupdate){
				System.out.println("insert : "+req);
				long result = db.insertAlarm( req, 1, current_hour, current_minute,
						
								   week.get(0).intValue(), 
								   week.get(1).intValue(),
								   week.get(2).intValue(), 
								   week.get(3).intValue(),
								   week.get(4).intValue(),
								   week.get(5).intValue(),
								   week.get(6).intValue());
				startActivity(new Intent(this, AlarmLayout.class));
				finish();
			}else{
				db.updateAlarm(req, 1, current_hour, current_minute,
						
								   week.get(0).intValue(), 
								   week.get(1).intValue(),
								   week.get(2).intValue(), 
								   week.get(3).intValue(),
								   week.get(4).intValue(),
								   week.get(5).intValue(),
								   week.get(6).intValue());
				startActivity(new Intent(this, AlarmLayout.class));
				finish();
			}
			setAlarm();
			break;
		case R.id.mon :
			changeButton(0, mon);
			break;
		case R.id.tue :
			changeButton(1, tue);
			break;
		case R.id.wed :
			changeButton(2, wed);
			break;
		case R.id.thu :
			changeButton(3, thu);
			break;
		case R.id.fri :
			changeButton(4, fri);
			break;
		case R.id.sat :
			changeButton(5, sat);
			break;
		case R.id.sun :
			changeButton(6, sun);
			break;
		}
	}
	
	public void changeButton(int index, Button btn){
		if(week.get(index).intValue()==0){
			btn.setBackgroundColor(Color.BLUE);
			btn.setTextColor(Color.WHITE);
			week.set(index,1);
		}else{
			btn.setBackgroundColor(Color.WHITE);
			btn.setTextColor(Color.RED);
			week.set(index, 0);
		}
	}
	
	public void checkButton(int index, Button btn){
		if(week.get(index).intValue()==0){
			btn.setBackgroundColor(Color.WHITE);
			btn.setTextColor(Color.RED);
		}else{
			btn.setBackgroundColor(Color.BLUE);
			btn.setTextColor(Color.WHITE);
		}
	}
	
	public void checkAllButton(){
		checkButton(0, mon);
		checkButton(1, tue);
		checkButton(2, wed);
		checkButton(3, thu);
		checkButton(4, fri);
		checkButton(5, sat);
		checkButton(6, sun);
	}
	
	public void uncheckButton(Button btn){
		btn.setBackgroundColor(Color.WHITE);
		btn.setTextColor(Color.RED);
	}

	@Override
	public void onCheckedChanged(CompoundButton v, boolean ischecked) {
		
		if(ischecked==true){
			week.set(0, 1);
			week.set(1, 1);
			week.set(2, 1);
			week.set(3, 1);
			week.set(4, 1);
			week.set(5, 1);
			week.set(6, 1);
			
		}else{
			week.set(0, 0);
			week.set(1, 0);
			week.set(2, 0);
			week.set(3, 0);
			week.set(4, 0);
			week.set(5, 0);
			week.set(6, 0);
		}
		checkAllButton();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(AlarmSetDetail.this, AlarmLayout.class));
		finish();
	}
	
}
