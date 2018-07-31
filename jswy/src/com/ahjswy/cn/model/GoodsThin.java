package com.ahjswy.cn.model;

import java.io.Serializable;

public class GoodsThin implements Serializable {
	private static final long serialVersionUID = 1L;
	private String barcode;
	private String id;
	private boolean isusebatch;
	private String model;
	private String name;
	private String pinyin;
	private String specification;

	// 条码
	public String getBarcode() {
		return this.barcode;
	}

	// id
	public String getId() {
		return this.id;
	}

	// 型号
	public String getModel() {
		return this.model;
	}

	// 名称
	public String getName() {
		return this.name;
	}

	public String getPinyin() {
		return this.pinyin;
	}

	public String getSpecification() {
		return this.specification;
	}

	public boolean isIsusebatch() {
		return this.isusebatch;
	}

	public void setBarcode(String paramString) {
		this.barcode = paramString;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setIsusebatch(boolean paramBoolean) {
		this.isusebatch = paramBoolean;
	}

	public void setModel(String paramString) {
		this.model = paramString;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setPinyin(String paramString) {
		this.pinyin = paramString;
	}

	public void setSpecification(String paramString) {
		this.specification = paramString;
	}

	@Override
	public String toString() {
		return "GoodsThin [barcode=" + barcode + ", id=" + id + ", isusebatch=" + isusebatch + ", model=" + model
				+ ", name=" + name + ", pinyin=" + pinyin + ", specification=" + specification + "]";
	}

}