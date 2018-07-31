package com.ahjswy.cn.ui.outgoods;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.cldb.Sz_stockwarn;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.request.ReqStrGetGoodsPrice;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.GoodsBatchSearchAct;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class OutDocAddGoodAct extends BaseActivity
		implements OnClickListener, OnFocusChangeListener, OnCheckedChangeListener {
	private DefDocItemXS docitem;
	// 条码
	private TextView tvBarcode;
	// 规格
	private TextView tvSpecification;
	// 仓库
	private Button btnWarehouse;
	// 单位
	private Button btnUnit;
	// 批次
	private Button btnBatch;
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
	// 陈列品
	// private CheckBox cbExhibition;
	// 备注
	private EditText etRemark;
	// 搭赠 赠品
	// private Button btnGoodsGive;
	// 搭赠 仓库
	// private Button btnWarehouseGive;
	// 搭赠批次
	// private Button btnProBatch;
	// 搭赠时间
	// private Button btnProDate;
	// 搭赠单位
	// private Button btnUnitGive;
	// 搭赠数量
	// private EditText etNumGive;
	// // 搭赠备注
	// private EditText etRemarkGive;
	// 搭赠 listview
	// private LinearLayout ll_PrmomotionGoods;
	private int position;
	private int positiongive;
//	private Button btnPromotionProDate;
	// private TextView tvSaleCue;
	private String customerid;
	private DefDocItemXS docitemgive;
	private TextView tvStock;
	private Sz_stockwarn stockwarn;
	private double stocknum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_outdoc_add_goods);
		initView();
		initDate();
	}

	private void initView() {
		tvBarcode = (TextView) findViewById(R.id.tvBarcode);
		tvSpecification = (TextView) findViewById(R.id.tvSpecification);
		tvStock = (TextView) findViewById(R.id.tvStock);
		btnWarehouse = (Button) findViewById(R.id.btnWarehouse);
		btnUnit = ((Button) findViewById(R.id.btnUnit));
		// 批次
		btnBatch = (Button) findViewById(R.id.btnBatch);
		// 日期
		// btnProDate = (Button) findViewById(R.id.btnProDate);
		etNum = (EditText) findViewById(R.id.etNum);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etSubtotal = (EditText) findViewById(R.id.etSubtotal);
		etDiscountRatio = (EditText) findViewById(R.id.etDiscountRatio);
		etDiscountPrice = (EditText) findViewById(R.id.etDiscountPrice);
		etDiscountSubtotal = (EditText) findViewById(R.id.etDiscountSubtotal);
		// cbExhibition = (CheckBox) findViewById(R.id.cbExhibition);
		etRemark = (EditText) findViewById(R.id.etRemark);
		// ll_PrmomotionGoods = (LinearLayout)
		// findViewById(R.id.ll_PrmomotionGoods);
		// 搭赠
		// btnWarehouseGive = (Button) findViewById(R.id.btnWarehouseGive);
		// btnGoodsGive = (Button) findViewById(R.id.btnGoodsGive);
		// 搭赠批次
		// btnProBatch = (Button) findViewById(R.id.btnProBatch);
		// 搭赠日期
//		btnPromotionProDate = (Button) findViewById(R.id.btnPromotionProDate);
		// btnUnitGive = (Button) findViewById(R.id.btnUnitGive);
		// dao = new GoodsUnitDAO();

		// title 监听
		btnWarehouse.setOnClickListener(this);
		btnBatch.setOnClickListener(this);
		// btnProDate.setOnClickListener(this);
		btnUnit.setOnClickListener(this);
		// cbExhibition.setOnCheckedChangeListener(this);
		// 搭赠
		// btnWarehouseGive.setOnClickListener(this);
		// btnGoodsGive.setOnClickListener(this);
		// btnProBatch.setOnClickListener(this);
//		btnPromotionProDate.setOnClickListener(this);
		// btnUnitGive.setOnClickListener(this);
		stockwarn = new Sz_stockwarn();
	}

	private void initDate() {
		customerid = getIntent().getStringExtra("customerid");
		position = getIntent().getIntExtra("position", -1);
		docitem = ((DefDocItemXS) getIntent().getSerializableExtra("docitem"));
		positiongive = getIntent().getIntExtra("positiongive", -1);
		tvBarcode.setText(this.docitem.getBarcode());
		tvSpecification.setText(this.docitem.getSpecification());
		btnBatch.setText(this.docitem.getBatch());
		btnWarehouse.setText(docitem.getWarehousename());
		btnWarehouse.setTag(this.docitem.getWarehouseid());
		btnUnit.setText(docitem.getUnitname());
		btnUnit.setTag(docitem.getUnitid());
		etNum.setText((docitem.getNum() == 0 ? "" : docitem.getNum() + ""));
		etPrice.setText(docitem.getPrice() + "");
		etSubtotal.setText(docitem.getSubtotal() + "");
		etDiscountRatio.setText(docitem.getDiscountratio() + "");
		etDiscountPrice.setText(docitem.getDiscountprice() + "");
		etDiscountSubtotal.setText(docitem.getDiscountsubtotal() + "");
		etRemark.setText(docitem.getRemark());
		this.btnUnit.setOnClickListener(this);
		this.btnWarehouse.setOnClickListener(this);
		// this.btnGoodsGive.setOnClickListener(this);
		// this.btnWarehouseGive.setOnClickListener(this);
		// this.btnUnitGive.setOnClickListener(this);
		this.etNum.setOnFocusChangeListener(this);
		this.etPrice.setOnFocusChangeListener(this);
		this.etSubtotal.setOnFocusChangeListener(this);
		this.etDiscountRatio.setOnFocusChangeListener(this);
		this.etDiscountPrice.setOnFocusChangeListener(this);
		this.etDiscountSubtotal.setOnFocusChangeListener(this);
		if ((this.docitem.isIspromotion()) && (this.docitem.getPromotiontype() == 0)) {
			this.etPrice.setCursorVisible(false);
			this.etPrice.setFocusable(false);
			this.etPrice.setFocusableInTouchMode(false);
			this.etSubtotal.setCursorVisible(false);
			this.etSubtotal.setFocusable(false);
			this.etSubtotal.setFocusableInTouchMode(false);
			this.etDiscountRatio.setCursorVisible(false);
			this.etDiscountRatio.setFocusable(false);
			this.etDiscountRatio.setFocusableInTouchMode(false);
			this.etDiscountPrice.setCursorVisible(false);
			this.etDiscountPrice.setFocusable(false);
			this.etDiscountPrice.setFocusableInTouchMode(false);
			this.etDiscountSubtotal.setCursorVisible(false);
			this.etDiscountSubtotal.setFocusable(false);
			this.etDiscountSubtotal.setFocusableInTouchMode(false);
			this.btnUnit.setEnabled(false);
		}
		PDH.show(this, "库存查询...", new ProgressCallBack() {

			@Override
			public void action() {
				stocknum = stockwarn.queryStockNum(docitem.getWarehouseid(), docitem.getGoodsid());
				runOnUiThread(new Runnable() {
					public void run() {
						tvStock.setText("库存:" + DocUtils.Stocknum(stocknum, docitem.getGoodsid(), docitem.getUnitid(),
								docitem.getUnitname()));
					}
				});

			}
		});
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
			OptionsItemSelected();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWarehouse:
			startActivityForResult(new Intent().setClass(this, GoodsWarehouseSearchAct.class).putExtra("goodsid",
					this.docitem.getGoodsid()), 6);
			break;
		case R.id.btnUnit:
			unitSelect();
			break;
		// 批次
		case R.id.btnBatch:
			startActivityForResult(new Intent().setClass(this, GoodsBatchSearchAct.class)
					.putExtra("goodsid", this.docitem.getGoodsid())
					.putExtra("warehouseid", this.btnWarehouse.getTag().toString()), 2);
			break;
		// 搭赠仓库
		case R.id.btnWarehouseGive:
			Intent intent = new Intent(this, GoodsWarehouseSearchAct.class);
			intent.putExtra("goodsid", docitemgive.getGoodsid());
			intent.putExtra("position", "7");
			startActivityForResult(intent, 7);
			break;
		// 搭赠 赠品
		case R.id.btnGoodsGive:
			// startActivityForResult(new Intent().setClass(this,
			// GoodsSearchAct.class), 0);
			break;
		// 搭赠批次
		// case R.id.btnProBatch:
		// startActivityForResult(new Intent().setClass(this,
		// GoodsBatchSearchAct.class)
		// .putExtra("goodsid", this.btnGoodsGive.getTag().toString())
		// .putExtra("warehouseid",
		// this.btnWarehouseGive.getTag().toString()), 3);
		// break;
		// 搭赠单位
		case R.id.btnUnitGive:
			// if (this.btnGoodsGive.getTag() != null) {
			// unitGiveSelect(btnGoodsGive.getTag().toString());
			// }
			break;
		}
	}

	private void OptionsItemSelected() {
		// 判断焦点
		// if (this.etNum.hasFocus()) {
		// this.etNum.clearFocus();
		// }
		// if (this.etPrice.hasFocus()) {
		// this.etPrice.clearFocus();
		// }
		// if (this.etSubtotal.hasFocus()) {
		// this.etSubtotal.clearFocus();
		// }
		// if (this.etDiscountRatio.hasFocus()) {
		// this.etDiscountRatio.clearFocus();
		// }
		// if (this.etDiscountPrice.hasFocus()) {
		// this.etDiscountPrice.clearFocus();
		// }
		// if (this.etDiscountSubtotal.hasFocus()) {
		// this.etDiscountSubtotal.clearFocus();
		// }
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
		localIntent.putExtra("docitem", this.docitem);
		localIntent.putExtra("positiongive", this.positiongive);
		localIntent.putExtra("docitemgive", this.docitemgive);
		setResult(RESULT_OK, localIntent);
		finish();
	}

	private void fillItem() {
		if (TextUtils.isEmptyS(this.btnWarehouse.getTag().toString())) {
			docitem.setWarehouseid(this.btnWarehouse.getTag().toString());
			docitem.setWarehousename(this.btnWarehouse.getText().toString());
		} else {
			this.docitem.setWarehouseid(null);
			this.docitem.setWarehousename("");
		}

		if (TextUtils.isEmptyS(this.btnUnit.getTag().toString())) {
			docitem.setUnitid(this.btnUnit.getTag().toString());
			docitem.setUnitname(this.btnUnit.getText().toString());
		} else {
			docitem.setUnitid(null);
			docitem.setUnitname("");
		}
		this.docitem.setNum(Utils.normalize(Utils.getDouble(this.etNum.getText().toString()).doubleValue(), 2));
		this.docitem.setBignum(new GoodsUnitDAO().getBigNum(this.docitem.getGoodsid(), this.docitem.getUnitid(),
				this.docitem.getNum()));
		// this.docitem.setIsexhibition(this.cbExhibition.isChecked());
		this.docitem.setDiscountratio(
				Utils.normalize(Utils.getDouble(this.etDiscountRatio.getText().toString()).doubleValue(), 2));
		// if (this.docitem.isIsusebatch()) {
		// // 批次 日期
		// this.docitem.setBatch(this.btnBatch.getText().toString());
		// this.docitem.setProductiondate(this.btnProDate.getText() + "
		// 00:00:00");
		// }
		this.docitem.setPrice(Utils.normalizePrice(Utils.getDouble(this.etPrice.getText().toString()).doubleValue()));
		this.docitem.setSubtotal(
				Utils.normalizeSubtotal(Utils.getDouble(this.etSubtotal.getText().toString()).doubleValue()));
		this.docitem.setDiscountprice(
				Utils.normalizePrice(Utils.getDouble(this.etDiscountPrice.getText().toString()).doubleValue()));
		this.docitem.setDiscountsubtotal(
				Utils.normalizeSubtotal(Utils.getDouble(this.etDiscountSubtotal.getText().toString()).doubleValue()));
		this.docitem.setRemark(this.etRemark.getText().toString());
		docitem.setIsgift(docitem.getPrice() == 0.0D ? true : false);
		if (docitem.isIsexhibition()) {
			this.docitemgive = null;
			this.docitem.setIspromotion(false);
			this.docitem.setPromotiontype(-1);
			this.docitem.setPromotiontypename(null);
			this.docitem.setPrice(0.0D);
			this.docitem.setSubtotal(0.0D);
			this.docitem.setDiscountprice(
					Utils.normalizePrice(Utils.getDouble(this.etDiscountPrice.getText().toString()).doubleValue()));
			this.docitem.setDiscountsubtotal(0.0D);
		}
		if ((this.docitem.isIspromotion()) && (this.docitem.getPromotiontype() == 0)) {
			this.docitemgive = null;
			return;
		}

	}

	private void setUseAble(boolean paramBoolean) {
		this.etPrice.setCursorVisible(paramBoolean);
		this.etPrice.setFocusable(paramBoolean);
		this.etPrice.setFocusableInTouchMode(paramBoolean);
		this.etSubtotal.setCursorVisible(paramBoolean);
		this.etSubtotal.setFocusable(paramBoolean);
		this.etSubtotal.setFocusableInTouchMode(paramBoolean);
		this.etDiscountPrice.setCursorVisible(paramBoolean);
		this.etDiscountPrice.setFocusable(paramBoolean);
		this.etDiscountPrice.setFocusableInTouchMode(paramBoolean);
		this.etDiscountRatio.setCursorVisible(paramBoolean);
		this.etDiscountRatio.setFocusable(paramBoolean);
		this.etDiscountRatio.setFocusableInTouchMode(paramBoolean);
		this.etDiscountSubtotal.setCursorVisible(paramBoolean);
		this.etDiscountSubtotal.setFocusable(paramBoolean);
		this.etDiscountSubtotal.setFocusableInTouchMode(paramBoolean);
	}

	// private GoodsUnit goodsUnit;

	private void unitGiveSelect(String paramString) {
		// final List<GoodsUnit> localList = new
		// GoodsUnitDAO().queryGoodsUnits(paramString);
		// String[] arrayOfString = new String[localList.size()];
		// for (int i = 0;; i++) {
		// if (i >= localList.size()) {
		// AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		// localBuilder.setTitle("搭赠商品单位");
		// localBuilder.setItems(arrayOfString, new
		// DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialogInterface, int
		// paramAnonymousInt) {
		// dialogInterface.dismiss();
		// goodsUnit = ((GoodsUnit) localList.get(paramAnonymousInt));
		// if (!goodsUnit.getUnitid().equals(btnUnitGive.getTag())) {
		// btnUnitGive.setText(goodsUnit.getUnitname());
		// btnUnitGive.setTag(goodsUnit.getUnitid());
		// }
		//
		// }
		// });
		// localBuilder.show();
		// return;
		// }
		// arrayOfString[i] = ((GoodsUnit) localList.get(i)).getUnitname();
		// }
	}

	private void setPrice(double price) {
		double num = Utils.normalize(Utils.getDouble(etNum.getText().toString()).doubleValue(), 2);
		double discountratio = Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(), 2);
		double prices = Utils.normalizePrice(price);
		double discountprice = Utils.normalizePrice(prices * discountratio);
		etPrice.setText(price + "");
		etSubtotal.setText(Utils.normalizeSubtotal(num * prices) + "");
		etDiscountPrice.setText(discountprice + "");
		etDiscountSubtotal.setText(Utils.normalizeSubtotal(num * discountprice) + "");
		etPrice.setTag(etPrice.getText());
		etSubtotal.setTag(etSubtotal.getText());
		etDiscountPrice.setTag(etDiscountPrice.getText());
		etDiscountSubtotal.setTag(etDiscountSubtotal.getText());
	}

	Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String message = msg.obj.toString();
			ReqStrGetGoodsPrice goodsprice = null;
			if (RequestHelper.isSuccess(message)) {
				switch (msg.what) {
				case 0:
					goodsprice = (ReqStrGetGoodsPrice) JSONUtil.parseArray(message, ReqStrGetGoodsPrice.class).get(0);
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
				case 2:

					break;
				default:
					break;
				}
			}

		};
	};

	// TODO 选择单位
	private void unitSelect() {
		final List<GoodsUnit> localList = new GoodsUnitDAO().queryGoodsUnits(this.docitem.getGoodsid());
		String[] arrayOfString = new String[localList.size()];
		for (int i = 0; i < localList.size(); i++) {
			arrayOfString[i] = localList.get(i).getUnitname();
		}
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("单位");
		localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dig, int position) {
				dig.dismiss();
				GoodsUnit goodsUnit = localList.get(position);
				if (!goodsUnit.getUnitid().equals(btnUnit.getTag())) {
					btnUnit.setText(goodsUnit.getUnitname());
					btnUnit.setTag(goodsUnit.getUnitid());
					docitem.unit = goodsUnit;
					tvStock.setText("库存:" + DocUtils.Stocknum(stocknum, docitem.unit));
					double goodsPrice = DocUtils.getGoodsPrice(customerid, goodsUnit.getGoodsid(),
							goodsUnit.getUnitid());
					setPrice(goodsPrice);
					// String stock = setItemStock(docitem.getStocknum(),
					// goodsUnit.getUnitname());
					// tvStock.setText("库存:" + stock);
					// PDH.show(OutDocAddGoodAct.this, new
					// PDH.ProgressCallBack() {
					// public void action() {
					// ArrayList<ReqStrGetGoodsPrice> localArrayList = new
					// ArrayList<ReqStrGetGoodsPrice>();
					// ReqStrGetGoodsPrice getgoodsprice = new
					// ReqStrGetGoodsPrice();
					// getgoodsprice.setType(2);
					// getgoodsprice.setCustomerid(OutDocAddGoodAct.this.customerid);
					// getgoodsprice.setWarehouseid(OutDocAddGoodAct.this.btnWarehouse.getTag().toString());
					// getgoodsprice.setGoodsid(OutDocAddGoodAct.this.docitem.getGoodsid());
					// getgoodsprice.setUnitid(OutDocAddGoodAct.this.goodsUnit.getUnitid());
					// getgoodsprice.setPrice(0.0D);
					// getgoodsprice.setIsdiscount(false);
					// localArrayList.add(getgoodsprice);
					// String localString = new
					// ServiceGoods().gds_GetMultiGoodsPrice(localArrayList,
					// false,
					// false);
					// handlerGet.sendMessage(handlerGet.obtainMessage(0,
					// localString));
					// }
					// });
				}
			}
		});
		localBuilder.show();
		return;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_FIRST_USER) {

		} else if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				// this.btnWarehouseGive.setTag(paramIntent.getStringExtra("warehouseid"));
				// this.btnWarehouseGive.setText(paramIntent.getStringExtra("warehousename"));
				break;
			case 2:
				// // 批次
				// RespGoodsBatchEntity localRespGoodsBatchEntity2 =
				// (RespGoodsBatchEntity) data
				// .getSerializableExtra("goodsbatch");
				// this.btnWarehouse.setTag(localRespGoodsBatchEntity2.getWarehouseId());
				// this.btnWarehouse.setText(localRespGoodsBatchEntity2.getWarehouseName());
				// this.btnBatch.setText(localRespGoodsBatchEntity2.getBatch());
				// try {
				// this.btnProDate
				// .setText(Utils.formatDate(
				// new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .parse(localRespGoodsBatchEntity2.getProductiondate()).getTime(),
				// "yyyy-MM-dd"));
				// } catch (ParseException e1) {
				// btnProDate.setText("");
				// e1.printStackTrace();
				// }
				break;

			case 3:
				// RespGoodsBatchEntity localRespGoodsBatchEntity1 =
				// (RespGoodsBatchEntity) data
				// .getSerializableExtra("goodsbatch");
				// this.btnWarehouseGive.setTag(localRespGoodsBatchEntity1.getWarehouseId());
				// this.btnWarehouseGive.setText(localRespGoodsBatchEntity1.getWarehouseName());
				// this.btnProBatch.setText(localRespGoodsBatchEntity1.getBatch());
				// try {
				// this.btnPromotionProDate
				// .setText(Utils.formatDate(
				// new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .parse(localRespGoodsBatchEntity1.getProductiondate()).getTime(),
				// "yyyy-MM-dd"));
				// } catch (ParseException e1) {
				// btnPromotionProDate.setText("");
				// e1.printStackTrace();
				// }
				break;
			case 6:
				// 仓库
				RespGoodsWarehouse warehouse = (RespGoodsWarehouse) data.getSerializableExtra("warehouse");
				this.btnWarehouse.setTag(warehouse.getWarehouseid());
				this.btnWarehouse.setText(warehouse.getWarehousename());
				stocknum = stockwarn.queryStockNum(warehouse.getWarehouseid(), docitem.getGoodsid());
				tvStock.setText("库存:" + DocUtils.Stocknum(stocknum, docitem.getGoodsid(),
						btnUnit.getTag() != null ? btnUnit.getTag().toString() : "", btnUnit.getText().toString()));

				// this.btnBatch.setText(localRespGoodsWarehouse2.getBatch());
				// btnProDate
				// .setText(
				// Utils.formatDate(
				// new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .parse(localRespGoodsWarehouse2.getProductiondate()).getTime(),
				// "yyyy-MM-dd"));
				break;
			case 7:
				// RespGoodsWarehouse localRespGoodsWarehouse1 =
				// (RespGoodsWarehouse) data
				// .getSerializableExtra("warehouse");
				// this.btnWarehouseGive.setTag(localRespGoodsWarehouse1.getWarehouseid());
				// this.btnWarehouseGive.setText(localRespGoodsWarehouse1.getWarehousename());
				// this.btnProBatch.setText(localRespGoodsWarehouse1.getBatch());
				// try {
				// this.btnPromotionProDate
				// .setText(Utils.formatDate(
				// new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .parse(localRespGoodsWarehouse1.getProductiondate()).getTime(),
				// "yyyy-MM-dd"));
				// } catch (ParseException e) {
				// btnPromotionProDate.setText("");
				// e.printStackTrace();
				// }
				break;
			default:
				break;
			}
		}
	}

	// 加上动态监听
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
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			// this.ll_PrmomotionGoods.setVisibility(8);
			setUseAble(false);
			this.etPrice.setText("0.00");
			this.etDiscountRatio.setText("1.00");
			this.etDiscountPrice.setText("0.00");
			this.etDiscountSubtotal.setText("0.00");
			this.etSubtotal.setText("0.00");
			this.etPrice.setTag(this.etPrice.getText());
			this.etSubtotal.setTag(this.etSubtotal.getText());
			this.etDiscountRatio.setTag(this.etDiscountRatio.getText());
			this.etDiscountPrice.setTag(this.etDiscountPrice.getText());
			this.etDiscountSubtotal.setTag(this.etDiscountSubtotal.getText());
			return;
		}
		// this.ll_PrmomotionGoods.setVisibility(0);
		setUseAble(true);
	}

	/**
	 * * 监听Back键按下事件,方法2: * 注意: * 返回值表示:是否能完全处理该事件 * 在此处返回false,所以会继续传播该事件. *
	 * 
	 */
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
