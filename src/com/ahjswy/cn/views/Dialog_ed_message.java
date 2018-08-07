package com.ahjswy.cn.views;

import java.util.Timer;
import java.util.TimerTask;

import com.ahjswy.cn.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dialog_ed_message extends Dialog {
	private TextView tvTitle;
	public EditText ed_service;
	private Button btn_dialg_ip_false;
	private Button btn_dialg_ip_true;

	public Dialog_ed_message(Context context) {
		super(context, R.style.MyDialog_NoTitle);
		init();
	}

	private void init() {
		setContentView(R.layout.dialog_service_ip);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		ed_service = (EditText) findViewById(R.id.ed_service);
		btn_dialg_ip_true = (Button) findViewById(R.id.btn_dialg_ip_true);
		btn_dialg_ip_false = (Button) findViewById(R.id.btn_dialg_ip_false);

		// 延迟弹出 软键盘
		Timer timer = new Timer();
		timer.schedule(new TimerTask()

		{
			public void run() {
				InputMethodManager inputManager =

				(InputMethodManager) ed_service.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.showSoftInput(ed_service, 0);
			}
		},

		500);
	}

	/*
	 * 设置 dialog 标题头
	 */
	public void setTitleMessage(String message) {
		tvTitle.setText(message);
	}

	/*
	 * 设置 dialog set ed message
	 */
	public void setEdtext(String message) {
		ed_service.setText(message);
	}

	/*
	 * 设置 dialog get ed message
	 */
	public String getEdtext() {
		return ed_service.getText().toString();
	}

	/*
	 * 设置 dialog 确定文字
	 */
	public void setTextTrue(String text) {
		btn_dialg_ip_true.setText(text);
	}

	/*
	 * 设置 dialog 取消文字
	 */
	public void setTextFalse(String text) {
		btn_dialg_ip_false.setText(text);
	}

	/**
	 * 确认监听
	 * 
	 * @param paramOnClickListener
	 */
	public void setCancelListener(View.OnClickListener paramOnClickListener) {
		btn_dialg_ip_true.setOnClickListener(paramOnClickListener);
	}

	// 取消监听
	public void setComfirmListener(View.OnClickListener paramOnClickListener) {
		btn_dialg_ip_false.setOnClickListener(paramOnClickListener);
	}

}
