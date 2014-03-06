package com.example.radiusdev;

import java.util.ArrayList;
import java.util.HashMap;
 
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomizedListView extends ListActivity{
	

    static final String KEY_ID = "contactId"; // parent node
	static final String KEY_FIRST = "firstName";
    static final String KEY_LAST = "lastName";
    static final String KEY_EMAIL = "emailAdress";
    static final String KEY_DISTANCE = "distance";
    //static final String KEY_THUMB_URL = "thumb_url";
    
    
    ListView listView;
    ArrayAdapter adapter;
    DBHelper dbTools = new DBHelper(this);
    TextView contactId;
    
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);
		Log.d("test",dbTools.toString());
		ArrayList<HashMap<String, String>> contactList = dbTools.getAllContacts();
		
		if(contactList.size() != 0){
			
			listView = getListView();
			adapter=new ArrayAdapter(this, contactList);
			listView.setAdapter(adapter);
			
			listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					contactId = (TextView) view.findViewById(R.id.contactId);
					
					String contactIdValue = contactId.getText().toString();
					
					Intent theIntent = new Intent(getApplication(), EditContact.class);
					
					theIntent.putExtra("contactId", contactIdValue);
					
					startActivity(theIntent);
					
				}
				
			});
		}
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
    
	public void showAddContact(View view){
		
		Intent theIntent = new Intent(getApplication(), NewContact.class);
		
		startActivity(theIntent);
	}

}
