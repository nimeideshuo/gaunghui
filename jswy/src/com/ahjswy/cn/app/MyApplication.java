package com.ahjswy.cn.app;

import com.ahjswy.cn.bean.bmob.bo_swy_user;
import com.ahjswy.cn.crash.CrashHandler;
import com.ahjswy.cn.ui.Swy_splash;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.TextUtils;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
	private static MyApplication instance;
	private static ActivityManager activityManager;
	private AccountPreference ap;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		activityManager = ActivityManager.getScreenManager();
		CrashHandler.getInstance().init(this);
		Bmob.initialize(this, "11482f9fcef5806efdf3f4a3f908048d");
		ap = new AccountPreference();
	}

	public synchronized static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	public void exit() {
		activityManager.popAllActivityExceptOne(null);
		Process.killProcess(Process.myPid());
		System.exit(0);
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	public ActivityManager getActivityManager() {
		return activityManager;
	}

	public String getUniqueCode() {
		if ((TextUtils.isEmptyS(SystemCommons.UNIQUE_CODE))) {
			return SystemCommons.UNIQUE_CODE;
		}
		String str1 = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
		CTelephoneInfo localCTelephoneInfo = CTelephoneInfo.getInstance(this);
		localCTelephoneInfo.setCTelephoneInfo();
		String str2 = localCTelephoneInfo.getImeiSIM1();
		String str3 = localCTelephoneInfo.getImeiSIM2();
		if (!TextUtils.isEmptyS(str2)) {
			str2 = "";
		}
		if (!TextUtils.isEmptyS(str3)) {
			str3 = "";
		}
		if ((TextUtils.isEmptyS(str1 + str2 + str3))) {
			// 将所有字母 全部转换成大写 toUpperCase
			SystemCommons.UNIQUE_CODE = (str1 + str2 + str3).toUpperCase();
			return (str1 + str2 + str3).toUpperCase();
		}
		WifiInfo localWifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
		SystemCommons.UNIQUE_CODE = localWifiInfo.getMacAddress();
		return localWifiInfo.getMacAddress();
	}

	// Android Id
	public String getAndroidId() {
		return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	/**
	 * 判断可用内存是否危险
	 * 
	 * @return
	 */
	public boolean isDangerMemory() {
		bo_swy_user user = JSONUtil.readValue(ap.getValue("bo_swy_user", "0"), bo_swy_user.class);
		int maxMemory = user == null ? 1 : Integer.parseInt(user.getMemory());
		final int M = 1024 * 1024;
		final Runtime runtime = Runtime.getRuntime();
		Log.i("tag", "最大可用内存：" + runtime.maxMemory() / M + "M");
		Log.i("tag", "当前可用内存：" + runtime.totalMemory() / M + "M");
		Log.i("tag", "当前空闲内存：" + runtime.freeMemory() / M + "M");
		Log.i("tag", "当前已使用内存：" + (runtime.totalMemory() - runtime.freeMemory()) / M + "M");
		if ((runtime.maxMemory() / M) - (runtime.totalMemory() / M) < maxMemory) {
			StringBuffer sb = new StringBuffer();
			sb.append("最大可用内存：" + runtime.maxMemory() / M + "M").append("\n");
			sb.append("当前可用内存：" + runtime.totalMemory() / M + "M").append("\n");
			sb.append("当前空闲内存：" + runtime.freeMemory() / M + "M").append("\n");
			sb.append("当前已使用内存：" + (runtime.totalMemory() - runtime.freeMemory()) / M).append("\n");
			DocUtils.insertLog("内存不足!", sb.toString());
			return true;
		} else {
			return false;
		}
	}

	// 得到本机Mac地址
	public String getMac() {
		// 获取wifi管理器
		WifiManager wifiMng = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfor = wifiMng.getConnectionInfo();
		return wifiInfor.getMacAddress();
	}

	public void main() {
		Intent localIntent = new Intent(this, Swy_splash.class);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		startActivity(localIntent);
	}

	public void splash() {
		activityManager.popAllActivityExceptOne(null);
		main();
	}

	/**
	 * 获取 版本名称
	 * 
	 * @return
	 */
	public String getVersionName() {
		try {
			return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
}
