package com.ahjswy.cn.views;

import com.ahjswy.cn.utils.TextUtils;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;

public class AutoTextViews extends AutoCompleteTextView {
	private String beforeTextChange;
	private OnTextChangeListener changeListener;
	private Handler handler;
	private boolean isItemClick = false;
	private boolean isReplace = true;

	public AutoTextViews(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		addTextChangedListener(this.watcher);
		this.handler = new Handler();
	}

	private TextWatcher watcher = new TextWatcher() {
		public void afterTextChanged(Editable paramAnonymousEditable) {
		}

		public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1,
				int paramAnonymousInt2, int paramAnonymousInt3) {
			AutoTextViews.this.beforeTextChange = paramAnonymousCharSequence.toString();
		}

		public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1,
				int paramAnonymousInt2, int paramAnonymousInt3) {
			if ((AutoTextViews.this.isItemClick) && (AutoTextViews.this.isReplace)) {
				AutoTextViews.this.isItemClick = false;
				return;
			}
			AutoTextViews.this.changeListener.onChanged(AutoTextViews.this, paramAnonymousCharSequence.toString());
		}
	};

	public void setOnTextChangeListener(OnTextChangeListener paramOnTextChangeListener) {
		this.changeListener = paramOnTextChangeListener;
	}

	public void setReplace(boolean paramBoolean) {
		this.isReplace = paramBoolean;
	}

	public void setText(String paramString) {
		super.setText(paramString);
		if (!TextUtils.isEmptyS(paramString))
			setSelection(paramString.length());
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

	private void show() {
		super.showDropDown();
	}

	public String getBeforeTextChange() {
		return this.beforeTextChange;
	}

	public void showDropDown() {
		this.handler.postDelayed(new Runnable() {
			public void run() {
				AutoTextViews.this.show();
			}
		}, 100L);
	}

	public abstract interface OnTextChangeListener {
		public abstract void onChanged(View v, String str);
	}
}
