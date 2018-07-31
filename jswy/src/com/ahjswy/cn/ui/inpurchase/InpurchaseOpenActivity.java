package com.ahjswy.cn.ui.inpurchase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.bean.PurchaseEntity;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.SupplierThin;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.service.ServicePayType;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.DepartmentSearchAct;
import com.ahjswy.cn.ui.SupplerInpurchaseAct;
import com.ahjswy.cn.ui.WarehouseSearchAct;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/*
 * 采购单
 */
public class InpurchaseOpenActivity extends BaseActivity implements OnClickListener {
	// 部门
	private Button btnDepartment;
	// 仓库
	private Button btnWarehouse;
	// 供应商
	private Button btnSuppler;
	// 整单折扣
	private EditText etDiscountRatio;
	// 交货日期
	private Button btnSettleTime;
	// 结算日期
	private Button btnDeliveryTime;
	// 摘要
	private EditText etSummary;
	// 备注
	private EditText etRemark;
	private Calendar calendar;
	private Def_Doc doccg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_inpurchaseopen);
		intview();
		initdate();
		initListener();
	}

	private void intview() {
		// 布局初始化
		btnDepartment = (Button) findViewById(R.id.btnDepartment);
		btnWarehouse = (Button) findViewById(R.id.btnWarehouse);
		btnSuppler = (Button) findViewById(R.id.btnSuppler);
		etDiscountRatio = (EditText) findViewById(R.id.etDiscountRatio);
		btnSettleTime = (Button) findViewById(R.id.btnSettleTime);
		btnDeliveryTime = (Button) findViewById(R.id.btnDeliveryTime);
		etSummary = (EditText) findViewById(R.id.etSummary);
		etRemark = (EditText) findViewById(R.id.etRemark);
		calendar = Calendar.getInstance();
	}

	private void initdate() {
		doccg = ((Def_Doc) getIntent().getSerializableExtra("doc"));
		if (doccg != null) {
			btnDepartment.setTag(doccg.getDepartmentid());
			btnDepartment.setText(doccg.getDepartmentname());
			btnWarehouse.setTag(doccg.getWarehouseid());
			btnWarehouse.setText(doccg.getWarehousename());
			btnSuppler.setTag(doccg.getCustomerid());
			btnSuppler.setText(doccg.getCustomername());
			etDiscountRatio.setText(doccg.getDiscountratio() + "");
			if (doccg.getDeliverytime() == null) {
				return;
			}
			this.btnDeliveryTime.setTag(doccg.getDeliverytime());
			this.btnDeliveryTime.setText(Utils.formatDate(doccg.getDeliverytime(), "yyyy-MM-dd"));
			if (doccg.getSettletime() == null) {
				return;
			}

			this.btnSettleTime.setTag(doccg.getSettletime());
			this.btnSettleTime.setText(Utils.formatDate(doccg.getSettletime(), "yyyy-MM-dd"));

			this.etSummary.setText(doccg.getSummary());
			this.etRemark.setText(doccg.getRemark());
		} else {
			// 设置部门
			Department defaultDepartment = SystemState.getDepartment();
			if (defaultDepartment != null) {
				this.btnDepartment.setTag(defaultDepartment.getDid());
				this.btnDepartment.setText(defaultDepartment.getDname());
			}
			// 设置仓库
			Warehouse defaultWarehouse = SystemState.getWarehouse();
			if (defaultWarehouse != null) {
				this.btnWarehouse.setTag(defaultWarehouse.getId().toString());
				this.btnWarehouse.setText(defaultWarehouse.getName());
			}
			this.etDiscountRatio.setText("1.0");
			long l = Utils.getCurrentTime(false);
			this.btnDeliveryTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			this.btnDeliveryTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
			this.btnSettleTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			this.btnSettleTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
		}
	}

	private void initListener() {
		// 布局监听
		btnDepartment.setOnClickListener(this);
		btnWarehouse.setOnClickListener(this);
		btnSuppler.setOnClickListener(this);
		btnSettleTime.setOnClickListener(dateClickListener);
		btnDeliveryTime.setOnClickListener(dateClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			if (doccg != null) {
				Intent intent = new Intent();
				setResult(RESULT_FIRST_USER, intent);
			}
			finish();
			break;
		case 0:
			intentTo();
			break;
		}

		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnDepartment:
			startActivityForResult(new Intent(this, DepartmentSearchAct.class), 5);// 部门
			break;
		case R.id.btnWarehouse:
			startActivityForResult(new Intent(this, WarehouseSearchAct.class), 6);// 仓库
			break;
		case R.id.btnSuppler:
			startActivityForResult(new Intent(this, SupplerInpurchaseAct.class), 3);// 供应商
			break;
		default:
			break;
		}
	}

	private View.OnClickListener dateClickListener = new View.OnClickListener() {
		private Button btn;
		private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				InpurchaseOpenActivity.this.calendar.set(1, year);
				InpurchaseOpenActivity.this.calendar.set(2, monthOfYear);
				InpurchaseOpenActivity.this.calendar.set(5, dayOfMonth);
				SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				btn.setText(localSimpleDateFormat.format(calendar.getTime()));
				btn.setTag(dateformat.format(calendar.getTime()));
			}
		};

		public void onClick(View view) {
			this.btn = ((Button) view);
			try {
				InpurchaseOpenActivity.this.calendar
						.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse((String) this.btn.getText()));
				new DatePickerDialog(InpurchaseOpenActivity.this, this.listener,
						InpurchaseOpenActivity.this.calendar.get(1), InpurchaseOpenActivity.this.calendar.get(2),
						InpurchaseOpenActivity.this.calendar.get(5)).show();
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	};

	private void intentTo() {
		if (btnDepartment.getText().toString().isEmpty()) {
			PDH.showMessage("部门不能为空");
			return;
		}
		if ((etDiscountRatio.getText().toString().length() == 0)
				|| (Double.parseDouble(this.etDiscountRatio.getText().toString()) <= 0.0D)
				|| (Double.parseDouble(this.etDiscountRatio.getText().toString()) > 1.0D)) {
			PDH.showMessage("整单折扣必须大于0且小于等于1");
			return;
		}
		if (btnDeliveryTime.getText().toString().isEmpty()) {
			PDH.showMessage("交货日期不能为空");
			return;
		}
		if (btnSettleTime.getText().toString().isEmpty()) {
			PDH.showMessage("结算日期不能为空");
			return;
		}
		if (doccg != null) {
			fillDoc();
			Intent intent = new Intent();
			intent.putExtra("doc", doccg);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			if (doccg == null) {
				doccg = new Def_Doc();
			}
			// 添加支付
			fillDoc();
			PDH.show(this, new ProgressCallBack() {

				@SuppressWarnings("static-access")
				@Override
				public void action() {
					payTypes = new ServicePayType().PayTypes(0, 0);
					handler.sendEmptyMessage(0);
				}
			});

		}
	}

	private List<DefDocPayType> payTypes;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (payTypes == null || payTypes.size() == 0) {
				doccg = null;
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						PDH.showMessage("没有从服务器上获取到支付方式!");
					}
				});
				return;
			}
			doccg.setBuilderid(SystemState.getUser().getId());// 开单人
			doccg.setBuildername(SystemState.getUser().getName());// 开单人名称
			doccg.setBuildtime(doccg.getSettletime());// 开单的时间
			doccg.setPrinttemplate("2014采购单模板");// 无效！PC端模板名称已经写死 2016采购单模板
			doccg.setIsavailable(true);
			doccg.setIssettleup(true);
			doccg.setShowid("采购入库单");
			PurchaseEntity entity = new PurchaseEntity();
			Collections.sort(payTypes);
			entity.setTypelistjson(JSONUtil.toJSONString(payTypes));
			entity.setDocjson(JSONUtil.toJSONString(doccg));

			// TODO
			Intent intent = new Intent(InpurchaseOpenActivity.this, InpurchaseEditActivity.class);
			intent.putExtra("entity", entity);
			intent.putExtra("ishaschanged", true);
			startActivity(intent);
			finish();
		}
	};

	// 部门保存
	private void fillDoc() {
		if (TextUtils.isEmptyS(btnDepartment.getTag().toString())) {
			doccg.setDepartmentid(btnDepartment.getTag().toString());
			doccg.setDepartmentname(btnDepartment.getText().toString());
		}
		// 仓库保存
		if (btnWarehouse.getText().toString().length() > 0) {
			doccg.setWarehouseid(btnWarehouse.getTag().toString());
			doccg.setWarehousename(btnWarehouse.getText().toString());
		}
		// 供应商保存btnSuppler
		if (TextUtils.isEmptyS(btnSuppler.getText().toString())) {
			doccg.setCustomerid(btnSuppler.getTag().toString());
			doccg.setCustomername(btnSuppler.getText().toString());
		}
		// 折扣
		doccg.setDiscountratio(Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(), 2));

		// 交货日期
		if (btnDeliveryTime.getTag() != null) {
			doccg.setDeliverytime(btnDeliveryTime.getTag().toString());
		}
		// 结算日期
		if (btnSettleTime.getTag() != null) {
			doccg.setSettletime(btnSettleTime.getTag().toString());
		}
		// 备注
		doccg.setRemark(etRemark.getText().toString());
		// 摘要
		doccg.setSummary(etSummary.getText().toString());
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 3:
				// 设置供应商
				SupplierThin suppler = (SupplierThin) data.getSerializableExtra("suppler");
				btnSuppler.setText(suppler.getName());
				btnSuppler.setTag(suppler.getId());
				break;
			case 5:
				// 设置部门
				Department localDepartment = (Department) data.getSerializableExtra("department");
				btnDepartment.setText(localDepartment.getDname());
				btnDepartment.setTag(localDepartment.getDid());
				break;
			case 6:
				// 设置仓库
				Warehouse localWarehouse = (Warehouse) data.getSerializableExtra("warehouse");
				this.btnWarehouse.setText(localWarehouse.getName());
				this.btnWarehouse.setTag(localWarehouse.getId());
				break;
			}
		}

	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("采购开单");
	}
}
