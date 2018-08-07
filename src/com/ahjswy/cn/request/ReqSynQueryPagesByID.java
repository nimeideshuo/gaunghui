package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSynQueryPagesByID
{

  @JsonProperty("id")
  private String id;

  @JsonProperty("pagesize")
  private int pagesize;

  @JsonProperty("rversion")
  private long rversion;

  public ReqSynQueryPagesByID()
  {
  }

  public ReqSynQueryPagesByID(String paramString, int paramInt, long paramLong)
  {
    this.id = paramString;
    this.pagesize = paramInt;
    this.rversion = paramLong;
  }

  @JsonIgnore
  public String getId()
  {
    return this.id;
  }

  @JsonIgnore
  public int getPageSize()
  {
    return this.pagesize;
  }

  @JsonIgnore
  public long getRVersion()
  {
    return this.rversion;
  }

  @JsonIgnore
  public void setId(String paramString)
  {
    this.id = paramString;
  }

  @JsonIgnore
  public void setPageSize(int paramInt)
  {
    this.pagesize = paramInt;
  }

  @JsonIgnore
  public void setRVersion(long paramLong)
  {
    this.rversion = paramLong;
  }
}