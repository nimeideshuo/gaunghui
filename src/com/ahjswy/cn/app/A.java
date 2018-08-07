package com.ahjswy.cn.app;

import com.ahjswy.cn.utils.JSONUtil;

public final class A {
	public static final String ERROR = "error";
	public static final String FAIL = "fail";
	public static final String FORBID = "forbid";
	public static final String LOGIN_OUTDATED = "login";
	public static final String NET_ERROR = "无网络连接";
	public static final String NO_DOG = "nodog";
	public static final String NULL = "null";
	public static final String PERMIT = "permit";
	public static final String REGISTER = "register";
	public static final String SUCCESS = "success";

	public static boolean isFail(String paramString) {
		return ((SUCCESS.equals(paramString)) || (PERMIT.equals(paramString)) || (JSONUtil.isJson(paramString)));
	}
}