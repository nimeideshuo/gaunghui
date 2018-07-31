package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqDopGetWarehouse
{

  @JsonProperty("goodsid")
  private String goodsid;

  @JsonProperty("warehouseid")
  private String warehouseid;

  public ReqDopGetWarehouse()
  {
  }

  public ReqDopGetWarehouse(String paramString1, String paramString2)
  {
    this.goodsid = paramString1;
    this.warehouseid = paramString2;
  }

  @JsonIgnore
  public String getGoodsId()
  {
    return this.goodsid;
  }

  @JsonIgnore
  public String getWarehouseId()
  {
    return this.warehouseid;
  }

  @JsonIgnore
  public void setGoodsId(String paramString)
  {
    this.goodsid = paramString;
  }

  @JsonIgnore
  public void setWarehouseId(String paramString)
  {
    this.warehouseid = paramString;
  }
}