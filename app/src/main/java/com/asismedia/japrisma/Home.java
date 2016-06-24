package com.asismedia.japrisma;

import java.util.ArrayList;
import java.util.Calendar;

import com.asismedia.japrisma.database.DownloadData;
import com.asismedia.japrisma.ext.Async;
import com.asismedia.japrisma.ext.ConnectionDetector;
import com.asismedia.japrisma.slidingmenu.adapter.NavDrawerListAdapter;
import com.asismedia.japrisma.slidingmenu.model.NavDrawerItem;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Home extends Activity {

	private DrawerLayout mDrawerLayout;
	public ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;

	//SharedPreferences prefs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		// creating connection detector class instance
		cd = new ConnectionDetector(getApplicationContext());

		//prefs = getSharedPreferences("com.asismedia.japrisma", MODE_PRIVATE);
		switch (checkAppStart()) {
			case NORMAL:
				// We don't want to get on the user's nerves
				//Toast.makeText(this, "Normal Use", Toast.LENGTH_SHORT).show();
				newDataNotif();
				break;
			case FIRST_TIME_VERSION:
				// TODO show what's new
				//Toast.makeText(this, "First Update Use", Toast.LENGTH_SHORT).show();
				// get Internet status
				break;
			case FIRST_TIME:
				// TODO show a tutorial
				//Toast.makeText(this, "First Time Use", Toast.LENGTH_SHORT).show();
				// get Internet status
				isInternetPresent = cd.isConnectingToInternet();
				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					// make HTTP requests
					new DownloadData(this).execute();
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					AlertDialog.Builder check = new AlertDialog.Builder(this);
					check.setTitle("No Internet Connection")
							.setMessage("You don't have internet connection.\nPlease check your connection.")
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									//dialog.cancel();
									reloadActivity();
								}
							}).show();
				}
				break;
			default:
				break;
		}


		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Search
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Touristic Spot
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Touristic Area
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Event
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// Help
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		// About
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		// Update
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return false;
	}

	//open drawer when option menu pressed
	public boolean onKeyDown(int keyCode, KeyEvent e) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			// your action...

			if (!mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.openDrawer(mDrawerList);
			} else {
				mDrawerLayout.closeDrawer(mDrawerList);
			}
			return true;
		}
		return super.onKeyDown(keyCode, e);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new SearchFragment();
				break;
			case 2:
				fragment = new TouristicspotFragment();
				break;
			case 3:
				fragment = new TouristicareaFragment();
				break;
			case 4:
				fragment = new EventFragment();
				break;
			case 5:
				fragment = new HelpFragment();
				break;
			case 6:
				fragment = new AboutFragment();
				break;
			case 7:
				fragment = new UpdateFragment();
				break;

			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	boolean doubleBackToExitPressedOnce = false;

	@Override
	public void onBackPressed(){
		//Fragment now = null;
	    FragmentManager fm = getFragmentManager();
	    if (fm.getBackStackEntryCount() > 0) {
			//Log.i("Home", "popping backstack");
			fm.popBackStack();
		} else {
			Fragment home = null;
			int position = 0;
			switch (position) {
				case 0:
					home = new HomeFragment();
					break;
			}
			if(home != null) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
						.replace(R.id.frame_container, home).commit();
			}
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);

			if (doubleBackToExitPressedOnce) {
				super.onBackPressed();
				return;
			}

			this.doubleBackToExitPressedOnce = true;
			Toast.makeText(this, "Press Back again to Exit", Toast.LENGTH_SHORT).show();

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					doubleBackToExitPressedOnce=false;
				}
			}, 2000);
	    }
	}



	public enum AppStart {
		FIRST_TIME, FIRST_TIME_VERSION, NORMAL;
	}

	private static final String LAST_APP_VERSION = "last_app_version";

	public AppStart checkAppStart() {
		PackageInfo pInfo;
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		AppStart appStart = AppStart.NORMAL;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			int lastVersionCode = sharedPreferences
					.getInt(LAST_APP_VERSION, -1);
			int currentVersionCode = pInfo.versionCode;
			appStart = checkAppStart(currentVersionCode, lastVersionCode);
			// Update version in preferences
			sharedPreferences.edit()
					.putInt(LAST_APP_VERSION, currentVersionCode).commit();
		} catch (PackageManager.NameNotFoundException e) {
			Log.w("Warning",
					"Unable to determine current app version from pacakge manager. Defenisvely assuming normal app start.");
		}
		return appStart;
	}

	public AppStart checkAppStart(int currentVersionCode, int lastVersionCode) {
		if (lastVersionCode == -1) {
			return AppStart.FIRST_TIME;
		} else if (lastVersionCode < currentVersionCode) {
			return AppStart.FIRST_TIME_VERSION;
		} else if (lastVersionCode > currentVersionCode) {
			Log.w("Warning", "Current version code (" + currentVersionCode
					+ ") is less then the one recognized on last startup ("
					+ lastVersionCode
					+ "). Defenisvely assuming normal app start.");
			return AppStart.NORMAL;
		} else {
			return AppStart.NORMAL;
		}
	}

	// Reload Home
	public void reloadActivity() {
		Intent objIntent = new Intent(getApplicationContext(), Home.class);
		startActivity(objIntent);
	}

	//New Data
	public void newDataNotif() {

		// Set the alarm to start at approximately 7:00 a.m.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 7);

		// BroadCase Receiver Intent Object
		Intent alarmIntent = new Intent(getApplicationContext(), Async.class);
		// Pending Intent Object
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		// Alarm Manager Object
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		// Alarm Manager calls BroadCast for every Ten seconds (10 * 1000), BroadCase further calls service to check if new records are inserted in
		// Remote MySQL DB
		//alarmManager.setRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis() + 5000, 10 * 1000, pendingIntent);
		//alarmManager.setRepeating(Notification.DEFAULT_SOUND, Calendar.getInstance().getTimeInMillis() + 5000, 10 * 1000, pendingIntent);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

	}

}
