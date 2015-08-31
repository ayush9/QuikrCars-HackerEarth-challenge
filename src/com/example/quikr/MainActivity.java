package com.example.quikr;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

    ListView lv=null;
    Context context;
    private ProgressDialog pDialog;
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

    // URL to get contacts JSON
    private static String url = "http://quikr.0x10.info/api/cars?type=json&query=list_cars";
 
    // contacts JSONArray
    JSONArray contacts = null;
    JSONArray contact1=null;
 
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         new GetContacts().execute();
     /* 
         lv.setOnItemClickListener(new OnItemClickListener() {
        	 
             @Override
             public void onItemClick(AdapterView<?> parent, View view,
                     int position, long id) {
                 Intent in = new Intent(getApplicationContext(),
                         MyActivity.class);
                 in.putExtra("hashMap", contactList.get(position));
                startActivity(in);
  
             }
         });
         
         
       */ 
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
 
        return super.onCreateOptionsMenu(menu);
    }



private class GetContacts extends AsyncTask<Void, Void, Void> {
	 
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
               // JSONObject jsonObj = new JSONObject(jsonStr);
                 
                // Getting JSON Array node
                contacts = new JSONArray(jsonStr);

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                     
                    String name = c.getString("name");
                    String imagee = c.getString("image");
                    String urldisplay = imagee;
               /*
                    Bitmap mIcon11 = null;
            	      try {
            	        InputStream in = new java.net.URL(urldisplay).openStream();
            	        mIcon11 = BitmapFactory.decodeStream(in);
            	      } catch (Exception e) {
            	          Log.e("Error", e.getMessage());
            	          e.printStackTrace();
            	      }
            	    bitmapArray.add(mIcon11);
            	    
            	   */ 
                    String type = c.getString("type");
                    String price = c.getString("price");
                    String rating = c.getString("rating");
                    String brand = c.getString("brand");
                    String color = c.getString("color");
                    String engine_cc = c.getString("engine_cc");
                    String mileage = c.getString("mileage");
                    String abs_exist = c.getString("abs_exist");
                    String description = c.getString("description");
                    String link = c.getString("link");
                    
                    
                    contact1 = new JSONArray(c.getString("cities"));
                    String city=contact1.getJSONObject(0).getString("city");
                    String users=contact1.getJSONObject(0).getString("users");
                    
                    // looping through All Contacts
                    for (int j = 1; j < contact1.length(); j++) {
                        JSONObject c1 = contact1.getJSONObject(j);
                         
                        city = city +","+ c1.getString("city");
                         users= users +","+ c1.getString("users");
                    }
                    
                    // tmp hashmap for single contact
                    HashMap<String, String> contact = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    contact.put("name", name);
                    contact.put("imagee", imagee);
                    contact.put("type", type);
                    contact.put("price", price);
                    contact.put("rating", rating);
                    contact.put("brand", brand);
                    contact.put("color", color);
                    contact.put("engine_cc", engine_cc);
                    contact.put("mileage", mileage);
                    contact.put("abs_exist", abs_exist);
                    contact.put("description", description);
                    contact.put("city", city);
                    contact.put("users", users);
                    contact.put("link", link);

                    // adding contact to contact list
                    contactList.add(contact);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */
        
        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter1(MainActivity.this, contactList)); // lv.setAdapter(new CustomAdapter1(MainActivity.this, contactList,bitmapArray));

    }

}

}