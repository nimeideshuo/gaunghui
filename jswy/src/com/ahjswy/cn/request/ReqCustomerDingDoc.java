package com.ahjswy.cn.request;

public class ReqCustomerDingDoc {
	private String showid;
	private String departmentid;
	private String departmentname;
	private boolean issettleup;
	private String buildtime;
	private String builderid;
	private String buildername;
	private long docid;
	private String doctype;//订单类型，非空, 12 销售订单，02 采购订单  
	private String customerid; //客户ID，非空
	private String customername;

	public ReqCustomerDingDoc() {
		super();
	}

	public ReqCustomerDingDoc(String showid, String departmentid, String departmentname, boolean issettleup,
			String buildtime, String builderid, String buildername, long docid, String doctype, String customerid,
			String customername) {
		super();
		this.showid = showid;
		this.departmentid = departmentid;
		this.departmentname = departmentname;
		this.issettleup = issettleup;
		this.buildtime = buildtime;
		this.builderid = builderid;
		this.buildername = buildername;
		this.docid = docid;
		this.doctype = doctype;
		this.customerid = customerid;
		this.customername = customername;
	}

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public boolean isIssettleup() {
		return issettleup;
	}

	public void setIssettleup(boolean issettleup) {
		this.issettleup = issettleup;
	}

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	public String getBuilderid() {
		return builderid;
	}

	public void setBuilderid(String builderid) {
		this.builderid = builderid;
	}

	public String getBuildername() {
		return buildername;
	}

	public void setBuildername(String buildername) {
		this.buildername = buildername;
	}

	public long getDocid() {
		return docid;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

}
