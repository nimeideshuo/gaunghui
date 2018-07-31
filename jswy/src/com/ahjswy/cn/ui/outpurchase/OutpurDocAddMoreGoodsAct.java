package com.ahjswy.cn.ui.outpurchase;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocCG;
import com.ahjswy.cn.model.DefDocItemTH;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.service.ServiceGoods;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.Dialog_listCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import mexxen.mx5010.barcode.BarcodeConfig;
import mexxen.mx5010.barcode.BarcodeEvent;
import mexxen.mx5010.barcode.BarcodeListener;
import mexxen.mx5010.barcode.BarcodeManager;

public class OutpurDocAddMoreGoodsAct extends BaseActivity {
	private ListView lv_commodity_add;
	private ArrayList<DefDocItemTH> items;
	private DefDocCG defdoccg;
	private OutpurDocAddMoreAdapter adapter;

	private Dialog_listCheckBox dialog;
	private BarcodeManager bm;
	ArrayList<DefDocItemTH> Newitems = null;
	ArrayList<RespGoodsWarehouse> listStocknum = null;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_out_doc_add_moregoods);
		intView();
		addListener();

	}

	private void intView() {
		lv_commodity_add = (ListView) findViewById(R.id.lv_commodity_add);
		items = (ArrayList<DefDocItemTH>) JSONUtil.str2list(getIntent().getStringExtra("items"),
				DefDocItemTH.class);
		defdoccg = (DefDocCG) getIntent().getSerializableExtra("doc");
		adapter = new OutpurDocAddMoreAdapter(this);
		if (items.size() > 0) {
			// 初始化 库存
			handlerAdd.sendEmptyMessage(0);
		}
		dialog = new Dialog_listCheckBox(OutpurDocAddMoreGoodsAct.this);
		if (listStocknum == null) {
			listStocknum = new ArrayList<RespGoodsWarehouse>();
		}
	}

	public void addListener() {
		if (bm == null) {
			bm = new BarcodeManager(this);
		}
		bm.addListener(new BarcodeListener() {
			@Override
			public void barcodeEvent(BarcodeEvent event) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (event.getOrder().equals("SCANNER_READ")) {
					ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(bm.getBarcode().toString());
					if (Newitems == null) {
						Newitems = new ArrayList<DefDocItemTH>();
					}
					if (goodsThinList.size() == 1) {
						DefDocItemTH fillItem = fillItem(goodsThinList.get(0), 0.0D, 0L);
						Newitems.add(fillItem);
						handlerItem.sendEmptyMessage(1);
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
									DefDocItemTH fillItem = fillItem(select.get(i), 0.0D, 0L);
									Newitems.add(fillItem);
								}
								handlerItem.sendEmptyMessage(1);
							}
						});
					} else {
						PDH.showFail("没有查找到商品！可以尝试更新数据");
					}
				}

			}
		});
		// 扫码枪 功能调用 先new 对相 在调用
		BarcodeConfig barcodeConfig = new BarcodeConfig(this);
		// 设置条码输出模式 不显示模式(复制到粘贴板)
		barcodeConfig.setOutputMode(2);

	}

	ArrayList<RespGoodsWarehouse> newlistStocknum = null;
	Handler handlerItem = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				PDH.show(OutpurDocAddMoreGoodsAct.this, new ProgressCallBack() {

					@Override
					public void action() {

						if (newlistStocknum == null) {
							newlistStocknum = new ArrayList<RespGoodsWarehouse>();
						}
						for (int i = 0; i < Newitems.size(); i++) {
							DefDocItemTH defdocitemTH = Newitems.get(i);
							String localString = new ServiceGoods().gds_GetGoodsWarehouses(defdocitemTH.getGoodsid(),
									defdocitemTH.isIsusebatch());
							List<RespGoodsWarehouse> goodsWarehouses;
							if (RequestHelper.isSuccess(localString)) {
								goodsWarehouses = JSONUtil.str2list(localString, RespGoodsWarehouse.class);
								for (int j = 0; j < goodsWarehouses.size(); j++) {
									if (defdocitemTH.getGoodsid().equals(goodsWarehouses.get(j).getGoodsid())
											&& defdocitemTH.getWarehouseid()
													.equals(goodsWarehouses.get(j).getWarehouseid())) {
										newlistStocknum.add(goodsWarehouses.get(j));
										break;
									}
								}
							}
						}
						additemsStocknum.sendEmptyMessage(2);
					}
				});
				break;
			}
		};
	};

	private DefDocItemTH fillItem(GoodsThin localGoodsThin, double paramDouble1, Long paramLong) {
		GoodsUnitDAO localGoodsUnitDAO = new GoodsUnitDAO();
		DefDocItemTH defdocitemth = new DefDocItemTH();
		defdocitemth.setGoodsid(localGoodsThin.getId());
		defdocitemth.setGoodsname(localGoodsThin.getName());
		defdocitemth.setBarcode(localGoodsThin.getBarcode());
		defdocitemth.setSpecification(localGoodsThin.getSpecification());
		defdocitemth.setWarehouseid(defdoccg.getWarehouseid());
		defdocitemth.setWarehousename(defdoccg.getWarehousename());
		defdocitemth.setItemid(paramLong);
		GoodsUnit localGoodsUnit = null;
		if (Utils.DEFAULT_OutDocUNIT == 0) {
			localGoodsUnit = localGoodsUnitDAO.queryBaseUnit(localGoodsThin.getId());
		} else {
			localGoodsUnit = localGoodsUnitDAO.queryBigUnit(localGoodsThin.getId());
		}
		defdocitemth.setUnitid(localGoodsUnit.getUnitid());
		defdocitemth.setUnitname(localGoodsUnit.getUnitname());
		defdocitemth.setNum(Utils.normalize(paramDouble1, 2));
		defdocitemth.setBignum(localGoodsUnitDAO.getBigNum(defdocitemth.getGoodsid(), defdocitemth.getUnitid(),
				defdocitemth.getNum()));
		// 价格
		defdocitemth.setPrice(0);
		// 小计
		defdocitemth.setSubtotal(Utils.normalizeSubtotal(defdocitemth.getNum() * defdocitemth.getPrice()));
		// 折扣率
		defdocitemth.setDiscountratio(defdoccg.getDiscountratio());
		// 折扣价格
		defdocitemth.setDiscountprice(Utils.normalizePrice(defdocitemth.getPrice() * defdoccg.getDiscountratio()));
		// 折后小计
		defdocitemth.setDiscountsubtotal(defdocitemth.getNum() * defdocitemth.getDiscountprice());
		if (defdocitemth.getPrice() == 0.0D) {
			defdocitemth.setIsgift(true);
		}
		// 总金额
		// defdocitemth.setCostprice(0.0D);
		defdocitemth.setIsusebatch(localGoodsThin.isIsusebatch());

		return defdocitemth;
	}

	// 初始化 库存
	Handler handlerAdd = new Handler() {
		public void handleMessage(android.os.Message msg) {
			PDH.show(OutpurDocAddMoreGoodsAct.this, new ProgressCallBack() {

				@Override
				public void action() {
					List<RespGoodsWarehouse> listStocknum = new ArrayList<RespGoodsWarehouse>();
					for (int i = 0; i < items.size(); i++) {
						DefDocItemTH defdocitemth = items.get(i);
						String localString = new ServiceGoods().gds_GetGoodsWarehouses(defdocitemth.getGoodsid(),
								defdocitemth.isIsusebatch());
						List<RespGoodsWarehouse> goodsWarehouses;
						if (RequestHelper.isSuccess(localString)) {
							goodsWarehouses = JSONUtil.str2list(localString, RespGoodsWarehouse.class);
							for (int j = 0; j < goodsWarehouses.size(); j++) {
								if (defdocitemth.getGoodsid().equals(goodsWarehouses.get(j).getGoodsid())
										&& defdocitemth.getWarehouseid()
												.equals(goodsWarehouses.get(j).getWarehouseid())) {
									listStocknum.add(goodsWarehouses.get(j));
									break;
								}

							}

						}
					}

					additemsStocknum.sendMessage(additemsStocknum.obtainMessage(0, listStocknum));
				}
			});

		};
	};
	Handler additemsStocknum = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				// 初始化 库存 商品添加
				@SuppressWarnings("unchecked")
				List<RespGoodsWarehouse> stocknum = (List<RespGoodsWarehouse>) msg.obj;
				listStocknum.addAll(stocknum);
				adapter.setlistStocknum(listStocknum);
				adapter.setData(items);
				lv_commodity_add.setAdapter(adapter);
				lv_commodity_add.setItemsCanFocus(true);
				break;
			case 2:
				items.addAll(Newitems);
				listStocknum.addAll(newlistStocknum);
				adapter.setlistStocknum(listStocknum);
				adapter.setData(items);
				// adapter.addlistData(Newitems, newlistStocknum);
				Newitems.clear();
				newlistStocknum.clear();
				// lv_commodity_add.setAdapter(adapter);
				break;
			default:
				break;
			}

		};
	};

	public void deleBm() {
		if (bm != null) {
			bm.removeListener(new BarcodeListener() {

				@Override
				public void barcodeEvent(BarcodeEvent arg0) {

				}
			});
			bm.dismiss();
			bm = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			// deleBm();
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			break;
		case 0:
			tv_title_start();
			break;

		default:
			break;
		}
		return true;
	}

	private List<DefDocItemTH> listDe;

	private void tv_title_start() {
		List<DefDocItemTH> data = adapter.getData();
		listDe = new ArrayList<DefDocItemTH>();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getNum() > 0.0D) {
				listDe.add(data.get(i));
			}
		}
		if (listDe.size() == 0) {
			PDH.showError("必需至少有一条商品数量大于0");
			return;
		}
		deleBm();
		Intent intent = new Intent();
		intent.putExtra("items", JSONUtil.toJSONString(listDe));
		setResult(RESULT_OK, intent);
		finish();

	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("添加商品");
	}
}
