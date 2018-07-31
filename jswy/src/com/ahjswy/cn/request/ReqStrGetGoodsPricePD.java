package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqStrGetGoodsPricePD
{

  @JsonProperty("batch")
  private String batch;

  @JsonProperty("costprice")
  private double costprice;

  @JsonProperty("goodsid")
  private String goodsid;

  @JsonProperty("productiondate")
  private String productiondate;

  @JsonProperty("stocknum")
  private double stocknum;

  @JsonProperty("unitid")
  private String unitid;

  @JsonProperty("warehouseid")
  private String warehouseid;

  public String getBatch()
  {
    return this.batch;
  }

  @JsonIgnore
  public double getCostprice()
  {
    return this.costprice;
  }

  @JsonIgnore
  public String getGoodsid()
  {
    return this.goodsid;
  }

  public String getProductiondate()
  {
    return this.productiondate;
  }

  @JsonIgnore
  public double getStocknum()
  {
    return this.stocknum;
  }

  @JsonIgnore
  public String getUnitid()
  {
    return this.unitid;
  }

  @JsonIgnore
  public String getWarehouseid()
  {
    return this.warehouseid;
  }

  public void setBatch(String paramString)
  {
    this.batch = paramString;
  }

  @JsonIgnore
  public void setCostprice(double paramDouble)
  {
    this.costprice = paramDouble;
  }

  @JsonIgnore
  public void setGoodsid(String paramString)
  {
    this.goodsid = paramString;
  }

  public void setProductiondate(String paramString)
  {
    this.productiondate = paramString;
  }

  @JsonIgnore
  public void setStocknum(double paramDouble)
  {
    this.stocknum = paramDouble;
  }

  @JsonIgnore
  public void setUnitid(String paramString)
  {
    this.unitid = paramString;
  }

  @JsonIgnore
  public void setWarehouseid(String paramString)
  {
    this.warehouseid = paramString;
  }
}