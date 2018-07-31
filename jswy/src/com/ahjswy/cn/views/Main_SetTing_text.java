package com.ahjswy.cn.views;

import com.ahjswy.cn.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main_SetTing_text extends RelativeLayout {

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.ahjswy.cn";
	private TextView tv_title;
	private TextView tv_title2;
	private String mTitle;
	private String leftName;

	public Main_SetTing_text(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTitle = attrs.getAttributeValue(NAMESPACE, "title");
		leftName = attrs.getAttributeValue(NAMESPACE, "leftName");
		inintView();
		initDate();
	}

	private void inintView() {
		// 将自定义布局 设置给当前的 main_set_text this：代表放在 ViewGroup 容器中
		View.inflate(getContext(), R.layout.main_set_text, this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title2 = (TextView) findViewById(R.id.tv_title2);
		setTitle(mTitle);
		setTitle2(leftName);
	}

	public void setTitle(String title) {
		tv_title.setText(title);
	}

	public void setTitle2(String title2) {
		tv_title2.setText(title2);
	}

	private void initDate() {
	}
}
