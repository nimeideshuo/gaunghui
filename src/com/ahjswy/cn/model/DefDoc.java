package com.ahjswy.cn.model;

import java.io.Serializable;

public class DefDoc implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String builderid;
	protected String buildername;
	protected String buildtime;
	protected String customeraddress;
	protected String customerid;
	protected String customername;
	protected String deliverytime;
	protected String departmentid;
	protected String departmentname;
	protected double discountratio;
	protected String distributionid;
	protected long docid;
	protected String doctypeid;
	protected String doctypename;
	protected String financialcfid;
	protected boolean isavailable;
	protected boolean isdistribution;
	protected boolean isposted;
	protected boolean issettleup;
	protected String makerid;
	protected String makername;
	protected String maketime;
	protected double preference;
	protected int printnum;
	protected String printtemplate;
	protected String remark;
	protected long rversion;
	protected String settletime;
	protected String showid;
	protected String summary;
	protected String warehouseid;
	protected String warehousename;
	// public String opendate;
	// public String overdate;

	// public String getOpendate() {
	// return opendate;
	// }
	//
	// public void setOpendate(String opendate) {
	// this.opendate = opendate;
	// }
	//
	// public String getOverdate() {
	// return overdate;
	// }

	// public void setOverdate(String overdate) {
	// this.overdate = overdate;
	// }

	public String getBuilderid() {
		return this.builderid;
	}

	public String getBuildername() {
		return this.buildername;
	}

	public String getBuildtime() {
		return this.buildtime;
	}

	public String getCustomeraddress() {
		return this.customeraddress;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public String getCustomername() {
		return this.customername;
	}

	public String getDeliverytime() {
		return this.deliverytime;
	}

	public String getDepartmentid() {
		return this.departmentid;
	}

	public String getDepartmentname() {
		return this.departmentname;
	}

	public double getDiscountratio() {
		return this.discountratio;
	}

	public String getDistributionid() {
		return this.distributionid;
	}

	public long getDocid() {
		return this.docid;
	}

	public String getDoctypeid() {
		return this.doctypeid;
	}

	public String getDoctypename() {
		return this.doctypename;
	}

	public String getFinancialcfid() {
		return this.financialcfid;
	}

	public String getMakerid() {
		return this.makerid;
	}

	public String getMakername() {
		return this.makername;
	}

	public String getMaketime() {
		return this.maketime;
	}

	public double getPreference() {
		return this.preference;
	}

	public int getPrintnum() {
		return this.printnum;
	}

	public String getPrinttemplate() {
		return this.printtemplate;
	}

	public String getRemark() {
		return this.remark;
	}

	public long getRversion() {
		return this.rversion;
	}

	public String getSettletime() {
		return this.settletime;
	}

	public String getShowid() {
		return this.showid;
	}

	public String getSummary() {
		return this.summary;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public String getWarehousename() {
		return this.warehousename;
	}

	public boolean isIsavailable() {
		return this.isavailable;
	}

	// 是否配送
	public boolean isIsdistribution() {
		return this.isdistribution;
	}

	public boolean isIsposted() {
		return this.isposted;
	}

	public boolean isIssettleup() {
		return this.issettleup;
	}

	public void setBuilderid(String builderid) {
		if (!builderid.isEmpty()) {
			this.builderid = builderid;
			return;
		}
		this.builderid = null;
	}

	public void setBuildername(String buildername) {
		this.buildername = buildername;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	public void setCustomeraddress(String customeraddress) {
		this.customeraddress = customeraddress;
	}

	public void setCustomerid(String customerid) {
		if (customerid != null && customerid.length() > 0) {
			this.customerid = customerid;
			return;
		}
		this.customerid = null;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}

	public void setDepartmentid(String departmentid) {
		if (departmentid != null) {
			this.departmentid = departmentid;
			return;
		}
		this.departmentid = null;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public void setDiscountratio(double discountratio) {
		this.discountratio = discountratio;
	}

	public void setDistributionid(String distributionid) {
		this.distributionid = distributionid;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public void setDoctypeid(String doctypeid) {
		this.doctypeid = doctypeid;
	}

	public void setDoctypename(String doctypename) {
		this.doctypename = doctypename;
	}

	public void setFinancialcfid(String financialcfid) {
		this.financialcfid = financialcfid;
	}

	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}

	public void setIsdistribution(boolean isdistribution) {
		this.isdistribution = isdistribution;
	}

	public void setIsposted(boolean isposted) {
		this.isposted = isposted;
	}

	public void setIssettleup(boolean issettleup) {
		this.issettleup = issettleup;
	}

	public void setMakerid(String makerid) {
		if (makerid != null) {
			this.makerid = makerid;
			return;
		}
		this.makerid = null;
	}

	public void setMakername(String makername) {
		this.makername = makername;
	}

	public void setMaketime(String maketime) {
		this.maketime = maketime;
	}

	public void setPreference(double preference) {
		this.preference = preference;
	}

	public void setPrintnum(int printnum) {
		this.printnum = printnum;
	}

	public void setPrinttemplate(String printtemplate) {
		this.printtemplate = printtemplate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRversion(long rversion) {
		this.rversion = rversion;
	}

	public void setSettletime(String settletime) {
		this.settletime = settletime;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setWarehouseid(String warehouseid) {
		if (!warehouseid.isEmpty()) {
			this.warehouseid = warehouseid;
			return;
		}
		this.warehouseid = null;
	}

	public void setWarehousename(String paramString) {
		this.warehousename = paramString;
	}
}