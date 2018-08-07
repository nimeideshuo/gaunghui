package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqStrGetGoodsPrice {

	@JsonProperty("batch")
	private String batch;

	@JsonProperty("customerid")
	private String customerid;

	@JsonProperty("goodsid")
	private String goodsid;

	@JsonProperty("isdiscount")
	private boolean isdiscount;

	@JsonProperty("price")
	private double price;

	@JsonProperty("productiondate")
	private String productiondate;

	@JsonProperty("type")
	private int type;

	@JsonProperty("unitid")
	private String unitid;

	@JsonProperty("warehouseid")
	private String warehouseid;

	// 批次
	public String getBatch() {
		return this.batch;
	}

	@JsonIgnore
	public String getCustomerid() {
		return this.customerid;
	}

	@JsonIgnore
	public String getGoodsid() {
		return this.goodsid;
	}

	@JsonIgnore
	public boolean getIsdiscount() {
		return this.isdiscount;
	}

	@JsonIgnore
	public double getPrice() {
		return this.price;
	}

	public String getProductiondate() {
		return this.productiondate;
	}

	@JsonIgnore
	public int getType() {
		return this.type;
	}

	@JsonIgnore
	public String getUnitid() {
		return this.unitid;
	}

	@JsonIgnore
	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setBatch(String paramString) {
		this.batch = paramString;
	}

	@JsonIgnore
	public void setCustomerid(String paramString) {
		this.customerid = paramString;
	}

	@JsonIgnore
	public void setGoodsid(String paramString) {
		this.goodsid = paramString;
	}

	@JsonIgnore
	public void setIsdiscount(boolean paramBoolean) {
		this.isdiscount = paramBoolean;
	}

	@JsonIgnore
	public void setPrice(double paramDouble) {
		this.price = paramDouble;
	}

	public void setProductiondate(String paramString) {
		this.productiondate = paramString;
	}

	@JsonIgnore
	public void setType(int paramInt) {
		this.type = paramInt;
	}

	@JsonIgnore
	public void setUnitid(String paramString) {
		this.unitid = paramString;
	}

	@JsonIgnore
	public void setWarehouseid(String paramString) {
		this.warehouseid = paramString;
	}
}