package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.GoodsClass;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GoodsClassDAO {
	private DBOpenHelper helper = new DBOpenHelper();
	private SQLiteDatabase db;

	/**
	 * 查询所有商品类别
	 * 
	 * @return
	 */
	public List<GoodsClass> queryAll() {
		db = helper.getReadableDatabase();
		String sql = "select id,name,pinyin from sz_goodsclass where isavailable='1'";
		Cursor cursor = db.rawQuery(sql, null);
		ArrayList<GoodsClass> listGoodClass = new ArrayList<GoodsClass>();
		try {
			while (cursor.moveToNext()) {
				GoodsClass goodsClass = new GoodsClass();
				goodsClass.setId(cursor.getString(0));
				goodsClass.setName(cursor.getString(1));
				goodsClass.setPinyin(cursor.getString(2));
				listGoodClass.add(goodsClass);
			}
			return listGoodClass;
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
