package com.ahjswy.cn;

import com.ahjswy.cn.ui.BaseActivity;

import android.os.Bundle;

public class SettingActivity extends BaseActivity {
	private PrefsFragment pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pref = new PrefsFragment();
		getFragmentManager().beginTransaction().replace(android.R.id.content, pref).commit();

	}

	protected void onResume() {
		super.onResume();
		if (this.pref == null)
			this.pref = new PrefsFragment();
	}

	public void setActionBarText() {
		getActionBar().setTitle("设置");
	}
}
