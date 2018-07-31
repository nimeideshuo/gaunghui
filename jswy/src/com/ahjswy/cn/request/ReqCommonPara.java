package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqCommonPara
{

  @JsonProperty("boolValue")
  private boolean boolValue;

  @JsonProperty("doubleValue")
  private double doubleValue;

  @JsonProperty("floatValue")
  private float floatValue;

  @JsonProperty("intValue")
  private int intValue;

  @JsonProperty("longValue")
  private long longValue;

  @JsonProperty("stringValue")
  private String stringValue;

  public ReqCommonPara()
  {
  }

  public ReqCommonPara(int paramInt, boolean paramBoolean, long paramLong, float paramFloat, double paramDouble, String paramString)
  {
    this.intValue = paramInt;
    this.boolValue = paramBoolean;
    this.longValue = paramLong;
    this.floatValue = paramFloat;
    this.doubleValue = paramDouble;
    this.stringValue = paramString;
  }

  @JsonIgnore
  public boolean getBoolValue()
  {
    return this.boolValue;
  }

  public double getDoubleValue()
  {
    return this.doubleValue;
  }

  @JsonIgnore
  public float getFloatValue()
  {
    return this.floatValue;
  }

  @JsonIgnore
  public int getIntValue()
  {
    return this.intValue;
  }

  @JsonIgnore
  public long getLongValue()
  {
    return this.longValue;
  }

  @JsonIgnore
  public String getStringValue()
  {
    return this.stringValue;
  }

  @JsonIgnore
  public void setBoolValue(boolean paramBoolean)
  {
    this.boolValue = paramBoolean;
  }

  @JsonIgnore
  public void setDoubleValue(double paramDouble)
  {
    this.doubleValue = paramDouble;
  }

  @JsonIgnore
  public void setFloatValue(float paramFloat)
  {
    this.floatValue = paramFloat;
  }

  @JsonIgnore
  public void setIntValue(int paramInt)
  {
    this.intValue = paramInt;
  }

  public void setLongValue(long paramLong)
  {
    this.longValue = paramLong;
  }

  @JsonIgnore
  public void setStringValue(String paramString)
  {
    this.stringValue = paramString;
  }
}