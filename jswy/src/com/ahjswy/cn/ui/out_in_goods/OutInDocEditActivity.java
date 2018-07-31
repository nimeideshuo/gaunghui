package com.ahjswy.cn.ui.out_in_goods;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.bean.DefDocItemDD;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.bean.Def_DocDraft;
import com.ahjswy.cn.bean.SaleEntity;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.FieldSaleForPrint;
import com.ahjswy.cn.model.FieldSaleItemForPrint;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.print.BTPrintHelper;
import com.ahjswy.cn.print.BTPrintHelper.PrintOverCall;
import com.ahjswy.cn.print.PrintMode;
import com.ahjswy.cn.request.ReqStrGetGoodsPrice;
import com.ahjswy.cn.response.RespServiceInfor;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.service.ServiceGoods;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.ui.SearchHelper;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.ui.out_in_goods.MenuPayPopup.EditMenuPopupListener;
import com.ahjswy.cn.utils.InfoDialog;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.AutoTextView;
import com.ahjswy.cn.views.Dialog_listCheckBox;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 销售订单编辑
 * 
 * @author Administrator
 *
 */
public class OutInDocEditActivity extends BaseActivity implements OnTouchListener {
	private LinearLayout linearSearch;
	private SearchHelper searchHelper;
	private MenuPayPopup menuPopup;
	private Button btnAdd;
	private SwipeMenuListView listView;
	private Def_Doc doccg;
	private OutInDocAdaprer adapter;
	private AutoTextView atvSearch;
	private ServiceStore serviceStore;
	private Dialog_listCheckBox dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_outindoc);
		initView();
		initData();
	}

	private void initView() {
		atvSearch = ((AutoTextView) findViewById(R.id.atvSearch));
		linearSearch = (LinearLayout) findViewById(R.id.linearSearch);
		searchHelper = new SearchHelper(this, linearSearch);
		atvSearch.setOnItemClickListener(onItemClickListeners);
		// 加载查找 添加
		btnAdd = (Button) findViewById(R.id.btnAdd);
		listView = (SwipeMenuListView) findViewById(R.id.listView_addShop);
		serviceStore = new ServiceStore();
		btnAdd.setOnClickListener(addGoodsClick);
		listView.setOnItemClickListener(goodsItemListener);
		listView.setOnTouchListener(this);
		dialog = new Dialog_listCheckBox(this);
		unitDAO = new GoodsUnitDAO();
	}

	private void combinationItem() {
		int combinationNum = listItem.size();
		ArrayList<DefDocItemDD> data = new ArrayList<DefDocItemDD>(listItem);
		ArrayList<DefDocItemDD> listDocItem = new ArrayList<DefDocItemDD>();
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < data.size(); i++) {
			DefDocItemDD items1 = data.get(i);
			if (map.get(items1.getGoodsid()) == null) {
				map.put(items1.getGoodsid(), new DefDocItemDD(items1));
				continue;
			}
			DefDocItemDD itemxs2 = (DefDocItemDD) map.get(items1.getGoodsid());
			if (items1.getGoodsid().equals(itemxs2.getGoodsid()) && items1.getUnitid().equals(itemxs2.getUnitid())
					&& items1.getPrice() == itemxs2.getPrice()
					&& items1.getDiscountratio() == itemxs2.getDiscountratio()) {
				itemxs2.setNum(itemxs2.getNum() + items1.getNum());
				itemxs2.setBignum(unitDAO.getBigNum(itemxs2.getGoodsid(), itemxs2.getUnitid(), itemxs2.getNum()));
				itemxs2.setSubtotal(itemxs2.getNum() * itemxs2.getPrice());
				itemxs2.setDiscountsubtotal(itemxs2.getNum() * itemxs2.getPrice() * itemxs2.getDiscountratio());
				map.put(itemxs2.getGoodsid(), itemxs2);
			} else {
				listDocItem.add(items1);
			}

		}
		Set<String> keySet = map.keySet();
		for (String string : keySet) {
			listDocItem.add((DefDocItemDD) map.get(string));
		}
		if ((combinationNum - listDocItem.size()) == 0) {
			// 没有要合并的商品!
			return;
		}
		showSuccess("同品增加成功!");
		// 设置数据
		listItem.clear();
		listItem.addAll(listDocItem);
		adapter.setItems(listItem);
	}

	private void initData() {
		ishaschanged = getIntent().getBooleanExtra("ishaschanged", true);
		SaleEntity entity = (SaleEntity) getIntent().getSerializableExtra("entity");
		doccg = ((Def_Doc) JSONUtil.readValue(entity.getDocjson(), Def_Doc.class));
		listItem = new ArrayList<DefDocItemDD>();
		adapter = new OutInDocAdaprer();
		SwipeMenuCreator local5 = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem localSwipeMenuItem1 = new SwipeMenuItem(getApplicationContext());
				localSwipeMenuItem1.setTitle("复制");
				localSwipeMenuItem1.setTitleSize(14);
				localSwipeMenuItem1.setTitleColor(-16777216);
				localSwipeMenuItem1.setWidth(100);
				localSwipeMenuItem1.setBackground(new ColorDrawable(Color.rgb(48, 177, 245)));
				menu.addMenuItem(localSwipeMenuItem1);
				SwipeMenuItem localSwipeMenuItem2 = new SwipeMenuItem(getApplicationContext());
				localSwipeMenuItem2.setTitle("删除");
				localSwipeMenuItem2.setTitleSize(14);
				localSwipeMenuItem2.setTitleColor(-16777216);
				localSwipeMenuItem2.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
				localSwipeMenuItem2.setWidth(100);
				menu.addMenuItem(localSwipeMenuItem2);
			}
		};
		listView.setMenuCreator(local5);
		listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					DefDocItemDD defdocitemxs = new DefDocItemDD(listItem.get(position));
					defdocitemxs.setItemid(0L);
					listItem.add(defdocitemxs);
					mHandler.postDelayed(new Runnable() {
						public void run() {
							adapter.setItems(listItem);
							setActionBarText();
							ishaschanged = true;
						}
					}, 180L);
					break;
				case 1:
					listItem.remove(position);
					mHandler.postDelayed(new Runnable() {
						public void run() {
							adapter.setItems(listItem);
							ishaschanged = true;
							setActionBarText();
						}
					}, 180L);

					break;
				}

				return false;
			}
		});

	}

	ScanerBarcodeListener barcodeListener = new ScanerBarcodeListener() {

		@Override
		public void setBarcode(String barcode) {
			atvSearch.setText("");
			if (dialog != null) {
				dialog.dismiss();
			}
			readBarcode(barcode);
		}
	};

	protected void readBarcode(String barcodeStr) {
		ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcodeStr);
		localArrayList = new ArrayList<DefDocItemDD>();
		if (goodsThinList == null || goodsThinList.size() == 0) {
			PDH.showMessage("没有找到商品!");
			return;
		}
		if (goodsThinList.size() == 1) {
			DefDocItemDD itemDD = fillItem(goodsThinList.get(0), 1, 0.0D, 0);
			localArrayList.add(itemDD);
			Intent intent = new Intent(OutInDocEditActivity.this, OutInDocAddMoreGoodsAct.class);
			intent.putExtra("items", JSONUtil.toJSONString(localArrayList));
			intent.putExtra("doc", doccg);
			startActivityForResult(intent, 1);
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
						localArrayList.add(fillItem);
					}
					Intent intent = new Intent(OutInDocEditActivity.this, OutInDocAddMoreGoodsAct.class);
					intent.putExtra("items", JSONUtil.toJSONString(localArrayList));
					intent.putExtra("doc", doccg);
					startActivityForResult(intent, 1);
				}
			});

		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		scaner.removeListener();
	}

	@Override
	protected void onResume() {
		super.onResume();
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(barcodeListener);
	}

	View.OnClickListener addGoodsClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if ((menuPopup != null) && (menuPopup.isShowing())) {
				menuPopup.dismiss();
				WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
				localLayoutParams.alpha = 1.0F;
				getWindow().setAttributes(localLayoutParams);
			}
			List<GoodsThin> localList = searchHelper.getAdapter().getSelect();
			if ((localList == null) || (localList.size() == 0)) {
				PDH.showMessage("请选择商品！");
				return;
			}
			atvSearch.setText("");
			if (!"1".equals(new AccountPreference().getValue("goods_select_more"))) {
				ArrayList<DefDocItemDD> localArrayList = new ArrayList<DefDocItemDD>();
				for (int i = 0; i < localList.size(); i++) {
					GoodsThin localGoodsThin = (GoodsThin) localList.get(i);
					localArrayList.add(fillItem(localGoodsThin, 1, 0.0D, 0));
				}
				startActivityForResult(new Intent(OutInDocEditActivity.this, OutInDocAddMoreGoodsAct.class)
						.putExtra("items", JSONUtil.toJSONString(localArrayList)).putExtra("doc", doccg), 1);
			}
		}
	};

	// private void addGoodsClick() {
	// if ((menuPopup != null) && (menuPopup.isShowing())) {
	// menuPopup.dismiss();
	// WindowManager.LayoutParams localLayoutParams =
	// getWindow().getAttributes();
	// localLayoutParams.alpha = 1.0F;
	// getWindow().setAttributes(localLayoutParams);
	// }
	// List<GoodsThin> localList = searchHelper.getAdapter().getSelect();
	// if ((localList == null) || (localList.size() == 0)) {
	// PDH.showMessage("请选择商品！");
	// return;
	// }
	// atvSearch.setText("");
	// if (!"1".equals(new AccountPreference().getValue("goods_select_more"))) {
	// ArrayList<DefDocItemDD> localArrayList = new ArrayList<DefDocItemDD>();
	// for (int i = 0; i < localList.size(); i++) {
	// GoodsThin localGoodsThin = (GoodsThin) localList.get(i);
	// localArrayList.add(fillItem(localGoodsThin, 1, 0.0D, 0));
	// }
	// startActivityForResult(new Intent().setClass(this,
	// OutInDocAddMoreGoodsAct.class)
	// .putExtra("items", JSONUtil.toJSONString(localArrayList)).putExtra("doc",
	// doccg), 1);
	// }
	//
	// }

	/* 商品item点击 商品详细信息 */
	OnItemClickListener goodsItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// deleBm();
			Intent localIntent = new Intent(OutInDocEditActivity.this, OutInDocAddGoodAct.class);
			localIntent.putExtra("customerid", doccg.getCustomerid());
			localIntent.putExtra("position", position);
			localIntent.putExtra("docitem", listItem.get(position));
			localIntent.putExtra("doc", doccg);
			startActivityForResult(localIntent, 3);
		}
	};
	// 销售订单
	private AdapterView.OnItemClickListener onItemClickListeners = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			onTouch(null, null);
			final GoodsThin localGoodsThin = searchHelper.getAdapter().getTempGoods().get(position);
			atvSearch.setText("");
			PDH.show(OutInDocEditActivity.this, new PDH.ProgressCallBack() {

				public void action() {
					DefDocItemDD onitemdefdoc = fillItem(localGoodsThin, 0, 0.0D, 0);
					newListItem = new ArrayList<DefDocItemDD>();
					List<ReqStrGetGoodsPrice> localArrayList = new ArrayList<ReqStrGetGoodsPrice>();
					ReqStrGetGoodsPrice localReqStrGetGoodsPrice = new ReqStrGetGoodsPrice();
					localReqStrGetGoodsPrice.setType(1);
					localReqStrGetGoodsPrice.setCustomerid(doccg.getCustomerid());
					// localReqStrGetGoodsPrice.setWarehouseid(onitemdefdoc.getWarehouseid());
					localReqStrGetGoodsPrice.setGoodsid(onitemdefdoc.getGoodsid());
					localReqStrGetGoodsPrice.setUnitid(onitemdefdoc.getUnitid());
					localReqStrGetGoodsPrice.setPrice(0.0D);
					localReqStrGetGoodsPrice.setIsdiscount(false);
					localArrayList.add(localReqStrGetGoodsPrice);
					newListItem.add(onitemdefdoc);
					String localString = new ServiceGoods().gds_GetMultiGoodsPrice(localArrayList, true,
							onitemdefdoc.isIsusebatch());
					handlerDetail.sendMessage(handlerDetail.obtainMessage(2, localString));
				}

			});

		}
	};
	private Handler handlerDetail = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (RequestHelper.isSuccess(msg.obj.toString())) {
				if (newListItem.isEmpty()) {
					return;
				}
				DefDocItemDD onitemdefdoc = newListItem.get(0);
				List<ReqStrGetGoodsPrice> localList = JSONUtil.str2list(msg.obj.toString(), ReqStrGetGoodsPrice.class);
				ReqStrGetGoodsPrice localReqStrGetGoodsPrice2 = (ReqStrGetGoodsPrice) localList.get(0);
				onitemdefdoc.setIsdiscount(localReqStrGetGoodsPrice2.getIsdiscount());
				onitemdefdoc.setPrice(Utils.normalizePrice(localReqStrGetGoodsPrice2.getPrice()));
				onitemdefdoc.setSubtotal(Utils.normalizeSubtotal(onitemdefdoc.getNum() * onitemdefdoc.getPrice()));
				// defDocItemDD.setBatch(localReqStrGetGoodsPrice2.getBatch());
				// defDocItemDD.setProductiondate(localReqStrGetGoodsPrice2.getProductiondate());
				onitemdefdoc.setDiscountratio(doccg.getDiscountratio());
				onitemdefdoc.setDiscountprice(
						Utils.normalizePrice(onitemdefdoc.getPrice() * onitemdefdoc.getDiscountratio()));
				onitemdefdoc.setDiscountsubtotal(
						Utils.normalizeSubtotal(onitemdefdoc.getNum() * onitemdefdoc.getDiscountprice()));

				if (onitemdefdoc.isIsdiscount()) {
					onitemdefdoc.setIsdiscount(localReqStrGetGoodsPrice2.getIsdiscount());
				}
				newListItem.add(onitemdefdoc);
				Intent localIntent = new Intent(OutInDocEditActivity.this, OutInDocAddGoodAct.class);
				localIntent.putExtra("customerid", doccg.getCustomerid());
				localIntent.putExtra("docitem", onitemdefdoc);
				localIntent.putExtra("doc", doccg);
				startActivityForResult(localIntent, 4);
			}
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "单击显示菜单").setIcon(getResources().getDrawable(R.drawable.btn_submenu)).setShowAsAction(1);
		return super.onCreateOptionsMenu(menu);
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {

		switch (menu.getItemId()) {
		case android.R.id.home:
			intenToMain();
			break;

		case 0:
			// popup_menu_outin
			if (menuPopup == null) {
				menuPopup = new MenuPayPopup(OutInDocEditActivity.this);
			}
			menuPopup.setMenuPopup(menuPopupListener);
			menuPopup.showAtLocation(listView, Gravity.BOTTOM, 0, 0);
			// 设置背景颜色
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.alpha = 0.8F;
			getWindow().setAttributes(localLayoutParams);
			break;
		}
		return true;
	};

	/**
	 * 保存并 跳到主页面
	 */
	private void intenToMain() {
		if (ishaschanged) {
			MAlertDialog maler = new MAlertDialog(this);

			maler.setMessage("是否保存当前单据?");
			maler.setNeutralButton(new MAlertDialog.OnClickListener() {

				@Override
				public void onClick(MAlertDialog dialog) {
					dialog.dismiss();
					String localString = validateDoc();
					if (localString != null) {
						InfoDialog.showError(OutInDocEditActivity.this, localString);
						return;
					}
					saveGoods();
				}
			});
			maler.setNegativeButton(new MAlertDialog.OnClickListener() {

				@Override
				public void onClick(MAlertDialog dialog) {
					Intent intent = new Intent(OutInDocEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();
				}
			});
			maler.show();
			return;
		}
		Intent intent = new Intent(OutInDocEditActivity.this, SwyMain.class);
		startActivity(intent);
		finish();

	}

	private String validateDoc() {
		String message = null;
		if ((doccg.getDiscountratio() <= 0.0D) || (doccg.getDiscountratio() > 1.0D)) {
			return "整单折扣必须大于0且小于等于1";
		}
		if (!TextUtils.isEmptyS(doccg.getSettletime())) {
			return "结算日期不能为空!";
		}
		if (!TextUtils.isEmptyS(doccg.getCustomerid())) {
			return "没有选择客户!";
		}
		List<DefDocItemDD> items = adapter.getItems();
		if (items != null && items.size() > 0) {
			for (int k = 0; k < items.size(); k++) {
				DefDocItemDD defDocItemDD = items.get(k);
				if (defDocItemDD.getNum() == 0.0D) {
					message = "【" + defDocItemDD.getGoodsname() + "】数量为0";
				}

				if (!TextUtils.isEmptyS(defDocItemDD.getUnitid())) {
					message = "【" + defDocItemDD.getGoodsname() + "】没有选择单位";
				}
			}
		}
		return message;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				doccg = (Def_Doc) data.getExtras().get("doc");
				break;
			case 1:
				newListItem = (ArrayList<DefDocItemDD>) JSONUtil.str2list(data.getStringExtra("items"),
						DefDocItemDD.class);

				final ArrayList<ReqStrGetGoodsPrice> localArrayList = new ArrayList<ReqStrGetGoodsPrice>();

				for (int i = 0; i < newListItem.size(); i++) {
					DefDocItemDD localDefDocItemXS5 = newListItem.get(i);
					ReqStrGetGoodsPrice localReqStrGetGoodsPrice = new ReqStrGetGoodsPrice();
					localReqStrGetGoodsPrice.setType(1);
					localReqStrGetGoodsPrice.setCustomerid(doccg.getCustomerid());
					// localReqStrGetGoodsPrice.setWarehouseid(localDefDocItemXS5.getWarehouseid());
					localReqStrGetGoodsPrice.setGoodsid(localDefDocItemXS5.getGoodsid());
					localReqStrGetGoodsPrice.setUnitid(localDefDocItemXS5.getUnitid());
					localReqStrGetGoodsPrice.setPrice(0.0D);
					localReqStrGetGoodsPrice.setIsdiscount(false);
					localArrayList.add(localReqStrGetGoodsPrice);
				}

				PDH.show(this, new ProgressCallBack() {

					@Override
					public void action() {
						final String localString2 = new ServiceGoods().gds_GetMultiGoodsPrice(localArrayList, true,
								true);
						runOnUiThread(new Runnable() {
							public void run() {
								setMainItem(localString2);
							}
						});

					}
				});
				ishaschanged = true;
				// initListener();
				setActionBarText();
				break;
			case 3:
				DefDocItemDD itemdd = (DefDocItemDD) data.getExtras().get("docitem");
				int position = data.getIntExtra("position", 0);
				listItem.set(position, itemdd);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				ishaschanged = true;
				itemCount();
				combinationItem();
				break;
			case 4:
				DefDocItemDD docitem = (DefDocItemDD) data.getExtras().get("docitem");
				listItem.add(docitem);
				combinationItem();
				adapter.setItems(listItem);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;
			}

		}

	};

	protected void setMainItem(String localString2) {
		List<ReqStrGetGoodsPrice> localList = JSONUtil.str2list(localString2, ReqStrGetGoodsPrice.class);
		for (int i = 0; i < newListItem.size(); i++) {
			DefDocItemDD defDocItemDD = newListItem.get(i);
			for (int j = 0; j < localList.size(); j++) {
				ReqStrGetGoodsPrice localReqStrGetGoodsPrice1 = (ReqStrGetGoodsPrice) localList.get(j);
				if ((defDocItemDD.getGoodsid().equals(localReqStrGetGoodsPrice1.getGoodsid()))
						&& (defDocItemDD.getUnitid().equals(localReqStrGetGoodsPrice1.getUnitid()))) {

					defDocItemDD.setIsdiscount(localReqStrGetGoodsPrice1.getIsdiscount());
					defDocItemDD
							.setPrice(Utils.normalizePrice(Utils.normalizePrice(localReqStrGetGoodsPrice1.getPrice())));
					defDocItemDD.setSubtotal(Utils.normalizePrice(defDocItemDD.getNum() * defDocItemDD.getPrice()));
					defDocItemDD.setDiscountprice(
							Utils.normalizePrice(defDocItemDD.getPrice() * defDocItemDD.getDiscountratio()));
					defDocItemDD.setDiscountsubtotal(
							Utils.normalizeSubtotal(defDocItemDD.getNum() * defDocItemDD.getDiscountprice()));
					boolean isgift = false;
					if (defDocItemDD.getPrice() == 0.0D) {
						isgift = true;
					}
					defDocItemDD.setIsgift(isgift);
					String bigNum = unitDAO.getBigNum(defDocItemDD.getGoodsid(), defDocItemDD.getUnitid(),
							defDocItemDD.getNum());
					defDocItemDD.setBignum(bigNum);
					defDocItemDD.setIsdiscount(localReqStrGetGoodsPrice1.getIsdiscount());
				}

			}
		}
		if (newListItem.size() > 0) {
			listItem.addAll(newListItem);
			combinationItem();
			adapter.setItems(listItem);
			listView.setAdapter(adapter);
		}
	}

	// /**
	// * 获取每个商品的 价格并设置给当前商品
	// *
	// * @param item
	// */
	// private void setItemsGoods(DefDocItemDD item) {
	// GoodsUnit basicUnit = mGoodsUnitDAO.getBasicUnit(item.getGoodsid());//
	// 基本单位
	// if (item.getUnitid().equals(basicUnit.getUnitid())) {
	// String goodsPrice = new
	// ServiceStore().GetCustomerPrice(doccg.getCustomerid(),
	// basicUnit.getGoodsid(),
	// basicUnit.getUnitid());
	// if (TextUtils.isEmptyS(goodsPrice)) {
	// JSONUtil.readValue2(goodsPrice);
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
	// item.setDiscountprice(Utils.normalize(price * item.getDiscountratio(),
	// 3));// 折后单价
	// item.setDiscountsubtotal(Utils.normalize(price * item.getDiscountratio()
	// * item.getNum(), 3));// 折后总价
	// GoodsUnitDAO localGoodsUnitDAO = new GoodsUnitDAO();// 设置基本单位
	// String bigNum = localGoodsUnitDAO.getBigNum(item.getGoodsid(),
	// item.getUnitid(), item.getNum());
	// item.setBignum(bigNum);
	// } else {
	// item.setIsgift(true);
	// item.setPrice(0);// 单价
	// item.setSubtotal(0);
	// item.setDiscountprice(0);
	// item.setDiscountratio(1.0);
	// item.setDiscountsubtotal(0);
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
	//
	// }

	// 设置商品件数
	protected void setBigNum(DefDocItemDD itemGoods) {
		String bigNum = unitDAO.getBigNum(itemGoods.getGoodsid(), itemGoods.getUnitid(), itemGoods.getNum());
		itemGoods.setBignum(bigNum);
	}

	JSONObject JsonInfor;
	boolean isNetwork;// 网络是否成功！
	// 底部菜单回调
	EditMenuPopupListener menuPopupListener = new EditMenuPopupListener() {

		@Override // 保存
		public void save() {
			saveGoods();
		}

		@Override // 属性
		public void docProperty() {
			// deleBm();
			Intent localIntent = new Intent(OutInDocEditActivity.this, OutInDocOpen.class);
			localIntent.putExtra("doc", doccg);
			startActivityForResult(localIntent, 0);
		}

		@Override // 删除
		public void delete() {
			Intent intent = new Intent(OutInDocEditActivity.this, SwyMain.class);
			startActivity(intent);
			finish();
		}

		RespServiceInfor serviceInfor;

		@Override
		public void check(final boolean check) {
			String validateDoc = validateDoc();
			if (validateDoc != null) {
				PDH.showMessage(validateDoc);
				return;
			}
			List<DefDocItemDD> items = adapter.getItems();
			if ((items == null) || (items.size() == 0)) {
				InfoDialog.showError(OutInDocEditActivity.this, "空单不能过账");
				return;
			}

			PDH.show(OutInDocEditActivity.this, "正在审核中...", new PDH.ProgressCallBack() {

				@Override
				public void action() {

					doccg.setIsposted(true);

					// 审核过账
					final String addSaleReview = serviceStore.AddSaleReview(doccg, listItem);
					if (TextUtils.isEmpty(addSaleReview)) {
						showError("审核失败!请查看商无忧服务是否启动 !");
						return;
					}
					serviceInfor = JSONUtil.readValue3(addSaleReview);
					if (!check && serviceInfor.Result) {// 是否是过账单，是否过账成功，都成功在跳转
						Intent intent = new Intent(OutInDocEditActivity.this, SwyMain.class);
						startActivity(intent);
						showSuccess(serviceInfor.Json.Desc);
					}

					if (serviceInfor.Result && check) {
						RespServiceInfor printDoc = PrintDoc(serviceInfor);
						if (printDoc != null && printDoc.Result) {
							Intent intent = new Intent(OutInDocEditActivity.this, SwyMain.class);
							startActivity(intent);
							finish();
							showSuccess("打印成功!");
						}
					}
					if (serviceInfor.Result && BLUE_PRINT_DOC == 3) {
						doccg.setShowid(serviceInfor.Json.Data);
						runOnUiThread(new Runnable() {
							public void run() {
								bluePrintDoc();
							}
						});
					}
				}
			});
		}

		@Override
		public void blueDevicePrint() {
			BLUE_PRINT_DOC = 3;
			menuPopupListener.check(false);
		}
	};
	private static int BLUE_PRINT_DOC = 0;

	private void bluePrintDoc() {
		BTPrintHelper btprint = new BTPrintHelper(OutInDocEditActivity.this);
		PrintMode printMode = PrintMode.getPrintMode();
		printMode.setDocInfo(getDocPrintInfo());
		printMode.setDatainfo(getDocPrintData());
		btprint.setMode(printMode);
		btprint.print(new AccountPreference().getPrinter());
		btprint.setPrintOverCall(new PrintOverCall() {

			@Override
			public void printOver() {
				showSuccess("打印完毕!");
			}
		});
	}

	protected List<FieldSaleItemForPrint> getDocPrintData() {
		List<FieldSaleItemForPrint> datainfo = new ArrayList<FieldSaleItemForPrint>();
		for (DefDocItemDD item : listItem) {
			FieldSaleItemForPrint itemForPrint = new FieldSaleItemForPrint();
			itemForPrint.setBarcode(item.getBarcode());
			itemForPrint.setDiscountratio(item.getDiscountprice());
			itemForPrint.setDiscountsubtotal(item.getDiscountsubtotal());
			itemForPrint.setGoodsid(item.getGoodsid());
			itemForPrint.setGoodsname(item.getGoodsname());
			itemForPrint.setItemtype("12");
			itemForPrint.setNum(item.getNum());
			itemForPrint.setPrice(item.getPrice());
			itemForPrint.setRemark(item.getRemark());
			itemForPrint.setSpecification(item.getSpecification());
			itemForPrint.setUnitname(item.getUnitname());
			datainfo.add(itemForPrint);
		}
		return datainfo;
	}

	protected FieldSaleForPrint getDocPrintInfo() {
		itemCount();
		FieldSaleForPrint v2 = new FieldSaleForPrint();
		v2.setDoctype("销售订单");
		v2.setShowid(doccg.getShowid());
		v2.setCustomername(this.doccg.getCustomername());
		v2.setDepartmentname(this.doccg.getDepartmentname());
		v2.setBuildername(this.doccg.getBuildername());
		v2.setBuildtime(Utils.formatDate(this.doccg.getBuildtime(), "yyyy-MM-dd HH:mm:ss"));
		v2.setSumamount("合计:" + getSumMoney());
		v2.setPreference("优惠:" + this.doccg.getPreference());
		v2.setReceivable("应收:" + Utils.getSubtotalMoney(getSumMoney() - doccg.getPreference()));
		// v2.setReceived("已收:" + Utils.getRecvableMoney(received));
		v2.setNum("数量:" + String.valueOf(sumNum));
		v2.setRemark(doccg.getRemark());
		return v2;
	}

	private double sumMoney;
	private double sumNum;

	private void itemCount() {
		sumMoney = 0D;
		sumNum = 0D;
		for (DefDocItemDD item : listItem) {
			sumMoney += item.getDiscountsubtotal();
			sumNum += item.getNum();
		}
	}

	/**
	 * 当前总金额
	 * 
	 * @return
	 */
	public double getSumMoney() {
		return sumMoney;
	}

	private void saveGoods() {
		if (listItem == null || listItem.size() == 0) {
			showError("请添加商品后保存!");
			return;
		}
		String validateDoc = validateDoc();
		if (validateDoc != null) {
			PDH.showMessage(validateDoc);
			return;
		}
		PDH.show(OutInDocEditActivity.this, "保存中", new ProgressCallBack() {

			@Override
			public void action() {
				doccg.setIsposted(false);
				// doccg.setMoney(discountsubtotal);
				String addSaleReview = serviceStore.AddSaleReview(doccg, listItem);
				if (TextUtils.isEmpty(addSaleReview)) {
					showError("失败!没有从服务器获取返回信息！请重试 !");
					return;
				}
				JSONUtil.readValue2(addSaleReview);
				if (JSONUtil.Result && (doccg.getDocid() == 0)) {
					showSuccess("保存成功!");
					Intent intent = new Intent(OutInDocEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();
					// doccg.setDocid(JSONUtil.DocID);// 保存单据id
					// doccg.setShowid(JSONUtil.Data);// 保存单据号
				} else if (JSONUtil.Result && (doccg.getDocid() != 0)) {
					showSuccess("更新成功!");
				} else {
					showSuccess(JSONUtil.Info);
				}
			}
		});
	}

	// boolean isprint = false;// 是否打印成功!
	private RespServiceInfor PrintDoc(RespServiceInfor infor) {
		// 审核打印
		Def_DocDraft draft = new Def_DocDraft();
		draft.setBuildtime(doccg.getBuildtime());
		draft.setMaketime(doccg.getBuildtime());
		draft.setDepartmentid(doccg.getDepartmentid());
		draft.setDepartmentname(doccg.getDepartmentname());
		draft.setShowid(infor.Json.Data);
		draft.setDoctypeid("12");// 单据类型， 12销售订单
		draft.setId(Long.parseLong(infor.Json.DocID));// 单据id
		String printdoc = serviceStore.PrintDoc(draft);
		if (TextUtils.isEmptyS(printdoc)) {
			return JSONUtil.readValue3(printdoc);
		}
		return null;
	}

	private boolean ishaschanged;
	private ArrayList<DefDocItemDD> newListItem;
	private List<DefDocItemDD> listItem;
	// private BarcodeConfig bc;
	// private BarcodeManager bm;
	private ArrayList<DefDocItemDD> localArrayList;
	private Scaner scaner;
	private GoodsUnitDAO unitDAO;

	@Override
	public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
		if ((this.menuPopup != null) && (this.menuPopup.isShowing())) {
			this.menuPopup.dismiss();
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.alpha = 1.0F;
			getWindow().setAttributes(localLayoutParams);
		}
		return false;
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
		localDefDocItem.setIsgift(localDefDocItem.getPrice() == 0.0D ? true : false);
		// localDefDocItem.setCostprice(0.0D);
		localDefDocItem.setRemark("");
		localDefDocItem.setRversion(0L);
		localDefDocItem.setIsdiscount(false);
		localDefDocItem.setIsusebatch(localGoodsThin.isIsusebatch());
		return localDefDocItem;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			intenToMain();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void setActionBarText() {
		// if (ishaschanged) {
		// getActionBar().setTitle("销售订单录入");
		// } else {
		// getActionBar().setTitle("已经保存");
		// }
		getActionBar().setTitle("销售订单录入");

	}

}
