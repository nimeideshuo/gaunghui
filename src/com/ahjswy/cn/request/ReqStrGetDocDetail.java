package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqStrGetDocDetail
{

  @JsonProperty("docid")
  private long docid;

  @JsonProperty("doctype")
  private String doctype;

  @JsonIgnore
  public long getDocid()
  {
    return this.docid;
  }

  @JsonIgnore
  public String getDoctype()
  {
    return this.doctype;
  }

  @JsonIgnore
  public void setDocid(long paramLong)
  {
    this.docid = paramLong;
  }

  @JsonIgnore
  public void setDoctype(String paramString)
  {
    this.doctype = paramString;
  }
}