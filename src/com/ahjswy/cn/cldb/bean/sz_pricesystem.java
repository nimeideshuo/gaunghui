package com.ahjswy.cn.cldb.bean;

import java.io.Serializable;

public class sz_pricesystem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String psid;
	public String psname;
	public String pinyin;
	public boolean ischaodan;
	public boolean ischexiao;
	public String remark;
	public boolean islocked;
	public boolean isavailable;
	public String builderid;
	public String buildtime;
	public String modifierid;
	public String modifytime;
	public String rversion;

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	public boolean isIslocked() {
		return islocked;
	}

	public void setIslocked(boolean islocked) {
		this.islocked = islocked;
	}

	public boolean isIsavailable() {
		return isavailable;
	}

	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}

	public String getPsid() {
		return psid;
	}

	public void setPsid(String psid) {
		this.psid = psid;
	}

	public String getPsname() {
		return psname;
	}

	public void setPsname(String psname) {
		this.psname = psname;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public boolean isIschaodan() {
		return ischaodan;
	}

	public void setIschaodan(boolean ischaodan) {
		this.ischaodan = ischaodan;
	}

	public boolean isIschexiao() {
		return ischexiao;
	}

	public void setIschexiao(boolean ischexiao) {
		this.ischexiao = ischexiao;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBuilderid() {
		return builderid;
	}

	public void setBuilderid(String builderid) {
		this.builderid = builderid;
	}

	public String getModifierid() {
		return modifierid;
	}

	public void setModifierid(String modifierid) {
		this.modifierid = modifierid;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getRversion() {
		return rversion;
	}

	public void setRversion(String rversion) {
		this.rversion = rversion;
	}

}
