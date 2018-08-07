package com.ahjswy.cn.model;

import java.io.Serializable;

public class GoodsClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String pinyin;
	// private String parentgoodsclassid;
	private String remark;
	private boolean isavailable;
	private boolean isselected;
	private String builderid;
	private String buildtime;
	private String modifierid;
	private String rversion;
	private String modifytime;

	public GoodsClass() {
	}

	public GoodsClass(String id, String name, String pinyin) {
		super();
		this.id = id;
		this.name = name;
		this.pinyin = pinyin;
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

	// public String getParentgoodsclassid() {
	// return parentgoodsclassid;
	// }
	//
	// public void setParentgoodsclassid(String parentgoodsclassid) {
	// this.parentgoodsclassid = parentgoodsclassid;
	// }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isIsavailable() {
		return isavailable;
	}

	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}

	public boolean isIsselected() {
		return isselected;
	}

	public void setIsselected(boolean isselected) {
		this.isselected = isselected;
	}

	public String getBuilderid() {
		return builderid;
	}

	public void setBuilderid(String builderid) {
		this.builderid = builderid;
	}

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
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

	@Override
	public String toString() {
		return "GoodsClass [name=" + name + ", pinyin=" + pinyin + ", remark=" + remark + ", isavailable=" + isavailable
				+ ", isselected=" + isselected + ", builderid=" + builderid + ", buildtime=" + buildtime
				+ ", modifierid=" + modifierid + ", rversion=" + rversion + ", modifytime=" + modifytime + "]";
	}

}
