package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqUsrUserLogin {

	@JsonProperty("key")
	private String key;

	@JsonProperty("password")
	private String password;

	@JsonProperty("userid")
	private String userid;

	public ReqUsrUserLogin() {
	}

	public ReqUsrUserLogin(String userid, String password, String key) {
		this.userid = userid;
		this.password = password;
		this.key = key;
	}

	@JsonIgnore
	public String getKey() {
		return this.key;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonIgnore
	public String getUserid() {
		return this.userid;
	}

	@JsonIgnore
	public void setKey(String key) {
		this.key = key;
	}

	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public void setUserid(String userid) {
		this.userid = userid;
	}
}