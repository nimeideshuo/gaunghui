package com.ahjswy.cn.utils;

import com.ahjswy.cn.app.AccountPreference;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
	public static boolean isConnected(Context context) {
		return (context != null) && (isWifiConnected(context)
				|| (isMobileConnected(context) && (!"0".equals(new AccountPreference().getValue("net_setting", "0")))));
	}

	/**
	 * 判断手机 网络是否打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();// 获取网络的连接情况
		if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetConnect(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
			NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();// 获取网络的连接情况
			if (activeNetInfo != null) {
				return activeNetInfo.isAvailable();
			}

		}
		return false;
	}

	/**
	 * 判断 wifi 是否打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();// 获取网络的连接情况
		if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		} else {
			return false;
		}
	}
}
