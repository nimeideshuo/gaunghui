package com.ahjswy.cn.ui.outpurchase;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemTH;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.request.ReqStrGetGoodsPrice;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.service.ServiceGoods;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.outgoods.GoodsWarehouseSearchAct;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OutpurDocAddGoodAct extends BaseActivity implements OnClickListener, OnFocusChangeListener {
	// 条码
	private TextView tvBarcode;
	// 规格
	private TextView tvSpecification;
	// 仓库
	private Button btnWarehouse;
	// 单位
	private Button btnUnit;
	// 批次
	// private Button btnBatch;
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
	// 备注
	private EditText etRemark;
	private LinearLayout ll_PrmomotionGoods;
	private int position;
	private int positiongive;
	private String customerid;
	private Button btnPromotionProDate;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_indoc_add_goods);
		initView();
		initDate();
	}

	private void initView() {
		tvBarcode = (TextView) findViewById(R.id.tvBarcode);
		tvSpecification = (TextView) findViewById(R.id.tvSpecification);
		btnWarehouse = (Button) findViewById(R.id.btnWarehouse);
		btnUnit = ((Button) findViewById(R.id.btnUnit));
		etNum = (EditText) findViewById(R.id.etNum);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etSubtotal = (EditText) findViewById(R.id.etSubtotal);
		etDiscountRatio = (EditText) findViewById(R.id.etDiscountRatio);
		etDiscountPrice = (EditText) findViewById(R.id.etDiscountPrice);
		etDiscountSubtotal = (EditText) findViewById(R.id.etDiscountSubtotal);
		etRemark = (EditText) findViewById(R.id.etRemark);
		btnWarehouse.setOnClickListener(this);
		btnUnit.setOnClickListener(this);
	}

	DefDocItemTH defdocitemth;

	private void initDate() {
		customerid = getIntent().getStringExtra("customerid");
		position = getIntent().getIntExtra("position", -1);
		defdocitemth = ((DefDocItemTH) getIntent().getSerializableExtra("docitem"));
		positiongive = getIntent().getIntExtra("positiongive", -1);
		tvBarcode.setText(defdocitemth.getBarcode());
		tvSpecification.setText(defdocitemth.getSpecification());
		btnWarehouse.setText(defdocitemth.getWarehousename());
		btnWarehouse.setTag(defdocitemth.getWarehouseid());
		btnUnit.setText(defdocitemth.getUnitname());
		btnUnit.setTag(defdocitemth.getUnitid());
		etNum.setText(defdocitemth.getNum() + "");
		etPrice.setText(defdocitemth.getPrice() + "");
		etSubtotal.setText(defdocitemth.getSubtotal() + "");
		etDiscountRatio.setText(defdocitemth.getDiscountratio() + "");
		etDiscountPrice.setText(defdocitemth.getDiscountprice() + "");
		etDiscountSubtotal.setText(defdocitemth.getDiscountsubtotal() + "");
		etRemark.setText(defdocitemth.getRemark());
		this.btnUnit.setOnClickListener(this);
		this.btnWarehouse.setOnClickListener(this);
		this.etNum.setOnFocusChangeListener(this);
		this.etPrice.setOnFocusChangeListener(this);
		this.etSubtotal.setOnFocusChangeListener(this);
		this.etDiscountRatio.setOnFocusChangeListener(this);
		this.etDiscountPrice.setOnFocusChangeListener(this);
		this.etDiscountSubtotal.setOnFocusChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			break;
		case 0:
			onFocusChange(null, true);
			OptionsItemSelected();
			break;
		default:
			break;
		}
		return true;
	}

	private void OptionsItemSelected() {

		if (!TextUtils.isEmptyS(btnWarehouse.getText().toString())) {
			PDH.showMessage("仓库不能为空");
			return;
		}
		if ((etDiscountRatio.getText().toString().length() == 0)
				|| (Double.parseDouble(this.etDiscountRatio.getText().toString()) <= 0.0D)
				|| (Double.parseDouble(this.etDiscountRatio.getText().toString()) > 1.0D)) {
			PDH.showMessage("整单折扣必须大于0且小于等于1");
			return;
		}
		if (Utils.getDouble(this.etNum.getText().toString()).doubleValue() <= 0.0D) {
			PDH.showMessage("数量必须大于0");
			return;
		}
		fillItem();
		Intent localIntent = new Intent();
		localIntent.putExtra("position", this.position);
		localIntent.putExtra("docitem", defdocitemth);
		localIntent.putExtra("positiongive", this.positiongive);
		// localIntent.putExtra("docitemgive", this.docitemgive);
		setResult(RESULT_OK, localIntent);
		finish();
	}

	private void fillItem() {
		if (TextUtils.isEmptyS(this.btnWarehouse.getTag().toString())) {
			defdocitemth.setWarehouseid(this.btnWarehouse.getTag().toString());
			defdocitemth.setWarehousename(this.btnWarehouse.getText().toString());
			if (TextUtils.isEmptyS(this.btnUnit.getTag().toString())) {
				defdocitemth.setUnitid(this.btnUnit.getTag().toString());
			}
			defdocitemth.setUnitname(this.btnUnit.getText().toString());
			defdocitemth.setNum(Utils.normalize(Utils.getDouble(this.etNum.getText().toString()).doubleValue(), 2));
			defdocitemth.setBignum(new GoodsUnitDAO().getBigNum(defdocitemth.getGoodsid(), defdocitemth.getUnitid(),
					defdocitemth.getNum()));
			// defdocitemCG.setIsexhibition(this.cbExhibition.isChecked());
			defdocitemth.setDiscountratio(
					Utils.normalize(Utils.getDouble(this.etDiscountRatio.getText().toString()).doubleValue(), 2));
			defdocitemth
					.setPrice(Utils.normalizePrice(Utils.getDouble(this.etPrice.getText().toString()).doubleValue()));
			defdocitemth.setSubtotal(
					Utils.normalizeSubtotal(Utils.getDouble(this.etSubtotal.getText().toString()).doubleValue()));
			defdocitemth.setDiscountprice(
					Utils.normalizePrice(Utils.getDouble(this.etDiscountPrice.getText().toString()).doubleValue()));
			defdocitemth.setDiscountsubtotal(Utils
					.normalizeSubtotal(Utils.getDouble(this.etDiscountSubtotal.getText().toString()).doubleValue()));
			defdocitemth.setRemark(this.etRemark.getText().toString());
			if (defdocitemth.getPrice() == 0.0D) {
				// 0 >true显示 赠 false不显示
				defdocitemth.setIsgift(true);
			} else {
				defdocitemth.setIsgift(false);
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_FIRST_USER) {

		} else if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 6:
				// 仓库
				RespGoodsWarehouse localRespGoodsWarehouse2 = (RespGoodsWarehouse) data
						.getSerializableExtra("warehouse");
				this.btnWarehouse.setTag(localRespGoodsWarehouse2.getWarehouseid());
				this.btnWarehouse.setText(localRespGoodsWarehouse2.getWarehousename());
				break;

			default:
				break;
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWarehouse:
			startActivityForResult(new Intent().setClass(this, GoodsWarehouseSearchAct.class).putExtra("goodsid",
					defdocitemth.getGoodsid()), 6);
			break;
		case R.id.btnUnit:
			unitSelect();
			break;
		default:
			break;
		}
	}

	private GoodsUnit goodsUnit;

	private void unitSelect() {

		final List<GoodsUnit> localList = new GoodsUnitDAO().queryGoodsUnits(defdocitemth.getGoodsid());
		String[] arrayOfString = new String[localList.size()];
		for (int i = 0; i < localList.size(); i++) {
			arrayOfString[i] = localList.get(i).getUnitname();
		}
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("单位");
		localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dig, int position) {
				dig.dismiss();
				goodsUnit = localList.get(position);
				if (!goodsUnit.getUnitid().equals(btnUnit.getTag()))
					PDH.show(OutpurDocAddGoodAct.this, new PDH.ProgressCallBack() {
						public void action() {
							ArrayList<ReqStrGetGoodsPrice> localArrayList = new ArrayList<ReqStrGetGoodsPrice>();
							ReqStrGetGoodsPrice getgoodsprice = new ReqStrGetGoodsPrice();
							getgoodsprice.setType(2);
							getgoodsprice.setCustomerid(customerid);
							getgoodsprice.setWarehouseid(btnWarehouse.getTag().toString());
							getgoodsprice.setGoodsid(defdocitemth.getGoodsid());
							getgoodsprice.setUnitid(goodsUnit.getUnitid());
							getgoodsprice.setPrice(0.0D);
							getgoodsprice.setIsdiscount(false);
							localArrayList.add(getgoodsprice);
							String localString = new ServiceGoods().gds_GetMultiGoodsPrice(localArrayList, false,
									false);
							handlerGet.sendMessage(handlerGet.obtainMessage(0, localString));
						}
					});
			}
		});
		localBuilder.show();
		return;
	}

	Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String message = msg.obj.toString();
			ReqStrGetGoodsPrice goodsprice = null;
			if (RequestHelper.isSuccess(message)) {
				switch (msg.what) {
				case 0:
					goodsprice = (ReqStrGetGoodsPrice) JSONUtil.str2list(message, ReqStrGetGoodsPrice.class).get(0);
					btnUnit.setText(goodsUnit.getUnitname());
					btnUnit.setTag(goodsUnit.getUnitid());
					double d1 = Utils.normalize(Utils.getDouble(etNum.getText().toString()).doubleValue(), 2);
					double d2 = Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(), 2);
					double d3 = Utils.normalizePrice(goodsprice.getPrice());
					double d4 = Utils.normalizePrice(d3 * d2);
					etPrice.setText(goodsprice.getPrice() + "");
					etSubtotal.setText(Utils.normalizeSubtotal(d1 * d3) + "");
					etDiscountPrice.setText(d4 + "");
					etDiscountSubtotal.setText(Utils.normalizeSubtotal(d1 * d4) + "");
					etPrice.setTag(etPrice.getText());
					etSubtotal.setTag(etSubtotal.getText());
					etDiscountPrice.setTag(etDiscountPrice.getText());
					etDiscountSubtotal.setTag(etDiscountSubtotal.getText());
					break;
				case 1:
					// if (!TextUtils.isEmptyS(goodsprice.getWarehouseid())) {
					// btnWarehouseGive
					// .setText(new
					// WarehouseDAO().getWarehouse(goodsprice.getWarehouseid()).getName());
					// btnWarehouseGive.setTag(goodsprice.getWarehouseid());
					// btnProBatch.setText(goodsprice.getBatch());
					// try {
					// btnPromotionProDate
					// .setText(
					// Utils.formatDate(
					// new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					// .parse(goodsprice.getProductiondate()).getTime(),
					// "yyyy-MM-dd"));
					// } catch (Exception localException) {
					// localException.printStackTrace();
					// }
					// } else {
					// btnWarehouseGive.setText(null);
					// btnWarehouseGive.setTag(null);
					// PDH.showFail(message);
					// }
					break;
				default:
					break;
				}
			}

		};
	};

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if (hasFocus) {
			// 获取单价
			double d30 = 0.0;
			double d31 = 0.0;
			double d32 = 0.0;
			if (etPrice.getText().toString().length() > 0) {
				d30 = Utils.normalizePrice(Utils.getDouble(etPrice.getText().toString()).doubleValue());
			}
			if (etNum.getText().toString().length() > 0) {
				d31 = Utils.normalize(Utils.getDouble(etNum.getText().toString()).doubleValue(), 2);
			}
			if (etDiscountRatio.getText().toString().length() > 0) {
				d32 = Utils.normalizePrice(
						d30 * Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(), 2));
			}

			double d33 = Utils.normalizeSubtotal(d31 * d30);
			double d34 = Utils.normalizeSubtotal(d31 * d32);
			// 小计
			this.etSubtotal.setText(d33 + "");
			// 折后单价
			this.etDiscountPrice.setText(d32 + "");
			// 折后小计
			this.etDiscountSubtotal.setText(d34 + "");
			this.etPrice.setTag(etPrice.getText());
			this.etDiscountPrice.setTag(etDiscountPrice.getText());
			this.etSubtotal.setTag(etSubtotal.getText());
			this.etDiscountSubtotal.setTag(etDiscountSubtotal.getText());
		}
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle(defdocitemth.getGoodsname());
	}
}
