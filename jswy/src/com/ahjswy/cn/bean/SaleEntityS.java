package com.ahjswy.cn.bean;

import java.util.List;

public class SaleEntityS {
	public String userid;
	public String docjson;
	public String docddjson;
	public String info;
	public Def_Doc doccg;
	List<DefDocItemDD> listItem;

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

	public Def_Doc getDoccg() {
		return doccg;
	}

	public void setDoccg(Def_Doc doccg) {
		this.doccg = doccg;
	}

	public List<DefDocItemDD> getListItem() {
		return listItem;
	}

	public void setListItem(List<DefDocItemDD> listItem) {
		this.listItem = listItem;
	}

	@Override
	public String toString() {
		return "SaleEntityS [userid=" + userid + ", docjson=" + docjson + ", docddjson=" + docddjson + ", info=" + info
				+ ", doccg=" + doccg + ", listItem=" + listItem + "]";
	}

}
