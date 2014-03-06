package com.example.radiusdev;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{
	
	private EditText email, pwd;
	private Button mSubmit, mRegister;
	
	private PreferencesHelper appPrefs;
	private AppPreferenceActivity _appPrefs;
	//Progress Dialog
	private ProgressDialog pDialog;
	
	//JSON parser class
	JSONparser jsonParser = new JSONparser();
	
	//localhost
	//private static final String LOGIN_URL = "http://198.21.230.127:1234/webservice/login.php";
	//testing on emulator
	//private static final String LOGIN_URL = "http://10.0.2.2/webservice/login.php";
	
	//Mecca Server
	private static final String LOGIN_URL = "http://1meccaproduction.com/radiusServer/login.php";
	
	//JSON element ids from response of php script
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_appPrefs= new AppPreferenceActivity(getApplicationContext());
		appPrefs = new PreferencesHelper(getApplicationContext());
		setContentView(R.layout.login);
		
		//setup input fields
		email = (EditText)findViewById(R.id.email);
		pwd = (EditText)findViewById(R.id.password);
		//setup buttons
		mSubmit = (Button)findViewById(R.id.login);
		mRegister = (Button)findViewById(R.id.register);
		//register listeners
		mSubmit.setOnClickListener(this);
		mRegister.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login) {
			new AttemptLogin().execute();
		} else if (v.getId() == R.id.register) {
			Intent i = new Intent(this, Register.class);
			finish();
			startActivity(i);
		} else {
		}
	}
	
	//A seperate thread than the thread that runs the GUI
	//Any type of networking should be done with asynctask
	class AttemptLogin extends AsyncTask<String, String, String>{
		/* three methods get called, first preExecute, then do in 
		   background, and once complete, the onPost execute method 
		   will be called */
		boolean failure = false;
		
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Attempting login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args){
			int success;
			String userID;
			String emailAddress = email.getText().toString();
			String password = pwd.getText().toString();
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("email", emailAddress));
				params.add(new BasicNameValuePair("password", password));
				
				Log.d("request!", "starting");
				//getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
				System.out.println(json);
				//check log for json response
			    Log.d("Login attempt", json.toString());
				//json success tag
				success = json.getInt(TAG_SUCCESS);
				userID=json.getString("ID");
				if(success == 1) {
					Log.d("Login Successful!", json.toString());
					appPrefs.saveSomeString(emailAddress);
					appPrefs.saveSomeString(userID);
					_appPrefs.saveUserID(userID);
					Intent i = new Intent(Login.this, Main.class);
					finish();
					startActivity(i);
					return json.getString(TAG_MESSAGE);
				}else{
					Log.d("Login Failed!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				} 
			} catch(JSONException e){
				e.printStackTrace();
			}  
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			//dismiss the dialog once product deleted
			pDialog.dismiss();
			if(file_url != null){
				Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}
	
}