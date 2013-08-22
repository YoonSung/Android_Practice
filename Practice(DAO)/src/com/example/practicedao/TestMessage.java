package com.example.practicedao;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TestMessage extends Activity implements OnClickListener, OnItemClickListener{

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
	    setContentView(R.layout.testmsg);
	    
	    //Initialize
	    btnInsert = (Button)findViewById(R.id.TestMsg_btnInsert);
	    btnInsert.setOnClickListener(this);
	    btnUpdate = (Button)findViewById(R.id.TestMsg_btnUpdate);
	    btnUpdate.setOnClickListener(this);
	    btnDelete = (Button)findViewById(R.id.TestMsg_btnDelete);
	    btnDelete.setOnClickListener(this);
	    
	    edtInsert1 = (EditText)findViewById(R.id.TestMsg_edtInsert1);
	    edtInsert2 = (EditText)findViewById(R.id.TestMsg_edtInsert2);
	    edtInsert3 = (EditText)findViewById(R.id.TestMsg_edtInsert3);
	    edtInsert4 = (EditText)findViewById(R.id.TestMsg_edtInsert4);
	    
	    edtUpdate1 = (EditText)findViewById(R.id.TestMsg_edtUpdate1);
	    edtUpdate2 = (EditText)findViewById(R.id.TestMsg_edtUpdate2);
	    edtUpdate3 = (EditText)findViewById(R.id.TestMsg_edtUpdate3);
	    edtUpdate4 = (EditText)findViewById(R.id.TestMsg_edtUpdate4);
	    edtUpdate5 = (EditText)findViewById(R.id.TestMsg_edtUpdate5);
	    
	    edtDelete = (EditText)findViewById(R.id.TestMsg_edtDelete);
	    
	    listView = (ListView)findViewById(R.id.TestMsg_list);
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
	    
	    Cursor cs = db.selectAllMsg();
	    
	    if (cs != null){
	    	boolean data = cs.moveToFirst();
	    	while (data){
	    		String tempString;
	    		tempString = "_id : "+cs.getInt(0) + "\n"
	    					 		 +"USER_ID : "+cs.getInt(1)+ "\n"
	    					 		 +"MSG     : "+cs.getString(2)+ "\n"
	    					 		 +"mHour   : "+cs.getInt(3) + "\n"
	    							 +"mMinute : "+cs.getInt(4);
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
		case R.id.TestMsg_btnInsert:

			int UserId = Integer.parseInt(edtInsert1.getText().toString());
			String content = edtInsert2.getText().toString();
			int mHour = Integer.parseInt(edtInsert3.getText().toString());
			int mMinute = Integer.parseInt(edtInsert4.getText().toString());
			
			db.insertMsg(UserId, content, mHour, mMinute);
			 		 
			String tempString = "USER_ID : "+UserId+ "\n"
			 		 +"MSG     : "+content+ "\n"
			 		 +"mHour   : "+mHour + "\n"
					 +"mMinute : "+mMinute;
			
			adapter.insert(tempString, adapterCount);
			listView.smoothScrollToPosition(adapterCount);
			break;
			
		case R.id.TestMsg_btnUpdate:
			int id = Integer.parseInt(edtUpdate1.getText().toString());
			int upUserId = Integer.parseInt(edtUpdate2.getText().toString());
			String upContent = edtUpdate3.getText().toString();
			int upHour= Integer.parseInt(edtUpdate4.getText().toString());
			int upMinute = Integer.parseInt(edtUpdate5.getText().toString());

			db.updateMsg(id, upUserId, upContent, upHour, upMinute);
			
			String upTempString = "USER_ID : "+upUserId+ "\n"
						 		 +"MSG     : "+upContent+ "\n"
						 		 +"mHour   : "+upHour + "\n"
								 +"mMinute : "+upMinute;
					
			adapter.insert(upTempString, adapterCount);
			listView.smoothScrollToPosition(adapterCount);
			break;
		
		case R.id.TestMsg_btnDelete:
			int _id = Integer.parseInt(edtDelete.getText().toString());
			if(db.deleteMsg(_id) !=0 )
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
