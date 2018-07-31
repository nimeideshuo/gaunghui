package com.ahjswy.cn.app;

import java.util.HashMap;
import java.util.List;

import com.ahjswy.cn.print.BTPrinter;
import com.ahjswy.cn.utils.JSONUtil;

import android.content.SharedPreferences;

public class AccountPreference {
	public static final String BASIC_DATA_UPDATETIME = "basic_data_updatitme";
	public static final String BIZINFO = "bizinfo";
	public static final String CUSTOMER_CHECK_SELECT = "customer_check_select";
	public static final String GOODS_CHECK_SELECT = "goods_check_select";
	public static final String GOODS_SELECT_MORE = "goods_select_more";
	public static final String ITEM_ORDER = "item_order";
	public static final String MAX_DBRVERSION = "max_rversion";
	public static final String NET_SETTING = "net_setting";
	public static final String OPEN_DOC_SET = "open_doc_set";
	public static final String ViewChangeprice = "ViewChangeprice";
	public static final String ViewKCStockBrowse = "ViewKCStockBrowse";
	public static final String WORK_DEPARTMENT = "wrok_department";
	private final String BIZPARAMETER_INFO = "bizparameter_info";
	private final String SETTING = "se_do";
	private SharedPreferences bizparamiterPreferences = MyApplication.getInstance()
			.getSharedPreferences(BIZPARAMETER_INFO, 0);
	private SharedPreferences setPreferences = MyApplication.getInstance().getSharedPreferences(SETTING, 0);

	public List<HashMap<String, String>> getBizInfoMap() {
		return JSONUtil.parse2ListMap(this.bizparamiterPreferences.getString(BIZINFO, "[]"));
	}

	public String getServerIp() {
		return getValue("ip");
	}

	public String getCGServerPort() {
		return getValue("cgPort");
	}

	public void setCGServerPort(String cgip) {
		setValue("cgPort", cgip);
	}
	public String getDBServerPort() {
		return getValue("dbPort");
	}
	public void setDBServerPort(String port) {
		setValue("dbPort", port);
	}
	public String getServerPort() {
		return getValue("servicePort");
	}
	public void setServerPort(String port) {
		setValue("servicePort", port);
	}


	public <T> T getValue(String value, Class<T> obj) {
		return JSONUtil.readValue(this.setPreferences.getString(value, ""), obj);
	}

	public String getValue(String value) {
		return this.setPreferences.getString(value, "");
	}

	public String getValue(String value, String defaultValue) {
		return this.setPreferences.getString(value, defaultValue);
	}

	public boolean saveBizInfo(String value) {
		SharedPreferences.Editor localEditor = this.bizparamiterPreferences.edit();
		localEditor.clear();
		localEditor.putString("bizinfo", value);
		return localEditor.commit();
	}

	public boolean setServerIp(String ip) {
		return setValue("ip", ip);
	}

	public boolean setValue(String value, Object obj) {
		String str = JSONUtil.object2Json(obj);
		return this.setPreferences.edit().putString(value, str).commit();
	}

	public boolean setValue(String key, String value) {
		return this.setPreferences.edit().putString(key, value).commit();
	}

	public boolean savePrinter(BTPrinter printer) {
		return this.setPreferences.edit().putString("printername", printer.getName())
				.putString("printeradress", printer.getAddress()).commit();
	}

	public BTPrinter getPrinter() {
		String printername = this.setPreferences.getString("printername", null);
		String printeradress = this.setPreferences.getString("printeradress", null);
		if (printername != null && printeradress != null) {
			return new BTPrinter(printername, printeradress);
		}
		return null;
	}
}