package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class AccountSetEntity
{

  @JsonProperty("database")
  private String database;

  @JsonProperty("id")
  private int id;

  @JsonProperty("name")
  private String name;

  public AccountSetEntity()
  {
  }

  public AccountSetEntity(int paramInt, String paramString1, String paramString2)
  {
    this.id = paramInt;
    this.name = paramString1;
    this.database = paramString2;
  }

  @JsonIgnore
  public String getDatabase()
  {
    return this.database;
  }

  @JsonIgnore
  public int getId()
  {
    return this.id;
  }

  @JsonIgnore
  public String getName()
  {
    return this.name;
  }

  @JsonIgnore
  public void setDatabase(String paramString)
  {
    this.database = paramString;
  }

  @JsonIgnore
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  @JsonIgnore
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}