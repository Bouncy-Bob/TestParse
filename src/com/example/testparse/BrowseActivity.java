package com.example.testparse;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class BrowseActivity extends Activity {

	private List<ParseUser> friends;
	private List<ParseObject>drinks;	
	private ParseObject drink;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		
		friends = ParseUser.getCurrentUser().getList("friends");
		
		ParseQuery<ParseObject> drinksQuery = ParseQuery.getQuery("Drink");
		drinksQuery.whereEqualTo("owner", ParseUser.getCurrentUser().getUsername());
		try{
			drinks=drinksQuery.find();
		}
		catch(ParseException e)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(e.getMessage()).setNeutralButton(R.string.dialog_browse_error_button, null).setOnDismissListener(new OnDismissListener(){

				@Override
				public void onDismiss(DialogInterface dialog) {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
				
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
		TextView top =(TextView)findViewById(R.id.text_drinks);
		top.setText(R.string.show_drinks_before +drinks.size()+ R.string.show_drinks_after);
		
	}
	
	public void redeemDrink(View view){
		drink = drinks.get(0);
		if(drink==null)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.dialog_no_drink).setNeutralButton(R.string.dialog_no_drink_button, null);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.dialog_redeem_drink_before 
					+drink.getParseUser("prevOwner").getUsername()+
					R.string.dialog_redeem_drink_after)
					.setPositiveButton(R.string.dialog_redeem_drink_button, new OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							drink.deleteEventually();
							drinks.remove(0);
						}
						
					})
					.setNegativeButton(R.string.dialog_cancel_redeem_drink, null);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

}
