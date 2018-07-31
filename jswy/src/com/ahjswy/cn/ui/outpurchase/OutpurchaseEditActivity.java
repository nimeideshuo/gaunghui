package com.ahjswy.cn.ui.outpurchase;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.dao.WarehouseDAO;
import com.ahjswy.cn.model.DefDocCG;
import com.ahjswy.cn.model.DefDocItemCG;
import com.ahjswy.cn.model.DefDocItemTH;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.model.DocContainerEntity;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.popupmenu.OutpurchaseEditMenuPopup;
import com.ahjswy.cn.request.ReqStrGetGoodsPrice;
import com.ahjswy.cn.service.ServiceGoods;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.ui.SearchHelper;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.ui.outpurchase.OutpurDocItemAdapter.Sum;
import com.ahjswy.cn.utils.InfoDialog;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.AutoTextView;
import com.ahjswy.cn.views.Dialog_listCheckBox;
import com.ahjswy.cn.views.Dialog_message;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;

import android.app.ActionBar;
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
import mexxen.mx5010.barcode.BarcodeConfig;
import mexxen.mx5010.barcode.BarcodeEvent;
import mexxen.mx5010.barcode.BarcodeListener;
import mexxen.mx5010.barcode.BarcodeManager;

/**
 * 销售退货开单
 * 
 * @author Administrator
 *
 */
