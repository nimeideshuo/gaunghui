package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSupQueryDepartment
{

  @JsonProperty("isonlystore")
  private boolean isonlystore;

  @JsonProperty("userid")
  private String userid;

  public ReqSupQueryDepartment()
  {
  }

  public ReqSupQueryDepartment(String paramString, boolean paramBoolean)
  {
    this.userid = paramString;
    this.isonlystore = paramBoolean;
  }

  @JsonIgnore
  public boolean getIsOnlyStore()
  {
    return this.isonlystore;
  }

  @JsonIgnore
  public String getUserid()
  {
    return this.userid;
  }

  @JsonIgnore
  public void setIsOnlyStore(boolean paramBoolean)
  {
    this.isonlystore = paramBoolean;
  }

  @JsonIgnore
  public void setUserid(String paramString)
  {
    this.userid = paramString;
  }
}