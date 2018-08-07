package com.ahjswy.cn.ui.inventory;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.DefDocPD;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.DocContainerEntity;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.DepartmentSearchAct;
import com.ahjswy.cn.ui.WarehouseSearchAct;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 盘点开单
 * 
 * @author Administrator
 *
 */
public class InventoryDocOpenActivity extends BaseActivity implements OnClickListener {

	private EditText etRemark;
	private EditText etSummary;
	private Button btnDepartment;
	private Button btnWarehouse;
	DefDocPD doc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dia_inventory_opendoc);
		initView();
		initData();
	}

	private void initView() {
		btnDepartment = (Button) findViewById(R.id.btnDepartment);
		btnWarehouse = (Button) findViewById(R.id.btnWarehouse);
		etRemark = (EditText) findViewById(R.id.etRemark);
		etSummary = (EditText) findViewById(R.id.etSummary);
		btnDepartment.setOnClickListener(this);
		btnWarehouse.setOnClickListener(this);
	}

	private int itemcount;
	private boolean isReadOnly;

	private void initData() {
		this.doc = ((DefDocPD) getIntent().getSerializableExtra("doc"));
		this.itemcount = getIntent().getIntExtra("itemcount", 0);
		this.isReadOnly = (this.doc == null || !this.doc.isIsavailable() || !this.doc.isIsposted() ? false : true);
		if (this.doc != null) {
			this.btnDepartment.setTag(this.doc.getDepartmentid());
			this.btnWarehouse.setTag(this.doc.getWarehouseid());
			this.btnDepartment.setText(this.doc.getDepartmentname());
			this.btnWarehouse.setText(this.doc.getWarehousename());
			this.etSummary.setText(this.doc.getSummary());
			this.etRemark.setText(this.doc.getRemark());
		} else {
			Department localDepartment = SystemState.getDepartment();
			if (localDepartment != null) {
				this.btnDepartment.setTag(localDepartment.getDid());
				this.btnDepartment.setText(localDepartment.getDname());
			}
			// 设置仓库
			Warehouse localWarehouse = SystemState.getWarehouse();
			if (localWarehouse != null) {
				this.btnWarehouse.setTag(localWarehouse.getId().toString());
				this.btnWarehouse.setText(localWarehouse.getName());
			}
		}
		if (isReadOnly) {
			this.btnDepartment.setBackground(this.etSummary.getBackground());
			this.btnWarehouse.setBackground(this.etSummary.getBackground());
			this.btnDepartment.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnWarehouse.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.etSummary.setCursorVisible(false);
			this.etSummary.setFocusable(false);
			this.etSummary.setFocusableInTouchMode(false);
			this.etRemark.setCursorVisible(false);
			this.etRemark.setFocusable(false);
			this.etRemark.setFocusableInTouchMode(false);
			btnWarehouse.setClickable(false);
			btnWarehouse.setEnabled(false);
			btnDepartment.setClickable(false);
			btnDepartment.setEnabled(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		if ((this.doc == null) || (!this.doc.isIsavailable()) || (!this.doc.isIsposted())) {
			paramMenu.add(0, 0, 0, "确定").setShowAsAction(2);
		}
		return super.onCreateOptionsMenu(paramMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
		case 0:
			if (!TextUtils.isEmptyS(this.btnDepartment.getTag().toString())) {
				PDH.showMessage("部门不能为空");
			} else if (!TextUtils.isEmptyS(this.btnWarehouse.getTag().toString())) {
				PDH.showMessage("仓库不能为空");
			} else if (doc != null) {
				fillDoc();
				Intent intent = new Intent();
				intent.putExtra("doc", this.doc);
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else {
				initDoc();
			}

			break;
		}
		return super.onOptionsItemSelected(menu);
	}

	/**
	 * 跳转到盘点开单
	 */
	private void fillDoc() {
		if (TextUtils.isEmptyS(this.btnDepartment.getTag().toString())) {
			doc.setDepartmentid(btnDepartment.getTag().toString());
			doc.setDepartmentname(btnDepartment.getText().toString());
		} else {
			this.doc.setDepartmentid(null);
			this.doc.setDepartmentname("");

		}
		if (TextUtils.isEmptyS(btnWarehouse.getTag().toString())) {
			doc.setWarehouseid(btnWarehouse.getTag().toString());
			doc.setWarehousename(btnWarehouse.getText().toString());
		} else {
			this.doc.setWarehouseid(null);
			this.doc.setWarehousename("");
		}
		this.doc.setRemark(this.etRemark.getText().toString());
		this.doc.setSummary(this.etSummary.getText().toString());
	}

	private void initDoc() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				String str = new ServiceStore().str_InitPDDoc(btnDepartment.getTag().toString(),
						btnWarehouse.getTag().toString());
				handler.sendMessage(handler.obtainMessage(0, str));
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String str = msg.obj.toString();
			if (RequestHelper.isSuccess(str)) {
				DocContainerEntity docent = (DocContainerEntity) JSONUtil.fromJson(str, DocContainerEntity.class);
				doc = ((DefDocPD) JSONUtil.fromJson(docent.getDoc(), DefDocPD.class));
				fillDoc();
				docent.setDoc(JSONUtil.toJSONString(doc));
				Intent localIntent = new Intent();
				localIntent.setClass(InventoryDocOpenActivity.this, InventoryEditActivity.class);
				localIntent.putExtra("docContainer", docent);
				localIntent.putExtra("ishaschanged", true);
				startActivity(localIntent);
				finish();
				return;
			}
			PDH.showFail(str);
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDepartment:
			startActivityForResult(new Intent(this, DepartmentSearchAct.class), 1);
			break;
		case R.id.btnWarehouse:
			if (this.itemcount == 0) {
				startActivityForResult(new Intent(this, WarehouseSearchAct.class), 2);
				return;
			}
			PDH.showMessage("单据中已存在盘点商品，不能更改仓库");
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 1:
				Department department = (Department) data.getExtras().get("department");
				btnDepartment.setText(department.getDname());
				btnDepartment.setTag(department.getDid());
				break;
			case 2:
				Warehouse warehouse = (Warehouse) data.getExtras().get("warehouse");
				this.btnWarehouse.setText(warehouse.getName());
				this.btnWarehouse.setTag(warehouse.getId());
				break;
			}
		}
	}

	@Override
	public void setActionBarText() {
		super.setActionBarText();
		if (this.doc != null) {
			setTitle(this.doc.getShowid());
		} else {
			setTitle("盘点开单");
		}

	}
}
