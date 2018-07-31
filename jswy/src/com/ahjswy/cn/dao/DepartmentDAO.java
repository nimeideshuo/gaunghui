package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;
import com.ahjswy.cn.model.Department;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DepartmentDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	public List<Department> getAllDepartment() {
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = this.db.rawQuery(
				"select did, dname, warehouseid, warehousename from sz_department where isavailable='1'", null);
		List<Department> localArrayList = new ArrayList<Department>();

		try {
			while (cursor.moveToNext()) {
				Department localDepartment = new Department();
				localDepartment.setDid(cursor.getString(0));
				localDepartment.setDname(cursor.getString(1));
				localDepartment.setWarehouseid(cursor.getString(2));
				localDepartment.setWarehousename(cursor.getString(3));
				localArrayList.add(localDepartment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return localArrayList;
	}

	// ERROR //
	public Department getDepartment(String paramString) {
		return null;
		// Byte code:
		// 0: aload_0
		// 1: aload_0
		// 2: getfield 17 com/sunwuyou/swyxs/dao/DepartmentDAO:helper
		// Lcom/sunwuyou/swyxs/dao/DBOpenHelper;
		// 5: invokevirtual 25
		// com/sunwuyou/swyxs/dao/DBOpenHelper:getReadableDatabase
		// ()Landroid/database/sqlite/SQLiteDatabase;
		// 8: putfield 27 com/sunwuyou/swyxs/dao/DepartmentDAO:db
		// Landroid/database/sqlite/SQLiteDatabase;
		// 11: aload_0
		// 12: getfield 27 com/sunwuyou/swyxs/dao/DepartmentDAO:db
		// Landroid/database/sqlite/SQLiteDatabase;
		// 15: ldc 81
		// 17: iconst_1
		// 18: anewarray 83 java/lang/String
		// 21: dup
		// 22: iconst_0
		// 23: aload_1
		// 24: aastore
		// 25: invokevirtual 35 android/database/sqlite/SQLiteDatabase:rawQuery
		// (Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
		// 28: astore_2
		// 29: aconst_null
		// 30: astore_3
		// 31: aload_2
		// 32: invokeinterface 44 1 0
		// 37: istore 6
		// 39: aconst_null
		// 40: astore_3
		// 41: iload 6
		// 43: ifeq +63 -> 106
		// 46: new 50 com/sunwuyou/swyxs/model/Department
		// 49: dup
		// 50: invokespecial 51 com/sunwuyou/swyxs/model/Department:<init> ()V
		// 53: astore 7
		// 55: aload 7
		// 57: aload_2
		// 58: iconst_0
		// 59: invokeinterface 55 2 0
		// 64: invokevirtual 59 com/sunwuyou/swyxs/model/Department:setDid
		// (Ljava/lang/String;)V
		// 67: aload 7
		// 69: aload_2
		// 70: iconst_1
		// 71: invokeinterface 55 2 0
		// 76: invokevirtual 62 com/sunwuyou/swyxs/model/Department:setDname
		// (Ljava/lang/String;)V
		// 79: aload 7
		// 81: aload_2
		// 82: iconst_2
		// 83: invokeinterface 55 2 0
		// 88: invokevirtual 65
		// com/sunwuyou/swyxs/model/Department:setWarehouseid
		// (Ljava/lang/String;)V
		// 91: aload 7
		// 93: aload_2
		// 94: iconst_3
		// 95: invokeinterface 55 2 0
		// 100: invokevirtual 68
		// com/sunwuyou/swyxs/model/Department:setWarehousename
		// (Ljava/lang/String;)V
		// 103: aload 7
		// 105: astore_3
		// 106: aload_2
		// 107: ifnull +9 -> 116
		// 110: aload_2
		// 111: invokeinterface 47 1 0
		// 116: aload_0
		// 117: getfield 27 com/sunwuyou/swyxs/dao/DepartmentDAO:db
		// Landroid/database/sqlite/SQLiteDatabase;
		// 120: ifnull +10 -> 130
		// 123: aload_0
		// 124: getfield 27 com/sunwuyou/swyxs/dao/DepartmentDAO:db
		// Landroid/database/sqlite/SQLiteDatabase;
		// 127: invokevirtual 48 android/database/sqlite/SQLiteDatabase:close
		// ()V
		// 130: aload_3
		// 131: areturn
		// 132: astore 5
		// 134: aload 5
		// 136: invokevirtual 77 java/lang/Exception:printStackTrace ()V
		// 139: aload_2
		// 140: ifnull +9 -> 149
		// 143: aload_2
		// 144: invokeinterface 47 1 0
		// 149: aload_0
		// 150: getfield 27 com/sunwuyou/swyxs/dao/DepartmentDAO:db
		// Landroid/database/sqlite/SQLiteDatabase;
		// 153: ifnull -23 -> 130
		// 156: aload_0
		// 157: getfield 27 com/sunwuyou/swyxs/dao/DepartmentDAO:db
		// Landroid/database/sqlite/SQLiteDatabase;
		// 160: invokevirtual 48 android/database/sqlite/SQLiteDatabase:close
		// ()V
		// 163: aload_3
		// 164: areturn
		// 165: astore 4
		// 167: aload_2
		// 168: ifnull +9 -> 177
		// 171: aload_2
		// 172: invokeinterface 47 1 0
		// 177: aload_0
		// 178: getfield 27 com/sunwuyou/swyxs/dao/DepartmentDAO:db
		// Landroid/database/sqlite/SQLiteDatabase;
		// 181: ifnull +10 -> 191
		// 184: aload_0
		// 185: getfield 27 com/sunwuyou/swyxs/dao/DepartmentDAO:db
		// Landroid/database/sqlite/SQLiteDatabase;
		// 188: invokevirtual 48 android/database/sqlite/SQLiteDatabase:close
		// ()V
		// 191: aload 4
		// 193: athrow
		// 194: astore 4
		// 196: goto -29 -> 167
		// 199: astore 5
		// 201: aload 7
		// 203: astore_3
		// 204: goto -70 -> 134
		//
		// Exception table:
		// from to target type
		// 31 39 132 java/lang/Exception
		// 46 55 132 java/lang/Exception
		// 31 39 165 finally
		// 46 55 165 finally
		// 134 139 165 finally
		// 55 103 194 finally
		// 55 103 199 java/lang/Exception
	}

	public boolean isExist() {
		this.db = this.helper.getReadableDatabase();
		Cursor localCursor = this.db.rawQuery("select 1 from sz_department", null);
		try {
			boolean bool1 = localCursor.moveToNext();
			boolean bool2 = false;
			if (bool1)
				bool2 = true;
			return bool2;
		} catch (Exception localException) {
			localException.printStackTrace();
			return false;
		} finally {
			localCursor.close();
		}
	}
}
