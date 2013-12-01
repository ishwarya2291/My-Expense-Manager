package com.iswarya.myexpensemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ReportsActivity extends Activity{

	SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reports, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_record_expenses:
			Intent recordExpensesIntent = new Intent(ReportsActivity.this, AllExpensesActivity.class);
			startActivity(recordExpensesIntent);
			return true;
		case R.id.action_export_app_data:
			Intent exportAppDataIntent = new Intent(ReportsActivity.this, ExportAppDataActivity.class);
			startActivity(exportAppDataIntent);
			return true;
		case R.id.action_logout:
			Intent mainIntent = new Intent(ReportsActivity.this, MyExpenseManagerMainActivity.class);
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
