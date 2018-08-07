package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqDocUploadForSettleUp
{

  @JsonProperty("doc")
  private String doc;

  @JsonProperty("items")
  private String items;

  @JsonProperty("paytypes")
  private String paytypes;

  public ReqDocUploadForSettleUp()
  {
  }

  public ReqDocUploadForSettleUp(String paramString1, String paramString2, String paramString3)
  {
    this.doc = paramString1;
    this.items = paramString2;
    this.paytypes = paramString3;
  }

  @JsonIgnore
  public String getDoc()
  {
    return this.doc;
  }

  @JsonIgnore
  public String getItems()
  {
    return this.items;
  }

  @JsonIgnore
  public String getPayTypes()
  {
    return this.paytypes;
  }

  @JsonIgnore
  public void setDoc(String paramString)
  {
    this.doc = paramString;
  }

  @JsonIgnore
  public void setItems(String paramString)
  {
    this.items = paramString;
  }

  @JsonIgnore
  public void setPayTypes(String paramString)
  {
    this.paytypes = paramString;
  }
}