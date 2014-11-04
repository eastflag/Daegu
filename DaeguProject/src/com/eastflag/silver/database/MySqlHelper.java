package com.eastflag.silver.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlHelper extends SQLiteOpenHelper{

	  public static final String TABLE_BASEBALL = "baseball";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_TIME = "time";
	  public static final String COLUMN_HIT = "hit";
	  public static final String COLUMN_DATE = "created_date";

	  private static final String DATABASE_NAME = "silver.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table " + TABLE_BASEBALL + "(" 
			  + COLUMN_ID + " integer primary key autoincrement, " 
			  + COLUMN_TIME + " integer not null, "
			  + COLUMN_HIT + " integer not null, "
			  + COLUMN_DATE + " text not null "
			  + ");";

	  public MySqlHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySqlHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_BASEBALL);
	    onCreate(db);
	  }

}
