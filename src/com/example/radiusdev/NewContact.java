package com.example.radiusdev;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewContact extends Activity {

	EditText firstName;
	EditText lastName;
	EditText phoneNumber;
	EditText emailAddress;
	
	DBHelper dbTools = new DBHelper(this);
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_contact);
		
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		emailAddress = (EditText) findViewById(R.id.emailAddress);
		
	}
	
	public void addNewContact(View view){
		
		HashMap<String, String> queryValuesMap = new HashMap<String, String>();
		
		queryValuesMap.put("firstName", firstName.getText().toString());
		queryValuesMap.put("lastName", lastName.getText().toString());
		queryValuesMap.put("phoneNumber", phoneNumber.getText().toString());
		queryValuesMap.put("emailAddress", emailAddress.getText().toString());
		
		dbTools.insertContact(queryValuesMap);
		
		this.callMainActivity(view);
	}

	private void callMainActivity(View view) {
		// TODO Auto-generated method stub
		Intent theIntent = new Intent(getApplication(), CustomizedListView.class);
		startActivity(theIntent);
		
	}
	
}
