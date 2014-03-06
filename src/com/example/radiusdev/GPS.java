package com.example.radiusdev;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


public class GPS extends Activity implements OnClickListener{
	private ProgressDialog pDialog;
	private String url_add_GPS="http://1meccaproduction.com/radiusServer/insertGPS.php";
	private String id="TESTDATA";
	private String GPs;
	private Button mBack;
	
	private PreferencesHelper appPrefs;
	
    TextView textLat;
    TextView textLong;
    JSONparser jsonParser = new JSONparser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.gps);
        
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        
        appPrefs = new PreferencesHelper(getApplicationContext());
        
        textLat = (TextView)findViewById(R.id.textLat);
        textLong = (TextView)findViewById(R.id.textLong);
        
        mBack = (Button)findViewById(R.id.backButton3);
        
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        
        mBack.setOnClickListener(this);
        //updates GPS gps every 30 seconds. 
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, ll);
       
    }

    public void onClick(View v)
    {
    	Intent i = new Intent(this, Main.class);
    	finish();
		startActivity(i);
    }
    
        class mylocationlistener implements LocationListener{

			@Override
			public void onLocationChanged(Location location) {
				if(location != null){
					double pLong = location.getLongitude();
					double pLat = location.getLatitude();
					GPs=pLat + "," + pLong;
					textLat.setText(Double.toString(pLat));
					textLong.setText(Double.toString(pLong));
					 new addGPS().execute();
					/*List<NameValuePair> content=new ArrayList<NameValuePair>();
					content.add(new BasicNameValuePair("id",id));
					String gps= textLat.toString() + "," + textLong.toString();
					content.add(new BasicNameValuePair("gps",gps));
					JSONObject jsonDat=jsonParser.makeHttpRequest(url_add_GPS,"POST",content);*/
					
				}
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

				
				
			}
        	
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    class addGPS extends AsyncTask<String, String, String> {
    	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(GPS.this);
            //pDialog.setMessage("Adding GPS COORDS..");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
        	String sp_emailAddr = appPrefs.getSomeString();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("email", sp_emailAddr));
            params.add(new BasicNameValuePair("gps", GPs));
            //Log.d("Create Response:", GPs);
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_GPS,
                    "POST", params);
 
           /* // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            params.clear();
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            //pDialog.dismiss();
        }
 
    }
    
}