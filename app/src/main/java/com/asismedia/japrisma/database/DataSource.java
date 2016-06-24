package com.asismedia.japrisma.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.asismedia.japrisma.model.Tourism;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataSource {

    //inisialiasi SQLite Database
    private SQLiteDatabase database;

    //inisialisasi kelas DBHelper
    //private DBHelper dbHelper;

    private MyDatabase myDatabase;

    //ambil semua nama kolom tourism
/*
    private String[] allTourColumns = {
            DBHelper.COL_IDTOUR,
            DBHelper.COL_IDTOURCAT,
            DBHelper.COL_IDTOURAREA,
            DBHelper.COL_IDTOURSUB,
            DBHelper.COL_NAME,
            DBHelper.COL_INDES,
            DBHelper.COL_ININFO,
            DBHelper.COL_ENDES,
            DBHelper.COL_ENINFO,
            DBHelper.COL_IMAGE
    };
*/
    /*
    private String[] allTourColumns = {
            DBHelper.COL_IDTOUR,
            //DBHelper.COL_IDTOURCAT,
            //DBHelper.COL_IDTOURAREA,
            //DBHelper.COL_IDTOURSUB,
            DBHelper.COL_CAT,
            DBHelper.COL_NAME,
            DBHelper.COL_SUB,
            DBHelper.COL_AREA,
            DBHelper.COL_INDES,
            DBHelper.COL_ININFO,
            DBHelper.COL_ENDES,
            DBHelper.COL_ENINFO,
            DBHelper.COL_IMAGE
    };
    */

    //DBHelper diinstantiasi pada constructor
    public DataSource(Context context) {
        //dbHelper = new DBHelper(context);
        myDatabase = new MyDatabase(context);
    }

    //membuka/membuat sambungan baru ke database
    public void open() throws SQLException {
        //database = dbHelper.getReadableDatabase();
        database = myDatabase.getReadableDatabase();
        database = myDatabase.getWritableDatabase();
    }

    public void fullopen() throws SQLException {
        database = myDatabase.getWritableDatabase();
    }

    //menutup sambungan ke database
    public void close() {
        //dbHelper.close();
        myDatabase.close();
    }

    private Tourism cursorToTourism(Cursor cursor) {

        // buat objek tourism baru
        Tourism tourism = new Tourism();

        // debug LOGCAT
        //Log.v("info", "The getLONG " + cursor.getLong(0));
        //Log.v("info", "The setLatLng "+cursor.getString(5)+","+cursor.getString(6));

        /* Set atribut pada objek barang dengan
         * data kursor yang diambil dari database*/
        tourism.setId_tourism(cursor.getLong(0));
        //tourism.setId_category(cursor.getLong(1));
        //tourism.setId_area(cursor.getLong(2));
        //tourism.setId_subarea(cursor.getLong(3));
        tourism.setCategory(cursor.getString(1));
        tourism.setName(cursor.getString(2));
        tourism.setSubarea(cursor.getString(3));
        tourism.setArea(cursor.getString(4));
        tourism.setIndescript(cursor.getString(5));
        tourism.setIninfo(cursor.getString(6));
        tourism.setEndescript(cursor.getString(7));
        tourism.setEninfo(cursor.getString(8));
        tourism.setImage(cursor.getString(9));

        //kembalikan sebagai objek barang
        return tourism;
    }


/*
    //mengambil semua data wisata
    public List<Tourism> getAllTourism() {

        List<Tourism> tourismList = new ArrayList<Tourism>();

        Cursor cursor = database.rawQuery("SELECT "
                + DBHelper.COL_IDTOUR + ", "
                + DBHelper.COL_CAT + ", "
                + DBHelper.COL_NAME + ", "
                + DBHelper.COL_SUB + ", "
                + DBHelper.COL_AREA + ", "
                + DBHelper.COL_INDES + ", "
                + DBHelper.COL_ININFO + ", "
                + DBHelper.COL_ENDES + ", "
                + DBHelper.COL_ENINFO + ", "
                + DBHelper.COL_IMAGE +
                " FROM " + DBHelper.TBL_TOURISM +
                " INNER JOIN " + DBHelper.TBL_CAT + " ON " + DBHelper.COL_IDTOURCAT + " = " + DBHelper.COL_IDCAT +
                " INNER JOIN " + DBHelper.TBL_AREA + " ON " + DBHelper.COL_IDTOURAREA + " = " + DBHelper.COL_IDAREA +
                " INNER JOIN " + DBHelper.TBL_SUB + " ON " + DBHelper.COL_IDTOURSUB + " = " + DBHelper.COL_IDSUB +
                " ORDER BY " + DBHelper.COL_NAME + " ASC ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTourism(cursor);
            tourism.setId_tourism(cursor.getInt(0));
            tourism.setName(cursor.getString(2));
            //Log.d("DataSource", "Judul : " + cursor.getString(4));
            //Log.d("DataSource", "deskripsi : " + cursor.getString(5));
            //Log.d("DataSource", "informasi : " + cursor.getString(6));
            tourismList.add(tourism);
            cursor.moveToNext();
        }

        // Make sure to close the cursor
        cursor.close();
        return tourismList;
    }

    public ArrayList<Tourism> getTourismArea() {

        ArrayList<Tourism> tourismList = new ArrayList<Tourism>();

        // select all SQL query
        Cursor cursor = database.rawQuery("SELECT "
                + DBHelper.COL_IDTOUR + ", "
                + DBHelper.COL_CAT + ", "
                + DBHelper.COL_NAME + ", "
                + DBHelper.COL_SUB + ", "
                + DBHelper.COL_AREA + ", "
                + DBHelper.COL_INDES + ", "
                + DBHelper.COL_ININFO + ", "
                + DBHelper.COL_ENDES + ", "
                + DBHelper.COL_ENINFO + ", "
                + DBHelper.COL_IMAGE +
                " FROM " + DBHelper.TBL_TOURISM +
                " INNER JOIN " + DBHelper.TBL_CAT + " ON " + DBHelper.COL_IDTOURCAT + " = " + DBHelper.COL_IDCAT +
                " INNER JOIN " + DBHelper.TBL_AREA + " ON " + DBHelper.COL_IDTOURAREA + " = " + DBHelper.COL_IDAREA +
                " INNER JOIN " + DBHelper.TBL_SUB + " ON " + DBHelper.COL_IDTOURSUB + " = " + DBHelper.COL_IDSUB +
                " WHERE " + DBHelper.COL_IDTOURCAT + " = 2 " +
                " ORDER BY " + DBHelper.COL_NAME + " ASC ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTourism(cursor);
            tourism.setId_tourism(cursor.getInt(0));
            tourism.setName(cursor.getString(2));
            tourismList.add(tourism);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return tourismList;
    }

    public ArrayList<Tourism> getTourismSpot() {

        ArrayList<Tourism> tourismList = new ArrayList<Tourism>();

        // select all SQL query
        Cursor cursor = database.rawQuery("SELECT "
                + DBHelper.COL_IDTOUR + ", "
                + DBHelper.COL_CAT + ", "
                + DBHelper.COL_NAME + ", "
                + DBHelper.COL_SUB + ", "
                + DBHelper.COL_AREA + ", "
                + DBHelper.COL_INDES + ", "
                + DBHelper.COL_ININFO + ", "
                + DBHelper.COL_ENDES + ", "
                + DBHelper.COL_ENINFO + ", "
                + DBHelper.COL_IMAGE +
                " FROM " + DBHelper.TBL_TOURISM +
                " INNER JOIN " + DBHelper.TBL_CAT + " ON " + DBHelper.COL_IDTOURCAT + " = " + DBHelper.COL_IDCAT +
                " INNER JOIN " + DBHelper.TBL_AREA + " ON " + DBHelper.COL_IDTOURAREA + " = " + DBHelper.COL_IDAREA +
                " INNER JOIN " + DBHelper.TBL_SUB + " ON " + DBHelper.COL_IDTOURSUB + " = " + DBHelper.COL_IDSUB +
                " WHERE " + DBHelper.COL_IDTOURCAT + " = 1 " +
                " ORDER BY " + DBHelper.COL_NAME + " ASC ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTourism(cursor);
            tourism.setId_tourism(cursor.getInt(0));
            tourism.setName(cursor.getString(2));
            tourismList.add(tourism);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return tourismList;
    }

    public ArrayList<Tourism> getEvent() {

        ArrayList<Tourism> tourismList = new ArrayList<Tourism>();

        // select all SQL query
        Cursor cursor = database.rawQuery("SELECT "
                + DBHelper.COL_IDTOUR + ", "
                + DBHelper.COL_CAT + ", "
                + DBHelper.COL_NAME + ", "
                + DBHelper.COL_SUB + ", "
                + DBHelper.COL_AREA + ", "
                + DBHelper.COL_INDES + ", "
                + DBHelper.COL_ININFO + ", "
                + DBHelper.COL_ENDES + ", "
                + DBHelper.COL_ENINFO + ", "
                + DBHelper.COL_IMAGE +
                " FROM " + DBHelper.TBL_TOURISM +
                " INNER JOIN " + DBHelper.TBL_CAT + " ON " + DBHelper.COL_IDTOURCAT + " = " + DBHelper.COL_IDCAT +
                " INNER JOIN " + DBHelper.TBL_AREA + " ON " + DBHelper.COL_IDTOURAREA + " = " + DBHelper.COL_IDAREA +
                " INNER JOIN " + DBHelper.TBL_SUB + " ON " + DBHelper.COL_IDTOURSUB + " = " + DBHelper.COL_IDSUB +
                " WHERE " + DBHelper.COL_IDTOURCAT + " = 3 " +
                " ORDER BY " + DBHelper.COL_NAME + " ASC ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTourism(cursor);
            tourism.setId_tourism(cursor.getInt(0));
            tourism.setName(cursor.getString(2));
            tourismList.add(tourism);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return tourismList;
    }
*/


    //mengambil semua data wisata
    public List<Tourism> getAllTourism() {

        List<Tourism> tourismList = new ArrayList<Tourism>();

        Cursor cursor = database.rawQuery("SELECT "
                + MyDatabase.COL_IDTOUR + ", "
                + MyDatabase.COL_CAT + ", "
                + MyDatabase.COL_NAME + ", "
                + MyDatabase.COL_SUB + ", "
                + MyDatabase.COL_AREA + ", "
                + MyDatabase.COL_INDES + ", "
                + MyDatabase.COL_ININFO + ", "
                + MyDatabase.COL_ENDES + ", "
                + MyDatabase.COL_ENINFO + ", "
                + MyDatabase.COL_IMAGE +
                " FROM " + MyDatabase.TBL_TOURISM +
                " INNER JOIN " + MyDatabase.TBL_CAT + " ON " + MyDatabase.COL_IDTOURCAT + " = " + MyDatabase.COL_IDCAT +
                " INNER JOIN " + MyDatabase.TBL_AREA + " ON " + MyDatabase.COL_IDTOURAREA + " = " + MyDatabase.COL_IDAREA +
                " INNER JOIN " + MyDatabase.TBL_SUB + " ON " + MyDatabase.COL_IDTOURSUB + " = " + MyDatabase.COL_IDSUB +
                " ORDER BY " + MyDatabase.COL_NAME + " ASC ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTourism(cursor);
            tourism.setId_tourism(cursor.getInt(0));
            tourism.setName(cursor.getString(2));
            tourismList.add(tourism);
            cursor.moveToNext();
        }

        // Make sure to close the cursor
        cursor.close();
        close();
        return tourismList;
    }

    public ArrayList<Tourism> getTourismArea() {

        ArrayList<Tourism> tourismList = new ArrayList<Tourism>();

        // select all SQL query
        Cursor cursor = database.rawQuery("SELECT "
                + MyDatabase.COL_IDTOUR + ", "
                + MyDatabase.COL_CAT + ", "
                + MyDatabase.COL_NAME + ", "
                + MyDatabase.COL_SUB + ", "
                + MyDatabase.COL_AREA + ", "
                + MyDatabase.COL_INDES + ", "
                + MyDatabase.COL_ININFO + ", "
                + MyDatabase.COL_ENDES + ", "
                + MyDatabase.COL_ENINFO + ", "
                + MyDatabase.COL_IMAGE +
                " FROM " + MyDatabase.TBL_TOURISM +
                " INNER JOIN " + MyDatabase.TBL_CAT + " ON " + MyDatabase.COL_IDTOURCAT + " = " + MyDatabase.COL_IDCAT +
                " INNER JOIN " + MyDatabase.TBL_AREA + " ON " + MyDatabase.COL_IDTOURAREA + " = " + MyDatabase.COL_IDAREA +
                " INNER JOIN " + MyDatabase.TBL_SUB + " ON " + MyDatabase.COL_IDTOURSUB + " = " + MyDatabase.COL_IDSUB +
                " WHERE " + MyDatabase.COL_IDTOURCAT + " = 2 " +
                " ORDER BY " + MyDatabase.COL_AREA + " ASC ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTourism(cursor);
            tourism.setId_tourism(cursor.getInt(0));
            tourism.setName(cursor.getString(2));
            tourismList.add(tourism);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        close();
        return tourismList;
    }

    public ArrayList<Tourism> getTourismSpot() {

        ArrayList<Tourism> tourismList = new ArrayList<Tourism>();

        // select all SQL query
        Cursor cursor = database.rawQuery("SELECT "
                + MyDatabase.COL_IDTOUR + ", "
                + MyDatabase.COL_CAT + ", "
                + MyDatabase.COL_NAME + ", "
                + MyDatabase.COL_SUB + ", "
                + MyDatabase.COL_AREA + ", "
                + MyDatabase.COL_INDES + ", "
                + MyDatabase.COL_ININFO + ", "
                + MyDatabase.COL_ENDES + ", "
                + MyDatabase.COL_ENINFO + ", "
                + MyDatabase.COL_IMAGE +
                " FROM " + MyDatabase.TBL_TOURISM +
                " INNER JOIN " + MyDatabase.TBL_CAT + " ON " + MyDatabase.COL_IDTOURCAT + " = " + MyDatabase.COL_IDCAT +
                " INNER JOIN " + MyDatabase.TBL_AREA + " ON " + MyDatabase.COL_IDTOURAREA + " = " + MyDatabase.COL_IDAREA +
                " INNER JOIN " + MyDatabase.TBL_SUB + " ON " + MyDatabase.COL_IDTOURSUB + " = " + MyDatabase.COL_IDSUB +
                " WHERE " + MyDatabase.COL_IDTOURCAT + " = 1 " +
                " ORDER BY " + MyDatabase.COL_AREA + " ASC ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTourism(cursor);
            tourism.setId_tourism(cursor.getInt(0));
            tourism.setName(cursor.getString(2));
            tourismList.add(tourism);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        close();
        return tourismList;
    }

    public ArrayList<Tourism> getEvent() {

        ArrayList<Tourism> tourismList = new ArrayList<Tourism>();

        // select all SQL query
        Cursor cursor = database.rawQuery("SELECT "
                + MyDatabase.COL_IDTOUR + ", "
                + MyDatabase.COL_CAT + ", "
                + MyDatabase.COL_NAME + ", "
                + MyDatabase.COL_SUB + ", "
                + MyDatabase.COL_AREA + ", "
                + MyDatabase.COL_INDES + ", "
                + MyDatabase.COL_ININFO + ", "
                + MyDatabase.COL_ENDES + ", "
                + MyDatabase.COL_ENINFO + ", "
                + MyDatabase.COL_IMAGE +
                " FROM " + MyDatabase.TBL_TOURISM +
                " INNER JOIN " + MyDatabase.TBL_CAT + " ON " + MyDatabase.COL_IDTOURCAT + " = " + MyDatabase.COL_IDCAT +
                " INNER JOIN " + MyDatabase.TBL_AREA + " ON " + MyDatabase.COL_IDTOURAREA + " = " + MyDatabase.COL_IDAREA +
                " INNER JOIN " + MyDatabase.TBL_SUB + " ON " + MyDatabase.COL_IDTOURSUB + " = " + MyDatabase.COL_IDSUB +
                " WHERE " + MyDatabase.COL_IDTOURCAT + " = 3 " +
                " ORDER BY " + MyDatabase.COL_AREA + " ASC ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTourism(cursor);
            tourism.setId_tourism(cursor.getInt(0));
            tourism.setName(cursor.getString(2));
            tourismList.add(tourism);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        close();
        return tourismList;
    }


    public boolean isSdReadable() {

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
        // We can read and write the media
            mExternalStorageAvailable = true;
            Log.i("isSdReadable", "External storage card is readable.");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
        // We can only read the media
            Log.i("isSdReadable", "External storage card is readable.");
            mExternalStorageAvailable = true;
        } else {
        // Something else is wrong. It may be one of many other
        // states, but all we need to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }

        return mExternalStorageAvailable;
    }


    //for update sqlite data from mysql
    public static final String TBL_CAT = "category";
    public static final String COL_IDCAT = "id_category";

    public static final String TBL_AREA = "area";
    public static final String COL_IDAREA = "id_area";

    public static final String TBL_SUB = "subarea";
    public static final String COL_IDSUB = "id_sub";

    public static final String TBL_TOURISM = "tourism";
    public static final String COL_IDTOUR = "id_tourism";
    public static final String COL_IDTOURCAT = "id_tourcat";
    public static final String COL_IDTOURAREA = "id_tourarea";
    public static final String COL_IDTOURSUB = "id_subarea";
    public static final String COL_NAME = "name";
    public static final String COL_INDES = "indescript";
    public static final String COL_ININFO = "ininfo";
    public static final String COL_ENDES = "endescript";
    public static final String COL_ENINFO = "eninfo";
    public static final String COL_IMAGE = "image";
    public static final String COL_TIME = "time";

    public static final String db_tour = "create table "
            + TBL_TOURISM + "("
            + COL_IDTOUR + " integer primary key autoincrement, "
            + COL_IDTOURCAT + " integer not null, "
            + COL_IDTOURAREA + " integer not null, "
            + COL_IDTOURSUB + " integer not null, "
            + COL_NAME + " varchar(100) not null, "
            + COL_INDES + " text, "
            + COL_ININFO + " text, "
            + COL_ENDES + " text, "
            + COL_ENINFO + " text, "
            + COL_IMAGE + " varchar(256), "
            + COL_TIME + " datetime, "
            + " FOREIGN KEY (" + COL_IDTOURCAT + ") REFERENCES " + TBL_CAT + "(" + COL_IDCAT + "), "
            + " FOREIGN KEY (" + COL_IDTOURAREA + ") REFERENCES " + TBL_AREA + "(" + COL_IDAREA + "), "
            + " FOREIGN KEY (" + COL_IDTOURSUB + ") REFERENCES " + TBL_SUB + "(" + COL_IDSUB + ") ); ";

    public void updateTourism() {
        database.execSQL("DROP TABLE IF EXISTS tourism");
        database.execSQL(db_tour);
        //Log.d("buat tabel ", "updateTourism success");
    }

    public void addTour(HashMap<String, String > data) {
        database = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tourism", data.get("id_tourism"));
        values.put("id_tourcat", data.get("id_tourcat"));
        values.put("id_tourarea", data.get("id_tourarea"));
        values.put("id_subarea", data.get("id_toursub"));
        values.put("name", data.get("tourname"));
        values.put("indescript", data.get("indescript"));
        values.put("ininfo", data.get("ininfo"));
        values.put("endescript", data.get("endescript"));
        values.put("eninfo", data.get("eninfo"));
        values.put("image", data.get("image"));
        values.put("time", data.get("time"));
        database.insert("tourism", null, values);
        database.close();

    }


    private Tourism cursorToTime(Cursor cursor) {

        // buat objek tourism baru
        Tourism tourism = new Tourism();

        // debug LOGCAT
        //Log.v("info", "The getLONG " + cursor.getLong(0));
        //Log.v("info", "The setLatLng "+cursor.getString(5)+","+cursor.getString(6));

        /* Set atribut pada objek barang dengan
         * data kursor yang diambil dari database*/

        tourism.setTime(cursor.getString(10));

        //kembalikan sebagai objek barang
        return tourism;
    }

    //mengambil data waktu
    public List<Tourism> getTime() {

        List<Tourism> tourismList = new ArrayList<Tourism>();

        Cursor cursor = database.rawQuery("SELECT "
                + MyDatabase.COL_IDTOUR + ", "
                + MyDatabase.COL_CAT + ", "
                + MyDatabase.COL_NAME + ", "
                + MyDatabase.COL_SUB + ", "
                + MyDatabase.COL_AREA + ", "
                + MyDatabase.COL_INDES + ", "
                + MyDatabase.COL_ININFO + ", "
                + MyDatabase.COL_ENDES + ", "
                + MyDatabase.COL_ENINFO + ", "
                + MyDatabase.COL_IMAGE + ", "
                + MyDatabase.COL_TIME +
                " FROM " + MyDatabase.TBL_TOURISM +
                " INNER JOIN " + MyDatabase.TBL_CAT + " ON " + MyDatabase.COL_IDTOURCAT + " = " + MyDatabase.COL_IDCAT +
                " INNER JOIN " + MyDatabase.TBL_AREA + " ON " + MyDatabase.COL_IDTOURAREA + " = " + MyDatabase.COL_IDAREA +
                " INNER JOIN " + MyDatabase.TBL_SUB + " ON " + MyDatabase.COL_IDTOURSUB + " = " + MyDatabase.COL_IDSUB +
                " ORDER BY " + MyDatabase.COL_TIME + " DESC LIMIT 1 ", null );


        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data wisata ke
        // daftar wisata
        while (!cursor.isAfterLast()) {
            Tourism tourism = cursorToTime(cursor);
            //Log.d("DataSource", "Time : " + cursor.getString(10));
            tourismList.add(tourism);
            cursor.moveToNext();
        }

        // Make sure to close the cursor
        cursor.close();
        close();
        return tourismList;
    }
}
