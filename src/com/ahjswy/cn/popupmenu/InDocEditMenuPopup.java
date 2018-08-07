package com.ahjswy.cn.popupmenu;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDoc;
import com.ahjswy.cn.ui.ingoods.InDocEditActivity;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class InDocEditMenuPopup extends PopupWindow implements OnClickListener {
	private InDocEditActivity activity;
	private View root;
	OnClickListener clicklistener;
	private DefDoc doc;
	private LinearLayout btnPay;
	private LinearLayout btnSave;
	private LinearLayout btnDelete;

	public InDocEditMenuPopup(InDocEditActivity paramInDocEditActivity) {
		this.activity = paramInDocEditActivity;
		doc = paramInDocEditActivity.getDoc();
		root = LayoutInflater.from(paramInDocEditActivity).inflate(R.layout.popup_menu_indocedit, null);
		setContentView(root);
		initView();
		setAnimationStyle(R.style.buttom_in_out);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		paramInDocEditActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int i = localDisplayMetrics.widthPixels;
		int j = localDisplayMetrics.heightPixels;
		setWidth(i);
		if ((doc.isIsavailable()) && (doc.isIsposted())) {
			setHeight(j / 11);
			show(false);
			return;
		}
		setHeight(j / 6);
		show(true);
	}

	private void initView() {
		Button btnCheck = (Button) root.findViewById(R.id.btnCheck);
		LinearLayout btnCheckPrint = (LinearLayout) root.findViewById(R.id.btnCheckPrint);
		LinearLayout btnCustomerHistory = (LinearLayout) root.findViewById(R.id.btnCustomerHistory);
		Button btnDocProperty = (Button) root.findViewById(R.id.btnDocProperty);
		btnDelete = (LinearLayout) root.findViewById(R.id.btnDelete);
		btnPay = (LinearLayout) root.findViewById(R.id.btnPay);
		btnSave = (LinearLayout) root.findViewById(R.id.btnSave);
		btnCheck.setOnClickListener(this);
		btnCheckPrint.setOnClickListener(this);
		btnCustomerHistory.setOnClickListener(this);
		btnDocProperty.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnPay.setOnClickListener(this);
		btnSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dismiss();
		WindowManager.LayoutParams localLayoutParams = this.activity.getWindow().getAttributes();
		localLayoutParams.alpha = 1.0F;
		this.activity.getWindow().setAttributes(localLayoutParams);
		switch (v.getId()) {
		case R.id.btnCheck:
			// 过账
			activity.check(false);
			break;
		case R.id.btnCheckPrint:
			// 过账打印
			this.activity.check(true);
			break;
		case R.id.btnCustomerHistory:
			// 客史
			this.activity.getCustomerHistory();
			break;
		case R.id.btnDocProperty:
			// 属性
			this.activity.docProperty();
			break;
		case R.id.btnDelete:
			// 删除
			this.activity.delete();
			break;
		case R.id.btnPay:
			// 收款
			this.activity.pay();
			break;
		case R.id.btnSave:
			// 保存
			this.activity.save();
			break;
		default:
			break;
		}
	}

	private void show(boolean paramBoolean) {
		if (paramBoolean) {
			doc = activity.getDoc();
		}
		if ((doc.isIsavailable()) && (doc.isIsposted())) {
			root.findViewById(R.id.linear_top).setVisibility(View.GONE);
			btnSave.setVisibility(View.GONE);
			// btnDelete.setVisibility(View.GONE);
		} else {
			root.findViewById(R.id.linear_top).setVisibility(View.VISIBLE);
			btnSave.setVisibility(View.VISIBLE);
			// btnDelete.setVisibility(View.VISIBLE);
		}
		btnDelete.setVisibility(View.GONE);
	}
}
