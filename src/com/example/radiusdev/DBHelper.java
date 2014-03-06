package com.example.radiusdev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
	
	public DBHelper(Context applicationContext){
		super(applicationContext, "contactbook.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		String query = "CREATE TABLE contacts (contactId INTEGER PRIMARY KEY, firstName TEXT,"+
					"lastName TEXT, phoneNumber TEXT, emailAddress TEXT)";
					
		db.execSQL(query);
			
	//	}
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String query = "DROP TABLE IF EXISTS contacts";
		db.execSQL(query);
		onCreate(db);
		
	}
	
	//Checks if database exists or not
	/* private boolean checkDataBase() {
	    SQLiteDatabase checkDB = null;
	    File f=new File("/data/data/your.app.package/databases/contactbook.db");
	    try {
	        checkDB = SQLiteDatabase.openDatabase(f.getPath(), null,
	                SQLiteDatabase.OPEN_READONLY);
	        checkDB.close();
	    } catch (SQLiteException e) {
	        // database doesn't exist yet.
	    }
	    return checkDB != null ? true : false;
	} */
	
	public void addUser(HashMap<String, String>queryValues){
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("firstName", queryValues.get("firstName"));
		values.put("lastName", queryValues.get("lastName"));
		values.put("phoneNumber", queryValues.get("phoneNumber"));
		values.put("emailAddress", queryValues.get("emailAddress"));
		db.insert("contacts", null, values);
		
		db.close();
		
	}
	public void insertContact(HashMap<String, String>queryValues){
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("firstName", queryValues.get("firstName"));
		values.put("lastName", queryValues.get("lastName"));
		values.put("phoneNumber", queryValues.get("phoneNumber"));
		values.put("emailAddress", queryValues.get("emailAddress"));
		db.insert("contacts", null, values);
		
		db.close();
		
	}
	
	public void userData(HashMap<String, String>queryValues){
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("firstName", queryValues.get("firstName"));
		values.put("lastName", queryValues.get("lastName"));
		values.put("phoneNumber", queryValues.get("phoneNumber"));
		values.put("emailAddress", queryValues.get("emailAddress"));
		db.insert("userData", null, values);
		
		db.close();
		
	}
	
	
	public int updateContact(HashMap<String, String>queryValues){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("firstName", queryValues.get("firstName"));
		values.put("lastName", queryValues.get("lastName"));
		values.put("phoneNumber", queryValues.get("phoneNumber"));
		values.put("emailAddress", queryValues.get("emailAddress"));
		
		return db.update("contacts", values, 
				"contactId"+"=?", new String[] {queryValues.get("contactId")});
	}
	
	
	public void deleteContact(String id){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		String deleteQuery = "DELETE FROM contacts WHERE contactId = '"+id+"'";
		
		db.execSQL(deleteQuery);
	}
	
	
	public ArrayList<HashMap<String,String>> getAllContacts(){
		
		ArrayList<HashMap<String,String>> contactArrayList = new ArrayList<HashMap<String,String>>();
		
		String selectQuery = "SELECT * FROM contacts ORDER BY lastName";
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			
			do{
				HashMap<String, String> contactMap = new HashMap<String, String>();
				
				contactMap.put("contactId", cursor.getString(0));
				contactMap.put("firstName", cursor.getString(1));
				contactMap.put("lastName", cursor.getString(2));
				contactMap.put("phoneNumber", cursor.getString(3));
				contactMap.put("emailAddress", cursor.getString(4));
				
				contactArrayList.add(contactMap);
			}while(cursor.moveToNext());
		}
		
		return contactArrayList;
	}
	
	public HashMap<String, String> getContactInfo(String id){
		
		HashMap<String, String> contactMap = new HashMap<String, String>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM contacts WHERE contactId = '"+id+"'";
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			
			do{				
				contactMap.put("contactId", cursor.getString(0));
				contactMap.put("firstName", cursor.getString(1));
				contactMap.put("lastName", cursor.getString(2));
				contactMap.put("phoneNumber", cursor.getString(3));
				contactMap.put("emailAddress", cursor.getString(4));
								
			}while(cursor.moveToNext());
		}
		
		return contactMap;
		
	}
	
	

}
