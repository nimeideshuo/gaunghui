package com.ahjswy.cn.views;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;

public class AutoTextView extends AutoCompleteTextView {
	private String beforeTextChange;
	private OnTextChangeListener changeListener;
	private Handler handler;
	private boolean isItemClick = false;
	private boolean isReplace = true;
	private TextWatcher watcher = new TextWatcher() {
		public void afterTextChanged(Editable paramAnonymousEditable) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			System.out.println("item click: " + AutoTextView.this.isItemClick);
			if ((AutoTextView.this.isItemClick) && (AutoTextView.this.isReplace)) {
				AutoTextView.this.isItemClick = false;
				return;
			}
			// 回调 返回
			changeListener.onChanged(AutoTextView.this, s.toString());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			//输入文本将要被改变时候调用
			beforeTextChange = s.toString();
		}

	};

	public AutoTextView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		addTextChangedListener(this.watcher);
		this.handler = new Handler();
	}

	private void show() {
		super.showDropDown();
	}

	public String getBeforeTextChange() {
		return this.beforeTextChange;
	}

	protected void replaceText(CharSequence paramCharSequence) {
		this.isItemClick = true;
		if (this.isReplace) {
			super.replaceText(paramCharSequence);
			return;
		}
		Editable localEditable = getText();
		Selection.setSelection(localEditable, localEditable.length());
	}

	public void setOnTextChangeListener(OnTextChangeListener paramOnTextChangeListener) {
		this.changeListener = paramOnTextChangeListener;
	}

	public void setReplace(boolean paramBoolean) {
		this.isReplace = paramBoolean;
	}

	public void setText(String paramString) {
		super.setText(paramString);
		if (!paramString.isEmpty()) {
			setSelection(paramString.length());
		}
	}

	public void showDropDown() {
		this.handler.postDelayed(new Runnable() {
			public void run() {
				AutoTextView.this.show();
			}
		}, 100L);
	}

	public abstract interface OnTextChangeListener {
		public abstract void onChanged(View paramView, String paramString);

	}
}