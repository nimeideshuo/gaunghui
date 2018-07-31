package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSynUpdateInfo
{

  @JsonProperty("pages")
  private long pages;

  @JsonProperty("tablename")
  private String tablename;

  public ReqSynUpdateInfo()
  {
  }

  public ReqSynUpdateInfo(String paramString, long paramLong)
  {
    this.tablename = paramString;
    this.pages = paramLong;
  }

  @JsonIgnore
  public long getPages()
  {
    return this.pages;
  }

  @JsonIgnore
  public String getTableName()
  {
    return this.tablename;
  }

  @JsonIgnore
  public void setPages(long paramLong)
  {
    this.pages = paramLong;
  }

  @JsonIgnore
  public void setTableName(String paramString)
  {
    this.tablename = paramString;
  }
}