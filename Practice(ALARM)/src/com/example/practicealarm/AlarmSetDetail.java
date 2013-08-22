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
	private AlarmManager alarmManager;
	private GregorianCalendar gregorianCalendar;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private NotificationManager notificationManager;
	private Common common;
	
	Button btnSet, btnReset, mon, tue, wed, thu, fri, sat, sun;
	TextView txtTime;
	ArrayList<Integer> week;
	Cursor cs;
	boolean isUpdate = false;
	ContactDB db;
	int requestId;
	
	int currentHour;
	int currentMinute;
	CheckBox checkBox;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		
		Intent intent = getIntent();
		isUpdate = intent.getBooleanExtra("isupdate",false);
		requestId = intent.getIntExtra("id", -1);
		//exception is not making
		
		txtTime = (TextView)findViewById(R.id.time);
		btnReset = (Button) findViewById(R.id.reset);
		btnSet = (Button) findViewById(R.id.set);
		datePicker = (DatePicker) findViewById(R.id.date_picker);
		timePicker = (TimePicker) findViewById(R.id.time_picker);
		
		mon = (Button)findViewById(R.id.mon);
		tue = (Button)findViewById(R.id.tue);
		wed = (Button)findViewById(R.id.wed);
		thu = (Button)findViewById(R.id.thu);
		fri = (Button)findViewById(R.id.fri);
		sat = (Button)findViewById(R.id.sat);
		sun = (Button)findViewById(R.id.sun);
		checkBox = (CheckBox)findViewById(R.id.checkbox);
		common = new Common(this);		
		
		
		checkBox.setOnCheckedChangeListener(this);
		
		mon.setOnClickListener(this);
		tue.setOnClickListener(this);
		wed.setOnClickListener(this);
		thu.setOnClickListener(this);
		fri.setOnClickListener(this);
		sat.setOnClickListener(this);
		sun.setOnClickListener(this);
		
		
		btnSet.setOnClickListener(this);
		btnReset.setOnClickListener(this);
		
		
		//Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
		
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		gregorianCalendar = new GregorianCalendar();
		
		datePicker.init(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH),
				   gregorianCalendar.get(Calendar.DAY_OF_MONTH), this);
		
		timePicker.setCurrentHour(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(gregorianCalendar.get(Calendar.MINUTE));
		timePicker.setOnTimeChangedListener(this);
		
		
		week = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0));
		
		db = new ContactDB(this);
		db.open();
		
		Toast.makeText(this, "isupdate : "+isUpdate + "  \\  request : "+requestId, 2000).show();
		
		if(isUpdate==true){
			cs = db.selectIDAlarm(""+requestId);
			cs.moveToFirst();
			requestId = cs.getInt(1);
			
			timePicker.setCurrentHour(cs.getInt(3));
			timePicker.setCurrentMinute(cs.getInt(4));
			
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
				checkBox.setChecked(true);
			}
			
			checkAllButton();

		}else{
			cs = db.selectAllAlarm();
			requestId = cs.getCount()+1;
		}
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		gregorianCalendar.set(year, monthOfYear, dayOfMonth, timePicker.getCurrentHour(),
				timePicker.getCurrentMinute());
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		
		currentHour = hourOfDay;
		currentMinute = minute;
		
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

		txtTime.setText(half+" "+hour+":"+min);
		gregorianCalendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
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
			checkBox.setChecked(false);
			
			gregorianCalendar = new GregorianCalendar();
			timePicker.setCurrentHour(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(gregorianCalendar.get(Calendar.MINUTE));
			
			break;
		case R.id.set :
			if(!isUpdate){
				System.out.println("insert : "+requestId);
				db.insertAlarm( requestId, 1, currentHour, currentMinute,
						
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
				db.updateAlarm(requestId, 1, currentHour, currentMinute,
						
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
			
			//set alarm
			common.setNoticeAlarm(gregorianCalendar, requestId);
			
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
