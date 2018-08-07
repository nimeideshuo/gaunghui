package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class RespStockProcessReParaEntity
{

  @JsonProperty("doctype")
  private String doctype;

  @JsonProperty("info")
  private String info;

  @JsonProperty("issubmitsuccess")
  private boolean issubmitsuccess;

  public RespStockProcessReParaEntity()
  {
  }

  public RespStockProcessReParaEntity(boolean paramBoolean, String paramString1, String paramString2)
  {
    this.issubmitsuccess = paramBoolean;
    this.info = paramString1;
    this.info = paramString2;
  }

  @JsonIgnore
  public String getDocType()
  {
    return this.doctype;
  }

  @JsonIgnore
  public String getInfo()
  {
    return this.info;
  }

  @JsonIgnore
  public boolean getIsSubmitSuccess()
  {
    return this.issubmitsuccess;
  }

  @JsonIgnore
  public void setDocType(String paramString)
  {
    this.doctype = paramString;
  }

  @JsonIgnore
  public void setInfo(String paramString)
  {
    this.info = paramString;
  }

  @JsonIgnore
  public void setIsSubmitSuccess(boolean paramBoolean)
  {
    this.issubmitsuccess = paramBoolean;
  }
}