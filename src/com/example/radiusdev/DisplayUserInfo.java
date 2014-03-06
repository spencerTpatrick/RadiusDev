package com.example.radiusdev;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayUserInfo extends Activity implements OnClickListener{

	private Button mBack, mEdit;
	String first, last, emailAdd, pw;
	TextView text_first_name, text_last_name, text_email, text_password;
	
	//Progress Dialog
	private ProgressDialog pDialog;
	private static AppPreferenceActivity _appPrefs;
	private PreferencesHelper appPrefs;
	
	//JSON parser class
	JSONparser jsonParser = new JSONparser();
	
	//Mecca Server
	private static final String USERINFO_URL = "http://1meccaproduction.com/radiusServer/userDat.php";
	
	//JSON element ids from response of php script
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_appPrefs = new AppPreferenceActivity(getApplicationContext());
		setContentView(R.layout.user_info);
		
		text_first_name = (TextView)findViewById(R.id.first_name);
		text_last_name = (TextView)findViewById(R.id.last_name);
		text_email = (TextView)findViewById(R.id.email2);
		text_password = (TextView)findViewById(R.id.password3);
		
		mBack = (Button)findViewById(R.id.backButton4);
		mEdit = (Button)findViewById(R.id.editButton);

		mBack.setOnClickListener(this);
		mEdit.setOnClickListener(this);
		
		new DisplayUser().execute();
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case R.id.home:
			Intent h = new Intent(this, Map.class);
			finish();
			startActivity(h);
			break;
		case R.id.profile:
			Intent i = new Intent(this, DisplayUserInfo.class);
			startActivity(i);
			break;
		case R.id.contacts:
			Intent j = new Intent(this, CustomizedListView.class);
			startActivity(j);
			break;
		case R.id.about:
			Intent k = new Intent(this, DisplayAbout.class);
			startActivity(k);
			break;
		case R.id.settings:
			Intent l = new Intent(this, UserSettings.class);
			startActivity(l);
			break;
			
		case R.id.signOut:
			Intent m = new Intent(this, Login.class);
			finish();
			startActivity(m);
			break;
		}
		return true;
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == (R.id.backButton4)) {
			Intent i = new Intent(this, Main.class);
			finish();
			startActivity(i);
		} else if (v.getId() == (R.id.editButton)) {
			Intent j = new Intent(this, EditUser.class);
			finish();
			startActivity(j);
		} else {
		}
	}
	
	class DisplayUser extends AsyncTask<String, String, String> {
		boolean failure = false;
		JSONArray jArray= new JSONArray();
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DisplayUserInfo.this);
			pDialog.setMessage("Getting User Info....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args){
			int success;
			
			try{
				/*List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("ID", _appPrefs.getSmsBody()));
				
				Log.d("request!", "starting");
				
				JSONObject json = jsonParser.makeHttpRequest(USERINFO_URL, "POST", params);
				Log.d("request2!", "starting");*/
				jArray = getJSON(USERINFO_URL,30000, _appPrefs.getSmsBody().toString());
				
				Log.i("Login ID: ", "ID: " +  _appPrefs.getSmsBody().toString());
				Log.i("Get User Info Attempt", jArray.toString());
				Log.i("json Length", jArray.length() + " ");
				//Log.i("first name", jArray.getString("first_name"));
				//Log.i("last name", jArray.getString("last_name"));
				//Log.i("email", jArray.getString("email"));
				//Log.i("password", jArray.getString("password"));
				//success = (Integer) jArray.get(0).get(TAG_SUCCESS);
				if(jArray.length() > 0){
					success =1;
				}else{
					success =0;
				}
				
				Log.i("Results: ", " "+success);
				if(success == 1) {
					Log.i("User Info", "Grabbed Awesome");
					//Log.i("User info retrieved.", jArray.getJSONObject(0).get(TAG_MESSAGE).toString());
					
					for (int i=0; i < jArray.length(); i++)
					{
					    //try {
					        JSONObject oneObject = jArray.getJSONObject(i);
					        // Pulling items from the array
					        first = oneObject.getString("first_name");
					        last = oneObject.getString("last_name");
					        emailAdd = oneObject.getString("email");
					        pw = oneObject.getString("password");
					    //} catch (JSONException e) {
					        // Oops
					    //}
					}
					
					/* runOnUiThread(new Runnable() {
			            @Override
			            public void run() {
			                // This code will always run on the UI thread, therefore is safe to modify UI elements.
			            	text_first_name.setText(first);
							text_last_name.setText(last);
							text_email.setText(emailAdd);
							text_password.setText(pw);
			            }
			        }); */
					
					finish();
					//return  jArray.getJSONObject(0).get(TAG_MESSAGE).toString();
				}else {
					//Log.d("Unable to get user info.", jArray.getJSONObject(0).get(TAG_MESSAGE).toString());
					//return jArray.getJSONObject(0).get(TAG_MESSAGE).toString();
					Log.i("User Info", "Lost Shit");
				}
			}catch (JSONException e){
				e.printStackTrace();
			}
			
			return null;
		}
		public JSONArray getJSON(String FUNCTION_URL, int timeout, String ID){
			
			JSONArray json = new JSONArray();
			
			try {
		        URL u = new URL(FUNCTION_URL);
		        HttpURLConnection c = (HttpURLConnection) u.openConnection();
		        c.setDoOutput(true);
		        c.setDoInput(true);
		        c.setUseCaches(false);
		        c.setRequestMethod("GET");
		        c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        c.setRequestProperty("Connection", "Keep-Alive");
		        String urlPar ="ID=" + ID;
		        Log.i("urlPar: ", urlPar);
		        c.setRequestProperty("charset", "utf-8");
		        c.setRequestProperty("Content-Length", "" + Integer.toString(urlPar.getBytes().length));
		        c.setConnectTimeout(timeout);
		        
		        DataOutputStream wr = new DataOutputStream(c.getOutputStream());
		        wr.writeBytes(urlPar);
		        wr.flush();
		        wr.close();
		        InputStream is = c.getInputStream();
		        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		        String line;
		        StringBuffer response = new StringBuffer();
		        
		        while((line = rd.readLine()) != null) {
		          response.append(line);
		          response.append('\r');
		          Log.i("", response.toString());
		        }
		        json= new JSONArray(response.toString());
		        
		    } catch (MalformedURLException ex) {
		       System.out.print("ERROR1");
		    } catch (IOException ex) {
		    	System.out.print("ERROR2 : " + ex.getMessage() );
		    } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    return json;
		}
		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if(file_url != null){
		        Toast.makeText(DisplayUserInfo.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}
}
