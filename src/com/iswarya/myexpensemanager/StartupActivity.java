package com.iswarya.myexpensemanager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class StartupActivity extends Activity {

	private static TextView mFullName;
	SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
	
		mFullName = (TextView) findViewById(R.id.hello_name);
		
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
         
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
         
        // name
        String name = user.get(SessionManager.KEY_NAME);
        mFullName.setText("Hello "+name);
         
        // email
        String email = user.get(SessionManager.KEY_EMAIL);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.startup, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_add:
			Intent addExpensesIntent = new Intent(StartupActivity.this, EditExpenseActivity.class);
			startActivity(addExpensesIntent);
			return true;
		case R.id.action_record_expenses:
			Intent recordExpensesIntent = new Intent(StartupActivity.this, AllExpensesActivity.class);
			startActivity(recordExpensesIntent);
			return true;
		case R.id.action_reports:
			Intent reportsIntent = new Intent(StartupActivity.this, ReportsActivity.class);
			startActivity(reportsIntent);
			return true;
		case R.id.action_export_app_data:
			Intent exportAppDataIntent = new Intent(StartupActivity.this, ExportAppDataActivity.class);
			startActivity(exportAppDataIntent);
			return true;
		case R.id.action_logout:
			Intent mainIntent = new Intent(StartupActivity.this, MyExpenseManagerMainActivity.class);
			startActivity(mainIntent);
			session.logoutUser();
			return true;
		case R.id.action_exit:
			finish();
			return true;
		case android.R.id.home:
			finish();
			return true;
		default: 
			return super.onOptionsItemSelected(item);
			
		}
	}
}
