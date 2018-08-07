package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqDocUploadForChaoDan
{

  @JsonProperty("doc")
  private String doc;

  @JsonProperty("indocid")
  private long indocid;

  @JsonProperty("items")
  private String items;

  @JsonProperty("outdocid")
  private long outdocid;

  @JsonProperty("outexchangedocid")
  private long outexchangedocid;

  @JsonProperty("paytypes")
  private String paytypes;

  public ReqDocUploadForChaoDan()
  {
  }

  public ReqDocUploadForChaoDan(long paramLong1, long paramLong2, long paramLong3, String paramString1, String paramString2, String paramString3)
  {
    this.outdocid = paramLong1;
    this.indocid = paramLong2;
    this.outexchangedocid = paramLong3;
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
  public long getInDocId()
  {
    return this.indocid;
  }

  @JsonIgnore
  public String getItems()
  {
    return this.items;
  }

  @JsonIgnore
  public long getOutDocId()
  {
    return this.outdocid;
  }

  @JsonIgnore
  public long getOutExchangeDocId()
  {
    return this.outexchangedocid;
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
  public void setInDocId(long paramLong)
  {
    this.indocid = paramLong;
  }

  @JsonIgnore
  public void setItems(String paramString)
  {
    this.items = paramString;
  }

  @JsonIgnore
  public void setOutDocId(long paramLong)
  {
    this.outdocid = paramLong;
  }

  @JsonIgnore
  public void setOutExchangeDocId(long paramLong)
  {
    this.outexchangedocid = paramLong;
  }

  @JsonIgnore
  public void setPayTypes(String paramString)
  {
    this.paytypes = paramString;
  }
}