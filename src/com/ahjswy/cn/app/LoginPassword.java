package com.ahjswy.cn.app;

import com.ahjswy.cn.R;
import com.ahjswy.cn.password.LockPatternView;
import com.ahjswy.cn.password.LockPatternView.OnPatternChangeListener;
import com.ahjswy.cn.service.ServiceSystem;
import com.ahjswy.cn.service.ServiceUser;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.ui.Swy_splash;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPassword extends BaseActivity implements OnPatternChangeListener {
	private LockPatternView lpwv;
	int i = 4;
	private TextView login_toast;
	boolean open = true;
	private AccountPreference ap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		TextView tv_passWord_name = (TextView) findViewById(R.id.tv_passWord_name);
		login_toast = (TextView) findViewById(R.id.login_toast);
		ap = new AccountPreference();
		lpwv = (LockPatternView) this.findViewById(R.id.mLocusPassWordView);
		lpwv.setOnPatternChangeListener(this);
		String passWord = ap.getValue("passWord", "");
		if (passWord.length() == 0) {
			login_toast.setText("请输入初始化密码");
		} else {
			login_toast.setText("请输入密码");
			open = false;
		}
		// if (MyApplication.getInstance().isTestSWY()) {
		// tv_passWord_name.setText("管理员");
		// } else {
		// }
		tv_passWord_name.setText(SystemState.getUser().getName().toString());

	}

	@Override
	public void onPatternChange(String patternPassword) {
		String passWord = ap.getValue("passWord", "");
		if (open) {
			if (patternPassword == null) {
				login_toast.setText("至少4个点");
			} else if (passWord.length() == 0) {
				login_toast.setText("请再次输入密码");
				ap.setValue("passWord", patternPassword);
			} else if (passWord.equals(patternPassword)) {
				Intent intent = new Intent(LoginPassword.this, SwyMain.class);
				// 打开新的Activity
				startActivity(intent);
				finish();
				return;
			} else if (!passWord.equals(patternPassword)) {
				ap.setValue("passWord", "");
				login_toast.setText("请输入初始化密码");
				Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
			}
		} else {
			if (passWord.equals(patternPassword)) {
				Intent intent = new Intent(LoginPassword.this, SwyMain.class);
				// 打开新的Activity
				startActivity(intent);
				finish();
				return;
			}
			if (patternPassword == null) {
				login_toast.setText("至少4个点");
			} else if (i == 0) {
				ap.setValue("passWord", "");
				login_toast.setTextColor(getResources().getColor(android.R.color.black));
				login_toast.setText("请输入初始化密码");
				open = true;
			} else {
				login_toast.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
				login_toast.setText("手势错误，你还可以输入" + i + "次");
				i--;
			}
		}
		lpwv.clear();
	}

	@Override
	public void onPatternStarted(boolean isStarted) {
		// TODO Auto-generated method stub

	}

	Handler bizhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String bizhan = msg.obj.toString();
			if ((RequestHelper.isSuccess(bizhan)) && (ap.saveBizInfo(bizhan))) {
				Utils.init();
				ap.setValue("bizinfo", bizhan);
			}
		};
	};
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				String[] infor = ((String) JSONUtil.parse2Map(message).get("info")).split(",");
				if (ap == null)
					ap = new AccountPreference();
				if ("permit".equals(infor[0])) {
					ap.setValue("ViewKCStockBrowse", "1");
				} else {
					ap.setValue("ViewKCStockBrowse", "0");
				}
				if ("permit".equals(infor[1])) {
					ap.setValue("ViewChangeprice", "1");
				} else {
					ap.setValue("ViewChangeprice", "0");
				}

			} else {
				PDH.showFail(message);
			}
		};
	};

	@Override
	protected void onStart() {
		super.onStart();
		// if (MyApplication.getInstance().isTestSWY()) {
		// return;
		// }
		PDH.show(this, new ProgressCallBack() {

			@Override
			public void action() {
				// 显示所有的仓库 库存
				// String bizp = new ServiceSystem().sys_GetBizParameter();
				// 获取 权限
				// String authority = new
				// ServiceUser().usr_GetAuthoritys("ViewKCStockBrowse,ViewChangeprice");
				String authority = new ServiceUser().usr_GetAuthoritys("ViewKCStockBrowse,ViewChangeprice");
				handler.sendMessage(handler.obtainMessage(0, authority));
				// 获取服务器基本设置
				String bizp = new ServiceSystem().sys_GetBizParameter();
				bizhandler.sendMessage(bizhandler.obtainMessage(0, bizp));
			}
		});
	}

}
