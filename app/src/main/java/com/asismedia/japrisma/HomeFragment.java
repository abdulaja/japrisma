package com.asismedia.japrisma;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.asismedia.japrisma.adapter.CustomList;
import com.poliveira.apps.parallaxlistview.ParallaxListView;

public class HomeFragment extends Fragment {
	
	Home parent;
	//ImageView mImageView;
	
	private CharSequence mTitle;
	// nav drawer title
	private CharSequence mDrawerTitle;
	
	public HomeFragment(){}
	
	//ListView list;
    String[] menu = {
        "Touristic Spot",
        "Touristic Area",
        "Event",
        "Help",
        "About",
        "Update"
    } ;
    Integer[] icId = {
    		R.drawable.ic_people,
            R.drawable.ic_photos,
            R.drawable.ic_communities,
            R.drawable.help,
            R.drawable.about,
            R.drawable.refresh
 
    };
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        mTitle = mDrawerTitle = getActivity().getTitle();
        /*
        ListView list = (ListView)rootView.findViewById(R.id.listView1);
        CustomList adapter = new CustomList(getActivity(), menu, icId);
        list.setAdapter(adapter);
        
        list.setOnItemClickListener(new OnItemClickListener() {
            
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
        		displayView(position);
        		//Toast.makeText(getActivity(), "Under Development\nPlease use slide menu", Toast.LENGTH_SHORT).show();
        		
        		
        	}
    	});
        */
		ParallaxListView mListView = (ParallaxListView) rootView.findViewById(R.id.view);
		mListView.setParallaxView(inflater.inflate(R.layout.image_header, mListView, false));

		CustomList adapter = new CustomList(getActivity(), menu, icId);
		mListView.setAdapter(adapter);

		//mImageView = new ImageView(getActivity());
		//mListView.setVisibility(View.GONE);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				displayView(position);
			}
		});

        return rootView;
    }

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
			case 0:
				break;
			case 1:
				// Create new fragment and transaction
				fragment = new TouristicspotFragment();
				break;
			case 2:
				// Create new fragment and transaction
				fragment = new TouristicareaFragment();
				break;
			case 3:
				// Create new fragment and transaction
				fragment = new EventFragment();
				break;
			case 4:
				// Create new fragment and transaction
				fragment = new HelpFragment();
				break;
			case 5:
				// Create new fragment and transaction
				fragment = new AboutFragment();
				break;
			case 6:
				// Create new fragment and transaction
				fragment = new UpdateFragment();
				break;

			default:
				break;

		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			//parent.mDrawerList.setItemChecked(position, true);
			//parent.mDrawerList.setSelection(position);
			setTitle(menu[position - 1]);
			//mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActivity().setTitle(mTitle);
	}

}
