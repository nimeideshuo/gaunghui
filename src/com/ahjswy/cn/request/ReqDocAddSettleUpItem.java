package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ahjswy.cn.utils.Utils;

public class ReqDocAddSettleUpItem
{

  @JsonProperty("docid")
  private long docid;

  @JsonProperty("docshowid")
  private String docshowid;

  @JsonProperty("doctime")
  private long doctime;

  @JsonProperty("doctype")
  private String doctype;

  @JsonProperty("doctypename")
  private String doctypename;

  @JsonProperty("leftamount")
  private double leftamount;

  @JsonProperty("receivableamount")
  private double receivableamount;

  @JsonProperty("thisamount")
  private double thisamount;

  public ReqDocAddSettleUpItem()
  {
  }

  public ReqDocAddSettleUpItem(long paramLong1, long paramLong2, String paramString1, String paramString2, String paramString3, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.doctime = paramLong1;
    this.docid = paramLong2;
    this.docshowid = paramString1;
    this.doctype = paramString2;
    this.doctypename = paramString3;
    this.receivableamount = Utils.normalizeDouble(paramDouble1);
    this.leftamount = Utils.normalizeDouble(paramDouble2);
    this.thisamount = Utils.normalizeDouble(paramDouble3);
  }

  @JsonIgnore
  public long getDocId()
  {
    return this.docid;
  }

  @JsonIgnore
  public String getDocShowId()
  {
    return this.docshowid;
  }

  @JsonIgnore
  public long getDocTime()
  {
    return this.doctime;
  }

  @JsonIgnore
  public String getDocType()
  {
    return this.doctype;
  }

  @JsonIgnore
  public String getDocTypeName()
  {
    return this.doctypename;
  }

  @JsonIgnore
  public double getLeftAmount()
  {
    return this.leftamount;
  }

  @JsonIgnore
  public double getReceivableAmount()
  {
    return this.receivableamount;
  }

  @JsonIgnore
  public double getThisAmount()
  {
    return this.thisamount;
  }

  @JsonIgnore
  public void setDocId(long paramLong)
  {
    this.docid = paramLong;
  }

  @JsonIgnore
  public void setDocShowId(String paramString)
  {
    this.docshowid = paramString;
  }

  @JsonIgnore
  public void setDocTime(long paramLong)
  {
    this.doctime = paramLong;
  }

  @JsonIgnore
  public void setDocType(String paramString)
  {
    this.doctype = paramString;
  }

  @JsonIgnore
  public void setDocTypeName(String paramString)
  {
    this.doctypename = paramString;
  }

  @JsonIgnore
  public void setLeftAmount(double paramDouble)
  {
    this.leftamount = Utils.normalizeDouble(paramDouble);
  }

  @JsonIgnore
  public void setReceivableAmount(double paramDouble)
  {
    this.receivableamount = Utils.normalizeDouble(paramDouble);
  }

  @JsonIgnore
  public void setThisAmount(double paramDouble)
  {
    this.thisamount = Utils.normalizeDouble(paramDouble);
  }
}