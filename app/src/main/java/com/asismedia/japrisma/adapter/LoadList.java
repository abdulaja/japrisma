package com.asismedia.japrisma.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.asismedia.japrisma.database.DataSource;
import com.asismedia.japrisma.model.Tourism;

import java.sql.SQLException;
import java.util.List;

public class LoadList extends AsyncTask<String, Void, Tourism> {

    private ProgressDialog pDialog;
    private Context context;
    private Activity rootView;
    private DataSource dataSource;
    private List<Tourism> tourism;

    public LoadList(Activity rootView) {
        this.rootView = rootView;
        this.context = rootView.getBaseContext();
    }

    @Override
    protected  void onPreExecute() {
        super.onPreExecute();

        // Showing progress dialog
        pDialog = new ProgressDialog(rootView);
        pDialog.setMessage("Loading Data...\nPlease wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Tourism doInBackground(String... params) {

        dataSource = new DataSource(context);
        //buka controller
        try {
            dataSource.open();
            //ambil semua data wisata
            tourism = dataSource.getAllTourism();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Tourism tourism) {
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();


    }
}
