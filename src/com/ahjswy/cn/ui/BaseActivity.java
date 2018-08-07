package com.ahjswy.cn.ui;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.utils.PDH;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;

public class BaseActivity extends Activity {
	private ActionBar actionBar;
	public boolean isModify = true;
	protected Handler mHandler;

	public void actionBarClickBack() {
		getActionBar().setDisplayOptions(4);
	}

	public void exit() {
		MyApplication.getInstance().exit();
	}

	public void onBackPressed() {
		super.onBackPressed();
		MyApplication.getInstance().getActivityManager().popActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SystemState.enableStrictMode();
		MyApplication.getInstance().getActivityManager().pushActivity(this);
		this.mHandler = new Handler();
	}

	protected void onDestroy() {
		MyApplication.getInstance().getActivityManager().popActivity(this);
		super.onDestroy();
	}

	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(menu);
	}

	protected void onResume() {
		super.onResume();
		this.actionBar = getActionBar();
		if (this.actionBar != null) {
			this.actionBar.setHomeButtonEnabled(true);
			setActionBarText();
		}
	}

	public void refreshUI() {

	}

	public void setActionBarText() {
		// getActionBar().setTitle("打印模版编辑");
	}

	public void setActionBarIcon() {
		getActionBar().setIcon(R.drawable.menu_btn_back);
	}

	protected void showError(final String showError) {
		this.mHandler.post(new Runnable() {
			public void run() {
				PDH.showError(showError);
			}
		});
	}

	protected void showSuccess(final String showSuccess) {
		this.mHandler.post(new Runnable() {
			public void run() {
				PDH.showSuccess(showSuccess);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}