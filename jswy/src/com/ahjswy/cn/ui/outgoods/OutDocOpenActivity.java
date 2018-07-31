package com.ahjswy.cn.ui.outgoods;

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
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.DefDocXS;
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
import com.ahjswy.cn.utils.GetTime;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.EditTextWithDel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class OutDocOpenActivity extends BaseActivity
		implements OnClickListener, com.ahjswy.cn.utils.DateTimePickDialogUtil.Time_callback {
	// 销售开单
	// 部门
	private Button btnDepartment;
	// 仓库
	private Button btnWarehouse;
	// 客户
	private Button btnCustomer;
	// 促销方案
	private Button btnPromotion;
	// 客户电话
	private EditText etMobile;
	// 客户车辆
	private EditText etTruckNumber;
	// 整单折扣
	private EditTextWithDel etDiscountRatio;
	// 结算日期
	private Button btnSettleTime;
	// 交货日期
	private Button btnDeliveryTime;
	// 备注
	private EditText etRemark;
	// 摘要
	private EditText etSummary;
	// 配送
	private CheckBox cbDistribution;
	// 配送地址
	private EditText etCustomerAddress;
	private DefDocXS doc;
	private boolean isReadOnly = false;
	private Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_outdoc_open);
		intview();
		initdate();
	}

	private void intview() {
		btnDepartment = (Button) findViewById(R.id.btnDepartment);
		btnWarehouse = (Button) findViewById(R.id.btnWarehouse);
		btnCustomer = (Button) findViewById(R.id.btnCustomer);
		btnPromotion = (Button) findViewById(R.id.btnPromotion);
		etMobile = (EditText) findViewById(R.id.etMobile);
		etTruckNumber = (EditText) findViewById(R.id.etTruckNumber);
		etDiscountRatio = (EditTextWithDel) findViewById(R.id.etDiscountRatio);
		btnSettleTime = (Button) findViewById(R.id.btnSettleTime);
		btnDeliveryTime = (Button) findViewById(R.id.btnDeliveryTime);
		etRemark = (EditText) findViewById(R.id.etRemark);
		etSummary = (EditText) findViewById(R.id.etSummary);
		cbDistribution = (CheckBox) findViewById(R.id.cbDistribution);
		etCustomerAddress = (EditText) findViewById(R.id.etCustomerAddress);
		etMobile.clearFocus();
		calendar = Calendar.getInstance();
	}

	private void initdate() {

		this.doc = ((DefDocXS) getIntent().getSerializableExtra("doc"));
		this.isReadOnly = this.doc == null || !this.doc.isIsavailable() || !this.doc.isIsposted() ? false : true;
		if (this.doc != null) {
			this.btnDepartment.setTag(this.doc.getDepartmentid());
			this.btnDepartment.setText(this.doc.getDepartmentname());
			this.btnWarehouse.setTag(this.doc.getWarehouseid());
			this.btnWarehouse.setText(this.doc.getWarehousename());
			this.btnCustomer.setTag(this.doc.getCustomerid());
			this.btnCustomer.setText(this.doc.getCustomername());
			this.btnPromotion.setTag(this.doc.getPromotionid());
			this.btnPromotion.setText(this.doc.getPromotionname());
			this.etMobile.setText(this.doc.getMobile());
			this.etTruckNumber.setText(this.doc.getTrucknumber());
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
			this.cbDistribution.setChecked(this.doc.isIsdistribution());
			this.etCustomerAddress.setText(this.doc.getCustomeraddress());
			this.etSummary.setText(this.doc.getSummary());
			this.etRemark.setText(this.doc.getRemark());
		} else {
			this.isReadOnly = false;
			// 设置部门
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
			// 设置折扣
			this.etDiscountRatio.setText("1.0");
			long l = Utils.getCurrentTime(false);
			this.btnDeliveryTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			this.btnDeliveryTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
			this.btnSettleTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			this.btnSettleTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
			this.cbDistribution.setChecked(false);
		}

		if (isReadOnly) {
			this.btnDepartment.setBackground(this.etSummary.getBackground());
			this.btnWarehouse.setBackground(this.etSummary.getBackground());
			this.btnCustomer.setBackground(this.etSummary.getBackground());
			this.btnPromotion.setBackground(this.etSummary.getBackground());
			this.btnDeliveryTime.setBackground(this.etSummary.getBackground());
			this.btnSettleTime.setBackground(this.etSummary.getBackground());
			this.btnDepartment.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnWarehouse.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnCustomer.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnPromotion.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnDeliveryTime.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.btnSettleTime.setPadding(Utils.dp2px(this, 10), 0, 0, 0);
			this.cbDistribution.setCursorVisible(false);
			this.cbDistribution.setFocusable(false);
			this.cbDistribution.setFocusableInTouchMode(false);
			this.etMobile.setCursorVisible(false);
			this.etMobile.setFocusable(false);
			this.etMobile.setFocusableInTouchMode(false);
			this.etTruckNumber.setCursorVisible(false);
			this.etTruckNumber.setFocusable(false);
			this.etTruckNumber.setFocusableInTouchMode(false);
			this.etDiscountRatio.setCursorVisible(false);
			this.etDiscountRatio.setFocusable(false);
			this.etDiscountRatio.setFocusableInTouchMode(false);
			this.cbDistribution.setCursorVisible(false);
			this.cbDistribution.setFocusable(false);
			this.cbDistribution.setFocusableInTouchMode(false);
			this.etCustomerAddress.setCursorVisible(false);
			this.etCustomerAddress.setFocusable(false);
			this.etCustomerAddress.setFocusableInTouchMode(false);
			this.etSummary.setCursorVisible(false);
			this.etSummary.setFocusable(false);
			this.etSummary.setFocusableInTouchMode(false);
			this.etRemark.setCursorVisible(false);
			this.etRemark.setFocusable(false);
			this.etRemark.setFocusableInTouchMode(false);
			this.cbDistribution.setEnabled(false);

			btnSettleTime.setCursorVisible(false);
			btnSettleTime.setFocusable(false);
			btnSettleTime.setFocusableInTouchMode(false);
			btnDeliveryTime.setCursorVisible(false);
			btnDeliveryTime.setFocusable(false);
			btnDeliveryTime.setFocusableInTouchMode(false);
		} else {
			// 设置监听
			btnDepartment.setOnClickListener(this);
			btnWarehouse.setOnClickListener(this);
			btnCustomer.setOnClickListener(this);
			btnPromotion.setOnClickListener(this);
			btnSettleTime.setOnClickListener(this);
			btnDeliveryTime.setOnClickListener(this);
			cbDistribution.setOnClickListener(this);
			etCustomerAddress.setOnClickListener(this);
		}
		this.isReadOnly = false;
	}

	long numClick = 0;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if ((this.doc == null) || (!this.doc.isIsavailable()) || (!this.doc.isIsposted())) {
			menu.add(0, 0, 0, "确定").setShowAsAction(2);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			if (doc != null) {
				Intent intent = new Intent();
				setResult(RESULT_FIRST_USER, intent);
			} else {
				startActivity(new Intent(this, SwyMain.class));

			}
			finish();
			break;
		case 0:
			intentTo();
			break;
		}
		return true;
	}

	long mHits[] = new long[2];

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDepartment:
			// 部门
			startActivityForResult(new Intent(this, DepartmentSearchAct.class), 5);
			break;

		case R.id.btnWarehouse:
			// 仓库
			startActivityForResult(new Intent(this, WarehouseSearchAct.class), 6);
			break;

		case R.id.btnCustomer:
			// 客户
			startActivityForResult(new Intent().setClass(this, CustomerSearchAct.class), 4);
			break;
		case R.id.btnSettleTime:

			DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(this, GetTime.getDateTime(),
					R.id.btnSettleTime, this);
			dateTimePicKDialog.dateTimePicKDialog(btnSettleTime);
			break;
		case R.id.btnDeliveryTime:
			DateTimePickDialogUtil jiesuan_riqi = new DateTimePickDialogUtil(this, GetTime.getDateTime(),
					R.id.btnDeliveryTime, this);
			jiesuan_riqi.dateTimePicKDialog(btnDeliveryTime);
			break;
		case R.id.etCustomerAddress:
			System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
			mHits[mHits.length - 1] = SystemClock.uptimeMillis();
			if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
				PDH.show(OutDocOpenActivity.this, new PDH.ProgressCallBack() {
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
		default:
			break;
		}
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
				new DatePickerDialog(OutDocOpenActivity.this, listener, calendar.get(1), calendar.get(2),
						calendar.get(5)).show();
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				// 地址
				String localString = data.getStringExtra("address");
				etCustomerAddress.setText(localString);
				break;
			case 4:
				CustomerThin localCustomerThin = (CustomerThin) data.getSerializableExtra("customer");
				btnCustomer.setText(localCustomerThin.getName());
				btnCustomer.setTag(localCustomerThin.getId());
				String promotionname = localCustomerThin.getPromotionname();
				etCustomerAddress.setText("");
				if (TextUtils.isEmptyS(promotionname)) {
					btnPromotion.setText(localCustomerThin.getPromotionname());
					btnPromotion.setTag(localCustomerThin.getPromotionid());
				} else {
					btnPromotion.setText("");
					btnPromotion.setTag(null);
				}
				if (TextUtils.isEmptyS(localCustomerThin.getContactmoblie())) {
					etMobile.setText(localCustomerThin.getContactmoblie());
				} else {
					etMobile.setText("");
				}
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

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			String localString = msg.obj.toString();
			if (RequestHelper.isSuccess(localString)) {
				DocContainerEntity entity = JSONUtil.fromJson(localString, DocContainerEntity.class);
				doc = ((DefDocXS) JSONUtil.fromJson(entity.getDoc(), DefDocXS.class));
				// 保存基本数据
				fillDoc();
				entity.setDoc(JSONUtil.toJSONString(doc));
				// TODO
				Intent localIntent = new Intent(OutDocOpenActivity.this, OutDocEditActivity.class);
				localIntent.putExtra("docContainer", entity);
				localIntent.putExtra("ishaschanged", true);
				startActivity(localIntent);
				finish();
				return;
			} else {
				RequestHelper.showError(localString);
			}

		}
	};

	public void intentTo() {
		if (btnDepartment.getTag() == null || btnDepartment.getTag().toString().length() == 0) {
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
			intent.putExtra("doc", doc);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			initDoc();
		}

	}

	// 保存所有 信息
	private void fillDoc() {
		// 部门save
		if (btnDepartment.getTag() != null && TextUtils.isEmptyS(btnDepartment.getTag().toString())) {
			doc.setDepartmentid(btnDepartment.getTag().toString());
			doc.setDepartmentname(btnDepartment.getText().toString());
		}
		// 仓库保存
		if (btnWarehouse.getTag() != null && TextUtils.isEmptyS(btnWarehouse.getTag().toString())) {
			doc.setWarehouseid(btnWarehouse.getTag().toString());
			doc.setWarehousename(btnWarehouse.getText().toString());
		}
		// 客户
		if (btnCustomer.getTag() != null && TextUtils.isEmptyS(btnCustomer.getTag().toString())) {
			this.doc.setCustomerid(this.btnCustomer.getTag().toString());
			this.doc.setCustomername(this.btnCustomer.getText().toString());
		}
		// 促销方案
		if (!btnPromotion.getText().toString().isEmpty()) {
			this.doc.setPromotionid(this.btnPromotion.getTag().toString());
			this.doc.setPromotionname(this.btnPromotion.getText().toString());
		} else {
			this.doc.setPromotionid(null);
			this.doc.setPromotionname("");
		}
		this.doc.setMobile("" + etMobile.getText().toString());
		this.doc.setTrucknumber("" + etTruckNumber.getText().toString());
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
		// 备注
		this.doc.setRemark(this.etRemark.getText().toString());
		// 摘要
		this.doc.setSummary(this.etSummary.getText().toString());
		// 配送
		this.doc.setIsdistribution(this.cbDistribution.isChecked());
		// 用户地址
		this.doc.setCustomeraddress(this.etCustomerAddress.getText().toString());
	};

	Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String localString1 = msg.obj.toString();
			if (RequestHelper.isSuccess(localString1)) {
				List<HashMap<String, String>> localList = JSONUtil.parse2ListMap(localString1);
				if (localList.size() == 1) {
					etCustomerAddress
							.setText((CharSequence) ((HashMap<String, String>) localList.get(0)).get("address"));
				} else if (localList.size() > 1) {
					Intent localIntent = new Intent(OutDocOpenActivity.this, CustomerAddressSearchAct.class);
					localIntent.putExtra("customername", btnCustomer.getText().toString());
					localIntent.putExtra("listaddress", localString1);
					startActivityForResult(localIntent, 0);

				} else {
					PDH.showFail("无可用地址");
				}
			} else {
				PDH.showFail(localString1);
			}
		};
	};

	private void initDoc() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				String localString = new ServiceStore().str_InitXSDoc(btnDepartment.getTag().toString(),
						btnWarehouse.getTag().toString());
				handler.sendMessage(handler.obtainMessage(0, localString));
			}
		});
	}

	@Override
	public void dateTime(int id, String time) {
		switch (id) {
		case R.id.btnSettleTime:
			this.btnSettleTime.setTag(Utils.formatDate(time, "yyyy-MM-dd HH:mm:ss"));
			this.btnSettleTime.setText(Utils.formatDate(time, "yyyy-MM-dd"));
			break;
		case R.id.btnDeliveryTime:
			this.btnDeliveryTime.setTag(Utils.formatDate(time, "yyyy-MM-dd HH:mm:ss"));
			this.btnDeliveryTime.setText(Utils.formatDate(time, "yyyy-MM-dd"));
			break;
		default:
			break;
		}
	}

	public void setActionBarText() {
		if (this.doc != null) {
			getActionBar().setTitle(this.doc.getShowid());
			return;
		}
		getActionBar().setTitle("销售开单");
	}
}
