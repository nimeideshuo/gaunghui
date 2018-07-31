package com.ahjswy.cn.model;

import java.io.Serializable;

public class DefDocItemTH implements Serializable {
	private static final long serialVersionUID = 1L;
	// 条码
	private String barcode;
	// 单位
	private String batch;
	// 数量
	private String bignum;
	// 成本价
	private double costprice;
	// 单价
	private double discountprice;
	// 折扣
	private double discountratio;
	// 折扣单价
	private double discountsubtotal;
	private long docid;
	// 商品 ID
	private String goodsid;
	// 商品名称
	private String goodsname;
	// 折扣
	private boolean isdiscount;
	// 赠品
	private boolean isgift;
	private boolean isusebatch;
	private long itemid;
	// 数量
	private double num;
	// 价格
	private double price;
	// 备注
	private String remark;
	// 版本号
	private long rversion;
	// 规格
	private String specification;
	// 小计
	private double subtotal;
	// 单位id
	private String unitid;
	// 单位 名称
	private String unitname;
	// 仓库 id
	private String warehouseid;
	// 仓库名称
	private String warehousename;

	public DefDocItemTH() {
	}

	public DefDocItemTH(DefDocItemTH paramDefDocItem) {
		this.itemid = paramDefDocItem.getItemid();
		this.docid = paramDefDocItem.getDocid();
		this.goodsid = paramDefDocItem.getGoodsid();
		this.goodsname = paramDefDocItem.getGoodsname();
		this.warehouseid = paramDefDocItem.getWarehouseid();
		this.warehousename = paramDefDocItem.getWarehousename();
		this.unitid = paramDefDocItem.getUnitid();
		this.unitname = paramDefDocItem.getUnitname();
		this.num = paramDefDocItem.getNum();
		this.bignum = paramDefDocItem.getBignum();
		this.price = paramDefDocItem.getPrice();
		this.subtotal = paramDefDocItem.getSubtotal();
		this.discountratio = paramDefDocItem.getDiscountratio();
		this.discountprice = paramDefDocItem.getDiscountprice();
		this.discountsubtotal = paramDefDocItem.getDiscountsubtotal();
		this.isgift = paramDefDocItem.isgift;
		this.remark = paramDefDocItem.getRemark();
		this.rversion = paramDefDocItem.getRversion();
		this.batch = paramDefDocItem.getBatch();
		this.barcode = paramDefDocItem.getBarcode();
		this.specification = paramDefDocItem.getSpecification();
		this.costprice = paramDefDocItem.getCostprice();
		this.isdiscount = paramDefDocItem.isdiscount;
		this.isusebatch = paramDefDocItem.isusebatch;
	}

	public DefDocItemTH(DefDocItemXS paramDefDocItemXS) {
		this.itemid = paramDefDocItemXS.getItemid();
		this.docid = paramDefDocItemXS.getDocid();
		this.goodsid = paramDefDocItemXS.getGoodsid();
		this.goodsname = paramDefDocItemXS.getGoodsname();
		this.warehouseid = paramDefDocItemXS.getWarehouseid();
		this.warehousename = paramDefDocItemXS.getWarehousename();
		this.unitid = paramDefDocItemXS.getUnitid();
		this.unitname = paramDefDocItemXS.getUnitname();
		this.num = paramDefDocItemXS.getNum();
		this.bignum = paramDefDocItemXS.getBignum();
		this.price = paramDefDocItemXS.getPrice();
		this.subtotal = paramDefDocItemXS.getSubtotal();
		this.discountratio = paramDefDocItemXS.getDiscountratio();
		this.discountprice = paramDefDocItemXS.getDiscountprice();
		this.discountsubtotal = paramDefDocItemXS.getDiscountsubtotal();
		this.isgift = paramDefDocItemXS.isIsgift();
		this.remark = paramDefDocItemXS.getRemark();
		this.rversion = paramDefDocItemXS.getRversion();
		this.barcode = paramDefDocItemXS.getBarcode();
		this.specification = paramDefDocItemXS.getSpecification();
		this.costprice = paramDefDocItemXS.getCostprice();
		this.isdiscount = paramDefDocItemXS.isIsdiscount();
		this.isusebatch = paramDefDocItemXS.isIsusebatch();
		this.batch = paramDefDocItemXS.getBatch();
	}

	public String getBarcode() {
		return this.barcode;
	}

	public String getBatch() {
		return this.batch;
	}

	public String getBignum() {
		return this.bignum;
	}

	public double getCostprice() {
		return this.costprice;
	}

	public double getDiscountprice() {
		return this.discountprice;
	}

	public double getDiscountratio() {
		return this.discountratio;
	}

	public double getDiscountsubtotal() {
		return this.discountsubtotal;
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

	public double getNum() {
		return this.num;
	}

	public double getPrice() {
		return this.price;
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

	public double getSubtotal() {
		return this.subtotal;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public String getWarehousename() {
		return this.warehousename;
	}

	public boolean isIsdiscount() {
		return this.isdiscount;
	}

	public boolean isIsgift() {
		return this.isgift;
	}

	// 批次是否显示
	public boolean isIsusebatch() {
		return this.isusebatch;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public void setBignum(String bignum) {
		this.bignum = bignum;
	}

	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}

	public void setDiscountprice(double discountprice) {
		this.discountprice = discountprice;
	}

	public void setDiscountratio(double discountratio) {
		this.discountratio = discountratio;
	}

	public void setDiscountsubtotal(double discountsubtotal) {
		this.discountsubtotal = discountsubtotal;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public void setGoodsid(String goodsid) {
		if (!goodsid.isEmpty()) {
			this.goodsid = goodsid;
			return;
		}
		this.goodsid = null;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public void setIsdiscount(boolean isdiscount) {
		this.isdiscount = isdiscount;
	}

	public void setIsgift(boolean isgift) {
		this.isgift = isgift;
	}

	public void setIsusebatch(boolean isusebatch) {
		this.isusebatch = isusebatch;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setRemark(String paramString) {
		this.remark = paramString;
	}

	public void setRversion(long paramLong) {
		this.rversion = paramLong;
	}

	public void setSpecification(String paramString) {
		this.specification = paramString;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public void setUnitid(String unitid) {
		if (!unitid.isEmpty()) {
			this.unitid = unitid;
			return;
		}
		this.unitid = null;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public void setWarehouseid(String warehouseid) {
		if (!warehouseid.isEmpty()) {
			this.warehouseid = warehouseid;
			return;
		}
		this.warehouseid = null;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}
}