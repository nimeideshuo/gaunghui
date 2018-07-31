package com.ahjswy.cn.model;

public class UnitidPrice {
	public String customerid;
	public String goodsid;
	public String unitid;
	public double price;
	
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
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

	@Override
	public String toString() {
		return "UnitidPrice [customerid=" + customerid + ", goodsid=" + goodsid + ", unitid=" + unitid + ", price="
				+ price + "]";
	}
	
}
