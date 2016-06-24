package com.asismedia.japrisma;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.asismedia.japrisma.adapter.MyExpandableListAdapter;
import com.asismedia.japrisma.database.DataSource;
import com.asismedia.japrisma.model.Tourism;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class TouristicareaFragment extends Fragment {
	
	//ExpandableListAdapter listAdapter;
	MyExpandableListAdapter listAdapter;
	private DataSource dataSource;
	ExpandableListView expListView;
	//List<String> listDataHeader;
	List<String> tourismHeader;
	//HashMap<String, List<String>> listDataChild;
	HashMap<String, List<Tourism>> tourismChild;

	// database
    
    //public void getCountry() {}
	
	public TouristicareaFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_touristicarea, container, false);
        
        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.expArea);

        // earlier
		dataSource = new DataSource(getActivity());
		//buka controller
		try {
			dataSource.open();
			//ambil semua data wisata
			fillData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        //fillData();
 
        
        // preparing list data
     	//prepareListData();

     	//listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        listAdapter = new MyExpandableListAdapter(getActivity().getBaseContext(), tourismHeader, tourismChild);
     	
     	// setting list adapter
     	expListView.setAdapter(listAdapter);

     	// Listview Group click listener
     	expListView.setOnGroupClickListener(new OnGroupClickListener() {

	     	@Override
	     	public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
	     		// Toast.makeText(getApplicationContext(),
	     		// "Group Clicked " + listDataHeader.get(groupPosition),
	     		// Toast.LENGTH_SHORT).show();
	     		return false;
	     	}
     	});

     	// Listview Group expanded listener
     	expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

     		@Override
     		public void onGroupExpand(int groupPosition) {
     		/*	Toast.makeText(getActivity(),
     				listDataHeader.get(groupPosition) + " is under development", Toast.LENGTH_SHORT).show();*/
     		}
     	});

     	// Listview Group collasped listener
     	expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

     		@Override
     		public void onGroupCollapse(int groupPosition) {
     			

     		}
     	});

     	// Listview on child click listener
     	expListView.setOnChildClickListener(new OnChildClickListener() {

     		@Override
     		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
     			// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "position: " + childPosition, Toast.LENGTH_SHORT).show();

				Bundle bundle = new Bundle();
				//bundle.putParcelable("tourname", value);
				bundle.putString("tourname", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getName());
				//Log.d("Bundle", "Data yang diangkut " + bundle);

				bundle.putString("tourarea", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getArea());
				bundle.putString("subarea", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getSubarea());
				bundle.putString("indescript", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getIndescript());
				bundle.putString("ininfo", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getIninfo());
				bundle.putString("endescript", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getEndescript());
				bundle.putString("eninfo", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getEninfo());
				bundle.putString("image", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getImage());



     			// Create new fragment and transaction
				Fragment newFragment = new TourismDetail();
				newFragment.setArguments(bundle);

				/*
        	    FragmentTransaction transaction = getFragmentManager().beginTransaction();

        	    // Replace whatever is in the fragment_container view with this fragment,
        	    // and add the transaction to the back stack
        	    transaction.replace(R.id.area, newFragment);
        	    transaction.addToBackStack(null);

        	    // Commit the transaction
        	    transaction.commit();
        	    */

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_out_left, R.animator.slide_in_right)
						.replace(R.id.frame_container, newFragment).addToBackStack(null).commit();

/*
     			Toast.makeText(
     				getActivity(),
     				tourismHeader.get(groupPosition)
     						+ " : "
     						+ tourismChild.get(
     								tourismHeader.get(groupPosition)).get(
     								childPosition) + " is under development" + childPosition, Toast.LENGTH_SHORT)
     				.show();
*/
     			return true;
     		}
     	});

		AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("ADF0EA0DA64D412D5B3EE242FC0A0F01")
				.addTestDevice("AA0A433677481AA864239179C3205E1E")
				.build();
		mAdView.loadAd(adRequest);
        
        return rootView;
    }
	

	private void fillData() {
		// set list adapter here

		final DataSource tourismHelper = new DataSource(getActivity());
		/*
		try {
			dataSource.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/

		final ArrayList<Tourism> listOfTourism = dataSource.getTourismArea();

		tourismHeader = new ArrayList<String>();
		tourismChild = new HashMap<String, List<Tourism>>();

		for (Tourism e : listOfTourism) {
			if (tourismChild.get(e.getArea()) == null) {
				tourismChild.put(e.getArea(), new ArrayList<Tourism>());
				tourismHeader.add(e.getArea());
			}
			tourismChild.get(e.getArea()).add(e);

		}

		dataSource.close();

	}

    
	/*
	 * Preparing the list data
	 */
	/*
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding head data
		listDataHeader.add("Chubu");
		listDataHeader.add("Cugoku");
		listDataHeader.add("Hokkaido");
		listDataHeader.add("Kanto");
		listDataHeader.add("Kinki");
		listDataHeader.add("Kyushu");
		listDataHeader.add("Sikoku");
		listDataHeader.add("Tohoku");


		// Adding child data
		List<String> a = new ArrayList<String>();
		a.add("The Shawshank Redemption");
		a.add("The Godfather");
		a.add("The Godfather: Part II");

		List<String> b = new ArrayList<String>();
		b.add("The Conjuring");
		b.add("Despicable Me 2");
		b.add("Turbo");

		List<String> c = new ArrayList<String>();
		c.add("2 Guns");
		c.add("The Smurfs 2");
		c.add("The Spectacular Now");
		
		List<String> d = new ArrayList<String>();
		d.add("The Conjuring");
		d.add("Despicable Me 2");
		d.add("Turbo");
		
		List<String> e = new ArrayList<String>();
		e.add("The Conjuring");
		e.add("Despicable Me 2");
		e.add("Turbo");
		
		List<String> f = new ArrayList<String>();
		f.add("The Conjuring");
		f.add("Despicable Me 2");
		f.add("Turbo");
		
		List<String> g = new ArrayList<String>();
		g.add("The Conjuring");
		g.add("Despicable Me 2");
		g.add("Turbo");
		
		List<String> h = new ArrayList<String>();
		h.add("The Conjuring");
		h.add("Despicable Me 2");
		h.add("Turbo");
		

		// Header, Child data
		listDataChild.put(listDataHeader.get(0), a);
		listDataChild.put(listDataHeader.get(1), b);
		listDataChild.put(listDataHeader.get(2), c);
		listDataChild.put(listDataHeader.get(3), d);
		listDataChild.put(listDataHeader.get(4), e);
		listDataChild.put(listDataHeader.get(5), f);
		listDataChild.put(listDataHeader.get(6), g);
		listDataChild.put(listDataHeader.get(7), h);
		
	}
	*/

}
