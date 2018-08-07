package com.ahjswy.cn.dao;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.model.Region;

import android.database.Cursor;

public class RegionDAO extends BaseDao {
	public List<Region> getAllRegions() {
		this.db = this.helper.getReadableDatabase();
		Cursor localCursor = this.db.rawQuery("select id,name,pinyin from sz_region where isavailable='1'", null);
		ArrayList<Region> localArrayList = new ArrayList<Region>();
		try {
			while (localCursor.moveToNext()) {
				Region localRegion = new Region();
				localRegion.setId(localCursor.getString(0));
				localRegion.setName(localCursor.getString(1));
				localRegion.setPinyin(localCursor.getString(2));
				localArrayList.add(localRegion);
			}
			return localArrayList;
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (localCursor != null)
				localCursor.close();
			if (this.db != null)
				this.db.close();
		}
		return localArrayList;
	}

}
