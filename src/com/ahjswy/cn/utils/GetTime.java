package com.ahjswy.cn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class GetTime {
	// 获取本地系统的时间
	public static String getDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

		String str = formatter.format(curDate);

		return str;

	}
}
