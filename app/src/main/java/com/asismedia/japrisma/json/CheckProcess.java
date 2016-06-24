package com.asismedia.japrisma.json;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asismedia.japrisma.R;
import com.asismedia.japrisma.database.DataSource;
import com.asismedia.japrisma.model.Tourism;
import com.asismedia.japrisma.model.Wrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CheckProcess extends AsyncTask<String, Void, Wrapper> {

    private ProgressDialog pDialog;
    private Context context;
    private static String url = "http://shiro.science/update/check";
    DataSource dataSource;
    HashMap<String, String> data;
    private List<Tourism> time;
    String sqlitetime, mysqltime, sqlite, mysql;
    private Activity rootView;
    ImageView checkUp, checkUpd, update, updated;

    public CheckProcess(Activity rootView) {
        this.rootView = rootView;
        this.context = rootView.getBaseContext();
    }

    @Override
    protected  void onPreExecute() {
        super.onPreExecute();

        // Showing progress dialog
        pDialog = new ProgressDialog(rootView);
        pDialog.setMessage("Checking Update...\nPlease wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    protected Wrapper doInBackground(String... params) {

        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        //Log.d("Response: ", "> " + jsonStr);

        dataSource = new DataSource(context);

        try {
            dataSource.open();
            time = dataSource.getTime();
            for (int i = 0; i < time.size(); i++) {
                //Log.d("Time : ", "> " + time.get(i).getTime());
                sqlitetime = time.get(i).getTime();
                //System.out.println("sqlite > " + sqlitetime);
            }
            try {
                JSONObject jobj = new JSONObject(jsonStr);
                mysqltime = (String) jobj.get("time");
                //System.out.println("mysql > " + mysqltime);

                data = new HashMap<String, String>();
                data.put("sqlite", sqlitetime);
                data.put("mysql", mysqltime);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Wrapper w = new Wrapper();
        w.sqlite = sqlitetime;
        w.mysql = mysqltime;

        return w;
    }

    @Override
    protected void onPostExecute(Wrapper w) {
        //Toast.makeText(context, "Checking Complete", Toast.LENGTH_SHORT).show();

        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();

        sqlite = w.sqlite;
        mysql = w.mysql;

        TextView updateState = (TextView) rootView.findViewById(R.id.updStat);

        checkUp = (ImageView) rootView.findViewById(R.id.ibCheckup);
        checkUpd = (ImageView) rootView.findViewById(R.id.ibCheckupd);
        update = (ImageView) rootView.findViewById(R.id.ibUpdate);
        updated = (ImageView) rootView.findViewById(R.id.ibUpdated);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");

            Date sqliteD = sdf.parse(sqlite);
            Date mysqlD = sdf.parse(mysql);

            //System.out.println(sdf.format(sqlite));
            //System.out.println(sdf.format(mysql));

            if(sqliteD.after(mysqlD)){
                //System.out.println("No Update Available");
                updateState.setText("No Update Available");
                //Toast.makeText(context, "No Update Available", Toast.LENGTH_SHORT).show();
                checkUp.setVisibility(View.INVISIBLE);
                checkUpd.setVisibility(View.VISIBLE);

            }

            if(sqliteD.before(mysqlD)){
                //System.out.println("Data Update is Available");
                updateState.setText("Data Update is Available");
                //Toast.makeText(context, "Data Update is Available", Toast.LENGTH_SHORT).show();
                updated.setVisibility(View.INVISIBLE);
                update.setVisibility(View.VISIBLE);
                checkUp.setVisibility(View.INVISIBLE);
                checkUpd.setVisibility(View.VISIBLE);

            }

            if(sqliteD.equals(mysqlD)){
                //System.out.println("Your Data is up-to-date");
                updateState.setText("Your Data is up-to-date");
                //Toast.makeText(context, "Your Data is up-to-date", Toast.LENGTH_SHORT).show();
                checkUp.setVisibility(View.INVISIBLE);
                checkUpd.setVisibility(View.VISIBLE);

            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }

}
