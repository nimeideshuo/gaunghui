package com.ahjswy.cn.utils;

import com.ahjswy.cn.app.AccountPreference;

import android.content.Context;

public class Service {
	Context contexts;

	public Service(Context context) {
		this.contexts = context;
	}

	/*
	 * 进行接口地址 拼接
	 */
	public static String getServiceAddress(String paramString1, String paramString2) {
		return paramString1 + "/" + paramString2;
	}

	/*
	 * get 获取 IP 地址
	 */
	public static String getIpUrl(String serviceIP) {
		// http://192.168.1.181:9682/system/checkregister 44519
		// return "http://" + serviceIP + ":44519/";
		// 因为 net 版本 修改 端口变动 现在去掉 默认端口 用户自己设置
		return "http://" + serviceIP + "/";

	}

	private static AccountPreference ap = new AccountPreference();

	// /**
	// * 拼接端口
	// *
	// * @param serviceIP
	// * @return
	// */
	// public static String getCGUrl(String port) {
	// return "http://" + ap.getServerIp() + ":" + port + "/";
	// }

}
