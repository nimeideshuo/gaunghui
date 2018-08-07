package com.ahjswy.cn.bean.bmob;

import java.io.Serializable;
import java.util.Date;

import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.Utils;
import com.google.gson.annotations.Expose;

import cn.bmob.v3.BmobObject;

/**
 * 云 用户类
 * 
 * @author Administrator
 *
 */
public class bo_swy_user extends BmobObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String accountset;
	public String code;
	public String database;
	public String deviceid;
	public String memory;
	public String message;
	public String registerDate;
	public String model;

	public Integer sleep;
	public Integer state;// 0 默认 1, 警告，2 停止,3 退出
	public String userName;

	public String userid;
	public String versionname;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setSleep(Integer sleep) {
		this.sleep = sleep;
	}

	public Integer getSleep() {
		return sleep;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Number getState() {
		return state;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public String getVersionname() {
		return versionname;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	private bo_swy_user() {
	}

	public static bo_swy_user factory() {
		bo_swy_user swyUser = new bo_swy_user();
		swyUser.accountset = SystemState.getAccountSet().getDatabase();
		swyUser.deviceid = MyApplication.getInstance().getAndroidId();
		swyUser.userName = SystemState.getUser().getName();
		swyUser.code = MyApplication.getInstance().getUniqueCode();
		swyUser.versionname = MyApplication.getInstance().getVersionName();
		swyUser.userid = SystemState.getDBUser().userid;
		// 数据库
		swyUser.database = SystemState.getAccountSet().getDatabase();
		// 提交的时间
		swyUser.registerDate = Utils.getData();
		// 默认提交的消息
		swyUser.message = "";
		// 默认的状态
		swyUser.state = 0;
		// 默认使用的内存
		swyUser.memory = "1";
		swyUser.model = android.os.Build.MODEL;
		// swyUser.accountset = "accountset";
		// swyUser.deviceid = "deviceid";
		// swyUser.userName = "userName";
		// swyUser.code = "code";
		// swyUser.versionname = "versionname";
		// swyUser.userid = "userid";
		// swyUser.database = "database";

		return swyUser;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getAccountset() {
		return accountset;
	}

	public void setAccountset(String accountset) {
		this.accountset = accountset;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "bo_swy_user [accountset=" + accountset + ", code=" + code + ", database=" + database + ", deviceid="
				+ deviceid + ", memory=" + memory + ", message=" + message + ", registerDate=" + registerDate
				+ ", sleep=" + sleep + ", state=" + state + ", userName=" + userName + ", userid=" + userid
				+ ", versionname=" + versionname + "]";
	}

}
