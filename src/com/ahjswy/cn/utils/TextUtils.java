package com.ahjswy.cn.utils;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class TextUtils {
	public static boolean isEmptyS(String str) {
		return ((str != null) && (str.length() != 0) && (!("null".equals(str))));
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	public static CharSequence setTextStyle(String str1, String str2) {
		SpannableString spannable = new SpannableString(str1 + str2);
		spannable.setSpan(new ForegroundColorSpan(-65536), spannable.length() - str2.length(), spannable.length(), 33);
		return spannable;
	}

	public static String out(String str) {
		if (TextUtils.isEmptyS(str)) {
			return str;
		}
		return "";
	}

	public static boolean isZero(String str) {
		boolean v0;
		if (str == null || str.length() == 0 || ("null".equals(str)) || ("0".endsWith(str))) {
			v0 = true;
		} else {
			v0 = false;
		}
		return v0;
	}

	public static String parsePicName(String oldName) {
		if (TextUtils.isEmptyS(oldName)) {
			oldName = null;
		} else if (oldName.contains("/")) {
			oldName = oldName.substring(oldName.lastIndexOf("/"));
		}
		return oldName;
	}
}
