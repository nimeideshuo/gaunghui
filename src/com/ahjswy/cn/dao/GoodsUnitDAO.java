package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.model.Unit;
import com.ahjswy.cn.utils.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class GoodsUnitDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	// 基本单位
	public GoodsUnit getBasicUnit(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		GoodsUnit goodsunit = null;
		Cursor cursor = this.db.rawQuery(
				"select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where isbasic=1 and goodsid = ?",
				new String[] { goodsid });
		try {
			while (cursor.moveToNext()) {
				goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setRatio(cursor.getDouble(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return goodsunit;
	}

	// 获取商品的件数
	public String getBigNum(String goodsid, String unitid, double num) {
		if (!(TextUtils.isEmpty(goodsid) && !TextUtils.isEmpty(unitid))) {
			String v2 = "";
			GoodsUnit v4 = this.getBigUnit(goodsid);
			GoodsUnit v3 = this.getBasicUnit(goodsid);
			if (v4 == null || v3 == null) {
				return "";
			}
			double v0 = num;
			if (!TextUtils.isEmpty(unitid)) {
				v0 *= this.getGoodsUnitRatio(goodsid, unitid);
			}
			int v6 = ((int) (v0 / v4.getRatio()));
			if (v6 != 0) {
				v2 = String.valueOf(v2) + v6 + v4.getUnitname();
				v0 -= (((double) v6)) * v4.getRatio();
			}
			if (v0 == 0) {
				return v2;
			}
			GoodsUnit v5 = this.getMidUnit(goodsid);
			if (v5 != null) {
				v6 = ((int) (v0 / v5.getRatio()));
				if (v6 != 0) {
					v2 = String.valueOf(v2) + v6 + v5.getUnitname();
					v0 -= (((double) v6)) * v5.getRatio();
				}
			}
			v0 = Utils.normalize(v0, 2);
			return v2 + (v0 % 1 == 0 ? (int) v0 + "" : v0 + "") + v3.getUnitname();
		}
		return "";
	}

	// 获取大的单位id
	public GoodsUnit getBigUnit(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		String[] arrays = { goodsid };
		GoodsUnit goodsunit = null;
		Cursor cursor = this.db.rawQuery(
				"select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where isshow=1 and goodsid = ?",
				arrays);
		try {
			while (cursor.moveToNext()) {
				goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setRatio(cursor.getDouble(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}

		return goodsunit;
	}

	// 查询单位
	public GoodsUnit getOneUnit(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		String[] arrays = { goodsid };
		GoodsUnit goodsunit = null;
		Cursor cursor = this.db.rawQuery(
				"select goodsid,unitid,unitname,isbasic,isshow,min(ratio) from sz_goodsunit where goodsid = ?", arrays);
		try {
			while (cursor.moveToNext()) {
				goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setRatio(cursor.getDouble(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}

		return goodsunit;
	}

	public double getBigUnitRatio(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = this.db.rawQuery("select ratio from sz_goodsunit where goodsid=? and isshow='1'",
					new String[] { goodsid });
			if (cursor.moveToNext()) {
				return cursor.getDouble(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return 1.0D;
	}

	public GoodsUnit getGoodsUnit(String goodsid, String unitid) {
		this.db = this.helper.getReadableDatabase();
		String[] arrays = { goodsid, unitid };
		GoodsUnit goodsunit = null;
		Cursor cursor = null;
		try {
			cursor = this.db.rawQuery(
					"select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where goodsid = ? and unitid=?",
					arrays);
			while (cursor.moveToNext()) {
				goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setRatio(cursor.getDouble(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}

		return goodsunit;
	}

	// TODO 获取商品 规格 1*多少
	public double getGoodsUnitRatio(String goodsid, String unitid) {
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = this.db.rawQuery("select a.ratio from sz_goodsunit a where goodsid=? and unitid=?",
					new String[] { goodsid, unitid });
			if (cursor.moveToNext()) {
				return cursor.getDouble(0);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return 1.0D;
	}

	// TODO 查询商品换算比例
	public GoodsUnit queryBigUnitRatio(String goodsid, String unitid) {
		this.db = this.helper.getReadableDatabase();
		String[] arrays = { goodsid, unitid };
		GoodsUnit goodsunit = null;
		Cursor cursor = this.db.rawQuery(
				"select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where goodsid =? and unitid=?",
				arrays);
		try {
			while (cursor.moveToNext()) {
				goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setRatio(cursor.getDouble(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return goodsunit;
	}

	public GoodsUnit getMidUnit(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		String[] arrays = { goodsid };
		GoodsUnit goodsunit = null;
		Cursor cursor = this.db.rawQuery(
				"select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where goodsid=? and ratio > 0 and ratio < 1 limit 1 offset 0",
				arrays);
		try {
			while (cursor.moveToNext()) {
				goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setRatio(cursor.getDouble(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}
		return goodsunit;
	}

	public boolean isBasicUnit(String goodsid, String unitid) {

		this.db = this.helper.getReadableDatabase();
		Cursor cursor = this.db.rawQuery("select isbasic from sz_goodsunit where goodsid=? and unitid=?",
				new String[] { goodsid, unitid });
		try {
			if (cursor.moveToNext()) {
				if (cursor.getInt(0) == 1) {
					return true;
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
		return false;
	}

	// 查询基本位
	public GoodsUnit queryBaseUnit(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		String[] arrays = { goodsid };
		Cursor cursor = null;
		try {
			cursor = this.db.rawQuery(
					"select a.goodsid,a.unitid,a.unitname,a.isbasic,a.isshow,a.ratio from sz_goodsunit a where isbasic=1 and goodsid =? ",
					arrays);
			while (cursor.moveToNext()) {
				GoodsUnit goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setRatio(cursor.getDouble(5));
				return goodsunit;
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

	// 查询计件单位
	public GoodsUnit queryBigUnit(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		String[] arrays = { goodsid };
		GoodsUnit goodsunit = null;
		Cursor cursor = this.db.rawQuery(
				"select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where isshow=1 and goodsid =?",
				arrays);
		try {
			while (cursor.moveToNext()) {
				goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setRatio(cursor.getDouble(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (this.db != null)
				this.db.close();
		}

		return goodsunit;
	}

	// 查询 商品单位
	public List<GoodsUnit> queryGoodsUnits(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = this.db.rawQuery(
				"select goodsid,unitid,unitname,ratio,isbasic,isshow from sz_goodsunit where goodsid=? order by ratio",
				new String[] { goodsid });
		ArrayList<GoodsUnit> localArrayList = new ArrayList<GoodsUnit>();
		try {
			while (cursor.moveToNext()) {
				GoodsUnit goodsunit = new GoodsUnit();
				goodsunit.setGoodsid(cursor.getString(0));
				goodsunit.setUnitid(cursor.getString(1));
				goodsunit.setUnitname(cursor.getString(2));
				goodsunit.setRatio(cursor.getDouble(3));
				goodsunit.setIsbasic(cursor.getInt(4) == 1 ? true : false);
				goodsunit.setIsshow(cursor.getInt(5) == 1 ? true : false);
				localArrayList.add(goodsunit);
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

	public GoodsUnit queryUnit(String unitid) {
		this.db = this.helper.getReadableDatabase();
		Cursor cursor = this.db.rawQuery(
				"select goodsid,unitid,unitname,ratio,isbasic,isshow from sz_goodsunit where unitid=? ",
				new String[] { unitid });
		try {
			while (cursor.moveToNext()) {
				GoodsUnit goodsUnit = new GoodsUnit();
				goodsUnit.setGoodsid(cursor.getString(0));
				goodsUnit.setUnitid(cursor.getString(1));
				goodsUnit.setUnitname(cursor.getString(2));
				goodsUnit.setRatio(cursor.getDouble(3));
				goodsUnit.setIsbasic(cursor.getInt(4) == 1 ? true : false);
				goodsUnit.setIsshow(cursor.getInt(5) == 1 ? true : false);
				return goodsUnit;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Unit> queryAll() {
		db = helper.getReadableDatabase();
		List<Unit> listUnit = new ArrayList<Unit>();
		Cursor cursor = null;
		try {

			String sql = "select id,name from sz_unit where isavailable = '1'";
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				Unit unit = new Unit();
				unit.setId(cursor.getString(0));
				unit.setName(cursor.getString(1));
				listUnit.add(unit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return listUnit;
	}

	/**
	 * 添加商品单位
	 * 
	 * @param fromJson
	 * @return
	 */
	public boolean insetAddGoodUnit(GoodsUnit unit) {
		this.db = this.helper.getWritableDatabase();
		try {

			String sql = "insert into sz_goodsunit(goodsid,unitid,unitname,isbasic,isshow,ratio) select ?,?,name,?,?,? from sz_unit where id=?";
			db.execSQL(sql, new String[] { unit.goodsid, unit.unitid, unit.isbasic == true ? "1" : "0",
					unit.isshow == true ? "1" : "0", unit.ratio + "", unit.getUnitid() });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}