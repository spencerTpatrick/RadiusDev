package com.example.radiusdev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DisplayAbout extends Activity{
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
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
			Intent h = new Intent(this, Main.class);
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
}
