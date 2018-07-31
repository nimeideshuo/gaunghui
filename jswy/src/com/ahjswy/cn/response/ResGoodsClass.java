package com.ahjswy.cn.response;

/**
 * 新增商品类别
 * 
 * @author Administrator
 *
 */
public class ResGoodsClass {
	private String name;
	private String pinyin;
	private String id;
	private String parentgoodsclassid;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentgoodsclassid() {
		return parentgoodsclassid;
	}

	public void setParentgoodsclassid(String parentgoodsclassid) {
		this.parentgoodsclassid = parentgoodsclassid;
	}

}
