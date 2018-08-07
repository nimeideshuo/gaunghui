package com.ahjswy.cn.ui.outgoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ahjswy.cn.R;
import com.ahjswy.cn.cldb.Sz_stockwarn;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.DefDocXS;
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
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 销售单数量添加Activity
 * 
 * @author Administrator
 *
 */
public class OutDocAddMoreGoodsAct extends BaseActivity {
	private List<DefDocItemXS> items;
	private OutDocAddMoreAdapter adapter;
	private DefDocXS doc;
	private Sz_stockwarn stockwarn;
	private List<DefDocItemXS> listDe;
	private SwipeMenuListView listview;
	private Dialog_listCheckBox dialog;
	private Scaner scaner;
	private GoodsUnitDAO dao;
	boolean isScanerBarcode = false;
	double num = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_out_doc_add_moregoods);
		intView();
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(barcodeListener);
	}

	private void intView() {
		dao = new GoodsUnitDAO();
		dialog = new Dialog_listCheckBox(this);
		stockwarn = new Sz_stockwarn();
		listview = (SwipeMenuListView) findViewById(R.id.lv_commodity_add);
		items = JSONUtil.parseArray(getIntent().getStringExtra("items"), DefDocItemXS.class);
		doc = (DefDocXS) getIntent().getSerializableExtra("doc");
		adapter = new OutDocAddMoreAdapter(this);
		adapter.setDoc(doc);
		listview.setAdapter(adapter);
		SwipeMenuCreator local5 = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
				item1.setTitle("赠品");
				item1.setTitleSize(14);
				item1.setTitleColor(-16777216);
				item1.setWidth(100);
				item1.setBackground(new ColorDrawable(Color.rgb(48, 177, 245)));
				menu.addMenuItem(item1);
			}
		};
		listview.setMenuCreator(local5);

		listview.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				if (index == 0) {
					adapter.getItem(position).setPrice(0);
					adapter.notifyDataSetInvalidated();
					PDH.showSuccess("赠品设置成功！");
				}
				return false;
			}
		});
		
		listview.setOnSwipeListener(new OnSwipeListener() {
			
			@Override
			public void onSwipeStart(int position) {
				//解决滑动  焦点问题
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onSwipeEnd(int position) {
				// TODO Auto-generated method stub
				
			}
		});

		isScanerBarcode = true;
		PDH.show(this, "库存查询中...", new PDH.ProgressCallBack() {

			@Override
			public void action() {
				for (DefDocItemXS item : items) {
					try {
						item.setPrice(DocUtils.getGoodsPrice(doc.getCustomerid(), item));
						double sumstock = stockwarn.querySumStock(item.getGoodsid());
						item.stocksumnum = sumstock;
						item.goodSumStock = DocUtils.Stocknum(sumstock, item.unit);
						double stocknum = stockwarn.queryStockNum(item.getWarehouseid(), item.getGoodsid());
						item.stocknum = stocknum;
						item.goodStock = DocUtils.Stocknum(stocknum, item.unit);
					} catch (Exception e) {
						e.printStackTrace();
						DocUtils.insertLog(e);
					}
				}
				handler.sendEmptyMessage(0);
			}
		});

		// adapter.setItem(items);
		// listview.setAdapter(adapter);
	}

	ScanerBarcodeListener barcodeListener = new ScanerBarcodeListener() {

		@Override
		public void setBarcode(String barcode) {
			if (isScanerBarcode) {
				showError("数据处理中...");
				return;
			}
			if (dialog != null) {
				dialog.dismiss();
			}
			try {
				readBarcode(barcode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		if (scaner != null) {
			scaner.removeListener();
			scaner = null;
		}
	}

	protected void readBarcode(String barcode) {
		if (items.size() >= 50) {
			showError("已经开的够多了！请确认一下");
			return;
		}
		ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcode);
		final Map<String, Object> goodsMap = new HashMap<String, Object>();
		if (goodsThinList.size() == 1) {
			long maxTempItemId = getMaxTempItemId();
			DefDocItemXS fillItem = fillItem(goodsThinList.get(0), DocUtils.getDefaultNum(), 0.0D, maxTempItemId + 1L);
			goodsMap.put(fillItem.getGoodsid(), fillItem);
			if (Utils.isCombination()) {
				combinationItem(goodsMap);
			}
			addItems(goodsMap);
		} else if (goodsThinList.size() > 1) {

			dialog.setGoods(goodsThinList);
			dialog.setTempGoods(goodsThinList);
			dialog.ShowMe();
			dialog.ensure(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					List<GoodsThin> select = dialog.getSelect();
					long maxTempItemId = getMaxTempItemId();
					for (int i = 0; i < select.size(); i++) {
						maxTempItemId += 1L;
						DefDocItemXS fillItem = fillItem(select.get(i), DocUtils.getDefaultNum(), 0.0D, maxTempItemId);
						goodsMap.put(fillItem.getGoodsid(), fillItem);
					}
					if (Utils.isCombination()) {
						combinationItem(goodsMap);
					}
					addItems(goodsMap);
				}
			});
		} else {
			PDH.showFail("没有查找到商品！可以尝试更新数据");
		}
	}

	private void combinationItem(Map<String, Object> goodsMap) {
		for (int i = 0; i < items.size(); i++) {
			DefDocItemXS itemXS = items.get(i);
			if (goodsMap.get(itemXS.getGoodsid()) != null) {
				itemXS.setNum(itemXS.getNum() + 1);
				goodsMap.remove(itemXS.getGoodsid());
				showError("相同商品数量+1");
				adapter.notifyDataSetChanged();
			}
		}
	}

	// 扫码枪添加商品 转换
	protected void addItems(final Map<String, Object> goodsMap) {
		if (goodsMap.isEmpty()) {
			return;
		}
		isScanerBarcode = true;
		PDH.show(this, "库存查询中...", new PDH.ProgressCallBack() {

			@Override
			public void action() {
				try {

					for (Object objitem : goodsMap.values()) {
						DefDocItemXS item = (DefDocItemXS) objitem;
						item.setPrice(DocUtils.getGoodsPrice(doc.getCustomerid(), item));
						double sumstock = stockwarn.querySumStock(item.getGoodsid());
						item.stocksumnum = sumstock;
						item.goodSumStock = DocUtils.Stocknum(sumstock, item.unit);
						double stocknum = stockwarn.queryStockNum(item.getWarehouseid(), item.getGoodsid());
						item.stocknum = stocknum;
						item.goodStock = DocUtils.Stocknum(stocknum, item.unit);
						items.add(item);
					}
					handler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
					DocUtils.insertLog(e);
				}
			}
		});

		// listview.setSelection(items.size());
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				adapter.setItem(items);
				adapter.notifyDataSetChanged();
				isScanerBarcode = false;
			}
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	@Override
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

	// 保存输入的值 必须有一个 大于0 的
	private void tv_title_start() {
		try {
			List<DefDocItemXS> data = adapter.getData();
			listDe = new ArrayList<DefDocItemXS>();
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getNum() > 0.0D) {
					listDe.add(data.get(i));
				}
			}
			if (listDe.size() == 0) {
				PDH.showError("必需至少有一条商品数量大于0");
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("items", JSONUtil.object2Json(listDe));
			setResult(RESULT_OK, intent);
			finish();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public long getMaxTempItemId() {
		long l1 = 0L;
		if (items.isEmpty()) {
			return l1;
		}
		for (int i = 0; i < items.size(); i++) {
			long l2 = items.get(i).getTempitemid();
			if (l2 > l1) {
				l1 = l2;
			}
		}
		return l1;
	}

	DefDocItemXS fillItem(GoodsThin goodsThin, double paramDouble1, double paramDouble2, long paramLong) {
		DefDocItemXS itemXS = new DefDocItemXS();
		itemXS.setItemid(0L);
		itemXS.setTempitemid(paramLong);
		itemXS.setDocid(this.doc.getDocid());
		itemXS.setGoodsid(goodsThin.getId());
		itemXS.setGoodsname(goodsThin.getName());
		itemXS.setBarcode(goodsThin.getBarcode());
		itemXS.setSpecification(goodsThin.getSpecification());
		itemXS.setModel(goodsThin.getModel());
		itemXS.setWarehouseid(this.doc.getWarehouseid());
		itemXS.setWarehousename(this.doc.getWarehousename());
		GoodsUnit goodsUnit = null;
		if (Utils.DEFAULT_OutDocUNIT == 0) {
			goodsUnit = dao.queryBaseUnit(goodsThin.getId());
		} else {
			goodsUnit = dao.queryBigUnit(goodsThin.getId());
		}
		itemXS.setUnitid(goodsUnit.getUnitid());
		itemXS.setUnitname(goodsUnit.getUnitname());
		itemXS.unit = goodsUnit;
		itemXS.setNum(Utils.normalize(paramDouble1, 2));
		itemXS.setBignum(dao.getBigNum(itemXS.getGoodsid(), itemXS.getUnitid(), itemXS.getNum()));
		// 价格
		itemXS.setPrice(Utils.normalizePrice(paramDouble2));
		// 小计
		itemXS.setSubtotal(Utils.normalizeSubtotal(itemXS.getNum() * itemXS.getPrice()));
		// 折扣率
		itemXS.setDiscountratio(doc.getDiscountratio());
		// 折扣价格
		itemXS.setDiscountprice(Utils.normalizePrice(itemXS.getPrice() * this.doc.getDiscountratio()));
		// 折扣小计
		itemXS.setDiscountsubtotal(Utils.normalizeSubtotal(itemXS.getNum() * itemXS.getDiscountprice()));
		if (itemXS.getPrice() == 0.0D) {
			itemXS.setIsgift(true);
			itemXS.setCostprice(0.0D);
			itemXS.setRemark("");
			itemXS.setRversion(0L);
			itemXS.setIsdiscount(false);
			itemXS.setIsexhibition(false);
			itemXS.setIspromotion(false);
			itemXS.setParentitemid(0L);
			itemXS.setPromotiontype(-1);
			itemXS.setPromotiontypename(null);
			itemXS.setOutorderdocid(0L);
			itemXS.setOutorderdocshowid(null);
			itemXS.setOutorderitemid(0L);
			// 是否显示批次
			itemXS.setIsusebatch(goodsThin.isIsusebatch());
		}
		return itemXS;
	}

	/**
	 * * 监听Back键按下事件,方法2: * 注意: * 返回值表示:是否能完全处理该事件 * 在此处返回false,所以会继续传播该事件. *
	 * 
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// deleBm();
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
		}
		return true;
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("商品添加");
	}
}
