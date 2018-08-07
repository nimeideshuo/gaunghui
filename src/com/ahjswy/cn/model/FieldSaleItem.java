package com.ahjswy.cn.model;

import java.io.Serializable;

import com.ahjswy.cn.utils.Utils;

public class FieldSaleItem implements Serializable {
	private static final long serialVersionUID = 1;
	public String cancelbatchin;
	public double cancelnum;
	public double cancelprice;
	public String cancelproductiondatein;
	public String cancelremark;
	public String cancelunitid;
	public String exchangebatchin;
	public double exchangenum;
	public String exchangeproductiondatein;
	public String exchangeremark;
	public String exchangeunitid;
	public long fieldsaleid;
	public String giftgoodsid;
	public String giftgoodsname;
	public double giftnum;
	public String giftremark;
	public String giftunitid;
	public String giftunitname;
	public double givenum;
	public String giveremark;
	public String giveunitid;
	public String goodsid;
	public boolean isexhibition;
	public boolean ispromotion;
	public int promotiontype;
	public double salenum;
	public double saleprice;
	public String saleremark;
	public String saleunitid;
	public long serialid;
	public String warehouseid;

	public String getCancelbatchin() {
		return this.cancelbatchin;
	}

	public double getCancelnum() {
		return this.cancelnum;
	}

	public double getCancelprice() {
		return this.cancelprice;
	}

	public String getCancelproductiondatein() {
		return this.cancelproductiondatein;
	}

	public String getCancelremark() {
		return this.cancelremark;
	}

	public String getCancelunitid() {
		return this.cancelunitid;
	}

	public String getExchangebatchin() {
		return this.exchangebatchin;
	}

	public double getExchangenum() {
		return this.exchangenum;
	}

	public String getExchangeproductiondatein() {
		return this.exchangeproductiondatein;
	}

	public String getExchangeremark() {
		return this.exchangeremark;
	}

	public String getExchangeunitid() {
		return this.exchangeunitid;
	}

	public long getFieldsaleid() {
		return this.fieldsaleid;
	}

	public String getGiftgoodsid() {
		return this.giftgoodsid;
	}

	public String getGiftgoodsname() {
		return this.giftgoodsname;
	}

	public double getGiftnum() {
		return this.giftnum;
	}

	public String getGiftremark() {
		return this.giftremark;
	}

	public String getGiftunitid() {
		return this.giftunitid;
	}

	public String getGiftunitname() {
		return this.giftunitname;
	}

	public double getGivenum() {
		return this.givenum;
	}

	public String getGiveremark() {
		return this.giveremark;
	}

	public String getGiveunitid() {
		return this.giveunitid;
	}

	public String getGoodsid() {
		return this.goodsid;
	}

	public boolean getIspromotion() {
		return this.ispromotion;
	}

	public int getPromotiontype() {
		return this.promotiontype;
	}

	public double getSalenum() {
		return this.salenum;
	}

	public double getSaleprice() {
		return this.saleprice;
	}

	public String getSaleremark() {
		return this.saleremark;
	}

	public String getSaleunitid() {
		return this.saleunitid;
	}

	public long getSerialid() {
		return this.serialid;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public boolean isIsexhibition() {
		return this.isexhibition;
	}

	public void setCancelbatchin(String cancelbatchin) {
		this.cancelbatchin = cancelbatchin;
	}

	public void setCancelnum(double cancelnum) {
		this.cancelnum = cancelnum;
	}

	public void setCancelprice(double cancelprice) {
		this.cancelprice = Utils.normalizeDouble(cancelprice);
	}

	public void setCancelproductiondatein(String cancelproductiondatein) {
		this.cancelproductiondatein = cancelproductiondatein;
	}

	public void setCancelremark(String cancelremark) {
		this.cancelremark = cancelremark;
	}

	public void setCancelunitid(String cancelunitid) {
		this.cancelunitid = cancelunitid;
	}

	public void setExchangebatchin(String exchangebatchin) {
		this.exchangebatchin = exchangebatchin;
	}

	public void setExchangenum(double exchangenum) {
		this.exchangenum = exchangenum;
	}

	public void setExchangeproductiondatein(String exchangeproductiondatein) {
		this.exchangeproductiondatein = exchangeproductiondatein;
	}

	public void setExchangeremark(String exchangeremark) {
		this.exchangeremark = exchangeremark;
	}

	public void setExchangeunitid(String exchangeunitid) {
		this.exchangeunitid = exchangeunitid;
	}

	public void setFieldsaleid(long fieldsaleid) {
		this.fieldsaleid = fieldsaleid;
	}

	public void setGiftgoodsid(String giftgoodsid) {
		this.giftgoodsid = giftgoodsid;
	}

	public void setGiftgoodsname(String giftgoodsname) {
		this.giftgoodsname = giftgoodsname;
	}

	public void setGiftnum(double giftnum) {
		this.giftnum = giftnum;
	}

	public void setGiftremark(String giftremark) {
		this.giftremark = giftremark;
	}

	public void setGiftunitid(String giftunitid) {
		this.giftunitid = giftunitid;
	}

	public void setGiftunitname(String giftunitname) {
		this.giftunitname = giftunitname;
	}

	public void setGivenum(double givenum) {
		this.givenum = givenum;
	}

	public void setGiveremark(String giveremark) {
		this.giveremark = giveremark;
	}

	public void setGiveunitid(String giveunitid) {
		this.giveunitid = giveunitid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public void setIsexhibition(boolean isexhibition) {
		this.isexhibition = isexhibition;
	}

	public void setIspromotion(boolean ispromotion) {
		this.ispromotion = ispromotion;
	}

	public void setPromotiontype(int promotiontype) {
		this.promotiontype = promotiontype;
	}

	public void setSalenum(double salenum) {
		this.salenum = salenum;
	}

	public void setSaleprice(double saleprice) {
		this.saleprice = Utils.normalizeDouble(saleprice);
	}

	public void setSaleremark(String saleremark) {
		this.saleremark = saleremark;
	}

	public void setSaleunitid(String saleunitid) {
		this.saleunitid = saleunitid;
	}

	public void setSerialid(long serialid) {
		this.serialid = serialid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}
