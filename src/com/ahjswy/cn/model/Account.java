package com.ahjswy.cn.model;

import java.io.Serializable;

public class Account
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String aid;
  private String aname;
  private String parentaccountid;

  public String getAid()
  {
    return this.aid;
  }

  public String getAname()
  {
    return this.aname;
  }

  public String getParentaccountid()
  {
    return this.parentaccountid;
  }

  public void setAid(String paramString)
  {
    this.aid = paramString;
  }

  public void setAname(String paramString)
  {
    this.aname = paramString;
  }

  public void setParentaccountid(String paramString)
  {
    this.parentaccountid = paramString;
  }
}