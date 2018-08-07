package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqVstUpload {

	@JsonProperty("issubmit")
	private boolean issubmit;

	@JsonProperty("jobs")
	private String jobs;

	@JsonProperty("visit")
	private String visit;

	public ReqVstUpload() {
	}

	public ReqVstUpload(String paramString1, String paramString2, boolean paramBoolean) {
		this.visit = paramString1;
		this.jobs = paramString2;
		this.issubmit = paramBoolean;
	}

	@JsonIgnore
	public boolean getIsSubmit() {
		return this.issubmit;
	}

	@JsonIgnore
	public String getJobs() {
		return this.jobs;
	}

	@JsonIgnore
	public String getVisit() {
		return this.visit;
	}

	@JsonIgnore
	public void setIsSubmit(boolean paramBoolean) {
		this.issubmit = paramBoolean;
	}

	@JsonIgnore
	public void setJobs(String paramString) {
		this.jobs = paramString;
	}

	@JsonIgnore
	public void setVisit(String paramString) {
		this.visit = paramString;
	}
}