package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.GoodsImage;
import com.ahjswy.cn.utils.BitmapUtils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GoodsImageDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	@Deprecated
	public List<GoodsImage> get() {
		return null;
		// db = helper.getWritableDatabase();
		// Cursor localCursor = this.db.rawQuery("select * from sz_goodsimage",
		// null);
		// ArrayList localArrayList = new ArrayList();
		// try {
		// boolean bool1 = localCursor.moveToNext();
		// if (!bool1) {
		// GoodsImage localGoodsImage = new GoodsImage();
		// localGoodsImage.setSerialid(localCursor.getLong(localCursor.getColumnIndex("serialid")));
		// localGoodsImage.setGoodsId(localCursor.getString(localCursor.getColumnIndex("goodsid")));
		// localGoodsImage.setImagePath(localCursor.getString(localCursor.getColumnIndex("imagepath")));
		// if (localCursor.getInt(localCursor.getColumnIndex("isgot")) == 0) {
		// Boolean bool2 = true;
		// localGoodsImage.setIsGot(bool2);
		// localArrayList.add(localGoodsImage);
		// return localArrayList;
		// }
		//
		// }
		// } catch (Exception localException) {
		// localException.printStackTrace();
		// } finally {
		// if (localCursor != null)
		// localCursor.close();
		// if (this.db != null)
		// this.db.close();
		// }
		// return localArrayList;
	}

	/**
	 * 
	 * @param paramString
	 * @return
	 */
	public List<GoodsImage> get(String goodsid) {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select serialid, goodsid, imagepath from sz_goodsimage where isgot = 1 and goodsid = ?",
				new String[] { goodsid });
		ArrayList<GoodsImage> listGoodsImage = new ArrayList<GoodsImage>();
		try {
			while (cursor.moveToNext()) {
				GoodsImage goodsimage = new GoodsImage();
				goodsimage.setSerialid(cursor.getLong(0));
				goodsimage.setGoodsId(cursor.getString(1));
				goodsimage.setImagePath(cursor.getString(2));
				goodsimage.setIsGot(true);
				listGoodsImage.add(goodsimage);
			}
			return listGoodsImage;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
		return null;
	}

	@Deprecated
	public int getCount() {
		return 0;
		// this.db = this.helper.getWritableDatabase();
		// Cursor localCursor = this.db.rawQuery("select count(*) from
		// sz_goodsimage", null);
		// try {
		// boolean bool = localCursor.moveToNext();
		// int i = 0;
		// if (bool) {
		// int j = localCursor.getInt(0);
		// i = j;
		// }
		// return i;
		// } catch (Exception localException) {
		// SQLiteDatabase localSQLiteDatabase;
		// do {
		// localException.printStackTrace();
		// if (localCursor != null)
		// localCursor.close();
		// localSQLiteDatabase = this.db;
		// int i = 0;
		// } while (localSQLiteDatabase == null);
		// this.db.close();
		// return 0;
		// } finally {
		// if (localCursor != null)
		// localCursor.close();
		// if (this.db != null)
		// this.db.close();
		// }
	}

	// 查询 sz_goodsimage 数据
	public List<GoodsImage> getNoImage() {
		this.db = this.helper.getWritableDatabase();
		Cursor cursor = this.db.rawQuery("select * from sz_goodsimage where isgot = 0", null);
		ArrayList<GoodsImage> localArrayList = new ArrayList<GoodsImage>();
		try {
			while (cursor.moveToNext()) {
				GoodsImage localGoodsImage = new GoodsImage();
				localGoodsImage.setSerialid(cursor.getLong(cursor.getColumnIndex("serialid")));
				localGoodsImage.setGoodsId(cursor.getString(cursor.getColumnIndex("goodsid")));
				localGoodsImage.setImagePath(cursor.getString(cursor.getColumnIndex("imagepath")));
				if (cursor.getInt(cursor.getColumnIndex("isgot")) != 0) {
					localGoodsImage.setIsGot(true);
				} else {
					localGoodsImage.setIsGot(false);
				}
				localArrayList.add(localGoodsImage);
			}
			return localArrayList;
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

	// 保存 Image
	public void saveImage(long serialid, String strImage, String filename) {
		if (strImage != null && strImage.length() > 0) {
			new BitmapUtils().savePicture(strImage, filename);
			this.db = this.helper.getWritableDatabase();
			this.db.execSQL("update sz_goodsimage set isgot = 1 where serialid = ?",
					new String[] { String.valueOf(serialid) });
			if (this.db != null) {
				this.db.close();
			}
		}
	}
}
