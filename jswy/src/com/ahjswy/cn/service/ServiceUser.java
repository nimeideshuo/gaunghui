package com.ahjswy.cn.service;

import java.util.LinkedHashMap;

import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.User;
import com.ahjswy.cn.request.ReqUsrCheckAuthority;
import com.ahjswy.cn.request.ReqUsrUserLocation;
import com.ahjswy.cn.request.ReqUsrUserLogin;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.Service;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.utils.Utils_help;

public class ServiceUser {
	private String baseAddress = "user";
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	public String usr_CheckAuthority(String paramString) {
		String url = Service.getServiceAddress(this.baseAddress, "checkauthority");
		ReqUsrCheckAuthority localReqUsrCheckAuthority = new ReqUsrCheckAuthority();
		localReqUsrCheckAuthority.setUserid(SystemState.getUser().getId());
		localReqUsrCheckAuthority.setAuthority(paramString);
		map.put("parameter", JSONUtil.toJSONString(localReqUsrCheckAuthority));
		return new Utils_help().getServiceInfor(url, map);
	}

	// 获取权限
	public String usr_GetAuthoritys(String authority) {
		String url = Utils.getServiceAddress(this.baseAddress, "getauthoritys");
		ReqUsrCheckAuthority localReqUsrCheckAuthority = new ReqUsrCheckAuthority();
		localReqUsrCheckAuthority.setUserid(SystemState.getUser().getId());
		localReqUsrCheckAuthority.setAuthority(authority);
		map.put("parameter", JSONUtil.toJSONString(localReqUsrCheckAuthority));
		return new Utils_help().getServiceInfor(url, map);
	}

	public String usr_QueryUser() {
		String url = Utils.getServiceAddress(this.baseAddress, "queryuser");
		return new Utils_help().getServiceInfor(url, map);
	}

	public String usr_UploadUserLocation(double longitude, double latitude, String address) {
		String url = Utils.getServiceAddress(this.baseAddress, "uploaduserlocation");
		User localUser = (User) SystemState.getObject("cu_user", User.class);
		ReqUsrUserLocation location = new ReqUsrUserLocation();
		location.setId(localUser.getId());
		location.setName(localUser.getName());
		location.setLongitude(longitude);
		location.setLatitude(latitude);
		location.setAddress(address);
		map.put("parameter", JSONUtil.toJSONString(location));
		return new Utils_help().getServiceInfor(url, map);
	}

	public String usr_UserLogin(String userid, String password) {
		String url = Utils.getServiceAddress(this.baseAddress, "userlogin");
		ReqUsrUserLogin userLogin = new ReqUsrUserLogin(userid, password, "mmendianban");
		map.put("parameter", JSONUtil.toJSONString(userLogin));
		return new Utils_help().getServiceInfor(url, map);
	}
}