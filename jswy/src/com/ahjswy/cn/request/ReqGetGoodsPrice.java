package com.ahjswy.cn.request;

public class ReqGetGoodsPrice {
	String customerid;
	String goodsid;
	String unitid;

	public ReqGetGoodsPrice() {
		super();
	}

	public ReqGetGoodsPrice(String customerid, String goodsid, String unitid) {
		super();
		this.customerid = customerid;
		this.goodsid = goodsid;
		this.unitid = unitid;
	}

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

}
