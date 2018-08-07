package com.ahjswy.cn.ui;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.Jswy_logUser;
import com.ahjswy.cn.app.LoginPassword;
import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.User;
import com.ahjswy.cn.service.ServiceSystem;
import com.ahjswy.cn.utils.BmobUtils;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.views.Dialog_ed_message;
import com.ahjswy.cn.views.Dialog_work_zt;
import com.ahjswy.cn.views.Dialog_work_zt.Dialog_work_zt_callBack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Swy_splash extends BaseActivity {
	private Dialog_ed_message serviceips;
	public static Swy_splash swy_splash;
	private Dialog_work_zt dialog_work_zt;
	private AccountPreference ap;
	private User user;
	public static int height;
	public static int width = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swy_splash);
		initview();
	}

	private void initview() {
		ap = new AccountPreference();
		loading = (TextView) findViewById(R.id.loading);
		tv_Version = (TextView) findViewById(R.id.tv_Version);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		width = localDisplayMetrics.widthPixels;
		height = localDisplayMetrics.heightPixels;
		tv_Version.setText("版本:" + MyApplication.getInstance().getVersionName());
		System.out.println("width:" + width + "   height:" + height);
		System.out.println("id:" + MyApplication.getInstance().getAndroidId());

		serviceips = new Dialog_ed_message(this);
		loading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				registerCheckHandler.sendEmptyMessage(1);
			}
		});
		BmobUtils.getInstance().updata();
		new Thread() {
			@Override
			public void run() {
				super.run();
				// 进行本地 ip 判断 是否 有ip
				if (ap.getServerIp().isEmpty()) {
					registerCheckHandler.sendEmptyMessageDelayed(1, 2000L);
					return;
				}
				// 进行 ip 地址检测
				String str = new ServiceSystem().sys_CheckRegister(ap.getServerIp() + ":" + ap.getServerPort());
				if (str.equals("register")) {
					registerCheckHandler.sendEmptyMessageDelayed(2, 2000L);
					return;
				}
				if (str.equals("success")) {
					if (SystemState.getAccountSet() == null) {
						// 验证工作帐套
						registerCheckHandler.sendEmptyMessageDelayed(3, 2000L);
						return;
					}
					user = SystemState.getObject("cu_user", User.class);
					if (user == null) {
						registerCheckHandler.sendEmptyMessageDelayed(6, 2000L);
						return;
					}
					registerCheckHandler.sendEmptyMessageDelayed(5, 2000L);
					return;
				}
				registerCheckHandler.sendMessage(registerCheckHandler.obtainMessage(4, str));

			}
		}.start();
	}

	private Handler registerCheckHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == 4) {
				String message = msg.obj.toString();
				loading.setText("无网络链接");
				if (message.length() > 1) {
					PDH.showFail(message);
					return;
				}
				PDH.showFail("无网络链接");
			}
			switch (msg.what) {
			case 1:
				serviceips.show();
				if (ap != null && ap.getServerIp().length() > 0) {
					serviceips.setEdtext(ap.getServerIp() +":"+ ap.getServerPort());
				}
				serviceips.setComfirmListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						MyApplication.getInstance().exit();
					}
				});

				serviceips.setCancelListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (serviceips.getEdtext().isEmpty()) {
							PDH.showFail("请输入服务器IP地址");
							return;
						}
						serviceips.dismiss();
						// 验证IP地址 是否正确
						PDH.show(Swy_splash.this, "正在连接...", new ProgressCallBack() {

							@Override
							public void action() {
								String ip_infor = new ServiceSystem()
										.sys_CheckRegister(serviceips.getEdtext().toString());
								if (ip_infor.equals("register")) {
									// 保存IP 地址
									ap.setServerIp(serviceips.getEdtext().split(":")[0]);
									// 保存端口号
									ap.setServerPort(serviceips.getEdtext().split(":")[1]);
									registerCheckHandler.sendEmptyMessage(2);
									return;
								}
								if (ip_infor.equals("success")) {
									// 保存IP 地址
									ap.setServerIp(serviceips.getEdtext().split(":")[0]);
									// 保存端口号
									ap.setServerPort(serviceips.getEdtext().split(":")[1]);
									registerCheckHandler.sendEmptyMessage(3);
									return;
								}
								registerCheckHandler.sendMessage(registerCheckHandler.obtainMessage(4, ip_infor));
							}
						});
					}
				}

				);

				break;
			case 2:
				// 跳到注册界面
				startActivity(new Intent(Swy_splash.this, RegisterPadAct.class));
				finish();
				break;
			case 3:
				// 工作帐套
				if (dialog_work_zt == null) {
					dialog_work_zt = new Dialog_work_zt(Swy_splash.this, R.style.MyDialog_NoTitle);
				}
				dialog_work_zt.onItemCallBack(new Dialog_work_zt_callBack() {

					@Override
					public void itemClick() {
						if (user == null) {
							// 跳转到 user 登陆界面
							registerCheckHandler.sendEmptyMessage(6);
							return;
						}
						registerCheckHandler.sendEmptyMessage(5);
					}
				});
				dialog_work_zt.showMe();
				break;

			case 5:
				// 账号不为null 直接跳到 九宫格 密码
				startActivity(new Intent().setClass(Swy_splash.this, LoginPassword.class));
				finish();
				break;
			case 6:
				startActivity(new Intent().setClass(Swy_splash.this, Jswy_logUser.class));
				finish();
				break;
			case 7:
				loading.setText("无网络链接");
				PDH.showFail("无网络链接");
				break;
			default:
				break;
			}
			// if (RequestHelper.isSuccess(msg.getData().getString(""))) {
			// // if ((this.user == null) || (!this.isfirststart)) {
			// // this.updateHandle.sendEmptyMessage(3);
			// // return;
			// // }
			// // this.updateHandle.sendEmptyMessageDelayed(0, 2000L);
			// // return;
			// }
			// this.showResult(localString);
		};
	};
	private TextView loading;
	private TextView tv_Version;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			System.exit(0);// 退出
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
