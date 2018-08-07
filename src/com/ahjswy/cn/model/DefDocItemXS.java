package com.ahjswy.cn.model;

import java.io.Serializable;

public class DefDocItemXS implements Serializable {
	private static final long serialVersionUID = 1L;
	// 条码
	private String barcode;
	// 批次
	private String batch;
	// 单位 数量
	private String bignum;
	// 成本费
	private double costprice;
	// 折扣单价
	private double discountprice;
	// 折扣
	private double discountratio;
	// 折扣小计
	private double discountsubtotal;
	private long docid;
	// 商品id
	private String goodsid;
	// 商品名称
	private String goodsname;
	// 是否 打折
	private boolean isdiscount;
	// 是否陈列
	private boolean isexhibition;
	// 是否 赠品
	private boolean isgift;
	// 【促】
	private boolean ispromotion;
	// 是否 批次
	private boolean isusebatch;

	private long itemid;
	// 手机号码
	private String model;
	// 数量
	private double num;

	private long outorderdocid;
	private String outorderdocshowid;
	private long outorderitemid;
	private long parentitemid;
	// 单价
	private double price;
	// 生产时间
	private String productiondate;
	// 促销类型
	private int promotiontype;
	// 促销名称
	private String promotiontypename;
	// 备注
	private String remark;
	private long rversion;
	// 规格
	private String specification;
	// 小计
	private double subtotal;

	private long tempitemid;
	// 单位id
	private String unitid;
	// 单位名称
	private String unitname;
	// 仓库id
	private String warehouseid;
	// 仓库名称
	private String warehousename;
	// 辅助计件单位
//	public double assistnum;
	// private RespGoodsWarehouse stocknum;
	public double stocknum;
	public double stocksumnum;
	public String goodStock;
	public String goodSumStock;
	public String buildbegintime;
	public String buildendtime;
	public GoodsUnit unit;
	// ======
	public boolean isExist;

	public String getBuildbegintime() {
		return buildbegintime;
	}

	public void setBuildbegintime(String buildbegintime) {
		this.buildbegintime = buildbegintime;
	}

	public String getBuildendtime() {
		return buildendtime;
	}

	public void setBuildendtime(String buildendtime) {
		this.buildendtime = buildendtime;
	}

	public DefDocItemXS() {
	}

