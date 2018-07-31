package com.ahjswy.cn.ui.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.model.DefDocItemPD;
import com.ahjswy.cn.model.DefDocPD;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.Dialog_listCheckBox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class InventoryAddMoreGoodsAct extends BaseActivity {
	private List<DefDocItemPD> items;
	private ListView listView;
	private InventoryAddMoreAdapter adapter;
	private Scaner scaner;
	private DefDocPD doc;
	private DocUtils docUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_inventory_add_more_goods);
		doc = (DefDocPD) getIntent().getSerializableExtra("doc");
		items = JSONUtil.parseArray(getIntent().getStringExtra("items"), DefDocItemPD.class);
		listView = ((ListView) findViewById(R.id.listView));
		adapter = new InventoryAddMoreAdapter(this);
		this.adapter.setItem(items);
		this.listView.setAdapter(this.adapter);
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(barcodeListener);
		dialog = new Dialog_listCheckBox(this);
		docUtils = new DocUtils();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "确定").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		if (menu.getItemId() == 1) {
			List<DefDocItemPD> listPD = new ArrayList<>();
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getNum() > 0) {
					listPD.add(items.get(i));
				}

			}
			if (listPD.size() == 0) {
				showError("请输入数量!");
				return false;
			}
			Intent intent = new Intent();
			intent.putExtra("items", JSONUtil.toJSONString(listPD));
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		return super.onOptionsItemSelected(menu);
	}

	ScanerBarcodeListener barcodeListener = new ScanerBarcodeListener() {

		@Override
		public void setBarcode(String barcode) {
			if (dialog != null) {
				dialog.dismiss();
			}
			if (items.size() >= DocUtils.MAXITEM) {
				showError("已经开的够多了！请确认一下");
				return;
			}
			readBarcode(barcode);
		}
	};
	private Dialog_listCheckBox dialog;

	private void readBarcode(String barcode) {
		final ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcode);
		if (goodsThinList.isEmpty()) {
			showError("没有查找到商品!");
			return;
		}
		final Map<String, Object> goodsMap = new HashMap<String, Object>();
		if (goodsThinList.size() == 1) {
			DefDocItemPD fillItem = docUtils.fillItem(doc, goodsThinList.get(0), 0.0D, 0.0D, DocUtils.getDefaultNum());
			if (Utils.isCombination()) {
				goodsMap.put(fillItem.getGoodsid(), fillItem);
				addItems(goodsMap);
			} else {
				items.add(fillItem);
			}
			adapter.setData(items);
			adapter.notifyDataSetInvalidated();
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
						DefDocItemPD fillItem = docUtils.fillItem(doc, select.get(i), 0d, 0.0D,
								DocUtils.getDefaultNum());
						if (Utils.isCombination()) {
							goodsMap.put(fillItem.getGoodsid(), fillItem);
							addItems(goodsMap);
						} else {
							items.add(fillItem);
						}
					}
					adapter.setData(items);
					adapter.notifyDataSetInvalidated();
				}
			});
		} else {
			PDH.showFail("没有查找到商品！可以尝试更新数据");
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		scaner.removeListener();
		scaner=null;
	}

	private void addItems(Map<String, Object> goodsMap) {
		for (int i = 0; i < items.size(); i++) {
			DefDocItemPD itemPD = items.get(i);
			if (goodsMap.get(itemPD.getGoodsid()) != null) {
				itemPD.setNum(itemPD.getNum() + 1);
				goodsMap.remove(itemPD.getGoodsid());
				showError("相同商品数量+1");
			}
		}
		if (goodsMap.isEmpty()) {
			return;
		}
		for (Object item : goodsMap.values()) {
			items.add((DefDocItemPD) item);
		}
	}

	public void setActionBarText() {
		setTitle("商品添加");
	}
}
