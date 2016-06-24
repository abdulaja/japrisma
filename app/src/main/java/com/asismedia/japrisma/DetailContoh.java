package com.asismedia.japrisma;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DetailContoh extends Fragment {
	
	TextView txt_descript;
	
	public DetailContoh(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		Bundle bundle = this.getArguments();
		String tourname = bundle.getString("tourname");

		Toast.makeText(getActivity(), "nama wisata : " + tourname, Toast.LENGTH_SHORT).show();
 
        View rootView = inflater.inflate(R.layout.contohdetail, container, false);

		/*
		((TextView) rootView.findViewById(R.id.deskripsi).findViewById(R.id.title)).setText("Deskripsi");
		((TextView) rootView.findViewById(R.id.informasi).findViewById(R.id.title)).setText("Informasi");
		((TextView) rootView.findViewById(R.id.description).findViewById(R.id.title)).setText("Description");
		((TextView) rootView.findViewById(R.id.information).findViewById(R.id.title)).setText("Information");

		ExpandableTextView expTv1 = (ExpandableTextView) rootView.findViewById(R.id.deskripsi)
				.findViewById(R.id.expand_text_view);
		ExpandableTextView expTv2 = (ExpandableTextView) rootView.findViewById(R.id.informasi)
				.findViewById(R.id.expand_text_view);
		ExpandableTextView expTv3 = (ExpandableTextView) rootView.findViewById(R.id.description)
				.findViewById(R.id.expand_text_view);
		ExpandableTextView expTv4 = (ExpandableTextView) rootView.findViewById(R.id.information)
				.findViewById(R.id.expand_text_view);


		expTv1.setText(getString(R.string.cdeskripsi));
		expTv2.setText(getString(R.string.cinfo));
		expTv3.setText(getString(R.string.cdeskripsi));
		expTv4.setText(getString(R.string.cinfo));
*/
         
        return rootView;
    }
	
	public void onBackPressed()
	{
	    FragmentManager fm = getActivity().getFragmentManager();
	    fm.popBackStack();
	}
	

	
}
