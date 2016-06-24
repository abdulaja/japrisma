package com.asismedia.japrisma.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper{
	 
	private static String db_path = null;

	public static final String db_name = "japrisma.db";
	public static final int db_version = 1;

	private final Context myContext;

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


	public static final String db_cat = "create table "
			+ TBL_CAT + "("
			+ COL_IDCAT + " integer primary key autoincrement, "
			+ COL_CAT + " varchar(50) not null);";

	public static final String db_area = "create table "
			+ TBL_AREA + "("
			+ COL_IDAREA + " integer primary key autoincrement, "
			+ COL_AREA + " varchar(50) not null);";

	public static final String db_sub = "create table "
			+ TBL_SUB + "("
			+ COL_IDSUB + " integer primary key autoincrement, "
			+ COL_IDAREASUB + " integer not null, "
			+ COL_SUB + " varchar(50) not null, "
			+ " FOREIGN KEY (" + COL_IDAREASUB + ") REFERENCES " + TBL_AREA + "(" + COL_IDAREA + ") );";

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
			+ " FOREIGN KEY (" + COL_IDTOURCAT + ") REFERENCES " + TBL_CAT + "(" + COL_IDCAT + "), "
			+ " FOREIGN KEY (" + COL_IDTOURAREA + ") REFERENCES " + TBL_AREA + "(" + COL_IDAREA + "), "
			+ " FOREIGN KEY (" + COL_IDTOURSUB + ") REFERENCES " + TBL_SUB + "(" + COL_IDSUB + ") ); ";


	public DBHelper(Context context) {
		super(context, db_name, null, db_version);
		this.myContext = context;
		db_path = "/data/data/"+context.getPackageName()+"/"+"databases/";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		//create category table
		db.execSQL(db_cat);
		//create area table
		db.execSQL(db_area);
		//create subarea table
		db.execSQL(db_sub);
		//create tourism table
		db.execSQL(db_tour);

		onInsert(db);

	}

	public void onInsert(SQLiteDatabase db) {
		//Insert data
		db.execSQL("INSERT INTO category (id_category, category) VALUES ('1','Touristic Spot');");
		db.execSQL("INSERT INTO category (id_category, category) VALUES ('2','Touristic Area');");
		db.execSQL("INSERT INTO category (id_category, category) VALUES ('3','Event');");

		//Insert data
		db.execSQL("INSERT INTO area (id_area, area) VALUES ('1','Chubu');");
		db.execSQL("INSERT INTO area (id_area, area) VALUES ('2','Chugoku');");
		db.execSQL("INSERT INTO area (id_area, area) VALUES ('3','Hokkaido');");
		db.execSQL("INSERT INTO area (id_area, area) VALUES ('4','Kanto');");
		db.execSQL("INSERT INTO area (id_area, area) VALUES ('5','Kinki');");
		db.execSQL("INSERT INTO area (id_area, area) VALUES ('6','Kyushu');");
		db.execSQL("INSERT INTO area (id_area, area) VALUES ('7','Shikoku');");
		db.execSQL("INSERT INTO area (id_area, area) VALUES ('8','Tohoku');");

		//Insert data
		db.execSQL("INSERT INTO subarea (id_sub, id_areasub, subarea) VALUES ('31','3','Hokkaido');");
		db.execSQL("INSERT INTO subarea (id_sub, id_areasub, subarea) VALUES ('41','4','Chiba');");

		//Insert data
		db.execSQL("INSERT INTO tourism (id_tourism, id_tourcat, id_tourarea, id_subarea, name, indescript, ininfo, endescript, eninfo, image) " +
				" VALUES ('1','2','3','31','Fuji Coba A', 'Ini adalah sebuah fuji coba untuk menggapai kesuksesan','Informasi masih dirahasiakan', 'Ini adalah sebuah fuji coba untuk menggapai kesuksesan','Informasi masih dirahasiakan','fujiv.jpg');");
		db.execSQL("INSERT INTO tourism (id_tourism, id_tourcat, id_tourarea, id_subarea, name, indescript, ininfo, endescript, eninfo, image) " +
				" VALUES ('2','1','4','41','Jozankei-onsen S','Ini adalah tempat sumber air panas yang mantaf gan','Informasi sangat rahasia','Ini adalah tempat sumber air panas yang mantaf gan','Informasi sangat rahasia','sapporov2.jpg');");
		db.execSQL("INSERT INTO tourism (id_tourism, id_tourcat, id_tourarea, id_subarea, name, indescript, ininfo, endescript, eninfo, image) " +
				" VALUES ('3','1','3','31','Fuji Coba S', 'Ini adalah sebuah fuji coba untuk menggapai kesuksesan','Informasi masih dirahasiakan', 'Ini adalah sebuah fuji coba untuk menggapai kesuksesan','Informasi masih dirahasiakan','fujiv.jpg');");
		db.execSQL("INSERT INTO tourism (id_tourism, id_tourcat, id_tourarea, id_subarea, name, indescript, ininfo, endescript, eninfo, image) " +
				" VALUES ('4','2','4','41','Jozankei-onsen A','Ini adalah tempat sumber air panas yang mantaf gan','Informasi sangat rahasia','Ini adalah tempat sumber air panas yang mantaf gan','Informasi sangat rahasia','sapporov2.jpg');");

		db.execSQL("INSERT INTO tourism (id_tourism, id_tourcat, id_tourarea, id_subarea, name, indescript, ininfo, endescript, eninfo, image) " +
				" VALUES ('5','1','4','41','Japan-onsen C','Ini adalah tempat sumber air panas yang mantaf gan','Informasi sangat rahasia','Ini adalah tempat sumber air panas yang mantaf gan','Informasi sangat rahasia','sapporov2.jpg');");
		db.execSQL("INSERT INTO tourism (id_tourism, id_tourcat, id_tourarea, id_subarea, name, indescript, ininfo, endescript, eninfo, image) " +
				" VALUES ('6','1','3','31','Fuji Mountain D', 'Ini adalah sebuah fuji coba untuk menggapai kesuksesan','Informasi masih dirahasiakan', 'Ini adalah sebuah fuji coba untuk menggapai kesuksesan','Informasi masih dirahasiakan','fujiv.jpg');");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		//db.execSQL("DROP TABLE IF EXISTS " + TBL_CAT);
		//db.execSQL("DROP TABLE IF EXISTS " + TBL_AREA);
		//db.execSQL("DROP TABLE IF EXISTS " + TBL_SUB);
		db.execSQL("DROP TABLE IF EXISTS " + TBL_TOURISM);
		onCreateTour(db);

	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(), "Downgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		//db.execSQL("DROP TABLE IF EXISTS " + TBL_CAT);
		//db.execSQL("DROP TABLE IF EXISTS " + TBL_AREA);
		//db.execSQL("DROP TABLE IF EXISTS " + TBL_SUB);
		db.execSQL("DROP TABLE IF EXISTS " + TBL_TOURISM);
		onCreateTour(db);
	}

	public void onCreateTour(SQLiteDatabase db) {
		//create tourism table
		db.execSQL(db_tour);
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase(){

		File dbFile = new File(db_path + db_name);
		return dbFile.exists();
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException{

		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(db_name);

		// Path to the just created empty db
		String outFileName = db_path + db_name;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

}
