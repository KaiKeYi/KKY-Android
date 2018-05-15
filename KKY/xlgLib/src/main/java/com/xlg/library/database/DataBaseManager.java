package com.xlg.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * @category数据库管理器
 * 
 */
public class DataBaseManager {

	private String TAG = getClass().getSimpleName();

	// 应用主数据库
	private SQLiteOpenHelper mDefaultDbHelper;

	public static DataBaseManager _instance = null;

	private Hashtable<Class<? extends BaseTable<?>>, BaseTable<?>> mDefaultDbTables;

	private DataBaseManager() {

		// 默认Tables
		mDefaultDbTables = new Hashtable<Class<? extends BaseTable<?>>, BaseTable<?>>();
	}

	public static DataBaseManager getDataBaseManager() {
		if (_instance == null) {
			_instance = new DataBaseManager();
		}
		return _instance;
	}

	/**
	 * 获得表实例
	 * 
	 * @param table
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public <T extends BaseTable<?>> T getTable(Class<T> table) {
		return (T) mDefaultDbTables.get(table);
	}

	/**
	 * 初始化数据库
	 * 
	 * @param context
	 */
	public void initDB(Context context) {
		if (mDefaultDbHelper != null)
			mDefaultDbHelper.close();
		mDefaultDbHelper = new RentallerDataBase(context);
		registTables();// 注册表
	}

	/**
	 * 注册默认数据库中的表
	 */
	private void registTables() {

//		mDefaultDbTables.put(ReturnPointTable.class, new ReturnPointTable(mDefaultDbHelper));

	}
	/**
	 * 释放数据库
	 */
	public void releaseDB() {
		if (mDefaultDbHelper != null) {
			try {
				mDefaultDbHelper.close();
			} catch (Exception e) {
			}
			mDefaultDbHelper = null;
		}
	}

	/**
	 * 获得默认数据库
	 * 
	 * @return
	 */
	public SQLiteOpenHelper getDefaultDB() {
		return mDefaultDbHelper;
	}

	/**
	 * 清空默认数据库
	 */
	public void clearDefaultDB() {
		if (mDefaultDbTables != null) {
			Iterator<Class<? extends BaseTable<?>>> iterator = mDefaultDbTables.keySet().iterator();
			while (iterator.hasNext()) {
				Class<? extends BaseTable<?>> key = iterator.next();
				BaseTable<?> table = mDefaultDbTables.get(key);
				table.deleteByCase(null, null);
			}
		}
	}

}
