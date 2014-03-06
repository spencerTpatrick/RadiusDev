package com.example.radiusdev;

/*  http://stackoverflow.com/questions/14123243/google-maps-api-v2-custom-infowindow-like-in-original-android-google-maps/15040761#15040761 */

//import com.example.radiusdev.MapWrapperLayout;
//import com.example.radiusdev.OnInfoWindowElemTouchListener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends FragmentActivity implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener{
	
	String id, gps;
	
	private SupportMapFragment mapFragment;
	private static GoogleMap map = null;
	private LocationClient mLocationClient;
	//private FollowMeLocationSource followMeLocationSource;
	
	/* private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private Button infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener; */
	
	LatLng latLng;
	String s_latLng;
	
	// Request code to send to Google Play Services
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	DBHelper dbHelper;
	Contact contacts;
	MediaPlayer mpSplash;
	
	private static final String FUNCTION_URL = "http://1meccaproduction.com/radiusServer/getUserList.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	//JSON parser class
	JSONparser jsonParser = new JSONparser();
	
	//Progress Dialog
	private ProgressDialog pDialog;
	private PreferencesHelper appPrefs;
	private AppPreferenceActivity _appPrefs;
	// DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {

	    // Global field to contain the error dialog
	    private Dialog mDialog;

	    // Default constructor. Sets the dialog field to null
	    public ErrorDialogFragment() {
	        super();
	        mDialog = null;
	    }

	    // Set the dialog to display
	    public void setDialog(Dialog dialog) {
	        mDialog = dialog;
	    }

	    // Return a Dialog to the DialogFragment.
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        return mDialog;
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.map);
		
		mLocationClient = new LocationClient(this,this,this);
		
		// creates our custom LocationSource and initializes some of its members
        //followMeLocationSource = new FollowMeLocationSource();
		
		mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        //final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
		
		if(mapFragment == null){
			Log.w("", "mapFragment is null");                  
		}
		map = mapFragment.getMap();
		
		map.setMyLocationEnabled(true);
		
		//mapWrapperLayout.init(map, getPixelsFromDp(this, 39 + 20)); 
		
		// We want to reuse the info window for all the markers, 
        // so let's create only one class member instance
        /* this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.info_window, null);
        this.infoTitle = (TextView)infoWindow.findViewById(R.id.title);
        this.infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);
        this.infoButton = (Button)infoWindow.findViewById(R.id.button); */

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up 
        /* this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.common_signin_btn_icon_light),
                getResources().getDrawable(R.drawable.common_signin_btn_icon_pressed_light)) 
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(Main.this, marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
            }
        }; 
        this.infoButton.setOnTouchListener(infoButtonListener);


        map.setInfoWindowAdapter(new InfoWindowAdapter() {
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                infoSnippet.setText(marker.getSnippet());
                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        }); */
	}
	
	/* public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    } */
	
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case R.id.home:
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
	
	// Called when the Activity is visible
	@Override
	protected void onStart() {
	    super.onStart();
	    // Connect the client.
	    if(isGooglePlayServicesAvailable()){
	        mLocationClient.connect();
	    }
	}
	
	@Override
    public void onPause() {
        /* Disable the my-location layer (this causes our LocationSource to be automatically deactivated.) */
        map.setMyLocationEnabled(false);

        super.onPause();
    }
	
	@Override
    public void onResume() {
        super.onResume();

        /* We query for the best Location Provider everytime this fragment is displayed
         * just in case a better provider might have become available since we last displayed it */
        //followMeLocationSource.getBestAvailableProvider();

        // Get a reference to the map/GoogleMap object
        //setUpMapIfNeeded();

        /* Enable the my-location layer (this causes our LocationSource to be automatically activated.)
         * While enabled, the my-location layer continuously draws an indication of a user's
         * current location and bearing, and displays UI controls that allow a user to interact
         * with their location (for example, to enable or disable camera tracking of their location and bearing).*/
        map.setMyLocationEnabled(true);
    }
	
	// Called when the Activity is no longer visible
	@Override
	protected void onStop() {
	    // Disconnecting the client invalidates it.
	    mLocationClient.disconnect();
	    super.onStop();
	}
	
	// Handle results returned to the FramentAcitivity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Decide what to do based on the original request code
	    switch (requestCode) {

	        case CONNECTION_FAILURE_RESOLUTION_REQUEST:
	            /*
	             * If the result code is Activity.RESULT_OK, try
	             * to connect again
	             */
	            switch (resultCode) {
	                case Activity.RESULT_OK:
	                    mLocationClient.connect();
	                    break;
	            }

	    }
	}
	
	private boolean isGooglePlayServicesAvailable() {
	    // Check that Google Play services is available
	    int resultCode =  GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    // If Google Play services is available
	    if (ConnectionResult.SUCCESS == resultCode) {
	        // In debug mode, log the status
	        Log.d("Location Updates", "Google Play services is available.");
	        return true;
	    } else {
	        // Get the error dialog from Google Play services
	        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog( resultCode,this,CONNECTION_FAILURE_RESOLUTION_REQUEST);
	        // If Google Play services can provide an error dialog
	        if (errorDialog != null) {
	            // Create a new DialogFragment for the error dialog
	            ErrorDialogFragment errorFragment = new ErrorDialogFragment();
	            errorFragment.setDialog(errorDialog);
	            //errorFragment.show(getSupportFragmentManager(), "Location Updates");
	        }

	        return false;
	    }
	}
	
	// Called by Location Services when the request to connect the client
	// finishes successfully.  At this point, you can request the current
	// location or start periodic updates
	@Override
	public void onConnected(Bundle dataBundle) {
	    // Display the connection status
	    Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	    Location location = mLocationClient.getLastLocation();
	    latLng = new LatLng(location.getLatitude(), location.getLongitude());
	    //Get rid of parenthesis on gps
	    s_latLng = latLng.toString();
	    s_latLng = s_latLng.substring(10, s_latLng.length()-1);
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
	    new GrabContacts().execute();
	    map.animateCamera(cameraUpdate);
	}
	
	// Called by Location Services if the connection to the location
	// client drops because of an error
	@Override
	public void onDisconnected() {
	    // Display the connection status
	    Toast.makeText(this, "Disconnected.",
	            Toast.LENGTH_SHORT).show();
	}
	
	// Called by location services if the attempt to Location Services
	// fails
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	    /*
	     * Google Play services can resolve some errors it detects.
	     * If the error has a resolution, try sending an Intent to
	     * start a Google Play services activity that can resolve
	     * error.
	     */
	    if (connectionResult.hasResolution()) {
	        try {
	            // Start an Activity that tries to resolve the error
	            connectionResult.startResolutionForResult(
	                    this,
	                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
	            /*
	            * Thrown if Google Play services canceled the original
	            * PendingIntent
	            */
	        } catch (IntentSender.SendIntentException e) {
	            // Log the error
	            e.printStackTrace();
	        }
	    } else {
	       Toast.makeText(getApplicationContext(), "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
	    }
	}
	
	//A seperate thread than the thread that runs the GUI
	//Any type of networking should be done with asynctask
	class GrabContacts extends AsyncTask<String, String, String>{
		/* three methods get called, first preExecute, then do in 
		   background, and once complete, the onPost execute method 
		   will be called */
		boolean failure = false;
		JSONArray json = new JSONArray();
		int sum = 0;
		ArrayList<String> gpsArr = new ArrayList<String>();
		ArrayList<String> idArr = new ArrayList<String>();
		ArrayList<String> firstNameArr = new ArrayList<String>();
		ArrayList<String> lastNameArr = new ArrayList<String>();
		ArrayList<String> emailArr = new ArrayList<String>();
		ArrayList<String> universityArr = new ArrayList<String>();
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Main.this);
			pDialog.setMessage("Grabbing Contacts...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args){
			try {
				Log.d("request!", "starting");
				
				json = getJSON(FUNCTION_URL,30000000,"1000", s_latLng);
				
				if(json.length() > 0) {
					Log.i("Contacts", "Received Awesome");
					Log.i("adsf", " "+json.length());
					for(int i = 0; i < json.length(); i++){
			            JSONObject jsonobj = json.getJSONObject(i);
			            String id=jsonobj.getString("ID");
			            String gps=jsonobj.getString("GPS");
			            String first_name =jsonobj.getString("first_name");
			            String last_name =jsonobj.getString("last_name");
			            String email =jsonobj.getString("email");
			            String university =jsonobj.getString("university");
			            
			            /* Log.i("ID",id);
			            Log.i("GPS",gps);
			            Log.i("First_name",first_name);
			            Log.i("Last_name",last_name);
			            Log.i("Email", email);
			            Log.i("University",university); */
			            
			            gpsArr.add(gps);
			            idArr.add(id);
			            firstNameArr.add(first_name);
			            lastNameArr.add(last_name);
			            emailArr.add(email);
			            universityArr.add(university);
			            sum++;
			         }
				}else{
					Log.i("Contacts", "Lost Shit");
					//return json.getString(TAG_MESSAGE);
				}
			} catch(JSONException e){
				e.printStackTrace();
			}
			
			return "Done";
		}
		
		protected void onPostExecute(String file_url) {
			for(int j=0; j<sum; j++){
				String[] parts = gpsArr.get(j).split("\\,");
	            double dlat = Double.parseDouble(parts[0]);
	            double dlong = Double.parseDouble(parts[1]);
	            
				map.addMarker(new MarkerOptions()
        			.position(new LatLng(dlat,dlong))
        			.title(firstNameArr.get(j) + " " + lastNameArr.get(j))
        			.snippet(universityArr.get(j)));
			}
			pDialog.dismiss();
		}
	} 
	
	public static JSONArray getJSON(String FUNCTION_URL, int timeout, String radius, String latLng){
		
		JSONArray json = new JSONArray();
		
		try {
	        URL u = new URL(FUNCTION_URL);
	        HttpURLConnection c = (HttpURLConnection) u.openConnection();
	        c.setDoOutput(true);
	        c.setDoInput(true);
	        c.setUseCaches(false);
	        c.setRequestMethod("POST");
	        c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        String urlPar ="radius=" + radius + "&cords=" + latLng;
	        c.setRequestProperty("charset", "utf-8");
	        c.setRequestProperty("Content-Length", "" + Integer.toString(urlPar.getBytes().length));
	        c.setConnectTimeout(timeout);
	        
	        DataOutputStream wr = new DataOutputStream(c.getOutputStream ());
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
	
}
