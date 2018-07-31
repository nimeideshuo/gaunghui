package com.ahjswy.cn.bean;

import java.io.Serializable;

public class Def_Doc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String builderid;// 创建人id
	private String buildername;// 创建人名称
	private String buildtime;// 创建的时间
	private String customeraddress;// 客户地址
	private String customerid;// 客户id
	private String customername;// 客户名称
	private String deliverytime;// 交货日期
	private String departmentid;// 部门id
	private String departmentname;// 部门名称
	private Double discountratio;// 折扣
	private long distributionid;
	private long docid;
	private String doctypeid;
	private String doctypename;
	private long financialcfid;
	private boolean isavailable;
	private boolean iscompleted;
	private boolean isdistribution;
	private boolean isposted;
	private boolean issettleup;
	private String makerid;
	private String makername;
	private String maketime;
	private double preference;
	private int printnum;/* 打印数量 */
	private String printtemplate;/* 打印模板 */
	private String remark;
	private long rversion;
	private String settletime;
	private String showid;
	private String summary;
	private String warehouseid;
	private String warehousename;
	private double money;// 客史价格,付款,给服务器记住使用
	private boolean isdraft;//false   过账  true  是保存草稿

	public void setIsdraft(boolean isdraft) {
		this.isdraft = isdraft;
	}

	public boolean isIsdraft() {
		return isdraft;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getMoney() {
		return money;
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

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	public String getCustomeraddress() {
		return customeraddress;
	}

	public void setCustomeraddress(String customeraddress) {
		this.customeraddress = customeraddress;
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

	public String getDeliverytime() {
		return deliverytime;
	}

	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
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

	public Double getDiscountratio() {
		return discountratio;
	}

	public void setDiscountratio(Double discountratio) {
		this.discountratio = discountratio;
	}

	public long getDistributionid() {
		return distributionid;
	}

	public void setDistributionid(long distributionid) {
		this.distributionid = distributionid;
	}

	public long getDocid() {
		return docid;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public String getDoctypeid() {
		return doctypeid;
	}

	public void setDoctypeid(String doctypeid) {
		this.doctypeid = doctypeid;
	}

	public String getDoctypename() {
		return doctypename;
	}

	public void setDoctypename(String doctypename) {
		this.doctypename = doctypename;
	}

	public long getFinancialcfid() {
		return financialcfid;
	}

	public void setFinancialcfid(long financialcfid) {
		this.financialcfid = financialcfid;
	}

	public boolean isIsavailable() {
		return isavailable;
	}

	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}

	public boolean isIscompleted() {
		return iscompleted;
	}

	public void setIscompleted(boolean iscompleted) {
		this.iscompleted = iscompleted;
	}

	public boolean isIsdistribution() {
		return isdistribution;
	}

	public void setIsdistribution(boolean isdistribution) {
		this.isdistribution = isdistribution;
	}

	public boolean isIsposted() {
		return isposted;
	}

	public void setIsposted(boolean isposted) {
		this.isposted = isposted;
	}

	public boolean isIssettleup() {
		return issettleup;
	}

	public void setIssettleup(boolean issettleup) {
		this.issettleup = issettleup;
	}

	public String getMakerid() {
		return makerid;
	}

	public void setMakerid(String makerid) {
		this.makerid = makerid;
	}

	public String getMakername() {
		return makername;
	}

	public void setMakername(String makername) {
		this.makername = makername;
	}

	public String getMaketime() {
		return maketime;
	}

	public void setMaketime(String maketime) {
		this.maketime = maketime;
	}

	public double getPreference() {
		return preference;
	}

	public void setPreference(double preference) {
		this.preference = preference;
	}

	public int getPrintnum() {
		return printnum;
	}

	public void setPrintnum(int printnum) {
		this.printnum = printnum;
	}

	/* 打印模板 */
	public String getPrinttemplate() {
		return printtemplate;
	}

	public void setPrinttemplate(String printtemplate) {
		this.printtemplate = printtemplate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getRversion() {
		return rversion;
	}

	public void setRversion(long rversion) {
		this.rversion = rversion;
	}

	public String getSettletime() {
		return settletime;
	}

	public void setSettletime(String settletime) {
		this.settletime = settletime;
	}

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWarehousename() {
		return warehousename;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}

}
