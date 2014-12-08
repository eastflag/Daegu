package com.eastflag.silverg.database;

import java.util.ArrayList;

import com.eastflag.silverg.dto.BaseballVO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MySqlController {
	// Database fields
	private SQLiteDatabase database;
	private MySqlHelper dbHelper;
	private String[] allColumns = { MySqlHelper.COLUMN_ID,
			MySqlHelper.COLUMN_TIME, MySqlHelper.COLUMN_HIT,
			MySqlHelper.COLUMN_DATE };

	public MySqlController(Context context) {
		dbHelper = new MySqlHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public long insertBaseball(BaseballVO baseball) {
		ContentValues values = new ContentValues();
		values.put(MySqlHelper.COLUMN_TIME, baseball.getTime());
		values.put(MySqlHelper.COLUMN_HIT, baseball.getHit());
		values.put(MySqlHelper.COLUMN_DATE, baseball.getCreated_date());

		long id = database.insert(MySqlHelper.TABLE_BASEBALL, null, values);
		return id;
	}

	public ArrayList<BaseballVO> selectBasebal() {
		ArrayList<BaseballVO> baseballList = new ArrayList<BaseballVO>();
		Cursor cursor = database.rawQuery("select time, hit, created_date from baseball order by time asc", null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BaseballVO baseball = new BaseballVO();
			baseball.setTime(cursor.getInt(0));
			baseball.setHit(cursor.getInt(1));
			baseball.setCreated_date(cursor.getString(2));

			cursor.moveToNext();
			baseballList.add(baseball);
		}

		cursor.close();
		return baseballList;
	}

	public void close() {
		dbHelper.close();
	}

}
