package com.asismedia.japrisma.json;

import android.content.Context;
import android.util.Log;

import com.asismedia.japrisma.database.DataSource;


import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateSqlite {

    private static String url = "http://shiro.science/update";
    //private static String url = "http://10.0.3.2/japrisma/update";

    DataSource dataSource;
    HashMap<String, String> data;

    public boolean UpdateTourism(Context context, boolean update_db) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        //Log.d("Response: ", "> " + jsonStr);

        dataSource = new DataSource(context);

        if (jsonStr != null) {

            try {
                dataSource.fullopen();

                if (update_db) {
                    dataSource.updateTourism();
                    //dataSource.insertTourism();

                    try {
                        JSONObject jobj = new JSONObject(jsonStr);
                        JSONArray jray = jobj.getJSONArray("tourism");
                        //System.out.println(jray.length());
                        // If no of array elements is not zero
                        if(jray.length() != 0) {
                            // Loop through each array element, get JSON object which has userid and username
                            for (int i = 0; i < jray.length(); i++) {
                                JSONObject ab = jray.getJSONObject(i);

                                String id_tour = ab.getString("id_tourism");
                                String id_tourcat = ab.getString("id_tourcat");
                                String id_tourarea = ab.getString("id_tourarea");
                                String id_toursub = ab.getString("id_subarea");
                                String tourname = ab.getString("name");
                                String indescript = ab.getString("indescript");
                                String inifo = ab.getString("ininfo");
                                String endescript = ab.getString("endescript");
                                String eninfo = ab.getString("eninfo");
                                String image = ab.getString("image");
                                String time = ab.getString("time");

                                data = new HashMap<String, String>();
                                data.put("id_tourism", id_tour);
                                data.put("id_tourcat", id_tourcat);
                                data.put("id_tourarea", id_tourarea);
                                data.put("id_toursub", id_toursub);
                                data.put("tourname", tourname);
                                data.put("indescript", indescript);
                                data.put("ininfo", inifo);
                                data.put("endescript", endescript);
                                data.put("eninfo", eninfo);
                                data.put("image", image);
                                data.put("time", time);

                                dataSource.addTour(data);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        dataSource.close();

        return true;
    }


}
