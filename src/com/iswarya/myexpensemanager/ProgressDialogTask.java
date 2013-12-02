package com.iswarya.myexpensemanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ProgressDialogTask extends AsyncTask <Void, Void, Void>  
{
	private Context mContext;
    public ProgressDialogTask(Context context) {
    	 mContext = context;
	}
	private ProgressDialog Dialog = new ProgressDialog(mContext);

	protected void onPreExecute()
	{
		Dialog.setMessage("Please wait...");
		Dialog.show();
	}
	protected void onPostExecute(Void unused)    
	{
		try
		{
			if(Dialog.isShowing())
			{
				Dialog.dismiss();
			}
			// do your Display and data setting operation here
		}
		catch(Exception e)
		{
		}       
	}
	@Override
    protected Void doInBackground(Void... params) 
    {
  	  // Do your background data fetching here 
  	  return null;   
    }    
}