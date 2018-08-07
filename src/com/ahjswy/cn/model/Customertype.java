package com.ahjswy.cn.model;

import java.io.Serializable;

public class Customertype
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id;
  private String name;
  private String pricesystemid;

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPricesystemid()
  {
    return this.pricesystemid;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPricesystemid(String paramString)
  {
    this.pricesystemid = paramString;
  }
}