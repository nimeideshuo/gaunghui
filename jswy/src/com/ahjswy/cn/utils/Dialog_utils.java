package com.ahjswy.cn.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class Dialog_utils {
	static int weizi = 0;

	// dialog 单选
	public static void setSingleChoiceItems(final Context context, String title, final String[] values, int weizhi,
			final setTingCallBack callBack) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		/**
		 * 第一个参数指定我们要显示的一组下拉单选框的数据集合 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
		 * 第三个参数给每一个单选项绑定一个监听器
		 */
		builder.setSingleChoiceItems(values, weizhi, new DialogInterface.OnClickListener() {
			// 被点击 item 时候 反应
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setItems(values, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				weizi = which;
			}
		});
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				callBack.settingCallBack(weizi);
			}
		});
		builder.show();
	}

	static Boolean duoXuan[];

	/*
	 * dialog 多选
	 */
	public static void setMultiChoiceItems(final Context context, String title, final String[] vialue,
			final boolean[] weizhi, final setDuoXuanCallBack setDuoXuan) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		// 设置一个单项选择下拉框
		/**
		 * 第一个参数指定我们要显示的一组下拉多选框的数据集合
		 * 第二个参数代表哪几个选项被选择，如果是null，则表示一个都不选择，如果希望指定哪一个多选选项框被选择，
		 * 需要传递一个boolean[]数组进去，其长度要和第一个参数的长度相同，例如 {true, false, false, true};
		 * 第三个参数给每一个多选项绑定一个监听器
		 */
		builder.setMultiChoiceItems(vialue, weizhi, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				weizhi[which] = isChecked;
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				boolean boo = false;
				for (int i = 0; i < weizhi.length; i++) {
					if (weizhi[i]) {
						boo = true;
					}
				}
				if (boo) {
					setDuoXuan.setDuoXuan(weizhi);
				} else {
					PDH.showMessage("请至少选择一项检索关键字。");
				}

			}

		});
		builder.show();
	}

	public interface setTingCallBack {
		public void settingCallBack(Integer set);

	}

	public interface setDuoXuanCallBack {
		void setDuoXuan(boolean[] weizhi);
	}
}
