package com.ahjswy.cn.response;

/**
 * 商品库存
 * 
 * @author Administrator
 *
 */
public class RespStockwarn {

	public String warehouseid;
	public String goodsid;
	public double stocknum;

	@Override
	public String toString() {
		return "RespStockwarn [warehouseid=" + warehouseid + ", goodsid=" + goodsid + ", stocknum=" + stocknum + "]";
	}

}
