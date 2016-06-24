package com.asismedia.japrisma;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asismedia.japrisma.database.DataSource;
import com.asismedia.japrisma.database.UpdateData;
import com.asismedia.japrisma.ext.ConnectionDetector;
import com.asismedia.japrisma.json.CheckProcess;
import com.asismedia.japrisma.model.Tourism;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateFragment extends Fragment {

    ImageView checkUp, checkUpd, update, updated;
    TextView updateState;
    String sqlitetime, mysqltime;

    DataSource dataSource;
    private List<Tourism> time;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> checkResult;

    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
	
	public UpdateFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // creating connection detector class instance
        cd = new ConnectionDetector(getActivity());

        checkResult = new ArrayList<HashMap<String, String>>();
 
        View rootView = inflater.inflate(R.layout.fragment_update, container, false);

        updateState = (TextView) rootView.findViewById(R.id.updStat);

        checkUp = (ImageView) rootView.findViewById(R.id.ibCheckup);
        checkUpd = (ImageView) rootView.findViewById(R.id.ibCheckupd);
        update = (ImageView) rootView.findViewById(R.id.ibUpdate);
        updated = (ImageView) rootView.findViewById(R.id.ibUpdated);

        checkUpd.setVisibility(View.INVISIBLE);
        update.setVisibility(View.INVISIBLE);

        checkUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                // check for Internet status
                if (isInternetPresent) {
                    // Internet Connection is Present
                    // make HTTP requests
                    updateCheck();
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    AlertDialog.Builder check = new AlertDialog.Builder(getActivity());
                    check.setTitle("No Internet Connection")
                            .setMessage("You don't have internet connection.\nPlease check your connection.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });

        updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Please Check Update First", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get Internet status
                isInternetPresent = cd.isConnectingToInternet();

                // check for Internet status
                if (isInternetPresent) {
                    // Internet Connection is Present
                    // make HTTP requests
                    updateNotif();
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    AlertDialog.Builder check = new AlertDialog.Builder(getActivity());
                    check.setTitle("No Internet Connection")
                            .setMessage("You don't have internet connection.\nPlease check your connection.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
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

    public void updateNotif() {
        AlertDialog.Builder notif = new AlertDialog.Builder(getActivity());
        notif.setMessage("Data will be updated")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new UpdateData(getActivity()).execute();
                        updated.setVisibility(View.VISIBLE);
                        update.setVisibility(View.INVISIBLE);
                        checkUp.setVisibility(View.INVISIBLE);
                        checkUpd.setVisibility(View.VISIBLE);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void updateCheck() {
        new CheckProcess(getActivity()).execute();

    }

    public void onBackPressed() {
        Fragment home = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, home).commit();
    }

}
