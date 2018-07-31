package com.ahjswy.cn.request;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqStrSearchDoc
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @JsonProperty("builderID")
  private String builderID;

  @JsonIgnore
  private String builderName;

  @JsonProperty("customerID")
  private String customerID;

  @JsonIgnore
  private String customerName;

  @JsonProperty("dateBeginTime")
  private String dateBeginTime;

  @JsonProperty("dateEndTime")
  private String dateEndTime;

  @JsonProperty("departmentID")
  private String departmentID;

  @JsonIgnore
  private String departmentName;

  @JsonProperty("doctype")
  private String doctype;

  @JsonIgnore
  private String doctypeName;

  @JsonProperty("inwarehouseID")
  private String inwarehouseID;

  @JsonIgnore
  private String inwarehouseName;

  @JsonProperty("onlyShowNoSettleUp")
  private boolean onlyShowNoSettleUp;

  @JsonProperty("outwarehouseID")
  private String outwarehouseID;

  @JsonIgnore
  private String outwarehouseName;

  @JsonProperty("pageindex")
  private int pageindex;

  @JsonProperty("pagesize")
  private int pagesize;

  @JsonProperty("remarkSummary")
  private String remarkSummary;

  @JsonProperty("showID")
  private String showID;

  @JsonProperty("warehouseID")
  private String warehouseID;

  @JsonIgnore
  private String warehouseName;

  @JsonIgnore
  public ReqStrSearchDoc()
  {
  }

  @JsonIgnore
  public ReqStrSearchDoc(ReqStrSearchDoc paramReqStrSearchDoc)
  {
    this.doctype = paramReqStrSearchDoc.getDoctype();
    this.departmentID = paramReqStrSearchDoc.getDepartmentID();
    this.customerID = paramReqStrSearchDoc.getCustomerID();
    this.warehouseID = paramReqStrSearchDoc.getWarehouseID();
    this.builderID = paramReqStrSearchDoc.getBuilderID();
    this.remarkSummary = paramReqStrSearchDoc.getRemarkSummary();
    this.showID = paramReqStrSearchDoc.getShowID();
    this.doctypeName = paramReqStrSearchDoc.getDoctypeName();
    this.departmentName = paramReqStrSearchDoc.getDepartmentName();
    this.customerName = paramReqStrSearchDoc.getCustomerName();
    this.warehouseName = paramReqStrSearchDoc.getWarehouseName();
    this.builderName = paramReqStrSearchDoc.getBuilderName();
    this.dateBeginTime = paramReqStrSearchDoc.getDateBeginTime();
    this.dateEndTime = paramReqStrSearchDoc.getDateEndTime();
    this.onlyShowNoSettleUp = paramReqStrSearchDoc.isOnlyShowNoSettleUp();
    this.inwarehouseID = paramReqStrSearchDoc.getInWarehouseID();
    this.outwarehouseID = paramReqStrSearchDoc.getOutWarehouseID();
    this.inwarehouseName = paramReqStrSearchDoc.getInWarehouseName();
    this.outwarehouseName = paramReqStrSearchDoc.getOutWarehouseName();
    this.pagesize = paramReqStrSearchDoc.getPagesize();
    this.pageindex = 1;
  }

  @JsonIgnore
  public String getBuilderID()
  {
    return this.builderID;
  }

  @JsonIgnore
  public String getBuilderName()
  {
    return this.builderName;
  }

  @JsonIgnore
  public String getCustomerID()
  {
    return this.customerID;
  }

  @JsonIgnore
  public String getCustomerName()
  {
    return this.customerName;
  }

  @JsonIgnore
  public String getDateBeginTime()
  {
    return this.dateBeginTime;
  }

  @JsonIgnore
  public String getDateEndTime()
  {
    return this.dateEndTime;
  }

  @JsonIgnore
  public String getDepartmentID()
  {
    return this.departmentID;
  }

  @JsonIgnore
  public String getDepartmentName()
  {
    return this.departmentName;
  }

  @JsonIgnore
  public String getDoctype()
  {
    return this.doctype;
  }

  @JsonIgnore
  public String getDoctypeName()
  {
    return this.doctypeName;
  }

  @JsonIgnore
  public String getInWarehouseID()
  {
    return this.inwarehouseID;
  }

  @JsonIgnore
  public String getInWarehouseName()
  {
    return this.inwarehouseName;
  }

  @JsonIgnore
  public String getOutWarehouseID()
  {
    return this.outwarehouseID;
  }

  @JsonIgnore
  public String getOutWarehouseName()
  {
    return this.outwarehouseName;
  }

  @JsonIgnore
  public int getPageindex()
  {
    return this.pageindex;
  }

  @JsonIgnore
  public int getPagesize()
  {
    return this.pagesize;
  }

  @JsonIgnore
  public String getRemarkSummary()
  {
    return this.remarkSummary;
  }

  @JsonIgnore
  public String getShowID()
  {
    return this.showID;
  }

  @JsonIgnore
  public String getWarehouseID()
  {
    return this.warehouseID;
  }

  @JsonIgnore
  public String getWarehouseName()
  {
    return this.warehouseName;
  }

  @JsonIgnore
  public boolean isOnlyShowNoSettleUp()
  {
    return this.onlyShowNoSettleUp;
  }

  @JsonIgnore
  public void setBuilderID(String paramString)
  {
    this.builderID = paramString;
  }

  @JsonIgnore
  public void setBuilderName(String paramString)
  {
    this.builderName = paramString;
  }

  @JsonIgnore
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }

  @JsonIgnore
  public void setCustomerName(String paramString)
  {
    this.customerName = paramString;
  }

  @JsonIgnore
  public void setDateBeginTime(String paramString)
  {
    this.dateBeginTime = paramString;
  }

  @JsonIgnore
  public void setDateEndTime(String paramString)
  {
    this.dateEndTime = paramString;
  }

  @JsonIgnore
  public void setDepartmentID(String paramString)
  {
    this.departmentID = paramString;
  }

  @JsonIgnore
  public void setDepartmentName(String paramString)
  {
    this.departmentName = paramString;
  }

  @JsonIgnore
  public void setDoctype(String paramString)
  {
    this.doctype = paramString;
  }

  @JsonIgnore
  public void setDoctypeName(String paramString)
  {
    this.doctypeName = paramString;
  }

  @JsonIgnore
  public void setInWarehouseID(String paramString)
  {
    this.inwarehouseID = paramString;
  }

  @JsonIgnore
  public void setInWarehouseName(String paramString)
  {
    this.inwarehouseName = paramString;
  }

  @JsonIgnore
  public void setOnlyShowNoSettleUp(boolean paramBoolean)
  {
    this.onlyShowNoSettleUp = paramBoolean;
  }

  @JsonIgnore
  public void setOutWarehouseID(String paramString)
  {
    this.outwarehouseID = paramString;
  }

  @JsonIgnore
  public void setOutWarehouseName(String paramString)
  {
    this.outwarehouseName = paramString;
  }

  @JsonIgnore
  public void setPageindex(int paramInt)
  {
    this.pageindex = paramInt;
  }

  @JsonIgnore
  public void setPagesize(int paramInt)
  {
    this.pagesize = paramInt;
  }

  @JsonIgnore
  public void setRemarkSummary(String paramString)
  {
    this.remarkSummary = paramString;
  }

  @JsonIgnore
  public void setShowID(String paramString)
  {
    this.showID = paramString;
  }

  @JsonIgnore
  public void setWarehouseID(String paramString)
  {
    this.warehouseID = paramString;
  }

  @JsonIgnore
  public void setWarehouseName(String paramString)
  {
    this.warehouseName = paramString;
  }
}