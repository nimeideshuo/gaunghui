package com.ahjswy.cn.model;

import java.io.Serializable;

import com.ahjswy.cn.utils.TextUtils;

public class DefDocTransfer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String builderid;
	private String buildername;
	private String buildtime;
	private String departmentid;
	private String departmentname;
	private long docid;
	private String doctypeid;
	private String doctypename;
	private String financialcfid;
	private String inwarehouseid;
	private String inwarehousename;
	private boolean isavailable;
	private boolean isposted;
	private String makerid;
	private String makername;
	private String maketime;
	private String outwarehouseid;
	private String outwarehousename;
	private int printnum;
	private String printtemplate;
	private String remark;
	private long rversion;
	private String showid;
	private String summary;

	public String getBuilderid() {
		return this.builderid;
	}

	public String getBuildername() {
		return this.buildername;
	}

	public String getBuildtime() {
		return this.buildtime;
	}

	public String getDepartmentid() {
		return this.departmentid;
	}

	public String getDepartmentname() {
		return this.departmentname;
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

	public String getInwarehouseid() {
		return this.inwarehouseid;
	}

	public String getInwarehousename() {
		return this.inwarehousename;
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

	public String getOutwarehouseid() {
		return this.outwarehouseid;
	}

	public String getOutwarehousename() {
		return this.outwarehousename;
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

	public String getShowid() {
		return this.showid;
	}

	public String getSummary() {
		return this.summary;
	}

	public boolean isIsavailable() {
		return this.isavailable;
	}

	public boolean isIsposted() {
		return this.isposted;
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

	public void setDepartmentid(String departmentid) {
		if (!departmentid.isEmpty()) {
			this.departmentid = departmentid;
			return;
		}
		this.departmentid = null;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
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

	public void setInwarehouseid(String inwarehouseid) {
		if (!inwarehouseid.isEmpty()) {
			this.inwarehouseid = inwarehouseid;
			return;
		}
		this.inwarehouseid = null;
	}

	public void setInwarehousename(String inwarehousename) {
		this.inwarehousename = inwarehousename;
	}

	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}

	public void setIsposted(boolean isposted) {
		this.isposted = isposted;
	}

	public void setMakerid(String makerid) {
		if (!makerid.isEmpty()) {
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

	public void setOutwarehouseid(String outwarehouseid) {
		if (!TextUtils.isEmpty(outwarehouseid)) {
			this.outwarehouseid = outwarehouseid;
			return;
		}
		this.outwarehouseid = null;
	}

	public void setOutwarehousename(String outwarehousename) {
		this.outwarehousename = outwarehousename;
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

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}