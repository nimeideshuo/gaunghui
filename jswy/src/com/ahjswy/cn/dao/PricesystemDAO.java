package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.Pricesystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 查询系统 单价
 * 
 * @author Administrator
 *
 */
public class PricesystemDAO {
	private DBOpenHelper helper = new DBOpenHelper();
	private SQLiteDatabase db;

	public List<Pricesystem> queryAll() {
		db = helper.getReadableDatabase();
		Cursor cursor = null;
		try {
			String sql = "select psid,psname,pinyin from sz_pricesystem where isavailable = '1' ";
			ArrayList<Pricesystem> listPrice = new ArrayList<Pricesystem>();
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				Pricesystem pricesystem = new Pricesystem();
				pricesystem.setPsid(cursor.getString(0));
				pricesystem.setPsname(cursor.getString(1));
				pricesystem.setPinyin(cursor.getString(2));
				listPrice.add(pricesystem);
			}
			return listPrice;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
}
