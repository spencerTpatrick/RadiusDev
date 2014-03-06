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

public class Register extends Activity implements OnClickListener{

	private EditText first, last, email, pwd, pwd2;
	private Button mRegister, mBack;
	
	//Progress Dialog
	private ProgressDialog pDialog;
	
	//JSON parser class
	JSONparser jsonParser = new JSONparser();
	
	//Mecca Server
	private static final String REGISTER_URL = "http://1meccaproduction.com/radiusServer/register.php";
	
	//JSON element ids from response of php script
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		first = (EditText)findViewById(R.id.firstname);
		last = (EditText)findViewById(R.id.lastname);
		email = (EditText)findViewById(R.id.email);
		pwd = (EditText)findViewById(R.id.password);
		pwd2 = (EditText)findViewById(R.id.password2);
		
		mRegister = (Button)findViewById(R.id.register);
		mBack = (Button)findViewById(R.id.backButton3);
		
		mRegister.setOnClickListener(this);
		mBack.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.register) {
			new CreateUser().execute();
		} else if (v.getId() == R.id.backButton3) {
			Intent i = new Intent(this, Login.class);
			finish();
			startActivity(i);
		} else {
		}
	}
	
	class CreateUser extends AsyncTask<String, String, String> {
		boolean failure = false;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Register.this);
			pDialog.setMessage("Creating User....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args){
			int success;
			String firstName = first.getText().toString();
			String lastName = last.getText().toString();
			String emailAddr = email.getText().toString();
			String password = pwd.getText().toString();
			String password2 = pwd2.getText().toString();
			
			try{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("first_name", firstName));
				params.add(new BasicNameValuePair("last_name", lastName));
				params.add(new BasicNameValuePair("email", emailAddr));
				params.add(new BasicNameValuePair("password", password));
				params.add(new BasicNameValuePair("password2", password2));
				
				Log.d("request!", "starting");
				
				JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);
				
				Log.d("Login attempt", json.toString());
				
				success = json.getInt(TAG_SUCCESS);
				if(success == 1) {
					Log.d("User Created!", json.toString());
					Intent i = new Intent(Register.this, Main.class);
					finish();
					startActivity(i);
					return json.getString(TAG_MESSAGE);
				}else {
					Log.d("User was not created!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				}
			}catch (JSONException e){
				e.printStackTrace();
			}
			
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if(file_url != null){
		        Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}
}
