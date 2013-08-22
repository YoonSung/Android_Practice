package com.example.practicealarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CallLog;
import android.util.Log;
import android.widget.Toast;

	
public class ContactDB {

	public static final String DATABASE_NAME = "pdb.db";
	private static final int DATABASE_VERSION = 1;

	private static final String TAG = "ContactDB";
	
	
	
	
	//CONTACTS TABLE ==========================================================================================
	private static final String CONTACT_TABLE = "contacts";
	public static final String CONTACT_ID = "_id";	
	public static final String CONTACT_ORIGIN_ID = "OID";
	public static final String CONTACT_NAME = "name";
	public static final String CONTACT_PHONE = "phone";
	public static final String CONTACT_CALLNUM = "num";
	public static final String CONTACT_LAST = "date";
	public static final	String CONTACT_SCORE = "score";
	
	
	private static final String DATABASE_CREATE = "create table if not exists "
										+CONTACT_TABLE
										+ "("
										+CONTACT_ID 		+" integer primary key autoincrement,"
										
										+CONTACT_ORIGIN_ID 	+" text not null, "
										+CONTACT_NAME 	+" text not null, "
										+CONTACT_PHONE 	+" text not null,"
										+CONTACT_CALLNUM + " integer,"
										+CONTACT_LAST +" text,"
										+CONTACT_SCORE + " integer);";
	
	
	
	//if insert failed, return -1	
	public long insertContact(String id, String name, String num)
	{
		ContentValues cv = new ContentValues();
		cv.put(CONTACT_ORIGIN_ID, id);
		cv.put(CONTACT_NAME, name);
		cv.put(CONTACT_PHONE, num);
		return mDB.insert(CONTACT_TABLE, null, cv);
	}

	
	public boolean deleteContact(long rowID){
		return (mDB.delete(CONTACT_TABLE, CONTACT_ID+"="+rowID, null) ==1);
	}
	
	
	/*
	"
			+CONTACT_TABLE
			+CONTACT_ID
			+CONTACT_ORIGIN_ID
			+CONTACT_NAME
			+CONTACT_PHONE
			+CONTACT_CALLNUM
			+CONTACT_LAST
			+CONTACT_SCORE
	*/
	
	//return a number of row effected
	
	/*
	public boolean updateContactById(int _ID, String OID, String NAME, String PHONE, int CALLNUM, String LAST_DATE,long SCORE ) {
		ContentValues cv = new ContentValues();
		
		
		if (OID != null)
			cv.put( CONTACT_ORIGIN_ID, OID);
		
		if (NAME != null)
			cv.put( CONTACT_NAME, NAME);
		
		if (PHONE != null)
			cv.put( CONTACT_PHONE, PHONE);
		
		if (CALLNUM != -1)
			cv.put( CONTACT_CALLNUM, CALLNUM);
		
		if (LAST_DATE != null)
			cv.put( CONTACT_LAST, LAST_DATE);
		
		if (SCORE != -1L)
			cv.put( CONTACT_SCORE, SCORE);
		
		
		return (mDB.update(CONTACT_TABLE, cv, CONTACT_ID+"="+_ID, null) ==1);
	}
	*/
	
