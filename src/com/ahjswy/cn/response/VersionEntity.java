package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class VersionEntity
{

  @JsonProperty("code")
  private String code;

  @JsonProperty("description")
  private String description;

  @JsonProperty("file")
  private String file;

  @JsonProperty("key")
  private String key;

  @JsonProperty("name")
  private String name;

  public VersionEntity()
  {
  }

  public VersionEntity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.key = paramString1;
    this.name = paramString2;
    this.code = paramString3;
    this.file = paramString4;
    this.description = paramString5;
  }

  @JsonIgnore
  public String getCode()
  {
    return this.code;
  }

  @JsonIgnore
  public String getDescription()
  {
    return this.description;
  }

  @JsonIgnore
  public String getFile()
  {
    return this.file;
  }

  @JsonIgnore
  public String getKey()
  {
    return this.key;
  }

  @JsonIgnore
  public String getName()
  {
    return this.name;
  }

  public void setCode(String paramString)
  {
    this.code = paramString;
  }

  @JsonIgnore
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  @JsonIgnore
  public void setFile(String paramString)
  {
    this.file = paramString;
  }

  @JsonIgnore
  public void setKey(String paramString)
  {
    this.key = paramString;
  }

  @JsonIgnore
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}