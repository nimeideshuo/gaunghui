package com.ahjswy.cn.cldb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.cldb.bean.sz_pricesystem;

public class CL_sz_pricesystem extends CloudDBBase {

	public List<sz_pricesystem> queryAll() {
		String sql = "select psid,psname,pinyin,ischaodan,ischexiao,remark,islocked,isavailable,builderid,buildtime,modifierid,modifytime,rversion from sz_pricesystem where isavailable =1";
		ResultSet query = executeQuery(sql);
		if (query == null) {
			return null;
		}
		List<sz_pricesystem> listPrice = new ArrayList<sz_pricesystem>();
		try {
			while (query.next()) {
				sz_pricesystem pricesystem = new sz_pricesystem();
				pricesystem.psid = query.getString("psid");
				pricesystem.psname = query.getString("psname");
				pricesystem.pinyin = query.getString("pinyin");
				pricesystem.ischaodan = query.getInt("ischaodan") == 0 ? false : true;
				pricesystem.ischexiao = query.getInt("ischexiao") == 0 ? false : true;
				pricesystem.remark = query.getString("remark");
				pricesystem.islocked = query.getInt("islocked") == 0 ? false : true;
				pricesystem.isavailable = query.getInt("isavailable") == 0 ? false : true;
				pricesystem.builderid = query.getString("builderid");
				pricesystem.buildtime = query.getString("buildtime");
				pricesystem.modifierid = query.getString("modifierid");
				pricesystem.modifytime = query.getString("modifytime");
				pricesystem.rversion = query.getString("rversion");
				listPrice.add(pricesystem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listPrice;
	}

}
