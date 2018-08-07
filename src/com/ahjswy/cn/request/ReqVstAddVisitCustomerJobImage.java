package com.ahjswy.cn.request;

public class ReqVstAddVisitCustomerJobImage {
	private String imagefile;
	private String imagepath;
	private boolean issignature;
	private String remark;
	private long visitjobid;

	public ReqVstAddVisitCustomerJobImage() {
	}
	
	public ReqVstAddVisitCustomerJobImage(long visitjobid, boolean issignature, String imagepath, String remark,
			String imagefile) {
		this.visitjobid = visitjobid;
		this.issignature = issignature;
		this.imagepath = imagepath;
		this.remark = remark;
		this.imagefile = imagefile;
	}

	public String getImagefile() {
		return imagefile;
	}

	public void setImagefile(String imagefile) {
		this.imagefile = imagefile;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public boolean isIssignature() {
		return issignature;
	}

	public void setIssignature(boolean issignature) {
		this.issignature = issignature;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getVisitjobid() {
		return visitjobid;
	}

	public void setVisitjobid(long visitjobid) {
		this.visitjobid = visitjobid;
	}

}
