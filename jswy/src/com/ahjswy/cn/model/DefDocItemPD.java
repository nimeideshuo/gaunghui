package com.ahjswy.cn.model;

import java.io.Serializable;

public class DefDocItemPD implements Serializable {
	private static final long serialVersionUID = 1L;
	private String barcode;
	private String batch;
	private String bignetnum;
	private String bignum;
	private String bigstocknum;
	private double costprice;
	private long docid;
	private String goodsid;
	private String goodsname;
	private boolean isusebatch;
	private long itemid;
	private String model;
	private double netamount;
	private double netnum;
	private double num;
	private String productiondate;
	private String remark;
	private long rversion;
	private String specification;
	private double stocknum;
	private String unitid;
	private String unitname;

	public DefDocItemPD() {
	}

	public DefDocItemPD(DefDocItemPD paramDefDocItemPD) {
		this.itemid = paramDefDocItemPD.getItemid();
		this.docid = paramDefDocItemPD.getDocid();
		this.goodsid = paramDefDocItemPD.getGoodsid();
		this.goodsname = paramDefDocItemPD.getGoodsname();
		this.unitid = paramDefDocItemPD.getUnitid();
		this.unitname = paramDefDocItemPD.getUnitname();
		this.num = paramDefDocItemPD.getNum();
		this.bignum = paramDefDocItemPD.getBignum();
		this.netnum = paramDefDocItemPD.getNetnum();
		this.bigstocknum = paramDefDocItemPD.getBigstocknum();
		this.stocknum = paramDefDocItemPD.getStocknum();
		this.bignetnum = paramDefDocItemPD.getBignetnum();
		this.netamount = paramDefDocItemPD.getNetamount();
		this.costprice = paramDefDocItemPD.getCostprice();
		this.remark = paramDefDocItemPD.getRemark();
		this.rversion = paramDefDocItemPD.getRversion();
		this.barcode = paramDefDocItemPD.getBarcode();
		this.model = paramDefDocItemPD.getModel();
		this.specification = paramDefDocItemPD.getSpecification();
		this.isusebatch = paramDefDocItemPD.isusebatch;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public String getBatch() {
		return this.batch;
	}

	public String getBignetnum() {
		return this.bignetnum;
	}

	public String getBignum() {
		return this.bignum;
	}

	public String getBigstocknum() {
		return this.bigstocknum;
	}

	public double getCostprice() {
		return this.costprice;
	}

	public long getDocid() {
		return this.docid;
	}

	public String getGoodsid() {
		return this.goodsid;
	}

	public String getGoodsname() {
		return this.goodsname;
	}

	public long getItemid() {
		return this.itemid;
	}

	public String getModel() {
		return this.model;
	}

	public double getNetamount() {
		return this.netamount;
	}

	public double getNetnum() {
		return this.netnum;
	}

	public double getNum() {
		return this.num;
	}

	public String getProductiondate() {
		return this.productiondate;
	}

	public String getRemark() {
		return this.remark;
	}

	public long getRversion() {
		return this.rversion;
	}

	public String getSpecification() {
		return this.specification;
	}

	public double getStocknum() {
		return this.stocknum;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public boolean isIsusebatch() {
		return this.isusebatch;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public void setBignetnum(String bignetnum) {
		this.bignetnum = bignetnum;
	}

	public void setBignum(String bignum) {
		this.bignum = bignum;
	}

	public void setBigstocknum(String bigstocknum) {
		this.bigstocknum = bigstocknum;
	}

	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public void setIsusebatch(boolean isusebatch) {
		this.isusebatch = isusebatch;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setNetamount(double netamount) {
		this.netamount = netamount;
	}

	public void setNetnum(double netnum) {
		this.netnum = netnum;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public void setProductiondate(String productiondate) {
		this.productiondate = productiondate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRversion(long rversion) {
		this.rversion = rversion;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setStocknum(double stocknum) {
		this.stocknum = stocknum;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	@Override
	public String toString() {
		return "DefDocItemPD [barcode=" + barcode + ", batch=" + batch + ", bignetnum=" + bignetnum + ", bignum="
				+ bignum + ", bigstocknum=" + bigstocknum + ", costprice=" + costprice + ", docid=" + docid
				+ ", goodsid=" + goodsid + ", goodsname=" + goodsname + ", isusebatch=" + isusebatch + ", itemid="
				+ itemid + ", model=" + model + ", netamount=" + netamount + ", netnum=" + netnum + ", num=" + num
				+ ", productiondate=" + productiondate + ", remark=" + remark + ", rversion=" + rversion
				+ ", specification=" + specification + ", stocknum=" + stocknum + ", unitid=" + unitid + ", unitname="
				+ unitname + "]";
	}

}