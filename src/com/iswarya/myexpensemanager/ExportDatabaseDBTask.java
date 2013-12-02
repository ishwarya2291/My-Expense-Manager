package com.iswarya.myexpensemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class ExportDatabaseDBTask extends AsyncTask<String, Void, Boolean>{
	private Context mContext;

    public ExportDatabaseDBTask(Context context) {
        mContext = context;
    } 
	
    // can use UI thread here
    protected void onPreExecute() {

    }

    // automatically done on worker thread (separate from UI thread)
    protected Boolean doInBackground(final String... args) {
    	File dbFile = new File(mContext.getDatabasePath(DatabaseHandler.DATABASE_NAME).toString());
       
       File exportDir = new File(Environment.getExternalStorageDirectory(), "");
       if (!exportDir.exists()) {
          exportDir.mkdirs();
       }
       File file = new File(exportDir, dbFile.getName());

       try {
          file.createNewFile();
          this.copyFile(dbFile, file);
          return true;
       } catch (IOException e) {
          Log.e("mypck", e.getMessage(), e);
          return false;
       }
    }

    // can use UI thread here
    protected void onPostExecute(final Boolean success) {    	
       if (success) {
          Toast.makeText(mContext, "Export successful!", Toast.LENGTH_SHORT).show();
       } else {
          Toast.makeText(mContext, "Export failed", Toast.LENGTH_SHORT).show();
       }
    }

    void copyFile(File src, File dst) throws IOException {
       FileChannel inChannel = new FileInputStream(src).getChannel();
       FileChannel outChannel = new FileOutputStream(dst).getChannel();
       try {
          inChannel.transferTo(0, inChannel.size(), outChannel);
       } finally {
          if (inChannel != null)
             inChannel.close();
          if (outChannel != null)
             outChannel.close();
       }
    }	
}