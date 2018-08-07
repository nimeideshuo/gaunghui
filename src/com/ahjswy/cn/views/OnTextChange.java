package com.ahjswy.cn.views;

import android.text.Editable;
import android.text.TextWatcher;

public class OnTextChange implements TextWatcher {
	OnTextClearChangeListener set;

	public OnTextChange(OnTextClearChangeListener set) {
		this.set = set;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (before == 0) {
			this.set.addTo(s.toString());
			return;
		}
		this.set.cutTo(s.toString());
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	public abstract interface OnTextClearChangeListener {
		public abstract void addTo(String addTo);

		public abstract void cutTo(String cutTo);
	}

}
