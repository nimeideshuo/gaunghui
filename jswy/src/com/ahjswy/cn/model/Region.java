package com.ahjswy.cn.model;

import java.io.Serializable;

public class Region
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id;
  private String name;
  private String pinyin;

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPinyin()
  {
    return this.pinyin;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPinyin(String paramString)
  {
    this.pinyin = paramString;
  }
}