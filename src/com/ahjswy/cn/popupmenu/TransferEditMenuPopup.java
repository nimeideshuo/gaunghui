package com.ahjswy.cn.popupmenu;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocTransfer;
import com.ahjswy.cn.ui.transfer.TransferEditActivity;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class TransferEditMenuPopup extends PopupWindow implements OnClickListener {
	private TransferEditActivity activity;
	private Button btnCheck;
	private Button btnCheckPrint;
	private LinearLayout btnDelete;
	private Button btnDocProperty;
	private Button btnSave;
	private DefDocTransfer doc;
	private View root;

	public TransferEditMenuPopup(TransferEditActivity activity) {
		this.activity = activity;
		this.root = LayoutInflater.from(activity).inflate(R.layout.popup_menu_transferedit, null);
		setContentView(this.root);
		init();
		this.doc = activity.getDoc();
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int i = localDisplayMetrics.widthPixels;
		int j = localDisplayMetrics.heightPixels;
		setWidth(i);
		if ((this.doc.isIsavailable()) && (this.doc.isIsposted())) {
			setHeight(j / 11);
		} else {
			setHeight(j / 6);
		}
		show();
	}

	private void init() {
		btnCheck = (Button) root.findViewById(R.id.btnCheck);
		btnCheckPrint = (Button) root.findViewById(R.id.btnCheckPrint);
		btnDocProperty = (Button) root.findViewById(R.id.btnDocProperty);
		btnDelete = (LinearLayout) root.findViewById(R.id.btnDelete);
		btnSave = (Button) root.findViewById(R.id.btnSave);
		btnCheck.setOnClickListener(this);
		btnCheckPrint.setOnClickListener(this);
		btnDocProperty.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnSave.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		dismiss();
		WindowManager.LayoutParams params = this.activity.getWindow().getAttributes();
		params.alpha = 1;
		this.activity.getWindow().setAttributes(params);
		switch (v.getId()) {
		case R.id.btnCheck:
			activity.check(false);
			break;
		case R.id.btnCheckPrint:
			activity.check(true);
			break;
		case R.id.btnDocProperty:
			activity.docProperty();
			break;
		case R.id.btnDelete:
			activity.delete();
			break;
		case R.id.btnSave:
			activity.save();
			break;

		default:
			break;
		}
	}

	private void show() {
		if ((this.doc.isIsavailable()) && (this.doc.isIsposted())) {
			this.root.findViewById(R.id.linear_top).setVisibility(View.GONE);
			this.btnSave.setVisibility(View.GONE);
			// this.btnDelete.setVisibility(View.GONE);
		} else {
			this.root.findViewById(R.id.linear_top).setVisibility(View.VISIBLE);
			this.btnSave.setVisibility(View.VISIBLE);
			// this.btnDelete.setVisibility(View.VISIBLE);
		}
		this.btnDelete.setVisibility(View.GONE);
	}
}
