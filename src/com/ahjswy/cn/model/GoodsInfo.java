package com.ahjswy.cn.model;

public class GoodsInfo extends Goods
{
  private static final long serialVersionUID = 1L;
  private double price;
  private String unitname;

  public double getPrice()
  {
    return this.price;
  }

  public String getUnitname()
  {
    return this.unitname;
  }

  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }

  public void setUnitname(String paramString)
  {
    this.unitname = paramString;
  }
}