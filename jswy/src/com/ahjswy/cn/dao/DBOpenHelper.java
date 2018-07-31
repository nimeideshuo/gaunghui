package com.ahjswy.cn.dao;

import java.io.InputStream;

import com.ahjswy.cn.app.MyApplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "fields.db";
	public static final int VERSION = 1;

	public DBOpenHelper() {
		super(MyApplication.getInstance(), DBNAME, null, 1);
		// 如果 sz_goods 不存在 null createTable()创建表
		if (!isTableExsit("sz_goods")) {
			createTable();
		}
	}

	Boolean bool1 = false;

	private boolean isTableExsit(String paramString) {
		if (paramString == null) {
			return false;
		}
		SQLiteDatabase localSQLiteDatabase = null;
		Cursor localCursor = null;
		try {
			localSQLiteDatabase = getReadableDatabase();
			localCursor = localSQLiteDatabase
					.rawQuery("select count(*) as c from sqlite_master where type ='table' and name ='"
							+ paramString.trim() + "' ", null);
			// 指针 移动到第一条记录上
			boolean bool2 = localCursor.moveToNext();

			if (bool2) {
				int i = localCursor.getInt(0);
				bool1 = false;
				if (i > 0) {
					bool1 = true;
				}
			}
			return bool1;
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (localCursor != null)
				localCursor.close();
			if (localSQLiteDatabase != null)
				localSQLiteDatabase.close();
		}
		return bool1;
	}

	// 代码
	public void createTable() {
		String[] arrayOfString;
		int j;
		try {
			// 读取本地 SQL assets 并执行 创建表
			InputStream localInputStream = MyApplication.getInstance().getAssets().open("fields.sql");
			StringBuilder localStringBuilder = new StringBuilder();
			byte[] arrayOfByte = new byte[1024];
			// int i = localInputStream.read(arrayOfByte);
			int lengs = 0;
			// 读取本地 sql db 每次 1024字节 循环读取完毕
			while ((lengs = localInputStream.read(arrayOfByte)) > 0) {
				localStringBuilder.append(new String(arrayOfByte, 0, lengs));
			}

			arrayOfString = localStringBuilder.toString().split(";");

			for (j = 0; j < arrayOfString.length; j++) {
				getWritableDatabase().execSQL(arrayOfString[j]);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			return;
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
