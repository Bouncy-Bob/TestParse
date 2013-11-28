package com.example.testparse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    Parse.initialize(this, "sv2nCHpaJPUYhRy5BYap6uysbcfNkkHLO1YFerRn", "9I1iiRO8r8igBc6yJfv13tRcDIZ2MYUiIlY5wd5N");
	    if(ParseUser.getCurrentUser()!=null)
	    {
	    	ParseUser.logOut();
	    	Intent loginIntent = new Intent(this,LoginActivity.class);
			startActivity(loginIntent);
			Log.v("mainClass","activity started");
	    	//setContentView(R.layout.activity_main);
	    }
	    else
	    {
	    	Intent loginIntent = new Intent(this,LoginActivity.class);
			startActivity(loginIntent);
			Log.v("mainClass","activity started");
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
