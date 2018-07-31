package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSynQueryCustomerPages
{

  @JsonProperty("pagesize")
  private int pagesize;

  public ReqSynQueryCustomerPages()
  {
  }

  public ReqSynQueryCustomerPages(int paramInt)
  {
    this.pagesize = paramInt;
  }

  @JsonIgnore
  public int getPageSize()
  {
    return this.pagesize;
  }

  @JsonIgnore
  public void setPageSize(int paramInt)
  {
    this.pagesize = paramInt;
  }
}