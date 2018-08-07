package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqUsrCheckAuthority {

	@JsonProperty("authority")
	private String authority;

	@JsonProperty("userid")
	private String userid;

	public ReqUsrCheckAuthority() {
	}

	public ReqUsrCheckAuthority(String userid, String authority) {
		this.userid = userid;
		this.authority = authority;
	}

	@JsonIgnore
	public String getAuthority() {
		return this.authority;
	}

	@JsonIgnore
	public String getUserid() {
		return this.userid;
	}

	@JsonIgnore
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@JsonIgnore
	public void setUserid(String userid) {
		this.userid = userid;
	}
}