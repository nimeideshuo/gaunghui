package com.ahjswy.cn.dao;

import com.ahjswy.cn.model.DocContainerEntity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 本地存储开单单据  销售单
 * @author Administrator
 *
 */
public class Sv_docitem {
	private DBOpenHelper helper = new DBOpenHelper();

	public DocContainerEntity queryDoc(String doctype) {
		SQLiteDatabase db = this.helper.getWritableDatabase();
		String sql = "select svid,showid,deleteinitem,deleteitem,doc,item,doctype,paytype from sv_docitem where doctype = ?";
		try {
			Cursor cursor = db.rawQuery(sql, new String[] { doctype });
			while (cursor.moveToNext()) {
				DocContainerEntity docEntity = new DocContainerEntity();
				docEntity.setSvid(cursor.getInt(0));
				docEntity.setShowid(cursor.getString(1));
				docEntity.setDeleteinitem(cursor.getString(2));
				docEntity.setDeleteitem(cursor.getString(3));
				docEntity.setDoc(cursor.getString(4));
				docEntity.setItem(cursor.getString(5));
				docEntity.setDoctype(cursor.getString(6));
				docEntity.setPaytype(cursor.getString(7));
				return docEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
		return null;
	}

	public String getLastShowId(String doctype) {
		SQLiteDatabase db = this.helper.getWritableDatabase();
		String sql = "select showid from sv_docitem where doctype=? ";
		try {
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				return cursor.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
		return "001";
	}

	public synchronized String insetDocItem(DocContainerEntity docEntity) {
		SQLiteDatabase db = this.helper.getWritableDatabase();
		String svId=null;
		db.beginTransaction();
		try {
		ContentValues values = new ContentValues();
		values.put("showid", docEntity.getShowid());
		values.put("deleteinitem", docEntity.getDeleteinitem());
		values.put("deleteitem", docEntity.getDeleteitem());
		values.put("doc", docEntity.getDoc());
		values.put("item", docEntity.getItem());
		values.put("doctype", docEntity.getDoctype());
		values.put("paytype", docEntity.getPaytype());
		db.insert("sv_docitem", null, values);
		db.setTransactionSuccessful();
//		 svId = getLastShowId(docEntity.getDoctype());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.endTransaction();
			db.close();
		}
		return svId;
	}

	public synchronized void updataDocItem(DocContainerEntity docEntity) {
		SQLiteDatabase db = this.helper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			values.put("deleteinitem", docEntity.getDeleteinitem());
			values.put("deleteitem", docEntity.getDeleteitem());
			values.put("doc", docEntity.getDoc());
			values.put("item", docEntity.getItem());
			values.put("doctype", docEntity.getDoctype());
			values.put("paytype", docEntity.getPaytype());
			db.update("sv_docitem", values, "doctype = ?", new String[] { docEntity.getDoctype() });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.endTransaction();
			db.close();
		}
		
	}

	public void deleteDoc(String doctype) {
		SQLiteDatabase db = this.helper.getWritableDatabase();
		db.delete("sv_docitem", "doctype=?", new String[] { doctype });
		db.close();
	}
}
