package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class RespQueryStockWithTimeEntity
{

  @JsonProperty("bigstocknumber")
  private String bigstocknumber;

  @JsonProperty("getstocktime")
  private long getstocktime;

  @JsonProperty("stocknum")
  private Double stocknum;

  public RespQueryStockWithTimeEntity()
  {
  }

  public RespQueryStockWithTimeEntity(Double paramDouble, String paramString, long paramLong)
  {
    this.stocknum = paramDouble;
    this.bigstocknumber = paramString;
    this.getstocktime = paramLong;
  }

  @JsonIgnore
  public String getBigStockNumber()
  {
    return this.bigstocknumber;
  }

  @JsonIgnore
  public long getGetStockTime()
  {
    return this.getstocktime;
  }

  @JsonIgnore
  public Double getStockNum()
  {
    return this.stocknum;
  }

  @JsonIgnore
  public void setBigStockNumber(String paramString)
  {
    this.bigstocknumber = paramString;
  }

  @JsonIgnore
  public void setGetStockTime(long paramLong)
  {
    this.getstocktime = paramLong;
  }

  @JsonIgnore
  public void setStockNum(Double paramDouble)
  {
    this.stocknum = paramDouble;
  }
}