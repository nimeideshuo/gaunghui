package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqDocUpload
{

  @JsonProperty("builderid")
  private String builderid;

  @JsonProperty("doc")
  private String doc;

  @JsonProperty("docid")
  private long docid;

  @JsonProperty("issubmit")
  private boolean issubmit;

  @JsonProperty("items")
  private String items;

  @JsonProperty("paytypes")
  private String paytypes;

  public ReqDocUpload()
  {
  }

  public ReqDocUpload(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    this.docid = paramLong;
    this.doc = paramString1;
    this.items = paramString2;
    this.builderid = paramString3;
    this.paytypes = paramString4;
    this.issubmit = paramBoolean;
  }

  @JsonIgnore
  public String getBuilderId()
  {
    return this.builderid;
  }

  @JsonIgnore
  public String getDoc()
  {
    return this.doc;
  }

  @JsonIgnore
  public long getDocId()
  {
    return this.docid;
  }

  @JsonIgnore
  public boolean getIsSubmit()
  {
    return this.issubmit;
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
  public void setBuilderId(String paramString)
  {
    this.builderid = paramString;
  }

  @JsonIgnore
  public void setDoc(String paramString)
  {
    this.doc = paramString;
  }

  @JsonIgnore
  public void setDocId(long paramLong)
  {
    this.docid = paramLong;
  }

  @JsonIgnore
  public void setIsSubmit(boolean paramBoolean)
  {
    this.issubmit = paramBoolean;
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