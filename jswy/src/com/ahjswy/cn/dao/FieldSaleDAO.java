package com.ahjswy.cn.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class FieldSaleDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	public boolean updateDocValue(long id, String key, String value) {
		this.db = this.helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(key, value);
		boolean isUpdate = true;
		if (this.db.update("kf_fieldsale", contentValues, "id=?", new String[] { String.valueOf(id) }) != 1) {
			isUpdate = false;
		}
		if (this.db != null) {
			this.db.close();
		}
		return isUpdate;
	}

}
