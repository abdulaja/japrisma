package com.asismedia.japrisma.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper{

    private static final String DB_NAME = "japrisma.db";
    private static final int DB_VERSION = 1;

    public static final String TBL_CAT = "category";
    public static final String COL_IDCAT = "id_category";
    public static final String COL_CAT = "category";

    public static final String TBL_AREA = "area";
    public static final String COL_IDAREA = "id_area";
    public static final String COL_AREA = "area";

    public static final String TBL_SUB = "subarea";
    public static final String COL_IDSUB = "id_sub";
    public static final String COL_IDAREASUB = "id_areasub";
    public static final String COL_SUB = "subarea";

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

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        // delete old database when upgrading
        setForcedUpgrade(DB_VERSION);
    }

}
