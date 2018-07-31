package com.ahjswy.cn.response;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class RespStrDocThinEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @JsonProperty("buildername")
  private String buildername;

  @JsonProperty("buildtime")
  private String buildtime;

  @JsonProperty("customername")
  private String customername;

  @JsonProperty("departmentname")
  private String departmentname;

  @JsonProperty("docid")
  private long docid;

  @JsonProperty("doctypeid")
  private String doctypeid;

  @JsonProperty("doctypename")
  private String doctypename;

  @JsonProperty("isavailable")
  private boolean isavailable;

  @JsonProperty("isposted")
  private boolean isposted;

  @JsonProperty("issettleup")
  private boolean issettleup;

  @JsonProperty("makername")
  private String makername;

  @JsonProperty("maketime")
  private String maketime;

  @JsonProperty("preference")
  private double preference;

  @JsonProperty("remark")
  private String remark;

  @JsonProperty("rversion")
  private long rversion;

  @JsonProperty("showid")
  private String showid;

  @JsonProperty("summary")
  private String summary;

  @JsonProperty("warehousename")
  private String warehousename;

  @JsonIgnore
  public String getBuildername()
  {
    return this.buildername;
  }

  @JsonIgnore
  public String getBuildtime()
  {
    return this.buildtime;
  }

  @JsonIgnore
  public String getCustomername()
  {
    return this.customername;
  }

  @JsonIgnore
  public String getDepartmentname()
  {
    return this.departmentname;
  }

  @JsonIgnore
  public long getDocid()
  {
    return this.docid;
  }

  @JsonIgnore
  public String getDoctypeid()
  {
    return this.doctypeid;
  }

  @JsonIgnore
  public String getDoctypename()
  {
    return this.doctypename;
  }

  @JsonIgnore
  public boolean getIsavailable()
  {
    return this.isavailable;
  }

  @JsonIgnore
  public boolean getIsposted()
  {
    return this.isposted;
  }

  @JsonIgnore
  public boolean getIssettleup()
  {
    return this.issettleup;
  }

  @JsonIgnore
  public String getMakername()
  {
    return this.makername;
  }

  @JsonIgnore
  public String getMaketime()
  {
    return this.maketime;
  }

  @JsonIgnore
  public double getPreference()
  {
    return this.preference;
  }

  @JsonIgnore
  public long getRVersion()
  {
    return this.rversion;
  }

  @JsonIgnore
  public String getRemark()
  {
    return this.remark;
  }

  @JsonIgnore
  public String getShowid()
  {
    return this.showid;
  }

  @JsonIgnore
  public String getSummary()
  {
    return this.summary;
  }

  @JsonIgnore
  public String getWarehousename()
  {
    return this.warehousename;
  }

  @JsonIgnore
  public void setBuildername(String paramString)
  {
    this.buildername = paramString;
  }

  @JsonIgnore
  public void setBuildtime(String paramString)
  {
    this.buildtime = paramString;
  }

  @JsonIgnore
  public void setCustomername(String paramString)
  {
    this.customername = paramString;
  }

  @JsonIgnore
  public void setDepartmentname(String paramString)
  {
    this.departmentname = paramString;
  }

  @JsonIgnore
  public void setDocid(long paramLong)
  {
    this.docid = paramLong;
  }

  @JsonIgnore
  public void setDoctypeid(String paramString)
  {
    this.doctypeid = paramString;
  }

  @JsonIgnore
  public void setDoctypename(String paramString)
  {
    this.doctypename = paramString;
  }

  @JsonIgnore
  public void setIsavailable(boolean paramBoolean)
  {
    this.isavailable = paramBoolean;
  }

  @JsonIgnore
  public void setIsposted(boolean paramBoolean)
  {
    this.isposted = paramBoolean;
  }

  @JsonIgnore
  public void setIssettleup(boolean paramBoolean)
  {
    this.issettleup = paramBoolean;
  }

  @JsonIgnore
  public void setMakername(String paramString)
  {
    this.makername = paramString;
  }

  @JsonIgnore
  public void setMaketime(String paramString)
  {
    this.maketime = paramString;
  }

  @JsonIgnore
  public void setPreference(double paramDouble)
  {
    this.preference = paramDouble;
  }

  @JsonIgnore
  public void setRVersion(long paramLong)
  {
    this.rversion = paramLong;
  }

  @JsonIgnore
  public void setRemark(String paramString)
  {
    this.remark = paramString;
  }

  @JsonIgnore
  public void setShowid(String paramString)
  {
    this.showid = paramString;
  }

  @JsonIgnore
  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }

  @JsonIgnore
  public void setWarehousename(String paramString)
  {
    this.warehousename = paramString;
  }
}