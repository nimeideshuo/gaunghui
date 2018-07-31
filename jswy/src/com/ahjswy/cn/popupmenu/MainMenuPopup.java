package com.ahjswy.cn.popupmenu;

import com.ahjswy.cn.R;
import com.ahjswy.cn.SettingActivity;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.service.ServiceSystem;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.views.Dialog_ed_message;
import com.ahjswy.cn.views.Dialog_message;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainMenuPopup extends PopupWindow implements OnClickListener {
	private Activity act;
	private Button btnClear;
	private Button btnExsit;
	private Button btnIp;
	private Button btnSetting;
	private View root;
	private AccountPreference ap;

	public MainMenuPopup(Activity act) {
		super(act);
		this.act = act;
		this.root = LayoutInflater.from(act).inflate(R.layout.popup_menu_main, null);
		setContentView(this.root);
		this.btnClear = ((Button) this.root.findViewById(R.id.btnClear));
		this.btnExsit = ((Button) this.root.findViewById(R.id.btnExsit));
		this.btnSetting = ((Button) root.findViewById(R.id.btnSetting));
		this.btnIp = ((Button) this.root.findViewById(R.id.btnIp));
		this.btnClear.setOnClickListener(this);
		this.btnExsit.setOnClickListener(this);
		this.btnSetting.setOnClickListener(this);
		this.btnIp.setOnClickListener(this);
		setAnimationStyle(R.style.buttom_in_out);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int i = localDisplayMetrics.widthPixels;
		int j = localDisplayMetrics.heightPixels;
		setWidth(i);
		setHeight(j / 11);
		setBackgroundDrawable(null);
		ap = new AccountPreference();
	}

	@Override
	public void onClick(View v) {
		final Dialog_message dialog_message = new Dialog_message(act);
		dismiss();
		WindowManager.LayoutParams localLayoutParams = this.act.getWindow().getAttributes();
		localLayoutParams.alpha = 1.0F;
		this.act.getWindow().setAttributes(localLayoutParams);
		switch (v.getId()) {
		case R.id.btnIp:
			final Dialog_ed_message serviceips = new Dialog_ed_message(act);
			serviceips.show();
			serviceips.setEdtext(ap.getServerIp().toString()+":"+ap.getServerPort());
			serviceips.setComfirmListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					serviceips.dismiss();

				}
			});
			serviceips.setCancelListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					serviceips.dismiss();
					if (serviceips.getEdtext().isEmpty()) {
						PDH.showFail("请输入服务器IP地址");
						return;
					}
					PDH.show(act, "正在测试链接...", new ProgressCallBack() {

						@Override
						public void action() {
							String str = new ServiceSystem().sys_CheckRegister(serviceips.getEdtext());
							if (str.equals("success")) {
								// 链接成功 则保存IP
								ap.setServerIp(serviceips.getEdtext().split(":")[0]);
								ap.setServerPort(serviceips.getEdtext().split(":")[1]);
								handler.sendEmptyMessage(0);
								return;
							}
							handler.sendMessage(handler.obtainMessage(2, str));
						}
					});
				}
			});
			break;
		case R.id.btnSetting:
			Department department = SystemState.getDepartment();
			if (department == null) {
				PDH.showFail("不存在部门,请在服务器上添加");
				return;
			}
			act.startActivity(new Intent().setClass(this.act, SettingActivity.class));
			break;
		case R.id.btnExsit:

			dialog_message.show();
			dialog_message.setMessage("确定退出程序?");
			dialog_message.setCancelListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 退出软件
					dialog_message.dismiss();
					MyApplication.getInstance().exit();
				}
			});
			break;
		case R.id.btnClear:
			dialog_message.show();
			dialog_message.setMessage("你确定注销当前用户?");
			dialog_message.setCancelListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_message.dismiss();
					SystemState.saveObject("cu_user", null);
					MyApplication.getInstance().splash();
				}
			});
			break;

		default:
			break;
		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				PDH.showMessage("程序即将重新启动");
				handler.sendEmptyMessageDelayed(1, 3000);
				break;
			case 1:
				MyApplication.getInstance().splash();
				break;
			case 2:
				RequestHelper.showError(msg.obj.toString());
				break;
			default:
				break;
			}

		};
	};
}