	/*
	public boolean updateContactByPhone(int _ID, String OID, String NAME, String PHONE, int CALLNUM, String LAST_DATE, long SCORE ) {
		ContentValues cv = new ContentValues();
		
		if (_ID != -1)
			cv.put( CONTACT_ORIGIN_ID, _ID);
		
		if (OID != null)
			cv.put( CONTACT_ORIGIN_ID, OID);
		
		if (NAME != null)
			cv.put( CONTACT_NAME, NAME);
		
		
		if (CALLNUM != -1)
			cv.put( CONTACT_CALLNUM, CALLNUM);
		
		if (LAST_DATE != null)
			cv.put( CONTACT_LAST, LAST_DATE);
		
		if (SCORE != -1L)
			cv.put( CONTACT_SCORE, SCORE);
		
		
		return (mDB.update(CONTACT_TABLE, cv, CONTACT_PHONE+"="+PHONE, null) ==1);
	}
	*/
	/*
	"
			+CONTACT_TABLE
			+CONTACT_ID
			+CONTACT_ORIGIN_ID
			+CONTACT_NAME
			+CONTACT_PHONE
			+CONTACT_CALLNUM
			+CONTACT_LAST
			+CONTACT_SCORE
	*/
	
	
	public boolean updateContactByOid(int _ID, String OID, String NAME, String PHONE, int CALLNUM, String LAST_DATE,long  SCORE ) {
		ContentValues cv = new ContentValues();
		
		if (_ID != -1)
			cv.put( CONTACT_ORIGIN_ID, _ID);
		
		
		if (NAME != null)
			cv.put( CONTACT_NAME, NAME);
		
		if (PHONE != null)
			cv.put( CONTACT_PHONE, PHONE);
		
		if (CALLNUM != -1)
			cv.put( CONTACT_CALLNUM, CALLNUM);
		
		if (LAST_DATE != null)
			cv.put( CONTACT_LAST, LAST_DATE);
	
		if (SCORE != -1L)
			cv.put( CONTACT_SCORE, SCORE);
		
		return (mDB.update(CONTACT_TABLE, cv, CONTACT_ORIGIN_ID+"="+OID, null) ==1);
	}
	
	
	/*
	"
			+CONTACT_TABLE
			+CONTACT_ID
			+CONTACT_ORIGIN_ID
			+CONTACT_NAME
			+CONTACT_PHONE
			+CONTACT_CALLNUM
			+CONTACT_LAST
			+CONTACT_SCORE
	*/
	
	
	//NOT USE NOW
	public Cursor selectOidContact(String OID){
		Cursor cs = mDB.query(true, CONTACT_TABLE, new String[]{CONTACT_ID, CONTACT_ORIGIN_ID, CONTACT_NAME, CONTACT_PHONE, CONTACT_CALLNUM, CONTACT_LAST, CONTACT_SCORE}, CONTACT_ORIGIN_ID+" = ?", new String[]{OID}, null, null, null, null);
		
		if(cs!=null)
			cs.moveToFirst();
		return cs;
	}
	

	//NOT USE NOW
	public Cursor selectRoofContact(String phone_num){
		Cursor cs = mDB.query(true, CONTACT_TABLE, new String[]{CONTACT_ID, CONTACT_ORIGIN_ID, CONTACT_CALLNUM, CONTACT_LAST, CONTACT_SCORE}, CONTACT_PHONE+" = ?",new String[]{phone_num}, null, null, null, null);
		if(cs!=null){
			cs.moveToFirst();
		}
		
		return cs;
	}
	
	public Cursor selectAllContact(){
		return mDB.query(CONTACT_TABLE, new String[]{CONTACT_ORIGIN_ID,CONTACT_NAME,CONTACT_PHONE, CONTACT_CALLNUM, CONTACT_LAST, CONTACT_SCORE}, null, null, null, null, null);
	}
	/*
	public static final String CONTACT_ID = "_id";	
	public static final String CONTACT_ORIGIN_ID = "OID";
	public static final String CONTACT_NAME = "name";
	public static final String CONTACT_PHONE = "phone";
	public static final String CONTACT_CALLNUM = "num";
	public static final String CONTACT_LAST = "date";
	public static final	String CONTACT_SCORE = "score";*/
	
	public Cursor selectScoreContact(){
		return mDB.query(CONTACT_TABLE, new String[]{CONTACT_ORIGIN_ID, CONTACT_LAST}, null, null, null, null, "score DESC");
					 //(CONTACT_TABLE, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal)
	}
	
	public Cursor selectLongContact(){
		return mDB.query(CONTACT_TABLE, new String[]{CONTACT_ORIGIN_ID}, null, null, null, null, "date ASC");
	}
	
	//ONE MONTH LOG TABLE ==========================================================================================
	private static final String LOG_ONE_TABLE = "log";
	public static final String LOG_ONE_ID = "_id";
	public static final String LOG_ONE_PHONE = "phone";
	public static final String LOG_ONE_LAST = "date";
	
	private static final String LOG_ONE_CREATE = "create table if not exists "
										+LOG_ONE_TABLE
										+ "("
										+LOG_ONE_ID 		+" integer primary key autoincrement,"
										+LOG_ONE_PHONE 	+" text not null, "
										+LOG_ONE_LAST 	+" text not null);";
	
	
	//if insert failed, return -1	
	public long insertOnelog (String number, String last)
	{
		ContentValues cv = new ContentValues();
		cv.put(LOG_ONE_PHONE, number);
		cv.put(LOG_ONE_LAST, last);
		return mDB.insert(LOG_ONE_TABLE, null, cv);
	}

	
	public boolean deleteOnelog(long rowID){
		return (mDB.delete(LOG_ONE_TABLE, LOG_ONE_ID+"="+rowID, null) ==1);
	}
	
