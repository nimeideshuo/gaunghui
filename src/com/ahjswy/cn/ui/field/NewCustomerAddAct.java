package com.ahjswy.cn.ui.field;

import java.util.HashMap;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.dao.CustomerTypeDAO;
import com.ahjswy.cn.dao.RegionDAO;
import com.ahjswy.cn.model.Customer;
import com.ahjswy.cn.model.Customertype;
import com.ahjswy.cn.model.Region;
import com.ahjswy.cn.service.ServiceCustomer;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.CustomerTypeSearchAct;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.ui.RegionSearchAct;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.UpdateUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class NewCustomerAddAct extends BaseActivity implements OnClickListener {
	private AutoCompleteTextView actvCusBank;
	private Button btnCusRegion;
	private Button btnCusType;
	private Customer customer;
	private Customertype customertype;
	private EditText etAddress;
	private EditText etCusBankAccount;
	private EditText etCusContactMobile;
	private EditText etCusId;
	private EditText etCusName;
	private EditText etCusRemark;
	private EditText etCusTelephone;
	private String regionId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_customer_dialog);
		initView();
		getCustomerID();
	}

	private List<Region> mapRegions;
	private List<Customertype> mapTypes;
	private Region region;

	private void initView() {
		this.btnCusRegion = ((Button) findViewById(R.id.btnCusRegion));
		this.btnCusType = ((Button) findViewById(R.id.btnCusType));
		this.btnCusRegion.setOnClickListener(this);
		this.btnCusType.setOnClickListener(this);
		this.etCusName = ((EditText) findViewById(R.id.etCusName));
		this.etCusId = ((EditText) findViewById(R.id.etCusId));
		this.etCusTelephone = ((EditText) findViewById(R.id.etCusTelephone));
		this.etCusContactMobile = ((EditText) findViewById(R.id.etCusContactMobile));
		this.etCusRemark = ((EditText) findViewById(R.id.etCusRemark));
		this.etCusBankAccount = ((EditText) findViewById(R.id.etCusBankAccount));
		this.etAddress = ((EditText) findViewById(R.id.etAddress));
		this.actvCusBank = ((AutoCompleteTextView) findViewById(R.id.actvCusBank));
		this.actvCusBank.setThreshold(1);
		this.mapTypes = new CustomerTypeDAO().queryAllcuCustomertypes();
		if ((this.mapTypes == null) || (this.mapTypes.size() == 0)) {
			PDH.showFail("客户分类信息不存在");
			finish();
			return;
		}
		this.customertype = ((Customertype) this.mapTypes.get(0));
		this.btnCusType.setText(this.customertype.getName());
		this.mapRegions = new RegionDAO().getAllRegions();
		if ((this.mapRegions == null) || (this.mapRegions.size() == 0)) {
			PDH.showFail("地区信息不存在");
			finish();
			return;
		}
		this.region = ((Region) this.mapRegions.get(0));
		this.btnCusRegion.setText(this.region.getName());
		regionId = region.getId();
	}

	private void getCustomerID() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				String localString = new ServiceCustomer().cu_GetNewCustomerID();
				handlerGetID.sendMessage(handlerGetID.obtainMessage(0, localString));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "提交").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		case 0:
			if (TextUtils.isEmpty(etCusId.getText().toString())) {
				PDH.showError("客户编号为必填");
				return false;
			}
			if (TextUtils.isEmpty(etCusName.getText().toString())) {
				PDH.showError("客户姓名为必填");
				return false;
			}
			this.customer = makeForm();
			saveCustomer(false, false);
			break;

		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	private Customer makeForm() {
		Customer localCustomer = new Customer();
		localCustomer.setName(etCusName.getText().toString().trim());
		localCustomer.setId(etCusId.getText().toString().trim());
		localCustomer.setPinyin(null);
		localCustomer.setContactMoblie(etCusContactMobile.getText().toString().trim());
		localCustomer.setTelephone(etCusTelephone.getText().toString().trim());
		localCustomer.setCustomerTypeId(customertype.getId());
		localCustomer.setRegionId(regionId);
		localCustomer.setVisitLineId(null);
		localCustomer.setDepositBank(actvCusBank.getText().toString());
		localCustomer.setBankingAccount(etCusBankAccount.getText().toString());
		localCustomer.setRemark(etCusRemark.getText().toString());
		localCustomer.setAddress(etAddress.getText().toString());
		localCustomer.setPriceSystemId(customertype.getPricesystemid());
		return localCustomer;
	}

	private void saveCustomer(final boolean isignorsamename, final boolean isignorsametel) {
		PDH.show(this, "正在提交，请稍候...", new PDH.ProgressCallBack() {
			public void action() {
				String localString = new ServiceCustomer().cu_AddCustomerForSale(customer, isignorsamename,
						isignorsametel);
				handler.sendMessage(handler.obtainMessage(0, localString));
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String localString = String.valueOf(msg.obj);
			if (RequestHelper.isSuccess(localString)) {
				HashMap<String, String> localHashMap = JSONUtil.parse2Map(localString);
				new UpdateUtils().saveToLocalDB(localHashMap);
				PDH.showSuccess("提交成功");
				finish();
				return;
			}
			if ("sameid".equals(localString)) {
				PDH.showFail("客户编号已经存在，请修改");
				return;
			}

			if ("samename".equals(localString)) {

				MAlertDialog dialog = new MAlertDialog(NewCustomerAddAct.this);
				dialog.setTitle("服务器存在同名客户，是否继续提交？");
				dialog.setNeutralButton("提交", new MAlertDialog.OnClickListener() {

					@Override
					public void onClick(MAlertDialog dialog) {
						dialog.dismiss();
						saveCustomer(true, false);
					}
				});
				dialog.setNegativeButton("取消", new MAlertDialog.OnClickListener() {

					@Override
					public void onClick(MAlertDialog dialog) {
						dialog.dismiss();
					}
				});
				dialog.show();
				return;
			}

			if ("sametel".equals(localString)) {
				MAlertDialog dialog = new MAlertDialog(NewCustomerAddAct.this);
				dialog.setMessage("服务器存在相同电话的客户，是否继续提交？");
				dialog.setNegativeButton("取消", new MAlertDialog.OnClickListener() {

					@Override
					public void onClick(MAlertDialog dialog) {
						dialog.dismiss();
					}
				});
				dialog.setNeutralButton("提交", new MAlertDialog.OnClickListener() {

					@Override
					public void onClick(MAlertDialog dialog) {
						dialog.dismiss();
						saveCustomer(true, true);
					}
				});
				dialog.show();
				return;

			}
			RequestHelper.showError(localString);
		};
	};
	private Handler handlerGetID = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			String localString = String.valueOf(paramAnonymousMessage.obj);
			if (RequestHelper.isSuccess(localString)) {
				HashMap<String, String> localHashMap = JSONUtil.parse2Map(localString);
				etCusId.setText((CharSequence) localHashMap.get("id"));
				return;
			}
			PDH.showFail(localString);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 客户分类
		case R.id.btnCusType:
			startActivityForResult(new Intent().setClass(this, CustomerTypeSearchAct.class), 0);
			break;
		// 地区
		case R.id.btnCusRegion:
			startActivityForResult(new Intent().setClass(this, RegionSearchAct.class), 1);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				customertype = ((Customertype) data.getSerializableExtra("customertype"));
				btnCusType.setText(this.customertype.getName());
				break;
			case 1:
				Region localRegion = (Region) data.getSerializableExtra("region");
				regionId = localRegion.getId();
				btnCusRegion.setText(localRegion.getName());
				break;
			}

		}
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("新增客户");
	}

}
