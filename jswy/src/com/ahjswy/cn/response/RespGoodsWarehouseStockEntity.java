package com.ahjswy.cn.response;

public class RespGoodsWarehouseStockEntity {

	public String stocknum;

	private String warehouseid;

	private String warehousename;

	public String getStocknum() {
		return this.stocknum;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public String getWarehousename() {
		return this.warehousename;
	}

	public void setStocknum(String paramString) {
		this.stocknum = paramString;
	}

	public void setWarehouseid(String paramString) {
		this.warehouseid = paramString;
	}

	public void setWarehousename(String paramString) {
		this.warehousename = paramString;
	}
}