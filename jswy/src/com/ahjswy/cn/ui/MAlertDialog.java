package com.ahjswy.cn.ui;

import com.ahjswy.cn.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MAlertDialog extends Dialog {
	private Button btn_Neutral;
	private Button btn_Negative;
	public Button btn_dialg_back;
	public TextView tv_message;
	public TextView tvTitle;

	/*
	 * 自定义主题 走次方法
	 */
	public MAlertDialog(Context context) {
		super(context, R.style.MyDialog_NoTitle);
		intView();
	}

	private void intView() {
		setContentView(R.layout.title_dialog);
		btn_Neutral = (Button) findViewById(R.id.btn_Neutral);
		btn_Negative = (Button) findViewById(R.id.btn_Negative);
		btn_dialg_back = (Button) findViewById(R.id.btn_dialg_back);
		tv_message = (TextView) findViewById(R.id.tv_message);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		btn_dialg_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setMessage(String message) {
		tv_message.setText(message);
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	/**
	 * 确认
	 * 
	 * @param text
	 * @param clicklistener
	 */
	public void setNeutralButton(String text, final OnClickListener clicklistener) {
		btn_Neutral.setText(text);
		btn_Neutral.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clicklistener.onClick(MAlertDialog.this);
				MAlertDialog.this.dismiss();
			}
		});
	}

	/**
	 * 确认
	 * 
	 * @param clicklistener
	 */
	public void setNeutralButton(final OnClickListener clicklistener) {
		btn_Neutral.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clicklistener.onClick(MAlertDialog.this);
				MAlertDialog.this.dismiss();
			}
		});
	}

	/**
	 * 否
	 * 
	 * @param text
	 * @param clicklistener
	 */
	public void setNegativeButton(String text, final OnClickListener clicklistener) {
		btn_Negative.setText(text);
		btn_Negative.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clicklistener.onClick(MAlertDialog.this);
				MAlertDialog.this.dismiss();
			}
		});
	}

	/**
	 * 否
	 * 
	 * @param clicklistener
	 */
	public void setNegativeButton(final OnClickListener clicklistener) {
		btn_Negative.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clicklistener.onClick(MAlertDialog.this);
				MAlertDialog.this.dismiss();
			}
		});
	}



	public interface OnClickListener {
		public void onClick(MAlertDialog dialog);
	}

}