public class OutpurchaseEditActivity extends BaseActivity
		implements OnTouchListener, OnClickListener, OnItemClickListener, Sum {
	private SearchHelper searchHelper;
	private Button btnAdd;
	private AutoTextView atvSearch;
	private OutpurchaseEditMenuPopup menuPopup;
	private boolean ishaschanged;
	private DefDocCG defdoccg;
	private List<DefDocPayType> listPayType;
	private SwipeMenuListView listview_copy_dele;
	private OutpurDocItemAdapter adapter;
	private Button bt_sumNumber;
	private Button bt_totalSum;
	private ServiceStore serviceStore;
	private List<Long> listItemDelete;
	private ArrayList<DefDocItemTH> newListItem;
	private List<DefDocItemTH> listItem;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_inpurchase_edit);
		initView();
		initDate();
		addListener();
	}

	private Dialog_listCheckBox dialog;

	private void initView() {
		LinearLayout linearSearch = (LinearLayout) findViewById(R.id.linearSearch);
		searchHelper = new SearchHelper(this, linearSearch);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		atvSearch = ((AutoTextView) findViewById(R.id.atvSearch));
		btnAdd.setOnClickListener(this);
		serviceStore = new ServiceStore();
		bt_sumNumber = (Button) findViewById(R.id.bt_sumNumber);
		bt_totalSum = (Button) findViewById(R.id.bt_totalSum);
		listview_copy_dele = (SwipeMenuListView) findViewById(R.id.listView_addShop);
		listview_copy_dele.setOnTouchListener(this);
		listview_copy_dele.setOnItemClickListener(this);
		atvSearch.setOnItemClickListener(onItemClickListeners);
		if (listItem == null) {
			listItem = new ArrayList<DefDocItemTH>();
		}
		dialog = new Dialog_listCheckBox(this);
	}

	private void initDate() {
		DocContainerEntity docContainerEntity = (DocContainerEntity) getIntent().getSerializableExtra("docContainer");
		defdoccg = ((DefDocCG) JSONUtil.readValue(docContainerEntity.getDoc(), DefDocCG.class));
		ishaschanged = getIntent().getBooleanExtra("ishaschanged", true);
		listPayType = JSONUtil.str2list(docContainerEntity.getPaytype(), DefDocPayType.class);
		adapter = new OutpurDocItemAdapter(this);
		adapter.setSum(this);// 回调计算底部数量总价
		SwipeMenuCreator local5 = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem localSwipeMenuItem1 = new SwipeMenuItem(
						OutpurchaseEditActivity.this.getApplicationContext());
				localSwipeMenuItem1.setTitle("复制");
				localSwipeMenuItem1.setTitleSize(14);
				localSwipeMenuItem1.setTitleColor(-16777216);
				localSwipeMenuItem1.setWidth(100);
				localSwipeMenuItem1.setBackground(new ColorDrawable(Color.rgb(48, 177, 245)));
				menu.addMenuItem(localSwipeMenuItem1);
				SwipeMenuItem localSwipeMenuItem2 = new SwipeMenuItem(
						OutpurchaseEditActivity.this.getApplicationContext());
				localSwipeMenuItem2.setTitle("删除");
				localSwipeMenuItem2.setTitleSize(14);
				localSwipeMenuItem2.setTitleColor(-16777216);
				localSwipeMenuItem2.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
				localSwipeMenuItem2.setWidth(100);
				menu.addMenuItem(localSwipeMenuItem2);
			}
		};
		listview_copy_dele.setMenuCreator(local5);
		listview_copy_dele.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					DefDocItemTH DefDocItemTH = listItem.get(position);
					DefDocItemTH.setItemid(0L);
					listItem.add(DefDocItemTH);
					handler.postDelayed(new Runnable() {
						public void run() {
							adapter.setData(listItem);
							ishaschanged = true;
							setActionBarText();
						}
					}, 180L);
					break;
				case 1:
					DefDocItemTH DefDocItemTH1 = listItem.get(position);
					if (DefDocItemTH1.getItemid() > 0L) {
						listItemDelete.add(Long.valueOf(DefDocItemTH1.getItemid()));
					}
					listItem.remove(position);
					handler.postDelayed(new Runnable() {
						public void run() {
							adapter.setData(listItem);
							ishaschanged = true;
							setActionBarText();
							sum();
						}
					}, 180L);
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	Handler handlerItem = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				deleBm();
				@SuppressWarnings("unchecked")
				ArrayList<DefDocItemCG> defdocitem = (ArrayList<DefDocItemCG>) msg.obj;
				Intent intent = new Intent().setClass(OutpurchaseEditActivity.this, OutpurDocAddMoreGoodsAct.class);
				intent.putExtra("items", JSONUtil.toJSONString(defdocitem));
				intent.putExtra("doc", defdoccg);
				startActivityForResult(intent, 1);

				break;
			}
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		// 添加图片
		paramMenu.add(0, 0, 0, "单击显示菜单").setIcon(getResources().getDrawable(R.drawable.btn_submenu)).setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			intenToMain();
			break;
		case 0:
			if (this.menuPopup == null) {
				menuPopup = new OutpurchaseEditMenuPopup(this);
			}
			menuPopup.showAtLocation(listview_copy_dele, Gravity.BOTTOM, 0, 0);
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.alpha = 0.8F;
			getWindow().setAttributes(localLayoutParams);
			break;
		default:
			break;
		}

		return true;
	}

	private AdapterView.OnItemClickListener onItemClickListeners = new AdapterView.OnItemClickListener() {
		@SuppressWarnings("rawtypes")
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			if ((menuPopup != null) && (menuPopup.isShowing())) {
				menuPopup.dismiss();
				WindowManager.LayoutParams localLayoutParams = OutpurchaseEditActivity.this.getWindow().getAttributes();
				localLayoutParams.alpha = 1.0F;
				OutpurchaseEditActivity.this.getWindow().setAttributes(localLayoutParams);
			}

			final GoodsThin localGoodsThin = searchHelper.getAdapter().getTempGoods().get(position);
			atvSearch.setText("");
			PDH.show(OutpurchaseEditActivity.this, new PDH.ProgressCallBack() {
				public void action() {
					DefDocItemTH defdocitemth = fillItem(localGoodsThin, 0.0D, 0);
					newListItem = new ArrayList<DefDocItemTH>();
					newListItem.add(defdocitemth);
					List<ReqStrGetGoodsPrice> localArrayList = new ArrayList<ReqStrGetGoodsPrice>();
					ReqStrGetGoodsPrice localReqStrGetGoodsPrice = new ReqStrGetGoodsPrice();
					localReqStrGetGoodsPrice.setType(1);
					localReqStrGetGoodsPrice.setCustomerid(defdoccg.getSupplerid());
					localReqStrGetGoodsPrice.setWarehouseid(defdocitemth.getWarehouseid());
					localReqStrGetGoodsPrice.setGoodsid(defdocitemth.getGoodsid());
					localReqStrGetGoodsPrice.setUnitid(defdocitemth.getUnitid());
					localReqStrGetGoodsPrice.setPrice(0.0D);
					localReqStrGetGoodsPrice.setIsdiscount(false);
					localArrayList.add(localReqStrGetGoodsPrice);
					String localString = new ServiceGoods().gds_GetMultiGoodsPrice(localArrayList, true,
							defdocitemth.isIsusebatch());
					handlerGet.sendMessage(handlerGet.obtainMessage(2, localString));
				}
			});

		}

	};

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
						InfoDialog.showError(OutpurchaseEditActivity.this, localString);
						return;
					}
					PDH.show(OutpurchaseEditActivity.this, new ProgressCallBack() {

						@Override
						public void action() {
							final String localString = serviceStore.str_SaveTHDoc(defdoccg, adapter.getData(),
									listPayType, listItemDelete);

							handler.post(new Runnable() {
								public void run() {
									if (RequestHelper.isSuccess(han())) {
										PDH.showSuccess("保存成功");
										Intent intent = new Intent(OutpurchaseEditActivity.this, SwyMain.class);
										startActivity(intent);
										finish();
										return;
									}
									RequestHelper.showError(localString);
								}

							});
						}
					});
				}
			});
			maler.setNegativeButton(new MAlertDialog.OnClickListener() {

				@Override
				public void onClick(MAlertDialog dialog) {
					Intent intent = new Intent(OutpurchaseEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();
				}
			});
			maler.show();
			return;
		}
		Intent intent = new Intent(OutpurchaseEditActivity.this, SwyMain.class);
		startActivity(intent);
		finish();

	}

	private String han() {
		List<DefDocItemTH> data = adapter.getData();
		for (int i = 0; i < data.size(); i++) {
			DefDocItemTH defdocitemth = data.get(i);
			defdocitemth.setItemid(10000 + i);
			defdocitemth.setDocid(2000 + i);
		}
		DocContainerEntity localDocContainerEntity = new DocContainerEntity();
		localDocContainerEntity.setPaytype("101");
		localDocContainerEntity.setDeleteitem(JSONUtil.toJSONString(listItemDelete));
		localDocContainerEntity.setDoc(JSONUtil.toJSONString(defdoccg));
		localDocContainerEntity.setDoctype(JSONUtil.toJSONString(listPayType));
		localDocContainerEntity.setItem(JSONUtil.toJSONString(adapter.getData()));
		localDocContainerEntity.setInfo("");
		return JSONUtil.toJSONString(localDocContainerEntity);
	}

	private String validateDoc() {
		String message = null;
		if (TextUtils.isEmptyS(defdoccg.getBuildtime())) {
			defdoccg.setBuildtime(Utils.formatDate(Utils.getCurrentTime(false), "yyyy-MM-dd HH:mm:ss"));
		}
		if (TextUtils.isEmptyS(defdoccg.getBuilderid())) {
			defdoccg.setBuilderid(SystemState.getUser().getId());
		}
		if (!TextUtils.isEmptyS(defdoccg.getDepartmentid())) {
			message = "部门不能为空";
		}
		if ((defdoccg.getDiscountratio() <= 0.0D) || (defdoccg.getDiscountratio() > 1.0D)) {
			PDH.showMessage("整单折扣必须大于0且小于等于1");
			return null;
		}
		if (!TextUtils.isEmptyS(defdoccg.getDeliverytime())) {
			message = "交货日期不能为空";
		}
		if (!TextUtils.isEmptyS(defdoccg.getSettletime())) {
			message = "结算日期不能为空";
		}
		if (!TextUtils.isEmptyS(defdoccg.getPrinttemplate())) {
			message = "打印模板不能为空";
		}
		if (newListItem != null && newListItem.size() > 0) {
			for (int k = 0; k < newListItem.size(); k++) {
				DefDocItemTH DefDocItemTH = newListItem.get(k);
				if (DefDocItemTH.getNum() == 0.0D) {
					message = "【" + DefDocItemTH.getGoodsname() + "】数量为0";
				}
				if (!TextUtils.isEmptyS(DefDocItemTH.getWarehouseid())) {
					message = "【" + DefDocItemTH.getGoodsname() + "】没有选择仓库";
				}

				if (!TextUtils.isEmptyS(DefDocItemTH.getUnitid())) {
					message = "【" + DefDocItemTH.getGoodsname() + "】没有选择单位";
				}
			}
		}

		return message;
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				if (msg.what == 2) {
					PDH.showMessage("删除成功");
					Intent intent = new Intent(OutpurchaseEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();
					return;
				}
				DocContainerEntity localDocContainerEntity = (DocContainerEntity) JSONUtil.fromJson(message,
						DocContainerEntity.class);
				defdoccg = JSONUtil.readValue(localDocContainerEntity.getDoc(), DefDocCG.class);
				listPayType = JSONUtil.str2list(localDocContainerEntity.getDoctype(), DefDocPayType.class);
				newListItem = (ArrayList<DefDocItemTH>) JSONUtil.str2list(localDocContainerEntity.getItem(),
						DefDocItemTH.class);
				listItemDelete.clear();
				listItemDelete = new ArrayList<Long>();
				System.out.println("res>>>>" + JSONUtil.toJSONString(localDocContainerEntity));
				switch (msg.what) {
				case 0:
					adapter.setData(newListItem);
					PDH.showSuccess("保存成功");
					ishaschanged = false;
					setActionBarText();
					return;
				case 1:
					// 判断是否过账失败 带测试过账失败返回值
					if ((defdoccg.isIsavailable() && defdoccg.isIsposted())) {// (doc.isIsavailable())
						// &&
						PDH.showSuccess("过账成功");
						startActivity(new Intent(OutpurchaseEditActivity.this, SwyMain.class));
						finish();
						return;
					}
					adapter.setData(newListItem);
					InfoDialog.showError(OutpurchaseEditActivity.this, localDocContainerEntity.getInfo());
					return;
				default:
					break;
				}
			}
			InfoDialog.showError(OutpurchaseEditActivity.this, message);
		};
	};

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if ((this.menuPopup != null) && (this.menuPopup.isShowing())) {
			this.menuPopup.dismiss();
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.alpha = 1.0F;
			getWindow().setAttributes(localLayoutParams);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAdd:
			atvSearch.clearFocus();
			if (atvSearch.getText().toString().length() > 0) {
				addMoreListener();
			} else {
				PDH.showMessage("请选择商品！");
			}
			break;

		default:
			break;
		}
	}

	private void addMoreListener() {
		onTouch(null, null);
		List<GoodsThin> localList = searchHelper.getAdapter().getSelect();
		if ((localList == null) || (localList.size() == 0)) {
			return;
		}
		deleBm();
		atvSearch.setText("");
		ArrayList<DefDocItemTH> localArrayList = new ArrayList<DefDocItemTH>();
		for (int i = 0; i < localList.size(); i++) {
			GoodsThin localGoodsThin = (GoodsThin) localList.get(i);
			localArrayList.add(fillItem(localGoodsThin, 0.0D, 0));
		}
		startActivityForResult(new Intent().setClass(OutpurchaseEditActivity.this, OutpurDocAddMoreGoodsAct.class)
				.putExtra("items", JSONUtil.toJSONString(localArrayList)).putExtra("doc", defdoccg), 1);
	}

	private DefDocItemTH fillItem(GoodsThin localGoodsThin, double num, long itemid) {
		GoodsUnitDAO localGoodsUnitDAO = new GoodsUnitDAO();
		DefDocItemTH defdocitemth = new DefDocItemTH();
		defdocitemth.setGoodsid(localGoodsThin.getId());
		defdocitemth.setGoodsname(localGoodsThin.getName());
		defdocitemth.setBarcode(localGoodsThin.getBarcode());
		defdocitemth.setSpecification(localGoodsThin.getSpecification());
		defdocitemth.setWarehouseid(defdoccg.getWarehouseid());
		defdocitemth.setWarehousename(defdoccg.getWarehousename());
		defdocitemth.setItemid(itemid);
		GoodsUnit localGoodsUnit = null;
		if (Utils.DEFAULT_OutDocUNIT == 0) {
			localGoodsUnit = localGoodsUnitDAO.queryBaseUnit(localGoodsThin.getId());
		} else {
			localGoodsUnit = localGoodsUnitDAO.queryBigUnit(localGoodsThin.getId());
		}

		defdocitemth.setUnitid(localGoodsUnit.getUnitid());
		defdocitemth.setUnitname(localGoodsUnit.getUnitname());
		defdocitemth.setNum(Utils.normalize(num, 2));
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
		// DefDocItemTH.setCostprice(0.0D);
		defdocitemth.setIsusebatch(localGoodsThin.isIsusebatch());

		return defdocitemth;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		DefDocItemTH defdocitemth = (DefDocItemTH) parent.getItemAtPosition(position);
		atvSearch.setText("");
		deleBm();
		int i = -1;
		Intent localIntent = new Intent();
		localIntent.putExtra("customerid", defdoccg.getSupplerid());
		localIntent.putExtra("positiongive", i);
		// localIntent.putExtra("docitemgive", "");
		localIntent.putExtra("position", position);
		localIntent.putExtra("docitem", defdocitemth);
		startActivityForResult(localIntent.setClass(OutpurchaseEditActivity.this, OutpurDocAddGoodAct.class), 2);
	}

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_FIRST_USER) {
			addListener();
		} else if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				newListItem = (ArrayList<DefDocItemTH>) JSONUtil.str2list(data.getStringExtra("items"),
						DefDocItemTH.class);
				final ArrayList<ReqStrGetGoodsPrice> localArrayList = new ArrayList<ReqStrGetGoodsPrice>();
				for (int i = 0; i < newListItem.size(); i++) {
					DefDocItemTH localDefDocItemXS5 = newListItem.get(i);
					ReqStrGetGoodsPrice localReqStrGetGoodsPrice = new ReqStrGetGoodsPrice();
					localReqStrGetGoodsPrice.setType(1);
					localReqStrGetGoodsPrice.setCustomerid(defdoccg.getSupplerid());
					localReqStrGetGoodsPrice.setWarehouseid(localDefDocItemXS5.getWarehouseid());
					localReqStrGetGoodsPrice.setGoodsid(localDefDocItemXS5.getGoodsid());
					localReqStrGetGoodsPrice.setUnitid(localDefDocItemXS5.getUnitid());
					localReqStrGetGoodsPrice.setPrice(0.0D);
					localReqStrGetGoodsPrice.setIsdiscount(false);
					localArrayList.add(localReqStrGetGoodsPrice);
				}
				PDH.show(this, new ProgressCallBack() {

					@Override
					public void action() {
						String localString2 = new ServiceGoods().gds_GetMultiGoodsPrice(localArrayList, true, true);

						handlerGet.sendMessage(handlerGet.obtainMessage(0, localString2));
					}
				});
				break;
			case 2:
				int j = data.getIntExtra("position", 0);
				DefDocItemTH DefDocItemTH3 = (DefDocItemTH) data.getSerializableExtra("docitem");
				newListItem = (ArrayList<DefDocItemTH>) adapter.getData();
				newListItem.set(j, DefDocItemTH3);
				// int k = data.getIntExtra("positiongive", -1);
				// DefDocItemTH DefDocItemTH4;
				// if ((DefDocItemTH3.isIspromotion()) &&
				// (DefDocItemTH3.getPromotiontype() == 1)) {
				// DefDocItemTH4 = (DefDocItemTH)
				// data.getSerializableExtra("docitemgive");
				// if (k >= 0) {
				// if ((DefDocItemTH4 != null) && (DefDocItemTH4.getNum() >
				// 0.0D)) {
				// newListItem.set(k, DefDocItemTH4);
				// }
				// }
				// }
				this.adapter.setData(newListItem);
				listview_copy_dele.setAdapter(adapter);
				ishaschanged = true;
				break;
			case 4:

				addListener();
				DefDocItemTH defDocItemTH4 = (DefDocItemTH) data.getSerializableExtra("docitem");
				listItem.add(defDocItemTH4);
				adapter.setData(listItem);
				listview_copy_dele.setAdapter(adapter);
				ishaschanged = true;
				refreshUI();
				break;
			case 5:
				// 收款方式
				addListener();
				String localString1 = data.getStringExtra("listpaytype");
				if (TextUtils.isEmptyS(localString1)) {
					listPayType = JSONUtil.str2list(localString1, DefDocPayType.class);
				}
				// 优惠
				double d2 = data.getDoubleExtra("preference", 0.0D);
				if (d2 != defdoccg.getPreference()) {
					defdoccg.setPreference(d2);
				}
				break;
			}
		}
	}

	private void addListener() {
		onTouch(null, null);
		BarcodeConfig barcodeConfig = new BarcodeConfig(this);
		// 设置条码输出模式 不显示模式(复制到粘贴板)
		barcodeConfig.setOutputMode(2);
		if (bm == null) {
			bm = new BarcodeManager(this);
		}
		bm.addListener(new BarcodeListener() {

			@Override
			public void barcodeEvent(BarcodeEvent event) {
				// 当条码事件的命令为“SCANNER_READ”时，进行操作
				atvSearch.setText("");
				if (dialog != null) {
					dialog.dismiss();
				}
				// TODO 调用 getBarcode()方法读取条码信息
				if (event.getOrder().equals("SCANNER_READ")) {
					ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(bm.getBarcode().toString());
					final ArrayList<DefDocItemTH> localArrayList = new ArrayList<DefDocItemTH>();
					if (goodsThinList.size() == 1) {
						DefDocItemTH fillItem = fillItem(goodsThinList.get(0), 0.0D, 0L);
						localArrayList.add(fillItem);
						handlerItem.sendMessage(handlerItem.obtainMessage(1, localArrayList));
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
									localArrayList.add(fillItem);
								}
								handlerItem.sendMessage(handlerItem.obtainMessage(1, localArrayList));
							}
						});
					} else {
						PDH.showFail("没有查找到商品！可以尝试更新数据");
					}
				}

			}
		});

	}

	Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String localString1 = msg.obj.toString();
			List<ReqStrGetGoodsPrice> localList = null;
			GoodsUnitDAO localGoodsUnitDAO;
			if (RequestHelper.isSuccess(localString1)) {
				if (msg.what == 0) {
					localList = JSONUtil.str2list(localString1, ReqStrGetGoodsPrice.class);
					if (newListItem != null) {
						if ((msg.what != 0) && (msg.what != 1)) {
							return;
						}
						localGoodsUnitDAO = new GoodsUnitDAO();
						for (int i = 0; i < newListItem.size(); i++) {
							DefDocItemTH DefDocItemTH1 = newListItem.get(i);
							for (int j = 0; j < localList.size(); j++) {
								ReqStrGetGoodsPrice localReqStrGetGoodsPrice1 = localList.get(j);
								if ((DefDocItemTH1.getGoodsid().equals(localReqStrGetGoodsPrice1.getGoodsid()))
										&& (DefDocItemTH1.getUnitid().equals(localReqStrGetGoodsPrice1.getUnitid()))) {
									String localString2 = localReqStrGetGoodsPrice1.getWarehouseid();
									if ((TextUtils.isEmptyS(localString2))
											&& (localString2.equals(DefDocItemTH1.getWarehouseid()))) {
										DefDocItemTH1.setWarehouseid(localString2);
										DefDocItemTH1.setWarehousename(
												new WarehouseDAO().getWarehouse(localString2).getName());
									}
									// 获取商品 是否允许打折
									DefDocItemTH1.setIsdiscount(localReqStrGetGoodsPrice1.getIsdiscount());
									// 设置商品单价
									DefDocItemTH1.setPrice(Utils.normalizePrice(
											Utils.normalizePrice(localReqStrGetGoodsPrice1.getPrice())));
									DefDocItemTH1.setSubtotal(
											Utils.normalizePrice(DefDocItemTH1.getNum() * DefDocItemTH1.getPrice()));
									if (DefDocItemTH1.isIsdiscount()) {
										DefDocItemTH1.setDiscountratio(defdoccg.getDiscountratio());
									}
									if (DefDocItemTH1.isIsusebatch()) {
										DefDocItemTH1.setBatch(localReqStrGetGoodsPrice1.getBatch());
									}
									// TODO 生产时间是否需要
									// DefDocItemTH1.setProductiondate(localReqStrGetGoodsPrice1.getProductiondate());
									DefDocItemTH1.setWarehouseid(localReqStrGetGoodsPrice1.getWarehouseid());
									if (!TextUtils.isEmptyS(localReqStrGetGoodsPrice1.getWarehouseid())) {
										Warehouse localWarehouse = new WarehouseDAO()
												.getWarehouse(localReqStrGetGoodsPrice1.getWarehouseid());
										if (localWarehouse != null) {
											DefDocItemTH1.setWarehousename(localWarehouse.getName());
										}
									}

									DefDocItemTH1.setDiscountprice(Utils.normalizePrice(
											DefDocItemTH1.getPrice() * DefDocItemTH1.getDiscountratio()));
									DefDocItemTH1.setDiscountsubtotal(Utils.normalizeSubtotal(
											DefDocItemTH1.getNum() * DefDocItemTH1.getDiscountprice()));
									boolean isgift = false;
									if (DefDocItemTH1.getPrice() == 0.0D) {
										isgift = true;
									}
									DefDocItemTH1.setIsgift(isgift);
									String bigNum = localGoodsUnitDAO.getBigNum(DefDocItemTH1.getGoodsid(),
											DefDocItemTH1.getUnitid(), DefDocItemTH1.getNum());
									DefDocItemTH1.setBignum(bigNum);
									DefDocItemTH1.setIsdiscount(localReqStrGetGoodsPrice1.getIsdiscount());
								}
							}

						}
					}
					if (newListItem != null && newListItem.size() > 0) {
						listItem.addAll(newListItem);
						adapter.setData(listItem);
						listview_copy_dele.setAdapter(adapter);
						addListener();
						ishaschanged = true;
						setActionBarText();

					}
					return;
				}
				if (msg.what == 2) {
					DefDocItemTH onitemdefdoc = newListItem.get(0);
					localList = JSONUtil.str2list(localString1, ReqStrGetGoodsPrice.class);
					ReqStrGetGoodsPrice localReqStrGetGoodsPrice2 = (ReqStrGetGoodsPrice) localList.get(0);
					String localString3 = localReqStrGetGoodsPrice2.getWarehouseid();
					if ((TextUtils.isEmptyS(localString3)) && (localString3.equals(onitemdefdoc.getWarehouseid()))) {
						onitemdefdoc.setWarehouseid(localString3);
						onitemdefdoc.setWarehousename(new WarehouseDAO().getWarehouse(localString3).getName());
					}
					onitemdefdoc.setIsdiscount(localReqStrGetGoodsPrice2.getIsdiscount());
					onitemdefdoc.setPrice(Utils.normalizePrice(localReqStrGetGoodsPrice2.getPrice()));
					onitemdefdoc.setSubtotal(Utils.normalizeSubtotal(onitemdefdoc.getNum() * onitemdefdoc.getPrice()));
					onitemdefdoc.setBatch(localReqStrGetGoodsPrice2.getBatch());
					// localDefDocItemXS2.setProductiondate(localReqStrGetGoodsPrice2.getProductiondate());

					onitemdefdoc.setDiscountratio(defdoccg.getDiscountratio());
					onitemdefdoc.setDiscountprice(
							Utils.normalizePrice(onitemdefdoc.getPrice() * onitemdefdoc.getDiscountratio()));
					onitemdefdoc.setDiscountsubtotal(
							Utils.normalizeSubtotal(onitemdefdoc.getNum() * onitemdefdoc.getDiscountprice()));
					if (onitemdefdoc.isIsdiscount()) {
						onitemdefdoc.setIsdiscount(localReqStrGetGoodsPrice2.getIsdiscount());
					}
					deleBm();
					Intent localIntent = new Intent(OutpurchaseEditActivity.this, OutpurDocAddGoodAct.class);
					localIntent.putExtra("customerid", defdoccg.getSupplerid());
					localIntent.putExtra("position", 0);
					localIntent.putExtra("docitem", onitemdefdoc);
					// localIntent.putExtra("positiongive", -1);
					// localIntent.putExtra("docitemgive", localDefDocItemXS3);
					startActivityForResult(localIntent, 4);
				}
			}
		};
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			intenToMain();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void setActionBarText() {
		ActionBar localActionBar = getActionBar();
		if (ishaschanged) {
			localActionBar.setTitle(defdoccg.getShowid() + "*");
		} else {
			localActionBar.setTitle(defdoccg.getShowid());
		}
	}

	double discountsubtotal;
	private BarcodeManager bm;

	@Override
	public void sum() {
		ishaschanged = true;
		setActionBarText();
		List<DefDocItemTH> data = adapter.getData();
		double sum = 0.00d;
		listItemDelete = adapter.getListItemDelete();
		for (int i = 0; i < data.size(); i++) {
			sum += data.get(i).getDiscountsubtotal();
		}
		sum = Utils.normalizePrice(sum);
		bt_sumNumber.setText("数量:" + data.size() + "个");
		bt_totalSum.setText("总额:" + sum + "元");
		discountsubtotal = sum;
	}

	// 过账 过账打印
	public void check(final boolean isprint) {
		String localString = validateDoc();
		if (localString != null) {
			InfoDialog.showError(this, localString);
			return;
		}
		final List<DefDocItemTH> data = adapter.getData();
		if ((data == null) || (data.size() == 0)) {
			InfoDialog.showError(this, "空单不能过账");
			return;
		}
		final Dialog_message dialog = new Dialog_message(this);
		dialog.show();
		dialog.setTitle("提示");
		dialog.setMessage("过账后的单据不能修改。\n确定过账？");
		dialog.setCancelListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PDH.show(OutpurchaseEditActivity.this, new PDH.ProgressCallBack() {

					@Override
					public void action() {
						dialog.dismiss();
						// 销售退货过账 此接口不能使用需要从写
						// String localString =
						// serviceStore.str_CheckCTDoc(defdoccg, data,
						// listPayType, listItemDelete,
						// isprint);
						// System.out.println(localString);
						handler.sendMessage(handler.obtainMessage(1, han()));
					}
				});
			}
		});
	}

	// 属性
	public void docProperty() {
		Intent localIntent = new Intent();
		// localIntent.putExtra("typechangecustomer", k);
		localIntent.putExtra("doc", defdoccg);
		startActivityForResult(localIntent.setClass(this, OutpurchaseOpenActivity.class), 4);
	}

	// 删除
	public void delete() {
		if (defdoccg.getDocid() > 0L) {
			final Dialog_message dialog = new Dialog_message(this);
			dialog.show();
			dialog.setMessage("确定删除?");
			dialog.setCancelListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					PDH.show(OutpurchaseEditActivity.this, new ProgressCallBack() {

						@Override
						public void action() {
							String localString = serviceStore.str_DeleteCGDoc(defdoccg.getDoctypeid(),
									defdoccg.getDocid());
							System.out.println(localString);
							handler.sendMessage(handler.obtainMessage(2, "success"));
						}
					});

				}

			});
			return;
		}
		startActivity(new Intent(OutpurchaseEditActivity.this, SwyMain.class));
		finish();
	}

	// 支付 付款
	public void pay() {
		deleBm();
		Intent localIntent = new Intent(this, OutpurDocPayAct.class);
		localIntent.putExtra("discountsubtotal", discountsubtotal);
		localIntent.putExtra("preference", defdoccg.getPreference());
		localIntent.putExtra("listpaytype", JSONUtil.toJSONString(listPayType));
		startActivityForResult(localIntent, 5);
	}

	// 保存
	public void save() {
		String localString = validateDoc();
		if (localString != null) {
			InfoDialog.showError(this, localString);
			return;
		}
		String s = serviceStore.str_SaveTHDoc(defdoccg, adapter.getData(), listPayType, listItemDelete);
		// TODO 代码待补全
		System.out.println(s);
		handler.sendMessage(handler.obtainMessage(0, han()));
	}

}
