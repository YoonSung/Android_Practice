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

public class TestSafetyZone extends Activity implements OnClickListener,
		OnItemClickListener {
	Button btnInsert, btnUpdate, btnDelete;
	EditText edtInsert1, edtInsert2, edtUpdate1, edtUpdate2,
			edtUpdate3, edtDelete;

	ListView listView;
	ArrayList<String> arrayList;
	ArrayAdapter<String> adapter;

	DAO db;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testsafetyzone);

		// Initialize
		btnInsert = (Button) findViewById(R.id.TestSafetyZone_btnInsert);
		btnInsert.setOnClickListener(this);
		btnUpdate = (Button) findViewById(R.id.TestSafetyZone_btnUpdate);
		btnUpdate.setOnClickListener(this);
		btnDelete = (Button) findViewById(R.id.TestSafetyZone_btnDelete);
		btnDelete.setOnClickListener(this);

		edtInsert1 = (EditText) findViewById(R.id.TestSafetyZone_edtInsert1);
		edtInsert2 = (EditText) findViewById(R.id.TestSafetyZone_edtInsert2);
		edtUpdate1 = (EditText) findViewById(R.id.TestSafetyZone_edtUpdate1);
		edtUpdate2 = (EditText) findViewById(R.id.TestSafetyZone_edtUpdate2);
		edtUpdate3 = (EditText) findViewById(R.id.TestSafetyZone_edtUpdate3);

		edtDelete = (EditText) findViewById(R.id.TestSafetyZone_edtDelete);

		listView = (ListView) findViewById(R.id.TestSafetyZone_list);
		arrayList = new ArrayList<String>();
		// arrayList.add("test");
		// arrayList.add("test1");
		// arrayList.add("test2");
		// arrayList.add("test3");

		adapter = new ArrayAdapter<String>(this, R.layout.testuser_list,
				arrayList);
		// Initialize End

		// Datebase Use
		db = new DAO(this);
		db.open();

		Cursor cs = db.selectAllZone();

		if (cs != null) {
			boolean data = cs.moveToFirst();
			while (data) {
				String tempString;
				tempString = "_id : " + cs.getInt(0) + "\n" + "LATI  : "
						+ cs.getFloat(1) + "\n" + "LONGI : "
						+ cs.getFloat(2);
				arrayList.add(tempString);
				data = cs.moveToNext();
			}
			cs.close();
		}

		// Datebase Use End

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
		case R.id.TestSafetyZone_btnInsert:
			Float latitude = Float.parseFloat(edtInsert1.getText().toString());
			Float longitude = Float.parseFloat(edtInsert2.getText().toString());
			db.insertZone(latitude, longitude);
			String tempString = "LATI  : " + latitude + "\n" + "LONGI : " + longitude;

			adapter.insert(tempString, adapterCount);
			listView.smoothScrollToPosition(adapterCount);
			break;

		case R.id.TestSafetyZone_btnUpdate:
			int id = Integer.parseInt(edtUpdate1.getText().toString());
			float upLati = Float.parseFloat(edtUpdate2.getText().toString());
			float upLongi = Float.parseFloat(edtUpdate3.getText().toString());

			db.updateZone(id, upLati, upLongi);

			String upTempString = "LATI  : " + upLati + "\n" + "LONGI : "
					+ upLongi;

			adapter.insert(upTempString, adapterCount);
			listView.smoothScrollToPosition(adapterCount);
			break;

		case R.id.TestSafetyZone_btnDelete:
			int _id = Integer.parseInt(edtDelete.getText().toString());
			if (db.deleteZone(_id) !=0 )
				Toast.makeText(this, "Delete Successed", 2000).show();
			listView.smoothScrollToPosition(adapterCount - 1);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView selectItem = (TextView) view;
		Toast.makeText(this, selectItem.getText(), 2000).show();
	}
}
