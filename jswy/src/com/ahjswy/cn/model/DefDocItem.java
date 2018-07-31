package com.ahjswy.cn.model;

import java.io.Serializable;

import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.utils.TextUtils;

public class DefDocItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String barcode;
	private String batch;
	private String bignum;
	private double costprice;
	private double discountprice;
	private double discountratio;
	private double discountsubtotal;
	private long docid;
	private String goodsid;
	private String goodsname;
	private boolean isdiscount;
	private boolean isgift;
	private boolean isusebatch;
	private long itemid;
	private String model;
	private double num;
	private double price;
	private String productiondate;
	private String remark;
	private long rversion;
	private String specification;
	private double subtotal;
	private String unitid;
	private String unitname;
	private String warehouseid;
	private String warehousename;

	public DefDocItem() {
	}

	public DefDocItem(DefDocItem paramDefDocItem) {
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
		this.productiondate = paramDefDocItem.getProductiondate();
		this.barcode = paramDefDocItem.getBarcode();
		this.model = paramDefDocItem.getModel();
		this.specification = paramDefDocItem.getSpecification();
		this.costprice = paramDefDocItem.getCostprice();
		this.isdiscount = paramDefDocItem.isdiscount;
		this.isusebatch = paramDefDocItem.isusebatch;
	}

	public DefDocItem(DefDocItemXS paramDefDocItemXS) {
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
		this.model = paramDefDocItemXS.getModel();
		this.specification = paramDefDocItemXS.getSpecification();
		this.costprice = paramDefDocItemXS.getCostprice();
		this.isdiscount = paramDefDocItemXS.isIsdiscount();
		this.isusebatch = paramDefDocItemXS.isIsusebatch();
		this.batch = paramDefDocItemXS.getBatch();
		this.productiondate = paramDefDocItemXS.getProductiondate();
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

	public String getModel() {
		return this.model;
	}

	public double getNum() {
		return this.num;
	}

	public double getPrice() {
		return this.price;
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
		if (!TextUtils.isEmpty(goodsid)) {
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

	public void setModel(String model) {
		this.model = model;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public void setUnitid(String unitid) {
		if (!TextUtils.isEmpty(unitid)) {
			this.unitid = unitid;
			return;
		}
		this.unitid = null;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public void setWarehouseid(String warehouseid) {
		if (!TextUtils.isEmpty(warehouseid)) {
			this.warehouseid = warehouseid;
			return;
		}
		this.warehouseid = null;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}

	public RespGoodsWarehouse respGoodsWarehouse;

	public void setStocknum(RespGoodsWarehouse respGoodsWarehouse) {
		this.respGoodsWarehouse = respGoodsWarehouse;
	}

	public RespGoodsWarehouse getStocknum() {
		return respGoodsWarehouse;
	}

}
