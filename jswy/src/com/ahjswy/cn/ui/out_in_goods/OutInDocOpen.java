package com.ahjswy.cn.ui.out_in_goods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.bean.SaleEntity;
import com.ahjswy.cn.model.CustomerThin;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.CustomerSearchAct;
import com.ahjswy.cn.ui.DepartmentSearchAct;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
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
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * 销售订单
 * 
 * @author Administrator
 *
 */
public class OutInDocOpen extends BaseActivity implements OnClickListener {
	Def_Doc doccg;
	private EditText etDiscountRatio, etSummary, etRemark;
	private Button btnDepartment, btnCustomer, btnSettleTime;
	private Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_outin);
		initView();
		initData();
		initListener();

	}

	private void initView() {
		btnDepartment = (Button) findViewById(R.id.btnDepartment);
		btnCustomer = (Button) findViewById(R.id.btnCustomer);
		btnSettleTime = (Button) findViewById(R.id.btnSettleTime);
		etDiscountRatio = (EditText) findViewById(R.id.etDiscountRatio);
		etSummary = (EditText) findViewById(R.id.etSummary);
		etRemark = (EditText) findViewById(R.id.etRemark);
		calendar = Calendar.getInstance();
	}

	private void initData() {
		doccg = ((Def_Doc) getIntent().getSerializableExtra("doc"));
		if (doccg != null) {
			btnDepartment.setTag(doccg.getDepartmentid());
			btnDepartment.setText(doccg.getDepartmentname());
			btnCustomer.setTag(doccg.getCustomerid());
			btnCustomer.setText(doccg.getCustomername());
			etDiscountRatio.setText(doccg.getDiscountratio() + "");
			if (doccg.getDeliverytime() == null) {
				return;
			}
			btnSettleTime.setTag(doccg.getSettletime());
			btnSettleTime.setText(doccg.getSettletime());
			etSummary.setText(doccg.getSummary());
			etRemark.setText(doccg.getRemark());
		} else {
			Department localDepartment = SystemState.getDepartment();
			if (localDepartment != null) {
				btnDepartment.setTag(localDepartment.getDid());
				btnDepartment.setText(localDepartment.getDname());
			}
			etDiscountRatio.setText("1.0");
			long l = Utils.getCurrentTime(false);
			btnSettleTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			btnSettleTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
		}
	}

	private void initListener() {
		btnDepartment.setOnClickListener(this);
		btnCustomer.setOnClickListener(this);
		btnSettleTime.setOnClickListener(this);
		btnSettleTime.setOnClickListener(dateClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {

		switch (menu.getItemId()) {
		case android.R.id.home:
			if (doccg != null) {
				Intent intent = new Intent();
				intent.putExtra("doc", doccg);
				setResult(RESULT_OK, intent);
			}
			finish();
			break;
		case 0:
			startInto();
			break;
		}
		return true;
	}

	private void startInto() {
		if (doccg == null) {
			doccg = new Def_Doc();
		} else {
			fillDoc();
			Intent intent = new Intent();
			intent.putExtra("doc", doccg);
			setResult(RESULT_OK, intent);
			finish();
			return;
		}
		if (!fillDoc()) {
			doccg.setBuildtime(Utils.getDataTime());
			doccg.setDeliverytime(btnSettleTime.getText().toString());
			SaleEntity entity = new SaleEntity();
			entity.setDocjson(JSONUtil.toJSONString(doccg));
			//TODO
			Intent intent = new Intent(OutInDocOpen.this, OutInDocEditActivity.class);
			intent.putExtra("entity", entity);
			intent.putExtra("ishaschanged", true);
			startActivity(intent);
			finish();
		}

	}

	/* 判断数据 输入是否正确 */
	private boolean fillDoc() {
		if (btnDepartment.getTag() == null) {
			PDH.showMessage("部门不能为空");
			return true;

		}
		if ((etDiscountRatio.getText().toString().length() == 0)
				|| (Double.parseDouble(etDiscountRatio.getText().toString()) <= 0.0D)
				|| (Double.parseDouble(etDiscountRatio.getText().toString()) > 1.0D)) {
			PDH.showMessage("整单折扣必须大于0且小于等于1");
			return true;
		}
		if (btnSettleTime.getTag().toString().isEmpty()) {
			PDH.showMessage("交货日期不能为空");
			return true;
		}
		if (TextUtils.isEmptyS(btnDepartment.getTag().toString())) {
			doccg.setDepartmentid(btnDepartment.getTag().toString());
			doccg.setDepartmentname(btnDepartment.getText().toString());
		}
		if (TextUtils.isEmptyS(btnCustomer.getText().toString())) {
			doccg.setCustomerid(btnCustomer.getTag().toString());
			doccg.setCustomername(btnCustomer.getText().toString());
		}
		// 折扣
		doccg.setDiscountratio(Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(), 2));
		// 交货日期
		if (!TextUtils.isEmpty(btnSettleTime.getText().toString())) {
			doccg.setSettletime(btnSettleTime.getText().toString());
		}
		// 备注
		doccg.setRemark(etRemark.getText().toString());
		// 摘要
		doccg.setSummary(etSummary.getText().toString());
		doccg.setBuilderid(SystemState.getUser().getId());
		doccg.setBuildername(SystemState.getUser().getName());
		return false;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDepartment:
			startActivityForResult(new Intent(this, DepartmentSearchAct.class), 1);// 部门
			break;
		case R.id.btnCustomer:
			startActivityForResult(new Intent(this, CustomerSearchAct.class), 2);// 客户
			break;
		}
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

		public void onClick(View view) {
			btn = ((Button) view);
			try {
				calendar.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse((String) btn.getText()));
				new DatePickerDialog(OutInDocOpen.this, listener, calendar.get(1), calendar.get(2), calendar.get(5))
						.show();
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_FIRST_USER) {

		} else if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				// 设置部门
				Department localDepartment = (Department) data.getSerializableExtra("department");
				btnDepartment.setText(localDepartment.getDname());
				btnDepartment.setTag(localDepartment.getDid());
				break;
			case 2:
				// 设置客户
				CustomerThin localCustomerThin = (CustomerThin) data.getSerializableExtra("customer");
				btnCustomer.setText(localCustomerThin.getName());
				btnCustomer.setTag(localCustomerThin.getId());
				break;
			}

		}

	};

	@Override
	public void setActionBarText() {
		super.setActionBarText();
		getActionBar().setTitle("销售订单");
	}
}
