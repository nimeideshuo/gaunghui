package com.ahjswy.cn.ui.out_in_goods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.bean.DefDocItemDD;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.request.ReqStrGetGoodsPrice;
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
import android.widget.TextView;

/**
 * 销售订单 商品详细
 * 
 * @author Administrator
 *
 */
public class OutInDocAddGoodAct extends BaseActivity implements OnFocusChangeListener, OnClickListener {
	// item_outin_addgoods
	// 条码
	private TextView tvBarcode;
	// 规格
	private TextView tvSpecification;
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
	private DefDocItemDD docitem;
	private EditText etRemark;
	private int position;
	Def_Doc doccg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_outin_addgoods);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		tvBarcode = ((TextView) findViewById(R.id.tvBarcode));
		tvSpecification = ((TextView) findViewById(R.id.tvSpecification));
		btnUnit = ((Button) findViewById(R.id.btnUnit));
		etNum = (EditText) findViewById(R.id.etNum);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etSubtotal = (EditText) findViewById(R.id.etSubtotal);
		etDiscountRatio = (EditText) findViewById(R.id.etDiscountRatio);
		etDiscountPrice = (EditText) findViewById(R.id.etDiscountPrice);
		etDiscountSubtotal = (EditText) findViewById(R.id.etDiscountSubtotal);
		etRemark = (EditText) findViewById(R.id.etRemark);
	}

	private void initData() {
		customerid = getIntent().getStringExtra("customerid");
		position = getIntent().getIntExtra("position", 0);
		docitem = (DefDocItemDD) getIntent().getExtras().get("docitem");
		doccg = (Def_Doc) getIntent().getExtras().get("doc");
		tvBarcode.setText(this.docitem.getBarcode());
		tvSpecification.setText(this.docitem.getSpecification());
		btnUnit.setText(docitem.getUnitname());
		btnUnit.setTag(docitem.getUnitid());
		etNum.setText(docitem.getNum() == 0 ? "" : docitem.getNum() + "");
		etPrice.setText(docitem.getPrice() + "");
		etSubtotal.setText(docitem.getSubtotal() + "");
		etDiscountRatio.setText(docitem.getDiscountratio() + "");
		etDiscountPrice.setText(docitem.getDiscountprice() + "");
		etDiscountSubtotal.setText(docitem.getDiscountsubtotal() + "");
		etRemark.setText(docitem.getRemark());
	}

	private void initListener() {
		this.btnUnit.setOnClickListener(this);
		this.etNum.setOnFocusChangeListener(this);
		this.etPrice.setOnFocusChangeListener(this);
		this.etSubtotal.setOnFocusChangeListener(this);
		this.etDiscountRatio.setOnFocusChangeListener(this);
		this.etDiscountPrice.setOnFocusChangeListener(this);
		this.etDiscountSubtotal.setOnFocusChangeListener(this);
	}

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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWarehouse:
			startActivityForResult(new Intent().setClass(this, GoodsWarehouseSearchAct.class).putExtra("goodsid",
					this.docitem.getGoodsid()), 1);
			break;
		case R.id.btnUnit:
			unitSelect();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		case 0:
			onFocusChange(null, true);
			startActivity();
			break;
		}

		return super.onOptionsItemSelected(menuItem);
	}

	private void startActivity() {

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
		localIntent.putExtra("docitem", docitem);
		setResult(RESULT_OK, localIntent);
		finish();
	}

	private void fillItem() {

		if (TextUtils.isEmptyS(this.btnUnit.getTag().toString())) {
			this.docitem.setUnitid(this.btnUnit.getTag().toString());
			this.docitem.setUnitname(this.btnUnit.getText().toString());
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
		docitem.setIsgift(docitem.getPrice() == 0.0D ? true : false);
		// TODO 商品详细代码待补齐
		// docitem.setCostprice(0.0D);
		// docitem.setRemark("");
		// docitem.setRversion(0L);
		// docitem.setIsdiscount(false);
		// docitem.setIsusebatch(docitem.isIsusebatch());
	}

	private GoodsUnit goodsUnit;

	private void unitSelect() {
		final List<GoodsUnit> localList = new GoodsUnitDAO().queryGoodsUnits(this.docitem.getGoodsid());
		String[] arrayOfString = new String[localList.size()];
		for (int i = 0; i < localList.size(); i++) {
			arrayOfString[i] = localList.get(i).getUnitname();
		}
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("单位");
		localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dig, int paramAnonymousInt) {
				dig.dismiss();
				goodsUnit = localList.get(paramAnonymousInt);
				if (!goodsUnit.getUnitid().equals(btnUnit.getTag()))
					PDH.show(OutInDocAddGoodAct.this, new PDH.ProgressCallBack() {
						public void action() {

							ArrayList<ReqStrGetGoodsPrice> localArrayList = new ArrayList<ReqStrGetGoodsPrice>();
							ReqStrGetGoodsPrice getgoodsprice = new ReqStrGetGoodsPrice();
							getgoodsprice.setType(2);
							getgoodsprice.setCustomerid(customerid);
							// getgoodsprice.setWarehouseid(OutDocAddGoodAct.this.btnWarehouse.getTag().toString());
							getgoodsprice.setGoodsid(docitem.getGoodsid());
							getgoodsprice.setUnitid(goodsUnit.getUnitid());
							getgoodsprice.setPrice(0.0D);
							getgoodsprice.setIsdiscount(false);
							localArrayList.add(getgoodsprice);
							String localString = new ServiceGoods().gds_GetMultiGoodsPrice(localArrayList, false,
									false);

							// ====================================
							// docitem.setUnitid(goodsUnit.getUnitid());
							// docitem.setUnitname(goodsUnit.getUnitname());
							// setItemsGoods(docitem);
							// handlerGet.sendEmptyMessage(0);
							handlerGet.sendMessage(handlerGet.obtainMessage(0, localString));
						}
					});
			}
		});
		localBuilder.show();
	}

	// 单价 大小比例转换
	Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String message = msg.obj.toString();
			ReqStrGetGoodsPrice goodsprice = null;
			if (docitem != null && RequestHelper.isSuccess(message)) {
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
				}
			}

		};
	};

	/**
	 * 获取每个商品的 价格并设置给当前商品
	 * 
	 * @param item
	 */
	// private boolean setItemsGoods(DefDocItemDD item) {
	// if (doccg == null || item == null) {
	// showError("商品信息NULL");
	// return true;
	// }
	// GoodsUnit basicUnit = new
	// GoodsUnitDAO().getBasicUnit(item.getGoodsid());// 基本单位
	// if (basicUnit.getUnitid().equals(item.getUnitid())) {
	// String goodsPrice = new
	// ServiceStore().GetCustomerPrice(doccg.getCustomerid(),
	// basicUnit.getGoodsid(),
	// basicUnit.getUnitid());
	// if (TextUtils.isEmptyS(goodsPrice)) {
	// JSONUtil.readValue2(goodsPrice);
	// if (JSONUtil.Result) {
	// String data = JSONUtil.Data;
	// List<ReqCustomerPrice> listPrice = JSONUtil.str2list(data,
	// ReqCustomerPrice.class);
	// double price = 0;
	// for (int i = 0; i < listPrice.size(); i++) {
	// price = listPrice.get(i).getGoodsprice();
	// }
	// if (price > 0) {
	// item.setIsgift(false);
	// } else {
	// item.setIsgift(true);
	// }
	// item.setPrice(price);// 单价
	// item.setDiscountprice(price * item.getDiscountratio());// 折后单价
	// item.setDiscountsubtotal(price * item.getDiscountratio() *
	// item.getNum());// 折后总价
	// GoodsUnitDAO localGoodsUnitDAO = new GoodsUnitDAO();// 设置基本单位
	// String bigNum = localGoodsUnitDAO.getBigNum(item.getGoodsid(),
	// item.getUnitid(), item.getNum());
	// item.setBignum(bigNum);
	// }
	//
	// } else {
	// item.setIsgift(true);
	// item.setPrice(0);// 单价
	// item.setSubtotal(0);
	// item.setDiscountprice(0);
	// item.setDiscountratio(1.0);
	// item.setDiscountsubtotal(0);
	// }
	// } else {
	// item.setIsgift(true);
	// item.setPrice(0);// 单价
	// item.setSubtotal(0);
	// item.setDiscountprice(0);
	// item.setDiscountratio(1.0);
	// item.setDiscountsubtotal(0);
	// }
	//
	// return false;
	// }

	@Override
	public void setActionBarText() {
		getActionBar().setTitle(docitem.getGoodsname());
	}

}
