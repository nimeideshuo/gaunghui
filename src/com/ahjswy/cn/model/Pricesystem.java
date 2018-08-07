package com.ahjswy.cn.model;

import java.io.Serializable;

public class Pricesystem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String psid;
	public String psname;
	public String pinyin;
	public double price;
	public String unitid;
	public String pricesystemid;

	public String getPricesystemid() {
		return pricesystemid;
	}

	public void setPricesystemid(String pricesystemid) {
		this.pricesystemid = pricesystemid;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPsid() {
		return psid;
	}

	public void setPsid(String psid) {
		this.psid = psid;
	}

	public String getPsname() {
		return psname;
	}

	public void setPsname(String psname) {
		this.psname = psname;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

}
