package com.example.radiusdev;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DisplayMapContacts {
	
	private static final String FUNCTION_URL = "http://1meccaproduction.com/radiusServer/getUserList.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	//JSON parser class
	JSONparser jsonParser = new JSONparser();
	String id, gps;
	LatLng latLng = new LatLng(0,0);
			
	/* protected String doInBackground(String... args){
		int success;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_id", id));
		params.add(new BasicNameValuePair("gps", gps));
			
		Log.d("request!", "starting");
		//getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(FUNCTION_URL, "GET", params);
		System.out.println(json);
		//check log for json response
	    Log.d("Grabbing Contacts", json.toString());
		//json success tag
		success = json.getInt(TAG_SUCCESS);
		if(success == 1) {
			//appPrefs.saveSomeString(emailAddress);
			
			//Intent i = new Intent(Login.this, Main.class);
			//finish();
			//startActivity(i);
			return json.getString(TAG_MESSAGE);
		}else{
			Log.d("Login Failed!", json.getString(TAG_MESSAGE));
			return json.getString(TAG_MESSAGE);
		} 
	} */
	
	
}
