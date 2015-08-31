package com.example.quikr;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import android.text.Html;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {
	HashMap<String, String> hashmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
		RatingBar rb;
        Intent intent = getIntent();    
        hashmap = (HashMap<String, String>) intent.getSerializableExtra("hashMap");
          t1=(TextView)findViewById(R.id.name);
        t1.setText(hashmap.get("name"));
        t2=(TextView)findViewById(R.id.type);
        t2.setText(hashmap.get("type"));
        t6=(TextView)findViewById(R.id.price);
        t6.setText("Rs. "+hashmap.get("price")+" Lakhs");
        t3=(TextView)findViewById(R.id.brand);
        t3.setText(hashmap.get("brand"));
      //  t4=(TextView)findViewById(R.id.color);
//        t4.setText(hashmap.get("color"));
        t5=(TextView)findViewById(R.id.description);
        t5.setText(hashmap.get("description"));
        t7=(TextView)findViewById(R.id.engine_cc);
        t7.setText(hashmap.get("engine_cc"));
        t8=(TextView)findViewById(R.id.mileage);
        t8.setText(hashmap.get("mileage"));
        t9=(TextView)findViewById(R.id.abs_exist);
        t9.setText(hashmap.get("abs_exist"));
        rb=(RatingBar)findViewById(R.id.rating);
        String str = hashmap.get("rating");
        float f=Float.parseFloat(str);
        rb.setRating(f);
        ImageView image = (ImageView) findViewById(R.id.imagee);    
        new DownloadImageTask(image).execute(hashmap.get("imagee"));
       
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_search:
            // search action
            return true;
        case R.id.action_share:
            // location found
        	Intent sendIntent = new Intent();
        	sendIntent.setAction(Intent.ACTION_SEND);
        	sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,Download the McAfee Products App @ "+hashmap.get("link")+"  and Enjoy!");
        	sendIntent.setType("text/plain");
        	startActivity(sendIntent);            
        	return true;
        case R.id.action_sms:
        	Intent i = new Intent(this,SMSActivity.class);
            startActivity(i);
            return true;
        case R.id.action_link:
        	 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(hashmap.get("link")));
             startActivity(browserIntent);            return true;
        case R.id.action_check_updates:
            // check for updates action
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 


    public void share(View v)
    {
    	Intent sendIntent = new Intent();
    	sendIntent.setAction(Intent.ACTION_SEND);
    	sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,Download the McAfee Products App @ "+hashmap.get("link")+"  and Enjoy!");
    	sendIntent.setType("text/plain");
    	startActivity(sendIntent);
    }
    public void store(View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(hashmap.get("link")));
        startActivity(browserIntent);
    }
    public void sms(View v)
    {
        Intent i = new Intent(this,SMSActivity.class);
        startActivity(i);
    }



	 public static Bitmap StringToBitmap(String encodedString) {
	        try {
	            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
	            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
	            return bitmap;
	        } catch (NullPointerException e) {
	            e.getMessage();
	            return null;
	        } catch (OutOfMemoryError e) {
	            return null;
	        }
	    }

}
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	  ImageView bmImage;

	  public DownloadImageTask(ImageView bmImage) {
	      this.bmImage = bmImage;
	  }

	  protected Bitmap doInBackground(String... urls) {
	      String urldisplay = urls[0];
	      Bitmap mIcon11 = null;
	      try {
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	          Log.e("Error", e.getMessage());
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
	      bmImage.setImageBitmap(result);
	  }
	}