	public Cursor selectAllOnelog(){
		return mDB.query(LOG_ONE_TABLE, new String[]{LOG_ONE_ID, LOG_ONE_PHONE, LOG_ONE_LAST}, null, null, null, null, "date ASC");
	}
	
	public Cursor selectOneLog(String phone_num){
		return mDB.query(LOG_ONE_TABLE, new String[]{LOG_ONE_ID, LOG_ONE_PHONE, LOG_ONE_LAST}, "phone=?", new String[]{phone_num}, null, null, null); 
	}
	
	
	//SEVEN MONTH LOG TABLE ==========================================================================================
	private static final String LOG_SEVEN_TABLE = "log";
	public static final String LOG_SEVEN_ID = "_id";
	public static final String LOG_SEVEN_PHONE = "phone";
	public static final String LOG_SEVEN_LAST = "date";
	
	private static final String LOG_SEVEN_CREATE = "create table if not exists "
										+LOG_SEVEN_TABLE
										+ "("
										+LOG_SEVEN_ID 		+" integer primary key autoincrement,"
										+LOG_SEVEN_PHONE 	+" text not null, "
										+LOG_SEVEN_LAST 	+" text not null);";

	//if insert failed, return -1	
	public long insertSevenlog (String phone_num, String last)
	{
		ContentValues cv = new ContentValues();
		cv.put(LOG_SEVEN_PHONE, phone_num);
		cv.put(LOG_SEVEN_LAST, last);
		return mDB.insert(LOG_SEVEN_TABLE, null, cv);
	}

	
	public boolean deleteSevenlog(long rowID){
		return (mDB.delete(LOG_SEVEN_TABLE, LOG_ONE_ID+"="+rowID, null) ==1);
	}
	
	public Cursor selectAllSevenlog(){
		return mDB.query(LOG_SEVEN_TABLE, new String[]{LOG_SEVEN_ID, LOG_SEVEN_PHONE, LOG_SEVEN_LAST}, null, null, null, null, "date ASC");	
	}
	
	//NOTICE TABLE ==========================================================================================
	private static final String NOTICE_TABLE = "NOTICE";
	public static final String NOTICE_ID = "_id";
	public static final String NOTICE_ORIGIN_ID = "OID";
	
	
	private static final String NOTICE_CREATE = "create table if not exists "
										+NOTICE_TABLE
										+ "("
										+NOTICE_ID 		+" integer primary key autoincrement,"
										+NOTICE_ORIGIN_ID 	+" text not null);";
	
	
	public long insertNoticelog (String OID)
	{
		ContentValues cv = new ContentValues();
		cv.put(NOTICE_ORIGIN_ID, OID);
		return mDB.insert(NOTICE_TABLE, null, cv);
	}

	
	public boolean deleteNoticelog(long rowID){
		return (mDB.delete(NOTICE_TABLE, NOTICE_ID+"="+rowID, null) ==1);
	}
	
	public Cursor selectAllNoticelog(){
		return mDB.query(NOTICE_TABLE, new String[]{NOTICE_ORIGIN_ID}, null, null, null, null, null);
	}
	

	
	//EXCLUDE TABLE ==========================================================================================
	
	
	
	
	//GROUP TABLE ==========================================================================================
	
	
	//ALARM TABLE ==========================================================================================
	private static final String ALARM_TABLE = "ALARM";
	public static final String ALARM_ID = "_id";
	public static final String ALARM_REQUEST = "req";
	public static final String ALARM_ACTIVATE = "isactivate";
	public static final String ALARM_HOUR = "hour";
	public static final String ALARM_MINUTE = "minute";
	public static final String ALARM_MON = "mon";
	public static final String ALARM_TUE = "tue";
	public static final String ALARM_WED = "wed";
	public static final String ALARM_THU = "thu";
	public static final String ALARM_FRI = "fri";
	public static final String ALARM_SAT = "sat";
	public static final String ALARM_SUN = "sun";
	
	
	private static final String ALARM_CREATE = "create table if not exists "
										+ALARM_TABLE
										+ "("
										+ALARM_ID 		+" integer primary key autoincrement,"
										+ALARM_REQUEST 	+" integer not null,"
										+ALARM_ACTIVATE +" integer not null,"
										+ALARM_HOUR		+" integer not null,"
										+ALARM_MINUTE   +" integer not null,"
										+ALARM_MON		+" integer not null,"
										+ALARM_TUE		+" integer not null,"
										+ALARM_WED		+" integer not null,"
										+ALARM_THU		+" integer not null,"
										+ALARM_FRI		+" integer not null,"
										+ALARM_SAT		+" integer not null,"
										+ALARM_SUN		+" integer not null);";
	
