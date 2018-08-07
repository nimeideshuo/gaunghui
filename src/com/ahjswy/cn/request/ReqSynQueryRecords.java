package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqSynQueryRecords {

	@JsonProperty("pageindex")
	private int pageindex;

	@JsonProperty("pagesize")
	private int pagesize;

	@JsonProperty("rversion")
	private long rversion;

	public ReqSynQueryRecords() {
	}

	public ReqSynQueryRecords(int pageindex, int pagesize, long rversion) {
		this.pageindex = pageindex;
		this.pagesize = pagesize;
		this.rversion = rversion;
	}

	@JsonIgnore
	public int getPageIndex() {
		return this.pageindex;
	}

	@JsonIgnore
	public int getPageSize() {
		return this.pagesize;
	}

	@JsonIgnore
	public long getRVersion() {
		return this.rversion;
	}

	@JsonIgnore
	public void setPageIndex(int pageindex) {
		this.pageindex = pageindex;
	}

	@JsonIgnore
	public void setPageSize(int pagesize) {
		this.pagesize = pagesize;
	}

	@JsonIgnore
	public void setRVersion(long rversion) {
		this.rversion = rversion;
	}
}