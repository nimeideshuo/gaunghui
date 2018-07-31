package com.ahjswy.cn.model;

import java.io.Serializable;

import com.ahjswy.cn.utils.TextUtils;

public class DefDocXS implements Serializable {
	private static final long serialVersionUID = 1L;
	// 建立 id
	protected String builderid;
	// 建立名称
	protected String buildername;
	// 建立时间
	protected String buildtime;
	// 客户地址
	protected String customeraddress;
	// 客户ID
	protected String customerid;
	// 客户名称
	protected String customername;
	// 结算时间
	protected String deliverytime;
	// 部门id
	protected String departmentid;
	// 部门名称
	protected String departmentname;
	// 折扣
	protected double discountratio;
	// 分配id
	protected String distributionid;
	protected long docid;
	// 支付id
	protected String doctypeid;
	// 支付名称
	protected String doctypename;
	// 财务id
	protected String financialcfid;
	protected boolean isavailable;
	// 分销
	protected boolean isdistribution;
	// 是否打印
	protected boolean isposted;
	// 结账
	protected boolean issettleup;
	// 制造商 id
	protected String makerid;
	// 制造商名称
	protected String makername;
	// 进行时间
	protected String maketime;
	// 手机
	protected String mobile;
	// 优惠
	protected double preference;
	// 打印数量
	protected int printnum;
	// 打印模板
	protected String printtemplate;
	// 促销id
	protected String promotionid;
	// 促销名称
	protected String promotionname;
	// 备注
	protected String remark;
	//
	protected long rversion;
	// 设置时间
	protected String settletime;
	// 显示单号
	protected String showid;
	// 摘要
	protected String summary;
	// 卡车数量
	protected String trucknumber;
	// 仓库 id
	protected String warehouseid;
	// 仓库 名称
	protected String warehousename;

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

	// 折扣率
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

	// 手机号码
	public String getMobile() {
		return this.mobile;
	}

	// 优惠
	public double getPreference() {
		return this.preference;
	}

	public int getPrintnum() {
		return this.printnum;
	}

	// 打印模板
	public String getPrinttemplate() {
		return this.printtemplate;
	}

	public String getPromotionid() {
		return this.promotionid;
	}

	public String getPromotionname() {
		return this.promotionname;
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

	public String getTrucknumber() {
		return this.trucknumber;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public String getWarehousename() {
		return this.warehousename;
	}

	// 可获得的
	public boolean isIsavailable() {
		return this.isavailable;
	}

	public boolean isIsdistribution() {
		return this.isdistribution;
	}

	public boolean isIsposted() {
		return this.isposted;
	}

	public boolean isIssettleup() {
		return this.issettleup;
	}

	public void setBuilderid(String paramString) {
		this.builderid = paramString;
	}

	public void setBuildername(String paramString) {
		this.buildername = paramString;
	}

	public void setBuildtime(String paramString) {
		this.buildtime = paramString;
	}

	// 用户地址
	public void setCustomeraddress(String paramString) {
		this.customeraddress = paramString;
	}

	// 用户ID
	public void setCustomerid(String customerid) {
		if (TextUtils.isEmptyS(customerid)) {
			this.customerid = customerid;
			return;
		}
		this.customerid = null;

	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public void setDeliverytime(String paramString) {
		this.deliverytime = paramString;
	}

	// 设置部门
	public void setDepartmentid(String paramString) {
		if (TextUtils.isEmptyS(paramString)) {
			this.departmentid = paramString;
			return;
		}
		this.departmentid = null;

	}

	public void setDepartmentname(String paramString) {
		this.departmentname = paramString;
	}

	public void setDiscountratio(double paramDouble) {
		this.discountratio = paramDouble;
	}

	public void setDistributionid(String paramString) {
		if (TextUtils.isEmptyS(paramString)) {
			this.distributionid = paramString;
			return;
		}
		this.distributionid = null;

	}

	public void setDocid(long paramLong) {
		this.docid = paramLong;
	}

	// 需要注意 如果 为 null 不用付款就能过账
	public void setDoctypeid(String paramString) {
		if (!TextUtils.isEmptyS(paramString)) {
			this.doctypeid = null;
			return;
		}
		this.doctypeid = paramString;
	}

	public void setDoctypename(String paramString) {
		this.doctypename = paramString;
	}

	public void setFinancialcfid(String paramString) {
		if (TextUtils.isEmptyS(paramString)) {
			this.financialcfid = null;
			return;
		}
		this.financialcfid = paramString;
	}

	public void setIsavailable(boolean paramBoolean) {
		this.isavailable = paramBoolean;
	}

	// 配送
	public void setIsdistribution(boolean paramBoolean) {
		this.isdistribution = paramBoolean;
	}

	// 是否记住客史 false记住客史true不记住
	public void setIsposted(boolean paramBoolean) {
		this.isposted = paramBoolean;
	}

	public void setIssettleup(boolean paramBoolean) {
		this.issettleup = paramBoolean;
	}

	public void setMakerid(String paramString) {
		this.makerid = paramString;
	}

	public void setMakername(String paramString) {
		this.makername = paramString;
	}

	public void setMaketime(String paramString) {
		this.maketime = paramString;
	}

	public void setMobile(String paramString) {
		this.mobile = paramString;
	}

	public void setPreference(double paramDouble) {
		this.preference = paramDouble;
	}

	public void setPrintnum(int paramInt) {
		this.printnum = paramInt;
	}

	public void setPrinttemplate(String paramString) {
		this.printtemplate = paramString;
	}

	public void setPromotionid(String paramString) {
		if (TextUtils.isEmptyS(paramString)) {
			this.promotionid = paramString;
			return;
		}
		this.promotionid = null;
	}

	public void setPromotionname(String paramString) {
		this.promotionname = paramString;
	}

	// 备注
	public void setRemark(String paramString) {
		this.remark = paramString;
	}

	public void setRversion(long paramLong) {
		this.rversion = paramLong;
	}

	// 结算日期
	public void setSettletime(String paramString) {
		this.settletime = paramString;
	}

	// 单号
	public void setShowid(String paramString) {
		this.showid = paramString;
	}

	// 摘要
	public void setSummary(String paramString) {
		this.summary = paramString;
	}

	public void setTrucknumber(String paramString) {
		this.trucknumber = paramString;
	}

	// 仓库 ID
	public void setWarehouseid(String warehouseid) {
		if (TextUtils.isEmptyS(warehouseid)) {
			this.warehouseid = warehouseid;
			return;
		}
		this.warehouseid = null;

	}

	// 仓库 名称
	public void setWarehousename(String paramString) {
		this.warehousename = paramString;
	}
}