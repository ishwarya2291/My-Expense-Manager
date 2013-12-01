package com.iswarya.myexpensemanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>{
	
	File file=null;
	private Context mContext;
    public ExportDatabaseCSVTask(Context context) {
        mContext = context;
    }
	DatabaseHandler dbHandler;
	private ProgressDialog dialog;
    
    @Override
    protected void onPreExecute() {
    	dialog = new ProgressDialog(mContext);
        this.dialog.setMessage("Exporting database...");
        this.dialog.show();
    }
    protected Boolean doInBackground(final String... args){
        dbHandler = new DatabaseHandler(mContext);
      	File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        file = new File(exportDir, "ExpenseDetails.csv");
        try {
          
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            
            List<Expense> listdata = dbHandler.getAllExpenses();
            Expense expense = null;
             
            // this is the Column of the table and same for Header of CSV file
            String arrStr1[] ={"Date", "Amount", "Note", "Account Type", "Category", "Payment Type", "Receipt Path"};
               csvWrite.writeNext(arrStr1);
                
            if(listdata.size() > 1)
            {
             for(int index=0; index < listdata.size(); index++)
             {
               expense=listdata.get(index);
               String arrStr[] ={expense.getDate(), expense.getAmount(), expense.getNote(), expense.getAccountType(), 
            		   			expense.getCategory(), expense.getPaymentType(), expense.getReceipt()};
               csvWrite.writeNext(arrStr);
             }
            }
            
            csvWrite.close();
            return true;
        }
        catch (IOException e){
            Log.e("MainActivity", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    protected void onPostExecute(final Boolean success) {

        if (this.dialog.isShowing()){
            this.dialog.dismiss();
        }
        if (success){
        	Toast.makeText(mContext, "Export successful!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mContext, "Export failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
