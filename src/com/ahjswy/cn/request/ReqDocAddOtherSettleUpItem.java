package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ahjswy.cn.utils.Utils;

public class ReqDocAddOtherSettleUpItem
{

  @JsonProperty("accountid")
  private String accountid;

  @JsonProperty("amount")
  private Double amount;

  public ReqDocAddOtherSettleUpItem()
  {
  }

  public ReqDocAddOtherSettleUpItem(String paramString, Double paramDouble)
  {
    this.accountid = paramString;
    this.amount = Double.valueOf(Utils.normalizeDouble(paramDouble.doubleValue()));
  }

  @JsonIgnore
  public String getAccountId()
  {
    return this.accountid;
  }

  @JsonIgnore
  public Double getAmount()
  {
    return this.amount;
  }

  @JsonIgnore
  public void setAccountId(String paramString)
  {
    this.accountid = paramString;
  }

  @JsonIgnore
  public void setAmount(Double paramDouble)
  {
    this.amount = Double.valueOf(Utils.normalizeDouble(paramDouble.doubleValue()));
  }
}