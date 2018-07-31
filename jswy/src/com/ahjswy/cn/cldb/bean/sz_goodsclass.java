package com.ahjswy.cn.cldb.bean;

import java.io.Serializable;

public class sz_goodsclass implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id;
	public String name;
	public String pinyin;
	public String parentgoodsclassid;
	public String remark;
	public boolean isavailable;
	public String iselected;
	public String builderid;
	public String buildtime;
	public String modifierid;
	public String rversion;
	public String modifytime;

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getParentgoodsclassid() {
		return parentgoodsclassid;
	}

	public void setParentgoodsclassid(String parentgoodsclassid) {
		this.parentgoodsclassid = parentgoodsclassid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getIsavailable() {
		return isavailable;
	}

	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}

	public String getIselected() {
		return iselected;
	}

	public void setIselected(String iselected) {
		this.iselected = iselected;
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

	public String getRversion() {
		return rversion;
	}

	public void setRversion(String rversion) {
		this.rversion = rversion;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

}
