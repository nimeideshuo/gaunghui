package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSupQueryStockNum
{

  @JsonProperty("goodsid")
  private String goodsid;

  @JsonProperty("isincludetruck")
  private boolean isincludetruck;

  @JsonProperty("warehouseid")
  private String warehouseid;

  public ReqSupQueryStockNum()
  {
  }

  public ReqSupQueryStockNum(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.goodsid = paramString1;
    this.warehouseid = paramString2;
    this.isincludetruck = paramBoolean;
  }

  @JsonIgnore
  public String getGoodsId()
  {
    return this.goodsid;
  }

  @JsonIgnore
  public boolean getIsIncludeTruck()
  {
    return this.isincludetruck;
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
  public void setIsIncludeTruck(boolean paramBoolean)
  {
    this.isincludetruck = paramBoolean;
  }

  @JsonIgnore
  public void setWarehouseId(String paramString)
  {
    this.warehouseid = paramString;
  }
}