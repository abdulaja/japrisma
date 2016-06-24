package com.asismedia.japrisma.json;

import android.content.Context;

import com.asismedia.japrisma.UpdateFragment;
import com.asismedia.japrisma.database.DataSource;
import com.asismedia.japrisma.model.Tourism;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Abdulaja on 17/09/2015.
 */
public class CheckUpdate {
    private static String url = "http://shiro.science/update/check";

    DataSource dataSource;
    HashMap<String, String> data;
    private List<Tourism> time;
    String sqlitetime;
    String mysqltime;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> checkResult;

    public boolean CheckforUpdate(Context context) {
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

                UpdateFragment uf = new UpdateFragment();
                checkResult.add(data);

                /*
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");

                    Date sqlite = sdf.parse(sqlitetime);
                    Date mysql = sdf.parse(mysqltime);

                    //System.out.println(sdf.format(sqlite));
                    //System.out.println(sdf.format(mysql));

                    if(sqlite.after(mysql)){
                        System.out.println("No Update Available");
                        //Toast.makeText(context, "No Update Available", Toast.LENGTH_SHORT).show();
                    }

                    if(sqlite.before(mysql)){
                        System.out.println("Data Update is Available");
                        //Toast.makeText(context, "Data Update is Available", Toast.LENGTH_SHORT).show();
                    }

                    if(sqlite.equals(mysql)){
                        System.out.println("Your Data is up-to-date");
                        //Toast.makeText(context, "Your Data is up-to-date", Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                */

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }
}
