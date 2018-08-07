package com.ahjswy.cn.model;

import java.io.Serializable;
import java.util.List;

public class DocContainerEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String deleteinitem;

	private String deleteitem;

	private String doc;

	private String doctype;

	private String info;

	private String initem;

	private String item;
//	private List<T> listitem;
//
//	public void setListitem(List<T> listitem) {
//		this.listitem = listitem;
//	}
//
//	public List<T> getListitem() {
//		return listitem;
//	}

	private String paytype;
	private int svid;

	public int getSvid() {
		return svid;
	}

	public void setSvid(int svid) {
		this.svid = svid;
	}

	public String getDeleteinitem() {
		return this.deleteinitem;
	}

	public String getDeleteitem() {
		return this.deleteitem;
	}

	public String getDoc() {
		return this.doc;
	}

	public String getDoctype() {
		return this.doctype;
	}

	public String getInfo() {
		return this.info;
	}

	public String getInitem() {
		return this.initem;
	}

	public String getItem() {
		return this.item;
	}

	public String getPaytype() {
		return this.paytype;
	}

	public void setDeleteinitem(String deleteinitem) {
		this.deleteinitem = deleteinitem;
	}

	public void setDeleteitem(String deleteitem) {
		this.deleteitem = deleteitem;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setInitem(String initem) {
		this.initem = initem;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String showid;

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	@Override
	public String toString() {
		return "DocContainerEntity [deleteinitem=" + deleteinitem + ", deleteitem=" + deleteitem + ", doc=" + doc
				+ ", doctype=" + doctype + ", info=" + info + ", initem=" + initem + ", item=" + item +  ", paytype=" + paytype + ", svid=" + svid + ", showid=" + showid + "]";
	}

}