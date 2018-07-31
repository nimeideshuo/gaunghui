package com.ahjswy.cn.model;

import java.io.Serializable;

public class DefDocItemCGD implements Serializable {
	private static final long serialVersionUID = 1L;
	// 条码
	private String barcode;
	// 数量单位
	private String bignum;
	// 成本单价
	private double costprice;
	// 折后单价
	private double discountprice;
	// 小计
	private double subtotal;
	// 折后小计
	private double discountsubtotal;
	// 商品id
	private String goodsid;
	// 商品名称
	private String goodsname;
	// 仓库id
	private String warehouseid;
	// 仓库名称
	private String warehousename;
	// 单位id
	private String unitid;
	// 单位名称
	private String unitname;
	// 是否赠品
	private boolean isgift;
	// 数量
	private double num;
	// 单价
	private double price;
	// 单品折扣
	private double discountratio;
	// 规格
	private String specification;
	// 备注
	private String remark;
	// 是否 批次
	private boolean isusebatch;
	// 是否 打折
	private boolean isdiscount;
	// 批次
	private String batch;
	// 版本号
	private long rversion;

	private long itemid;
	private long tempitemid;
	private long docid;
	public long getDocid() {
		return docid;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public long getTempitemid() {
		return tempitemid;
	}

	public void setTempitemid(long tempitemid) {
		this.tempitemid = tempitemid;
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

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public DefDocItemCGD() {
	}

	public DefDocItemCGD(DefDocItemCGD cg) {
		this.barcode = cg.getBarcode();
		this.bignum = cg.getBignum();
		this.costprice = cg.getCostprice();
		this.discountprice = cg.getDiscountprice();
		this.subtotal = cg.getSubtotal();
		this.discountsubtotal = cg.getDiscountsubtotal();
		this.goodsid = cg.getGoodsid();
		this.goodsname = cg.getGoodsname();
		this.warehouseid = cg.getWarehouseid();
		this.warehousename = cg.getWarehousename();
		this.unitid = cg.getUnitid();
		this.unitname = cg.getUnitname();
		this.isgift = cg.isIsgift();
		this.num = cg.getNum();
		this.price = cg.getPrice();
		this.discountratio = cg.getDiscountratio();
		this.specification = cg.getSpecification();
		this.remark = cg.getRemark();
		this.isusebatch = cg.isusebatch;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBignum() {
		return bignum;
	}

	public void setBignum(String bignum) {
		this.bignum = bignum;
	}

	public double getCostprice() {
		return costprice;
	}

	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}

	public double getDiscountprice() {
		return discountprice;
	}

	public void setDiscountprice(double discountprice) {
		this.discountprice = discountprice;
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

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWarehousename() {
		return warehousename;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
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

	public boolean isIsgift() {
		return isgift;
	}

	public void setIsgift(boolean isgift) {
		this.isgift = isgift;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountratio() {
		return discountratio;
	}

	public void setDiscountratio(double discountratio) {
		this.discountratio = discountratio;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isIsusebatch() {
		return isusebatch;
	}

	public void setIsusebatch(boolean isusebatch) {
		this.isusebatch = isusebatch;
	}

	public boolean isIsdiscount() {
		return isdiscount;
	}

	public void setIsdiscount(boolean isdiscount) {
		this.isdiscount = isdiscount;
	}

}
