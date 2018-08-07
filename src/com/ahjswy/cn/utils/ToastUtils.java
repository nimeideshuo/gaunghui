package com.ahjswy.cn.utils;

import android.widget.Toast;

public class ToastUtils {
	public static void show(String str) {
		Toast.makeText(Utils.getContext(), str, Toast.LENGTH_SHORT).show();
	}
}
