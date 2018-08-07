package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqStrInitDoc {

	@JsonProperty("departmentID")
	private String departmentID;

	@JsonProperty("makerID")
	private String makerID;

	@JsonProperty("makerName")
	private String makerName;

	@JsonProperty("warehouseID")
	private String warehouseID;

	@JsonProperty("warehouseID2")
	private String warehouseID2;

	@JsonIgnore
	public String getDepartmentID() {
		return this.departmentID;
	}

	@JsonIgnore
	public String getMakerID() {
		return this.makerID;
	}

	@JsonIgnore
	public String getMakerName() {
		return this.makerName;
	}

	@JsonIgnore
	public String getWarehouseID() {
		return this.warehouseID;
	}

	@JsonIgnore
	public String getWarehouseID2() {
		return this.warehouseID2;
	}

	@JsonIgnore
	public void setDepartmentID(String paramString) {
		this.departmentID = paramString;
	}

	// 设置用户id
	@JsonIgnore
	public void setMakerID(String paramString) {
		this.makerID = paramString;
	}

	// 设置用户名称
	@JsonIgnore
	public void setMakerName(String paramString) {
		this.makerName = paramString;
	}
	// 设置仓库ID
	@JsonIgnore
	public void setWarehouseID(String paramString) {
		this.warehouseID = paramString;
	}

	@JsonIgnore
	public void setWarehouseID2(String paramString) {
		this.warehouseID2 = paramString;
	}
}