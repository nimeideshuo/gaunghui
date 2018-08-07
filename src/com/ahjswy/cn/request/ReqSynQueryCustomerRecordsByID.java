package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSynQueryCustomerRecordsByID
{

  @JsonProperty("customers")
  private String customers;

  @JsonProperty("isupdate")
  private boolean isupdate;

  @JsonProperty("maxorderno")
  private int maxorderno;

  public ReqSynQueryCustomerRecordsByID()
  {
  }

  public ReqSynQueryCustomerRecordsByID(int paramInt, boolean paramBoolean, String paramString)
  {
    this.maxorderno = paramInt;
    this.isupdate = paramBoolean;
    this.customers = paramString;
  }

  @JsonIgnore
  public String getCustomers()
  {
    return this.customers;
  }

  @JsonIgnore
  public boolean getIsUpdate()
  {
    return this.isupdate;
  }

  @JsonIgnore
  public int getMaxOrderNo()
  {
    return this.maxorderno;
  }

  @JsonIgnore
  public void setCustomers(String paramString)
  {
    this.customers = paramString;
  }

  @JsonIgnore
  public void setIsUpdate(boolean paramBoolean)
  {
    this.isupdate = paramBoolean;
  }

  @JsonIgnore
  public void setMaxOrderNo(int paramInt)
  {
    this.maxorderno = paramInt;
  }
}