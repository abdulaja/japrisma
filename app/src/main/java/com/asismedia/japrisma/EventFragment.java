package com.asismedia.japrisma;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.asismedia.japrisma.adapter.MyExpandableListAdapter;
import com.asismedia.japrisma.database.DataSource;
import com.asismedia.japrisma.model.Tourism;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventFragment extends Fragment {

    private DataSource dataSource;

    MyExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> tourismHeader;
    HashMap<String, List<Tourism>> tourismChild;

    ListView listView;
	
	public EventFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        // get the expandable listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.expEvent);

        //get datasource
        dataSource = new DataSource(getActivity());

        //buka controller
        try {
            dataSource.open();
            //ambil semua data wisata
            fillData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // setting list adapter
        listAdapter = new MyExpandableListAdapter(getActivity(), tourismHeader, tourismChild);
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getActivity(),
                //listDataHeader.get(groupPosition) + " is under development", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                Bundle bundle = new Bundle();
                bundle.putString("tourname", tourismChild.get(tourismHeader.get(groupPosition)).get(childPosition).getName());
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

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_out_left, R.animator.slide_in_right)
                        .replace(R.id.frame_container, newFragment).addToBackStack(null).commit();

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
        final ArrayList<Tourism> listOfTourism = dataSource.getEvent();

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

}
