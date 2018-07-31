package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class RespGoodsPriceEntity {

	@JsonProperty("batch")
	private String batch;

	@JsonProperty("goodsid")
	private String goodsid;

	@JsonProperty("price")
	private double price;

	@JsonProperty("pricesystemid")
	private String pricesystemid;

	@JsonProperty("pricesystemname")
	private String pricesystemname;

	@JsonProperty("unitid")
	private String unitid;

	@JsonProperty("unitname")
	private String unitname;

	public String getBatch() {
		return this.batch;
	}

	@JsonIgnore
	public String getGoodsid() {
		return this.goodsid;
	}

	@JsonIgnore
	public double getPrice() {
		return this.price;
	}

	@JsonIgnore
	public String getPricesystemid() {
		return this.pricesystemid;
	}

	@JsonIgnore
	public String getPricesystemname() {
		return this.pricesystemname;
	}

	@JsonIgnore
	public String getUnitid() {
		return this.unitid;
	}

	@JsonIgnore
	public String getUnitname() {
		return this.unitname;
	}

	public void setBatch(String paramString) {
		this.batch = paramString;
	}

	@JsonIgnore
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	@JsonIgnore
	public void setPrice(double price) {
		this.price = price;
	}

	@JsonIgnore
	public void setPricesystemid(String pricesystemid) {
		this.pricesystemid = pricesystemid;
	}

	@JsonIgnore
	public void setPricesystemname(String pricesystemname) {
		this.pricesystemname = pricesystemname;
	}

	@JsonIgnore
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	@JsonIgnore
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
}