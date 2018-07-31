package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ahjswy.cn.utils.Utils;

public class ReqDocAddChaoDanItem
{

  @JsonProperty("cancelnum")
  private double cancelnum;

  @JsonProperty("cancelprice")
  private double cancelprice;

  @JsonProperty("cancelunitid")
  private String cancelunitid;

  @JsonProperty("exchangenum")
  private double exchangenum;

  @JsonProperty("exchangeunitid")
  private String exchangeunitid;

  @JsonProperty("giftgoodsid")
  private String giftgoodsid;

  @JsonProperty("giftnum")
  private double giftnum;

  @JsonProperty("giftunitid")
  private String giftunitid;

  @JsonProperty("givenum")
  private double givenum;

  @JsonProperty("giveunitid")
  private String giveunitid;

  @JsonProperty("goodsid")
  private String goodsid;

  @JsonProperty("ispromotion")
  private boolean ispromotion;

  @JsonProperty("promotiontype")
  private int promotiontype;

  @JsonProperty("remark")
  private String remark;

  @JsonProperty("salenum")
  private double salenum;

  @JsonProperty("saleprice")
  private double saleprice;

  @JsonProperty("saleunitid")
  private String saleunitid;

  @JsonProperty("warehouseid")
  private String warehouseid;

  public ReqDocAddChaoDanItem()
  {
  }

  public ReqDocAddChaoDanItem(String paramString1, String paramString2, double paramDouble1, double paramDouble2, String paramString3, double paramDouble3, String paramString4, double paramDouble4, double paramDouble5, String paramString5, double paramDouble6, String paramString6, String paramString7, boolean paramBoolean, int paramInt, String paramString8, String paramString9, double paramDouble7)
  {
    this.goodsid = paramString1;
    this.warehouseid = paramString2;
    this.salenum = paramDouble1;
    this.saleprice = Utils.normalizeDouble(paramDouble2);
    this.saleunitid = paramString3;
    this.givenum = paramDouble3;
    this.giveunitid = paramString4;
    this.cancelnum = paramDouble4;
    this.cancelprice = Utils.normalizeDouble(paramDouble5);
    this.cancelunitid = paramString5;
    this.exchangenum = paramDouble6;
    this.exchangeunitid = paramString6;
    this.remark = paramString7;
    this.ispromotion = paramBoolean;
    this.promotiontype = paramInt;
    this.giftgoodsid = paramString8;
    this.giftunitid = paramString9;
    this.giftnum = paramDouble7;
  }

  @JsonIgnore
  public double getCancelNum()
  {
    return this.cancelnum;
  }

  @JsonIgnore
  public double getCancelPrice()
  {
    return this.cancelprice;
  }

  @JsonIgnore
  public String getCancelUnitId()
  {
    return this.cancelunitid;
  }

  @JsonIgnore
  public double getExchangeNum()
  {
    return this.exchangenum;
  }

  @JsonIgnore
  public String getExchangeUnitId()
  {
    return this.exchangeunitid;
  }

  @JsonIgnore
  public String getGiftGoodsId()
  {
    return this.giftgoodsid;
  }

  @JsonIgnore
  public double getGiftNum()
  {
    return this.giftnum;
  }

  @JsonIgnore
  public String getGiftUnitId()
  {
    return this.giftunitid;
  }

  @JsonIgnore
  public double getGiveNum()
  {
    return this.givenum;
  }

  @JsonIgnore
  public String getGiveUnitId()
  {
    return this.giveunitid;
  }

  @JsonIgnore
  public String getGoodsId()
  {
    return this.goodsid;
  }

  @JsonIgnore
  public boolean getIsPromotion()
  {
    return this.ispromotion;
  }

  @JsonIgnore
  public int getPromotionType()
  {
    return this.promotiontype;
  }

  @JsonIgnore
  public String getRemark()
  {
    return this.remark;
  }

  @JsonIgnore
  public double getSaleNum()
  {
    return this.salenum;
  }

  @JsonIgnore
  public double getSalePrice()
  {
    return this.saleprice;
  }

  @JsonIgnore
  public String getSaleUnitId()
  {
    return this.saleunitid;
  }

  @JsonIgnore
  public String getWarehouseId()
  {
    return this.warehouseid;
  }

  @JsonIgnore
  public void setCancelNum(double paramDouble)
  {
    this.cancelnum = paramDouble;
  }

  @JsonIgnore
  public void setCancelPrice(double paramDouble)
  {
    this.cancelprice = Utils.normalizeDouble(paramDouble);
  }

  @JsonIgnore
  public void setCancelUnitId(String paramString)
  {
    this.cancelunitid = paramString;
  }

  @JsonIgnore
  public void setExchangeNum(double paramDouble)
  {
    this.exchangenum = paramDouble;
  }

  @JsonIgnore
  public void setExchangeUnitId(String paramString)
  {
    this.exchangeunitid = paramString;
  }

  @JsonIgnore
  public void setGiftGoodsId(String paramString)
  {
    this.giftgoodsid = paramString;
  }

  @JsonIgnore
  public void setGiftNum(double paramDouble)
  {
    this.giftnum = paramDouble;
  }

  @JsonIgnore
  public void setGiftUnitId(String paramString)
  {
    this.giftunitid = paramString;
  }

  @JsonIgnore
  public void setGiveNum(double paramDouble)
  {
    this.givenum = paramDouble;
  }

  @JsonIgnore
  public void setGiveUnitId(String paramString)
  {
    this.giveunitid = paramString;
  }

  @JsonIgnore
  public void setGoodsId(String paramString)
  {
    this.goodsid = paramString;
  }

  @JsonIgnore
  public void setIsPromotion(boolean paramBoolean)
  {
    this.ispromotion = paramBoolean;
  }

  @JsonIgnore
  public void setPromotionType(int paramInt)
  {
    this.promotiontype = paramInt;
  }

  @JsonIgnore
  public void setRemark(String paramString)
  {
    this.remark = paramString;
  }

  @JsonIgnore
  public void setSaleNum(double paramDouble)
  {
    this.salenum = paramDouble;
  }

  @JsonIgnore
  public void setSalePrice(double paramDouble)
  {
    this.saleprice = Utils.normalizeDouble(paramDouble);
  }

  @JsonIgnore
  public void setSaleUnitId(String paramString)
  {
    this.saleunitid = paramString;
  }

  @JsonIgnore
  public void setWarehouseId(String paramString)
  {
    this.warehouseid = paramString;
  }
}