package com.ahjswy.cn.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.Goods;
import com.ahjswy.cn.model.GoodsInfo;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.response.RespQueryStockWithTimeEntity;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GoodsDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	public int getCount() {
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = this.db.rawQuery("select count(*) from sz_goods", null);
		try {
			if (cursor.moveToNext()) {
				return cursor.getInt(0);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return 0;
	}

	// 获取商品
	public Goods getGoods(String str) {

		Cursor cursor = null;
		String array[] = { str, str };
		String localString1 = "select id,name,pinyin,barcode,salecue,specification,model,goodsclassid,goodsclassname,stocknumber,bigstocknumber,getstocktime, isusebatch from sz_goods g where g.id=? or g.barcode=? ";
		this.db = this.helper.getReadableDatabase();
		cursor = this.db.rawQuery(localString1, array);
		try {
			while (cursor.moveToNext()) {
				Goods goods = new Goods();
				goods.setId(cursor.getString(0));
				goods.setName(cursor.getString(1));
				goods.setPinyin(cursor.getString(2));
				goods.setBarcode(cursor.getString(3));
				goods.setSalecue(cursor.getString(4));
				goods.setSpecification(cursor.getString(5));
				goods.setModel(cursor.getString(6));
				goods.setGoodsclassid(cursor.getString(7));
				goods.setGoodsclassname(cursor.getString(8));
				goods.setStocknumber(cursor.getString(9));
				goods.setBigstocknumber(cursor.getString(10));
				goods.setGetstocktime(new Timestamp(cursor.getLong(11)));
				goods.setIsusebatch(cursor.getInt(12) == 1 ? true : false);
				return goods;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return null;

	}

	// 获取商品 薄
	public GoodsThin getGoodsThin(String str) {
		Cursor cursor = null;
		GoodsThin goodsthin = new GoodsThin();
		String array[] = { str, str };
		String localString1 = "select g.id,g.name,g.pinyin,g.barcode,g.specification,g.model, g.isusebatch from sz_goods g where  g.id=? or g.barcode=?";
		this.db = this.helper.getReadableDatabase();
		try {
			cursor = this.db.rawQuery(localString1, array);
			while (cursor.moveToNext()) {
				goodsthin.setId(cursor.getString(0));
				goodsthin.setName(cursor.getString(1));
				goodsthin.setPinyin(cursor.getString(2));
				goodsthin.setBarcode(cursor.getString(3));
				goodsthin.setSpecification(cursor.getString(4));
				goodsthin.setModel(cursor.getString(5));
				if (cursor.getInt(6) == 1) {
					goodsthin.setIsusebatch(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return goodsthin;
	}

	// 条码查询,商品编号和条码查询
	public ArrayList<GoodsThin> getGoodsThinList(String barcode) {
		ArrayList<GoodsThin> list = new ArrayList<GoodsThin>();
		String array[] = { barcode, barcode, barcode };
		String localString1 = "select g.id,g.name,g.pinyin,g.barcode,g.specification,g.model, g.isusebatch from sz_goods g where isavailable='1' and  barcode=? or id=? or model=?";
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = this.db.rawQuery(localString1, array);
		try {
			while (cursor.moveToNext()) {
				GoodsThin goodsthin = new GoodsThin();
				goodsthin.setId(cursor.getString(0));
				goodsthin.setName(cursor.getString(1));
				goodsthin.setPinyin(cursor.getString(2));
				goodsthin.setBarcode(cursor.getString(3));
				goodsthin.setSpecification(cursor.getString(4));
				goodsthin.setModel(cursor.getString(5));
				goodsthin.setIsusebatch(cursor.getInt(6) == 1 ? true : false);
				list.add(goodsthin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return list;
	}

	// 从 SQL 中 取 数据 匹配
	public ArrayList<GoodsThin> queryGoods(String paramString) {
		Cursor localCursor = null;
		String localString1 = "select g.id,g.name,g.pinyin,g.barcode,g.specification,g.model,g.isusebatch from sz_goods g where g.isavailable='1' and (";
		String[] arrayOfString1 = Utils.GOODS_CHECK_SELECT.split(",");
		int i = 0;
		String[] arrayOfString2 = null;
		int j = 0;
		ArrayList<GoodsThin> localArrayList = null;
		arrayOfString2 = new String[arrayOfString1.length];
		this.db = this.helper.getReadableDatabase();

		if (i <= arrayOfString1.length) {
			for (int k = 0; k < arrayOfString1.length; k++) {
				if (j < arrayOfString2.length) {
					localString1 = localString1 + "g." + arrayOfString1[i] + " like ? ";
					if (i != -1 + arrayOfString1.length) {
						localString1 = localString1 + " or ";
						i++;
					}
					arrayOfString2[k] = ("%" + paramString + "%");
				}
			}
		}
		localString1 = localString1 + ") order by g.id asc";// desc降序asc升序
		try {
			localCursor = this.db.rawQuery(localString1, arrayOfString2);
			localArrayList = new ArrayList<GoodsThin>();
			while (localCursor.moveToNext()) {
				GoodsThin goodsThin = new GoodsThin();
				goodsThin.setId(localCursor.getString(0));
				goodsThin.setName(localCursor.getString(1));
				goodsThin.setPinyin(localCursor.getString(2));
				goodsThin.setBarcode(localCursor.getString(3));
				goodsThin.setSpecification(localCursor.getString(4));
				goodsThin.setModel(localCursor.getString(5));
				if (localCursor.getInt(6) == 1) {
					// 隐藏或显示 批次 1显示 0隐藏
					goodsThin.setIsusebatch(true);
				} else {
					goodsThin.setIsusebatch(false);
				}
				localArrayList.add(goodsThin);
			}
		} catch (Exception e) {

		} finally {
			if (localCursor != null)
				localCursor.close();
			if (this.db != null)
				this.db.close();
		}
		return localArrayList;

	}

	/**
	 * 查询商品 名称 和 规格 是否存在
	 * 
	 * @return
	 */
	public GoodsThin queryGoodsNameANDSpecification(String name, String specification) {
		this.db = this.helper.getReadableDatabase();
		String sql = "select g.id,g.name,g.pinyin,g.barcode,g.specification,g.model, g.isusebatch from sz_goods g where isavailable='1' and  name=? and  specification=?";
		try {
			Cursor cursor = db.rawQuery(sql, new String[] { name, specification });
			if (cursor.moveToNext()) {
				GoodsThin goodsThin = new GoodsThin();
				goodsThin.setId(cursor.getString(0));
				goodsThin.setName(cursor.getString(1));
				goodsThin.setPinyin(cursor.getString(2));
				goodsThin.setBarcode(cursor.getString(3));
				goodsThin.setSpecification(cursor.getString(4));
				goodsThin.setModel(cursor.getString(5));
				goodsThin.setIsusebatch(cursor.getInt(6) == 1 ? true : false);
				return goodsThin;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Deprecated
	public String queryGoodsBigStockNumber(String paramString) {
		return paramString;
	}

	@Deprecated
	public List<GoodsThin> queryGoodsByBarcode(String paramString, long paramLong) {
		return null;
		// this.db = this.helper.getReadableDatabase();
		// SQLiteDatabase localSQLiteDatabase = this.db;
		// String[] arrayOfString = new String[2];
		// arrayOfString[0] = paramString;
		// // arrayOfString[1] = paramLong;
		// Cursor localCursor = localSQLiteDatabase.rawQuery(
		// "select g.id,g.name,g.pinyin,g.barcode,g.specification,g.model,
		// isusebatch from sz_goods g where g.isavailable='1' and g.barcode=?
		// and g.id not in (select f.goodsid from kf_fieldsaleitem f where
		// f.fieldsaleid=?) order by lower(g.pinyin)",
		// arrayOfString);
		// ArrayList localArrayList = new ArrayList();
		// try {
		// while (true) {
		// boolean bool1 = localCursor.moveToNext();
		// if (!bool1)
		// return localArrayList;
		// GoodsThin localGoodsThin = new GoodsThin();
		// localGoodsThin.setId(localCursor.getString(0));
		// localGoodsThin.setName(localCursor.getString(1));
		// localGoodsThin.setPinyin(localCursor.getString(2));
		// localGoodsThin.setBarcode(localCursor.getString(3));
		// localGoodsThin.setSpecification(localCursor.getString(4));
		// localGoodsThin.setModel(localCursor.getString(5));
		// if (localCursor.getInt(6) != 1) {
		// localGoodsThin.setIsusebatch(false);
		// }
		//
		// localArrayList.add(localGoodsThin);
		// }
		// } catch (Exception localException) {
		// while (true) {
		// localException.printStackTrace();
		// boolean bool2 = false;
		// }
		// } finally {
		// if (localCursor != null)
		// localCursor.close();
		// if (this.db != null)
		// this.db.close();
		// }
	}

	public int queryGoodsIndexByPinyin(String str) {
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = this.db.rawQuery(
				"select count(*) from sz_goods where lower(substr(pinyin, 1, 1)) < lower(substr(?, 1, 1))",
				new String[] { str });
		try {
			if (cursor.moveToNext()) {
				return cursor.getInt(0);
			}
		} catch (Exception localException) {
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return 0;
	}

	@Deprecated
	public List<GoodsInfo> queryGoodsInfos(String paramString) {
		return null;
		// this.db = this.helper.getReadableDatabase();
		// String localString1 = "SELECT
		// g.id,g.name,g.pinyin,g.barcode,g.salecue,g.specification,g.model,g.goodsclassid,g.goodsclassname,g.stocknumber,g.bigstocknumber,g.getstocktime,
		// isusebatch FROM sz_goods g WHERE g.isavailable='1' and (";
		// String[] arrayOfString1 = Utils.GOODS_CHECK_SELECT.split(",");
		// int i = 0;
		// while (true) {
		// String[] arrayOfString2;
		// int j = 0;
		// Cursor localCursor = null;
		// ArrayList localArrayList;
		// if (i >= arrayOfString1.length) {
		// String localString2 = localString1 + ") order by g.id asc";
		// arrayOfString2 = new String[arrayOfString1.length];
		// j = 0;
		// if (j < arrayOfString2.length)
		// localCursor = this.db.rawQuery(localString2, arrayOfString2);
		// localArrayList = new ArrayList();
		// }
		// try {
		// while (true) {
		// boolean bool1 = localCursor.moveToNext();
		// if (!bool1) {
		// localString1 = localString1 + "g." + arrayOfString1[i] + " like ? ";
		// if (i != -1 + arrayOfString1.length)
		// localString1 = localString1 + " or ";
		// i++;
		// // arrayOfString2[j] = ("%" + paramString + "%");
		// j++;
		// }
		// GoodsInfo localGoodsInfo = new GoodsInfo();
		// localGoodsInfo.setId(localCursor.getString(0));
		// localGoodsInfo.setName(localCursor.getString(1));
		// localGoodsInfo.setPinyin(localCursor.getString(2));
		// localGoodsInfo.setBarcode(localCursor.getString(3));
		// localGoodsInfo.setSalecue(localCursor.getString(4));
		// localGoodsInfo.setSpecification(localCursor.getString(5));
		// localGoodsInfo.setModel(localCursor.getString(6));
		// localGoodsInfo.setGoodsclassid(localCursor.getString(7));
		// localGoodsInfo.setGoodsclassname(localCursor.getString(8));
		// localGoodsInfo.setStocknumber(localCursor.getString(9));
		// localGoodsInfo.setBigstocknumber(localCursor.getString(10));
		// localGoodsInfo.setGetstocktime(new
		// Timestamp(localCursor.getLong(11)));
		// if (localCursor.getInt(12) != 1)
		// ;
		// // localGoodsInfo.setIsusebatch(bool2);
		// // localArrayList.add(localGoodsInfo);
		// }
		// } catch (Exception localException) {
		// while (true) {
		// localException.printStackTrace();
		// boolean bool2 = false;
		// }
		// } finally {
		// if (localCursor != null)
		// localCursor.close();
		// if (this.db != null)
		// this.db.close();
		// }
		// }
	}

	/**
	 * 查询 所有商品 薄
	 * 
	 * @return
	 */
	public List<GoodsThin> queryGoodsThin() {
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = this.db.rawQuery(
				"select id,name,pinyin,barcode,specification,model , isusebatch from sz_goods where isavailable=\'1\' order by lower(pinyin)",
				null);
		ArrayList<GoodsThin> listGoodsThin = new ArrayList<GoodsThin>();
		try {
			while (cursor.moveToNext()) {
				GoodsThin goodsThin = new GoodsThin();
				goodsThin.setId(cursor.getString(0));
				goodsThin.setName(cursor.getString(1));
				goodsThin.setPinyin(cursor.getString(2));
				goodsThin.setBarcode(cursor.getString(3));
				goodsThin.setSpecification(cursor.getString(4));
				goodsThin.setModel(cursor.getString(5));
				if (cursor.getInt(6) == 1) {
					goodsThin.setIsusebatch(true);
				} else {
					goodsThin.setIsusebatch(false);
				}
				listGoodsThin.add(goodsThin);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return listGoodsThin;
	}

	public void update(String paramString, RespQueryStockWithTimeEntity paramRespQueryStockWithTimeEntity) {
		this.db = this.helper.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("stocknumber", paramRespQueryStockWithTimeEntity.getStockNum());
		localContentValues.put("bigstocknumber", paramRespQueryStockWithTimeEntity.getBigStockNumber());
		localContentValues.put("getstocktime", Long.valueOf(paramRespQueryStockWithTimeEntity.getGetStockTime()));
		this.db.update("sz_goods", localContentValues, "id=?", new String[] { paramString });
	}

	public void update(String paramString1, String paramString2, String paramString3) {
		this.db = this.helper.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put(paramString2, paramString3);
		this.db.update("sz_goods", localContentValues, "id=?", new String[] { paramString1 });
	}

	// TODO 添加商品
	public boolean insertAddGood(Goods goods) {
		if (goods == null || TextUtils.isEmpty(goods.getId())) {
			return false;
		}
		this.db = this.helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", goods.id);
		values.put("name", goods.name);
		values.put("pinyin", goods.pinyin);
		values.put("barcode", goods.barcode);
		values.put("specification", goods.specification);
		values.put("model", goods.model);
		values.put("goodsclassid", goods.goodsclassid);
		values.put("isusebatch", goods.isusebatch);
		values.put("isavailable", goods.isavailable == true ? "1" : "0");
		long insert = db.insert("sz_goods", null, values);
		return insert == -1 ? false : true;
	}
}
