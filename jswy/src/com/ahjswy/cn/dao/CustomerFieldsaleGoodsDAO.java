package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.CustomerRecords;
import com.ahjswy.cn.model.UnitidPrice;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CustomerFieldsaleGoodsDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	public List<CustomerRecords> queryAllCustomerPrice() {
		db = helper.getWritableDatabase();
		String sql = "select customerid,goodsid,goodsthirdclassid,unitid,price cu_customerfieldsalegoods where issale = 1";
		List<CustomerRecords> listCustomerRecords = new ArrayList<CustomerRecords>();
		try {
			Cursor cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				CustomerRecords records = new CustomerRecords();
				records.setCustomerid(cursor.getString(0));
				records.setGoodsid(cursor.getString(1));
				records.setGoodsthirdclassid(cursor.getString(2));
				records.setUnitid(cursor.getString(3));
				records.setPrice(cursor.getDouble(4));
				listCustomerRecords.add(records);
			}
			return listCustomerRecords;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 查询商品单位客史价格
	// public CustomerRecords queryUnitidPrice(String customerid, String
	// goodsid, String unitid) {
	// db = helper.getWritableDatabase();
	// String sql = "select customerid,goodsid,goodsthirdclassid,unitid,price
	// from cu_customerfieldsalegoods where issale = 'true' and customerid=? and
	// goodsid=? and unitid=?";
	// try {
	// Cursor cursor = db.rawQuery(sql, new String[] { customerid, goodsid,
	// unitid });
	// if (cursor.moveToNext()) {
	// CustomerRecords records = new CustomerRecords();
	// records.setCustomerid(cursor.getString(0));
	// records.setGoodsid(cursor.getString(1));
	// records.setGoodsthirdclassid(cursor.getString(2));
	// records.setUnitid(cursor.getString(3));
	// records.setPrice(cursor.getDouble(4));
	// return records;
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	public CustomerRecords queryUnitidPrice(String customerid, String goodsid) {
		db = helper.getWritableDatabase();
		String sql = "select customerid,goodsid,goodsthirdclassid,unitid,price from cu_customerfieldsalegoods where issale = 'true' and customerid=? and goodsid=? ";
		try {
			Cursor cursor = db.rawQuery(sql, new String[] { customerid, goodsid });
			CustomerRecords records = null;
			while (cursor.moveToNext()) {
				if (records == null) {
					records = new CustomerRecords();
				}
				records.setCustomerid(cursor.getString(0));
				records.setGoodsid(cursor.getString(1));
				records.setGoodsthirdclassid(cursor.getString(2));
				records.setUnitid(cursor.getString(3));
				records.setPrice(cursor.getDouble(4));
			}
			return records;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 查询商品客史 价格
	 * 
	 * @param prices
	 *            查询商品集合
	 * @return
	 */
	public List<UnitidPrice> queryUnitidPriceList(List<UnitidPrice> prices) {
		db = helper.getWritableDatabase();
		List<UnitidPrice> listUnitidPrice = new ArrayList<UnitidPrice>();
		StringBuilder pricesSB = new StringBuilder();
		pricesSB.append(
				"select a.customerid,a.goodsid,a.unitid,a.price,a.issale from cu_customerfieldsalegoods a where issale='true' and");
		String strCustomerid = " a.customerid in (";
		String strGoodsid = " and a.goodsid in (";
		String strUnitid = " and a.unitid in (";
		String d = ",";
		for (int i = 0; i < prices.size(); i++) {
			if (i == prices.size() - 1) {
				d = "";
			}
			UnitidPrice price = prices.get(i);
			strCustomerid += "'" + price.customerid + "'" + d;
			strGoodsid += "'" + price.goodsid + "'" + d;
			strUnitid += "'" + price.unitid + "'" + d;
		}
		strCustomerid += ")";
		strGoodsid += ")";
		strUnitid += ")";
		pricesSB.append(strCustomerid);
		pricesSB.append(strGoodsid);
		pricesSB.append(strUnitid);
		Cursor query = db.rawQuery(pricesSB.toString(), null);
		try {
			while (query.moveToNext()) {
				UnitidPrice price = new UnitidPrice();
				price.customerid = query.getString(0);
				price.goodsid = query.getString(1);
				price.unitid = query.getString(2);
				price.price = query.getDouble(3);
				listUnitidPrice.add(price);
			}
			return listUnitidPrice;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listUnitidPrice;
	}

}
