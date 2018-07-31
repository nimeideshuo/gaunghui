package com.ahjswy.cn.model;

import java.io.Serializable;

public class DefDocPayType implements Serializable, Comparable<DefDocPayType> {
	private static final long serialVersionUID = 1L;
	private double amount;
	private long docid;
	private long itemid;
	private String paytypeid;
	private String paytypename;
	private long rversion;

	public double getAmount() {
		return this.amount;
	}

	public long getDocid() {
		return this.docid;
	}

	public long getItemid() {
		return this.itemid;
	}

	public String getPaytypeid() {
		return this.paytypeid;
	}

	public String getPaytypename() {
		return this.paytypename;
	}

	public long getRversion() {
		return this.rversion;
	}

	// 设置总额
	public void setAmount(double paramDouble) {
		this.amount = paramDouble;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public void setPaytypeid(String paytypeid) {
		this.paytypeid = paytypeid;
	}

	public void setPaytypename(String paytypename) {
		this.paytypename = paytypename;
	}

	public void setRversion(long rversion) {
		this.rversion = rversion;
	}

	@Override
	public String toString() {
		return "DefDocPayType [amount=" + amount + ", docid=" + docid + ", itemid=" + itemid + ", paytypeid="
				+ paytypeid + ", paytypename=" + paytypename + ", rversion=" + rversion + "]";
	}

	@Override
	public int compareTo(DefDocPayType another) {
		return -1;
	}

}