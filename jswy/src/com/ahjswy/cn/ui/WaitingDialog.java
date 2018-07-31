package com.ahjswy.cn.ui;

import com.ahjswy.cn.R;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.widget.TextView;

public class WaitingDialog extends Dialog {
	public WaitingDialog(Activity paramActivity) {
		super(paramActivity, R.style.Transparent);
		setCanceledOnTouchOutside(false);
		setContentView(R.layout.dia_loading);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}

	public void setText(String paramString) {
		((TextView) findViewById(R.id.textView)).setText(paramString);
	}

	public void show() {
		super.show();
		((AnimationDrawable) findViewById(R.id.imageView).getBackground()).stop();
		((AnimationDrawable) findViewById(R.id.imageView).getBackground()).start();
	}
}