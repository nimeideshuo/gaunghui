package com.ahjswy.cn.response;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class RespPromotionRule
  implements Serializable
{

  @JsonIgnore
  private static final long serialVersionUID = 1L;

  @JsonProperty("barcode")
  private String barcode;

  @JsonProperty("batch")
  private String batch;

  @JsonProperty("goodsid")
  private String goodsid;

  @JsonProperty("goodsname")
  private String goodsname;

  @JsonProperty("model")
  private String model;

  @JsonProperty("price")
  private double price;

  @JsonProperty("productiondate")
  private String productiondate;

  @JsonProperty("specification")
  private String specification;

  @JsonProperty("summary")
  private String summary;

  @JsonProperty("unitid")
  private String unitid;

  @JsonProperty("unitname")
  private String unitname;

  @JsonProperty("warehouseid")
  private String warehouseid;

  @JsonProperty("warehousename")
  private String warehousename;

  @JsonIgnore
  public String getBarcode()
  {
    return this.barcode;
  }

  public String getBatch()
  {
    return this.batch;
  }

  @JsonIgnore
  public String getGoodsid()
  {
    return this.goodsid;
  }

  @JsonIgnore
  public String getGoodsname()
  {
    return this.goodsname;
  }

  @JsonIgnore
  public String getModel()
  {
    return this.model;
  }

  @JsonIgnore
  public double getPrice()
  {
    return this.price;
  }

  public String getProductiondate()
  {
    return this.productiondate;
  }

  @JsonIgnore
  public String getSpecification()
  {
    return this.specification;
  }

  @JsonIgnore
  public String getSummary()
  {
    return this.summary;
  }

  @JsonIgnore
  public String getUnitid()
  {
    return this.unitid;
  }

  @JsonIgnore
  public String getUnitname()
  {
    return this.unitname;
  }

  @JsonIgnore
  public String getWarehouseid()
  {
    return this.warehouseid;
  }

  @JsonIgnore
  public String getWarehousename()
  {
    return this.warehousename;
  }

  @JsonIgnore
  public void setBarcode(String paramString)
  {
    this.barcode = paramString;
  }

  public void setBatch(String paramString)
  {
    this.batch = paramString;
  }

  @JsonIgnore
  public void setGoodsid(String paramString)
  {
    this.goodsid = paramString;
  }

  @JsonIgnore
  public void setGoodsname(String paramString)
  {
    this.goodsname = paramString;
  }

  @JsonIgnore
  public void setModel(String paramString)
  {
    this.model = paramString;
  }

  @JsonIgnore
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }

  public void setProductiondate(String paramString)
  {
    this.productiondate = paramString;
  }

  @JsonIgnore
  public void setSpecification(String paramString)
  {
    this.specification = paramString;
  }

  @JsonIgnore
  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }

  @JsonIgnore
  public void setUnitid(String paramString)
  {
    this.unitid = paramString;
  }

  @JsonIgnore
  public void setUnitname(String paramString)
  {
    this.unitname = paramString;
  }

  @JsonIgnore
  public void setWarehouseid(String paramString)
  {
    this.warehouseid = paramString;
  }

  @JsonIgnore
  public void setWarehousename(String paramString)
  {
    this.warehousename = paramString;
  }
}