	public DefDocItemXS(DefDocItemXS item) {
		this.itemid = item.getItemid();
		this.tempitemid = item.getTempitemid();
		this.docid = item.getDocid();
		this.goodsid = item.getGoodsid();
		this.goodsname = item.getGoodsname();
		this.warehouseid = item.getWarehouseid();
		this.warehousename = item.getWarehousename();
		this.unitid = item.getUnitid();
		this.unitname = item.getUnitname();
		this.num = item.getNum();
		this.bignum = item.getBignum();
		this.price = item.getPrice();
		this.subtotal = item.getSubtotal();
		this.discountratio = item.getDiscountratio();
		this.discountprice = item.getDiscountprice();
		this.discountsubtotal = item.getDiscountsubtotal();
		this.isgift = item.isgift;
		this.costprice = item.getCostprice();
		this.remark = item.getRemark();
		this.rversion = item.getRversion();
		this.isdiscount = item.isdiscount;
		this.barcode = item.getBarcode();
		this.batch = item.getBatch();
		this.productiondate = item.getProductiondate();
		this.model = item.getModel();
		this.specification = item.getSpecification();
		this.isexhibition = item.isexhibition;
		this.isusebatch = item.isusebatch;
		if ((item.isIspromotion()) && (item.getPromotiontype() == 0)) {
			this.ispromotion = true;
			this.parentitemid = 0L;
			this.promotiontype = item.getPromotiontype();
		} else {
			this.ispromotion = false;
			this.parentitemid = 0L;
			this.promotiontype = -1;
		}
		if (promotiontypename == item.getPromotiontypename()) {
			this.outorderdocid = 0L;
			this.outorderdocshowid = null;
			this.outorderitemid = 0L;
		} else {
			this.promotiontypename = null;
		}

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

	// 折后单价
	public double getDiscountprice() {
		return this.discountprice;
	}

	// 单品折扣
	public double getDiscountratio() {
		return this.discountratio;
	}

	// 折扣小计
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

	// 数量
	public double getNum() {
		return this.num;
	}

	public long getOutorderdocid() {
		return this.outorderdocid;
	}

	public String getOutorderdocshowid() {
		return this.outorderdocshowid;
	}

	public long getOutorderitemid() {
		return this.outorderitemid;
	}

	public long getParentitemid() {
		return this.parentitemid;
	}

	// 单价
	public double getPrice() {
		return this.price;
	}

	// 生产时间
	public String getProductiondate() {
		return this.productiondate;
	}

	// 促销方式
	public int getPromotiontype() {
		return this.promotiontype;
	}

	// 促销名称
	public String getPromotiontypename() {
		return this.promotiontypename;
	}

	// 备注
	public String getRemark() {
		return this.remark;
	}

	public long getRversion() {
		return this.rversion;
	}

	// 规格
	public String getSpecification() {
		return this.specification;
	}

	// 小计
	public double getSubtotal() {
		return this.subtotal;
	}

	public long getTempitemid() {
		return this.tempitemid;
	}

	// 单位 id
	public String getUnitid() {
		return this.unitid;
	}

	// 单位名称
	public String getUnitname() {
		return this.unitname;
	}

	// 仓库 id
	public String getWarehouseid() {
		return this.warehouseid;
	}

	// 仓库 name
	public String getWarehousename() {
		return this.warehousename;
	}

	// 折扣
	public boolean isIsdiscount() {
		return this.isdiscount;
	}

	// 【列】
	public boolean isIsexhibition() {
		return this.isexhibition;
	}

	public boolean isIsgift() {
		return this.isgift;
	}

	// 促销
	public boolean isIspromotion() {
		return this.ispromotion;
	}

	// 批次
	public boolean isIsusebatch() {
		return this.isusebatch;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	// 批次
	public void setBatch(String batch) {
		this.batch = batch;
	}

	public void setBignum(String bignum) {
		this.bignum = bignum;
	}

	// 成本价格
	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}

	// 折后单价
	public void setDiscountprice(double discountprice) {
		this.discountprice = discountprice;
	}

	// 停止 终止
	public void setDiscountratio(double discountratio) {
		this.discountratio = discountratio;
	}

	// 折扣小计
	public void setDiscountsubtotal(double discountsubtotal) {
		this.discountsubtotal = discountsubtotal;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	// 设置 商品 id
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	// 商品名称
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	// 折扣
	public void setIsdiscount(boolean isdiscount) {
		this.isdiscount = isdiscount;
	}

	public void setIsexhibition(boolean isexhibition) {
		this.isexhibition = isexhibition;
	}

	public void setIsgift(boolean isgift) {
		this.isgift = isgift;
	}

	public void setIspromotion(boolean ispromotion) {
		this.ispromotion = ispromotion;
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

	// 数量
	public void setNum(double num) {
		this.num = num;
	}

	public void setOutorderdocid(long outorderdocid) {
		this.outorderdocid = outorderdocid;
	}

	public void setOutorderdocshowid(String outorderdocshowid) {
		this.outorderdocshowid = outorderdocshowid;
	}

	public void setOutorderitemid(long outorderitemid) {
		this.outorderitemid = outorderitemid;
	}

	public void setParentitemid(long parentitemid) {
		this.parentitemid = parentitemid;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setProductiondate(String productiondate) {
		this.productiondate = productiondate;
	}

	public void setPromotiontype(int promotiontype) {
		this.promotiontype = promotiontype;
	}

	public void setPromotiontypename(String promotiontypename) {
		this.promotiontypename = promotiontypename;
	}

	// 备注
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRversion(long rversion) {
		this.rversion = rversion;
	}

	public void setSpecification(String paramString) {
		this.specification = paramString;
	}

	// 小计
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public void setTempitemid(long tempitemid) {
		this.tempitemid = tempitemid;
	}

	// 单位 ID
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	// 单位 name
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}

	@Override
	public String toString() {
		return "DefDocItemXS [barcode=" + barcode + ", batch=" + batch + ", bignum=" + bignum + ", costprice="
				+ costprice + ", discountprice=" + discountprice + ", discountratio=" + discountratio
				+ ", discountsubtotal=" + discountsubtotal + ", docid=" + docid + ", goodsid=" + goodsid
				+ ", goodsname=" + goodsname + ", isdiscount=" + isdiscount + ", isexhibition=" + isexhibition
				+ ", isgift=" + isgift + ", ispromotion=" + ispromotion + ", isusebatch=" + isusebatch + ", itemid="
				+ itemid + ", model=" + model + ", num=" + num + ", outorderdocid=" + outorderdocid
				+ ", outorderdocshowid=" + outorderdocshowid + ", outorderitemid=" + outorderitemid + ", parentitemid="
				+ parentitemid + ", price=" + price + ", productiondate=" + productiondate + ", promotiontype="
				+ promotiontype + ", promotiontypename=" + promotiontypename + ", remark=" + remark + ", rversion="
				+ rversion + ", specification=" + specification + ", subtotal=" + subtotal + ", tempitemid="
				+ tempitemid + ", unitid=" + unitid + ", unitname=" + unitname + ", warehouseid=" + warehouseid
				+ ", warehousename=" + warehousename + ", stocknum=" + stocknum + ", goodStock=" + goodStock
				+ ", buildbegintime=" + buildbegintime + ", buildendtime=" + buildendtime + ", isExist=" + isExist
				+ "]";
	}

}