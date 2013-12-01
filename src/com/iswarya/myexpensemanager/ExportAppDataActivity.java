package com.iswarya.myexpensemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ExportAppDataActivity extends Activity{

	private Button mCreateBackupButton;
	private Button mCancelButton;
	SessionManager session;
	ExportDatabaseCSVTask task = new ExportDatabaseCSVTask(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_export_app_data_activity);
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
	
		
		mCreateBackupButton = (Button) findViewById(R.id.create_backup_button);
		mCreateBackupButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					task.execute("");
				//	Toast.makeText(getBaseContext(), "Export successful!", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					System.out.println("Error in Export App Data Activity");
					e.printStackTrace();
				}
			}
		});
		
		
		mCancelButton = (Button) findViewById(R.id.cancel_button);
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ExportAppDataActivity.this, AllExpensesActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.export_app_data_acctivity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_record_expenses:
			Intent recordExpensesIntent = new Intent(ExportAppDataActivity.this, AllExpensesActivity.class);
			startActivity(recordExpensesIntent);
			return true;
		case R.id.action_reports:
			Intent reportsIntent = new Intent(ExportAppDataActivity.this, ReportsActivity.class);
			startActivity(reportsIntent);
			return true;
		case R.id.action_logout:
			Intent mainIntent = new Intent(ExportAppDataActivity.this, MyExpenseManagerMainActivity.class);
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


