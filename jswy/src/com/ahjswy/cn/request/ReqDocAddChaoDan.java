package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ahjswy.cn.utils.Utils;

public class ReqDocAddChaoDan {

	@JsonProperty("builderid")
	private String builderid;

	@JsonProperty("buildtime")
	private long buildtime;

	@JsonProperty("customerid")
	private String customerid;

	@JsonProperty("deliverytime")
	private long deliverytime;

	@JsonProperty("departmentid")
	private String departmentid;

	@JsonProperty("iscarsell")
	private boolean iscarsell;

	@JsonProperty("mobile")
	private String mobile;

	@JsonProperty("preference")
	private Double preference;

	@JsonProperty("pricesystemid")
	private String pricesystemid;

	@JsonProperty("printnum")
	private int printnum;

	@JsonProperty("promotionid")
	private String promotionid;

	@JsonProperty("remark")
	private String remark;

	@JsonProperty("settletime")
	private long settletime;

	@JsonProperty("warehouseid")
	private String warehouseid;

	public ReqDocAddChaoDan() {
		this.deliverytime = Utils.getCurrentTime(false);
		this.settletime = Utils.getCurrentTime(false);
	}

	public ReqDocAddChaoDan(boolean paramBoolean, String paramString1, String paramString2, String paramString3,
			Double paramDouble, String paramString4, String paramString5, String paramString6, long paramLong,
			String paramString7, String paramString8, int paramInt) {
		this.iscarsell = paramBoolean;
		this.customerid = paramString1;
		this.departmentid = paramString2;
		this.warehouseid = paramString3;
		this.preference = Double.valueOf(Utils.normalizeDouble(paramDouble.doubleValue()));
		this.pricesystemid = paramString4;
		this.promotionid = paramString5;
		this.deliverytime = Utils.getCurrentTime(false);
		this.settletime = Utils.getCurrentTime(false);
		this.builderid = paramString6;
		this.buildtime = paramLong;
		this.mobile = paramString7;
		this.remark = paramString8;
		this.printnum = paramInt;
	}

	@JsonIgnore
	public long getBuildTime() {
		return this.buildtime;
	}

	@JsonIgnore
	public String getBuilderId() {
		return this.builderid;
	}

	@JsonIgnore
	public String getCustomerId() {
		return this.customerid;
	}

	@JsonIgnore
	public long getDeliveryTime() {
		return this.deliverytime;
	}

	@JsonIgnore
	public String getDepartmentId() {
		return this.departmentid;
	}

	@JsonIgnore
	public boolean getIsCarsell() {
		return this.iscarsell;
	}

	@JsonIgnore
	public String getMobile() {
		return this.mobile;
	}

	@JsonIgnore
	public double getPreference() {
		return this.preference.doubleValue();
	}

	@JsonIgnore
	public String getPriceSystemId() {
		return this.pricesystemid;
	}

	@JsonIgnore
	public int getPrintNum() {
		return this.printnum;
	}

	@JsonIgnore
	public String getPromotionId() {
		return this.promotionid;
	}

	@JsonIgnore
	public String getRemark() {
		return this.remark;
	}

	@JsonIgnore
	public long getSettleTime() {
		return this.settletime;
	}

	@JsonIgnore
	public String getWarehouseId() {
		return this.warehouseid;
	}

	@JsonIgnore
	public void setBuildTime(Long paramLong) {
		this.buildtime = paramLong.longValue();
	}

	@JsonIgnore
	public void setBuilderId(String paramString) {
		this.builderid = paramString;
	}

	@JsonIgnore
	public void setCustomerId(String paramString) {
		this.customerid = paramString;
	}

	@JsonIgnore
	public void setDeliveryTime(Long paramLong) {
		this.deliverytime = paramLong.longValue();
	}

	@JsonIgnore
	public void setDepartmentId(String paramString) {
		this.departmentid = paramString;
	}

	@JsonIgnore
	public void setIsCarsell(boolean paramBoolean) {
		this.iscarsell = paramBoolean;
	}

	@JsonIgnore
	public void setMobile(String paramString) {
		this.mobile = paramString;
	}

	@JsonIgnore
	public void setPreference(double paramDouble) {
		this.preference = Double.valueOf(Utils.normalizeDouble(paramDouble));
	}

	@JsonIgnore
	public void setPriceSystemId(String paramString) {
		this.pricesystemid = paramString;
	}

	@JsonIgnore
	public void setPrintNum(int paramInt) {
		this.printnum = paramInt;
	}

	@JsonIgnore
	public void setPromotionId(String paramString) {
		this.promotionid = paramString;
	}

	@JsonIgnore
	public void setRemark(String paramString) {
		this.remark = paramString;
	}

	@JsonIgnore
	public void setSettleTime(Long paramLong) {
		this.settletime = paramLong.longValue();
	}

	@JsonIgnore
	public void setWarehouseId(String paramString) {
		this.warehouseid = paramString;
	}
}