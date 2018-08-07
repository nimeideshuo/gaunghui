package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class RespVistCustomerEntity
{

  @JsonProperty("localid")
  private long localid;

  @JsonProperty("serverid")
  private long serverid;

  public RespVistCustomerEntity()
  {
  }

  public RespVistCustomerEntity(long paramLong1, long paramLong2)
  {
    this.localid = paramLong1;
    this.serverid = paramLong2;
  }

  @JsonIgnore
  public long getLocalId()
  {
    return this.localid;
  }

  @JsonIgnore
  public long getServerId()
  {
    return this.serverid;
  }

  @JsonIgnore
  public void setLocalId(long paramLong)
  {
    this.localid = paramLong;
  }

  @JsonIgnore
  public void setServerId(long paramLong)
  {
    this.serverid = paramLong;
  }
}