package com.asismedia.japrisma.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.asismedia.japrisma.R;
import com.asismedia.japrisma.json.UpdateSqlite;

public class UpdateData extends AsyncTask<Void, Void, Void> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    private ProgressDialog pDialog;

    private Context context;
    private Activity rootView;

    public UpdateData(Activity rootView) {
        this.rootView = rootView;
        this.context = rootView;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Downloading file..");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

    }

    @Override
    protected  void onPreExecute() {
        super.onPreExecute();

        //create a progress dialog
        //mProgressDialog.show();

        // Showing progress dialog

        pDialog = new ProgressDialog(rootView);
        pDialog.setMessage("Downloading file...\nPlease wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


    }


    @Override
    protected Void doInBackground(Void... arg0) {

        UpdateSqlite updateSqlite = new UpdateSqlite();
        updateSqlite.UpdateTourism(rootView, true);

        return null;
    }

/*
    protected void onProgressUpdate(String... progress) {
        //Log.d("ANDRO_ASYNC", progress[0]);
        mProgressDialog.setProgress(Integer.parseInt(progress[0]));
    }
*/

    @Override
    protected void onPostExecute(Void args) {
        Toast.makeText(context, "Update Finished", Toast.LENGTH_SHORT).show();

        TextView updateState = (TextView) rootView.findViewById(R.id.updStat);
        updateState.setText("Data Update is Complete ");
        //mProgressDialog.dismiss();

        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
