package com.ahjswy.cn.bean;

import java.io.Serializable;

public class DefDocItemDD implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 条码
	private String barcode;
	// 商品 ID
	private String goodsid;
	// 规格
	private String specification;
	// 手机
	private String model;
	// 商品名称
	private String goodsname;
	private String unitid;
	private String unitname;
	private double num;
	private double discountratio;
	private String remark;
	private String bignum;
	private double price;
	private double subtotal;
	private double discountsubtotal;
	private long docid;
	private long itemid;
	// 版本号
	private long rversion;
	private boolean isgift;
	private boolean isdiscount;
	private double discountprice;
	private double arrivalnum;
	private String bigarrivalnum;
	private boolean isusebatch;
	public String showStock;
	public String stocknum;
	public String bigstocknumber;

	public DefDocItemDD() {
	}

	public DefDocItemDD(DefDocItemDD docItemDD) {
		super();
		this.barcode = docItemDD.getBarcode();
		this.goodsid = docItemDD.getGoodsid();
		this.specification = docItemDD.getSpecification();
		this.model = docItemDD.getModel();
		this.goodsname = docItemDD.getGoodsname();
		this.unitid = docItemDD.getUnitid();
		this.unitname = docItemDD.getUnitname();
		this.num = docItemDD.getNum();
		this.discountratio = docItemDD.getDiscountratio();
		this.remark = docItemDD.getRemark();
		this.bignum = docItemDD.getBignum();
		this.price = docItemDD.getPrice();
		this.subtotal = docItemDD.getSubtotal();
		this.discountsubtotal = docItemDD.getDiscountsubtotal();
		this.docid = docItemDD.getDocid();
		this.itemid = docItemDD.getItemid();
		this.rversion = docItemDD.getRversion();
		this.isgift = docItemDD.isIsgift();
		this.isdiscount = docItemDD.isIsdiscount();
		this.discountprice = docItemDD.getDiscountprice();
		this.arrivalnum = docItemDD.getArrivalnum();
		this.bigarrivalnum = docItemDD.getBigarrivalnum();
		this.isusebatch = docItemDD.isIsusebatch();
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public double getDiscountratio() {
		return discountratio;
	}

	public void setDiscountratio(double discountratio) {
		this.discountratio = discountratio;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBignum() {
		return bignum;
	}

	public void setBignum(String bignum) {
		this.bignum = bignum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getDiscountsubtotal() {
		return discountsubtotal;
	}

	public void setDiscountsubtotal(double discountsubtotal) {
		this.discountsubtotal = discountsubtotal;
	}

	public long getDocid() {
		return docid;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public long getRversion() {
		return rversion;
	}

	public void setRversion(long rversion) {
		this.rversion = rversion;
	}

	public boolean isIsgift() {
		return isgift;
	}

	public void setIsgift(boolean isgift) {
		this.isgift = isgift;
	}

	public boolean isIsdiscount() {
		return isdiscount;
	}

	public void setIsdiscount(boolean isdiscount) {
		this.isdiscount = isdiscount;
	}

	public double getDiscountprice() {
		return discountprice;
	}

	public void setDiscountprice(double discountprice) {
		this.discountprice = discountprice;
	}

	public double getArrivalnum() {
		return arrivalnum;
	}

	public void setArrivalnum(double arrivalnum) {
		this.arrivalnum = arrivalnum;
	}

	public String getBigarrivalnum() {
		return bigarrivalnum;
	}

	public void setBigarrivalnum(String bigarrivalnum) {
		this.bigarrivalnum = bigarrivalnum;
	}

	public boolean isIsusebatch() {
		return isusebatch;
	}

	public void setIsusebatch(boolean isusebatch) {
		this.isusebatch = isusebatch;
	}

	@Override
	public String toString() {
		return "DefDocItemDD [barcode=" + barcode + ", goodsid=" + goodsid + ", specification=" + specification
				+ ", model=" + model + ", goodsname=" + goodsname + ", unitid=" + unitid + ", unitname=" + unitname
				+ ", num=" + num + ", discountratio=" + discountratio + ", remark=" + remark + ", bignum=" + bignum
				+ ", price=" + price + ", subtotal=" + subtotal + ", discountsubtotal=" + discountsubtotal + ", docid="
				+ docid + ", itemid=" + itemid + ", rversion=" + rversion + ", isgift=" + isgift + ", isdiscount="
				+ isdiscount + ", discountprice=" + discountprice + ", arrivalnum=" + arrivalnum + ", bigarrivalnum="
				+ bigarrivalnum + ", isusebatch=" + isusebatch + "]";
	}

}
