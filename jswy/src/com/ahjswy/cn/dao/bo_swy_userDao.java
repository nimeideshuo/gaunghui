package com.ahjswy.cn.dao;

import com.ahjswy.cn.bean.bmob.bo_swy_user;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class bo_swy_userDao {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();


	public bo_swy_user queryUser(String userId) {
		Cursor cursor = this.db.rawQuery(
				"select userid, userName, versionname, deviceid, registerDate, code, database, accountset, sleep,message,memory state from bo_swy_user where userid=?",
				new String[] { userId });
		
		
		

		return null;
	}

}
