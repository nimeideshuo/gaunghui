package com.ahjswy.cn.model;

import java.io.Serializable;

public class FieldSaleForPrint implements Serializable {
	private static final long serialVersionUID = 1L;
	private String buildername;
	private String buildtime;
	private String customername;
	private String departmentname;
	private String doctype;
	private String num;
	private String preference;
	private String receivable;
	private String received;
	private String showid;
	private String remark;
	private String sumamount;
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSumamount() {
		return sumamount;
	}

	public void setSumamount(String sumamount) {
		this.sumamount = sumamount;
	}

	public String getBuildername() {
		return this.buildername;
	}

	public String getBuildtime() {
		return this.buildtime;
	}

	public String getCustomername() {
		return this.customername;
	}

	public String getDepartmentname() {
		return this.departmentname;
	}

	public String getDoctype() {
		return this.doctype;
	}

	public String getNum() {
		return this.num;
	}

	public String getPreference() {
		return this.preference;
	}

	public String getReceivable() {
		return this.receivable;
	}

	public String getReceived() {
		return this.received;
	}

	public String getShowid() {
		return this.showid;
	}

	public void setBuildername(String buildername) {
		this.buildername = buildername;
	}

	public void setBuildtime(String paramString) {
		this.buildtime = paramString;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public void setReceivable(String receivable) {
		this.receivable = receivable;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
