package com.ahjswy.cn.ui.inventory;

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

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * 我的盘点 筛选
 * 
 * @author Administrator
 *
 */
public class InventoryDocSearchAct extends BaseActivity implements OnClickListener {
	private ReqStrSearchDoc condition;
	private Button btnDepartment;
	private Button btnEndtime;
	private Button btnStarttime;
	private Button btnWarehouse;
	private Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_inventorydocsearch);
		condition = ((ReqStrSearchDoc) getIntent().getSerializableExtra("condition"));
		initView(condition);
		this.calendar = Calendar.getInstance();
	}

	EditText etRemarkSummary;
	EditText etShowID;

	private void initView(ReqStrSearchDoc condition) {
		this.btnDepartment = ((Button) findViewById(R.id.btnDepartment));
		this.btnWarehouse = ((Button) findViewById(R.id.btnWarehouse));
		this.btnStarttime = ((Button) findViewById(R.id.btnStarttime));
		this.btnEndtime = ((Button) findViewById(R.id.btnEndtime));
		this.etRemarkSummary = ((EditText) findViewById(R.id.etRemarkSummary));
		this.etShowID = ((EditText) findViewById(R.id.etShowID));
		this.btnDepartment.setOnClickListener(this);
		this.btnWarehouse.setOnClickListener(this);
		this.btnStarttime.setOnClickListener(this.dateClickListener);
		this.btnEndtime.setOnClickListener(this.dateClickListener);
		this.btnDepartment.setText(condition.getDepartmentName());
		this.btnDepartment.setTag(condition.getDepartmentID());
		this.btnWarehouse.setText(condition.getWarehouseName());
		this.btnWarehouse.setTag(condition.getWarehouseID());
		try {
			Date localDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(condition.getDateBeginTime());
			Date localDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(condition.getDateEndTime());
			this.btnStarttime.setText(
					new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Long.valueOf(localDate1.getTime())));
			this.btnEndtime.setText(
					new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Long.valueOf(localDate2.getTime())));
			this.etRemarkSummary.setText(condition.getRemarkSummary());
			this.etShowID.setText(condition.getShowID());
		} catch (Exception e) {
			e.printStackTrace();
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
			fillCondition();
			Intent localIntent = new Intent();
			localIntent.putExtra("condition", this.condition);
			setResult(Activity.RESULT_OK, localIntent);
			finish();
		}
		return super.onOptionsItemSelected(menu);
	}

	private void fillCondition() {
		if (btnDepartment.getTag() != null) {
			this.condition.setDepartmentID(this.btnDepartment.getTag().toString());
			this.condition.setDepartmentName(this.btnDepartment.getText().toString());
		}
		if (btnWarehouse.getTag() != null) {
			this.condition.setWarehouseID(this.btnWarehouse.getTag().toString());
			this.condition.setWarehouseName(this.btnWarehouse.getText().toString());
		}
		this.condition.setDateBeginTime(this.btnStarttime.getText().toString() + " 00:00:00");
		this.condition.setDateEndTime(this.btnEndtime.getText().toString() + " 00:00:00");
		this.condition.setRemarkSummary(this.etRemarkSummary.getText().toString());
		this.condition.setShowID(this.etShowID.getText().toString());

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
		default:
			// startActivityForResult(new Intent().setClass(this,
			// DocTypeListAct.class), 0);
			break;

		}
	}

	View.OnClickListener dateClickListener = new View.OnClickListener() {

		private Button btn;

		@Override
		public void onClick(View v) {
			btn = ((Button) v);
			try {
				calendar.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(btn.getText().toString()));
				new DatePickerDialog(InventoryDocSearchAct.this, listener, calendar.get(1), calendar.get(2),
						calendar.get(5)).show();

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(1, year);
				calendar.set(2, monthOfYear);
				calendar.set(5, dayOfMonth);
				SimpleDateFormat datePicker = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				btn.setText(datePicker.format(calendar.getTime()));
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 5:
				Department department = (Department) data.getSerializableExtra("department");
				this.btnDepartment.setText(department.getDname());
				this.btnDepartment.setTag(department.getDid());
				return;
			case 6:
				Warehouse warehouse = (Warehouse) data.getSerializableExtra("warehouse");
				this.btnWarehouse.setText(warehouse.getName());
				this.btnWarehouse.setTag(warehouse.getId());
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void setActionBarText() {
		setTitle("筛选");
	}
}
