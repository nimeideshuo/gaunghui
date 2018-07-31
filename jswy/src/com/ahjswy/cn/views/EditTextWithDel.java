package com.ahjswy.cn.views;

import com.ahjswy.cn.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * @description: 自定义带有删除功能的EditText
 * @projectName: EditTextWithDelDome
 * @date: 2016-02-28
 * @time: 23:34
 */
public class EditTextWithDel extends EditText {
	// private final static String TAG = "EditTextWithDel";
	private Drawable imgAble;
	private Context mContext;

	public EditTextWithDel(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				imgAble = mContext.getResources().getDrawable(R.drawable.search_clear_pressed);
			}
		}, 100);
		// setOnFocusChangeListener(new FocusChangeListenerImpl());
//		addTextChangedListener(new TextWatcher() {
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				if (s.toString().contains(".")) {
//					if (s.length() - 1 - s.toString().indexOf(".") > 3) {
//						s = s.toString().subSequence(0, s.toString().indexOf(".") + 4);
//						setText(s);
//						setSelection(s.length());
//						return;
//					}
//				}
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				setDrawable();
//			}
//		});
		// this.setOnFocusChangeListener(new FocusChangeListenerImpl());
		setDrawable();
		// setCleanDrawable(false);
	}

	// 设置删除图片
	private void setDrawable() {
		if (length() < 1) {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		} else {
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
		}
	}

	Clean clean;

	public void setClean(Clean clean) {
		this.clean = clean;
	}

	public void setCleanDrawable(boolean hasFocus) {
		if (hasFocus) {
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
		} else {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
	}

	// 处理删除事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawY();
			// Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right - 50;
			if (rect.contains(eventX, eventY)) {
				setText("");
				if (clean != null) {
					clean.clean();
				}
			}
			setDrawable();
		}
		return super.onTouchEvent(event);
	}

	// 监听判断 是否显示右边的图标
	// private class FocusChangeListenerImpl implements OnFocusChangeListener {
	// @Override
	// public void onFocusChange(View v, boolean hasFocus) {
	// if (hasFocus) {
	// boolean isVisible = getText().toString().length() >= 1;
	// setCleanDrawable(isVisible);
	// } else {
	// setCleanDrawable(false);
	// }
	// }
	//
	// }

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	public interface Clean {
		void clean();
	}
}