package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class IDNameEntity
{

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  public IDNameEntity()
  {
  }

  public IDNameEntity(String paramString1, String paramString2)
  {
    this.id = paramString1;
    this.name = paramString2;
  }

  @JsonIgnore
  public String getId()
  {
    return this.id;
  }

  @JsonIgnore
  public String getName()
  {
    return this.name;
  }

  @JsonIgnore
  public void setId(String paramString)
  {
    this.id = paramString;
  }

  @JsonIgnore
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}