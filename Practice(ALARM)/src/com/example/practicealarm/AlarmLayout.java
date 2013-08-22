package com.example.practicealarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmLayout extends Activity {

	int wrap_content;
	int match_parent;

	LinearLayout entire_top;
	ScrollView top_2;
	LinearLayout top_3;
	LinearLayout.LayoutParams entire_top_param;
	Button top_1;
	LinearLayout.LayoutParams top_1_param;
	LinearLayout.LayoutParams top_2_param;
	LinearLayout.LayoutParams top_3_param;
	ContactDB db = new ContactDB(this);
	Cursor cs_all, cs;
	int req;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		wrap_content = LinearLayout.LayoutParams.WRAP_CONTENT;
		match_parent = LinearLayout.LayoutParams.MATCH_PARENT;

		// entire top
		entire_top = new LinearLayout(this);
		entire_top.setOrientation(LinearLayout.VERTICAL);
		entire_top_param = new LinearLayout.LayoutParams(match_parent,
				match_parent);

		// button +
		top_1 = new Button(this);
		top_1.setText("+ 알람추가");
		top_1.setTextSize(30);
		top_1_param = new LinearLayout.LayoutParams(match_parent, wrap_content);
		top_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(AlarmLayout.this, AlarmSetDetail.class));
				finish();
			}
		});

		// scrollview
		top_2 = new ScrollView(this);
		top_2_param = new LinearLayout.LayoutParams(match_parent, wrap_content);
		top_2.setScrollBarStyle(ScrollView.SCROLLBARS_INSIDE_OVERLAY);
		top_2.setVerticalFadingEdgeEnabled(true);
		top_2.setHorizontalFadingEdgeEnabled(false);
		// top_2.setBackgroundColor(Color.WHITE);

		top_3 = new LinearLayout(this);
		top_3.setOrientation(LinearLayout.VERTICAL);
		// top_3.setBackgroundColor(Color.BLUE);
		top_3_param = new LinearLayout.LayoutParams(match_parent, match_parent);

		db.open();
		cs_all = db.selectAllAlarm();
		if (cs_all ==null)
			Log.e("AlarmLayout", "selectAllAlarm error");
		if (cs_all != null) {
			try {
				int rowidx = cs_all.getCount();
				cs_all.moveToFirst();
				Log.e("AlarmLayout", ""+cs_all.getCount());
				if (rowidx != 0) {
					for (int i = 1; i <= rowidx; i++){
						new Alarm(AlarmLayout.this, "알람번호 " + i, cs_all.getInt(0));
						cs_all.moveToNext();
					}
					//new Alarm(AlarmLayout.this, "???? " + cs_all.getInt(0), 2);
				}
			} catch (Exception e) {
				Log.e("AlarmLayout", "" + e);
			}

		}
		// new Alarm(this, "??????", 1);
		// new Alarm(this, "??????", 1);
		// new Alarm(this, "??????", 1);

		top_2.addView(top_3, top_3_param);
		// new Alarm(this, "??????", 1);
		entire_top.addView(top_1, top_1_param);
		entire_top.addView(top_2, top_2_param);
		// entire_top.addView(top_3, top_3_param);

		setContentView(entire_top, entire_top_param);
		
		
		
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		db.close();
		cs_all.close();
	}

	public class Alarm implements OnClickListener, OnLongClickListener {

		Context context;

		LinearLayout alarm_top;
		LinearLayout.LayoutParams alarm_top_param;
		LinearLayout alarm_content;
		LinearLayout.LayoutParams alarm_content_param;
		TextView alarm_content_top;
		LinearLayout.LayoutParams alarm_content_top_param;
		TextView alarm_content_middle;
		LinearLayout.LayoutParams alarm_content_middle_param;
		LinearLayout alarm_content_bottom;
		LinearLayout.LayoutParams alarm_content_bottom_param;
		LinearLayout.LayoutParams img_param;
		TextView mon, tue, wed, thu, fri, sat, sun;
		Button alarm_button;
		LinearLayout.LayoutParams alarm_button_param;

		int request;
		
		public Alarm(Context context, String test, int request) {

			this.context = context;
			this.request = request;
			
			cs = db.selectIDAlarm(""+request);
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
			// alarm content
			alarm_top = new LinearLayout(context);
			alarm_top.setOrientation(LinearLayout.HORIZONTAL);
			alarm_top.setBackgroundColor(Color.DKGRAY);
			alarm_top_param = new LinearLayout.LayoutParams(match_parent,
					wrap_content);
			alarm_top_param.setMargins(3, 3, 3, 3);
			//

			// alarm content_ left
			alarm_content = new LinearLayout(context);
			alarm_content.setOnClickListener(this);
			alarm_content.setOnLongClickListener(this);
			alarm_content.setTag("UPDATE");
			

			
			alarm_content.setOrientation(LinearLayout.VERTICAL);
			alarm_content_param = new LinearLayout.LayoutParams(wrap_content,
					wrap_content);
			alarm_content_param.weight = 1;

			alarm_content_top = new TextView(context);
			alarm_content_top.setText("테스트라는데?");
			alarm_content_top.setText(test);
			alarm_content_top.setTextSize(15);
			alarm_content_top_param = new LinearLayout.LayoutParams(
					match_parent, wrap_content);

			alarm_content_middle = new TextView(context);
			
			String set_half = "오전";
			String set_hour = null;
			String set_minute = null;
			
			
			int hour = cs.getInt(3);
			int minute = cs.getInt(4);
			System.out.println("hour :"+hour+" minute :"+minute);
			if(hour>12){
				set_half = "오후";
				set_hour = ""+(hour - 12);
			}else{
				set_hour = ""+hour;
			}
			
			if(minute<10){
				set_minute = "0"+minute;
			}else{
				set_minute = ""+minute;
			}
			System.out.println("sethour :"+set_hour+" set_minute :"+set_minute);
			
			alarm_content_middle.setText(set_half + " "+set_hour+":"+set_minute);
			alarm_content_middle.setTextSize(30);
			alarm_content_middle_param = new LinearLayout.LayoutParams(
					wrap_content, wrap_content);

			alarm_content_bottom = new LinearLayout(context);
			alarm_content_bottom.setOrientation(LinearLayout.HORIZONTAL);
			alarm_content_bottom_param = new LinearLayout.LayoutParams(
					match_parent, wrap_content);

			// ??????
			
			
			
			img_param = new LinearLayout.LayoutParams(wrap_content,
					wrap_content);
			img_param.setMargins(5,5,5,5);
			
			
			mon = new TextView(context);
			mon.setText("월");
			mon.setTextSize(20);
			mon.setTypeface(null, Typeface.BOLD);
			if(cs.getInt(5)==0)
				mon.setTextColor(Color.GRAY);
			else
				mon.setTextColor(Color.WHITE);
			
			tue = new TextView(context);
			tue.setText("화");
			tue.setTypeface(null, Typeface.BOLD);
			tue.setTextSize(20);
			if(cs.getInt(6)==0)
				tue.setTextColor(Color.GRAY);
			else
				tue.setTextColor(Color.WHITE);
			
			wed = new TextView(context);
			wed.setText("수");
			wed.setTextSize(20);
			wed.setTypeface(null, Typeface.BOLD);
			if(cs.getInt(7)==0)
				wed.setTextColor(Color.GRAY);
			else
				wed.setTextColor(Color.WHITE);
			
			thu = new TextView(context);
			thu.setText("목");
			thu.setTextSize(20);
			thu.setTypeface(null, Typeface.BOLD);
			if(cs.getInt(8)==0)
				thu.setTextColor(Color.GRAY);
			else
				thu.setTextColor(Color.WHITE);
			
			fri = new TextView(context);
			fri.setText("금");
			fri.setTextSize(20);
			fri.setTypeface(null, Typeface.BOLD);
			if(cs.getInt(9)==0)
				fri.setTextColor(Color.GRAY);
			else
				fri.setTextColor(Color.WHITE);
			
			sat = new TextView(context);
			sat.setText("토");
			sat.setTextSize(20);
			sat.setTypeface(null, Typeface.BOLD);
			if(cs.getInt(10)==0)
				sat.setTextColor(Color.GRAY);
			else
				sat.setTextColor(Color.WHITE);
			
			sun = new TextView(context);
			sun.setText("일");
			sun.setTextSize(20);
			sun.setTypeface(null, Typeface.BOLD);
			if(cs.getInt(11)==0)
				sun.setTextColor(Color.GRAY);
			else
				sun.setTextColor(Color.WHITE);
			
			
			// alarm content right
			alarm_button = new Button(context);
			alarm_button.setOnClickListener(this);
			alarm_button.setTag("ALARM");
			//alarm_button.setText("??????????");
			
			if(cs.getInt(3)==0){
				alarm_button.setBackgroundResource(R.drawable.clocksleep);
			}else{
				alarm_button.setBackgroundResource(R.drawable.clockactive);
			}
			alarm_button.setTextSize(15);
			alarm_button.setTextColor(Color.BLACK);
			alarm_button_param = new LinearLayout.LayoutParams(wrap_content,
					match_parent);
			// alarm_button_param.weight = 0;

			alarm_content_bottom.addView(mon, img_param);
			alarm_content_bottom.addView(tue, img_param);
			alarm_content_bottom.addView(wed, img_param);
			alarm_content_bottom.addView(thu, img_param);
			alarm_content_bottom.addView(fri, img_param);
			alarm_content_bottom.addView(sat, img_param);
			alarm_content_bottom.addView(sun, img_param);

			alarm_content.addView(alarm_content_top, alarm_content_top_param);
			alarm_content.addView(alarm_content_middle,
					alarm_content_middle_param);
			alarm_content.addView(alarm_content_bottom,
					alarm_content_bottom_param);

			alarm_top.addView(alarm_content, alarm_content_param);
			alarm_top.addView(alarm_button, alarm_button_param);

			top_3.addView(alarm_top, alarm_top_param);

			cs.close();
		}

		@Override
		public void onClick(View v) {
			if(v.getTag()=="UPDATE"){
				Intent intent = new Intent(AlarmLayout.this, AlarmSetDetail.class);
				intent.putExtra("isupdate", true);
				intent.putExtra("id", request);
				startActivity(intent);
				finish();
			}else if(v.getTag()=="ALARM"){
				
				cs = db.selectIDAlarm(""+request);
				cs.moveToFirst();
				
				if(cs.getInt(2)==0){
					alarm_button.setBackgroundResource(R.drawable.clockactive);
					
					db.updateAlarmActive(request, 1);
					
				}else{
					alarm_button.setBackgroundResource(R.drawable.clocksleep);
					db.updateAlarmActive(request, 0);
				}
				
				cs.close();
			}
		}

		@Override
		public boolean onLongClick(View arg0) {
			new AlertDialog.Builder(context)
			//.setIcon(R.drawable.ic_launcher) //kakao?? ???? 
			.setTitle("알람을 삭제하시겠습니까?")
			.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					db.deleteAlarm(request);
					startActivity(new Intent(AlarmLayout.this, AlarmLayout.class));
					finish();
				}
			}).create().show();
			return false;
		}

	}
}
