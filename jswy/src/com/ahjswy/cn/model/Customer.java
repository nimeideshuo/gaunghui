package com.ahjswy.cn.model;

import java.io.Serializable;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	private String bankingaccount;

	private String contact;

	private String contactmoblie;

	private String customertypeid;

	private String depositbank;

	private String id;

	private double latitude;

	private double longitude;

	private String name;

	private String pinyin;

	private String pricesystemid;

	private String promotionid;

	private String regionid;

	private String remark;

	private String telephone;

	private String visitlineid;

	public String getAddress() {
		return this.address;
	}

	public String getBankingAccount() {
		return this.bankingaccount;
	}

	public String getContact() {
		return this.contact;
	}

	public String getContactMoblie() {
		return this.contactmoblie;
	}

	public String getCustomerTypeId() {
		return this.customertypeid;
	}

	public String getDepositBank() {
		return this.depositbank;
	}

	public String getId() {
		return this.id;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public String getName() {
		return this.name;
	}

	public String getPinyin() {
		return this.pinyin;
	}

	public String getPriceSystemId() {
		return this.pricesystemid;
	}

	public String getPromotionId() {
		return this.promotionid;
	}

	public String getRegionId() {
		return this.regionid;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public String getVisitLineId() {
		return this.visitlineid;
	}

	public void setAddress(String paramString) {
		this.address = paramString;
	}

	public void setBankingAccount(String paramString) {
		this.bankingaccount = paramString;
	}

	public void setContact(String paramString) {
		this.contact = paramString;
	}

	public void setContactMoblie(String paramString) {
		this.contactmoblie = paramString;
	}

	public void setCustomerTypeId(String paramString) {
		this.customertypeid = paramString;
	}

	public void setDepositBank(String paramString) {
		this.depositbank = paramString;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setLatitude(double paramDouble) {
		this.latitude = paramDouble;
	}

	public void setLongitude(double paramDouble) {
		this.longitude = paramDouble;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setPinyin(String paramString) {
		this.pinyin = paramString;
	}

	public void setPriceSystemId(String paramString) {
		this.pricesystemid = paramString;
	}

	public void setPromotionId(String paramString) {
		this.promotionid = paramString;
	}

	public void setRegionId(String paramString) {
		this.regionid = paramString;
	}

	public void setRemark(String paramString) {
		this.remark = paramString;
	}

	public void setTelephone(String paramString) {
		this.telephone = paramString;
	}

	public void setVisitLineId(String paramString) {
		this.visitlineid = paramString;
	}
}