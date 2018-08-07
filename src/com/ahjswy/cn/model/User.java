package com.ahjswy.cn.model;

import java.io.Serializable;

public class User
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id;
  private boolean isaccountmanager;
  private String name;
  private String offlinepassword;
  private String password;

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getOfflinepassword()
  {
    return this.offlinepassword;
  }

  public String getPassword()
  {
    return this.password;
  }

  public boolean isIsaccountmanager()
  {
    return this.isaccountmanager;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setIsaccountmanager(boolean paramBoolean)
  {
    this.isaccountmanager = paramBoolean;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setOfflinepassword(String paramString)
  {
    this.offlinepassword = paramString;
  }

  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
}