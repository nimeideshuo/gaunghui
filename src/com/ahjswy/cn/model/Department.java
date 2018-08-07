package com.ahjswy.cn.model;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("did")
	private String did;

	@JsonProperty("dname")
	private String dname;

	@JsonProperty("warehouseid")
	private String warehouseid;

	@JsonProperty("warehousename")
	private String warehousename;

	public String getDid() {
		return this.did;
	}

	public String getDname() {
		return this.dname;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public String getWarehousename() {
		return this.warehousename;
	}

	public void setDid(String paramString) {
		this.did = paramString;
	}

	public void setDname(String paramString) {
		this.dname = paramString;
	}

	public void setWarehouseid(String paramString) {
		this.warehouseid = paramString;
	}

	public void setWarehousename(String paramString) {
		this.warehousename = paramString;
	}
}