package com.ahjswy.cn.bean.bmob;

import cn.bmob.v3.BmobObject;

public class ExceptionLog extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String userid;
	public String username;
	public String deviceid;
	public String versionname;
	public String model;
	public String message;
	public String log;
	public String datetime;
	public String accountset;
	public String data;
	public transient int id;
	public transient boolean isupdata;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAccountset() {
		return accountset;
	}

	public void setAccountset(String accountset) {
		this.accountset = accountset;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

}
