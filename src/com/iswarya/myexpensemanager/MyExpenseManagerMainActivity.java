package com.iswarya.myexpensemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;



public class MyExpenseManagerMainActivity extends Activity {

	private EditText mFullName, mEmailAddress;
	private Button mContinueButton;
	 // Session Manager Class
    SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_expense_manager_main);
		setupUI(findViewById(R.id.parent));

		
		// Session Manager
        session = new SessionManager(getApplicationContext());
		
		mFullName = (EditText) findViewById(R.id.full_name);
		mEmailAddress = (EditText) findViewById(R.id.email_address);
		mContinueButton = (Button) findViewById(R.id.continue_button_text);
		
		mContinueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get fullname, password from EditText
                String fullName = mFullName.getText().toString();
                String emailAddress = mEmailAddress.getText().toString();
                
                // Check if full name, password is filled 
                if(fullName.trim().length() > 0 && emailAddress.trim().length() > 0){
                    // For testing purpose fullname, password is checked with sample data
                    // fullname = test
                    // emailAddress = test
                    if(fullName.equals(fullName) && emailAddress.equals(emailAddress)){
                         
                        // Creating user login session
                        // For testing i am storing name, email as follow
                        // Use user real data
                        session.createLoginSession(fullName, emailAddress);
                         
                        // Staring StartupActivity
                        Intent i = new Intent(getApplicationContext(), StartupActivity.class);
                        startActivity(i);
                     //   finish();
                         
                    }else{
                        // fullname / emailAddress doesn't match
                    	Builder alert = new AlertDialog.Builder(MyExpenseManagerMainActivity.this);
                    	alert.setTitle("Login Failed!!");
                    	alert.setMessage("Name and Email are incorrect");
                    	alert.setPositiveButton("OK",null);
                    	alert.show();  
                    }               
                }else{
                    // user didn't entered fullname or emailAddress
                    // Show alert asking him to enter the details
                	Builder alert = new AlertDialog.Builder(MyExpenseManagerMainActivity.this);
                	alert.setTitle("Login Failed!!");
                	alert.setMessage("Please enter Name and Email Address");
                	alert.setPositiveButton("OK",null);
                	alert.show();  
                }

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_expense_manager_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_exit:
			finish();
			return true;
		default: 
			return super.onOptionsItemSelected(item);
			
		}
	}
	
	public void setupUI(View view) {
	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) {

	        view.setOnTouchListener(new OnTouchListener() {

	            @Override
				public boolean onTouch(View v, MotionEvent event) {
	                hideSoftKeyboard(v);
	                return false;
	            }

	        });
	    }

	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) {

	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

	            View innerView = ((ViewGroup) view).getChildAt(i);

	            setupUI(innerView);
	        }
	    }
	}
	
	public static void hideSoftKeyboard(View v) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
}
