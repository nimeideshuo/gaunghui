package com.ahjswy.cn.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class InfoDialog {
	public static void showError(Context paramContext, String paramString) {
		new AlertDialog.Builder(paramContext).setTitle("错误").setMessage(paramString).setPositiveButton("确定", null)
				.show();
	}

	public static void showErrorBack(Context context, String message, OnClickListener clickListener) {
		new AlertDialog.Builder(context).setTitle("错误").setMessage(message).setPositiveButton("确定", clickListener)
				.show();
	}

	public static void showMessage(Context paramContext, String paramString) {
		new AlertDialog.Builder(paramContext).setTitle("提示").setMessage(paramString).setPositiveButton("确定", null)
				.show();
	}

}
