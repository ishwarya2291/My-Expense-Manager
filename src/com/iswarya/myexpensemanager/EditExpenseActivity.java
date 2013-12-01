package com.iswarya.myexpensemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditExpenseActivity extends Activity {

	public Button mSaveButton, mCancelButton, mTakePhotoButton, mGalleryButton, mUpdateButton;
	private ImageView mReceiptImage;
	private EditText mAmountInDollars, mDate, mNotes;
	private Spinner mAccountType,mCategoryType,mPaymentMethod;
	SessionManager session;
	String imagePath = "";
	static int mId;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;
	DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_expense);
		
		// Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
		
		mSaveButton = (Button) findViewById(R.id.save_button);
		mCancelButton = (Button) findViewById(R.id.cancel_button);
		mTakePhotoButton = (Button) findViewById(R.id.take_photo);
		mGalleryButton = (Button) findViewById(R.id.gallery);
		mUpdateButton = (Button) findViewById(R.id.update_button);
		mReceiptImage = (ImageView) findViewById(R.id.receipt);
		mAmountInDollars = (EditText) findViewById(R.id.amount_in_dollars);
		mDate = (EditText) findViewById(R.id.expense_date);
		mNotes = (EditText) findViewById(R.id.notes);
		mAccountType = (Spinner) findViewById(R.id.account_type_spinner);
		mCategoryType = (Spinner) findViewById(R.id.category_type_spinner);
		mPaymentMethod = (Spinner) findViewById(R.id.payment_method_spinner);
		
		
		Intent intent = getIntent();
		System.out.println(intent.getIntExtra("mId", 0)+ " Intent Value");
		if(intent.getIntExtra("mId", 0)>0){
	        mId = intent.getIntExtra("mId", 0);
	        System.out.println(mId);
	        Expense expense = new Expense();
	        expense = db.getExpense(mId);
	        mAmountInDollars.setText(expense.getAmount());
	        mDate.setText(expense.getDate());
	        mNotes.setText(expense.getNote());
	        
	        if(expense.getAccountType().equals("Business")){
	        	mAccountType.setSelection(0);
	        }else if(expense.getAccountType().equals("Family")){
	        	mAccountType.setSelection(1);
	        }else if(expense.getAccountType().equals("Friends")){
	        	mAccountType.setSelection(2);
	        }else if(expense.getAccountType().equals("Home")){
	        	mAccountType.setSelection(3);
	        }else if(expense.getAccountType().equals("Kids")){
	        	mAccountType.setSelection(4);
	        }else if(expense.getAccountType().equals("Personal")){
	        	mAccountType.setSelection(5);
	        }else if(expense.getAccountType().equals("Work")){
	        	mAccountType.setSelection(6);
	        }else if(expense.getAccountType().equals("Miscellaneous")){
	        	mAccountType.setSelection(7);
	        }
	        	
	        if(expense.getCategory().equals("Airfare")){
	        	mCategoryType.setSelection(0);
	        }else if(expense.getCategory().equals("Bus")){
	        	mCategoryType.setSelection(1);
	        }else if(expense.getCategory().equals("Car")){
	        	mCategoryType.setSelection(2);
	        }else if(expense.getCategory().equals("Entertainment")){
	        	mCategoryType.setSelection(3);
	        }else if(expense.getCategory().equals("Food")){
	        	mCategoryType.setSelection(4);
	        }else if(expense.getCategory().equals("Fuel")){
	        	mCategoryType.setSelection(5);
	        }else if(expense.getCategory().equals("Groceries")){
	        	mCategoryType.setSelection(6);
	        }else if(expense.getCategory().equals("Hotel")){
	        	mCategoryType.setSelection(7);
	        }else if(expense.getCategory().equals("Laundry")){
	        	mCategoryType.setSelection(8);
	        }else if(expense.getCategory().equals("Medical")){
	        	mCategoryType.setSelection(9);
	        }else if(expense.getCategory().equals("Mobile")){
	        	mCategoryType.setSelection(10);
	        }else if(expense.getCategory().equals("Train")){
	        	mCategoryType.setSelection(11);
	        }else if(expense.getCategory().equals("Miscellaneous")){
	        	mCategoryType.setSelection(12);
	        }
	        
	        if(expense.getPaymentType().equals("Credit Card")){
	        	mPaymentMethod.setSelection(0);
	        }else if(expense.getPaymentType().equals("Debit Card")){
	        	mPaymentMethod.setSelection(1);
	        }else if(expense.getPaymentType().equals("Cash")){
	        	mPaymentMethod.setSelection(2);
	        }else if(expense.getPaymentType().equals("Miscellaneous")){
	        	mPaymentMethod.setSelection(3);
	        }
	        mUpdateButton.setVisibility(View.VISIBLE);
	        mSaveButton.setVisibility(View.INVISIBLE);
	        
		}

		
		mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// create Intent to take a picture and return control to the calling application
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// create a file to save the image
				File imagesFolder = new File(Environment.getExternalStorageDirectory(),
						"MyExpenseManager");
				imagesFolder.mkdirs(); // <----
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				File image = new File(imagesFolder, "IMG_"+ timeStamp + ".jpg");
				fileUri = Uri.fromFile(image);

				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image filename

				// start the image capture Intent
				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});	
		
		mGalleryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent,1);
				
			}
		});
		
		mReceiptImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent imageIntent = new Intent(EditExpenseActivity.this,ImageActivity.class);
				imageIntent.putExtra("imgPath", imagePath);
	            startActivity(imageIntent);				
			}
		});
		
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Inserting expense data.....");
				if(imagePath.equals("")){
					imagePath = "No Receipt";
					 System.out.println(imagePath);
				}
				db.addExpense(new Expense(mAmountInDollars.getText().toString(),
											mDate.getText().toString(),
											mNotes.getText().toString(), 
											mAccountType.getSelectedItem().toString(),
											mCategoryType.getSelectedItem().toString(),
											mPaymentMethod.getSelectedItem().toString(),
											imagePath ));
				Intent i = new Intent(EditExpenseActivity.this, AllExpensesActivity.class);
				startActivity(i);
			}
		});
		
		mUpdateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Updating expense data.....");
				db.updateExpense(new Expense(mAmountInDollars.getText().toString(),
													  mDate.getText().toString(),
													  mNotes.getText().toString(),
													  mAccountType.getSelectedItem().toString(),
													  mCategoryType.getSelectedItem().toString(),
													  mPaymentMethod.getSelectedItem().toString(),
													  imagePath ), mId);				
				Intent i = new Intent(EditExpenseActivity.this, AllExpensesActivity.class);
				startActivity(i);
				
			}
		});
		
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(EditExpenseActivity.this, AllExpensesActivity.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n" + fileUri.getPath(),
						Toast.LENGTH_LONG).show();
						
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeFile(fileUri.getPath());

				mReceiptImage.setImageBitmap(bitmap);
				imagePath = getRealPathFromURI(fileUri);

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}else{
			if(resultCode == RESULT_OK)
	        {  
	            Uri selectedImage = data.getData();
	            InputStream imageStream = null;
	            try 
	            {
	                imageStream = getContentResolver().openInputStream(selectedImage);
	            } 
	            catch (FileNotFoundException e) 
	            {
	                e.printStackTrace();
	            }

	            BitmapFactory.Options options = new BitmapFactory.Options();
	            options.inSampleSize =8;

	            Bitmap selectedPicture = null;
	            selectedPicture = BitmapFactory.decodeStream(imageStream,null,options);
	            mReceiptImage.setImageBitmap(selectedPicture);
	            imagePath = getRealPathFromURI(selectedImage);
	            
	        }
		}
	}
	
	@SuppressWarnings("deprecation")
	public String getRealPathFromURI(Uri contentUri)
    {
		 try
	        {
	            String[] proj = {MediaStore.Images.Media.DATA};
	          //  EditExpenseActivity activity = new EditExpenseActivity();
	            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            cursor.moveToFirst();
	            return cursor.getString(column_index);
	        }
	        catch (Exception e)
	        {
	            return contentUri.getPath();
	        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_expense, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_reports:
			Intent reportsIntent = new Intent(EditExpenseActivity.this, ReportsActivity.class);
			startActivity(reportsIntent);
			return true;
		case R.id.action_export_app_data:
			Intent exportAppDataIntent = new Intent(EditExpenseActivity.this, ExportAppDataActivity.class);
			startActivity(exportAppDataIntent);
			return true;
		case R.id.action_logout:
			Intent mainIntent = new Intent(EditExpenseActivity.this, MyExpenseManagerMainActivity.class);
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
