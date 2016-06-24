package com.asismedia.japrisma;

import java.sql.SQLException;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.asismedia.japrisma.adapter.MySimpleArrayAdapter;
import com.asismedia.japrisma.database.DataSource;
import com.asismedia.japrisma.model.Tourism;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SearchFragment extends Fragment {

    // Out custom adapter
    MySimpleArrayAdapter adapter;
	
	// contains our listview items
    //ArrayList<SearchFragment> listItems;
 
    // database
    private DataSource dataSource;
    
    // list of area titles
    //ArrayList<String> tourData;
    private List<Tourism> tourism, tempTourism;
    //private ArrayList<Area> area;
    
    ListView listView;

	public String Title;
    EditText txtsearch;

	public SearchFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        txtsearch = (EditText) rootView.findViewById(R.id.txtsearch);
        //onDestroyView();


        // We're getting our listView by the id
        listView = (ListView) rootView.findViewById(R.id.listsearch);

        dataSource = new DataSource(getActivity());
        //buka controller
        try {
            dataSource.open();
            //ambil semua data wisata
            tourism = dataSource.getAllTourism();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //ambil semua data wisata
        //tourism = dataSource.getAllTourism();
/*
        for(int i=0; i< tourism.size(); i++){
            Log.d("DataSource", "tourname : " + tourism.get(i).getName());

        }
*/
        //tourData = new ArrayList<String>();

        //masukkan data wisata ke array adapter
        //ArrayAdapter<Tourism> adapter = new ArrayAdapter<Tourism>(getActivity(), android.R.layout.simple_list_item_1, tourism);
        adapter = new MySimpleArrayAdapter(getActivity(), tourism);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setSelected(true);

        txtsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {

			    if (count < before) {
                    //we're deleting char so we need to reset the adapter data
                    adapter.resetData();

                }

                // When user changed the Text
                adapter.getFilter().filter(cs.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence cs, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable cs) {
                // TODO Auto-generated method stub
                /*
                String keyword = txtsearch.getText().toString().toLowerCase();
                adapter.filter(keyword);
                */
            }
        });


        /*
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                //Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                //Tourism tourism = (Tourism) parent.getItemAtPosition(position);

                //String tour = (String) parent.getItemAtPosition(position);
                //final String selection = listView.getItemAtPosition(position).toString();
                // Create new fragment and transaction
                Bundle bundle = new Bundle();
                bundle.putString("tourname", tourism.get(position).getName());
                bundle.putString("tourarea", tourism.get(position).getArea());
                bundle.putString("subarea", tourism.get(position).getSubarea());
                bundle.putString("indescript", tourism.get(position).getIndescript());
                bundle.putString("ininfo", tourism.get(position).getIninfo());
                bundle.putString("endescript", tourism.get(position).getEndescript());
                bundle.putString("eninfo", tourism.get(position).getEninfo());
                bundle.putString("image", tourism.get(position).getImage());

                //Log.d("Bundle ", "Data yang dibawa " + bundle);
                Fragment newFragment = new TourismDetail();
                newFragment.setArguments(bundle);
                //FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                //transaction.replace(R.id.search, newFragment);
                //transaction.addToBackStack(null);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, newFragment).addToBackStack(null).commit();

                // Commit the transaction
                //transaction.commit();

            }
        });
        */
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("ADF0EA0DA64D412D5B3EE242FC0A0F01")
                .addTestDevice("AA0A433677481AA864239179C3205E1E")
                .build();
        mAdView.loadAd(adRequest);

        return rootView;
    }


}
