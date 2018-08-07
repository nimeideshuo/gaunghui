package com.ahjswy.cn.request;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqDopGetBatch implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("goodsid")
	private String goodsid;

	@JsonProperty("warehouseid")
	private String warehouseid;

	public ReqDopGetBatch() {
	}

	public ReqDopGetBatch(String paramString1, String paramString2) {
		this.goodsid = paramString1;
		this.warehouseid = paramString2;
	}

	@JsonIgnore
	public String getGoodsId() {
		return this.goodsid;
	}

	@JsonIgnore
	public String getWarehouseId() {
		return this.warehouseid;
	}

	@JsonIgnore
	public void setGoodsId(String paramString) {
		this.goodsid = paramString;
	}

	@JsonIgnore
	public void setWarehouseId(String paramString) {
		this.warehouseid = paramString;
	}
}