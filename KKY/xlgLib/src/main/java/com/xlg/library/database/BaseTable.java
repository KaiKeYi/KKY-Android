package com.xlg.library.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import com.xlg.library.base.BaseApp;
import com.xlg.library.utils.LogUtil;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTable<T> implements BaseColumns {

	public static String SCHEMA = "content://";
	
	
	private String mTableName;
	private SQLiteOpenHelper mSqLiteOpenHelper;
	
	public BaseTable(String tableName, SQLiteOpenHelper sqlHelper){
		this.mTableName = tableName;
		this.mSqLiteOpenHelper = sqlHelper;
		SCHEMA += BaseApp.getAppContext().getPackageName();
	}
	
	/**
	 * 通过cursor转换为对象
	 * @param cursor
	 * @return
	 */
	public abstract T getItemFromCursor(Cursor cursor);

	/**
	 * 对象转成contentValues
	 * @param item
	 * @return
	 */
	public abstract ContentValues getContentValues(T item);
	
	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName(){
		return mTableName;
	}
	
	/**
	 * 检索所有数据到列表中
	 * @return
	 */
	public List<T> queryAll(){
		return queryByCase(null, null, null);
	}
	
	/**
	 * 通过特定条件检索数据
	 * @param where
	 * @param orderBy
	 * @return
	 */
	public List<T> queryByCase(String where, String args[], String orderBy){
		List<T> items = null;
		Cursor cursor = null;
		try {
			SQLiteDatabase db = getDatabase();
			if(db == null)
				return null;
			items = new ArrayList<T>();
			cursor = db.query(getTableName(), null, where, args, null, null, orderBy);
			while(cursor.moveToNext()){
				items.add(getItemFromCursor(cursor));
			}
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
			e.printStackTrace();
			
		} finally {
			if(cursor != null){
				cursor.close();
			}
		}
		return items;
	}
	
	/**
	 * 获得一行数据
	 * @param where
	 * @param args
	 * @param orderBy
	 * @return
	 */
	public T querySingleByCase(String where, String args[], String orderBy){
		List<T> items = queryByCase(where, args, orderBy);
		if(items != null && items.size() > 0){
			return items.get(0);
		}
		return null;
	}
	
	/**
	 * 获得数据行数
	 * @param where
	 * @param args
	 * @return
	 */
	public int getCount(String where, String args[]){
		Cursor cursor = null;
		try {
			SQLiteDatabase db = getDatabase();
			if(db == null)
				return 0;
			cursor = db.query(getTableName(), null, where, args, null, null, null);
			return cursor.getCount();
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		} finally {
			if(cursor != null){
				cursor.close();
			}
		}
		return 0;
	}
	
	/**
	 * 插入数据
	 * @param item
	 * @return
	 */
	public long insert(T item){
		Cursor c = null;
		long result = -1;
		try {
			SQLiteDatabase db = getDatabase();
			if(db == null)
				throw new RuntimeException("database must't be null!");
			result = db.insert(getTableName(), null, getContentValues(item));
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		} finally {
			if(c != null){
				c.close();
			}
		}
		return result;
	}
	
	/**
	 * 批量插入数据
	 * @param items
	 */
	public void insert(List<T> items){
		Cursor c = null;
		SQLiteDatabase db = getDatabase();
		try {
			if(db == null)
				return;
			db.beginTransaction();
			for(int i=0; i< items.size(); i++){
				db.insert(getTableName(), null, getContentValues(items.get(i)));	
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		} finally {
			if(c != null){
				c.close();
			}
			try {
				db.endTransaction();
			} catch (Exception e2) {
			}
		}
	}
	
	/**
	 * 通过特定条件删除数据
	 * @param where
	 * @param args
	 * @return
	 */
	public int deleteByCase(String where, String args[]){
		int count = -1;
		try {
			SQLiteDatabase db = getDatabase();
			if(db == null)
				throw new RuntimeException("database must't be null!");
			count = db.delete(getTableName(), where, args);
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
		return count;
	}
	
	/**
	 * 更新记录
	 * @param item
	 * @param where
	 * @param args
	 * @return
	 */
	public int updateByCase(T item, String where, String args[]){
		int count = -1;
		try {
			SQLiteDatabase db = getDatabase();
			if(db == null)
				throw new RuntimeException("database must't be null!");
			
			ContentValues values = getContentValues(item);
			count = db.update(getTableName(), values, where, args);
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
		return count;
	}
	
	/**
	 * 通过ID查询
	 * @param id
	 * @return
	 */
	public T queryById(long id){
		List<T> result = queryByCase(_ID + "=" + id, null, null);
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * 获得String
	 * @param cursor
	 * @param columnName
	 * @return
	 */
	@SuppressLint("UseValueOf")
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T getValue(Cursor cursor, String columnName, Class<T> t){
		int index = cursor.getColumnIndex(columnName);
		if(String.class.getName().equals(t.getName())){
			if(index >= 0)
				return (T) cursor.getString(index);
			return null;
		} else if(Integer.class.getName().equals(t.getName())){
			if(index >= 0)
				return (T) new Integer(cursor.getInt(index));
			return (T) new Integer(0);
		} else if(Long.class.getName().equals(t.getName())){
			if(index >= 0)
				return (T) new Long(cursor.getLong(index));
			return (T) new Long(0);
		} else if(Float.class.getName().equals(t.getName())){
			if(index >= 0)
				return (T) new Float(cursor.getFloat(index));
			return (T) new Float(0);
		} else if(Double.class.getName().equals(t.getName())){
			if(index >= 0)
				return (T) new Double(cursor.getDouble(index));
			return (T) new Float(0);
		} else if(Date.class.getName().equals(t.getName())){
			if(index >= 0)
				return (T) new Date(cursor.getLong(index));
			return (T) new Date(System.currentTimeMillis());
		}
		return null;
	}
	
	/**
	 * 获得数据库
	 * @return
	 */
	public SQLiteDatabase getDatabase(){
		if(mSqLiteOpenHelper != null)
			return mSqLiteOpenHelper.getWritableDatabase();
		
		SQLiteOpenHelper helper = DataBaseManager.getDataBaseManager().getDefaultDB();
		if(helper == null)
			return null;
		return helper.getWritableDatabase();
	}
	
	/**
	 * 获得通知的URI
	 * @return
	 */
	public static Uri getNotifyUri(String tableName){
		Uri uri = Uri.parse(SCHEMA  + tableName + "/" + tableName);
		return uri;
	}
	
	//批量动作
	public void notifyDataChange(){
		notifyChange(getNotifyUri(getTableName()));
	}
	
	/**
	 * 通知数据改变
	 * @param uri
	 */
	public void notifyChange(Uri uri){
		BaseApp.getAppContext().getContentResolver().notifyChange(uri, null);
	}
}
