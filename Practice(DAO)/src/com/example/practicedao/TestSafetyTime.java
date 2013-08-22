package com.example.practicedao;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TestSafetyTime extends Activity implements OnClickListener, OnItemClickListener{

	Button btnInsert, btnUpdate, btnDelete;
	EditText edtInsert1, edtInsert2, edtInsert3, edtInsert4, edtUpdate1, edtUpdate2, edtUpdate3, edtUpdate4, edtUpdate5, edtDelete;
	
	ListView listView;
	ArrayList<String> arrayList;
	ArrayAdapter<String> adapter;
	
	DAO db;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.testsafetytime);
	    
	    //Initialize
	    btnInsert = (Button)findViewById(R.id.TestSafetyTime_btnInsert);
	    btnInsert.setOnClickListener(this);
	    btnUpdate = (Button)findViewById(R.id.TestSafetyTime_btnUpdate);
	    btnUpdate.setOnClickListener(this);
	    btnDelete = (Button)findViewById(R.id.TestSafetyTime_btnDelete);
	    btnDelete.setOnClickListener(this);
	    
	    edtInsert1 = (EditText)findViewById(R.id.TestSafetyTime_edtInsert1);
	    edtInsert2 = (EditText)findViewById(R.id.TestSafetyTime_edtInsert2);
	    edtInsert3 = (EditText)findViewById(R.id.TestSafetyTime_edtInsert3);
	    edtInsert4 = (EditText)findViewById(R.id.TestSafetyTime_edtInsert4);
	    
	    edtUpdate1 = (EditText)findViewById(R.id.TestSafetyTime_edtUpdate1);
	    edtUpdate2 = (EditText)findViewById(R.id.TestSafetyTime_edtUpdate2);
	    edtUpdate3 = (EditText)findViewById(R.id.TestSafetyTime_edtUpdate3);
	    edtUpdate4 = (EditText)findViewById(R.id.TestSafetyTime_edtUpdate4);
	    edtUpdate5 = (EditText)findViewById(R.id.TestSafetyTime_edtUpdate5);
	    
	    edtDelete = (EditText)findViewById(R.id.TestSafetyTime_edtDelete);
	    
	    listView = (ListView)findViewById(R.id.TestSafetyTime_list);
	    arrayList = new ArrayList<String>();
//	    arrayList.add("test");
//	    arrayList.add("test1");
//	    arrayList.add("test2");
//	    arrayList.add("test3");
	    
	    adapter = new ArrayAdapter<String>(this, R.layout.testuser_list, arrayList);
	    //Initialize End
	    
	    //Datebase Use
	    db = new DAO(this);
	    db.open();
	    
	    Cursor cs = db.selectAllTime();
	    
	    if (cs != null){
	    	boolean data = cs.moveToFirst();
	    	while (data){
	    		String tempString;
	    		tempString = "_id : "+cs.getInt(0) + "\n"
	    					 		 +"startH : "+cs.getInt(1)+ "\n"
	    					 		 +"startM : "+cs.getInt(2)+ "\n"
	    					 		 +"endH   : "+cs.getInt(3) + "\n"
	    							 +"endM   : "+cs.getInt(4) + "\n";
	    		arrayList.add(tempString);
	    		data = cs.moveToNext();
	    	}
	    	cs.close();
	    }

	    //Datebase Use End
	    
	    listView.setAdapter(adapter);
	    listView.setOnItemClickListener(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		db.close();
	}

	@Override
	public void onClick(View v) {
		
		int adapterCount = adapter.getCount();
		
		switch (v.getId()) {
		case R.id.TestSafetyTime_btnInsert:

			int sHour = Integer.parseInt(edtInsert1.getText().toString());
			int sMinute = Integer.parseInt(edtInsert2.getText().toString());
			int eHour = Integer.parseInt(edtInsert3.getText().toString());
			int eMinute = Integer.parseInt(edtInsert4.getText().toString());
			
			db.insertTime(sHour, sMinute, eHour, eMinute);
			String tempString = "startH : "+sHour+"\n"
							   +"startM : "+sMinute+"\n"
							   +"endH   : "+eHour+"\n"
							   +"endM   : "+eMinute;
			
			adapter.insert(tempString, adapterCount);
			listView.smoothScrollToPosition(adapterCount);
			break;
			
		case R.id.TestSafetyTime_btnUpdate:
			int id = Integer.parseInt(edtUpdate1.getText().toString());
			int upStartH = Integer.parseInt(edtUpdate2.getText().toString());
			int upStartM = Integer.parseInt(edtUpdate3.getText().toString());
			int upEndH = Integer.parseInt(edtUpdate4.getText().toString());
			int upEndM = Integer.parseInt(edtUpdate5.getText().toString());

			db.updateTime(id, upStartH, upStartM, upEndH, upEndM);
			
			String upTempString = "startH : "+upStartH+"\n"
							     +"startM : "+upStartM+"\n"
							     +"endH   : "+upEndH+"\n"
							     +"endM   : "+upEndM;
					
			adapter.insert(upTempString, adapterCount);
			listView.smoothScrollToPosition(adapterCount);
			break;
		
		case R.id.TestSafetyTime_btnDelete:
			int _id = Integer.parseInt(edtDelete.getText().toString());
			if(db.deleteTime(_id) !=0 )
				Toast.makeText(this, "Delete Successed", 2000).show();
			listView.smoothScrollToPosition(adapterCount-1);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TextView selectItem = (TextView)view;
		Toast.makeText(this, selectItem.getText(), 2000).show();
	}
}
