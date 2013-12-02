package com.iswarya.myexpensemanager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReportsActivity extends Activity{

	SessionManager session;
	public static Button mReportAccountButton, mReportCategoryButton, mReportPaymentMethodButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        
        mReportAccountButton = (Button) findViewById(R.id.reports_account);
        mReportCategoryButton = (Button) findViewById(R.id.reports_category);
        mReportPaymentMethodButton = (Button) findViewById(R.id.reports_payment_method);
        
        FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		AccountFragment pieChartFragment = new AccountFragment();
		ft.add(R.id.pie_chart_fragment, pieChartFragment);
		ft.commit();
		
		mReportAccountButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				accountFragment();
			}
		});
		
		mReportCategoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				categoryFragment();
			}
		});
		
		mReportPaymentMethodButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paymentMethodFragment();
			}
		});
		
	}
	
	public void accountFragment(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		AccountFragment accFragment = new AccountFragment();
		ft.replace(R.id.pie_chart_fragment, accFragment);
		ft.commit();
	}
	
	public void categoryFragment(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		CategoryFragment catFragment = new CategoryFragment();
		ft.replace(R.id.pie_chart_fragment, catFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	
	public void paymentMethodFragment(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		PaymentMethodFragment payFragment = new PaymentMethodFragment();
		ft.replace(R.id.pie_chart_fragment, payFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
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
			ExportDatabaseCSVTask task = new ExportDatabaseCSVTask(this);
			task.execute("");
			ExportDatabaseDBTask dbTask = new ExportDatabaseDBTask(this);
			dbTask.execute("");
			return true;
		case R.id.action_logout:
			Intent mainIntent = new Intent(ReportsActivity.this, MyExpenseManagerMainActivity.class);
			startActivity(mainIntent);
			session.logoutUser();
			return true;
		case R.id.action_exit:
			moveTaskToBack(true); 
		    this.finish();
			return true;
		case android.R.id.home:
			finish();
			return true;
		default: 
			return super.onOptionsItemSelected(item);
			
		}
	}
}