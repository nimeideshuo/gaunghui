package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.Customertype;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CustomerTypeDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	public List<Customertype> queryAllcuCustomertypes() {
		db = this.helper.getReadableDatabase();
		Cursor localCursor = this.db.rawQuery("select id,name,pricesystemid from cu_customertype where isavailable='1'",
				null);
		try {
			ArrayList<Customertype> localArrayList = new ArrayList<Customertype>();
			while (localCursor.moveToNext()) {
				Customertype localCustomertype = new Customertype();
				localCustomertype.setId(localCursor.getString(0));
				localCustomertype.setName(localCursor.getString(1));
				localCustomertype.setPricesystemid(localCursor.getString(2));
				localArrayList.add(localCustomertype);
			}
			return localArrayList;
		} catch (Exception e) {
			
		} finally {
			if (localCursor != null) {
				localCursor.close();
			}
			if (this.db != null) {
				this.db.close();
			}
		}
		return null;
	}

}
