package com.ahjswy.cn.bean;

import java.io.Serializable;

/**
 * 采购单类
 * 
 * @author Administrator
 *
 */
public class PurchaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;
	private String info;
	private String docjson;
	private String goodsitemjson;
	private String typelistjson;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDocjson() {
		return docjson;
	}

	public void setDocjson(String docjson) {
		this.docjson = docjson;
	}

	public String getGoodsitemjson() {
		return goodsitemjson;
	}

	public void setGoodsitemjson(String goodsitemjson) {
		this.goodsitemjson = goodsitemjson;
	}

	public String getTypelistjson() {
		return typelistjson;
	}

	public void setTypelistjson(String typelistjson) {
		this.typelistjson = typelistjson;
	}

	@Override
	public String toString() {
		return "PurchaseEntity [userid=" + userid + ", info=" + info + ", docjson=" + docjson + ", goodsitemjson="
				+ goodsitemjson + ", typelistjson=" + typelistjson + "]";
	}

}
