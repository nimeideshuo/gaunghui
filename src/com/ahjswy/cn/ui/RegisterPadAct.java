package com.ahjswy.cn.ui;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.model.User;
import com.ahjswy.cn.service.ServiceSystem;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterPadAct extends BaseActivity implements View.OnClickListener {
	private Button btnExit;
	private Button btnRegister;
	private EditText etPadUser;
	private EditText etRegisterId;
	private AccountPreference ap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		// 加载 注册布局
		TextView tv_user = (TextView) findViewById(R.id.tv_user);
		TextView tv_pass = (TextView) findViewById(R.id.tv_pass);
		etRegisterId = ((EditText) findViewById(R.id.ed_userName));
		etPadUser = ((EditText) findViewById(R.id.ed_password));
		btnRegister = ((Button) findViewById(R.id.btn_start));
		btnExit = ((Button) findViewById(R.id.btn_exit));
		// 加载布局 设设置监听
		btnRegister.setOnClickListener(this);
		btnExit.setOnClickListener(this);
		ap = new AccountPreference();
		tv_user.setText("注册码");
		tv_pass.setText("注册人");
		btnRegister.setText("注册");
	}

	// 点击的 是 注册按钮
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			register();
			break;
		case R.id.btn_exit:
			MyApplication.getInstance().exit();
		default:
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				RequestHelper.showError(msg.obj.toString());
				break;
			case 1:
				// 保存 注册 信息 注册人
				User us = new User();
				us.setId(registerId);
				us.setName(padUser);
				ap.setValue("cu_user", us);
				break;
			default:
				break;
			}

		};
	};
	private String registerId;
	private String padUser;

	private void register() {
		registerId = etRegisterId.getText().toString();
		padUser = etPadUser.getText().toString();
		if (registerId.isEmpty()) {
			PDH.showError("请输入注册码");
			return;
		}
		if (padUser.isEmpty()) {
			PDH.showError("请输入注册人");
			return;
		}
		PDH.show(this, "正在注册.....", new ProgressCallBack() {
			@Override
			public void action() {
				// 待验证 信息
				String str = new ServiceSystem().sys_Register(registerId, padUser);
				if (RequestHelper.isSuccess(str)) {
					handler.sendEmptyMessage(1);
					RegisterPadAct.this.startActivity(new Intent().setClass(RegisterPadAct.this, Swy_splash.class));
					RegisterPadAct.this.finish();
					return;
				}
				handler.sendMessage(handler.obtainMessage(0, str));
			}
		});
		;
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("注册");
	}
}