package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class RespUserEntity
{

  @JsonProperty("goodsclassid")
  private String goodsclassid;

  @JsonProperty("gpsinterval")
  private int gpsinterval;

  @JsonProperty("id")
  private String id;

  @JsonProperty("isaccountmanager")
  private boolean isaccountmanager;

  @JsonProperty("isavailable")
  private boolean isavailable;

  @JsonProperty("name")
  private String name;

  @JsonProperty("visitlineid")
  private String visitlineid;

  public RespUserEntity()
  {
  }

  public RespUserEntity(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    this.id = paramString1;
    this.name = paramString2;
    this.visitlineid = paramString3;
    this.goodsclassid = paramString4;
    this.isaccountmanager = paramBoolean1;
    this.isavailable = paramBoolean2;
    this.gpsinterval = paramInt;
  }

  public String getGoodsclassid()
  {
    return this.goodsclassid;
  }

  @JsonIgnore
  public int getGpsInterval()
  {
    return this.gpsinterval;
  }

  @JsonIgnore
  public String getId()
  {
    return this.id;
  }

  @JsonIgnore
  public boolean getIsAccountManager()
  {
    return this.isaccountmanager;
  }

  @JsonIgnore
  public boolean getIsAvailable()
  {
    return this.isavailable;
  }

  @JsonIgnore
  public String getName()
  {
    return this.name;
  }

  public String getVisitlineid()
  {
    return this.visitlineid;
  }

  public void setGoodsclassid(String paramString)
  {
    this.goodsclassid = paramString;
  }

  @JsonIgnore
  public void setGpsInterval(int paramInt)
  {
    this.gpsinterval = paramInt;
  }

  @JsonIgnore
  public void setId(String paramString)
  {
    this.id = paramString;
  }

  @JsonIgnore
  public void setIsAccountManager(boolean paramBoolean)
  {
    this.isaccountmanager = paramBoolean;
  }

  @JsonIgnore
  public void setIsAvailable(boolean paramBoolean)
  {
    this.isavailable = paramBoolean;
  }

  @JsonIgnore
  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setVisitlineid(String paramString)
  {
    this.visitlineid = paramString;
  }
}