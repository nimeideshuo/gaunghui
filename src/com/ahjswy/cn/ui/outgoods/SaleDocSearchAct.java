package com.ahjswy.cn.ui.outgoods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.CustomerThin;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.request.ReqStrSearchDoc;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.CustomerSearchAct;
import com.ahjswy.cn.ui.DepartmentSearchAct;
import com.ahjswy.cn.ui.DocTypeListAct;
import com.ahjswy.cn.ui.WarehouseSearchAct;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class SaleDocSearchAct extends BaseActivity implements OnClickListener {
	private Button btnCustomer;
	private Button btnDepartment;
	private Button btnDoctype;
	private Button btnEndtime;
	private Button btnStarttime;
	private Button btnWarehouse;
	private Calendar calendar;
	private CheckBox checkOnlyShowNoSettleUp;
	private ReqStrSearchDoc condition;
	private EditText etRemarkSummary;
	private EditText etShowID;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_saledocsearch);
		condition = ((ReqStrSearchDoc) getIntent().getSerializableExtra("condition"));
		initView(condition);
		calendar = Calendar.getInstance();
	}

	private void initView(ReqStrSearchDoc reqDoc) {
		btnDoctype = ((Button) findViewById(R.id.btnDoctype));
		btnDepartment = ((Button) findViewById(R.id.btnDepartment));
		btnWarehouse = ((Button) findViewById(R.id.btnWarehouse));
		btnCustomer = ((Button) findViewById(R.id.btnCustomer));
		btnStarttime = ((Button) findViewById(R.id.btnStarttime));
		btnEndtime = ((Button) findViewById(R.id.btnEndtime));
		Button btnDefaultSetting = (Button) findViewById(R.id.btnDefaultSetting);
		etRemarkSummary = ((EditText) findViewById(R.id.etRemarkSummary));
		etShowID = ((EditText) findViewById(R.id.etShowID));
		checkOnlyShowNoSettleUp = ((CheckBox) findViewById(R.id.checkBox));
		btnDoctype.setOnClickListener(this);
		btnDepartment.setOnClickListener(this);
		btnWarehouse.setOnClickListener(this);
		btnCustomer.setOnClickListener(this);
		btnDefaultSetting.setOnClickListener(this);
		btnStarttime.setOnClickListener(dateClickListener);
		btnEndtime.setOnClickListener(dateClickListener);
		btnDoctype.setText(reqDoc.getDoctypeName());
		btnDoctype.setTag(reqDoc.getDoctype());
		btnDepartment.setText(reqDoc.getDepartmentName());
		btnDepartment.setTag(reqDoc.getDepartmentID());
		btnWarehouse.setText(reqDoc.getWarehouseName());
		btnWarehouse.setTag(reqDoc.getWarehouseID());
		btnCustomer.setText(reqDoc.getCustomerName());
		btnCustomer.setTag(reqDoc.getCustomerID());
		btnStarttime
				.setText(reqDoc.getDateBeginTime().equals(SystemState.defaultTime) ? "" : reqDoc.getDateBeginTime());
		try {
			Date localDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reqDoc.getDateEndTime());
			btnEndtime.setText(
					new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Long.valueOf(localDate2.getTime())));
			if (reqDoc.getDateBeginTime().contains(SystemState.defaultTime)) {
				btnStarttime.setText("");
			} else {
				Date localDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reqDoc.getDateBeginTime());
				btnStarttime.setText(
						new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Long.valueOf(localDate1.getTime())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		etRemarkSummary.setText(reqDoc.getRemarkSummary());
		etShowID.setText(reqDoc.getShowID());
		checkOnlyShowNoSettleUp.setChecked(reqDoc.isOnlyShowNoSettleUp());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDepartment:
			startActivityForResult(new Intent().setClass(this, DepartmentSearchAct.class), 5);
			break;
		case R.id.btnWarehouse:
			startActivityForResult(new Intent().setClass(this, WarehouseSearchAct.class), 6);
			break;
		case R.id.btnDoctype:
			startActivityForResult(new Intent().setClass(this, DocTypeListAct.class), 0);
			break;
		case R.id.btnCustomer:
			startActivityForResult(new Intent().setClass(this, CustomerSearchAct.class), 4);
			break;
		case R.id.btnDefaultSetting:
			condition.setDoctype(null);
			condition.setDoctypeName("全部");
			condition.setDepartmentID(null);
			condition.setDepartmentName("全部");
			condition.setWarehouseID(null);
			condition.setWarehouseName("全部");
			condition.setCustomerID(null);
			condition.setCustomerName("全部");
			btnDoctype.setText("全部");
			btnDoctype.setTag(null);
			btnDepartment.setText("全部");
			btnDepartment.setTag(null);
			btnWarehouse.setText("全部");
			btnWarehouse.setTag(null);
			btnCustomer.setText("全部");
			btnCustomer.setTag(null);
			btnStarttime.setText("");
			btnStarttime.setTag(null);
			btnEndtime.setText(Utils.getData());
			showSuccess("恢复默认成功!");
			break;

		default:
			break;
		}
	}

	private ReqStrSearchDoc fillCondition() {
		if (btnDoctype.getTag() == null) {
			condition.setDoctype(null);
			condition.setDoctypeName("全部");
		} else {
			condition.setDoctype(btnDoctype.getTag().toString());
			condition.setDoctypeName(btnDoctype.getText().toString());
		}

		if (btnDepartment.getTag() == null) {
			condition.setDepartmentID(null);
			condition.setDepartmentName("全部");
		} else {
			condition.setDepartmentID(btnDepartment.getTag().toString());
			condition.setDepartmentName(btnDepartment.getText().toString());
		}

		if (btnWarehouse.getTag() == null) {
			condition.setWarehouseID(null);
			condition.setWarehouseName("全部");
		} else {
			condition.setWarehouseID(btnWarehouse.getTag().toString());
			condition.setWarehouseName(btnWarehouse.getText().toString());
		}

		if (btnCustomer.getTag() == null) {
			condition.setCustomerID(null);
			condition.setCustomerName("全部");
		} else {
			condition.setCustomerID(btnCustomer.getTag().toString());
			condition.setCustomerName(btnCustomer.getText().toString());
		}
		String begTime = btnStarttime.getTag() == null ? SystemState.defaultTime : btnStarttime.getTag().toString();
		condition.setDateBeginTime(begTime + " 00:00:00");
		condition.setDateEndTime(btnEndtime.getText().toString() + " 00:00:00");
		condition.setRemarkSummary(etRemarkSummary.getText().toString().toString());
		condition.setShowID(etShowID.getText().toString());
		condition.setOnlyShowNoSettleUp(checkOnlyShowNoSettleUp.isChecked());
		return condition;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				btnDoctype.setText(data.getStringExtra("doctypename"));
				btnDoctype.setTag(data.getStringExtra("doctypeid"));
				break;
			case 4:
				CustomerThin localCustomerThin = (CustomerThin) data.getSerializableExtra("customer");
				btnCustomer.setText(localCustomerThin.getName());
				btnCustomer.setTag(localCustomerThin.getId());
				break;
			case 5:
				Department localDepartment = (Department) data.getSerializableExtra("department");
				btnDepartment.setText(localDepartment.getDname());
				btnDepartment.setTag(localDepartment.getDid());
				break;
			case 6:
				Warehouse localWarehouse = (Warehouse) data.getSerializableExtra("warehouse");
				btnWarehouse.setText(localWarehouse.getName());
				btnWarehouse.setTag(localWarehouse.getId());
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, "确定").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuitem) {
		switch (menuitem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		case 0:
			fillCondition();
			Intent localIntent = new Intent();
			localIntent.putExtra("condition", condition);
			setResult(RESULT_OK, localIntent);
			finish();
			break;
		}
		return true;
	}

	private View.OnClickListener dateClickListener = new View.OnClickListener() {
		private Button btn;
		private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(1, year);
				calendar.set(2, monthOfYear);
				calendar.set(5, dayOfMonth);
				SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				btn.setText(localSimpleDateFormat.format(calendar.getTime()));
				btn.setTag(localSimpleDateFormat.format(calendar.getTime()));
			}
		};

		public void onClick(View v) {
			this.btn = ((Button) v);
			try {
				calendar.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(
						TextUtils.isEmpty(btn.getText().toString()) ? Utils.getData() : btn.getText().toString()));
				new DatePickerDialog(SaleDocSearchAct.this, this.listener, SaleDocSearchAct.this.calendar.get(1),
						SaleDocSearchAct.this.calendar.get(2), calendar.get(5)).show();
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	};

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("筛选");
	}

}
