package com.ahjswy.cn.ui.outgoods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.bean.Def_DocDraft;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.Sv_docitem;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.model.DefDocXS;
import com.ahjswy.cn.model.DocContainerEntity;
import com.ahjswy.cn.model.FieldSaleForPrint;
import com.ahjswy.cn.model.FieldSaleItemForPrint;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.popupmenu.OutDocEditMenuPopup;
import com.ahjswy.cn.print.BTPrintHelper;
import com.ahjswy.cn.print.BTPrintHelper.PrintOverCall;
import com.ahjswy.cn.print.PrintMode;
import com.ahjswy.cn.request.ReqCustomerDingDoc;
import com.ahjswy.cn.request.ReqCustomerdebt;
import com.ahjswy.cn.response.DingDocitem;
import com.ahjswy.cn.response.RespServiceInfor;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.service.ServiceCustomer;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.ui.SearchHelper;
import com.ahjswy.cn.ui.SwyMain;
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
import android.widget.TextView;

public class OutDocEditActivity extends BaseActivity implements OnItemClickListener, OnTouchListener {
	// private ServiceStore serviceStore;
	private List<DefDocItemXS> listItem;
	private List<DefDocPayType> listPayType;
	private boolean ishaschanged;
	// 添加
	private Button btnAdd;
	public DefDocXS doc;
	private SwipeMenuListView listview_copy_dele;
	private List<Long> listItemDelete;
	private OutDocItemAdapter adapter;
	private LinearLayout linearSearch;
	private SearchHelper searchHelper;
	private AutoTextView atvSearch;
	private OutDocEditMenuPopup menuPopup;
	private DocContainerEntity docContainerEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_out_doc_edit_activity);
		intView();
		intDate();
		refreshUI();
		// bottomCount();
	}

	private void intView() {

		// 输入匹配 listview
		linearSearch = (LinearLayout) findViewById(R.id.linearSearch);
		searchHelper = new SearchHelper(this, linearSearch);
		atvSearch = ((AutoTextView) findViewById(R.id.atvSearch));
		atvSearch.setOnItemClickListener(onItemClickListeners);
		// 加载 头 布局
		// 加载查找 添加
		btnAdd = (Button) findViewById(R.id.btnAdd);
		listview_copy_dele = (SwipeMenuListView) findViewById(R.id.listView_addShop);
		listview_copy_dele.setOnTouchListener(this);
		// 底部 总数量
		bt_sumNumber = (Button) findViewById(R.id.bt_sumNumber);
		// 底部总价格
		bt_totalSum = (Button) findViewById(R.id.bt_totalSum);
		btnGoodClass = (Button) findViewById(R.id.btn_goodClass);
		ishaschanged = getIntent().getBooleanExtra("ishaschanged", true);
		docContainerEntity = (DocContainerEntity) getIntent().getSerializableExtra("docContainer");
		doc = ((DefDocXS) JSONUtil.fromJson(docContainerEntity.getDoc(), DefDocXS.class));
		if (TextUtils.isEmpty(docContainerEntity.getItem())) {
			listItem = new ArrayList<>();
		} else {
			listItem = JSONUtil.parseArray(docContainerEntity.getItem(), DefDocItemXS.class);
		}
		listPayType = JSONUtil.parseArray(docContainerEntity.getPaytype(), DefDocPayType.class);
		adapter = new OutDocItemAdapter(this);
		adapter.setData(listItem);
		listview_copy_dele.setAdapter(adapter);
		listItemDelete = new ArrayList<Long>();
		btnAdd.setOnClickListener(onAddGoodsClick);
		listview_copy_dele.setOnItemClickListener(this);
		dialog = new Dialog_listCheckBox(this);
		tv_Customer = (TextView) findViewById(R.id.tv_Customer);
		if (doc.isIsavailable() && !doc.isIsposted()) {
			queryCustomerdebt();
		}
		saveDocItemDB = new Sv_docitem();
		// serviceStore = new ServiceStore();
		utils = new DocUtils();
	}

	/**
	 * 本地 sql 写入 与 更新
	 */
	public void saveXSDoc() {
		DocContainerEntity docEntity = null;
		try {
			docEntity = new DocContainerEntity();
			// 保存到本地
			docEntity.setDeleteinitem(docContainerEntity.getDeleteinitem());
			docEntity.setDeleteitem(JSONUtil.object2Json(listItemDelete));
			docEntity.setDoc(JSONUtil.object2Json(doc));
			docEntity.setItem(JSONUtil.toJSONString(listItem));
			docEntity.setDoctype(docContainerEntity.getDoctype());
			docEntity.setPaytype(JSONUtil.object2Json(listPayType));
			if (saveDocItemDB.queryDoc(docContainerEntity.getDoctype()) == null) {
				saveDocItemDB.insetDocItem(docEntity);
			} else {
				saveDocItemDB.updataDocItem(docEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showError("存储失败!");
			DocUtils.insertLog(e, docEntity.toString());
		}

	}

	/**
	 * 查询客户欠款
	 */
	private void queryCustomerdebt() {
		if (TextUtils.isEmptyS(doc.getCustomerid())) {
			// 查询客户 欠款
			String queryCustomerDebt = new ServiceCustomer()
					.cu_queryCustomer(new ReqCustomerdebt(doc.getCustomerid(), true, 0));
			if (RequestHelper.isSuccess(queryCustomerDebt)) {
				ReqCustomerdebt reqCustomerdebt = JSONUtil.fromJson(queryCustomerDebt, ReqCustomerdebt.class);
				tv_Customer.setVisibility(View.VISIBLE);
				tv_Customer.setText("当前结存: " + reqCustomerdebt.amount + " 元");
			} else {
				tv_Customer.setText("当前结存: ? 元");
			}
		}
	}

	private void intDate() {
		SwipeMenuCreator local5 = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
				item1.setTitle("复制");
				item1.setTitleSize(14);
				item1.setTitleColor(-16777216);
				item1.setWidth(100);
				item1.setBackground(new ColorDrawable(Color.rgb(48, 177, 245)));
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
				item2.setTitle("删除");
				item2.setTitleSize(14);
				item2.setTitleColor(-16777216);
				item2.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
				item2.setWidth(100);
				menu.addMenuItem(item2);
			}
		};
		listview_copy_dele.setMenuCreator(local5);
		listview_copy_dele.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				if (listItem.size() == 0) {
					return false;
				}
				switch (index) {
				case 0:
					DefDocItemXS defdocitemxs = new DefDocItemXS(listItem.get(position));
					defdocitemxs.setItemid(0L);
					defdocitemxs.setTempitemid(1L + getMaxTempItemId());
					listItem.add(defdocitemxs);
					adapter.setData(listItem);
					handler.postDelayed(new Runnable() {
						public void run() {
							adapter.notifyDataSetChanged();
							refreshUI();
							ishaschanged = true;
							setActionBarText();
							// bottomCount();
							saveXSDoc();
						}
					}, 180L);
					break;
				case 1:
					// 删除
					DefDocItemXS itemdel = listItem.get(position);
					if (itemdel.getItemid() > 0L) {
						listItemDelete.add(Long.valueOf(itemdel.getItemid()));
					}
					listItem.remove(position);
					adapter.setData(listItem);
					handler.postDelayed(new Runnable() {
						public void run() {
							adapter.notifyDataSetChanged();
							refreshUI();
							ishaschanged = true;
							setActionBarText();
							// bottomCount();
							saveXSDoc();
						}
					}, 180L);
					break;
				}
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(barcodeListener);
	}

	ScanerBarcodeListener barcodeListener = new ScanerBarcodeListener() {

		@Override
		public void setBarcode(String barcode) {
			if (isScanerBarcode) {
				showError("数据处理中....");
				return;
			}
			// 已经过账的单据 不允许继续扫描开单
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
	boolean isScanerBarcode = false;

	private void readBarcode(String barcode) {
		ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcode);
		final ArrayList<DefDocItemXS> localArrayList = new ArrayList<DefDocItemXS>();
		if (goodsThinList.size() == 1) {
			long l = getMaxTempItemId();
			DefDocItemXS fillItem = utils.fillItem(doc, goodsThinList.get(0), DocUtils.getDefaultNum(), 0.0D, l + 1L);
			localArrayList.add(fillItem);
			Intent intent = new Intent(OutDocEditActivity.this, OutDocAddMoreGoodsAct.class);
			intent.putExtra("items", JSONUtil.object2Json(localArrayList));
			intent.putExtra("doc", doc);
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
					long l = getMaxTempItemId();
					for (int i = 0; i < select.size(); i++) {
						l += 1L;
						DefDocItemXS fillItem = utils.fillItem(doc, select.get(i), DocUtils.getDefaultNum(), 0.0D, l);
						localArrayList.add(fillItem);
					}
					if (localArrayList.isEmpty()) {
						return;
					}
					Intent intent = new Intent(OutDocEditActivity.this, OutDocAddMoreGoodsAct.class);
					intent.putExtra("items", JSONUtil.object2Json(localArrayList));
					intent.putExtra("doc", doc);
					startActivityForResult(intent, 1);
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
	}

	// 获取最大的 TempItemId
	public long getMaxTempItemId() {
		long l1 = 0L;
		if (listItem.size() <= 0) {
			return l1;
		}
		for (int i = 0; i < listItem.size(); i++) {
			long l2 = listItem.get(i).getTempitemid();
			if (l2 > l1) {
				l1 = l2;
			}
		}
		return l1;
	}

	View.OnClickListener onAddGoodsClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			atvSearch.clearFocus();
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
			ArrayList<DefDocItemXS> localArrayList = new ArrayList<DefDocItemXS>();
			long l = getMaxTempItemId();
			for (int i = 0; i < localList.size(); i++) {
				GoodsThin localGoodsThin = (GoodsThin) localList.get(i);
				l += 1L;
				DefDocItemXS item = utils.fillItem(doc, localGoodsThin, DocUtils.getDefaultNum(), 0.0D, l);
				if (item == null) {
					PDH.showMessage("商品有误!");
					return;
				} else {
					localArrayList.add(item);
				}
			}
			Intent intent = new Intent(OutDocEditActivity.this, OutDocAddMoreGoodsAct.class);
			intent.putExtra("items", JSONUtil.object2Json(localArrayList));
			intent.putExtra("doc", doc);
			startActivityForResult(intent, 1);
		}
	};

	public void refreshUI() {
		if (doc.isIsavailable() && doc.isIsposted()) {
			findViewById(R.id.linearSearch).setVisibility(View.GONE);
			findViewById(R.id.top).setVisibility(View.GONE);
			findViewById(R.id.linNumber).setVisibility(View.GONE);
			listview_copy_dele.setItemSwipe(false);
		} else {
			findViewById(R.id.linearSearch).setVisibility(View.VISIBLE);
			findViewById(R.id.top).setVisibility(View.VISIBLE);
			// findViewById(R.id.linNumber).setVisibility(View.VISIBLE);
			listview_copy_dele.setItemSwipe(true);
		}
	}

	/**
	 * main ontouch item
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		DefDocItemXS defdocitemxs = (DefDocItemXS) parent.getItemAtPosition(position);
		atvSearch.setText("");
		if ((doc.isIsavailable()) && (doc.isIsposted())) {
			return;
		}

		Intent localIntent = new Intent();
		localIntent.putExtra("customerid", doc.getCustomerid());
		localIntent.putExtra("positiongive", -1);
		localIntent.putExtra("docitemgive", "");
		localIntent.putExtra("position", position);
		localIntent.putExtra("docitem", defdocitemxs);
		// 商品详情
		startActivityForResult(localIntent.setClass(this, OutDocAddGoodAct.class), 2);
	}

	private AdapterView.OnItemClickListener onItemClickListeners = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if ((menuPopup != null) && (menuPopup.isShowing())) {
				menuPopup.dismiss();
				WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
				localLayoutParams.alpha = 1.0F;
				getWindow().setAttributes(localLayoutParams);
			}
			if (listItem.size() > DocUtils.MAXITEM) {
				showError("商品已经开满！");
				return;
			}

			final GoodsThin localGoodsThin = searchHelper.getAdapter().getTempGoods().get(position);
			atvSearch.setText("");
			PDH.show(OutDocEditActivity.this, new PDH.ProgressCallBack() {

				public void action() {
					try {

						long l = getMaxTempItemId();
						DefDocItemXS docItem = utils.fillItem(doc, localGoodsThin, DocUtils.getDefaultNum(), 0.0D,
								l + 1L);
						if (docItem == null) {
							return;
						}
						docItem.setPrice(DocUtils.getGoodsPrice(doc.getCustomerid(), docItem));
						setAddItem(docItem);
						Intent localIntent = new Intent(OutDocEditActivity.this, OutDocAddGoodAct.class);
						localIntent.putExtra("customerid", doc.getCustomerid());
						localIntent.putExtra("position", 0);
						localIntent.putExtra("docitem", docItem);
						startActivityForResult(localIntent, 4);
					} catch (Exception e) {
						e.printStackTrace();
						DocUtils.insertLog(e);
					}
				}

			});

		}

	};

	private void setAddItem(DefDocItemXS docItem) {
		// 折后小计
		docItem.setSubtotal(Utils.normalizeSubtotal(docItem.getNum() * docItem.getPrice()));
		// 折扣
		docItem.setDiscountratio(doc.getDiscountratio());
		// 是否赠送
		docItem.setIsgift(docItem.getPrice() == 0.0D);
		docItem.setDiscountprice(Utils.normalizePrice(docItem.getPrice() * docItem.getDiscountratio()));
		docItem.setDiscountsubtotal(Utils.normalizeSubtotal(docItem.getNum() * docItem.getDiscountprice()));
		String bigNum = DocUtils.getBigNum(docItem.getGoodsid(), docItem.getUnitid(), docItem.getNum());
		docItem.setBignum(bigNum);
	}

	// 过账
	public void check(final boolean isprint) {
		String localString = validateDoc();
		if (localString != null) {
			InfoDialog.showError(this, localString);
			return;
		}
		listItem = adapter.getData();
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
				PDH.show(OutDocEditActivity.this, new PDH.ProgressCallBack() {

					@Override
					public void action() {
						dialog.dismiss();
						String localString = new ServiceStore().str_CheckXSDoc(doc, listItem, listPayType,
								listItemDelete, isprint);
						handler.sendMessage(handler.obtainMessage(1, localString));
					}
				});
			}
		});
	}

	// 收款
	public void pay() {
		boolean isreadonly = false;
		Intent localIntent = new Intent();
		if ((!this.doc.isIsavailable()) || (!this.doc.isIsposted())) {
			isreadonly = true;
		} else {
			isreadonly = false;
		}
		double discountsubtotal = 0.0d;
		for (DefDocItemXS item : listItem) {
			discountsubtotal += item.getDiscountsubtotal();
		}
		localIntent.putExtra("discountsubtotal", Utils.normalizeSubtotal(discountsubtotal));
		localIntent.putExtra("isreadonly", isreadonly);
		localIntent.putExtra("preference", this.doc.getPreference());
		localIntent.putExtra("customerid", this.doc.getCustomerid());
		localIntent.putExtra("listpaytype", JSONUtil.object2Json(this.listPayType));
		startActivityForResult(localIntent.setClass(this, OutDocPayAct.class), 8);
	}

	public void getCustomerHistory() {
		if (TextUtils.isEmptyS(this.doc.getCustomerid())) {
			Intent localIntent = new Intent();
			localIntent.putExtra("customerid", doc.getCustomerid());
			localIntent.putExtra("customername", doc.getCustomername());
			startActivityForResult(localIntent.setClass(this, SaleCustomerHistoryActivity.class), 7);
			return;
		}
		PDH.showMessage("单据未指定客户");
	}

	private boolean check;

	public void save(boolean check) {
		if (doc.isIsposted()) {
			return;
		}
		this.check = check;
		String localString = validateDoc();
		if (localString != null) {
			InfoDialog.showError(this, localString);
			return;
		}
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				String localString = new ServiceStore().str_SaveXSDoc(doc, adapter.getData(), listPayType,
						listItemDelete);
				handler.sendMessage(handler.obtainMessage(0, localString));
			}
		});
	}

	// 蓝牙过账/打印
	public void blueDevicePrint() {
		if (doc.isIsposted()) {
			bluePrint();
		} else {
			check(false);
		}
	}

	private void bluePrint() {
		// bottomCount();
		BTPrintHelper btprint = new BTPrintHelper(OutDocEditActivity.this);
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

	public List<FieldSaleItemForPrint> getDocPrintData() {
		List<FieldSaleItemForPrint> datainfo = new ArrayList<FieldSaleItemForPrint>();
		for (int i = 0; i < listItem.size(); i++) {
			DefDocItemXS item = listItem.get(i);
			FieldSaleItemForPrint itemForPrint = new FieldSaleItemForPrint();
			itemForPrint.setBarcode(item.getBarcode());
			itemForPrint.setDiscountratio(item.getDiscountprice());
			itemForPrint.setDiscountsubtotal(item.getDiscountsubtotal());
			itemForPrint.setGoodsid(item.getGoodsid());
			itemForPrint.setGoodsname(item.getGoodsname());
			itemForPrint.setItemtype(i + 1 + "");
			itemForPrint.setNum(item.getNum());
			itemForPrint.setPrice(item.getPrice());
			itemForPrint.setRemark(item.getRemark());
			itemForPrint.setSpecification(item.getSpecification());
			itemForPrint.setUnitname(item.getUnitname());
			datainfo.add(itemForPrint);
		}
		return datainfo;
	}

	public FieldSaleForPrint getDocPrintInfo() {

		double discountsubtotal = 0.0d;
		for (DefDocItemXS item : listItem) {
			discountsubtotal += item.getDiscountsubtotal();
		}
		FieldSaleForPrint printItem = new FieldSaleForPrint();
		// v2.setId(doc.getId());
		printItem.setDoctype("销售单");
		printItem.setShowid(doc.getShowid());
		printItem.setCustomername(this.doc.getCustomername());
		printItem.setDepartmentname(this.doc.getDepartmentname());
		printItem.setBuildername(this.doc.getBuildername());
		printItem.setBuildtime(Utils.formatDate(this.doc.getBuildtime(), "yyyy-MM-dd HH:mm:ss"));
		printItem.setSumamount("合计:" + getSumMoney());
		printItem.setPreference("优惠:" + this.doc.getPreference());
		printItem.setReceivable("应收:" + Utils.getSubtotalMoney(discountsubtotal - doc.getPreference()));
		printItem.setReceived("已收:" + Utils.getRecvableMoney(received));
		printItem.setNum("数量:" + String.valueOf(0));
		printItem.setRemark(doc.getRemark());
		return printItem;
	}

	private RespServiceInfor printDoc() {
		Def_DocDraft draft = new Def_DocDraft();
		draft.setDepartmentid(doc.getDepartmentid());
		draft.setDepartmentname(doc.getDepartmentname());
		draft.setShowid(doc.getShowid());
		draft.setDoctypeid("13");// 单据类型， 12销售订单
		draft.setId(doc.getDocid());// 单据id
		final String printdoc = new ServiceStore().PrintDoc(draft);
		if (!TextUtils.isEmpty(printdoc)) {
			return JSONUtil.readValue3(printdoc);
		}
		return null;
	}

	// 删除
	public void delete() {
		if (this.doc.getDocid() > 0L) {
			final Dialog_message dialog = new Dialog_message(this);
			dialog.show();
			dialog.setMessage("确定删除?");
			dialog.setCancelListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					PDH.show(OutDocEditActivity.this, new ProgressCallBack() {

						@Override
						public void action() {
							String localString = new ServiceStore().str_DeleteDoc(doc.getDoctypeid(), doc.getDocid());
							handler.sendMessage(handler.obtainMessage(2, localString));
						}
					});

				}

			});
			return;
		}
		saveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
		startActivity(new Intent(OutDocEditActivity.this, SwyMain.class));
		finish();

	}

	// 属性
	public void docProperty() {
		Intent localIntent = new Intent(this, OutDocOpenActivity.class);
		localIntent.putExtra("doc", doc);
		startActivityForResult(localIntent, 0);
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
			return "整单折扣必须大于0且小于等于1!";
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
			DefDocItemXS localDefDocItemXS = listItem.get(k);
			if (localDefDocItemXS.getNum() == 0.0D) {
				message = "【" + localDefDocItemXS.getGoodsname() + "】数量为0";
			}
			if (!TextUtils.isEmptyS(localDefDocItemXS.getWarehouseid())) {
				message = "【" + localDefDocItemXS.getGoodsname() + "】没有选择仓库";
			}

			if (!TextUtils.isEmptyS(localDefDocItemXS.getUnitid())) {
				message = "【" + localDefDocItemXS.getGoodsname() + "】没有选择单位";
			}

			if (!(TextUtils.isEmptyS(localDefDocItemXS.getBatch())) && (localDefDocItemXS.isIsusebatch())) {
				message = "【" + localDefDocItemXS.getGoodsname() + "】没有选择批次";
			}

		}
		return message;
	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			String localString = msg.obj.toString();
			if (RequestHelper.isSuccess(localString)) {
				if (msg.what == 2) {
					PDH.showMessage("删除成功");
					Intent intent = new Intent(OutDocEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();
					return;
				}
				DocContainerEntity dce = (DocContainerEntity) JSONUtil.fromJson(localString, DocContainerEntity.class);
				doc = JSONUtil.fromJson(dce.getDoc(), DefDocXS.class);
				listPayType = JSONUtil.parseArray(dce.getPaytype(), DefDocPayType.class);
				listItem = JSONUtil.parseArray(dce.getItem(), DefDocItemXS.class);
				listItemDelete = new ArrayList<Long>();
				switch (msg.what) {
				case 0:
					adapter.setData(listItem);
					PDH.showSuccess("保存成功");
					ishaschanged = false;
					setActionBarText();
					adapter.notifyDataSetChanged();
					if (check) {
						// 打印单据
						printDocHandler.sendEmptyMessage(0);
					} else {
						saveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
						startActivity(new Intent(OutDocEditActivity.this, SwyMain.class));
					}
					return;
				case 1:
					if ((doc.isIsavailable()) && (doc.isIsposted())) {
						PDH.showSuccess("过账成功");
						if (DocUtils.isBluetoothPrint()) {
							bluePrint();
							startActivity(new Intent(OutDocEditActivity.this, SwyMain.class));
						} else {
							startActivity(new Intent(OutDocEditActivity.this, SwyMain.class));
							finish();
						}
						new Sv_docitem().deleteDoc(docContainerEntity.getDoctype());
						return;
					}
					adapter.setData(listItem);
					refreshUI();
					RequestHelper.showError(dce.getInfo());
					return;
				default:
					break;
				}
			} else {
				RequestHelper.showError(localString);
			}
		}
	};
	private Handler printDocHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			RespServiceInfor printDoc = printDoc();
			if (printDoc != null && printDoc.Result) {
				// 打印单据
				showSuccess("打印成功!");
				startActivity(new Intent(OutDocEditActivity.this, SwyMain.class));
			} else {
				showError("打印失败!服务请开启！");
			}
		};
	};

	// 保存打印
	public void printServiceDoc() {
		RespServiceInfor printDoc = printDoc();
		if (printDoc != null && printDoc.Result) {// 打印单据
			showSuccess("打印成功!");
		} else {
			showError("打印失败!服务请开启！");
		}
	}

	private double sumMoney = 0D;

	/**
	 * 计算底部 品种 数量 总价
	 */
	private void bottomCount() {
		// sumMoney = 0D;
		// int sumNum = 0;
		// ArrayMap<String, Object> maps = new ArrayMap<>();
		// // Map<String, Object> map = new HashMap<>();
		// for (DefDocItemXS itemXS : listItem) {
		// sumMoney += itemXS.getDiscountsubtotal();
		// sumNum += itemXS.getNum();
		// // map.put(itemXS.getGoodsid(), itemXS);
		// maps.put(itemXS.getGoodsid(), itemXS);
		// }
		// btnGoodClass.setText("品种:" + maps.size());
		// bt_sumNumber.setText("数量:" + sumNum);
		// bt_totalSum.setText("总价:\n" + Utils.normalizePrice(sumMoney) + "元");
		// maps.clear();
	}

	/**
	 * 当前总金额
	 * 
	 * @return
	 */
	public double getSumMoney() {
		return sumMoney;
	}

	private Button bt_sumNumber;
	private Button bt_totalSum;
	private Dialog_listCheckBox dialog;
	private Button btnGoodClass;
	private double preference;// 优惠
	private double received;// 已收
	private Scaner scaner;
	private TextView tv_Customer;
	// private Sv_docitem sv;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				doc = (DefDocXS) data.getSerializableExtra("doc");
				ishaschanged = true;
				setActionBarText();
				// bottomCount();
				queryCustomerdebt();
				saveXSDoc();
				break;
			case 1:
				isScanerBarcode = true;
				PDH.show(this, "数据查询中...", new ProgressCallBack() {

					@Override
					public void action() {
						List<DefDocItemXS> newListItem = JSONUtil.parseArray(data.getStringExtra("items"),
								DefDocItemXS.class);
						for (int i = 0; i < newListItem.size(); i++) {
							DefDocItemXS itemXS = newListItem.get(i);
							setAddItem(itemXS);
						}
						if (Utils.isCombination()) {
							listItem.addAll(newListItem);
							int size = listItem.size();
							utils.combinationItem(listItem, listItemDelete);
							if (listItem.size() < size) {
								showSuccess("同品增加成功!");
							}
						} else {
							listItem.addAll(0, newListItem);
						}
						handlerItem.sendEmptyMessage(0);
					}
				});

				break;
			case 2:
				int j = data.getIntExtra("position", 0);
				DefDocItemXS localDefDocItemXS3 = (DefDocItemXS) data.getSerializableExtra("docitem");
				listItem.set(j, localDefDocItemXS3);
				if (Utils.isCombination()) {
					int size = listItem.size();
					utils.combinationItem(listItem, listItemDelete);
					if (listItem.size() < size) {
						showSuccess("同品增加成功!");
					}
				}
				adapter.setData(listItem);
				adapter.notifyDataSetChanged();
				ishaschanged = true;
				// bottomCount();
				refreshUI();
				saveXSDoc();

				break;
			case 3:
				doc = (DefDocXS) data.getSerializableExtra("doc");
				saveXSDoc();
				break;
			case 4:
				// TODO
				DefDocItemXS defDocItemXS4 = (DefDocItemXS) data.getSerializableExtra("docitem");
				// defDocItemXS4.assistnum =
				// DocUtils.getAssistnum(defDocItemXS4.getGoodsid(),
				// defDocItemXS4.getUnitid(),
				// defDocItemXS4.getNum());
				if (Utils.isCombination()) {
					listItem.add(defDocItemXS4);
					int size = listItem.size();
					utils.combinationItem(listItem, listItemDelete);
					if (listItem.size() < size) {
						showSuccess("同品增加成功!");
					}
				} else {
					listItem.add(0, defDocItemXS4);
				}
				adapter.setData(listItem);
				listview_copy_dele.setAdapter(adapter);
				ishaschanged = true;
				refreshUI();
				bottomCount();
				saveXSDoc();
				break;
			case 7:
				List<DefDocItemXS> itemXSList = JSONUtil.parseArray(data.getStringExtra("selecteditem"),
						DefDocItemXS.class);
				long itemId = this.getMaxTempItemId();
				for (int i = 0; i < itemXSList.size(); i++) {
					DefDocItemXS item = new DefDocItemXS(itemXSList.get(i));
					item.setDocid(this.doc.getDocid());
					item.setItemid(0);
					item.setTempitemid(itemId);
					listItem.add(item);
				}
				this.refreshUI();
				bottomCount();
				if (Utils.isCombination()) {
					int size = listItem.size();
					utils.combinationItem(listItem, listItemDelete);
					if (listItem.size() < size) {
						showSuccess("同品增加成功!");
					}
				}
				this.adapter.setData(this.listItem);
				listview_copy_dele.setAdapter(adapter);
				saveXSDoc();
				break;
			case 8:
				// 收款
				String localString1 = data.getStringExtra("listpaytype");
				if (TextUtils.isEmptyS(localString1)) {
					listPayType = JSONUtil.parseArray(localString1, DefDocPayType.class);
				}
				preference = data.getDoubleExtra("preference", 0.0D);
				received = data.getDoubleExtra("received", 0.0D);
				if (preference != this.doc.getPreference()) {
					this.doc.setPreference(preference);
				}
				saveXSDoc();
				break;
			case 300:
				final long docid = data.getLongExtra("docid", 0);
				final String doctype = data.getStringExtra("doctype");
				PDH.show(this, new ProgressCallBack() {

					@Override
					public void action() {
						ReqCustomerDingDoc dingDoc = new ReqCustomerDingDoc();
						dingDoc.setDocid(docid);
						dingDoc.setDoctype(doctype);
						String str = new ServiceCustomer().cu_queryCustomerDingDocitem(dingDoc);
						if (RequestHelper.isSuccess(str)) {
							// TODO 获取数据 解析 进行拼接
							List<DingDocitem> listGoods = JSONUtil.parseArray(str, DingDocitem.class);
							for (DingDocitem item : listGoods) {
								DefDocItemXS itemXS = getDefDocItemXS(item);
								listItem.add(itemXS);
							}
							dinghandler.sendEmptyMessage(0);
						}

					}

				});

				break;
			}
		}
	}

	Handler dinghandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter.setData(listItem);
			adapter.notifyDataSetChanged();
		};

	};

	private DefDocItemXS getDefDocItemXS(DingDocitem item) {
		DefDocItemXS itemxs = new DefDocItemXS();
		itemxs.setBarcode(item.getBarcode());
		itemxs.setGoodsid(item.getGoodsid());
		itemxs.setGoodsname(item.getGoodsname());
		itemxs.setSpecification(item.getSpecification());
		itemxs.setModel(item.getModel());
		itemxs.setUnitid(item.getUnitid());
		itemxs.setUnitname(item.getUnitname());
		itemxs.setNum(item.getNum()-item.getArrivalnum());
		itemxs.setDiscountratio(item.getDiscountratio());
		itemxs.setRemark(item.getRemark());
		itemxs.setBignum(item.getBignum());
		itemxs.setPrice(item.getPrice());
		itemxs.setSubtotal(itemxs.getNum()*item.getPrice());
		itemxs.setDiscountsubtotal(itemxs.getNum()*itemxs.getDiscountratio()*item.getDiscountprice());
		itemxs.setDocid(item.getDocid());
		itemxs.setItemid(item.getItemid());
		itemxs.setIsgift(item.getIsgift());
		itemxs.setRversion(item.getRversion());
		itemxs.setIsdiscount(item.getIsdiscount());
		itemxs.setDiscountprice(item.getDiscountprice());
		itemxs.setWarehouseid(doc.getWarehouseid());
		itemxs.setWarehousename(doc.getWarehousename());
		itemxs.setOutorderdocid(item.getDocid());
		itemxs.setOutorderitemid(item.getItemid());
		return itemxs;
	}

	Handler handlerItem = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter.setData(listItem);
				adapter.notifyDataSetChanged();
				ishaschanged = true;
				setActionBarText();
				bottomCount();
				refreshUI();
				saveXSDoc();
				isScanerBarcode = false;
				break;

			default:
				break;
			}
		};
	};
	private DocUtils utils;
	private Sv_docitem saveDocItemDB;

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
						InfoDialog.showError(OutDocEditActivity.this, localString);
						return;
					}
					PDH.show(OutDocEditActivity.this, new ProgressCallBack() {

						@Override
						public void action() {
							final String localString = new ServiceStore().str_SaveXSDoc(doc, listItem, listPayType,
									listItemDelete);
							handler.post(new Runnable() {
								public void run() {
									if (RequestHelper.isSuccess(localString)) {
										PDH.showSuccess("保存成功");
										saveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
										Intent intent = new Intent(OutDocEditActivity.this, SwyMain.class);
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
					dialog.dismiss();
					saveDocItemDB.deleteDoc(docContainerEntity.getDoctype());
					Intent intent = new Intent(OutDocEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();

				}
			});
			maler.show();
			return;
		}
		Intent intent = new Intent(OutDocEditActivity.this, SwyMain.class);
		startActivity(intent);
		finish();

	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			if (ishaschanged) {
				intenToMain();
			} else {
				setResult(RESULT_FIRST_USER);
				finish();
			}
			break;
		case 0:
			if (this.menuPopup == null) {
				this.menuPopup = new OutDocEditMenuPopup(this, doc);
			}
			this.menuPopup.showAtLocation(listview_copy_dele, Gravity.BOTTOM, 0, 0);
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.alpha = 0.8F;
			getWindow().setAttributes(localLayoutParams);
			break;
		}
		return true;
	}

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

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setIcon(getResources().getDrawable(R.drawable.btn_submenu)).setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	@Override
	public void setActionBarText() {
		String showid = TextUtils.isEmpty(doc.getShowid()) ? "销售开单" : doc.getShowid();
		if (ishaschanged) {
			getActionBar().setTitle(showid + "*");
		} else {
			getActionBar().setTitle(showid);
		}
	}

}
