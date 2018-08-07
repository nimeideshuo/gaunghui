package com.ahjswy.cn.model;

import java.io.Serializable;

import com.ahjswy.cn.utils.TextUtils;

public class DefDocCG implements Serializable {
	private static final long serialVersionUID = 1L;
	// 建立 id
	protected String builderid;
	// 建立名称
	protected String buildername;
	// 建立时间
	protected String buildtime;
	// 供应商ID
	protected String supplerid;
	// 供应商名称
	protected String supplername;
	// 结算时间
	protected String deliverytime;
	// 交货日期
	protected String settletime;
	// 部门id
	protected String departmentid;
	// 部门名称
	protected String departmentname;
	// 折扣
	protected double discountratio;
	// 分布id
	protected String distributionid;
	// 文档id
	protected long docid;
	// 支付id
	protected String doctypeid;
	// 支付名称
	protected String doctypename;
	// 结转记录id
	// protected String financialcfid;
	// 分销
	// protected boolean isdistribution;
	// 0代表草稿箱 1代表普通的单子
	protected boolean isposted;
	// 当前单据 是否可用
	protected boolean isavailable;
	// 0 代表未过账的单子 1 代表已过账的单子
	protected boolean issettleup;
	// 制造商 id
	protected String makerid;
	// 制造商名称
	protected String makername;
	// 进行时间
	protected String maketime;
	// 优惠
	protected double preference;
	// 打印数量
	protected int printnum;
	// 打印模板
	protected String printtemplate;

	// 促销id
	// protected String promotionid;
	// // 促销名称
	// protected String promotionname;
	// 备注
	protected String remark;
	//
	protected long rversion;
	// 显示单号
	protected String showid;
	// 摘要
	protected String summary;
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

	// TODO 必须要添加
	public long getDocid() {
		return this.docid;
	}

	public String getDoctypeid() {
		return this.doctypeid;
	}

	public String getDoctypename() {
		return this.doctypename;
	}
	// 结转记录id
	// public String getFinancialcfid() {
	// return this.financialcfid;
	// }

	public String getMakerid() {
		return this.makerid;
	}

	public String getMakername() {
		return this.makername;
	}

	public String getMaketime() {
		return this.maketime;
	}

	public String getSupplerid() {
		return supplerid;
	}

	public String getSupplername() {
		return supplername;
	}

	// 优惠
	public double getPreference() {
		return this.preference;
	}

	public int getPrintnum() {
		return this.printnum;
	}

	public String getPrinttemplate() {
		return this.printtemplate;
	}
	//
	// public String getPromotionid() {
	// return this.promotionid;
	// }
	//
	// public String getPromotionname() {
	// return this.promotionname;
	// }

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

	//
	// public boolean isIsdistribution() {
	// return this.isdistribution;
	// }
	// 过账后 是草稿箱 还是 普通单据
	public boolean isIsposted() {
		return this.isposted;
	}

	// 过账 或者没过帐
	public boolean isIssettleup() {
		return this.issettleup;
	}

	public void setBuilderid(String builderid) {
		this.builderid = builderid;
	}

	public void setBuildername(String buildername) {
		this.buildername = buildername;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	// 供应商ID
	public void setSupplerid(String supplerid) {
		if (TextUtils.isEmptyS(supplerid)) {
			this.supplerid = supplerid;
			return;
		}
		supplerid = null;
	}

	public void setSupplername(String supplername) {
		this.supplername = supplername;
	}

	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}

	// 设置部门
	public void setDepartmentid(String departmentid) {
		if (TextUtils.isEmptyS(departmentid)) {
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
		if (TextUtils.isEmptyS(distributionid)) {
			this.distributionid = distributionid;
			return;
		}
		this.distributionid = null;

	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	// 需要注意 如果 为 null 不用付款就能过账
	public void setDoctypeid(String doctypeid) {
		if (!TextUtils.isEmptyS(doctypeid)) {
			this.doctypeid = null;
			return;
		}
		this.doctypeid = doctypeid;
	}

	public void setDoctypename(String doctypename) {
		this.doctypename = doctypename;
	}

	// 结转记录id
	// public void setFinancialcfid(String financialcfid) {
	// if (TextUtils.isEmptyS(financialcfid)) {
	// this.financialcfid = null;
	// return;
	// }
	// this.financialcfid = paramString;
	// }
	//
	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}

	// 是否记住客史 false记住客史true不记住
	public void setIsposted(boolean isposted) {
		this.isposted = isposted;
	}

	public void setIssettleup(boolean issettleup) {
		this.issettleup = issettleup;
	}

	public void setMakerid(String makerid) {
		this.makerid = makerid;
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

	// 打印出来的 数量
	public void setPrintnum(int printnum) {
		this.printnum = printnum;
	}

	//
	// 设置模板
	public void setPrinttemplate(String printtemplate) {
		this.printtemplate = printtemplate;
	}
	//
	// public void setPromotionid(String paramString) {
	// if (TextUtils.isEmptyS(paramString)) {
	// this.promotionid = paramString;
	// return;
	// }
	// this.promotionid = null;
	// }
	//
	// public void setPromotionname(String paramString) {
	// this.promotionname = paramString;
	// }

	// 备注
	public void setRemark(String remark) {
		this.remark = remark;
	}

	// 版本号
	public void setRversion(long rversion) {
		this.rversion = rversion;
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
	public void setSummary(String summary) {
		this.summary = summary;
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
	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}
}