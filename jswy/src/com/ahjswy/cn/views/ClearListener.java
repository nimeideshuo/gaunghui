package com.ahjswy.cn.views;

import android.view.MotionEvent;
import android.view.View;

public class ClearListener implements View.OnTouchListener {
	private int width;

	public ClearListener(int width) {
		this.width = width;
	}

	@Override // TODO 等待调试
	public boolean onTouch(View v, MotionEvent event) {
		// ((EditText) v).getCompoundDrawables()[2] != null &&
		if (event.getAction() == MotionEvent.ACTION_UP
				&& event.getX() > ((float) (v.getWidth() - v.getPaddingRight() - this.width))) {
			System.out.println("getWidth     true");
			return true;
		}
		// if (((EditText) v).getCompoundDrawables()[2] != null &&
		// event.getAction() == 1
		// && event.getX() > ((float) (v.getWidth() - v.getPaddingRight() -
		// this.width))) {
		// System.out.println("getWidth true");
		// return true;
		// }
		System.out.println("ccc    false");
		return false;
	}

}
