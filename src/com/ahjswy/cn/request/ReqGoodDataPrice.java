package com.ahjswy.cn.request;

import java.io.Serializable;

public class ReqGoodDataPrice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String startDataTime;
	public String endDataTime;
	public String goodsId;

	public String getStartDataTime() {
		return startDataTime;
	}

	public void setStartDataTime(String startDataTime) {
		this.startDataTime = startDataTime;
	}

	public String getEndDataTime() {
		return endDataTime;
	}

	public void setEndDataTime(String endDataTime) {
		this.endDataTime = endDataTime;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

}
