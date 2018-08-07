package com.ahjswy.cn.model;

import java.io.Serializable;

public class CustomerThin implements Serializable {
	private static final long serialVersionUID = 1L;
	private String address;
	private String contactmoblie;
	private double discountratio;
	private String id;
	private String name;
  private String pinyin;
  private String pricesystemid;
  private String promotionid;
  private String promotionname;
  private int settleterm;

  public String getAddress()
  {
    return this.address;
  }

  public String getContactmoblie()
  {
    return this.contactmoblie;
  }

  public double getDiscountratio()
  {
    return this.discountratio;
  }

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPinyin()
  {
    return this.pinyin;
  }

  public String getPricesystemid()
  {
    return this.pricesystemid;
  }

  public String getPromotionid()
  {
    return this.promotionid;
  }

  public String getPromotionname()
  {
    return this.promotionname;
  }

  public int getSettleterm()
  {
    return this.settleterm;
  }

  public void setAddress(String paramString)
  {
    this.address = paramString;
  }

  public void setContactmoblie(String paramString)
  {
    this.contactmoblie = paramString;
  }

  public void setDiscountratio(double paramDouble)
  {
    this.discountratio = paramDouble;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPinyin(String paramString)
  {
    this.pinyin = paramString;
  }

  public void setPricesystemid(String paramString)
  {
    this.pricesystemid = paramString;
  }

  public void setPromotionid(String paramString)
  {
    this.promotionid = paramString;
  }

  public void setPromotionname(String paramString)
  {
    this.promotionname = paramString;
  }

  public void setSettleterm(int paramInt)
  {
    this.settleterm = paramInt;
  }
}