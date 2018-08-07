package com.ahjswy.cn.model;

import com.ahjswy.cn.utils.Utils;

public class FieldSaleItemForPrint {
	protected String barcode;
	protected double discountratio;
	protected double discountsubtotal;//折扣小计
	protected String goodsid;
	protected String goodsname;
	protected String itemtype;
	protected double num;
	protected double price;
	protected String remark;
	protected String specification;
	protected String unitname;

	public String getBarcode() {
		return this.barcode;
	}

	public double getDiscountratio() {
		return this.discountratio;
	}

	public double getDiscountsubtotal() {
		return this.discountsubtotal;
	}

	public String getGoodsid() {
		return this.goodsid;
	}

	public String getGoodsname() {
		return this.goodsname;
	}

	public String getItemtype() {
		return this.itemtype;
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

	public String getSpecification() {
		return this.specification;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setDiscountratio(double discountratio) {
		this.discountratio = discountratio;
	}

	public void setDiscountsubtotal(double discountsubtotal) {
		this.discountsubtotal = Utils.normalizeDouble(discountsubtotal);
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public void setPrice(double price) {
		this.price = Utils.normalizeDouble(price);
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
}
