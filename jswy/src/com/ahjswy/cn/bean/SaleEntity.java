package com.ahjswy.cn.bean;

import java.io.Serializable;

public class SaleEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String userid;
	public String docjson;
	public String docddjson;
	public String info;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDocjson() {
		return docjson;
	}

	public void setDocjson(String docjson) {
		this.docjson = docjson;
	}

	public String getDocddjson() {
		return docddjson;
	}

	public void setDocddjson(String docddjson) {
		this.docddjson = docddjson;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "SaleEntity [userid=" + userid + ", docjson=" + docjson + ", docddjson=" + docddjson + ", info=" + info
				+ "]";
	}

}
