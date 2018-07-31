package com.ahjswy.cn.ui.inventory;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemPD;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.request.ReqStrGetGoodsPricePD;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.Activity;
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
 * 盘点单详情
 * 
 * @author Administrator
 *
 */
public class InventoryAddGoodAct extends BaseActivity implements OnClickListener, OnFocusChangeListener {
	private EditText etStockNum;
	private EditText etNum;
	private EditText etNetNum;
	private EditText etRemark;
	private TextView tvBarcode;
	private TextView tvSpecification;
	private Button btnBatch;
	private Button btnUnit;
	private String warehouseid;
	private int position;
	private DefDocItemPD docitem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_inventory_add_goods);
		initView();
		initData();
	}

	private void initView() {
		tvBarcode = (TextView) findViewById(R.id.tvBarcode);
		tvSpecification = (TextView) findViewById(R.id.tvSpecification);
		btnBatch = (Button) findViewById(R.id.btnBatch);

		btnUnit = (Button) findViewById(R.id.btnUnit);
		etStockNum = (EditText) findViewById(R.id.etStockNum);
		etNum = (EditText) findViewById(R.id.etNum);
		etNetNum = (EditText) findViewById(R.id.etNetNum);
		etRemark = (EditText) findViewById(R.id.etRemark);
	}

	private void initData() {
		this.btnUnit.setOnClickListener(this);
		this.btnBatch.setOnClickListener(this);
		this.etNum.setOnFocusChangeListener(this);
		this.etNetNum.setOnFocusChangeListener(this);
		warehouseid = getIntent().getStringExtra("warehouseid");
		position = getIntent().getIntExtra("position", 0);
		docitem = ((DefDocItemPD) getIntent().getSerializableExtra("docitem"));
		// if ((this.docitem != null) && (this.docitem.isIsusebatch())) {
		// findViewById(R.id.linearDate).setVisibility(View.VISIBLE);
		// findViewById(R.id.btnDate).setVisibility(View.VISIBLE);
		// } else {
		findViewById(R.id.linearDate).setVisibility(View.GONE);
		findViewById(R.id.linearBatch).setVisibility(View.GONE);
		// }
		this.etStockNum.setCursorVisible(false);
		this.etStockNum.setFocusable(false);
		this.etStockNum.setFocusableInTouchMode(false);
		this.tvBarcode.setText(this.docitem.getBarcode());
		this.tvSpecification.setText(this.docitem.getSpecification());
		this.btnUnit.setText(this.docitem.getUnitname());
		this.btnUnit.setTag(this.docitem.getUnitid());
		this.etStockNum.setText(this.docitem.getStocknum() + "");
		this.etStockNum.setTag(Double.valueOf(this.docitem.getCostprice()));
		this.etNum.setText(this.docitem.getNum() + "");
		this.etNetNum.setText(this.docitem.getNetnum() + "");
		this.etRemark.setText(this.docitem.getRemark());
		this.btnBatch.setText(this.docitem.getBatch());
		// this.btnDate.setText(Utils.formatDate(new
		// SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss").parse(this.docitem.getProductiondate()).getTime(),
		// "yyyy-MM-dd"));
		this.etNum.setTag(this.etNum.getText().toString());
		this.etNetNum.setTag(this.etNetNum.getText().toString());
		mDocUtils = new DocUtils();
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

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				goodsUnit = ((GoodsUnit) localList.get(which));
				if (!goodsUnit.getUnitid().equals(btnUnit.getTag())) {

					double goodscostprice = mDocUtils.queryGoodsCostprice(warehouseid, docitem.getGoodsid(),
							docitem.getUnitid());
					double sumStock = mDocUtils.queryPDSumStock(docitem.getGoodsid(), goodsUnit.getUnitid(),
							warehouseid);
					btnUnit.setText(goodsUnit.getUnitname());
					btnUnit.setTag(goodsUnit.getUnitid());
					etStockNum.setText(sumStock + "");
					etNum.setText(etStockNum.getText());
					etNetNum.setText("0");
					etStockNum.setTag(goodscostprice);
					etNum.setTag(etNum.getText());
					etNetNum.setTag(etNetNum.getText());

					// PDH.show(InventoryAddGoodAct.this, new
					// PDH.ProgressCallBack() {
					// public void action() {
					// ArrayList<ReqStrGetGoodsPricePD> localArrayList = new
					// ArrayList<ReqStrGetGoodsPricePD>();
					// ReqStrGetGoodsPricePD rp = new ReqStrGetGoodsPricePD();
					// rp.setWarehouseid(warehouseid);
					// rp.setGoodsid(docitem.getGoodsid());
					// rp.setUnitid(goodsUnit.getUnitid());
					// rp.setStocknum(0.0D);
					// rp.setCostprice(0.0D);
					// rp.setBatch(btnBatch.getText().toString());
					// localArrayList.add(rp);
					// String localString = new
					// ServiceGoods().gds_GetMultiGoodsPricePD(localArrayList);
					// handlerGet.sendMessage(handlerGet.obtainMessage(0,
					// localString));
					// }
					// });
				}

			}
		});
		localBuilder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "确定").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
		case android.R.id.home:
			setResult(Activity.RESULT_FIRST_USER);
			finish();
			break;
		case 0:
			if (this.etNum.hasFocus())
				this.etNum.clearFocus();
			if (this.etNetNum.hasFocus())
				this.etNetNum.clearFocus();
			if ((this.docitem.isIsusebatch()) && (!TextUtils.isEmptyS(this.btnBatch.getText().toString()))) {
				PDH.showMessage("请选择批次");
				return false;
			}
			fillItem();
			Intent intent = new Intent();
			intent.putExtra("position", this.position);
			intent.putExtra("docitem", this.docitem);
			setResult(Activity.RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(menu);
	}

	private void fillItem() {

		GoodsUnitDAO localGoodsUnitDAO = new GoodsUnitDAO();
		this.docitem
				.setStocknum(Utils.normalize(Utils.getDouble(this.etStockNum.getText().toString()).doubleValue(), 2));
		this.docitem.setBigstocknum(localGoodsUnitDAO.getBigNum(this.docitem.getGoodsid(), this.docitem.getUnitid(),
				this.docitem.getStocknum()));
		this.docitem.setBignum(localGoodsUnitDAO.getBigNum(this.docitem.getGoodsid(), this.docitem.getUnitid(),
				this.docitem.getStocknum()));
		this.docitem.setNum(Utils.normalize(Utils.getDouble(this.etNum.getText().toString()).doubleValue(), 2));
		this.docitem.setBignum(localGoodsUnitDAO.getBigNum(this.docitem.getGoodsid(), this.docitem.getUnitid(),
				this.docitem.getNum()));
		this.docitem.setNetnum(Utils.normalize(this.docitem.getNum() - this.docitem.getStocknum(), 2));
		this.docitem.setBignetnum(localGoodsUnitDAO.getBigNum(this.docitem.getGoodsid(), this.docitem.getUnitid(),
				this.docitem.getNetnum()));
		this.docitem
				.setCostprice(Utils.normalizePrice(Utils.getDouble(this.etStockNum.getTag().toString()).doubleValue()));
		this.docitem.setNetamount(Utils.normalizeSubtotal(this.docitem.getNetnum() * this.docitem.getCostprice()));
		this.docitem.setRemark(this.etRemark.getText().toString());
		// if (this.docitem.isIsusebatch()) {
		// this.docitem.setBatch(this.btnBatch.getText().toString());
		// this.docitem.setProductiondate(this.btnDate.getText() + " 00:00:00");
		// }
		if (TextUtils.isEmptyS(this.btnUnit.getTag().toString())) {
			this.docitem.setUnitid(this.btnUnit.getTag().toString());
			this.docitem.setUnitname(this.btnUnit.getText().toString());
		} else {
			this.docitem.setUnitid(null);
			this.docitem.setUnitname("");
		}
	}

	private Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String localString = msg.obj.toString();
			if (RequestHelper.isSuccess(localString)) {
				ReqStrGetGoodsPricePD rp = (ReqStrGetGoodsPricePD) JSONUtil
						.str2list(localString, ReqStrGetGoodsPricePD.class).get(0);
				btnUnit.setText(goodsUnit.getUnitname());
				btnUnit.setTag(goodsUnit.getUnitid());
				etStockNum.setText(rp.getStocknum() + "");
				etNum.setText(etStockNum.getText());
				etNetNum.setText("0");
				etStockNum.setTag(Double.valueOf(rp.getCostprice()));
				etNum.setTag(etNum.getText());
				etNetNum.setTag(etNetNum.getText());
			}

		};
	};
	private DocUtils mDocUtils;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUnit:
			unitSelect();
			break;
		// case R.id.批次:
		// startActivityForResult(new Intent().setClass(this,
		// GoodsBatchSearchAct.class).putExtra("goodsid",
		// this.docitem.getGoodsid()).putExtra("all",
		// true).putExtra("warehouseid", this.warehouseid), 2);
		// break;
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			switch (v.getId()) {
			case R.id.etNum:

				double num = Utils.normalize(Utils.getDouble(this.etNum.getText().toString()).doubleValue(), 2);
				double lastNum = Utils.normalize(Utils.getDouble(this.etNum.getTag().toString()).doubleValue(), 2);
				if (num != lastNum) {
					double stockNum = Utils.getDouble(this.etStockNum.getText().toString()).doubleValue();
					this.etNetNum.setText((num - stockNum) + "");
					this.etNum.setTag(this.etNum.getText());
					this.etNetNum.setTag(this.etNetNum.getText().toString());
				}
				return;
			case R.id.etNetNum:
				double v0 = Utils.normalize(Utils.getDouble(this.etNetNum.getText().toString()).doubleValue(), 2);
				if (v0 != Utils.normalize(Utils.getDouble(this.etNetNum.getTag().toString()).doubleValue(), 2)) {
					this.etNum
							.setText(new StringBuilder(String.valueOf(Utils.normalize(
									v0 + Utils.normalize(
											Utils.getDouble(this.etStockNum.getText().toString()).doubleValue(), 2),
							2))).toString());
					this.etNetNum.setTag(this.etNetNum.getText().toString());
					this.etNum.setTag(this.etNum.getText());
				}

				return;

			default:
				return;
			}
		}
	}

	public void setActionBarText() {
		setTitle(this.docitem.getGoodsname());
	}
}
