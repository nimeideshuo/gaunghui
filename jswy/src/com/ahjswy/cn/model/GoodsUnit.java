package com.ahjswy.cn.model;

import java.io.Serializable;

public class GoodsUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	public String goodsid;

	public boolean isbasic;

	public boolean isshow;

	public double ratio;

	public String unitid;

	public String unitname;

	public String getGoodsid() {
		return this.goodsid;
	}

	// 折扣率
	public double getRatio() {
		return this.ratio;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public boolean isIsbasic() {
		return this.isbasic;
	}

	public boolean isIsshow() {
		return this.isshow;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public void setIsbasic(boolean isbasic) {
		this.isbasic = isbasic;
	}

	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	@Override
	public String toString() {
		return "GoodsUnit [goodsid=" + goodsid + ", isbasic=" + isbasic + ", isshow=" + isshow + ", ratio=" + ratio
				+ ", unitid=" + unitid + ", unitname=" + unitname + "]";
	}

}