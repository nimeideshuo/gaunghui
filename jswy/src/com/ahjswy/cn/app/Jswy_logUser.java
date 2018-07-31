package com.ahjswy.cn.app;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DBUser;
import com.ahjswy.cn.model.User;
import com.ahjswy.cn.response.RespUserEntity;
import com.ahjswy.cn.service.ServiceSystem;
import com.ahjswy.cn.service.ServiceUser;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.TextUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Jswy_logUser extends BaseActivity implements OnClickListener {
	private Button btn_start;
	private EditText ed_userName;
	private EditText ed_password;
	AccountPreference ap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		initView();
	}

	private void initView() {
		ap = new AccountPreference();
		findViewById(R.id.tv_register_title).setVisibility(View.GONE);
		findViewById(R.id.btn_exit).setVisibility(View.GONE);
		ed_userName = (EditText) findViewById(R.id.ed_userName);
		ed_password = (EditText) findViewById(R.id.ed_password);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_start.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			startIntent();
			break;
		default:
			break;
		}
	}

	public void startIntent() {
		if (TextUtils.isEmpty(ed_userName.getText().toString())) {
			PDH.showError("请输入用户名");
			return;
		}
		if (TextUtils.isEmpty(ed_password.getText().toString())) {
			PDH.showError("请输入密码");
			return;
		}
		//testSWY
		// if(MyApplication.getInstance().isTestSWY()){
		// PDH.show(this, "验证账号密码...", new ProgressCallBack() {
		//
		// @Override
		// public void action() {
		// SystemClock.sleep(500);
		// startActivity(new Intent(Jswy_logUser.this, LoginPassword.class));
		// finish();
		// }
		// });
		// return;
		// }
		
		
		// 验证 user password 密码是否正确

		PDH.show(this, "正在加载...", new ProgressCallBack() {

			@Override
			public void action() {

				// user 账号验证
				String infor = new ServiceUser().usr_UserLogin(ed_userName.getText().toString(),
						ed_password.getText().toString());
				if (RequestHelper.isSuccess(infor)) {
					RespUserEntity respUserEntity = (RespUserEntity) JSONUtil.readValue(infor, RespUserEntity.class);
					User localUser = new User();
					localUser.setId(respUserEntity.getId());
					localUser.setIsaccountmanager(respUserEntity.getIsAccountManager());
					localUser.setName(respUserEntity.getName());
					localUser.setPassword("");
					localUser.setOfflinepassword("");
					SystemState.saveObject("cu_user", localUser);
					SystemState.saveObject("gpsinterval", Integer.valueOf(respUserEntity.getGpsInterval()));
					// ap.setValue("pw", "");
					// 获取数据库链接的账号密码
					if (SystemState.getDBUser() == null) {
						String dbinfo = new ServiceSystem().sys_getdbuserinfo();
						if (RequestHelper.isSuccess(dbinfo)) {
							DBUser dbUser = JSONUtil.fromJson(dbinfo, DBUser.class);
							SystemState.saveObject("dbUser", dbUser);
						} else {
							showError("数据库链接失败!请登录重试!");
							return;
						}
					}
					startActivity(new Intent(Jswy_logUser.this, LoginPassword.class));
					finish();
				} else {
					showError("网络异常" + infor);
				}

			}
		});
	}

	/**
	 * * 监听Back键按下事件,方法2: * 注意: * 返回值表示:是否能完全处理该事件 * 在此处返回false,所以会继续传播该事件. *
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			MyApplication.getInstance().exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void setActionBarText() {
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setTitle("登陆");
	}
}
