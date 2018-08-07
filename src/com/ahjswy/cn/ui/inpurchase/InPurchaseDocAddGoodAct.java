package com.ahjswy.cn.ui.inpurchase;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemCG;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.service.ServiceStore;
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

public class InPurchaseDocAddGoodAct extends BaseActivity implements OnClickListener, OnFocusChangeListener {
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
	// private LinearLayout ll_PrmomotionGoods;
	private int position;
	private int positiongive;
	// private String customerid;
	// private Button btnPromotionProDate;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_inpurchase_add_goods);
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
		// title 监听
		btnWarehouse.setOnClickListener(this);
		btnUnit.setOnClickListener(this);
		goodsUnitDAO = new GoodsUnitDAO();

	}

	DefDocItemCG defdocitemCG;

	private void initDate() {
		// customerid = getIntent().getStringExtra("customerid");
		position = getIntent().getIntExtra("position", -1);
		defdocitemCG = ((DefDocItemCG) getIntent().getSerializableExtra("docitem"));
		positiongive = getIntent().getIntExtra("positiongive", -1);
		doccg = (Def_Doc) getIntent().getSerializableExtra("doc");
		tvBarcode.setText(defdocitemCG.getBarcode());
		tvSpecification.setText(defdocitemCG.getSpecification());
		btnWarehouse.setText(defdocitemCG.getWarehousename());
		btnWarehouse.setTag(defdocitemCG.getWarehouseid());
		btnUnit.setText(defdocitemCG.getUnitname());
		btnUnit.setTag(defdocitemCG.getUnitid());
		etNum.setText(defdocitemCG.getNum() == 0 ? "" : defdocitemCG.getNum() + "");
		etPrice.setText(defdocitemCG.getPrice() + "");
		etSubtotal.setText(defdocitemCG.getSubtotal() + "");
		etDiscountRatio.setText(defdocitemCG.getDiscountratio() + "");
		etDiscountPrice.setText(defdocitemCG.getDiscountprice() + "");
		etDiscountSubtotal.setText(defdocitemCG.getDiscountsubtotal() + "");
		etRemark.setText(defdocitemCG.getRemark());
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
		localIntent.putExtra("position", position);
		localIntent.putExtra("docitem", defdocitemCG);
		localIntent.putExtra("positiongive", this.positiongive);
		setResult(RESULT_OK, localIntent);
		finish();

	}

	private void fillItem() {
		if (TextUtils.isEmptyS(this.btnWarehouse.getTag().toString())) {
			defdocitemCG.setWarehouseid(this.btnWarehouse.getTag().toString());
			defdocitemCG.setWarehousename(this.btnWarehouse.getText().toString());
			if (TextUtils.isEmptyS(this.btnUnit.getTag().toString())) {
				defdocitemCG.setUnitid(this.btnUnit.getTag().toString());
			}
			defdocitemCG.setUnitname(this.btnUnit.getText().toString());
			defdocitemCG.setNum(Utils.normalize(Utils.getDouble(this.etNum.getText().toString()).doubleValue(), 2));
			defdocitemCG.setBignum(new GoodsUnitDAO().getBigNum(defdocitemCG.getGoodsid(), defdocitemCG.getUnitid(),
					defdocitemCG.getNum()));
			defdocitemCG.setDiscountratio(
					Utils.normalize(Utils.getDouble(this.etDiscountRatio.getText().toString()).doubleValue(), 2));
			defdocitemCG
					.setPrice(Utils.normalizePrice(Utils.getDouble(this.etPrice.getText().toString()).doubleValue()));
			defdocitemCG.setSubtotal(
					Utils.normalizeSubtotal(Utils.getDouble(this.etSubtotal.getText().toString()).doubleValue()));
			defdocitemCG.setDiscountprice(
					Utils.normalizePrice(Utils.getDouble(this.etDiscountPrice.getText().toString()).doubleValue()));
			defdocitemCG.setDiscountsubtotal(Utils
					.normalizeSubtotal(Utils.getDouble(this.etDiscountSubtotal.getText().toString()).doubleValue()));
			defdocitemCG.setRemark(this.etRemark.getText().toString());
			if (defdocitemCG.getPrice() == 0.0D) {
				// 0 >true显示 赠 false不显示
				defdocitemCG.setIsgift(true);
			} else {
				defdocitemCG.setIsgift(false);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWarehouse:
			startActivityForResult(new Intent().setClass(this, GoodsWarehouseSearchAct.class).putExtra("goodsid",
					defdocitemCG.getGoodsid()), 6);
			break;
		case R.id.btnUnit:
			unitSelect();
			break;
		}
	}

	private GoodsUnit goodsUnit;

	private void unitSelect() {
		final List<GoodsUnit> localList = goodsUnitDAO.queryGoodsUnits(defdocitemCG.getGoodsid());
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
					PDH.show(InPurchaseDocAddGoodAct.this, new PDH.ProgressCallBack() {
						public void action() {
							defdocitemCG.setUnitid(goodsUnit.getUnitid());
							defdocitemCG.setUnitname(goodsUnit.getUnitname());
							setItemsGoods(defdocitemCG);
							handlerGet.sendEmptyMessage(0);
						}
					});
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

	/**
	 * 获取每个商品的 价格并设置给当前商品
	 * 
	 * @param defdocitemcg
	 */
	private boolean setItemsGoods(DefDocItemCG defdocitemcg) {
		List<DefDocItemCG> itemCGs = new ArrayList<DefDocItemCG>();
		itemCGs.add(defdocitemcg);
		String goodsPrice = new ServiceStore().GetGoodsPrice(doccg, JSONUtil.toJSONString(itemCGs));
		if (TextUtils.isEmptyS(goodsPrice)) {
			JSONUtil.readValue2(goodsPrice);
			double price = Double.parseDouble(JSONUtil.Data);
			if (price > 0) {
				defdocitemcg.setIsgift(false);
			} else {
				defdocitemcg.setIsgift(true);
			}
			defdocitemcg.setPrice(price);// 单价
			defdocitemcg.setDiscountprice(price * defdocitemcg.getDiscountratio());// 折后单价
			defdocitemcg.setDiscountsubtotal(price * defdocitemcg.getDiscountratio() * defdocitemcg.getNum());
		} else {
			defdocitemcg.setPrice(0);// 单价
			defdocitemcg.setDiscountprice(0);
			defdocitemcg.setDiscountratio(1.0);
			defdocitemcg.setDiscountsubtotal(0);
		}

		return false;
	}

	// 单价 大小比例转换
	Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (defdocitemCG != null) {
				switch (msg.what) {
				case 0:
					btnUnit.setText(defdocitemCG.getUnitname());
					btnUnit.setTag(defdocitemCG.getUnitid());
					double d1 = Utils.normalize(Utils.getDouble(etNum.getText().toString()).doubleValue(), 2);
					double d2 = Utils.normalize(Utils.getDouble(etDiscountRatio.getText().toString()).doubleValue(), 2);
					double d3 = Utils.normalizePrice(defdocitemCG.getPrice());
					double d4 = Utils.normalizePrice(d3 * d2);
					etPrice.setText(defdocitemCG.getPrice() + "");
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
	private Def_Doc doccg;
	private GoodsUnitDAO goodsUnitDAO;

	@Override
	public void setActionBarText() {
		getActionBar().setTitle(defdocitemCG.getGoodsname());
	}
}
