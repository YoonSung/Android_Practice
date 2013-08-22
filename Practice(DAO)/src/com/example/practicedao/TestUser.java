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

public class TestUser extends Activity implements OnClickListener, OnItemClickListener {

	Button btnInsert, btnUpdate, btnDelete;
	EditText edtInsert1, edtInsert2, edtInsert3, edtUpdate1, edtUpdate2, edtUpdate3, edtUpdate4, edtDelete;
	
	ListView listView;
	ArrayList<String> arrayList;
	ArrayAdapter<String> adapter;
	
	DAO db;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.testuser);
	    
	    //Initialize
	    btnInsert = (Button)findViewById(R.id.TestUser_btnInsert);
	    btnInsert.setOnClickListener(this);
	    btnUpdate = (Button)findViewById(R.id.TestUser_btnUpdate);
	    btnUpdate.setOnClickListener(this);
	    btnDelete = (Button)findViewById(R.id.TestUser_btnDelete);
	    btnDelete.setOnClickListener(this);
	    
	    edtInsert1 = (EditText)findViewById(R.id.TestUser_edtInsert1);
	    edtInsert2 = (EditText)findViewById(R.id.TestUser_edtInsert2);
	    edtInsert3 = (EditText)findViewById(R.id.TestUser_edtInsert3);
	    edtUpdate1 = (EditText)findViewById(R.id.TestUser_edtUpdate1);
	    edtUpdate2 = (EditText)findViewById(R.id.TestUser_edtUpdate2);
	    edtUpdate3 = (EditText)findViewById(R.id.TestUser_edtUpdate3);
	    edtUpdate4 = (EditText)findViewById(R.id.TestUser_edtUpdate4);
	    
	    edtDelete = (EditText)findViewById(R.id.TestUser_edtDelete);
	    
	    listView = (ListView)findViewById(R.id.TestUser_list);
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
	    
	    Cursor cs = db.selectAllUser();
	    
	    if (cs != null){
	    	boolean data = cs.moveToFirst();
	    	while (data){
	    		String tempString;
	    		tempString = "_id : "+cs.getInt(0) + "\n"
	    					 		 +"name : "+cs.getString(1)+ "\n"
	    					 		 +"number : "+cs.getString(2)+ "\n"
	    					 		 +"allow : "+cs.getInt(3) + "\n";
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
		case R.id.TestUser_btnInsert:
			boolean allow = false;
			
			//db
			if("1".equals(edtInsert3.getText().toString()));
				allow = true;
			String name = edtInsert1.getText().toString();
			String number =edtInsert2.getText().toString();
			db.insertUser(name, number, allow);
			String tempString = "name : "+name +"\n"+"number : "+number+"\n"+"allow : "+allow;
			
			adapter.insert(tempString, adapterCount);
			listView.smoothScrollToPosition(adapterCount);
			break;
			
		case R.id.TestUser_btnUpdate:
			boolean upAllow = false;
			
			//db
			if(edtUpdate4.getText().toString() == "1")
				upAllow = true;
			int id = Integer.parseInt(edtUpdate1.getText().toString());
			String upName = edtUpdate2.getText().toString();
			String upNumber =edtUpdate3.getText().toString();

			db.updateUser(id, upName, upNumber, upAllow);
			
			String upTempString = "name : "+upName +"\n"+"number : "+upNumber+"\n"+"allow : "+upAllow;
			
			adapter.insert(upTempString, adapterCount);
			listView.smoothScrollToPosition(adapterCount);
			break;
		
		case R.id.TestUser_btnDelete:
			int _id = Integer.parseInt(edtDelete.getText().toString());
			if(db.deleteUser(_id) !=0 )
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
