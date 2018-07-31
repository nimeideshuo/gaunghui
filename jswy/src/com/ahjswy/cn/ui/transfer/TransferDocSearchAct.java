package com.ahjswy.cn.ui.transfer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.request.ReqStrSearchDoc;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.DepartmentSearchAct;
import com.ahjswy.cn.ui.WarehouseSearchAct;
import com.ahjswy.cn.utils.PDH;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class TransferDocSearchAct extends BaseActivity implements OnClickListener {
	private Calendar calendar;
	private Button btnDepartment;
	private Button btnEndtime;
	private Button btnInWarehouse;
	private Button btnOutWarehouse;
	private Button btnStarttime;
	private ReqStrSearchDoc condition;
	private EditText etRemarkSummary;
	private EditText etShowID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_transferdocsearch);
		condition = ((ReqStrSearchDoc) getIntent().getSerializableExtra("condition"));
		initView(condition);
		calendar = Calendar.getInstance();
	}

	private void initView(ReqStrSearchDoc condition) {
		this.btnDepartment = ((Button) findViewById(R.id.btnDepartment));
		this.btnInWarehouse = ((Button) findViewById(R.id.btnInWarehouse));
		this.btnOutWarehouse = ((Button) findViewById(R.id.btnOutWarehouse));
		this.btnStarttime = ((Button) findViewById(R.id.btnStarttime));
		this.btnEndtime = ((Button) findViewById(R.id.btnEndtime));
		this.etRemarkSummary = ((EditText) findViewById(R.id.etRemarkSummary));
		this.etShowID = ((EditText) findViewById(R.id.etShowID));
		this.btnDepartment.setOnClickListener(this);
		this.btnInWarehouse.setOnClickListener(this);
		this.btnOutWarehouse.setOnClickListener(this);
		this.btnStarttime.setOnClickListener(dateClickListener);
		this.btnEndtime.setOnClickListener(dateClickListener);
		this.btnDepartment.setText(condition.getDepartmentName());
		this.btnDepartment.setTag(condition.getDepartmentID());
		this.btnInWarehouse.setText(condition.getInWarehouseName());
		this.btnInWarehouse.setTag(condition.getInWarehouseID());
		this.btnOutWarehouse.setText(condition.getOutWarehouseName());
		this.btnOutWarehouse.setTag(condition.getOutWarehouseID());
		this.etRemarkSummary.setText(condition.getRemarkSummary());
		this.etShowID.setText(condition.getShowID());
		try {
			Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(condition.getDateBeginTime());
			Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(condition.getDateEndTime());
			this.btnStarttime.setText(
					new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Long.valueOf(startDate.getTime())));
			this.btnEndtime
					.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Long.valueOf(endDate.getTime())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDepartment:
			startActivityForResult(new Intent().setClass(this, DepartmentSearchAct.class), 5);
			break;
		case R.id.btnInWarehouse:
			startActivityForResult(new Intent().setClass(this, WarehouseSearchAct.class), 3);
			break;
		case R.id.btnOutWarehouse:
			startActivityForResult(new Intent().setClass(this, WarehouseSearchAct.class), 6);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "确定").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {

		if (menu.getItemId() == 0) {
			if ((btnInWarehouse.getTag() != null) && (btnOutWarehouse.getTag() != null)
					&& (this.btnInWarehouse.getTag().toString().equals(this.btnOutWarehouse.getTag().toString()))) {
				PDH.showMessage("调入仓库与调出仓库不能相同");
			} else {
				fillCondition();
				Intent localIntent = new Intent();
				localIntent.putExtra("condition", this.condition);
				setResult(Activity.RESULT_OK, localIntent);
				finish();
			}
		}

		return super.onOptionsItemSelected(menu);
	}

	private ReqStrSearchDoc fillCondition() {
		if (btnDepartment.getTag() != null) {
			this.condition.setDepartmentID(this.btnDepartment.getTag().toString());
			this.condition.setDepartmentName(this.btnDepartment.getText().toString());
		} else {
			this.condition.setDepartmentID(null);
			this.condition.setDepartmentName("全部");
		}
		if (btnInWarehouse.getTag() != null) {
			this.condition.setInWarehouseID(this.btnInWarehouse.getTag().toString());
			this.condition.setInWarehouseName(this.btnInWarehouse.getText().toString());
		} else {
			this.condition.setInWarehouseID(null);
			this.condition.setInWarehouseName("全部");
		}
		if (btnOutWarehouse.getTag() != null) {
			this.condition.setOutWarehouseID(this.btnOutWarehouse.getTag().toString());
			this.condition.setOutWarehouseName(this.btnOutWarehouse.getText().toString());
		} else {
			this.condition.setOutWarehouseID(null);
			this.condition.setOutWarehouseName("全部");
		}
		this.condition.setDateBeginTime(this.btnStarttime.getText() + " 00:00:00");
		this.condition.setDateEndTime(this.btnEndtime.getText() + " 00:00:00");
		this.condition.setRemarkSummary(this.etRemarkSummary.getText().toString());
		this.condition.setShowID(this.etShowID.getText().toString());
		return this.condition;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 3:
				Warehouse inWarehouse = (Warehouse) data.getSerializableExtra("warehouse");
				this.btnInWarehouse.setText(inWarehouse.getName());
				this.btnInWarehouse.setTag(inWarehouse.getId());
				break;
			case 5:
				Department department = (Department) data.getSerializableExtra("department");
				this.btnDepartment.setText(department.getDname());
				this.btnDepartment.setTag(department.getDid());
				return;
			case 6:
				// TODO
				Warehouse outWarehouse = (Warehouse) data.getSerializableExtra("warehouse");
				this.btnOutWarehouse.setText(outWarehouse.getName());
				this.btnOutWarehouse.setTag(outWarehouse.getId());
				return;

			default:
				break;
			}
		}

	}

	OnClickListener dateClickListener = new OnClickListener() {
		private Button btn;
		private OnDateSetListener listener = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(1, year);
				calendar.set(2, monthOfYear);
				calendar.set(5, dayOfMonth);
				SimpleDateFormat datePicker = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				btn.setText(datePicker.format(calendar.getTime()));
			}
		};

		@Override
		public void onClick(View v) {
			this.btn = ((Button) v);
			try {
				calendar.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(this.btn.getText().toString()));
				new DatePickerDialog(TransferDocSearchAct.this, listener, calendar.get(1), calendar.get(2),
						calendar.get(5)).show();
				return;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	};

	public void setActionBarText() {
		setTitle("筛选");
	}

}
