package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ahjswy.cn.utils.Utils;

public class ReqDocUpdatePayType
{

  @JsonProperty("amount")
  private double amount;

  @JsonProperty("paytypeid")
  private String paytypeid;

  public ReqDocUpdatePayType()
  {
  }

  public ReqDocUpdatePayType(String paramString, double paramDouble)
  {
    this.paytypeid = paramString;
    this.amount = Utils.normalizeDouble(paramDouble);
  }

  @JsonIgnore
  public double getAmount()
  {
    return this.amount;
  }

  @JsonIgnore
  public String getPayTypeId()
  {
    return this.paytypeid;
  }

  @JsonIgnore
  public void setAmount(double paramDouble)
  {
    this.amount = Utils.normalizeDouble(paramDouble);
  }

  @JsonIgnore
  public void setPayTypeId(String paramString)
  {
    this.paytypeid = paramString;
  }
}