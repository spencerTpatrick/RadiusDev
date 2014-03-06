package com.example.radiusdev;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditContact extends Activity{
	
	//Unedited variables
	EditText firstName, lastName, phoneNumber, emailAddress;
	//Edited variables
	EditText n_firstName, n_lastName, n_phoneNumber, n_emailAddress;
	
	//private Button SAVE, CANCEL, DELETE;
	
	HashMap<String, String> queryValuesMap = new HashMap<String, String>();
	
	DBHelper dbTools = new DBHelper(this);
	
	public void onCreate (Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact);
		System.out.println("here");
		
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		emailAddress = (EditText) findViewById(R.id.emailAddress);
		
		Intent theIntent = getIntent();
		
		//Bundle extras = theIntent.getExtras();
		String contactId = theIntent.getStringExtra("contactId");
		
		//String contactId = theIntent.getStringExtra("contactId");
		Log.d("ID: ", "ID: " + contactId);
		
		HashMap<String, String> contactList = dbTools.getContactInfo(contactId);
		for(String key : contactList.keySet()){
			System.out.println(key);
		}
		
		if(contactList.size() != 0){
			firstName.setText(contactList.get("firstName"));
			lastName.setText(contactList.get("lastName"));
			phoneNumber.setText(contactList.get("phoneNumber"));
			emailAddress.setText(contactList.get("emailAddress"));
						
		}
	}
	
	public void editContact (View view){
		//HashMap<String, String> queryValuesMap = new HashMap<String, String>();
		
		//dbTools.updateContact(queryValuesMap);
		
		//this.callMainActivity(view);
	}
	
	public void saveContact(View view){
		//HashMap<String, String> queryValuesMap = new HashMap<String, String>();
		
		n_firstName = (EditText) findViewById(R.id.firstName);
		n_lastName = (EditText) findViewById(R.id.lastName);
		n_phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		n_emailAddress = (EditText) findViewById(R.id.emailAddress);
		
		Intent theIntent = getIntent();
		
		String contactId = theIntent.getStringExtra("contactId");
		
		queryValuesMap.put("contactId", contactId);
		queryValuesMap.put("firstName", n_firstName.getText().toString());
		queryValuesMap.put("lastName", n_lastName.getText().toString());
		queryValuesMap.put("phoneNumber", n_phoneNumber.getText().toString());
		queryValuesMap.put("emailAddress", n_emailAddress.getText().toString());
		
		dbTools.updateContact(queryValuesMap);
		this.callMainActivity(view);
	}
	
	public void removeContact(View view){
		
		Intent theIntent = getIntent();
		
		String contactId = theIntent.getStringExtra("contactId");
		
		dbTools.deleteContact(contactId);
		
		this.callMainActivity(view);
		
	}
	
	public void callMainActivity(View view){
		Intent objIntent = new Intent(getApplication(), CustomizedListView.class);
		
		startActivity(objIntent);
	}

}
