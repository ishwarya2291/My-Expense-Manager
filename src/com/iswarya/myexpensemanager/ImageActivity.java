package com.iswarya.myexpensemanager;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class ImageActivity extends Activity {

	private static ImageView mDisplayReceipt;
	private String imgPath;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		mDisplayReceipt = (ImageView) findViewById(R.id.display_receipt);
		
		Intent intent = getIntent();
        imgPath = intent.getStringExtra("imgPath");
		
		File imgFile = new  File(imgPath);
        if(imgFile.exists())
        {
        	mDisplayReceipt.setImageURI(Uri.fromFile(imgFile));

        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

}
