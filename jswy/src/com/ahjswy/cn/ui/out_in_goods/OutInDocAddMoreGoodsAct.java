package com.ahjswy.cn.ui.out_in_goods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.bean.DefDocItemDD;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.request.ReqStrGetGoodsPrice;
import com.ahjswy.cn.response.RespGoodsStock;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.service.ServiceSupport;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.Dialog_listCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 销售订单数量添加Activity
 * 
 * @author Administrator
 *
 */
public class OutInDocAddMoreGoodsAct extends BaseActivity {
	private ListView listview;
	private List<DefDocItemDD> items;
	private Def_Doc doccg;
	private OutInDocAddMoreAdapter adapter;
	private Dialog_listCheckBox dialog;
	private ServiceSupport support;
	private Scaner scaner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_out_doc_add_moregoods);
		intView();
		initData();
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(barcodeListener);
	}

	private void intView() {
		listview = (ListView) findViewById(R.id.lv_commodity_add);
		doccg = (Def_Doc) getIntent().getSerializableExtra("doc");
		items = JSONUtil.str2list(getIntent().getStringExtra("items"), DefDocItemDD.class);
		support = new ServiceSupport();
	}

	private void initData() {
		dialog = new Dialog_listCheckBox(this);
		adapter = new OutInDocAddMoreAdapter(this);
		adapter.setDoc(doccg);
		for (int i = 0; i < items.size(); i++) {
			setItemStock(items.get(i));
			setItemPrice(items.get(i));
		}
		adapter.setData(items);
		listview.setAdapter(adapter);
	}

	// 设置商品库存
	private void setItemStock(DefDocItemDD item) {
		String stockNum = support.sup_QueryStockNum(item.getGoodsid(), doccg.getWarehouseid());
		if (TextUtils.isEmptyS(stockNum)) {
			RespGoodsStock stockEntity = JSONUtil.readValue(stockNum, RespGoodsStock.class);
			item.stocknum = stockEntity.stocknum + "";
			item.bigstocknumber = stockEntity.bigstocknumber;
			if (Utils.DEFAULT_OutDocUNIT == 0) {
				item.showStock = stockEntity.stocknum + item.getUnitname();
			} else {
				item.showStock = stockEntity.bigstocknumber;
			}
		} else {
			showError("获取商品库存失败!");
			item.showStock = "0" + item.getUnitname();
		}
	}

	// 设置商品价格
	private void setItemPrice(DefDocItemDD item) {
		ReqStrGetGoodsPrice goodsPrice = DocUtils.GetMultiGoodsPrice(doccg.getCustomerid(), doccg.getWarehouseid(),
				item.getGoodsid(), item.getUnitid());
		item.setPrice(goodsPrice == null ? 0 : goodsPrice.getPrice());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		case 0:
			saveValue();
			break;
		}

		return super.onOptionsItemSelected(menu);
	}

	private void saveValue() {
		ArrayList<DefDocItemDD> listItemDD = new ArrayList<DefDocItemDD>();
		List<DefDocItemDD> data = adapter.getData();
		for (int i = 0; i < data.size(); i++) {
			DefDocItemDD localDefDocItem = (DefDocItemDD) data.get(i);
			if (localDefDocItem.getNum() > 0.0D) {
				listItemDD.add(localDefDocItem);
			}
		}
		if (listItemDD.size() == 0) {
			Toast.makeText(this, "必需至少有一条商品数量大于0", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent localIntent = new Intent();
		localIntent.putExtra("items", JSONUtil.toJSONString(listItemDD));
		setResult(RESULT_OK, localIntent);
		finish();
	}

	ScanerBarcodeListener barcodeListener = new ScanerBarcodeListener() {

		@Override
		public void setBarcode(String barcode) {
			readBarcode(barcode);
		}
	};

	protected void readBarcode(String barcode) {
		ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcode);
		if (goodsThinList == null || goodsThinList.size() == 0) {
			PDH.showMessage("没有找到商品!");
			return;
		}
		if (goodsThinList.size() == 1) {
			DefDocItemDD itemDD = fillItem(goodsThinList.get(0), 1, 0.0D, 0);
			setItemStock(itemDD);
			setItemPrice(itemDD);
			adapter.addData(itemDD);
			adapter.notifyDataSetChanged();
		} else if (goodsThinList.size() > 1) {
			dialog.setGoods(goodsThinList);
			dialog.setTempGoods(goodsThinList);
			dialog.ShowMe();
			dialog.ensure(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					List<GoodsThin> select = dialog.getSelect();
					for (int i = 0; i < select.size(); i++) {
						DefDocItemDD fillItem = fillItem(select.get(i), 1, 0.0D, 0);
						setItemStock(fillItem);
						setItemPrice(fillItem);
						adapter.addData(fillItem);
					}
					adapter.notifyDataSetChanged();
				}
			});

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		scaner.removeListener();
		scaner=null;
	}

	private DefDocItemDD fillItem(GoodsThin localGoodsThin, double num, double price, long l) {
		GoodsUnitDAO localGoodsUnitDAO = new GoodsUnitDAO();
		DefDocItemDD localDefDocItem = new DefDocItemDD();
		localDefDocItem.setItemid(0L);
		localDefDocItem.setDocid(doccg.getDocid());
		localDefDocItem.setGoodsid(localGoodsThin.getId());
		localDefDocItem.setGoodsname(localGoodsThin.getName());
		localDefDocItem.setBarcode(localGoodsThin.getBarcode());
		localDefDocItem.setSpecification(localGoodsThin.getSpecification());
		localDefDocItem.setModel(localGoodsThin.getModel());
		GoodsUnit localGoodsUnit = null;
		if (Utils.DEFAULT_OutDocUNIT == 0) {
			localGoodsUnit = localGoodsUnitDAO.queryBaseUnit(localGoodsThin.getId());
		} else {
			localGoodsUnit = localGoodsUnitDAO.queryBigUnit(localGoodsThin.getId());
		}
		localDefDocItem.setUnitid(localGoodsUnit.getUnitid());
		localDefDocItem.setUnitname(localGoodsUnit.getUnitname());
		localDefDocItem.setNum(Utils.normalize(num, 2));
		localDefDocItem.setBignum(localGoodsUnitDAO.getBigNum(localDefDocItem.getGoodsid(), localDefDocItem.getUnitid(),
				localDefDocItem.getNum()));
		localDefDocItem.setPrice(Utils.normalizePrice(price));
		localDefDocItem.setSubtotal(Utils.normalizeSubtotal(localDefDocItem.getNum() * localDefDocItem.getPrice()));
		localDefDocItem.setDiscountratio(doccg.getDiscountratio());
		localDefDocItem.setDiscountprice(Utils.normalizePrice(localDefDocItem.getPrice() * doccg.getDiscountratio()));
		localDefDocItem.setDiscountsubtotal(
				Utils.normalizeSubtotal(localDefDocItem.getNum() * localDefDocItem.getDiscountprice()));
		boolean isgift = false;
		if (localDefDocItem.getPrice() == 0.0D) {
			isgift = true;
		}
		localDefDocItem.setIsgift(isgift);
		// localDefDocItem.setCostprice(0.0D);
		localDefDocItem.setRemark("");
		localDefDocItem.setRversion(0L);
		localDefDocItem.setIsdiscount(false);
		localDefDocItem.setIsusebatch(localGoodsThin.isIsusebatch());
		return localDefDocItem;
	}

	public void setActionBarText() {
		setTitle("添加商品");
	}
}
