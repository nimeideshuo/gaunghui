package com.ahjswy.cn.ui.ingoods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.CustomerThin;
import com.ahjswy.cn.model.DefDoc;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.DocContainerEntity;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.CustomerAddressSearchAct;
import com.ahjswy.cn.ui.CustomerSearchAct;
import com.ahjswy.cn.ui.DepartmentSearchAct;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.ui.WarehouseSearchAct;
import com.ahjswy.cn.utils.DateTimePickDialogUtil;
import com.ahjswy.cn.utils.DateTimePickDialogUtil.Time_callback;
import com.ahjswy.cn.utils.GetTime;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * 销售退货
 * 
 * @author Administrator
 *
 */
public class InDocOpenActivity extends BaseActivity implements OnClickListener, Time_callback {
	private DefDoc doc;
	boolean isReadOnly;
	// 部门
	private Button btnDepartment;
	// 仓库
	private Button btnWarehouse;
	// 客户
	private Button btnCustomer;
	// 折扣
	private EditText etDiscountRatio;
	// 交货日期
	private Button btnDeliveryTime;
	// 结算日期
	private Button btnSettleTime;
	// 备注
	private EditText etRemark;
	// 摘要
	private EditText etSummary;
	// 配送
	private CheckBox cbDistribution;
	// 配送地址
	private EditText etCustomerAddress;
	// private Button btnOpenDate;
	// private Button btnOverDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_indocopen);
		initView();
		initDate();
	}

	private void initView() {
		btnDepartment = (Button) findViewById(R.id.btnDepartment);
		btnWarehouse = (Button) findViewById(R.id.btnWarehouse);
		btnCustomer = (Button) findViewById(R.id.btnCustomer);
		etDiscountRatio = (EditText) findViewById(R.id.etDiscountRatio);
		btnDeliveryTime = (Button) findViewById(R.id.btnDeliveryTime);
		btnSettleTime = (Button) findViewById(R.id.btnSettleTime);
		// btnOpenDate = (Button) findViewById(R.id.btnOpenDate);
		// btnOverDate = (Button) findViewById(R.id.btnOverDate);
		cbDistribution = (CheckBox) findViewById(R.id.cbDistribution);
		etCustomerAddress = (EditText) findViewById(R.id.etCustomerAddress);
		etSummary = (EditText) findViewById(R.id.etSummary);
		etRemark = (EditText) findViewById(R.id.etRemark);
		calendar = Calendar.getInstance();
	}

	private void initDate() {
		doc = (DefDoc) getIntent().getSerializableExtra("doc");
		if ((this.doc != null) && (this.doc.isIsavailable())) {
			if (doc.isIsposted()) {
				this.isReadOnly = true;
			}
			this.btnDepartment.setTag(this.doc.getDepartmentid());
			this.btnDepartment.setText(this.doc.getDepartmentname());
			this.btnWarehouse.setTag(this.doc.getWarehouseid());
			this.btnWarehouse.setText(this.doc.getWarehousename());
			this.btnCustomer.setTag(this.doc.getCustomerid());
			this.btnCustomer.setText(this.doc.getCustomername());
			this.etDiscountRatio.setText(this.doc.getDiscountratio() + "");
			if (this.doc.getDeliverytime() == null) {
				return;
			}
			this.btnDeliveryTime.setTag(this.doc.getDeliverytime());
			this.btnDeliveryTime.setText(Utils.formatDate(this.doc.getDeliverytime(), "yyyy-MM-dd"));
			if (this.doc.getSettletime() == null) {
				return;
			}
			this.btnSettleTime.setTag(this.doc.getSettletime());
			this.btnSettleTime.setText(Utils.formatDate(this.doc.getSettletime(), "yyyy-MM-dd"));
			etRemark.setText(doc.getRemark());
			etSummary.setText(doc.getSummary());
			this.cbDistribution.setChecked(this.doc.isIsdistribution());
			etCustomerAddress.setText(doc.getCustomeraddress());
		} else {
			this.isReadOnly = false;
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
			this.etDiscountRatio.setText("1.0");
			long l = Utils.getCurrentTime(false);
			this.btnDeliveryTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			this.btnDeliveryTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
			this.btnSettleTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			this.btnSettleTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
			this.cbDistribution.setChecked(false);
		}
		if (this.isReadOnly) {
			this.btnDepartment.setBackground(this.etSummary.getBackground());
			this.btnWarehouse.setBackground(this.etSummary.getBackground());
			this.btnCustomer.setBackground(this.etSummary.getBackground());
			this.btnDeliveryTime.setBackground(this.etSummary.getBackground());
			this.btnSettleTime.setBackground(this.etSummary.getBackground());
			this.btnDepartment.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnWarehouse.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnCustomer.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnDeliveryTime.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnSettleTime.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			// checkbox
			this.cbDistribution.setCursorVisible(false);
			this.cbDistribution.setFocusable(false);
			this.cbDistribution.setFocusableInTouchMode(false);
			this.etDiscountRatio.setCursorVisible(false);
			this.etDiscountRatio.setFocusable(false);
			this.etDiscountRatio.setFocusableInTouchMode(false);
			this.etCustomerAddress.setCursorVisible(false);
			this.etCustomerAddress.setFocusable(false);
			this.etCustomerAddress.setFocusableInTouchMode(false);
			this.etSummary.setCursorVisible(false);
			this.etSummary.setFocusable(false);
			this.etSummary.setFocusableInTouchMode(false);
			this.etRemark.setCursorVisible(false);
			this.etRemark.setFocusable(false);
			this.etRemark.setFocusableInTouchMode(false);
			// checkbox禁止点击
			this.cbDistribution.setCursorVisible(false);
			this.cbDistribution.setFocusable(false);
			this.cbDistribution.setFocusableInTouchMode(false);
			this.cbDistribution.setEnabled(false);
		} else {
			// 布局监听
			this.btnDepartment.setOnClickListener(this);
			this.btnWarehouse.setOnClickListener(this);
			this.btnCustomer.setOnClickListener(this);
			this.btnDeliveryTime.setOnClickListener(this);
			this.btnSettleTime.setOnClickListener(this);
			etCustomerAddress.setOnClickListener(this);
			// btnOpenDate.setOnClickListener(dateClickListener);
			// btnOverDate.setOnClickListener(dateClickListener);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			if (doc != null) {
				setResult(RESULT_FIRST_USER, new Intent());
			} else {
				startActivity(new Intent(this, SwyMain.class));

			}
			finish();
			break;
		case 0:
			onOptionsItemSelected();
			break;

		default:
			break;
		}
		return true;
	}

	View.OnClickListener dateClickListener = new View.OnClickListener() {
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
		private Button btn;

		@Override
		public void onClick(View v) {
			btn = (Button) v;
			try {
				calendar.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse((String) btn.getText()));
				new DatePickerDialog(InDocOpenActivity.this, listener, calendar.get(1), calendar.get(2),
						calendar.get(5)).show();
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	};

	long mHits[] = new long[2];

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDepartment:
			startActivityForResult(new Intent().setClass(this, DepartmentSearchAct.class), 5);
			break;
		case R.id.btnWarehouse:
			startActivityForResult(new Intent().setClass(this, WarehouseSearchAct.class), 6);
			break;
		case R.id.btnCustomer:
			startActivityForResult(new Intent().setClass(this, CustomerSearchAct.class), 4);
			break;
		case R.id.btnDeliveryTime:
			DateTimePickDialogUtil dateDeliveryTime = new DateTimePickDialogUtil(this, GetTime.getDateTime(),
					R.id.btnDeliveryTime, this);
			dateDeliveryTime.dateTimePicKDialog(btnDeliveryTime);
			break;
		case R.id.btnSettleTime:
			DateTimePickDialogUtil dateSettleTime = new DateTimePickDialogUtil(this, GetTime.getDateTime(),
					R.id.btnSettleTime, this);
			dateSettleTime.dateTimePicKDialog(btnDeliveryTime);
			break;
		case R.id.etCustomerAddress:
			System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
			mHits[mHits.length - 1] = SystemClock.uptimeMillis();
			if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
				PDH.show(InDocOpenActivity.this, new PDH.ProgressCallBack() {
					public void action() {
						if (btnCustomer.getTag() != null) {
							String localString = new ServiceStore()
									.str_GetCustomerAddress(btnCustomer.getTag().toString());
							handlerGet.sendMessage(handlerGet.obtainMessage(0, localString));
						}

					}
				});
			}
			break;
		}
	}

	public void onOptionsItemSelected() {
		if (btnDepartment.getTag().toString().isEmpty()) {
			PDH.showMessage("部门不能为空");
			return;
		}
		if ((etDiscountRatio.getText().toString().length() == 0)
				|| (Double.parseDouble(this.etDiscountRatio.getText().toString()) <= 0.0D)
				|| (Double.parseDouble(this.etDiscountRatio.getText().toString()) > 1.0D)) {
			PDH.showMessage("整单折扣必须大于0且小于等于1");
			return;
		}
		if (btnDeliveryTime.getTag().toString().isEmpty()) {
			PDH.showMessage("交货日期不能为空");
			return;
		}
		if (btnSettleTime.getTag().toString().isEmpty()) {
			PDH.showMessage("结算日期不能为空");
			return;
		}

		// 属性走此方法
		if (this.doc != null) {
			fillDoc();
			Intent intent = new Intent();
			intent.putExtra("doc", this.doc);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			initDoc();
		}
	}

	private void initDoc() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				String localString = new ServiceStore().str_InitXTDoc(btnDepartment.getTag().toString(),
						btnWarehouse.getTag().toString());
				handler.sendMessage(handler.obtainMessage(0, localString));
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String localString = msg.obj.toString();
			if (RequestHelper.isSuccess(localString)) {
				DocContainerEntity docEntity = (DocContainerEntity) JSONUtil.fromJson(localString,
						DocContainerEntity.class);
				doc = ((DefDoc) JSONUtil.fromJson(docEntity.getDoc(), DefDoc.class));
				fillDoc();
				docEntity.setDoc(JSONUtil.object2Json(doc));
				Intent localIntent = new Intent();
				localIntent.setClass(InDocOpenActivity.this, InDocEditActivity.class);
				localIntent.putExtra("docContainer", docEntity);
				startActivity(localIntent);
				finish();
			} else {
				RequestHelper.showError(localString);
			}

		};
	};
	Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				List<HashMap<String, String>> localList = JSONUtil.parse2ListMap(message);
				if (localList.size() == 1) {
					etCustomerAddress
							.setText((CharSequence) ((HashMap<String, String>) localList.get(0)).get("address"));
				} else if (localList.size() > 1) {
					Intent localIntent = new Intent(InDocOpenActivity.this, CustomerAddressSearchAct.class);
					localIntent.putExtra("customername", btnCustomer.getText().toString());
					localIntent.putExtra("listaddress", message);
					startActivityForResult(localIntent, 0);

				} else {
					PDH.showFail("无可用地址");
				}
			} else {
				PDH.showFail(message);
			}
		};
	};
	private Calendar calendar;

	private void fillDoc() {
		// 部门save
		if (btnDepartment.getText().toString().length() > 0) {
			doc.setDepartmentid(btnDepartment.getTag().toString());
			doc.setDepartmentname(btnDepartment.getText().toString());
		}
		// 仓库保存
		if (btnWarehouse.getText().toString().length() > 0) {
			doc.setWarehouseid(btnWarehouse.getTag().toString());
			doc.setWarehousename(this.btnWarehouse.getText().toString());
		}
		// 客户
		if (btnCustomer.getText().toString().length() > 0) {
			this.doc.setCustomerid(this.btnCustomer.getTag().toString());
			this.doc.setCustomername(this.btnCustomer.getText().toString());
		}
		// 折扣
		this.doc.setDiscountratio(
				Utils.normalize(Utils.getDouble(this.etDiscountRatio.getText().toString()).doubleValue(), 2));
		// 交货日期
		if (btnDeliveryTime.getText().toString().length() > 0) {
			this.doc.setDeliverytime(this.btnDeliveryTime.getTag().toString());
		}
		// 结算日期
		if (btnSettleTime.getText().toString().length() > 0) {
			this.doc.setSettletime(this.btnSettleTime.getTag().toString());
		}
		// if (!TextUtils.isEmpty(btnOpenDate.getText().toString())) {
		// doc.setOpendate(btnOpenDate.getText().toString());
		// }
		// if (!TextUtils.isEmpty(btnOverDate.getText().toString())) {
		// doc.setOverdate(btnOverDate.getText().toString());
		// }
		// 备注
		this.doc.setRemark(this.etRemark.getText().toString());
		// 摘要
		this.doc.setSummary(this.etSummary.getText().toString());
		// 配送
		this.doc.setIsdistribution(this.cbDistribution.isChecked());
		// 配送地址
		this.doc.setCustomeraddress(this.etCustomerAddress.getText().toString());

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				String localString = data.getStringExtra("address");
				etCustomerAddress.setText(localString);
				break;
			case 4:
				etCustomerAddress.setText("");
				CustomerThin localCustomerThin = (CustomerThin) data.getSerializableExtra("customer");
				btnCustomer.setText(localCustomerThin.getName());
				btnCustomer.setTag(localCustomerThin.getId());
				break;
			case 5:
				// 设置部门
				Department localDepartment = (Department) data.getSerializableExtra("department");
				btnDepartment.setText(localDepartment.getDname());
				btnDepartment.setTag(localDepartment.getDid());
				break;
			case 6:
				Warehouse localWarehouse = (Warehouse) data.getSerializableExtra("warehouse");
				this.btnWarehouse.setText(localWarehouse.getName());
				this.btnWarehouse.setTag(localWarehouse.getId());
				break;
			}
		}
	}

	@Override
	public void dateTime(int id, String time) {
		switch (id) {
		case R.id.btnDeliveryTime:
			this.btnDeliveryTime.setTag(Utils.formatDate(time, "yyyy-MM-dd HH:mm:ss"));
			this.btnDeliveryTime.setText(Utils.formatDate(time, "yyyy-MM-dd"));
			break;
		case R.id.btnSettleTime:
			this.btnSettleTime.setTag(Utils.formatDate(time, "yyyy-MM-dd HH:mm:ss"));
			this.btnSettleTime.setText(Utils.formatDate(time, "yyyy-MM-dd"));
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (doc != null) {
				Intent intent = new Intent();
				intent.putExtra("doc", this.doc);
				setResult(1, intent);
				finish();
				return false;
			}
			startActivity(new Intent(this, SwyMain.class));
			finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("退货开单");
	}
}
