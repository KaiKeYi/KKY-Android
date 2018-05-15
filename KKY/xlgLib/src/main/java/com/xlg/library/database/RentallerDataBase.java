package com.xlg.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class RentallerDataBase extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "kky.db";
	public static final int DATABASE_VERSION = 1;
	Context mContext;

	public RentallerDataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建数据库
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		doUpdate(db, oldVersion, newVersion);
	}

	private void createTable(SQLiteDatabase db) {
		// 创建数据库
		List<String> sqls = getCreateSql();
		if (sqls != null && sqls.size() > 0) {
			for (int i = 0; i < sqls.size(); i++) {
				String sql = sqls.get(i);
				if (!TextUtils.isEmpty(sql)) {
					db.execSQL(sql);
				}
			}
		}
	}

	/**
	 * 获得创建SQL文
	 * 
	 * @return
	 */
	private List<String> getCreateSql() {
		List<String> sqls = new ArrayList<String>();

//		sqls.add(ReturnPointTable.getReturnPointTable());

		return sqls;
	}

	/**
	 * 数据库升级动作
	 * 
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	private void doUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {

		//TODO
	}
}
