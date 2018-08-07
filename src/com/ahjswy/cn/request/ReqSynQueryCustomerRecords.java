package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSynQueryCustomerRecords
{

  @JsonProperty("pageindex")
  private int pageindex;

  @JsonProperty("pagesize")
  private int pagesize;

  public ReqSynQueryCustomerRecords()
  {
  }

  public ReqSynQueryCustomerRecords(int paramInt1, int paramInt2)
  {
    this.pageindex = paramInt1;
    this.pagesize = paramInt2;
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
  public void setPageIndex(int paramInt)
  {
    this.pageindex = paramInt;
  }

  @JsonIgnore
  public void setPageSize(int paramInt)
  {
    this.pagesize = paramInt;
  }
}