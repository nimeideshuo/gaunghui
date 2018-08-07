package com.ahjswy.cn.popupmenu;

import com.ahjswy.cn.R;
import com.ahjswy.cn.ui.inpurchase.InpurchaseEditActivity;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class InpurchaseEditMenuPopup extends PopupWindow implements OnClickListener {
	InpurchaseEditActivity activity;
	private View root;

	public InpurchaseEditMenuPopup(InpurchaseEditActivity activity) {
		this.activity = activity;
		root = LayoutInflater.from(activity).inflate(R.layout.popup_menu_indoc, null);
		setContentView(root);
		initView();
		setAnimationStyle(R.style.buttom_in_out);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int i = localDisplayMetrics.widthPixels;
		int j = localDisplayMetrics.heightPixels;
		setWidth(i);
		// TODO 设置 采购入库 editActivity中的menu菜单的高度
		// if ((this.doc.isIsavailable()) && (this.doc.isIsposted())) {
		// setHeight(j / 11);
		// show(false);
		// return;
		// }
		setHeight(j / 6);
	}

	private void initView() {
		Button btnCheck = (Button) root.findViewById(R.id.btnCheck);
		Button btnCheckPrint = (Button) root.findViewById(R.id.btnCheckPrint);
		Button btnDocProperty = (Button) root.findViewById(R.id.btnDocProperty);
		Button btnDelete = (Button) root.findViewById(R.id.btnDelete);
		Button btnPay = (Button) root.findViewById(R.id.btnPay);
		Button btnSave = (Button) root.findViewById(R.id.btnSave);
		Button btnDoc = (Button) root.findViewById(R.id.btnDoc);
		LinearLayout ll_Delete = (LinearLayout) root.findViewById(R.id.ll_Delete);
		btnCheck.setOnClickListener(this);
		btnCheckPrint.setOnClickListener(this);
		btnDocProperty.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnPay.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnDoc.setOnClickListener(this);
		ll_Delete.setVisibility(View.GONE);
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
		case R.id.btnDoc:
			this.activity.dingDoc();
			break;
		default:
			break;
		}
	}

}
