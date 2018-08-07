package com.ahjswy.cn.dao;

import android.database.sqlite.SQLiteDatabase;

public class BaseDao {
	protected SQLiteDatabase db;
	protected DBOpenHelper helper = new DBOpenHelper();

	public boolean deleteDataBase() {
		this.db = this.helper.getWritableDatabase();
		this.db.execSQL("delete from sz_user");
		this.db.execSQL("delete from sz_department");
		this.db.execSQL("delete from sz_warehouse");
		this.db.execSQL("delete from sz_paytype");
		this.db.execSQL("delete from cu_customer");
		this.db.execSQL("delete from cu_customertype");
		this.db.execSQL("delete from sz_region");
		this.db.execSQL("delete from sz_visitline");
		this.db.execSQL("delete from sz_goods");
		this.db.execSQL("delete from sz_goodsprice");
		this.db.execSQL("delete from sz_goodsunit");
		this.db.execSQL("delete from sz_goodsimage");
		this.db.execSQL("delete from cu_customerfieldsalegoods");
		return true;
	}
	/**
	 * 客史
	 */
	public static String DELETE_CUSTOMERFIELDSALE = "delete from cu_customerfieldsalegoods";

	public boolean deleteDB(String table) {
		this.db = this.helper.getWritableDatabase();
		this.db.execSQL(table);
		return true;

	}

}
