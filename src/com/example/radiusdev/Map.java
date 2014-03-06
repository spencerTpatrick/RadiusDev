package com.example.radiusdev;

import org.json.JSONObject;

import com.google.android.gms.maps.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Map  extends Activity {
	
	private GoogleMap mMap = null;
	//private LatLng location = new LatLng(34.67583085856639,-82.83429526616644);
	//private static final String USERINFO_URL = "http://1meccaproduction.com/radiusServer/displayUserInfo.php";
	
	//JSONObject jsonParser = new JSONObject();
	
	//JSONObject json = jsonParser.makeHttpRequest(USERINFO_URL, "GET", );
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        //mMap.animateCamera(CameraUpdateFactory.newLatLng(location));
        //GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        /* LatLng userLoc = new LatLng(34.67583085856639,-82.83429526616644);
        
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 13));
        
        mMap.addMarker(new MarkerOptions()
        		.title("User")
        		.snippet("This is you.")
        		.position(userLoc)); */
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
			break;
		case R.id.profile:
			Intent i = new Intent(this, DisplayUserInfo.class);
			//finish();
			startActivity(i);
			break;
		case R.id.contacts:
			Intent j = new Intent(this, CustomizedListView.class);
			//finish();
			startActivity(j);
			break;
		case R.id.about:
			Intent k = new Intent(this, DisplayAbout.class);
			//finish();
			startActivity(k);
			break;
		case R.id.settings:
			Intent l = new Intent(this, UserSettings.class);
			//finish();
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
    
    
}
