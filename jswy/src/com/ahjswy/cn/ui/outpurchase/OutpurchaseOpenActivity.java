package com.ahjswy.cn.ui.outpurchase;

import java.util.ArrayList;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.DefDocCG;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.DocContainerEntity;
import com.ahjswy.cn.model.SupplierThin;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.DepartmentSearchAct;
import com.ahjswy.cn.ui.SupplerInpurchaseAct;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.ui.WarehouseSearchAct;
import com.ahjswy.cn.utils.DateTimePickDialogUtil;
import com.ahjswy.cn.utils.DateTimePickDialogUtil.Time_callback;
import com.ahjswy.cn.utils.GetTime;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

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
	 * 采购退货  代码需要补齐
	 * @author Administrator
	 *
	 */
public class OutpurchaseOpenActivity extends BaseActivity implements OnClickListener, Time_callback {
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
	private DefDocCG defdoccg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_inpurchaseopen);
		intview();
		initdate();
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
	}

	private void initdate() {
		defdoccg = ((DefDocCG) getIntent().getSerializableExtra("doc"));
		if ((defdoccg != null)) { // && (defdoccg.isIsavailable())
			btnDepartment.setTag(defdoccg.getDepartmentid());
			btnDepartment.setText(defdoccg.getDepartmentname());
			btnWarehouse.setTag(defdoccg.getWarehouseid());
			btnWarehouse.setText(defdoccg.getWarehousename());
			btnSuppler.setTag(defdoccg.getSupplerid());
			btnSuppler.setText(defdoccg.getSupplername());
			etDiscountRatio.setText(defdoccg.getDiscountratio() + "");
			if (defdoccg.getDeliverytime() == null) {
				return;
			}
			btnDeliveryTime.setTag(defdoccg.getDeliverytime());
			btnDeliveryTime.setText(Utils.formatDate(defdoccg.getDeliverytime(), "yyyy-MM-dd"));
			if (defdoccg.getSettletime() == null) {
				return;
			}
			this.btnSettleTime.setTag(defdoccg.getSettletime());
			this.btnSettleTime.setText(Utils.formatDate(defdoccg.getSettletime(), "yyyy-MM-dd"));
			this.etSummary.setText(defdoccg.getSummary());
			this.etRemark.setText(defdoccg.getRemark());
		} else {
			Department department = SystemState.getDepartment();
			if (department != null) {
				this.btnDepartment.setTag(department.getDid());
				this.btnDepartment.setText(department.getDname());
			}
			// 设置仓库
			Warehouse warehouse = SystemState.getWarehouse();
			if (warehouse != null) {
				this.btnWarehouse.setTag(warehouse.getId().toString());
				this.btnWarehouse.setText(warehouse.getName());
			}
			this.etDiscountRatio.setText("1.0");
			long l = Utils.getCurrentTime(false);
			this.btnDeliveryTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			this.btnDeliveryTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
			this.btnSettleTime.setTag(Utils.formatDate(l, "yyyy-MM-dd HH:mm:ss"));
			this.btnSettleTime.setText(Utils.formatDate(l, "yyyy-MM-dd"));
		}
		// 布局监听
		btnDepartment.setOnClickListener(this);
		btnWarehouse.setOnClickListener(this);
		btnSuppler.setOnClickListener(this);
		btnSettleTime.setOnClickListener(this);
		btnDeliveryTime.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			if (defdoccg != null) {
				Intent intent = new Intent();
				setResult(RESULT_FIRST_USER, intent);
				finish();
				return true;
			}
			startActivity(new Intent(this, SwyMain.class));
			finish();
			break;
		case 0:
			intentTo();
			break;

		default:
			break;
		}

		return true;
	}

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
		if (defdoccg != null) {
			fillDoc();
			Intent intent = new Intent();
			intent.putExtra("doc", defdoccg);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			initDoc();
		}
	}

	private void initDoc() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				String localString = new ServiceStore().str_InitCGDoc(btnDepartment.getTag().toString(),
						btnWarehouse.getTag().toString());

			}
		});
		if (defdoccg == null) {
			defdoccg = new DefDocCG();
		}
		defdoccg.setShowid("CT_201606060002");
		defdoccg.setDoctypeid("101");
		defdoccg.setDoctypename("采购单");
		defdoccg.setPrinttemplate("2014采购退货单模板");
		defdoccg.setBuilderid("001");
		defdoccg.setBuildername("管理员");
		defdoccg.setMakerid("001");
		defdoccg.setMakername("管理员");
		defdoccg.setIsavailable(true);
		defdoccg.setIsposted(true);
		// 优惠
		defdoccg.setPreference(0);
		defdoccg.setRversion(10086);
		ArrayList<DefDocPayType> arrayList = new ArrayList<DefDocPayType>();
		DefDocPayType defDocPayType1 = new DefDocPayType();
		defDocPayType1.setAmount(0);
		defDocPayType1.setPaytypeid("001");
		defDocPayType1.setPaytypename("现金");
		defDocPayType1.setRversion(100001);
		arrayList.add(defDocPayType1);
		DefDocPayType defDocPayType2 = new DefDocPayType();
		defDocPayType2.setAmount(0);
		defDocPayType2.setPaytypeid("002");
		defDocPayType2.setPaytypename("银行卡");
		defDocPayType2.setRversion(100002);
		arrayList.add(defDocPayType2);
		DefDocPayType defDocPayType3 = new DefDocPayType();
		defDocPayType3.setAmount(0);
		defDocPayType3.setPaytypeid("003");
		defDocPayType3.setPaytypename("支票");
		defDocPayType3.setRversion(100003);
		arrayList.add(defDocPayType3);
		DefDocPayType defDocPayType4 = new DefDocPayType();
		defDocPayType4.setAmount(0);
		defDocPayType4.setPaytypeid("004");
		defDocPayType4.setPaytypename("支付宝");
		defDocPayType4.setRversion(100004);
		arrayList.add(defDocPayType4);
		DefDocPayType defDocPayType5 = new DefDocPayType();
		defDocPayType5.setAmount(0);
		defDocPayType5.setPaytypeid("005");
		defDocPayType5.setPaytypename("冲抵");
		defDocPayType5.setRversion(100005);
		arrayList.add(defDocPayType5);
		DocContainerEntity localDocContainerEntity = new DocContainerEntity();
		localDocContainerEntity.setDoctype("101");
		localDocContainerEntity.setDoc(JSONUtil.toJSONString(defdoccg));
		localDocContainerEntity.setPaytype(JSONUtil.toJSONString(arrayList));
		localDocContainerEntity.setInfo("");
		handler.sendMessage(handler.obtainMessage(0, JSONUtil.toJSONString(localDocContainerEntity)));

	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			String localString = msg.obj.toString();
			if (RequestHelper.isSuccess(localString)) {
				DocContainerEntity localDocContainerEntity = (DocContainerEntity) JSONUtil.fromJson(msg.obj.toString(),
						DocContainerEntity.class);
				defdoccg = ((DefDocCG) JSONUtil.readValue(localDocContainerEntity.getDoc(), DefDocCG.class));
				// 保存基本数据
				fillDoc();
				localDocContainerEntity.setDoc(JSONUtil.toJSONString(defdoccg));
				Intent localIntent = new Intent(OutpurchaseOpenActivity.this, OutpurchaseEditActivity.class);
				localIntent.putExtra("docContainer", localDocContainerEntity);
				localIntent.putExtra("ishaschanged", true);
				System.out.println("res>>>" + JSONUtil.toJSONString(localDocContainerEntity));
				startActivity(localIntent);
				finish();
			} else {
				RequestHelper.showError(localString);
			}
		}

	};

	private void fillDoc() {
		// 部门保存
		if (TextUtils.isEmptyS(btnDepartment.getTag().toString())) {
			defdoccg.setDepartmentid(btnDepartment.getTag().toString());
			defdoccg.setDepartmentname(btnDepartment.getText().toString());
		}
		// 仓库保存
		if (btnWarehouse.getTag() != null) {
			defdoccg.setWarehouseid(btnWarehouse.getTag().toString());
			defdoccg.setWarehousename(btnWarehouse.getText().toString());
		}
		// 供应商保存btnSuppler
		if (btnSuppler.getTag() != null) {
			defdoccg.setSupplerid(btnSuppler.getTag().toString());
			defdoccg.setSupplername(btnSuppler.getText().toString());
		}
		// 折扣
		defdoccg.setDiscountratio(
				Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(), 2));

		// 交货日期
		if (btnDeliveryTime.getTag() != null) {
			defdoccg.setDeliverytime(btnDeliveryTime.getTag().toString());
		}
		// 结算日期
		if (btnSettleTime.getTag() != null) {
			defdoccg.setSettletime(btnSettleTime.getTag().toString());
		}
		// 备注
		defdoccg.setRemark(etRemark.getText().toString());
		// 摘要
		defdoccg.setSummary(etSummary.getText().toString());
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnDepartment:
			startActivityForResult(new Intent(this, DepartmentSearchAct.class), 5);
			break;
		case R.id.btnWarehouse:
			startActivityForResult(new Intent(this, WarehouseSearchAct.class), 6);
			break;
		case R.id.btnSuppler:
			startActivityForResult(new Intent(this, SupplerInpurchaseAct.class), 3);
			break;
		case R.id.btnSettleTime:
			DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(this, GetTime.getDateTime(),
					R.id.btnSettleTime, this);
			dateTimePicKDialog.dateTimePicKDialog(btnDeliveryTime);
			break;
		case R.id.btnDeliveryTime:
			DateTimePickDialogUtil jiesuan_riqi = new DateTimePickDialogUtil(this, GetTime.getDateTime(),
					R.id.btnDeliveryTime, this);
			jiesuan_riqi.dateTimePicKDialog(btnSettleTime);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("采购退货");
	}
}
