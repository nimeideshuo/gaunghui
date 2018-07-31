package com.ahjswy.cn.model;

import java.io.Serializable;

public class DefDocPD implements Serializable {
	private static final long serialVersionUID = 1L;
	private String builderid;
	private String buildername;
	private String buildtime;
	private String departmentid;
	private String departmentname;
	private double discountratio;
  private long docid;
  private String doctypeid;
  private String doctypename;
  private long indocid;
  private String indocshowid;
  private boolean isavailable;
  private boolean isposted;
  private boolean ispostedindoc;
  private boolean ispostedoutdoc;
  private String makerid;
  private String makername;
  private String maketime;
  private long outdocid;
  private String outdocshowid;
  private int printnum;
  private String printtemplate;
  private String remark;
  private long rversion;
  private String showid;
  private String summary;
  private String warehouseid;
  private String warehousename;

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

  public String getDepartmentid()
  {
    return this.departmentid;
  }

  public String getDepartmentname()
  {
    return this.departmentname;
  }

  public double getDiscountratio()
  {
    return this.discountratio;
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

  public long getIndocid()
  {
    return this.indocid;
  }

  public String getIndocshowid()
  {
    return this.indocshowid;
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

  public long getOutdocid()
  {
    return this.outdocid;
  }

  public String getOutdocshowid()
  {
    return this.outdocshowid;
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

  public String getShowid()
  {
    return this.showid;
  }

  public String getSummary()
  {
    return this.summary;
  }

  public String getWarehouseid()
  {
    return this.warehouseid;
  }

  public String getWarehousename()
  {
    return this.warehousename;
  }

  public boolean isIsavailable()
  {
    return this.isavailable;
  }

  public boolean isIsposted()
  {
    return this.isposted;
  }

  public boolean isIspostedindoc()
  {
    return this.ispostedindoc;
  }

  public boolean isIspostedoutdoc()
  {
    return this.ispostedoutdoc;
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

  public void setDepartmentid(String paramString)
  {
    this.departmentid = paramString;
  }

  public void setDepartmentname(String paramString)
  {
    this.departmentname = paramString;
  }

  public void setDiscountratio(double paramDouble)
  {
    this.discountratio = paramDouble;
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

  public void setIndocid(long paramLong)
  {
    this.indocid = paramLong;
  }

  public void setIndocshowid(String paramString)
  {
    this.indocshowid = paramString;
  }

  public void setIsavailable(boolean paramBoolean)
  {
    this.isavailable = paramBoolean;
  }

  public void setIsposted(boolean paramBoolean)
  {
    this.isposted = paramBoolean;
  }

  public void setIspostedindoc(boolean paramBoolean)
  {
    this.ispostedindoc = paramBoolean;
  }

  public void setIspostedoutdoc(boolean paramBoolean)
  {
    this.ispostedoutdoc = paramBoolean;
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

  public void setOutdocid(long paramLong)
  {
    this.outdocid = paramLong;
  }

  public void setOutdocshowid(String paramString)
  {
    this.outdocshowid = paramString;
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

  public void setShowid(String paramString)
  {
    this.showid = paramString;
  }

  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }

  public void setWarehouseid(String paramString)
  {
    this.warehouseid = paramString;
  }

  public void setWarehousename(String paramString)
  {
    this.warehousename = paramString;
  }
}