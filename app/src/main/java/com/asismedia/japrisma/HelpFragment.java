package com.asismedia.japrisma;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ms.square.android.expandabletextview.ExpandableTextView;

public class HelpFragment extends Fragment {
	
	public HelpFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_help, container, false);

        ((TextView) rootView.findViewById(R.id.bantuan).findViewById(R.id.title).findViewById(R.id.tv_title)).setText("Panduan Pengguna");
        ((TextView) rootView.findViewById(R.id.help).findViewById(R.id.title).findViewById(R.id.tv_title)).setText("User Guide");

        ExpandableTextView expTv1 = (ExpandableTextView) rootView.findViewById(R.id.bantuan)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv2 = (ExpandableTextView) rootView.findViewById(R.id.help)
                .findViewById(R.id.expand_text_view);

        expTv1.setText(getString(R.string.cbantuan));
        expTv2.setText(getString(R.string.chelp));

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("ADF0EA0DA64D412D5B3EE242FC0A0F01")
                .addTestDevice("AA0A433677481AA864239179C3205E1E")
                .build();
        mAdView.loadAd(adRequest);
         
        return rootView;
    }

}
