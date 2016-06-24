package com.asismedia.japrisma.ext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.asismedia.japrisma.database.DataSource;
import com.asismedia.japrisma.model.Tourism;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Async extends BroadcastReceiver {
    static int noOfTimes = 0;
    DataSource dataSource;
    private List<Tourism> time;
    String sqlitetime, mysqltime, sqlite, mysql;
    //HashMap<String, String> data;

    @Override
    public void onReceive(final Context context, Intent intent) {

        noOfTimes++;
        //Toast.makeText(context, "Async Service Running for " + noOfTimes + " times", Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        dataSource = new DataSource(context);

        client.post("http://shiro.science/update/check", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                //System.out.println(response);
                try {
                    dataSource.open();
                    time = dataSource.getTime();
                    for (int i = 0; i < time.size(); i++) {
                        sqlitetime = time.get(i).getTime();
                    }
                    try {
                        JSONObject jobj = new JSONObject(response);
                        mysqltime = (String) jobj.get("time");
                        //System.out.println("mysql > " + mysqltime);
                        //.makeText(context, "mysql > " + mysqltime, Toast.LENGTH_LONG).show();
                        //Log.v("info", "mysql > " + mysqltime);
                        //Log.v("info", "sqlite > " + sqlitetime);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
                    try {
                        Date sqliteD = sdf.parse(sqlitetime);
                        Date mysqlD = sdf.parse(mysqltime);

                        if(sqliteD.after(mysqlD)){
                            //do nothing
                            //Toast.makeText(context, "Your Data is up-to-date", Toast.LENGTH_SHORT).show();
                        }

                        if(sqliteD.before(mysqlD)){
                            final Intent intnt = new Intent(context, AppNotif.class);
                            // Call MyService
                            context.startService(intnt);
                        }

                        if(sqliteD.equals(mysqlD)){
                            //do nothing
                            //Toast.makeText(context, "Your Data is up-to-date", Toast.LENGTH_SHORT).show();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if(statusCode == 404){
                    //Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
                }else if(statusCode == 500){
                    //Toast.makeText(context, "500", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(context, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
