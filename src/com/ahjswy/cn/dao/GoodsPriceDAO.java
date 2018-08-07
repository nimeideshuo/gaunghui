package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.response.RespGoodsPriceEntity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GoodsPriceDAO {
	private SQLiteDatabase db;
	private DBOpenHelper helper = new DBOpenHelper();

	/**
	 * 查询商品 预设的价格体系
	 * 
	 * @param goodsid
	 * @param unitid
	 * @param pricesystemid
	 * @return
	 */
	public double queryBasicPrice(String goodsid, String pricesystemid) {
		this.db = this.helper.getReadableDatabase();
		String sql = "select price from sz_goodsprice where goodsid=? and unitid=(select unitid from sz_goodsunit where goodsid=? and isbasic='1') and pricesystemid=?";
		try {
			Cursor cursor = db.rawQuery(sql, new String[] { goodsid, goodsid,
					pricesystemid.length() == 1 ? "0" + pricesystemid : pricesystemid });
			if (cursor.moveToNext()) {
				return cursor.getDouble(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	/**
	 * 查询商品 预设的价格体系
	 * 
	 * @param goodsid
	 * @param unitid
	 * @param pricesystemid
	 *            价格体系id
	 * @return
	 */
	public double queryBasicPrice(String goodsid, String unitid, String pricesystemid) {
		this.db = this.helper.getReadableDatabase();
		String sql = "select price from sz_goodsprice where goodsid=? and unitid=? and pricesystemid=?";
		try {
			Cursor cursor = db.rawQuery(sql, new String[] { goodsid, unitid,
					pricesystemid.length() == 1 ? "0" + pricesystemid : pricesystemid });
			if (cursor.moveToNext()) {
				return cursor.getDouble(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	/**
	 * 查询商品价格体系
	 * 
	 * @param goodsid
	 * @return
	 */
	public List<RespGoodsPriceEntity> queryPriceList(String goodsid) {
		this.db = this.helper.getReadableDatabase();
		String sql = "select goodsid,unitid,unitname,pricesystemid,pricesystemname,price from sz_goodsprice where goodsid=? and price!='0.0' and price is not null ";
		List<RespGoodsPriceEntity> listPriceEntity = new ArrayList<RespGoodsPriceEntity>();
		try {
			Cursor cursor = db.rawQuery(sql, new String[] { goodsid, });
			while (cursor.moveToNext()) {
				RespGoodsPriceEntity entity = new RespGoodsPriceEntity();
				entity.setGoodsid(cursor.getString(0));
				entity.setUnitid(cursor.getString(1));
				entity.setUnitname(cursor.getString(2));
				entity.setPricesystemid(cursor.getString(3));
				entity.setPricesystemname(cursor.getString(4));
				entity.setPrice(cursor.getDouble(5));
				listPriceEntity.add(entity);
			}
			return listPriceEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listPriceEntity;
	}

	/**
	 * 增加 商品 价格体系
	 * 
	 * @param entity
	 * @return
	 */
	public boolean insert(RespGoodsPriceEntity entity) {
		this.db = this.helper.getWritableDatabase();
		try {
			String sql = "insert into sz_goodsprice(goodsid,unitid,unitname,pricesystemid,pricesystemname,price) select ?,?,?,?,psname,? from sz_pricesystem where psid=?";
			db.execSQL(sql, new String[] { entity.getGoodsid(), entity.getUnitid(), entity.getUnitname(),
					entity.getPricesystemid(), entity.getPrice() + "", entity.getPricesystemid() });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
