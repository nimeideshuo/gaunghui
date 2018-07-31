package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class TerminalEntity
{

  @JsonProperty("Identifier")
  private String Identifier;

  @JsonProperty("IsPC")
  private boolean IsPC;

  @JsonProperty("LoginName")
  private String LoginName;

  @JsonProperty("Mac")
  private String Mac;

  @JsonProperty("Model")
  private String Model;

  @JsonProperty("Owner")
  private String Owner;

  @JsonProperty("RegistrationCode")
  private String RegistrationCode;

  @JsonProperty("VersionKey")
  private String VersionKey;

  public TerminalEntity()
  {
  }

  public TerminalEntity(String paramString1, String paramString2)
  {
    this.Identifier = paramString1;
    this.IsPC = false;
    this.VersionKey = paramString2;
  }

  public TerminalEntity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean, String paramString7)
  {
    this.RegistrationCode = paramString1;
    this.Identifier = paramString2;
    this.Model = paramString3;
    this.Mac = paramString4;
    this.Owner = paramString5;
    this.LoginName = paramString6;
    this.IsPC = paramBoolean;
    this.VersionKey = paramString7;
  }

  @JsonIgnore
  public String getIdentifier()
  {
    return this.Identifier;
  }

  @JsonIgnore
  public boolean getIsPC()
  {
    return this.IsPC;
  }

  @JsonIgnore
  public String getLoginName()
  {
    return this.LoginName;
  }

  public String getMac()
  {
    return this.Mac;
  }

  @JsonIgnore
  public String getModel()
  {
    return this.Model;
  }

  @JsonIgnore
  public String getOwner()
  {
    return this.Owner;
  }

  @JsonIgnore
  public String getRegistrationCode()
  {
    return this.RegistrationCode;
  }

  @JsonIgnore
  public String getVersionKey()
  {
    return this.VersionKey;
  }

  @JsonIgnore
  public void setIdentifier(String paramString)
  {
    this.Identifier = paramString;
  }

  @JsonIgnore
  public void setIsPC(boolean paramBoolean)
  {
    this.IsPC = paramBoolean;
  }

  @JsonIgnore
  public void setLoginName(String paramString)
  {
    this.LoginName = paramString;
  }

  @JsonIgnore
  public void setMac(String paramString)
  {
    this.Mac = paramString;
  }

  @JsonIgnore
  public void setModel(String paramString)
  {
    this.Model = paramString;
  }

  @JsonIgnore
  public void setOwner(String paramString)
  {
    this.Owner = paramString;
  }

  @JsonIgnore
  public void setRegistrationCode(String paramString)
  {
    this.RegistrationCode = paramString;
  }

  @JsonIgnore
  public void setVersionKey(String paramString)
  {
    this.VersionKey = paramString;
  }
}