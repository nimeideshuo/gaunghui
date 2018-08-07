package com.ahjswy.cn.bean;

import java.io.Serializable;

public class GoodEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String goods;
	public String goodsPrice;
	public String goodsUnit;
	public String goodsImage;
	// 查询商品平均价
	public String goodsid;
	public String customerid;
	public String buildendtime;
	public String buildbegintime;
	public int unittype;

	public String getBuildendtime() {
		return buildendtime;
	}

	public void setBuildendtime(String buildendtime) {
		this.buildendtime = buildendtime;
	}

	public String getBuildbegintime() {
		return buildbegintime;
	}

	public void setBuildbegintime(String buildbegintime) {
		this.buildbegintime = buildbegintime;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public int getUnittype() {
		return unittype;
	}

	public void setUnittype(int unittype) {
		this.unittype = unittype;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsunit() {
		return goodsUnit;
	}

	public void setGoodsunit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getGoodsimage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

}
