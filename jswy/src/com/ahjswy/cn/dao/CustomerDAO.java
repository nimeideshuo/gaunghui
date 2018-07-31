package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.CustomerThin;
import com.ahjswy.cn.model.SupplierThin;
import com.ahjswy.cn.utils.TextUtils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CustomerDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	public CustomerThin getCustomer(String customerid) {
		List<CustomerThin> queryAllCustomer = queryAllCustomer();
		for (int i = 0; i < queryAllCustomer.size(); i++) {
			if (customerid.equals(queryAllCustomer.get(i).getId())) {
				return queryAllCustomer.get(i);
			}
		}
		return null;
	}

	// 查找所有SQL 客户
	public List<CustomerThin> queryAllCustomer() {
		this.db = this.helper.getWritableDatabase();
		Cursor cursor = this.db.rawQuery(
				"select id, name, pinyin, contactmoblie, address, settleterm, discountratio, promotionid, promotionname, pricesystemid from cu_customer where isavailable='1' and iscustomer=1 order by name",
				null);
		ArrayList<CustomerThin> localArrayList = new ArrayList<CustomerThin>();
		try {
			while (cursor.moveToNext()) {
				CustomerThin localCustomerThin = new CustomerThin();
				localCustomerThin.setId(cursor.getString(0));
				localCustomerThin.setName(cursor.getString(1));
				localCustomerThin.setPinyin(cursor.getString(2));
				localCustomerThin.setContactmoblie(cursor.getString(3));
				localCustomerThin.setAddress(cursor.getString(4));
				localCustomerThin.setSettleterm(cursor.getInt(5));
				localCustomerThin.setDiscountratio(cursor.getDouble(6));
				localCustomerThin.setPromotionid(cursor.getString(7));
				localCustomerThin.setPromotionname(cursor.getString(8));
				localCustomerThin.setPricesystemid(cursor.getString(9));
				localArrayList.add(localCustomerThin);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return localArrayList;
	}

	/**
	 * 客户是否使用客史价格
	 * 
	 * @param customerid
	 * @return
	 */
	public boolean isUseCustomerprice(String customerid) {
		this.db = this.helper.getReadableDatabase();
		String sql = "select isusecustomerprice from cu_customer where id=? ";
		Cursor cursor =null;
		try {
			 cursor = db.rawQuery(sql, new String[] { customerid });
			if (cursor.moveToNext()) {
				return cursor.getInt(0) == 0 ? false : true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		
		return false;
	}

	public CustomerThin queryCustomer(String customerName) {
		this.db = this.helper.getWritableDatabase();
		Cursor cursor = this.db.rawQuery(
				"select id, name, pinyin, contactmoblie, address, settleterm, discountratio, promotionid, promotionname, pricesystemid from cu_customer where name=? ",
				new String[] { customerName });
		try {
			if (cursor.moveToNext()) {
				CustomerThin localCustomerThin = new CustomerThin();
				localCustomerThin.setId(cursor.getString(0));
				localCustomerThin.setName(cursor.getString(1));
				localCustomerThin.setPinyin(cursor.getString(2));
				localCustomerThin.setContactmoblie(cursor.getString(3));
				localCustomerThin.setAddress(cursor.getString(4));
				localCustomerThin.setSettleterm(cursor.getInt(5));
				localCustomerThin.setDiscountratio(cursor.getDouble(6));
				localCustomerThin.setPromotionid(cursor.getString(7));
				localCustomerThin.setPromotionname(cursor.getString(8));
				localCustomerThin.setPricesystemid(cursor.getString(9));
				return localCustomerThin;
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return null;
	}

	/**
	 * 根据客户id 查询 客户
	 * 
	 * @param customerid
	 * @return
	 */
	public CustomerThin queryCustomerId(String customerid) {

		this.db = this.helper.getReadableDatabase();
		Cursor cursor =null;
		try {

			 cursor = this.db.rawQuery(
					"select id, name, pinyin, contactmoblie, address, settleterm, discountratio, promotionid, promotionname, pricesystemid from cu_customer where id=? ",
					new String[] { customerid });
			if (cursor.moveToNext()) {
				CustomerThin customer = new CustomerThin();
				customer.setId(cursor.getString(0));
				customer.setName(cursor.getString(1));
				customer.setPinyin(cursor.getString(2));
				customer.setContactmoblie(cursor.getString(3));
				customer.setAddress(cursor.getString(4));
				customer.setSettleterm(cursor.getInt(5));
				customer.setDiscountratio(cursor.getDouble(6));
				customer.setPromotionid(cursor.getString(7));
				customer.setPromotionname(cursor.getString(8));
				customer.setPricesystemid(cursor.getString(9));
				return customer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return null;
	}

	/**
	 * 查询客户价格体系
	 * 
	 * @param customerid
	 * @return
	 */
	public String queryPricesystemid(String customerid) {
		if (TextUtils.isEmpty(customerid)) {
			return null;
		}
		this.db = this.helper.getReadableDatabase();
		Cursor cursor=null;
		try {
			 cursor = this.db.rawQuery(
					"select  a.pricesystemid   from cu_customer a where id is not null and id=? ",
					new String[] { customerid });
			if (cursor.moveToNext()) {
				return cursor.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return null;
	}

	// 查找所有SQL 客户供应商
	public List<SupplierThin> queryAllSupplier() {
		this.db = this.helper.getWritableDatabase();
		Cursor cursor = this.db.rawQuery(
				"select id, name, pinyin from cu_customer where isavailable='1' and issupplier='1' order by name",
				null);
		ArrayList<SupplierThin> localArrayList = new ArrayList<SupplierThin>();
		try {
			while (cursor.moveToNext()) {
				SupplierThin supplierthin = new SupplierThin();
				supplierthin.setId(cursor.getString(0));
				supplierthin.setName(cursor.getString(1));
				supplierthin.setPinyin(cursor.getString(2));
				localArrayList.add(supplierthin);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return localArrayList;
	}
}
