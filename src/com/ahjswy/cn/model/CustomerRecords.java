package com.ahjswy.cn.model;

//客户商品客史
public class CustomerRecords {
	public String customerid;
	public String goodsid;
	public String goodsthirdclassid;
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

	public String getGoodsthirdclassid() {
		return goodsthirdclassid;
	}

	public void setGoodsthirdclassid(String goodsthirdclassid) {
		this.goodsthirdclassid = goodsthirdclassid;
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
		return "CustomerRecords [customerid=" + customerid + ", goodsid=" + goodsid + ", goodsthirdclassid="
				+ goodsthirdclassid + ", unitid=" + unitid + ", price=" + price + "]";
	}
	
}
