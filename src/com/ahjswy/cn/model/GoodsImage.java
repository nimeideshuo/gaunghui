package com.ahjswy.cn.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class GoodsImage
{

  @JsonProperty("goodsid")
  private String goodsid;

  @JsonProperty("imagepath")
  private String imagepath;

  @JsonProperty("isgot")
  private boolean isgot;

  @JsonProperty("serialid")
  private long serialid;

  public GoodsImage()
  {
  }

  public GoodsImage(long paramLong, String paramString1, String paramString2, boolean paramBoolean)
  {
    this.serialid = paramLong;
    this.goodsid = paramString1;
    this.imagepath = paramString2;
    this.isgot = paramBoolean;
  }

  @JsonIgnore
  public String getGoodsId()
  {
    return this.goodsid;
  }

  @JsonIgnore
  public String getImagePath()
  {
    return this.imagepath;
  }

  @JsonIgnore
  public boolean getIsGot()
  {
    return this.isgot;
  }

  @JsonIgnore
  public long getSerialid()
  {
    return this.serialid;
  }

  @JsonIgnore
  public void setGoodsId(String paramString)
  {
    this.goodsid = paramString;
  }

  @JsonIgnore
  public void setImagePath(String paramString)
  {
    this.imagepath = paramString;
  }

  @JsonIgnore
  public void setIsGot(boolean paramBoolean)
  {
    this.isgot = paramBoolean;
  }

  @JsonIgnore
  public void setSerialid(long paramLong)
  {
    this.serialid = paramLong;
  }
}