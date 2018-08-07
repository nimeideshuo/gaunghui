package com.ahjswy.cn.ui;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.MyApplication;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.TextView;

public class AboutActivity extends BaseActivity {
	public static int height = 0;
	public static int width = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_splash);
		TextView version = (TextView) findViewById(R.id.version);
		version.setText(MyApplication.getInstance().getVersionName());
		DisplayMetrics displayMetrics = new DisplayMetrics();
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
	}

}
