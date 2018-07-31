package com.ahjswy.cn.ui.ingoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDoc;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.Dialog_listCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/*
 * 销售退货==商品添加
 */
public class InDocAddMoreGoodsAct extends BaseActivity {
	private List<DefDocItemXS> items;
	private ListView listView;
	private InDocAddMoreAdapter adapter;
	private DefDoc doc;
	Scaner scaner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_indoc_add_more_goods);
		initView();
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(barcodeListener);
	}

	private void initView() {
		items = JSONUtil.parseArray(getIntent().getStringExtra("items"), DefDocItemXS.class);
		doc = (DefDoc) getIntent().getSerializableExtra("doc");
		listView = ((ListView) findViewById(R.id.listView));
		adapter = new InDocAddMoreAdapter(this);
		// 测试代码
		adapter.setData(items);
		listView.setAdapter(adapter);
		dialog = new Dialog_listCheckBox(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		scaner.removeListener();
		scaner=null;
	}

	ScanerBarcodeListener barcodeListener = new ScanerBarcodeListener() {

		@Override
		public void setBarcode(String barcode) {
			if (dialog != null) {
				dialog.dismiss();
			}
			readBarcode(barcode);
		}
	};

	private void readBarcode(String barcode) {
		ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcode);
		final Map<String, Object> goodsMap = new HashMap<String, Object>();
		if (goodsThinList.size() == 1) {
			DefDocItemXS defdocitem = fillItem(goodsThinList.get(0), DocUtils.getDefaultNum(), 0.0D);
			if (Utils.isCombination()) {
				goodsMap.put(defdocitem.getGoodsid(), defdocitem);
				combinationItem(goodsMap);
			} else {
				items.add(defdocitem);
			}
			adapter.setData(items);
			adapter.notifyDataSetChanged();
			// listView.setSelection(items.size());
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
						DefDocItemXS defdocitem = fillItem(select.get(i), DocUtils.getDefaultNum(), 0.0D);
						if (Utils.isCombination()) {
							goodsMap.put(defdocitem.getGoodsid(), defdocitem);
							combinationItem(goodsMap);
						} else {
							items.add(defdocitem);
						}
					}
					adapter.setData(items);
					adapter.notifyDataSetChanged();
					// listView.setSelection(items.size());
				}
			});
		} else {
			PDH.showFail("没有查找到商品！可以尝试更新数据");
		}

	}

	private void combinationItem(Map<String, Object> goodsMap) {
		List<DefDocItemXS> data = adapter.getData();
		for (int i = 0; i < data.size(); i++) {
			DefDocItemXS itemXS = data.get(i);
			if (goodsMap.get(itemXS.getGoodsid()) != null) {
				itemXS.setNum(itemXS.getNum() + 1);
				goodsMap.remove(itemXS.getGoodsid());
				showError("相同商品数量+1");
			}
		}
		if (goodsMap.isEmpty()) {
			return;
		}
		for (Object item : goodsMap.values()) {
			items.add((DefDocItemXS) item);
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
			tv_title_start();
			break;
		}

		return true;
	}

	private void tv_title_start() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				ArrayList<DefDocItemXS> localArrayList = new ArrayList<DefDocItemXS>();
				List<DefDocItemXS> data = adapter.getData();
				for (int i = 0; i < data.size(); i++) {
					DefDocItemXS localDefDocItem = (DefDocItemXS) data.get(i);
					if (localDefDocItem.getNum() > 0.0D) {
						localArrayList.add(localDefDocItem);
					}
				}
				if (localArrayList.size() <= 0) {
					showError("必需至少有一条商品数量大于0");
					return;
				}
				Intent localIntent = new Intent();
				localIntent.putExtra("items", JSONUtil.object2Json(localArrayList));
				setResult(RESULT_OK, localIntent);
				finish();
			}
		});

	}

	private Dialog_listCheckBox dialog;

	private DefDocItemXS fillItem(GoodsThin goodsThin, double num, double price) {
		GoodsUnitDAO localGoodsUnitDAO = new GoodsUnitDAO();
		DefDocItemXS itemXS = new DefDocItemXS();
		itemXS.setItemid(0L);
		itemXS.setDocid(this.doc.getDocid());
		itemXS.setGoodsid(goodsThin.getId());
		itemXS.setGoodsname(goodsThin.getName());
		itemXS.setBarcode(goodsThin.getBarcode());
		itemXS.setSpecification(goodsThin.getSpecification());
		itemXS.setModel(goodsThin.getModel());
		itemXS.setWarehouseid(this.doc.getWarehouseid());
		itemXS.setWarehousename(this.doc.getWarehousename());
		GoodsUnit localGoodsUnit = null;
		if (Utils.DEFAULT_OutDocUNIT == 0) {
			localGoodsUnit = localGoodsUnitDAO.queryBaseUnit(goodsThin.getId());
		} else {
			localGoodsUnit = localGoodsUnitDAO.queryBigUnit(goodsThin.getId());
		}
		itemXS.setUnitid(localGoodsUnit.getUnitid());
		itemXS.setUnitname(localGoodsUnit.getUnitname());
		itemXS.setNum(Utils.normalize(num, 2));
		itemXS.setBignum(localGoodsUnitDAO.getBigNum(itemXS.getGoodsid(), itemXS.getUnitid(), itemXS.getNum()));
		itemXS.setPrice(Utils.normalizePrice(price));
		itemXS.setSubtotal(Utils.normalizeSubtotal(itemXS.getNum() * itemXS.getPrice()));
		itemXS.setDiscountratio(this.doc.getDiscountratio());
		itemXS.setDiscountprice(Utils.normalizePrice(itemXS.getPrice() * this.doc.getDiscountratio()));
		itemXS.setDiscountsubtotal(Utils.normalizeSubtotal(itemXS.getNum() * itemXS.getDiscountprice()));
		boolean isgift = false;
		if (itemXS.getPrice() == 0.0D) {
			isgift = true;
		}
		itemXS.setIsgift(isgift);
		itemXS.setCostprice(0.0D);
		itemXS.setRemark("");
		itemXS.setRversion(0L);
		itemXS.setIsdiscount(false);
		itemXS.setIsusebatch(goodsThin.isIsusebatch());

		return itemXS;
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("商品添加");
	}
}
