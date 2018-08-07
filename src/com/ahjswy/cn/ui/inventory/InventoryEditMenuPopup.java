package com.ahjswy.cn.ui.inventory;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocPD;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class InventoryEditMenuPopup extends PopupWindow implements OnClickListener {
	InventoryEditActivity activity;
	private View root;

	public InventoryEditMenuPopup(InventoryEditActivity activity) {
		this.activity = activity;
		root = View.inflate(activity, R.layout.popup_menu_inventoryedit, null);
		setContentView(root);
		doc = activity.getDoc();
		init();
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int width = localDisplayMetrics.widthPixels;
		int height = localDisplayMetrics.heightPixels;
		setWidth(width);
		if ((this.doc.isIsavailable()) && (this.doc.isIsposted())) {
			setHeight(height / 11);
			show();
		} else {
			setHeight(height / 6);
			show();
		}

	}

	Button btnCheck, btnDocProperty;
	LinearLayout btnCheckPrint, btnSave, btnDelete;
	private DefDocPD doc;

	private void init() {
		this.btnCheck = ((Button) this.root.findViewById(R.id.btnCheck));
		this.btnCheckPrint = ((LinearLayout) this.root.findViewById(R.id.btnCheckPrint));
		this.btnDocProperty = ((Button) this.root.findViewById(R.id.btnDocProperty));
		this.btnDelete = ((LinearLayout) this.root.findViewById(R.id.btnDelete));
		this.btnSave = ((LinearLayout) this.root.findViewById(R.id.btnSave));
		this.btnCheck.setOnClickListener(this);
		this.btnCheckPrint.setOnClickListener(this);
		this.btnDocProperty.setOnClickListener(this);
		this.btnSave.setOnClickListener(this);
		this.btnDelete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dismiss();
		LayoutParams attributes = activity.getWindow().getAttributes();
		attributes.alpha = 1.0f;
		activity.getWindow().setAttributes(attributes);
		switch (v.getId()) {
		case R.id.btnCheck:
			activity.check(false);
			break;
		case R.id.btnCheckPrint:
			activity.check(true);
			break;
		case R.id.btnDocProperty:
			this.activity.docProperty();
			break;
		case R.id.btnDelete:
			this.activity.delete();
			break;
		case R.id.btnSave:
			this.activity.save();
			break;
		}
	}

	private void show() {
		if ((this.doc.isIsavailable()) && (this.doc.isIsposted())) {
			this.root.findViewById(R.id.linear_top).setVisibility(8);
			this.btnSave.setVisibility(View.GONE);
			this.btnDelete.setVisibility(View.GONE);
		} else {
			this.root.findViewById(R.id.linear_top).setVisibility(0);
			this.btnSave.setVisibility(View.VISIBLE);
			this.btnDelete.setVisibility(View.VISIBLE);
		}
		this.btnDelete.setVisibility(View.GONE);
	}
}
