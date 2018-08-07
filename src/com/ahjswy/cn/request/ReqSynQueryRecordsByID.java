package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSynQueryRecordsByID
{

  @JsonProperty("id")
  private String id;

  @JsonProperty("pageindex")
  private int pageindex;

  @JsonProperty("pagesize")
  private int pagesize;

  @JsonProperty("rversion")
  private long rversion;

  public ReqSynQueryRecordsByID()
  {
  }

  public ReqSynQueryRecordsByID(int paramInt1, int paramInt2, String paramString, long paramLong)
  {
    this.pageindex = paramInt1;
    this.pagesize = paramInt2;
    this.id = paramString;
    this.rversion = paramLong;
  }

  @JsonIgnore
  public String getId()
  {
    return this.id;
  }

  @JsonIgnore
  public int getPageIndex()
  {
    return this.pageindex;
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
  public void setId(String id)
  {
    this.id = id;
  }

  @JsonIgnore
  public void setPageIndex(int pageindex)
  {
    this.pageindex = pageindex;
  }

  @JsonIgnore
  public void setPageSize(int pagesize)
  {
    this.pagesize = pagesize;
  }

  @JsonIgnore
  public void setRVersion(long rversion)
  {
    this.rversion = rversion;
  }
}