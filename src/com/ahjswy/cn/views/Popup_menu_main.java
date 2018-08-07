package com.ahjswy.cn.views;

import com.ahjswy.cn.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class Popup_menu_main extends PopupWindow {
	Context context;
	private View mMenuView;
	OnClickListener itemsOnClick;

	public Popup_menu_main(Context context, OnClickListener itemsOnClick) {
		super(context);
		this.context = context;
		this.itemsOnClick = itemsOnClick;
		initView();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popup_menu_main, null);

		Button btnIp = (Button) mMenuView.findViewById(R.id.btnIp);
		Button btnSetting = (Button) mMenuView.findViewById(R.id.btnSetting);
		Button btnClear = (Button) mMenuView.findViewById(R.id.btnClear);
		Button btnExsit = (Button) mMenuView.findViewById(R.id.btnExsit);

		btnIp.setOnClickListener(itemsOnClick);
		btnSetting.setOnClickListener(itemsOnClick);
		btnClear.setOnClickListener(itemsOnClick);
		btnExsit.setOnClickListener(itemsOnClick);

		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(R.color.huise);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.lin_popup).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}
}
