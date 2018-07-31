package com.ahjswy.cn.app;

import com.ahjswy.cn.model.DBUser;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.User;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.request.ReqStrSearchDoc;
import com.ahjswy.cn.response.AccountSetEntity;
import com.ahjswy.cn.utils.JSONUtil;

import android.content.SharedPreferences;
import android.os.StrictMode;

public class SystemState {
	public static final String GPS_INTERVAL = "gpsinterval";
	public static final String P_ACCOUNTSET = "accountset";
	public static final String P_CUR_USER = "cu_user";
	public static final String P_DOC = "doc";
	public static final String P_Department = "department";
	public static final String P_Warehouse = "warehouse";
	public static final String SETTING = "basic_setting";
	public static SharedPreferences basic_setting;
	public static ReqStrSearchDoc conditionDB;
	public static ReqStrSearchDoc conditionPD;
	public static ReqStrSearchDoc conditionXS;
	public static final String[] customer_select_items;
	public static final String[] customer_select_keys;
	public static final String[] goods_select_items;
	public static final String[] goods_select_keys = { "id", "pinyin", "name", "barcode" };
	public static String random;
	public static final String defaultTime = "2000-01-01";
	static {
		goods_select_items = new String[] { "编号", "拼音", "名称", "条形码" };
		customer_select_keys = new String[] { "id", "pinyin", "name" };
		customer_select_items = new String[] { "编号", "拼音", "名称" };
		conditionXS = null;
		conditionDB = null;
		conditionPD = null;
		random = "";
		basic_setting = MyApplication.getInstance().getSharedPreferences("basic_setting", 0);
	}

	public static void enableStrictMode() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().build());
	}

	// 获取工作帐套
	public static AccountSetEntity getAccountSet() {
		return ((AccountSetEntity) getObject("accountset", AccountSetEntity.class));
	}

	// 获取工作部门
	public static Department getDepartment() {
		return ((Department) getObject("department", Department.class));
	}

	public static <T> T getObject(String key, Class<T> defValue) {
		return JSONUtil.readValue(basic_setting.getString(key, ""), defValue);
	}

	// 获取 user 用户
	public static User getUser() {
		return ((User) getObject("cu_user", User.class));
	}

	// 获取仓库
	public static Warehouse getWarehouse() {
		return ((Warehouse) getObject("warehouse", Warehouse.class));
	}

	// 获取数据库用户密码
	public static DBUser getDBUser() {
		return ((DBUser) getObject("dbUser", DBUser.class));
	}

	public static boolean saveObject(String key, Object value) {
		SharedPreferences.Editor localEditor = basic_setting.edit();
		localEditor.putString(key, JSONUtil.object2Json(value));
		return localEditor.commit();
	}
}