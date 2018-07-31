package com.ahjswy.cn.ui.ingoods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.dao.Sv_docitem;
import com.ahjswy.cn.model.DefDoc;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.model.DocContainerEntity;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.popupmenu.InDocEditMenuPopup;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.ui.SearchHelper;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.ui.ingoods.InDocItemAdapter.Sum;
import com.ahjswy.cn.ui.outgoods.OutDocPayAct;
import com.ahjswy.cn.ui.outgoods.SaleCustomerHistoryActivity;
import com.ahjswy.cn.utils.DocUtils;
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

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
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

//销售退货单
public class InDocEditActivity extends BaseActivity implements OnItemClickListener, OnTouchListener, Sum {
	private ServiceStore serviceStore;
	private LinearLayout linearSearch;
	// 添加
	private Button btnAdd;
	private SwipeMenuListView listView;
	private Button bt_sumNumber;
	private Button bt_totalSum;
	private boolean ishaschanged;
	private DefDoc doc;
	private List<DefDocPayType> listPayType;
	private List<Long> listItemDelete;
	private List<DefDocItemXS> listItem;
	private SearchHelper searchHelper;
	private AutoTextView atvSearch;
	InDocItemAdapter adapter;
	private InDocEditMenuPopup menuPopup;
	Scaner scaner;
	boolean isScanerBarcode = false;
	private Dialog_listCheckBox dialog;
	MAlertDialog maler;
	private Button btnGoodClass;
	private GoodsUnitDAO unitDao;
	// private AccountPreference ap;
	private DocContainerEntity docContainerEntity;
	private DocUtils mUtils;
	private Sv_docitem mSaveDocItemDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_indocedit_activity);
		intView();
		intDate();
		refreshUI();
		sum();
	}

	private void intView() {

		atvSearch = ((AutoTextView) findViewById(R.id.atvSearch));
		linearSearch = (LinearLayout) findViewById(R.id.linearSearch);
		btnGoodClass = (Button) findViewById(R.id.btn_goodClass);
		searchHelper = new SearchHelper(this, linearSearch);
		atvSearch.setOnItemClickListener(this.onItemClickListeners);
		// 添加按钮监听
		btnAdd = (Button) findViewById(R.id.btnAdd);
		// 底部 总数量
		bt_sumNumber = (Button) findViewById(R.id.bt_sumNumber);
		// 底部总价格
		bt_totalSum = (Button) findViewById(R.id.bt_totalSum);
		serviceStore = new ServiceStore();
		ishaschanged = getIntent().getBooleanExtra("ishaschanged", true);
		listView = (SwipeMenuListView) findViewById(R.id.listView_addShop);
		listView.setOnTouchListener(this);

		adapter = new InDocItemAdapter(this);
		adapter.setSum(this);
		btnAdd.setOnClickListener(addMoreListener);
		dialog = new Dialog_listCheckBox(InDocEditActivity.this);
		docContainerEntity = (DocContainerEntity) getIntent().getSerializableExtra("docContainer");
		this.doc = JSONUtil.fromJson(docContainerEntity.getDoc(), DefDoc.class);
		if (TextUtils.isEmpty(docContainerEntity.getItem())) {
			listItem = new ArrayList<>();
		} else {
			listItem = JSONUtil.parseArray(docContainerEntity.getItem(), DefDocItemXS.class);
		}
		listPayType = JSONUtil.parseArray(docContainerEntity.getPaytype(), DefDocPayType.class);
		if (listItem != null) {
			adapter.setData(listItem);
			listView.setAdapter(adapter);
		}
		maler = new MAlertDialog(this);
		unitDao = new GoodsUnitDAO();
		mUtils = new DocUtils();
		mSaveDocItemDB = new Sv_docitem();
	}

	private void intDate() {

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
					DefDocItemXS defdocitem = new DefDocItemXS(listItem.get(position));
					defdocitem.setItemid(0L);
					listItem.add(defdocitem);
					handler.postDelayed(new Runnable() {
						public void run() {
							adapter.setData(listItem);
							refreshUI();
							ishaschanged = true;
							setActionBarText();
							sum();
							saveTHDoc();
						}
					}, 180L);
					break;
				case 1:
					long l = listItem.get(position).getItemid();
					if (l > 0L) {
						listItemDelete.add(l);
					}
					listItem.remove(position);
					handler.postDelayed(new Runnable() {
						public void run() {
							adapter.setData(listItem);
							refreshUI();
							ishaschanged = true;
							setActionBarText();
							sum();
							saveTHDoc();
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

	ScanerBarcodeListener barcodeListener = new ScanerBarcodeListener() {

		@Override
		public void setBarcode(String barcode) {
			if (isScanerBarcode) {
				showError("数据处理中...");
				return;
			}
			if (doc.isIsavailable() && doc.isIsposted()) {
				return;
			}
			atvSearch.setText("");
			if (dialog != null) {
				dialog.dismiss();
			}
			if (listItem.size() > DocUtils.MAXITEM) {
				showError("商品已经开满！");
				return;
			}
			readBarcode(barcode);
		}
	};

	private void readBarcode(String barcode) {
		ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcode);
		final ArrayList<DefDocItemXS> localArrayList = new ArrayList<DefDocItemXS>();

		if (goodsThinList.size() == 1) {
			DefDocItemXS defdocitem = fillItem(goodsThinList.get(0), DocUtils.getDefaultNum(), 0.0D);
			localArrayList.add(defdocitem);
			Intent intent = new Intent().setClass(InDocEditActivity.this, InDocAddMoreGoodsAct.class);
			intent.putExtra("items", JSONUtil.object2Json(localArrayList));
			intent.putExtra("doc", doc);
			startActivityForResult(intent, 2);
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
						localArrayList.add(defdocitem);
					}
					if (localArrayList.size() == 0) {
						return;
					}
					Intent intent = new Intent(InDocEditActivity.this, InDocAddMoreGoodsAct.class);
					intent.putExtra("items", JSONUtil.object2Json(localArrayList));
					intent.putExtra("doc", doc);
					startActivityForResult(intent, 2);
				}
			});
		} else {
			PDH.showFail("没有查找到商品！可以尝试更新数据");
		}
	}

	OnItemClickListener onItemClickListeners = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if ((InDocEditActivity.this.menuPopup != null) && (InDocEditActivity.this.menuPopup.isShowing())) {
				InDocEditActivity.this.menuPopup.dismiss();
				WindowManager.LayoutParams localLayoutParams = InDocEditActivity.this.getWindow().getAttributes();
				localLayoutParams.alpha = 1.0F;
				InDocEditActivity.this.getWindow().setAttributes(localLayoutParams);
			}
			if (listItem.size() > DocUtils.MAXITEM) {
				showError("商品已经开满！");
				return;
			}
			final GoodsThin localGoodsThin = (GoodsThin) InDocEditActivity.this.searchHelper.getAdapter().getTempGoods()
					.get(position);
			atvSearch.setText("");
			PDH.show(InDocEditActivity.this, new ProgressCallBack() {

				@Override
				public void action() {
					DefDocItemXS docItem = fillItem(localGoodsThin, 0.0D, 0.0D);
					docItem.setPrice(DocUtils.getGoodsPrice(doc.getCustomerid(), docItem));
					setAddItem(docItem);
					Intent localIntent = new Intent(InDocEditActivity.this, InDocAddGoodAct.class);
					localIntent.putExtra("docitem", docItem);
					localIntent.putExtra("customerid", doc.getCustomerid());
					startActivityForResult(localIntent, 4);
				}
			});
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
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
				this.menuPopup = new InDocEditMenuPopup(this);
			}
			this.menuPopup.showAtLocation(listView, Gravity.BOTTOM, 0, 0);
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.alpha = 0.8F;
			getWindow().setAttributes(localLayoutParams);
			break;
		}
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		scaner.removeListener();
		scaner = null;
	}

	public DefDoc getDoc() {
		return doc;
	}

	public void refreshUI() {
		if (doc.isIsavailable() && doc.isIsposted()) {
			findViewById(R.id.linearSearch).setVisibility(View.GONE);
			findViewById(R.id.top).setVisibility(View.GONE);
			findViewById(R.id.linNumber).setVisibility(View.GONE);
			listView.setItemSwipe(false);
		} else {
			findViewById(R.id.linearSearch).setVisibility(View.VISIBLE);
			findViewById(R.id.top).setVisibility(View.VISIBLE);
			findViewById(R.id.linNumber).setVisibility(View.VISIBLE);
			listView.setItemSwipe(true);
			listView.setOnItemClickListener(this);
		}
	}

	private String validateDoc() {
		String message = null;
		if (TextUtils.isEmptyS(this.doc.getBuildtime())) {
			this.doc.setBuildtime(Utils.formatDate(Utils.getCurrentTime(false), "yyyy-MM-dd HH:mm:ss"));
		}
		if (TextUtils.isEmptyS(this.doc.getBuilderid())) {
			this.doc.setBuilderid(SystemState.getUser().getId());
		}
		if (!TextUtils.isEmptyS(doc.getCustomerid())) {
			return "没有选择客户!";
		}
		if (!TextUtils.isEmptyS(this.doc.getDepartmentid())) {
			return "部门不能为空!";
		}
		if ((this.doc.getDiscountratio() <= 0.0D) || (this.doc.getDiscountratio() > 1.0D)) {
			return "整单折扣必须大于0且小于等于1";
		}
		if (!TextUtils.isEmptyS(this.doc.getDeliverytime())) {
			return "交货日期不能为空";
		}
		if (!TextUtils.isEmptyS(this.doc.getSettletime())) {
			return "结算日期不能为空";
		}
		if (!TextUtils.isEmptyS(this.doc.getPrinttemplate())) {
			return "打印模板不能为空";
		}
		for (int k = 0; k < listItem.size(); k++) {
			DefDocItemXS localDefDocItem = (DefDocItemXS) this.listItem.get(k);
			if (localDefDocItem.getNum() == 0.0D) {
				message = "【" + localDefDocItem.getGoodsname() + "】数量为0";
			}
			if (!TextUtils.isEmptyS(localDefDocItem.getWarehouseid())) {
				message = "【" + localDefDocItem.getGoodsname() + "】没有选择仓库";
			}

			if (!TextUtils.isEmptyS(localDefDocItem.getUnitid())) {
				message = "【" + localDefDocItem.getGoodsname() + "】没有选择单位";
			}

			if (!(TextUtils.isEmptyS(localDefDocItem.getBatch())) && (localDefDocItem.isIsusebatch())) {
				message = "【" + localDefDocItem.getGoodsname() + "】没有选择批次";
			}

		}
		return message;
	}

	View.OnClickListener addMoreListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			List<GoodsThin> localList = searchHelper.getAdapter().getSelect();
			if ((localList == null) || (localList.size() == 0)) {
				PDH.showMessage("请选择商品！");
				return;
			}
			atvSearch.setText("");
			ArrayList<DefDocItemXS> localArrayList = new ArrayList<DefDocItemXS>();
			for (int i = 0; i < localList.size(); i++) {
				GoodsThin localGoodsThin = (GoodsThin) localList.get(i);
				localArrayList.add(fillItem(localGoodsThin, DocUtils.getDefaultNum(), 0.0D));
			}
			startActivityForResult(new Intent().setClass(InDocEditActivity.this, InDocAddMoreGoodsAct.class)
					.putExtra("items", JSONUtil.object2Json(localArrayList)).putExtra("doc", doc), 2);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(barcodeListener);
	}

	// 过账
	public void check(final boolean paramBoolean) {
		String localString = validateDoc();
		if (localString != null) {
			InfoDialog.showError(this, localString);
			return;
		}
		if ((this.listItem == null) || (this.listItem.size() == 0)) {
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
				PDH.show(InDocEditActivity.this, new PDH.ProgressCallBack() {

					@Override
					public void action() {
						dialog.dismiss();
						String localString = serviceStore.str_CheckXTDoc(doc, listItem, listPayType, listItemDelete,
								paramBoolean);
						handler.sendMessage(handler.obtainMessage(1, localString));
					}
				});
			}
		});
	}

	// 客史
	public void getCustomerHistory() {
		if (TextUtils.isEmptyS(doc.getCustomerid())) {
			Intent localIntent = new Intent();
			localIntent.putExtra("customerid", this.doc.getCustomerid());
			localIntent.putExtra("customername", this.doc.getCustomername());
			startActivityForResult(localIntent.setClass(this, SaleCustomerHistoryActivity.class), 7);
			return;
		}
		PDH.showMessage("单据未指定客户");
	}

	// 属性
	public void docProperty() {
		// deleBm();
		Intent localIntent = new Intent(InDocEditActivity.this, InDocOpenActivity.class);
		localIntent.putExtra("doc", doc);
		startActivityForResult(localIntent, 0);
	}

	// 付款
	public void pay() {
		boolean isreadonly = false;
		Intent localIntent = new Intent();
		if ((!this.doc.isIsavailable()) || (!this.doc.isIsposted())) {
			isreadonly = true;
		} else {
			isreadonly = false;
		}
		// 获取折扣合计的总金额
		localIntent.putExtra("discountsubtotal", sum);
		// 判断是否是 销售退货
		localIntent.putExtra("isreceive", false);
		localIntent.putExtra("isreadonly", isreadonly);
		localIntent.putExtra("preference", this.doc.getPreference());
		localIntent.putExtra("listpaytype", JSONUtil.object2Json(this.listPayType));
		startActivityForResult(localIntent.setClass(this, OutDocPayAct.class), 8);
	}

	// 删除
	public void delete() {
		if (doc.getDocid() > 0L) {
			final Dialog_message dialog = new Dialog_message(this);
			dialog.show();
			dialog.setMessage("确定删除?");
			dialog.setCancelListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					PDH.show(InDocEditActivity.this, new ProgressCallBack() {
						@Override
						public void action() {
							String localString = serviceStore.str_DeleteDoc(doc.getDoctypeid(), doc.getDocid());
							handler.sendMessage(handler.obtainMessage(2, localString));
						}
					});
				}
			});
			return;
		}
		Intent intent = new Intent(InDocEditActivity.this, SwyMain.class);
		startActivity(intent);
		finish();
	}

	// 保存
	public void save() {
		String localString = validateDoc();
		if (localString != null) {
			InfoDialog.showError(this, localString);
			return;
		}
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				String localString = serviceStore.str_SaveXTDoc(doc, adapter.getData(), listPayType, listItemDelete);
				handler.sendMessage(handler.obtainMessage(0, localString));
			}
		});
	}

	double sum = 0.0D;

	// sum 总价
	public void sum() {
		List<DefDocItemXS> data = adapter.getData();
		listItemDelete = adapter.getListItemDelete();
		bt_sumNumber.setText("数量:" + data.size() + "个");
		double sumMoney = 0D;
		int sumNum = 0;
		Map<String, Object> map = new HashMap<>();
		for (DefDocItemXS defDocItemXS : data) {
			sumMoney += defDocItemXS.getDiscountsubtotal();
			sumNum += defDocItemXS.getNum();
			map.put(defDocItemXS.getGoodsid(), defDocItemXS);
		}
		sum = Utils.normalizePrice(sumMoney);
		btnGoodClass.setText("品种:" + map.size());
		bt_sumNumber.setText("数量:" + sumNum);
		bt_totalSum.setText("总价:\n" + Utils.normalizePrice(sumMoney) + "元");

	}

	private void intenToMain() {
		if (ishaschanged) {
			maler.show();
			maler.setMessage("是否保存当前单据?");
			maler.setNeutralButton(new MAlertDialog.OnClickListener() {

				@Override
				public void onClick(MAlertDialog dialog) {
					dialog.dismiss();
					String localString = validateDoc();
					if (localString != null) {
						InfoDialog.showError(InDocEditActivity.this, localString);
						return;
					}
					PDH.show(InDocEditActivity.this, new ProgressCallBack() {

						@Override
						public void action() {
							final String localString = serviceStore.str_SaveXTDoc(doc, listItem, listPayType,
									listItemDelete);
							handler.post(new Runnable() {
								public void run() {
									if (RequestHelper.isSuccess(localString)) {
										PDH.showSuccess("保存成功");
										Intent intent = new Intent(InDocEditActivity.this, SwyMain.class);
										startActivity(intent);
										finish();
										// DocUtils.deleteTHDoc();
										mSaveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
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
					// DocUtils.deleteTHDoc();
					dialog.dismiss();
					Intent intent = new Intent(InDocEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();
					mSaveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
				}
			});
			return;
		}
		setResult(RESULT_FIRST_USER);
		finish();

	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			String localString = msg.obj.toString();
			if (RequestHelper.isSuccess(localString)) {
				if (msg.what == 2) {
					PDH.showMessage("删除成功");
					Intent intent = new Intent(InDocEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();
					// DocUtils.deleteTHDoc();
					mSaveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
					return;
				}
				DocContainerEntity entity = (DocContainerEntity) JSONUtil.fromJson(localString,
						DocContainerEntity.class);
				doc = ((DefDoc) JSONUtil.fromJson(entity.getDoc(), DefDoc.class));
				listItem = (List<DefDocItemXS>) JSONUtil.parseArray(entity.getItem(), DefDocItemXS.class);
				listPayType = JSONUtil.parseArray(entity.getPaytype(), DefDocPayType.class);
				listItemDelete = new ArrayList<Long>();
				switch (msg.what) {
				case 0:
					adapter.setData(listItem);
					refreshUI();
					PDH.showSuccess("保存成功");
					ishaschanged = false;
					setActionBarText();
					// DocUtils.deleteTHDoc();
					mSaveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
					startActivity(new Intent(InDocEditActivity.this, SwyMain.class));
					return;
				case 1:
					if ((doc.isIsavailable()) && (doc.isIsposted())) {
						PDH.showSuccess("过账成功");
						// DocUtils.deleteTHDoc();
						mSaveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
						Intent intent = new Intent(InDocEditActivity.this, SwyMain.class);
						startActivity(intent);
						return;
					}
					adapter.setData(listItem);
					refreshUI();
					InfoDialog.showError(InDocEditActivity.this, entity.getInfo());
					return;
				}
			}
			InfoDialog.showError(InDocEditActivity.this, localString);
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent localIntent = new Intent();
		localIntent.putExtra("customerid", doc.getCustomerid());
		localIntent.putExtra("position", position);
		localIntent.putExtra("docitem", (Serializable) listItem.get(position));
		startActivityForResult(localIntent.setClass(InDocEditActivity.this, InDocAddGoodAct.class), 3);
	}

	private DefDocItemXS fillItem(GoodsThin goodsThin, double num, double price) {
		DefDocItemXS localDefDocItem = new DefDocItemXS();
		localDefDocItem.setItemid(0L);
		localDefDocItem.setDocid(this.doc.getDocid());
		localDefDocItem.setGoodsid(goodsThin.getId());
		localDefDocItem.setGoodsname(goodsThin.getName());
		localDefDocItem.setBarcode(goodsThin.getBarcode());
		localDefDocItem.setSpecification(goodsThin.getSpecification());
		localDefDocItem.setModel(goodsThin.getModel());
		localDefDocItem.setWarehouseid(this.doc.getWarehouseid());
		localDefDocItem.setWarehousename(this.doc.getWarehousename());
		GoodsUnit localGoodsUnit = null;
		if (Utils.DEFAULT_OutDocUNIT == 0) {
			localGoodsUnit = unitDao.queryBaseUnit(goodsThin.getId());
		} else {
			localGoodsUnit = unitDao.queryBigUnit(goodsThin.getId());
		}
		localDefDocItem.setUnitid(localGoodsUnit.getUnitid());
		localDefDocItem.setUnitname(localGoodsUnit.getUnitname());
		localDefDocItem.setNum(Utils.normalize(num, 2));
		localDefDocItem.setBignum(
				unitDao.getBigNum(localDefDocItem.getGoodsid(), localDefDocItem.getUnitid(), localDefDocItem.getNum()));
		localDefDocItem.setPrice(Utils.normalizePrice(price));
		localDefDocItem.setSubtotal(Utils.normalizeSubtotal(localDefDocItem.getNum() * localDefDocItem.getPrice()));
		localDefDocItem.setDiscountratio(this.doc.getDiscountratio());
		localDefDocItem
				.setDiscountprice(Utils.normalizePrice(localDefDocItem.getPrice() * this.doc.getDiscountratio()));
		localDefDocItem.setDiscountsubtotal(
				Utils.normalizeSubtotal(localDefDocItem.getNum() * localDefDocItem.getDiscountprice()));
		localDefDocItem.setIsgift(localDefDocItem.getPrice() == 0.0D ? true : false);
		localDefDocItem.setCostprice(0.0D);
		localDefDocItem.setRemark("");
		localDefDocItem.setRversion(0L);
		localDefDocItem.setIsdiscount(false);
		localDefDocItem.setIsusebatch(goodsThin.isIsusebatch());
		return localDefDocItem;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				doc = (DefDoc) data.getSerializableExtra("doc");
				ishaschanged = true;
				setActionBarText();
				saveTHDoc();
				break;
			case 1:
				doc = (DefDoc) data.getSerializableExtra("doc");
				saveTHDoc();
				break;
			case 2:
				isScanerBarcode = true;
				// 修改 为本地查询客史价格
				PDH.show(this, "数据查询中...", new ProgressCallBack() {

					@Override
					public void action() {
						try {
							ArrayList<DefDocItemXS> newListItem = (ArrayList<DefDocItemXS>) JSONUtil
									.parseArray(data.getStringExtra("items"), DefDocItemXS.class);
							for (DefDocItemXS itemXS : newListItem) {
								itemXS.setPrice(DocUtils.getGoodsPrice(doc.getCustomerid(), itemXS));
								setAddItem(itemXS);
							}
							if (Utils.isCombination()) {
								int size = listItem.size();
								listItem.addAll(newListItem);
								mUtils.combinationItem(listItem, listItemDelete);
								if (listItem.size() < size) {
									showSuccess("同品增加成功!");
								}
							} else {
								listItem.addAll(0, newListItem);
							}
							handlerItem.sendEmptyMessage(0);
						} catch (Exception e) {
							e.printStackTrace();
							DocUtils.insertLog(e);
						}
					}
				});
				break;
			case 3:
				int j = data.getIntExtra("position", 0);
				DefDocItemXS localDefDocItem2 = (DefDocItemXS) data.getSerializableExtra("docitem");
				listItem.set(j, localDefDocItem2);
				if (Utils.isCombination()) {
					int size = listItem.size();
					mUtils.combinationItem(listItem, listItemDelete);
					showSuccess("同品增加成功!");
					if (listItem.size() < size) {
						showSuccess("同品增加成功!");
					}
				}
				adapter.setData(listItem);
				refreshUI();
				saveTHDoc();
				break;
			case 4:
				DefDocItemXS localDefDocItem4 = (DefDocItemXS) data.getSerializableExtra("docitem");
				listItem.add(localDefDocItem4);
				refreshUI();
				if (Utils.isCombination()) {
					mUtils.combinationItem(listItem, listItemDelete);
					showSuccess("同品增加成功!");
				}
				adapter.setData(listItem);
				listView.setAdapter(adapter);
				saveTHDoc();
				break;

			case 7:
				List<DefDocItemXS> itemXSList = JSONUtil.parseArray(data.getStringExtra("selecteditem"),
						DefDocItemXS.class);
				for (int i = 0; i < itemXSList.size(); i++) {
					DefDocItemXS item = new DefDocItemXS(itemXSList.get(i));
					item.setDocid(this.doc.getDocid());
					item.setItemid(0);
					listItem.add(item);
				}
				if (Utils.isCombination()) {
					mUtils.combinationItem(listItem, listItemDelete);
					showSuccess("同品增加成功!");
				}
				this.adapter.setData(this.listItem);
				listView.setAdapter(adapter);
				refreshUI();
				sum();
				saveTHDoc();
				break;
			case 8:
				// 支付
				String localString1 = data.getStringExtra("listpaytype");
				if (TextUtils.isEmptyS(localString1)) {
					this.listPayType = JSONUtil.parseArray(localString1, DefDocPayType.class);
					double d = data.getDoubleExtra("preference", 0.0D);
					if (d != this.doc.getPreference()) {
						this.doc.setPreference(d);
					}
				}
				saveTHDoc();
				break;
			}
		}

	}

	Handler handlerItem = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter.setData(listItem);
				adapter.notifyDataSetChanged();
				ishaschanged = true;
				setActionBarText();
				refreshUI();
				saveTHDoc();
				isScanerBarcode = false;
				break;

			default:
				break;
			}
		};
	};

	protected void setAddItem(DefDocItemXS docItem) {
		// 折后小计
		docItem.setSubtotal(Utils.normalizeSubtotal(docItem.getNum() * docItem.getPrice()));
		// 重点查看是否有错误!
		docItem.setDiscountratio(doc.getDiscountratio());
		docItem.setIsgift(docItem.getPrice() == 0.0D ? true : false);
		docItem.setDiscountprice(Utils.normalizePrice(docItem.getPrice() * docItem.getDiscountratio()));
		docItem.setDiscountsubtotal(Utils.normalizeSubtotal(docItem.getNum() * docItem.getDiscountprice()));
		String bigNum = unitDao.getBigNum(docItem.getGoodsid(), docItem.getUnitid(), docItem.getNum());
		docItem.setBignum(bigNum);
	}


	protected void saveTHDoc() {
		try {
			DocContainerEntity docEntity = new DocContainerEntity();
			// 保存到本地
			docEntity.setDeleteinitem(docContainerEntity.getDeleteinitem());
			docEntity.setDeleteitem(JSONUtil.object2Json(listItemDelete));
			docEntity.setDoc(JSONUtil.object2Json(doc));
			docEntity.setItem(JSONUtil.toJSONString(listItem));
			docEntity.setDoctype(docContainerEntity.getDoctype());
			docEntity.setPaytype(JSONUtil.object2Json(listPayType));
			if (mSaveDocItemDB.queryDoc(docContainerEntity.getDoctype()) == null) {
				mSaveDocItemDB.insetDocItem(docEntity);
			} else {
				mSaveDocItemDB.updataDocItem(docEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showError("网络链接不稳定!");
			DocUtils.insertLog(e);
		}

	}

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
	public void setActionBarText() {
		String showId = TextUtils.isEmpty(doc.getShowid()) ? "销售退货单" : doc.getShowid();
		if (this.ishaschanged) {
			getActionBar().setTitle(showId + "*");
		} else {
			getActionBar().setTitle(showId);
		}
	}
}
