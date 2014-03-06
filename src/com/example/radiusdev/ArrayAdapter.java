package com.example.radiusdev;

import java.util.ArrayList;
import java.util.HashMap;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArrayAdapter extends BaseAdapter{

	private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    
    public ArrayAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader = new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView contactID = (TextView)vi.findViewById(R.id.contactId); // contact ID
        TextView firstName = (TextView)vi.findViewById(R.id.first_name); // first_name
        TextView lastName = (TextView)vi.findViewById(R.id.last_name); // last_name
        TextView email = (TextView)vi.findViewById(R.id.email); // email
        TextView distance = (TextView)vi.findViewById(R.id.distance); // distance from user
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String,String> contact = new HashMap<String,String>();
        contact = data.get(position);
 
        // Setting all values in listview
        contactID.setText(contact.get(CustomizedListView.KEY_ID));
        firstName.setText(contact.get(CustomizedListView.KEY_FIRST));
        lastName.setText(contact.get(CustomizedListView.KEY_LAST));
        email.setText(contact.get(CustomizedListView.KEY_EMAIL));
        distance.setText(contact.get(CustomizedListView.KEY_DISTANCE));
        //imageLoader.DisplayImage(contact.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
	
}
