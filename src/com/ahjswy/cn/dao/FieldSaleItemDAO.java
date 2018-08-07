package com.ahjswy.cn.dao;

import com.ahjswy.cn.model.FieldSaleItem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FieldSaleItemDAO {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public FieldSaleItemDAO() {
		this.helper = new DBOpenHelper();
	}

	/**
	 * 删除 销售单据
	 * 
	 * @param serialid
	 * @return
	 */
	public boolean delete(long serialid) {
		db = helper.getWritableDatabase();
		try {
			String deleteItem = "delete from kf_fieldsaleitem where serialid=?";
			db.execSQL(deleteItem, new String[] { "" + serialid });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
				return true;
			}
		}
		return false;

	}

	public boolean deleteAll() {
		db = helper.getWritableDatabase();
		this.db.execSQL("delete from kf_fieldsaleitem");
		return true;
	}

	/***
	 * 根据 商品 id 删除
	 * 
	 * @param goodsid
	 * @return
	 */
	public boolean deleteGoods(String goodsid) {
		db = helper.getWritableDatabase();
		try {
			String deleteItem = "delete from kf_fieldsaleitem where goodsid =?";
			db.execSQL(deleteItem, new String[] { goodsid });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
				return true;
			}
		}
		return false;

	}

	public FieldSaleItem getFieldSaleItem(long goodsid) {
		this.db = this.helper.getWritableDatabase();
		String sql = "select serialid, fieldsaleid,warehouseid, goodsid, saleunitid,salenum,saleprice,saleremark,givenum,giveunitid,giveremark,cancelbasenum,cancelremark,remark,giftgoodsid,giftgoodsname,giftunitid,giftunitname,giftnum,giftremarkpromotiontype,ispromotion,isexhibition from kf_fieldsaleitem where item.goodsid=?";
		Cursor query = db.rawQuery(sql, new String[] { String.valueOf(goodsid) });
		FieldSaleItem item = null;
		if (query.moveToNext()) {
			item = new FieldSaleItem();
			item.setSerialid(query.getLong(0));
			item.setFieldsaleid(query.getLong(1));
			item.setWarehouseid(query.getString(2));
			item.setGoodsid(query.getString(3));
			item.setSaleunitid(query.getString(4));
			item.setSalenum(query.getDouble(5));
			item.setSaleprice(query.getDouble(6));
			item.setSaleremark(query.getString(7));
			item.setGivenum(query.getDouble(8));
			item.setGiveunitid(query.getString(9));
			item.setGiveremark(query.getString(10));
			item.setCancelbatchin(query.getString(11));
			item.setCancelremark(query.getString(12));

		}
		return item;
	}

	/**
	 * 是否有此商品
	 * 
	 * @param goodsid
	 * @return
	 */
	public boolean isGood(String goodsid) {
		String sql = "select goodsid from kf_fieldsaleitem where goodsid =?";
		this.db = this.helper.getWritableDatabase();
		Cursor query = db.rawQuery(sql, new String[] { goodsid });
		if (query.moveToNext()) {
			return true;
		}
		return false;
	}

	public long saveGood(String goods) {
		this.db = this.helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("goodsid", goods);
		db.insert("kf_fieldsaleitem", null, values);
		return 0;
	}

	public long saveFieldSaleItem(FieldSaleItem item) {
		this.db = this.helper.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put("fieldsaleid", Long.valueOf(item.getFieldsaleid()));
		content.put("goodsid", item.getGoodsid());
		long insert = this.db.insert("kf_fieldsaleitem", null, content);
		if (this.db != null) {
			this.db.close();
		}
		return insert;
	}

	public boolean updateFieldSaleItem(FieldSaleItem arg11) {
		return false;
	}

	public void deleteZeroItem(long fieldsaleid) {
		this.db = this.helper.getWritableDatabase();
		String sql = "delete from kf_fieldsaleitem where fieldsaleid=? and salenum=0 and cancelnum=0 and givenum=0 and exchangenum=0";
		try {
			this.db.execSQL(sql, new String[] { String.valueOf(fieldsaleid) });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}

	}

	public int getCount(long fieldsaleid) {
		this.db = this.helper.getWritableDatabase();
		int content = 0;
		try {
			Cursor cursor = this.db.rawQuery("select count(*) from kf_fieldsaleitem where fieldsaleid = ? ",
					new String[] { String.valueOf(fieldsaleid) });
			if (cursor.moveToNext()) {
				content = cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public int getCount() {
		this.db = this.helper.getWritableDatabase();
		String sql = "select max(serialid) from kf_fieldsaleitem ";
		Cursor query = db.rawQuery(sql, null);
		if (query.moveToNext()) {
			return query.getInt(0);
		}
		return 0;
	}

}
