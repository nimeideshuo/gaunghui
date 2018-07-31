package com.ahjswy.cn.ui.transfer;

import java.text.SimpleDateFormat;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItem;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.outgoods.GoodsWarehouseSearchAct;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 调拨开单详情
 * 
 * @author Administrator
 *
 */
public class TransferAddGoodAct extends BaseActivity implements OnClickListener {
	private Button btnBatch;
	private Button btnDate;
	private Button btnUnit;
	private Button btnWarehouse;
	private DefDocItem docitem;
	private EditText etNum;
	private EditText etRemark;
	private GoodsUnit goodsUnit;
	private int position;
	TextView tvBarcode;
	TextView tvSpecification;
	private DocUtils docUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_transfer_add_goods);
		initView();
		initData();

	}

	private void initView() {
		this.tvBarcode = ((TextView) findViewById(R.id.tvBarcode));
		this.tvSpecification = ((TextView) findViewById(R.id.tvSpecification));
		this.btnWarehouse = ((Button) findViewById(R.id.btnWarehouse));
		this.btnUnit = ((Button) findViewById(R.id.btnUnit));
		this.btnBatch = ((Button) findViewById(R.id.btnBatch));
		this.btnDate = ((Button) findViewById(R.id.btnDate));
		this.etNum = ((EditText) findViewById(R.id.etNum));
		this.etRemark = ((EditText) findViewById(R.id.etRemark));
		this.btnUnit.setOnClickListener(this);
		this.btnBatch.setOnClickListener(this);
		this.btnWarehouse.setOnClickListener(this);
	}

	private void initData() {
		position = getIntent().getIntExtra("position", 0);
		this.docitem = ((DefDocItem) getIntent().getSerializableExtra("docitem"));
		if ((this.docitem != null) && (this.docitem.isIsusebatch())) {
			findViewById(R.id.linearDate).setVisibility(View.VISIBLE);
			findViewById(R.id.linearBatch).setVisibility(View.VISIBLE);
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
		etNum.setText(docitem.getNum() == 0 ? "" : docitem.getNum() + "");
		etRemark.setText(docitem.getRemark());
		docUtils =new DocUtils();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "确定").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		if (menu.getItemId() == 0) {
			if (!TextUtils.isEmptyS(this.btnWarehouse.getText().toString())) {
				PDH.showMessage("仓库不能为空");
				return false;
			}
			if (Utils.getDouble(this.etNum.getText().toString()).doubleValue() == 0.0D) {
				PDH.showMessage("数量必须大于0");
				return false;
			}
			fillItem();
			Intent localIntent = new Intent();
			localIntent.putExtra("position", this.position);
			localIntent.putExtra("docitem", this.docitem);
			setResult(Activity.RESULT_OK, localIntent);
			finish();

		}
		return super.onOptionsItemSelected(menu);
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
		if (docitem.getPrice() == 0) {
//			showError("没有查询到成本价格! 或者为0");
		}
		this.docitem.setNum(Utils.normalize(Utils.getDouble(this.etNum.getText().toString()).doubleValue(), 2));
		this.docitem.setBignum(new GoodsUnitDAO().getBigNum(this.docitem.getGoodsid(), this.docitem.getUnitid(),
				this.docitem.getNum()));
		this.docitem.setSubtotal(Utils.normalizeSubtotal(this.docitem.getNum() * this.docitem.getPrice()));
		this.docitem.setRemark(this.etRemark.getText().toString());
		this.docitem.setBatch(this.btnBatch.getText().toString());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUnit:
			unitSelect();
			break;
		case R.id.btnWarehouse:
			startActivityForResult(
					new Intent(this, GoodsWarehouseSearchAct.class).putExtra("goodsid", this.docitem.getGoodsid()), 6);
			break;

		default:
			break;
		}
	}

	private void unitSelect() {
		final List<GoodsUnit> localList = new GoodsUnitDAO().queryGoodsUnits(this.docitem.getGoodsid());
		String[] items = new String[localList.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = localList.get(i).getUnitname();
		}
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("单位");
		localBuilder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				goodsUnit = localList.get(which);
				if (!goodsUnit.getUnitid().equals(btnUnit.getTag().toString())) {
					docitem.setUnitid(goodsUnit.getUnitid());
					docitem.setUnitname(goodsUnit.getUnitname());
					double goodscostprice = docUtils.queryGoodsCostprice(btnWarehouse.getTag().toString(),
							docitem.getGoodsid(), docitem.getUnitid());
					btnUnit.setText(goodsUnit.getUnitname());
					btnUnit.setTag(goodsUnit.getUnitid());
					docitem.setPrice(goodscostprice);
				}

			}
		});
		localBuilder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 6:
				RespGoodsWarehouse respWarehouse = (RespGoodsWarehouse) data.getSerializableExtra("warehouse");
				this.btnWarehouse.setTag(respWarehouse.getWarehouseid());
				this.btnWarehouse.setText(respWarehouse.getWarehousename());
				this.btnBatch.setText(respWarehouse.getBatch());
				try {
					this.btnDate.setText(Utils.formatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(respWarehouse.getProductiondate()).getTime(), "yyyy-MM-dd"));
				} catch (Exception paramIntent) {
					this.btnDate.setText("");
					paramIntent.printStackTrace();
				}
				return;
			default:
				break;
			}
		}
	}

	@Override
	public void setActionBarText() {
		setTitle(this.docitem.getGoodsname());
	}
}
