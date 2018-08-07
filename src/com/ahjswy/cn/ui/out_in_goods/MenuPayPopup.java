package com.ahjswy.cn.ui.out_in_goods;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * 付款PopupWindow弹出菜单
 * 
 * @author Administrator
 *
 */
public class MenuPayPopup extends PopupWindow implements OnClickListener {
	private View root;
	private LinearLayout btnSave;
	private LinearLayout btnDelete;
	Context context;
	Activity activity;

	public MenuPayPopup(Activity activity) {
		this.activity = activity;
		root = LayoutInflater.from(activity).inflate(R.layout.popup_menu_outin, null);
		setContentView(root);
		initView();
		setAnimationStyle(R.style.buttom_in_out);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int i = localDisplayMetrics.widthPixels;
		int j = localDisplayMetrics.heightPixels;
		// 设置menu宽度
		setWidth(i);
		// 设置menu高度
		setHeight(j / 6);
	}

	private void initView() {
		ap = new AccountPreference();
		Button btnCheck = (Button) root.findViewById(R.id.btnCheck);
		LinearLayout btnCheckPrint = (LinearLayout) root.findViewById(R.id.btnCheckPrint);
		Button btnDocProperty = (Button) root.findViewById(R.id.btnDocProperty);
		btnDelete = (LinearLayout) root.findViewById(R.id.btnDelete);
		// btnPay = (LinearLayout) root.findViewById(R.id.btnPay);
		btnSave = (LinearLayout) root.findViewById(R.id.btnSave);
		LinearLayout btnBluePrint = (LinearLayout) root.findViewById(R.id.btnBluePrint);
		btnCheck.setOnClickListener(this);
		btnCheckPrint.setOnClickListener(this);
		btnDocProperty.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnBluePrint.setOnClickListener(this);
		btnDelete.setVisibility(View.GONE);
		boolean bluetoothPrintIsShow = Boolean.parseBoolean(ap.getValue("bluetoothPrintIsShow", "false"));
		if (!bluetoothPrintIsShow) {
			btnBluePrint.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		dismiss();
		WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
		localLayoutParams.alpha = 1.0F;
		activity.getWindow().setAttributes(localLayoutParams);
		if (menuPopup != null) {

			switch (v.getId()) {
			case R.id.btnCheck:
				/* PopupWindow审核回调 */
				menuPopup.check(false);
				break;
			case R.id.btnCheckPrint:
				/* PopupWindow审核打印回调 */
				menuPopup.check(true);
				break;
			case R.id.btnDocProperty:
				/* PopupWindow属性回调 */
				menuPopup.docProperty();
				break;
			case R.id.btnDelete:
				/* PopupWindow删除回调 */
				menuPopup.delete();
				break;
			// case R.id.btnPay:
			// /* PopupWindow付款回调 */
			// menuPopup.pay();
			// break;
			case R.id.btnSave:
				/* PopupWindow保存回调 */
				menuPopup.save();
				break;
			case R.id.btnBluePrint:
				/* PopupWindow保存回调 */
				menuPopup.blueDevicePrint();
				break;

			}
		}
	}

	EditMenuPopupListener menuPopup;
	private AccountPreference ap;

	public void setMenuPopup(EditMenuPopupListener menuPopup) {
		this.menuPopup = menuPopup;
	}

	public EditMenuPopupListener getMenuPopup() {
		return menuPopup;
	}

	public interface EditMenuPopupListener {
		/**
		 * PopupWindow过账回调
		 * 
		 * @param check
		 */
		void check(boolean check);

		/**
		 * PopupWindow保存回调
		 */
		void save();

		/**
		 * PopupWindow删除回调
		 */
		void delete();

		/**
		 * PopupWindow属性回调
		 */
		void docProperty();

		/**
		 * PopupWindow属性回调
		 */
		void blueDevicePrint();

	}
}
