package com.ahjswy.cn.model;

import java.io.Serializable;

public class DefDocExchange
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected String builderid;
  protected String buildername;
  protected String buildtime;
  protected String customeraddress;
  protected String customerid;
  protected String customername;
  protected String deliverytime;
  protected String departmentid;
  protected String departmentname;
  protected String distributionid;
  protected long docid;
  protected String doctypeid;
  protected String doctypename;
  protected String financialcfid;
  protected double indocdiscountratio;
  protected String inwarehouseid;
  protected String inwarehousename;
  protected boolean isavailable;
  protected boolean isdistribution;
  protected boolean isposted;
  protected boolean issettleup;
  protected String makerid;
  protected String makername;
  protected String maketime;
  protected double outdocdiscountratio;
  protected String outwarehouseid;
  protected String outwarehousename;
  protected double preference;
  protected int printnum;
  protected String printtemplate;
  protected String remark;
  protected long rversion;
  protected String settletime;
  protected String showid;
  protected String summary;

  public String getBuilderid()
  {
    return this.builderid;
  }

  public String getBuildername()
  {
    return this.buildername;
  }

  public String getBuildtime()
  {
    return this.buildtime;
  }

  public String getCustomeraddress()
  {
    return this.customeraddress;
  }

  public String getCustomerid()
  {
    return this.customerid;
  }

  public String getCustomername()
  {
    return this.customername;
  }

  public String getDeliverytime()
  {
    return this.deliverytime;
  }

  public String getDepartmentid()
  {
    return this.departmentid;
  }

  public String getDepartmentname()
  {
    return this.departmentname;
  }

  public String getDistributionid()
  {
    return this.distributionid;
  }

  public long getDocid()
  {
    return this.docid;
  }

  public String getDoctypeid()
  {
    return this.doctypeid;
  }

  public String getDoctypename()
  {
    return this.doctypename;
  }

  public String getFinancialcfid()
  {
    return this.financialcfid;
  }

  public double getIndocdiscountratio()
  {
    return this.indocdiscountratio;
  }

  public String getInwarehouseid()
  {
    return this.inwarehouseid;
  }

  public String getInwarehousename()
  {
    return this.inwarehousename;
  }

  public String getMakerid()
  {
    return this.makerid;
  }

  public String getMakername()
  {
    return this.makername;
  }

  public String getMaketime()
  {
    return this.maketime;
  }

  public double getOutdocdiscountratio()
  {
    return this.outdocdiscountratio;
  }

  public String getOutwarehouseid()
  {
    return this.outwarehouseid;
  }

  public String getOutwarehousename()
  {
    return this.outwarehousename;
  }

  public double getPreference()
  {
    return this.preference;
  }

  public int getPrintnum()
  {
    return this.printnum;
  }

  public String getPrinttemplate()
  {
    return this.printtemplate;
  }

  public String getRemark()
  {
    return this.remark;
  }

  public long getRversion()
  {
    return this.rversion;
  }

  public String getSettletime()
  {
    return this.settletime;
  }

  public String getShowid()
  {
    return this.showid;
  }

  public String getSummary()
  {
    return this.summary;
  }

  public boolean isIsavailable()
  {
    return this.isavailable;
  }

  public boolean isIsdistribution()
  {
    return this.isdistribution;
  }

  public boolean isIsposted()
  {
    return this.isposted;
  }

  public boolean isIssettleup()
  {
    return this.issettleup;
  }

  public void setBuilderid(String paramString)
  {
    if (!paramString.isEmpty())
    {
      this.builderid = paramString;
      return;
    }
    this.builderid = null;
  }

  public void setBuildername(String paramString)
  {
    this.buildername = paramString;
  }

  public void setBuildtime(String paramString)
  {
    this.buildtime = paramString;
  }

  public void setCustomeraddress(String paramString)
  {
    this.customeraddress = paramString;
  }

  public void setCustomerid(String paramString)
  {
    if (!paramString.isEmpty())
    {
      this.customerid = paramString;
      return;
    }
    this.customerid = null;
  }

  public void setCustomername(String paramString)
  {
    this.customername = paramString;
  }

  public void setDeliverytime(String paramString)
  {
    this.deliverytime = paramString;
  }

  public void setDepartmentid(String paramString)
  {
    if (!paramString.isEmpty())
    {
      this.departmentid = paramString;
      return;
    }
    this.departmentid = null;
  }

  public void setDepartmentname(String paramString)
  {
    this.departmentname = paramString;
  }

  public void setDistributionid(String paramString)
  {
    this.distributionid = paramString;
  }

  public void setDocid(long paramLong)
  {
    this.docid = paramLong;
  }

  public void setDoctypeid(String paramString)
  {
    this.doctypeid = paramString;
  }

  public void setDoctypename(String paramString)
  {
    this.doctypename = paramString;
  }

  public void setFinancialcfid(String paramString)
  {
    this.financialcfid = paramString;
  }

  public void setIndocdiscountratio(double paramDouble)
  {
    this.indocdiscountratio = paramDouble;
  }

  public void setInwarehouseid(String paramString)
  {
    if (!paramString.isEmpty())
    {
      this.inwarehouseid = paramString;
      return;
    }
    this.inwarehouseid = null;
  }

  public void setInwarehousename(String paramString)
  {
    this.inwarehousename = paramString;
  }

  public void setIsavailable(boolean paramBoolean)
  {
    this.isavailable = paramBoolean;
  }

  public void setIsdistribution(boolean paramBoolean)
  {
    this.isdistribution = paramBoolean;
  }

  public void setIsposted(boolean paramBoolean)
  {
    this.isposted = paramBoolean;
  }

  public void setIssettleup(boolean paramBoolean)
  {
    this.issettleup = paramBoolean;
  }

  public void setMakerid(String paramString)
  {
    if (!paramString.isEmpty())
    {
      this.makerid = paramString;
      return;
    }
    this.makerid = null;
  }

  public void setMakername(String paramString)
  {
    this.makername = paramString;
  }

  public void setMaketime(String paramString)
  {
    this.maketime = paramString;
  }

  public void setOutdocdiscountratio(double paramDouble)
  {
    this.outdocdiscountratio = paramDouble;
  }

  public void setOutwarehouseid(String paramString)
  {
    if (!paramString.isEmpty())
    {
      this.outwarehouseid = paramString;
      return;
    }
    this.outwarehouseid = null;
  }

  public void setOutwarehousename(String paramString)
  {
    this.outwarehousename = paramString;
  }

  public void setPreference(double paramDouble)
  {
    this.preference = paramDouble;
  }

  public void setPrintnum(int paramInt)
  {
    this.printnum = paramInt;
  }

  public void setPrinttemplate(String paramString)
  {
    this.printtemplate = paramString;
  }

  public void setRemark(String paramString)
  {
    this.remark = paramString;
  }

  public void setRversion(long paramLong)
  {
    this.rversion = paramLong;
  }

  public void setSettletime(String paramString)
  {
    this.settletime = paramString;
  }

  public void setShowid(String paramString)
  {
    this.showid = paramString;
  }

  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }
}