package com.ahjswy.cn.cldb;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.cldb.bean.sz_goodsclass;

public class CL_sz_goodsclass extends CloudDBBase {
	public List<sz_goodsclass> queryAll() {
		try {
			List<sz_goodsclass> listGoodsClass = new ArrayList<sz_goodsclass>();
			String sql = "SELECT id,name,pinyin,parentgoodsclassid,isavailable,remark,builderid,buildtime,modifierid,modifytime from sz_goodsclass where isavailable =1";
			ResultSet query = executeQuery(sql);
			if (query == null) {
				return null;
			}
			while (query.next()) {
				sz_goodsclass goodsclass = new sz_goodsclass();
				goodsclass.id = query.getString("id");
				goodsclass.name = query.getString("name");
				goodsclass.pinyin = query.getString("pinyin");
				goodsclass.parentgoodsclassid = query.getString("parentgoodsclassid");
				goodsclass.isavailable = query.getInt("isavailable") == 0 ? false : true;
				goodsclass.builderid = query.getString("builderid");
				goodsclass.buildtime = query.getString("buildtime");
				goodsclass.modifierid = query.getString("modifierid");
				// goodsclass.rversion = query.getString("rversion");
				goodsclass.modifytime = query.getString("modifytime");
				listGoodsClass.add(goodsclass);
			}
			return listGoodsClass;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
