package com.ahjswy.cn.ui.ingoods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.bean.GoodEntity;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.service.ServiceGoods;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.outgoods.GoodsWarehouseSearchAct;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class InDocAddGoodAct extends BaseActivity implements OnClickListener, OnFocusChangeListener {
	// 条码
	private TextView tvBarcode;
	// 规格
	private TextView tvSpecification;
	// 时间
	private Button btnDate;
	// 批次
	private EditText etBatch;
	// 仓库
	private Button btnWarehouse;
	// 单位
	private Button btnUnit;
	// 数量
	private EditText etNum;
	// 单价
	private EditText etPrice;
	// 小计
	private EditText etSubtotal;
	// 单品折扣
	private EditText etDiscountRatio;
	// 折后单价
	private EditText etDiscountPrice;
	// 折后小计
	private EditText etDiscountSubtotal;
	private String customerid;
	private DefDocItemXS docitem;
	private EditText etRemark;
	private Calendar cal;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_indoc_add_goods);
		initView();
	}

	private void initView() {
		tvBarcode = ((TextView) findViewById(R.id.tvBarcode));
		tvSpecification = ((TextView) findViewById(R.id.tvSpecification));
		btnDate = ((Button) findViewById(R.id.btnDate));
		startDate = (Button) findViewById(R.id.startDate);
		endDate = (Button) findViewById(R.id.endDate);
		Button queryPrice = (Button) findViewById(R.id.queryPrice);
		etBatch = ((EditText) findViewById(R.id.etBatch));

		btnWarehouse = ((Button) findViewById(R.id.btnWarehouse));
		btnUnit = ((Button) findViewById(R.id.btnUnit));

		etNum = (EditText) findViewById(R.id.etNum);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etSubtotal = (EditText) findViewById(R.id.etSubtotal);
		etDiscountRatio = (EditText) findViewById(R.id.etDiscountRatio);
		etDiscountPrice = (EditText) findViewById(R.id.etDiscountPrice);
		etDiscountSubtotal = (EditText) findViewById(R.id.etDiscountSubtotal);
		etRemark = (EditText) findViewById(R.id.etRemark);

		this.btnWarehouse.setOnClickListener(this);
		this.btnUnit.setOnClickListener(this);
		queryPrice.setOnClickListener(this);
		this.btnDate.setOnClickListener(dateClickListener);
		this.startDate.setOnClickListener(dateClickListener);
		this.endDate.setOnClickListener(dateClickListener);
		this.etNum.setOnFocusChangeListener(this);
		this.etPrice.setOnFocusChangeListener(this);
		this.etSubtotal.setOnFocusChangeListener(this);
		this.etDiscountRatio.setOnFocusChangeListener(this);
		this.etDiscountPrice.setOnFocusChangeListener(this);
		this.etDiscountSubtotal.setOnFocusChangeListener(this);
		customerid = getIntent().getStringExtra("customerid");
		position = getIntent().getIntExtra("position", 0);
		docitem = ((DefDocItemXS) getIntent().getSerializableExtra("docitem"));
		// 是否显示批次时间
		if ((this.docitem != null) && (this.docitem.isIsusebatch())) {
			findViewById(R.id.linearDate).setVisibility(View.VISIBLE);
			findViewById(R.id.linearBatch).setVisibility(View.VISIBLE);
			try {
				this.etBatch.setText(this.docitem.getBatch());
				if (docitem.getProductiondate() != null) {
					this.btnDate.setText(Utils.formatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(this.docitem.getProductiondate()).getTime(), "yyyy-MM-dd"));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			findViewById(R.id.linearDate).setVisibility(View.GONE);
			findViewById(R.id.linearBatch).setVisibility(View.GONE);
		}
		this.tvBarcode.setText(this.docitem.getBarcode());
		this.tvSpecification.setText(this.docitem.getSpecification());
		this.btnWarehouse.setText(this.docitem.getWarehousename());
		this.btnWarehouse.setTag(this.docitem.getWarehouseid());
		this.btnUnit.setText(this.docitem.getUnitname());
		this.btnUnit.setTag(this.docitem.getUnitid());
		if (docitem.getNum() != 0.0D) {
			etNum.setText(docitem.getNum() + "");
		}
		this.etPrice.setText(docitem.getPrice() + "");
		this.etSubtotal.setText(docitem.getSubtotal() + "");
		this.etDiscountRatio.setText(this.docitem.getDiscountratio() + "");
		this.etDiscountPrice.setText(this.docitem.getDiscountprice() + "");
		this.etDiscountSubtotal.setText(this.docitem.getDiscountsubtotal() + "");
		this.etRemark.setText(this.docitem.getRemark() + "");
		this.etNum.setTag(this.etNum.getText().toString());
		this.etPrice.setTag(this.etPrice.getText().toString());
		this.etSubtotal.setTag(this.etSubtotal.getText().toString());
		this.etDiscountRatio.setTag(this.etDiscountRatio.getText().toString());
		this.etDiscountPrice.setTag(this.etDiscountPrice.getText().toString());
		this.etDiscountSubtotal.setTag(this.etDiscountSubtotal.getText().toString());
		cal = Calendar.getInstance();
		startDate.setText(docitem.getBuildbegintime());
		if (TextUtils.isEmpty(docitem.getBuildendtime())) {
			endDate.setText(Utils.getData());
		} else {
			endDate.setText(docitem.getBuildendtime());
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
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			break;
		case 0:
			onFocusChange(null, true);
			onCreateOptionsMenu();
			break;
		}

		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWarehouse:
			startActivityForResult(
					new Intent(this, GoodsWarehouseSearchAct.class).putExtra("goodsid", this.docitem.getGoodsid()), 6);
			break;
		case R.id.btnUnit:
			unitSelect();
			break;
		case R.id.queryPrice:
			if (TextUtils.isEmpty(customerid)) {
				showError("请选择客户!");
				return;
			}
			if (TextUtils.isEmpty(docitem.getGoodsid())) {
				return;
			}
			if (TextUtils.isEmpty(startDate.getText().toString()) || TextUtils.isEmpty(endDate.getText().toString())) {
				showError("请选择查询的 开始日期 和 结束日期!");
				return;
			}
			// 查询
			boolean basicUnit = new GoodsUnitDAO().isBasicUnit(docitem.getGoodsid(), docitem.getUnitid());
			GoodEntity gooden = new GoodEntity();
			gooden.setCustomerid(customerid);
			gooden.setGoodsid(docitem.getGoodsid());
			gooden.setUnittype(basicUnit == true ? 1 : 2);
			gooden.setBuildbegintime(startDate.getText().toString() + " 00:00:00");
			gooden.setBuildendtime(endDate.getText().toString() + " 00:00:00");
			docitem.setBuildbegintime(startDate.getText().toString());
			docitem.setBuildendtime(endDate.getText().toString());
			String goodPrice = new ServiceGoods().gds_Querygoodsavgprice(gooden);
			try {
				double price = Double.parseDouble(goodPrice);
				etPrice.setText(price + "");
				showSuccess("查询成功!");
			} catch (Exception e) {
				e.printStackTrace();
				showError(goodPrice);
			}
			break;
		}
	}

	private void onCreateOptionsMenu() {
		if (!TextUtils.isEmptyS(btnWarehouse.getTag().toString())) {
			PDH.showMessage("仓库不能为空");
			return;
		}
		if (etNum.getText().toString().length() <= 0
				|| Utils.getDouble(this.etNum.getText().toString()).doubleValue() <= 0.0D) {
			PDH.showMessage("数量必须大于0");
			return;
		}
		if ((etDiscountRatio.getText().toString().length() == 0)
				|| (Double.parseDouble(this.etDiscountRatio.getText().toString()) <= 0.0D)
				|| (Double.parseDouble(this.etDiscountRatio.getText().toString()) > 1.0D)) {
			PDH.showMessage("整单折扣必须大于0且小于等于1");
			return;
		}
		fillItem();
		Intent localIntent = new Intent();
		localIntent.putExtra("position", position);
		localIntent.putExtra("docitem", this.docitem);
		setResult(RESULT_OK, localIntent);
		finish();

	}

	private void fillItem() {
		if (TextUtils.isEmptyS(this.btnWarehouse.getTag().toString())) {
			this.docitem.setWarehouseid(this.btnWarehouse.getTag().toString());
			this.docitem.setWarehousename(this.btnWarehouse.getText().toString());
		} else {
			this.docitem.setWarehouseid(null);
			this.docitem.setWarehousename("");
		}
		if (TextUtils.isEmptyS(this.btnUnit.getTag().toString())) {
			this.docitem.setUnitid(this.btnUnit.getTag().toString());
			this.docitem.setUnitname(this.btnUnit.getText().toString());
		} else {
			this.docitem.setUnitid(null);
			this.docitem.setUnitname("");
		}
		if (this.docitem.isIsusebatch()) {
			this.docitem.setBatch(this.etBatch.getText().toString());
			this.docitem.setProductiondate(this.btnDate.getText() + " 00:00:00");
		}
		this.docitem.setNum(Utils.normalize(Utils.getDouble(this.etNum.getText().toString()).doubleValue(), 2));
		this.docitem.setBignum(new GoodsUnitDAO().getBigNum(this.docitem.getGoodsid(), this.docitem.getUnitid(),
				this.docitem.getNum()));
		this.docitem.setPrice(Utils.normalizePrice(Utils.getDouble(this.etPrice.getText().toString()).doubleValue()));
		this.docitem.setSubtotal(
				Utils.normalizeSubtotal(Utils.getDouble(this.etSubtotal.getText().toString()).doubleValue()));
		this.docitem.setDiscountratio(
				Utils.normalize(Utils.getDouble(this.etDiscountRatio.getText().toString()).doubleValue(), 2));
		this.docitem.setDiscountprice(
				Utils.normalizePrice(Utils.getDouble(this.etDiscountPrice.getText().toString()).doubleValue()));
		this.docitem.setDiscountsubtotal(
				Utils.normalizeSubtotal(Utils.getDouble(this.etDiscountSubtotal.getText().toString()).doubleValue()));
		this.docitem.setRemark(this.etRemark.getText().toString());
		// localDefDocItem = this.docitem;
		docitem.setIsgift(docitem.getPrice() == 0.0D ? true : false);
		// docitem.setCostprice(0.0D);
		// docitem.setRemark("");
		// docitem.setRversion(0L);
		// docitem.setIsdiscount(false);
		docitem.setIsusebatch(docitem.isIsusebatch());

	}

	private GoodsUnit goodsUnit;

	// 单位
	private void unitSelect() {
		final List<GoodsUnit> localList = new GoodsUnitDAO().queryGoodsUnits(this.docitem.getGoodsid());
		String[] arrayOfString = new String[localList.size()];
		for (int i = 0; i < localList.size(); i++) {
			arrayOfString[i] = localList.get(i).getUnitname();
		}
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("单位");
		localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				goodsUnit = localList.get(which);
				if (!goodsUnit.getUnitid().equals(btnUnit.getTag())) {
					double goodsPrice = DocUtils.getGoodsPrice(customerid, goodsUnit.getGoodsid(),
							goodsUnit.getUnitid());
					setPrice(goodsPrice);
				}

				// PDH.show(InDocAddGoodAct.this, new PDH.ProgressCallBack() {
				// public void action() {
				// ArrayList<ReqStrGetGoodsPrice> localArrayList =
				// new ArrayList<ReqStrGetGoodsPrice>();
				// ReqStrGetGoodsPrice getgoodsprice = new
				// ReqStrGetGoodsPrice();
				// getgoodsprice.setType(2);
				// getgoodsprice.setCustomerid(customerid);
				// getgoodsprice.setWarehouseid(btnWarehouse.getTag().toString());
				// getgoodsprice.setGoodsid(docitem.getGoodsid());
				// getgoodsprice.setUnitid(goodsUnit.getUnitid());
				// getgoodsprice.setPrice(0.0D);
				// getgoodsprice.setIsdiscount(false);
				// localArrayList.add(getgoodsprice);
				// String localString = new
				// ServiceGoods().gds_GetMultiGoodsPrice(localArrayList,
				// false,
				// docitem.isIsusebatch());
				// handlerGet.sendMessage(handlerGet.obtainMessage(0,
				// localString));
				// }
				// });
			}
		});
		localBuilder.show();
		return;

	}

	private void setPrice(double price) {
		btnUnit.setText(goodsUnit.getUnitname());
		btnUnit.setTag(goodsUnit.getUnitid());
		double d1 = Utils.normalize(Utils.getDouble(etNum.getText().toString()).doubleValue(), 2);
		double d2 = Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(), 2);
		double d3 = Utils.normalizePrice(price);
		double d4 = Utils.normalizePrice(d3 * d2);
		etPrice.setText(price + "");
		etSubtotal.setText(Utils.normalizeSubtotal(d1 * d3) + "");
		etDiscountPrice.setText(d4 + "");
		etDiscountSubtotal.setText(Utils.normalizeSubtotal(d1 * d4) + "");
		etPrice.setTag(etPrice.getText());
		etSubtotal.setTag(etSubtotal.getText());
		etDiscountPrice.setTag(etDiscountPrice.getText());
		etDiscountSubtotal.setTag(etDiscountSubtotal.getText());
		docitem.setUnitid(goodsUnit.getUnitid());
		docitem.setUnitname(goodsUnit.getUnitname());
	}

	private View.OnClickListener dateClickListener = new View.OnClickListener() {
		private Button btn;
		private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				InDocAddGoodAct.this.cal.set(1, year);
				InDocAddGoodAct.this.cal.set(2, monthOfYear);
				InDocAddGoodAct.this.cal.set(5, dayOfMonth);
				SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				btn.setText(localSimpleDateFormat.format(InDocAddGoodAct.this.cal.getTime()));
				if (Utils.intGenerateBatch != 0) {
					etBatch.setText(Utils.generateBatch(cal.getTimeInMillis()));
				}
			}
		};

		public void onClick(View view) {
			this.btn = ((Button) view);
			try {
				cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(this.btn.getText().toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			new DatePickerDialog(InDocAddGoodAct.this, this.listener, cal.get(1), cal.get(2), cal.get(5)).show();
		}
	};
	// Handler handlerGet = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// String localString = msg.obj.toString();
	// if (RequestHelper.isSuccess(localString)) {
	// ReqStrGetGoodsPrice localReqStrGetGoodsPrice = null;
	// localReqStrGetGoodsPrice = (ReqStrGetGoodsPrice) JSONUtil
	// .str2list(localString, ReqStrGetGoodsPrice.class).get(0);
	// btnUnit.setText(goodsUnit.getUnitname());
	// btnUnit.setTag(goodsUnit.getUnitid());
	// double d1 =
	// Utils.normalize(Utils.getDouble(etNum.getText().toString()).doubleValue(),
	// 2);
	// double d2 =
	// Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(),
	// 2);
	// double d3 = Utils.normalizePrice(localReqStrGetGoodsPrice.getPrice());
	// double d4 = Utils.normalizePrice(d3 * d2);
	// etPrice.setText(localReqStrGetGoodsPrice.getPrice() + "");
	// etSubtotal.setText(Utils.normalizeSubtotal(d1 * d3) + "");
	// etDiscountPrice.setText(d4 + "");
	// etDiscountSubtotal.setText(Utils.normalizeSubtotal(d1 * d4) + "");
	// etPrice.setTag(etPrice.getText());
	// etSubtotal.setTag(etSubtotal.getText());
	// etDiscountPrice.setTag(etDiscountPrice.getText());
	// etDiscountSubtotal.setTag(etDiscountSubtotal.getText());
	// docitem.setUnitid(goodsUnit.getUnitid());
	// docitem.setUnitname(goodsUnit.getUnitname());
	// return;
	// }
	// PDH.showFail(localString);
	// };
	// };
	private Button startDate;
	private Button endDate;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 6:
				RespGoodsWarehouse localRespGoodsWarehouse2 = (RespGoodsWarehouse) data
						.getSerializableExtra("warehouse");
				this.btnWarehouse.setTag(localRespGoodsWarehouse2.getWarehouseid());
				this.btnWarehouse.setText(localRespGoodsWarehouse2.getWarehousename());
				break;
			}
		}

	};

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			// 获取单价
			double d30 = 0.0;
			double d31 = 0.0;
			double d32 = 0.0;
			if (etPrice.getText().toString().length() > 0) {
				d30 = Utils.normalizePrice(Utils.getDouble(this.etPrice.getText().toString()).doubleValue());
			}
			if (etNum.getText().toString().length() > 0) {
				d31 = Utils.normalize(Utils.getDouble(this.etNum.getText().toString()).doubleValue(), 2);
			}
			if (etDiscountRatio.getText().toString().length() > 0) {
				d32 = Utils.normalizePrice(d30
						* Utils.normalize(Utils.getDouble(this.etDiscountRatio.getText().toString()).doubleValue(), 2));
			}

			double d33 = Utils.normalizeSubtotal(d31 * d30);
			double d34 = Utils.normalizeSubtotal(d31 * d32);
			// 小计
			this.etSubtotal.setText(d33 + "");
			// 折后单价
			this.etDiscountPrice.setText(d32 + "");
			// 折后小计
			this.etDiscountSubtotal.setText(d34 + "");
			this.etPrice.setTag(this.etPrice.getText());
			this.etDiscountPrice.setTag(this.etDiscountPrice.getText());
			this.etSubtotal.setTag(this.etSubtotal.getText());
			this.etDiscountSubtotal.setTag(this.etDiscountSubtotal.getText());
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle(docitem.getGoodsname());
	}
}
