package com.ahjswy.cn.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ahjswy.cn.bean.QueryEntity;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.utils.HttpRequestUtils;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.TextUtils;

/**
 * 网络请求支付接口
 * 
 * @author Administrator
 *
 */
public class ServicePayType {
	public static String baseAddress = "LoginService/PayTypes";

	/**
	 * 获取所有支付方式, 0,0代表不分页
	 */
	public static List<DefDocPayType> PayTypes(int length, int index) {
		QueryEntity entity = new QueryEntity();
		entity.setIndex(index);
		entity.setLength(length);
		List<DefDocPayType> payTypelist = null;
		if (payTypelist == null) {
			payTypelist = new ArrayList<DefDocPayType>();
		}
		String payType = new HttpRequestUtils().Post(baseAddress, JSONUtil.toJSONString(entity));
		try {
			if (TextUtils.isEmpty(payType)) {
				return null;
			}
			JSONObject object = new JSONObject(payType);
			String jsonData = object.getString("Json");
			JSONObject datas = new JSONObject(jsonData);
			String PayTypeData = datas.getString("Data");
			JSONArray array = new JSONArray(PayTypeData);
			for (int i = 0; i < array.length(); i++) {
				DefDocPayType payType2 = new DefDocPayType();
				JSONObject jsonObject = array.getJSONObject(i);
				payType2.setPaytypeid(jsonObject.getString("id"));
				payType2.setPaytypename(jsonObject.getString("name"));
				payTypelist.add(payType2);
			}
			return payTypelist;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有支付方式, 0,0代表不分页
	 */
	public static List<DefDocPayType> PayTypes(String ip, int length, int index) {
		QueryEntity entity = new QueryEntity();
		entity.setIndex(index);
		entity.setLength(length);
		List<DefDocPayType>	payTypelist = new ArrayList<DefDocPayType>();
		String payType = new HttpRequestUtils().PostIp(ip, baseAddress, JSONUtil.toJSONString(entity));
		try {
			if (TextUtils.isEmpty(payType)) {
				return payTypelist;
			}
			JSONObject object = new JSONObject(payType);
			String jsonData = object.getString("Json");
			JSONObject datas = new JSONObject(jsonData);
			String PayTypeData = datas.getString("Data");
			JSONArray array = new JSONArray(PayTypeData);
			for (int i = 0; i < array.length(); i++) {
				DefDocPayType payType2 = new DefDocPayType();
				JSONObject jsonObject = array.getJSONObject(i);
				payType2.setItemid(Long.parseLong(jsonObject.getString("id")));
				payType2.setPaytypename(jsonObject.getString("name"));
				payTypelist.add(payType2);
			}
			return payTypelist;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