	public long insertAlarm (int request, int activate, int hour, int minute, int mon, int tue, int wed, int thu, int fri, int sat, int sun)
	{
		ContentValues cv = new ContentValues();
		cv.put(ALARM_REQUEST, request);
		cv.put(ALARM_ACTIVATE, activate);
		cv.put(ALARM_HOUR, hour);
		cv.put(ALARM_MINUTE, minute);
		cv.put(ALARM_MON, mon);
		cv.put(ALARM_TUE, tue);
		cv.put(ALARM_WED, wed);
		cv.put(ALARM_THU, thu);
		cv.put(ALARM_FRI, fri);
		cv.put(ALARM_SAT, sat);
		cv.put(ALARM_SUN, sun);
		
		return mDB.insert(ALARM_TABLE, null, cv);
	}

	
	public boolean deleteAlarm(int rowID){
		return (mDB.delete(ALARM_TABLE, ALARM_REQUEST+"="+rowID, null) ==1);
	}
	
	public Cursor selectAllAlarm(){
		return mDB.query(ALARM_TABLE, new String[]
				{ALARM_ID,
				ALARM_REQUEST, 
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
	
	public Cursor selectIDAlarm(String rowidx){
		return mDB.query(ALARM_TABLE, new String[]
				{ALARM_ID, 
				ALARM_REQUEST, 
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
				}, ALARM_REQUEST+" = ?", new String[]{rowidx}, null, null, null);
	}
	
	public boolean updateAlarm( int _ID,
								int activate,
								int hour,
								int minute, 
								int mon, 
								int tue,
								int wed,
								int thu,
								int fri,
								int sat,
								int sun)
								{
		ContentValues cv = new ContentValues();
		cv.put(ALARM_ACTIVATE, activate);
		cv.put(ALARM_HOUR, hour);
		cv.put(ALARM_MINUTE, minute);
		cv.put(ALARM_MON, mon);
		cv.put(ALARM_TUE, tue);
		cv.put(ALARM_WED, wed);
		cv.put(ALARM_THU, thu);
		cv.put(ALARM_FRI, fri);
		cv.put(ALARM_SAT, sat);
		cv.put(ALARM_SUN, sun);
		
		return (mDB.update(ALARM_TABLE, cv, ALARM_ID+"="+_ID, null) ==1);
	}
	
	
	public boolean updateAlarmActive( int _ID, int activate) {
		ContentValues cv = new ContentValues();
		cv.put(ALARM_ACTIVATE, activate);
		
		return (mDB.update(ALARM_TABLE, cv, ALARM_ID+"="+_ID, null) ==1);
	}
	
	//==========================================================================================
	
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	private final Context context;
	

	
	public ContactDB (Context context){
		this.context = context;
	}
	
	/*
	public ContactDB delete() throws SQLException{
		mDBHelper = new DBHelper(context);
	}
	*/
	public ContactDB open() throws SQLException{
		mDBHelper = new DBHelper(context);
		mDB = mDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		mDBHelper.close();
	}
	
	

	
	/////////////////////////////////////////////////////////inner class
	private class DBHelper extends SQLiteOpenHelper {
		
		
		
		public DBHelper (Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
			db.execSQL(LOG_ONE_CREATE);
			db.execSQL(LOG_SEVEN_CREATE);
			db.execSQL(NOTICE_CREATE);
			db.execSQL(ALARM_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading db from version" + oldVersion + " to"+ newVersion + ", which will destroy all old data");
			
			db.execSQL("drop table if exists "+CONTACT_TABLE);
			db.execSQL("drop table if exists "+LOG_ONE_TABLE);
			db.execSQL("drop table if exists "+LOG_SEVEN_TABLE);
			db.execSQL("drop table if exists "+NOTICE_TABLE);
			db.execSQL("drop table if exists "+ALARM_TABLE);
			
			onCreate(db);
		}
		
		

	}
	/////////////////////////////////////////////////////////inner class	
}
