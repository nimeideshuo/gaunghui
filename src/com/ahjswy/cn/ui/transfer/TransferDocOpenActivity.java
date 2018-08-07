package com.ahjswy.cn.ui.transfer;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.DefDocTransfer;
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
 * 调拨开单
 * 
 * @author Administrator
 *
 */
public class TransferDocOpenActivity extends BaseActivity implements OnClickListener {
	private DefDocTransfer doc;
	boolean isReadOnly;
	private Button btnDepartment;
	private Button btnInWarehouse;
	private Button btnOutWarehouse;
	private EditText etRemark;
	private EditText etSummary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dia_transfer_opendoc);
		doc = ((DefDocTransfer) getIntent().getSerializableExtra("doc"));
		initView();
		if ((this.doc != null) && (this.doc.isIsavailable())) {
			this.isReadOnly = doc.isIsposted();
			this.btnDepartment.setTag(this.doc.getDepartmentid());
			this.btnInWarehouse.setTag(this.doc.getInwarehouseid());
			this.btnOutWarehouse.setTag(this.doc.getOutwarehouseid());
			this.btnDepartment.setText(this.doc.getDepartmentname());
			this.btnInWarehouse.setText(this.doc.getInwarehousename());
			this.btnOutWarehouse.setText(this.doc.getOutwarehousename());
			this.etSummary.setText(this.doc.getSummary());
			this.etRemark.setText(this.doc.getRemark());
		} else {
			Department department = SystemState.getDepartment();
			if (department != null) {
				this.btnDepartment.setTag(department.getDid());
				this.btnDepartment.setText(department.getDname());
			}
			Warehouse warehouse = SystemState.getWarehouse();
			if (warehouse != null) {
				this.btnInWarehouse.setTag(warehouse.getId());
				this.btnInWarehouse.setText(warehouse.getName());
			}

		}
		if (isReadOnly) {
			this.btnDepartment.setBackground(this.etSummary.getBackground());
			this.btnInWarehouse.setBackground(this.etSummary.getBackground());
			this.btnOutWarehouse.setBackground(this.etSummary.getBackground());
			this.btnDepartment.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnInWarehouse.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnOutWarehouse.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.etSummary.setCursorVisible(false);
			this.etSummary.setFocusable(false);
			this.etSummary.setFocusableInTouchMode(false);
			this.etRemark.setCursorVisible(false);
			this.etRemark.setFocusable(false);
			this.etRemark.setFocusableInTouchMode(false);
		} else {
			btnDepartment.setOnClickListener(this);
			btnInWarehouse.setOnClickListener(this);
			btnOutWarehouse.setOnClickListener(this);
		}

	}

	private void initView() {
		btnDepartment = (Button) findViewById(R.id.btnDepartment);
		btnInWarehouse = (Button) findViewById(R.id.btnInWarehouse);
		btnOutWarehouse = (Button) findViewById(R.id.btnOutWarehouse);
		etRemark = (EditText) findViewById(R.id.etRemark);
		etSummary = (EditText) findViewById(R.id.etSummary);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "确定").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == 0) {
			if (!this.isReadOnly) {
				if (!TextUtils.isEmptyS(this.btnDepartment.getTag().toString())) {
					PDH.showMessage("部门不能为空");
				} else if (!TextUtils.isEmptyS(this.btnInWarehouse.getTag().toString())) {
					PDH.showMessage("调入仓库不能为空");
				} else if ((btnOutWarehouse.getTag() != null)
						&& (this.btnInWarehouse.getTag().equals(this.btnOutWarehouse.getTag()))) {
					PDH.showMessage("调出仓库不能与调入仓库相同");
				} else if (this.doc != null) {
					fillDoc();
					Intent localIntent = new Intent();
					localIntent.putExtra("doc", this.doc);
					setResult(Activity.RESULT_OK, localIntent);
					finish();
				} else {
					initDoc();
				}

			} else {
				finish();
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void initDoc() {
		PDH.show(this, new PDH.ProgressCallBack() {

			@Override
			public void action() {
				String departmentid = btnDepartment.getTag().toString();
				String warehouseid = btnInWarehouse.getTag().toString();
				String outwarehouse = null;
				if (btnOutWarehouse.getTag() != null) {
					outwarehouse = btnOutWarehouse.getTag().toString();
				}
				String str1 = new ServiceStore().str_InitDBDoc(departmentid, warehouseid, outwarehouse);
				handler.sendMessage(handler.obtainMessage(0, str1));
			}

		});
	}

	private void fillDoc() {
		if (TextUtils.isEmptyS(this.btnDepartment.getTag().toString())) {
			this.doc.setDepartmentid(this.btnDepartment.getTag().toString());
			this.doc.setDepartmentname(this.btnDepartment.getText().toString());
		} else {
			this.doc.setDepartmentid(null);
			this.doc.setDepartmentname("");
		}
		if (TextUtils.isEmptyS(this.btnInWarehouse.getTag().toString())) {
			this.doc.setInwarehouseid(this.btnInWarehouse.getTag().toString());
			this.doc.setInwarehousename(this.btnInWarehouse.getText().toString());
		} else {
			this.doc.setInwarehouseid(null);
			this.doc.setInwarehousename("");
		}

		if (btnOutWarehouse.getTag() != null) {
			this.doc.setOutwarehouseid(this.btnOutWarehouse.getTag().toString());
			this.doc.setOutwarehousename(this.btnOutWarehouse.getText().toString());
		} else {
			this.doc.setOutwarehouseid(null);
			this.doc.setOutwarehousename("");

		}
		this.doc.setRemark(this.etRemark.getText().toString());
		this.doc.setSummary(this.etSummary.getText().toString());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDepartment:
			startActivityForResult(new Intent(this, DepartmentSearchAct.class), 5);
			break;
		case R.id.btnInWarehouse:
			startActivityForResult(new Intent(this, WarehouseSearchAct.class), 6);
			break;
		case R.id.btnOutWarehouse:
			startActivityForResult(new Intent(this, WarehouseSearchAct.class), 0);
			break;

		default:
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				DocContainerEntity doccontainer = (DocContainerEntity) JSONUtil.fromJson(message,
						DocContainerEntity.class);
				doc = ((DefDocTransfer) JSONUtil.fromJson(doccontainer.getDoc(), DefDocTransfer.class));
				fillDoc();
				doccontainer.setDoc(JSONUtil.object2Json(doc));
				// TODO
				Intent intent = new Intent(TransferDocOpenActivity.this, TransferEditActivity.class);
				intent.putExtra("docContainer", doccontainer);
				intent.putExtra("ishaschaned", true);
				startActivity(intent);
				finish();
				return;

			} else {
				PDH.showFail(message);
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 0:
				Warehouse paramIntent = (Warehouse) data.getSerializableExtra("warehouse");
				this.btnOutWarehouse.setText(paramIntent.getName());
				this.btnOutWarehouse.setTag(paramIntent.getId());
				break;
			case 5:
				Department department = (Department) data.getSerializableExtra("department");
				this.btnDepartment.setText(department.getDname());
				this.btnDepartment.setTag(department.getDid());
				break;
			case 6:
				Warehouse warehouse = (Warehouse) data.getSerializableExtra("warehouse");
				this.btnInWarehouse.setText(warehouse.getName());
				this.btnInWarehouse.setTag(warehouse.getId());
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void setActionBarText() {
		if (doc != null) {
			setTitle(doc.getShowid());
		}
		setTitle("调拨开单");
	}

}
