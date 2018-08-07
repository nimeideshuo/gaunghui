package com.ahjswy.cn.response;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class RespGoodsBatchEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @JsonProperty("batch")
  private String batch;

  @JsonProperty("bigstocknum")
  private String bigstocknum;

  @JsonProperty("goodsid")
  private String goodsid;

  @JsonProperty("productiondate")
  private String productiondate;

  @JsonProperty("stocknum")
  private double stocknum;

  @JsonProperty("warehouseid")
  private String warehouseid;

  @JsonProperty("warehousename")
  private String warehousename;

  public RespGoodsBatchEntity()
  {
  }

  public RespGoodsBatchEntity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, double paramDouble, String paramString6)
  {
    this.goodsid = paramString1;
    this.batch = paramString2;
    this.productiondate = paramString3;
    this.stocknum = paramDouble;
    this.warehouseid = paramString4;
    this.warehousename = paramString5;
    this.bigstocknum = paramString6;
  }

  @JsonIgnore
  public String getBatch()
  {
    return this.batch;
  }

  @JsonIgnore
  public String getBigstocknum()
  {
    return this.bigstocknum;
  }

  @JsonIgnore
  public String getGoodsid()
  {
    return this.goodsid;
  }

  @JsonIgnore
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
  public String getWarehouseId()
  {
    return this.warehouseid;
  }

  @JsonIgnore
  public String getWarehouseName()
  {
    return this.warehousename;
  }

  @JsonIgnore
  public void setBatch(String paramString)
  {
    this.batch = paramString;
  }

  @JsonIgnore
  public void setBigstocknum(String paramString)
  {
    this.bigstocknum = paramString;
  }

  @JsonIgnore
  public void setGoodsid(String paramString)
  {
    this.goodsid = paramString;
  }

  @JsonIgnore
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
  public void setWarehouseId(String paramString)
  {
    this.warehouseid = paramString;
  }

  @JsonIgnore
  public void setWarehouseName(String paramString)
  {
    this.warehousename = paramString;
  }
}