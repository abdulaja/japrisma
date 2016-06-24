package com.asismedia.japrisma;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;


public class Cover extends Activity {
	
	ProgressBar progressBar;
	   
	@Override
	protected void onResume(){
	   	super.onResume();
	   	new BackgroundAsyncTask().execute();
	   	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cover);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

	}
	
	public class BackgroundAsyncTask extends AsyncTask<Void, Integer, Void> {
		int myProgress;
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			myProgress = 0;
		}
		
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			while(myProgress<100){
				myProgress++;
				publishProgress(myProgress);
				SystemClock.sleep(20);
			}
			Intent inten = new Intent(Cover.this, Home.class);
			Cover.this.startActivity(inten);
			Cover.this.finish();
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub              
			progressBar.setProgress(values[0]);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cover, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
