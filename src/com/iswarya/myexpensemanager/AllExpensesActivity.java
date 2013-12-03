package com.iswarya.myexpensemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AllExpensesActivity extends Activity {

	SessionManager session;
	private ListView mList;
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	private TextView mTotalMonthAmount;
	DatabaseHandler db;
	Expense expense;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_expenses);
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        
        db = new DatabaseHandler(this);
        expense = new Expense();
        
        mTotalMonthAmount = (TextView) findViewById(R.id.total_amount_month);
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();   

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

          public void onShake() { 
            finish();
            startActivity(getIntent());
            Toast.makeText(AllExpensesActivity.this, "Refreshed!!!", Toast.LENGTH_SHORT).show();
          }
        });
        
        mList = (ListView) findViewById(R.id.list);
        
        
        ExpenseListArrayAdapter adapter = new ExpenseListArrayAdapter(this, db.getAllExpenses());
        
        mList.setAdapter(adapter);
        double allItems=0;
        for(int i=0;i<db.getExpensesCount();i++){
        	allItems = Double.parseDouble(adapter.getItem(i).getAmount()) + allItems; 	
        }
        
        
        mTotalMonthAmount.setText(Double.toString(allItems));
      
        
        mList.setOnItemClickListener(new OnItemClickListener() {
        	
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
        			expense = (Expense) arg0.getAdapter().getItem(arg2);
        			int id= expense.getId();
        			Intent showExpenseIntent = new Intent(AllExpensesActivity.this, ShowExpenseActivity.class);
        			showExpenseIntent.putExtra("mId", id);  			
        			startActivity(showExpenseIntent);
        	}
        	
		});
        
        mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				expense = (Expense) arg0.getAdapter().getItem(arg2);
				int id= expense.getId();
				expense = db.getExpense(id);
				Builder alert = new AlertDialog.Builder(AllExpensesActivity.this);
				alert.setTitle("Delete expense");
            	alert.setMessage("Do you really want to delete this expense?");
            	alert.setPositiveButton("Yes",new DialogInterface.OnClickListener() {		
					@Override
					public void onClick(DialogInterface dialog, int which) {
						db.deleteExpense(expense);		
						finish();
			            startActivity(getIntent());
					}
				});            	
            	alert.setNegativeButton("No", null);
            	alert.show(); 
				
				return true;
			}
		});
        
	}
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener,
	        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_UI);
	  }

	  @Override
	  protected void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onPause();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_expenses, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_add:
			Intent addIntent = new Intent(AllExpensesActivity.this, EditExpenseActivity.class);
			startActivity(addIntent);
			return true;
		case R.id.action_refresh:
			finish();
            startActivity(getIntent());
            Toast.makeText(AllExpensesActivity.this, "Refreshed!!!", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_reports:
			Intent reportsIntent = new Intent(AllExpensesActivity.this, ReportsActivity.class);
			startActivity(reportsIntent);
			return true;
		case R.id.action_export_app_data:
			ExportDatabaseCSVTask task = new ExportDatabaseCSVTask(this);
			task.execute("");
			ExportDatabaseDBTask dbTask = new ExportDatabaseDBTask(this);
			dbTask.execute("");
			return true;
		case R.id.action_logout:
			Intent mainIntent = new Intent(AllExpensesActivity.this, MyExpenseManagerMainActivity.class);
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
