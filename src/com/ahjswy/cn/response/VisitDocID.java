package com.ahjswy.cn.response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class VisitDocID
{

  @JsonProperty("indocid")
  private long indocid;

  @JsonProperty("outdocid")
  private long outdocid;

  @JsonProperty("outexchangedocid")
  private long outexchangedocid;

  public VisitDocID()
  {
    this.outdocid = 0L;
    this.indocid = 0L;
    this.outexchangedocid = 0L;
  }

  public VisitDocID(long paramLong1, long paramLong2, long paramLong3)
  {
    this.outdocid = paramLong1;
    this.indocid = paramLong2;
    this.outexchangedocid = paramLong3;
  }

  @JsonIgnore
  public long getInDocId()
  {
    return this.indocid;
  }

  @JsonIgnore
  public long getOutDocId()
  {
    return this.outdocid;
  }

  @JsonIgnore
  public long getOutExchangeDocId()
  {
    return this.outexchangedocid;
  }

  @JsonIgnore
  public void setInDocId(long paramLong)
  {
    this.indocid = paramLong;
  }

  @JsonIgnore
  public void setOutDocId(long paramLong)
  {
    this.outdocid = paramLong;
  }

  @JsonIgnore
  public void setOutExchangeDocId(long paramLong)
  {
    this.outexchangedocid = paramLong;
  }
}