package com.iswarya.myexpensemanager;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowExpenseActivity extends Activity {
	
	SessionManager session;
	int mId;
	private static TextView mAmountInDollars, mDate, mNotes, mAccountType,mCategoryType,mPaymentMethod, mReceiptText;
	private static ImageView mReceiptImage;
	private static Button mDeleteButton;
	DatabaseHandler db;
    Expense expense = new Expense();
    private String imgPath;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_expense);
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        
        db = new DatabaseHandler(this);
        
        Intent intent = getIntent();
        mId = intent.getIntExtra("mId", 0);
        System.out.println(mId + "in show expense act");
        expense = db.getExpense(mId);
        
        mAmountInDollars = (TextView) findViewById(R.id.show_amount_in_dollars);
        mDate = (TextView) findViewById(R.id.show_date);
        mNotes = (TextView) findViewById(R.id.show_note);
        mAccountType = (TextView) findViewById(R.id.show_account_type);
        mCategoryType = (TextView) findViewById(R.id.show_category_name);
        mPaymentMethod = (TextView) findViewById(R.id.show_payment_type);
        mReceiptText = (TextView) findViewById(R.id.receipt_text);
        mReceiptImage = (ImageView) findViewById(R.id.show_receipt);
        mDeleteButton = (Button) findViewById(R.id.delete_button);
        
        mAmountInDollars.setText(expense.getAmount());
        mDate.setText(expense.getDate());
        mNotes.setText(expense.getNote());
        mAccountType.setText(expense.getAccountType());
        mCategoryType.setText(expense.getCategory());
        mPaymentMethod.setText(expense.getPaymentType());        
        
        imgPath = expense.getReceipt();
        System.out.println("Image Path is "+imgPath);
        if(!imgPath.equals("No Receipt")){
        	File imgFile = new  File(imgPath);
            if(imgFile.exists())
            {
            	mReceiptImage.setImageURI(Uri.fromFile(imgFile));

            }
            mReceiptImage.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				Intent imageIntent = new Intent(ShowExpenseActivity.this,ImageActivity.class);
    				imageIntent.putExtra("imgPath", imgPath);
    	            startActivity(imageIntent);
    				
    			}
    		});
        }else{
        	mReceiptText.setText("");
        }
   
        mDeleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.deleteExpense(expense);
				Intent i = new Intent(ShowExpenseActivity.this, AllExpensesActivity.class);
				startActivity(i);
			}
		});
           
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_expense, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_edit:
			Intent addIntent = new Intent(ShowExpenseActivity.this, EditExpenseActivity.class);
			addIntent.putExtra("mId", mId);
			startActivity(addIntent);
			return true;
		case R.id.action_reports:
			Intent reportsIntent = new Intent(ShowExpenseActivity.this, ReportsActivity.class);
			startActivity(reportsIntent);
			return true;
		case R.id.action_export_app_data:
			ExportDatabaseCSVTask task = new ExportDatabaseCSVTask(this);
			task.execute("");
			return true;
		case R.id.action_logout:
			Intent mainIntent = new Intent(ShowExpenseActivity.this, MyExpenseManagerMainActivity.class);
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
