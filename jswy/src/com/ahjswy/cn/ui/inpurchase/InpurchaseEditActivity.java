package com.ahjswy.cn.ui.inpurchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.bean.Def_DocDraft;
import com.ahjswy.cn.bean.PurchaseEntity;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemCG;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.popupmenu.InpurchaseEditMenuPopup;
import com.ahjswy.cn.request.ReqCustomerDingDoc;
import com.ahjswy.cn.response.DingDocCGitem;
import com.ahjswy.cn.response.DingDocitem;
import com.ahjswy.cn.response.RespServiceInfor;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.service.ServiceCustomer;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.ui.QueryDocListActivity;
import com.ahjswy.cn.ui.SearchHelper;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.ui.inpurchase.InpurDocItemAdapter.Sum;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.InfoDialog;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.MLog;
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

/*
 * 采购单开单
 */
public class InpurchaseEditActivity extends BaseActivity implements OnTouchListener, OnItemClickListener, Sum {
	private SearchHelper searchHelper;
	private AutoTextView atvSearch;
	private InpurchaseEditMenuPopup menuPopup;
	private boolean ishaschanged;
	private List<DefDocPayType> listPayType;
	private SwipeMenuListView listview_copy_dele;
	private InpurDocItemAdapter adapter;
	private List<DefDocItemCG> listItem;
	// ==================================
	private Button btnAdd;
	Def_Doc doccg;
	private GoodsUnitDAO mGoodsUnitDAO;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_inpurchase_edit);
		initView();
		initDate();
		sum();
	}

	private void initView() {
		LinearLayout linearSearch = (LinearLayout) findViewById(R.id.linearSearch);
		searchHelper = new SearchHelper(this, linearSearch);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		atvSearch = ((AutoTextView) findViewById(R.id.atvSearch));
		serviceStore = new ServiceStore();
		bt_sumNumber = (Button) findViewById(R.id.bt_sumNumber);
		bt_totalSum = (Button) findViewById(R.id.bt_totalSum);
		btnGoodClass = (Button) findViewById(R.id.btn_goodClass);
		listview_copy_dele = (SwipeMenuListView) findViewById(R.id.listView_addShop);
		listItem = new ArrayList<DefDocItemCG>();
		btnAdd.setOnClickListener(addMoreListener);
		listview_copy_dele.setOnItemClickListener(this);
		listview_copy_dele.setOnTouchListener(this);
		atvSearch.setOnItemClickListener(onItemClickListeners);
	}

	private void initDate() {
		// 初始化 表示单据需要保存
		ishaschanged = getIntent().getBooleanExtra("ishaschanged", true);
		PurchaseEntity entity = (PurchaseEntity) getIntent().getSerializableExtra("entity");
		doccg = ((Def_Doc) JSONUtil.readValue(entity.getDocjson(), Def_Doc.class));
		listPayType = JSONUtil.str2list(entity.getTypelistjson(), DefDocPayType.class);
		mGoodsUnitDAO = new GoodsUnitDAO();
		adapter = new InpurDocItemAdapter(this);
		listview_copy_dele.setAdapter(adapter);
		adapter.setSum(this);
		dialog = new Dialog_listCheckBox(InpurchaseEditActivity.this);

		SwipeMenuCreator local5 = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem localSwipeMenuItem1 = new SwipeMenuItem(
						InpurchaseEditActivity.this.getApplicationContext());
				localSwipeMenuItem1.setTitle("复制");
				localSwipeMenuItem1.setTitleSize(14);
				localSwipeMenuItem1.setTitleColor(-16777216);
				localSwipeMenuItem1.setWidth(100);
				localSwipeMenuItem1.setBackground(new ColorDrawable(Color.rgb(48, 177, 245)));
				menu.addMenuItem(localSwipeMenuItem1);
				SwipeMenuItem localSwipeMenuItem2 = new SwipeMenuItem(
						InpurchaseEditActivity.this.getApplicationContext());
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
					final DefDocItemCG defdocitemcg = new DefDocItemCG(adapter.getItem(position));
					defdocitemcg.setItemid(0L);
					defdocitemcg.setTempitemid(1L + getMaxTempItemId());
					mHandler.postDelayed(new Runnable() {
						public void run() {
							adapter.addItem(defdocitemcg);
							ishaschanged = true;
							setActionBarText();
						}
					}, 180L);
					break;
				case 1:
					DefDocItemCG defdocitemcg1 = listItem.get(position);
					if (defdocitemcg1.getItemid() > 0L) {
						listItemDelete.add(Long.valueOf(defdocitemcg1.getItemid()));
					}
					listItem.remove(position);
					mHandler.postDelayed(new Runnable() {
						public void run() {
							adapter.setData(listItem);
							ishaschanged = true;
							setActionBarText();
							sum();
						}
					}, 180L);
					break;
				}
				return false;
			}
		});
	}

	private Dialog_listCheckBox dialog;

	private void readBarcode(String barcodeStr) {

		// 调用 getBarcode()方法读取条码信息
		ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcodeStr);
		localArrayList = new ArrayList<DefDocItemCG>();
		if (goodsThinList.size() == 1) {
			long maxTempItemId = getMaxTempItemId();
			DefDocItemCG fillItem = fillItem(goodsThinList.get(0), DocUtils.getDefaultNum(), 0.0D, maxTempItemId + 1L);
			localArrayList.add(fillItem);
			Intent intent = new Intent(InpurchaseEditActivity.this, InpurDocAddMoreGoodsAct.class);
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
					long maxTempItemId = getMaxTempItemId();
					for (int i = 0; i < select.size(); i++) {
						DefDocItemCG fillItem = fillItem(select.get(i), DocUtils.getDefaultNum(), 0.0D,
								maxTempItemId += 1L);
						localArrayList.add(fillItem);
					}
					Intent intent = new Intent(InpurchaseEditActivity.this, InpurDocAddMoreGoodsAct.class);
					intent.putExtra("items", JSONUtil.toJSONString(localArrayList));
					intent.putExtra("doc", doccg);
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
		scaner = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(new ScanerBarcodeListener() {

			@Override
			public void setBarcode(String barcode) {
				atvSearch.setText("");
				readBarcode(barcode);
			}
		});
	}

	protected void combinationItem() {
		int combinationNum = listItem.size();
		ArrayList<DefDocItemCG> data = new ArrayList<DefDocItemCG>(listItem);
		ArrayList<DefDocItemCG> listDocItem = new ArrayList<DefDocItemCG>();
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		for (int i = 0; i < data.size(); i++) {
			DefDocItemCG items1 = data.get(i);
			if (map.get(items1.getGoodsid()) == null) {
				map.put(items1.getGoodsid(), new DefDocItemCG(items1));
				continue;
			}
			DefDocItemCG itemxs2 = (DefDocItemCG) map.get(items1.getGoodsid());
			if (items1.getGoodsid().equals(itemxs2.getGoodsid()) && items1.getUnitid().equals(itemxs2.getUnitid())
					&& items1.getPrice() == itemxs2.getPrice()
					&& items1.getDiscountratio() == itemxs2.getDiscountratio()
					&& items1.getWarehouseid().equals(itemxs2.getWarehouseid())) {
				itemxs2.setNum(itemxs2.getNum() + items1.getNum());
				itemxs2.setBignum(mGoodsUnitDAO.getBigNum(itemxs2.getGoodsid(), itemxs2.getUnitid(), itemxs2.getNum()));
				itemxs2.setSubtotal(itemxs2.getNum() * itemxs2.getPrice());
				itemxs2.setDiscountsubtotal(itemxs2.getNum() * itemxs2.getPrice() * itemxs2.getDiscountratio());
				map.put(itemxs2.getGoodsid(), itemxs2);
				if (items1.getItemid() != 0) {
					listItemDelete.add(items1.getItemid());
				}
			} else {
				listDocItem.add(items1);
			}

		}
		Set<String> keySet = map.keySet();
		for (String string : keySet) {
			listDocItem.add((DefDocItemCG) map.get(string));
		}
		if ((combinationNum - listDocItem.size()) == 0) {
			// 没有要合并的商品!
			return;
		}
		showSuccess("同品增加成功!");
		// 设置数据
		listItem.clear();
		listItem.addAll(listDocItem);
		adapter.setData(listItem);
	}

	private AdapterView.OnItemClickListener onItemClickListeners = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			onTouch(null, null);
			final GoodsThin localGoodsThin = searchHelper.getAdapter().getTempGoods().get(position);
			atvSearch.setText("");
			PDH.show(InpurchaseEditActivity.this, new PDH.ProgressCallBack() {

				public void action() {
					long l = getMaxTempItemId();
					final DefDocItemCG onitemdefdoc = fillItem(localGoodsThin, 0.0D, 0.0D, l + 1L);
					if (!TextUtils.isEmpty(doccg.getCustomerid())) {
						if (setItemsGoods(onitemdefdoc)) {
							showError("商品单价获取失败！");
							return;
						}
					}
					Intent localIntent = new Intent(InpurchaseEditActivity.this, InPurchaseDocAddGoodAct.class);
					localIntent.putExtra("customerid", doccg.getCustomerid());
					localIntent.putExtra("docitem", onitemdefdoc);
					localIntent.putExtra("doc", doccg);
					startActivityForResult(localIntent, 4);
				}
			});

		}

	};

	/**
	 * 获取每个商品的 价格并设置给当前商品
	 * 
	 * @param defdocitemcg
	 */
	private boolean setItemsGoods(DefDocItemCG defdocitemcg) {
		List<DefDocItemCG> itemCGs = new ArrayList<DefDocItemCG>();
		itemCGs.add(defdocitemcg);
		String goodsPrice = new ServiceStore().GetGoodsPrice(doccg, JSONUtil.toJSONString(itemCGs));
		if (TextUtils.isEmptyS(goodsPrice)) {
			RespServiceInfor infor = JSONUtil.readValue3(goodsPrice);
			double price = Double.parseDouble(TextUtils.isEmptyS(infor.Json.Data) == true ? infor.Json.Data : "0");
			defdocitemcg.setIsgift(price == 0 ? true : false);
			defdocitemcg.setPrice(price);// 单价
			defdocitemcg.setDiscountprice(Utils.normalize(price * defdocitemcg.getDiscountratio(), 3));// 折后单价
			defdocitemcg.setDiscountsubtotal(
					Utils.normalize(price * defdocitemcg.getDiscountratio() * defdocitemcg.getNum(), 3));// 折后总价
			String bigNum = mGoodsUnitDAO.getBigNum(defdocitemcg.getGoodsid(), defdocitemcg.getUnitid(),
					defdocitemcg.getNum());
			defdocitemcg.setBignum(bigNum);
			defdocitemcg.setSubtotal(defdocitemcg.getNum() * price);// 小计
		} else {
			defdocitemcg.setPrice(0);// 单价
			defdocitemcg.setIsgift(true);
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		// 添加图片
		paramMenu.add(0, 0, 0, "单击显示菜单").setIcon(getResources().getDrawable(R.drawable.btn_submenu)).setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			intenToMain();
			break;
		case 0:
			if (this.menuPopup == null) {
				menuPopup = new InpurchaseEditMenuPopup(this);
			}
			this.menuPopup.showAtLocation(listview_copy_dele, Gravity.BOTTOM, 0, 0);
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.alpha = 0.8F;
			getWindow().setAttributes(localLayoutParams);
			break;
		}

		return true;
	}

	public View.OnClickListener addMoreListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			onTouch(null, null);
			List<GoodsThin> localList = searchHelper.getAdapter().getSelect();
			if ((localList == null) || (localList.size() == 0)) {
				return;
			}
			atvSearch.setText("");
			ArrayList<DefDocItemCG> localArrayList = new ArrayList<DefDocItemCG>();
			for (int i = 0; i < localList.size(); i++) {
				GoodsThin localGoodsThin = (GoodsThin) localList.get(i);
				DefDocItemCG fillItem = fillItem(localGoodsThin, DocUtils.getDefaultNum(), 0.0D, 0);
				if (fillItem == null) {
					continue;
				}
				localArrayList.add(fillItem);
			}
			Intent intent = new Intent(InpurchaseEditActivity.this, InpurDocAddMoreGoodsAct.class);
			intent.putExtra("items", JSONUtil.toJSONString(localArrayList));
			intent.putExtra("doc", doccg);
			startActivityForResult(intent, 1);
		}
	};

	private DefDocItemCG fillItem(GoodsThin localGoodsThin, double paramDouble1, double paramDouble2, long paramLong) {
		DefDocItemCG defDocItemCG = new DefDocItemCG();
		defDocItemCG.setGoodsid(localGoodsThin.getId());
		defDocItemCG.setGoodsname(localGoodsThin.getName());
		defDocItemCG.setBarcode(localGoodsThin.getBarcode());
		defDocItemCG.setSpecification(localGoodsThin.getSpecification());
		defDocItemCG.setWarehouseid(doccg.getWarehouseid());
		defDocItemCG.setWarehousename(doccg.getWarehousename());
		defDocItemCG.setItemid(0L);
		defDocItemCG.setTempitemid(paramLong);
		defDocItemCG.setDiscountratio(doccg.getDiscountratio());
		GoodsUnit localGoodsUnit = null;
		if (Utils.DEFAULT_OutDocUNIT == 0) {
			localGoodsUnit = mGoodsUnitDAO.queryBaseUnit(localGoodsThin.getId());
		} else {
			localGoodsUnit = mGoodsUnitDAO.queryBigUnit(localGoodsThin.getId());
		}
		if (localGoodsUnit == null) {
			return null;
		}
		defDocItemCG.setUnitid(localGoodsUnit.getUnitid());
		defDocItemCG.setUnitname(localGoodsUnit.getUnitname());
		defDocItemCG.setNum(Utils.normalize(paramDouble1, 2));
		defDocItemCG.setBignum(
				mGoodsUnitDAO.getBigNum(defDocItemCG.getGoodsid(), defDocItemCG.getUnitid(), defDocItemCG.getNum()));

		// 价格
		defDocItemCG.setPrice(Utils.normalizePrice(paramDouble2));
		// 小计
		defDocItemCG.setSubtotal(Utils.normalizeSubtotal(defDocItemCG.getNum() * defDocItemCG.getPrice()));
		// 折扣率
		defDocItemCG.setDiscountratio(doccg.getDiscountratio());
		// 折扣价格
		defDocItemCG.setDiscountprice(Utils.normalizePrice(defDocItemCG.getPrice() * doccg.getDiscountratio()));
		// 折后小计
		defDocItemCG.setDiscountsubtotal(defDocItemCG.getNum() * defDocItemCG.getDiscountprice());
		if (defDocItemCG.getPrice() == 0.0D) {
			defDocItemCG.setIsgift(true);
		}
		// 总金额
		// defDocItemCG.setCostprice(0.0D);
		defDocItemCG.setIsusebatch(localGoodsThin.isIsusebatch());

		return defDocItemCG;
	}

	/**
	 * 返回保存单据
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
						InfoDialog.showError(InpurchaseEditActivity.this, localString);
						return;
					}
					save();
				}
			});
			maler.setNegativeButton(new MAlertDialog.OnClickListener() {

				@Override
				public void onClick(MAlertDialog dialog) {
					Intent intent = new Intent(InpurchaseEditActivity.this, SwyMain.class);
					startActivity(intent);
					finish();
				}
			});
			maler.show();
			return;
		}
		Intent intent = new Intent(InpurchaseEditActivity.this, SwyMain.class);
		startActivity(intent);
		finish();

	}

	// 过账打印
	public void check(final boolean checkPrint) {
		String localString = validateDoc();
		if (localString != null) {
			InfoDialog.showError(this, localString);
			return;
		}
		final List<DefDocItemCG> data = adapter.getData();
		if ((data == null) || (data.size() == 0)) {
			InfoDialog.showError(this, "空单不能过账");
			return;
		}
		// 判断商品是否结清
		if (TextUtils.isEmpty(doccg.getCustomerid()) && (discountsubtotal - preference - received > 0)) {
			PDH.showMessage("商品还没有结清！");
			return;
		}
		final Dialog_message dialog = new Dialog_message(this);
		dialog.show();
		dialog.setTitle("提示");
		dialog.setMessage("过账后的单据不能修改。\n确定过账？");
		dialog.setCancelListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PDH.show(InpurchaseEditActivity.this, new PDH.ProgressCallBack() {

					@Override
					public void action() {
						dialog.dismiss();
						// 是否是草稿箱 true 过账 false 是草稿箱
						doccg.setIsposted(true);//
						doccg.setIsdraft(false);
						doccg.setIssettleup(true);
						int sumMoney = 0;
						for (int i = 0; i < listItem.size(); i++) {
							// 全部计件单位转换成基本单位
							DefDocItemCG defDocItemCG = listItem.get(i);
							sumMoney += defDocItemCG.getDiscountsubtotal();
						}
						doccg.setMoney(sumMoney - preference);// 计算应收金额
						String purchaseInfo = serviceStore.savePurchase(doccg, listPayType, listItem);
						if (TextUtils.isEmpty(purchaseInfo)) {
							showError("请重新尝试!");
							return;
						}
						RespServiceInfor infor = JSONUtil.readValue3(purchaseInfo);
						showSuccess(infor.Info);
						if (!checkPrint && infor.Result) {// 是否是过账单，是否过账成功，都成功在跳转
							Intent intent = new Intent(InpurchaseEditActivity.this, SwyMain.class);
							startActivity(intent);
							finish();
							return;
						}
						if (infor.Result && checkPrint) {// 过账成功！必须是需要打印，
							if (isprintDoc(doccg, infor).Result) {
								Intent intent = new Intent(InpurchaseEditActivity.this, SwyMain.class);
								startActivity(intent);
								finish();
							}
						}
					}
				});
			}
		});
	}

	// 打印单据

	private RespServiceInfor isprintDoc(Def_Doc doc, RespServiceInfor infro) {
		Def_DocDraft draft = new Def_DocDraft();
		draft.setBuildtime(doc.getBuildtime());
		draft.setMaketime(doc.getBuildtime());
		draft.setDepartmentid(doc.getDepartmentid());
		draft.setDepartmentname(doc.getDepartmentname());
		draft.setShowid(infro.Json.Data);
		draft.setDoctypeid(doc.getDoctypeid());// 单据类型，03采购单
		draft.setId(Long.parseLong(infro.Json.DocID));// 单据id
		String printdoc = serviceStore.PrintDoc(draft);
		if (TextUtils.isEmpty(printdoc)) {
			return null;
		}
		return JSONUtil.readValue3(printdoc);
	}

	// 属性
	public void docProperty() {
		Intent localIntent = new Intent();
		localIntent.putExtra("doc", doccg);
		startActivityForResult(localIntent.setClass(this, InpurchaseOpenActivity.class), 0);
	}

	public void delete() {
		if (!TextUtils.isEmpty(doccg.getDocid() + "")) {
			final Dialog_message dialog = new Dialog_message(this);
			dialog.show();
			dialog.setMessage("确定删除?");
			dialog.setCancelListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					PDH.show(InpurchaseEditActivity.this, new ProgressCallBack() {

						@Override
						public void action() {
							String purchaseInfo = serviceStore.DelDraft(doccg.getMakerid(), doccg.getDocid(), "03");
							if (TextUtils.isEmptyS(purchaseInfo)) {
								JSONUtil.readValue2(purchaseInfo);
								if (JSONUtil.Result) {
									showSuccess("删除单据成功!");
									// 删除成功返回主页面
									Intent intent = new Intent(InpurchaseEditActivity.this, SwyMain.class);
									startActivity(intent);
									finish();
								} else {
									showError(JSONUtil.Info);
								}
							} else {
								showError("失败!重新尝试!");
							}
						}
					});
				}
			});
			return;
		}
		startActivity(new Intent(InpurchaseEditActivity.this, SwyMain.class));
		finish();
	}

	double discountsubtotal;// 总金额

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

	/*
	 * 付款方式
	 */
	public void pay() {
		Intent localIntent = new Intent(this, InpurDocPayAct.class);
		localIntent.putExtra("discountsubtotal", discountsubtotal);// 折后合计
		localIntent.putExtra("listpaytype", JSONUtil.toJSONString(listPayType));// 支付类型集合
		localIntent.putExtra("preference", doccg.getPreference());// .getPreference()
		localIntent.putExtra("customerid", doccg.getCustomerid());
		startActivityForResult(localIntent, 5);
	}

	/**
	 * 保存单据
	 */
	public void save() {
		String localString = validateDoc();
		if (localString != null) {
			InfoDialog.showError(this, localString);
			return;
		}
		if (listItem.size() == 0) {
			PDH.showFail("没有商品!请添加商品后保存!");
			return;
		}
		PDH.show(this, "保存中", new ProgressCallBack() {

			@Override
			public void action() {
				doccg.setIsposted(false);
				doccg.setIsdraft(true);
				doccg.setIssettleup(false);
				doccg.setMoney(discountsubtotal);
				String purchaseInfo = serviceStore.savePurchase(doccg, listPayType, listItem);
				if (TextUtils.isEmpty(purchaseInfo)) {
					showError("失败!没有从服务器获取返回信息！请重试 !");
					return;
				}
				RespServiceInfor infor = JSONUtil.readValue3(purchaseInfo);
				if (infor.Result) {
					showSuccess(infor.Json.Desc);
					startActivity(new Intent(InpurchaseEditActivity.this, SwyMain.class));
					finish();
				} else {
					showError(infor.Info);
				}
				// JSONUtil.readValue2(purchaseInfo);
				// if (JSONUtil.Result && (doccg.getDocid() == 0)) {
				// showSuccess(JSONUtil.Desc);
				// doccg.setDocid(JSONUtil.DocID);// 保存单据id
				// doccg.setShowid(JSONUtil.Data);// 保存单据号
				// } else if (JSONUtil.Result && (doccg.getDocid() != 0)) {
				// showSuccess(JSONUtil.Desc);
				// } else {
				// showSuccess(JSONUtil.Info);
				// }
			}
		});
	}

	public void dingDoc() {
		if (TextUtils.isEmpty(doccg.getCustomerid())) {
			PDH.showFail("请选择供应商!");
			return;
		}
		// 查询客户
		Intent intent = new Intent(this, QueryDocListActivity.class);
		intent.putExtra("doctype", QueryDocListActivity.TYPE_OUT_PURCHASE);
		intent.putExtra("customerid", doccg.getCustomerid());
		startActivityForResult(intent, 300);
	}

	// private List<DefDocItemCG> newListItem;

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				doccg = (Def_Doc) data.getSerializableExtra("doc");
				ishaschanged = true;
				setActionBarText();
				break;
			case 1:
				final List<DefDocItemCG> newListItem = JSONUtil.str2list(data.getStringExtra("items"),
						DefDocItemCG.class);
				if (newListItem != null && 0 < newListItem.size()) {
					PDH.show(InpurchaseEditActivity.this, new ProgressCallBack() {

						@Override
						public void action() {
							for (int i = 0; i < newListItem.size(); i++) {
								setItemsGoods(newListItem.get(i));
								// setBigNum(newListItem.get(i));
							}
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									if (Utils.isCombination()) {
										listItem.addAll(newListItem);
										combinationItem();
									} else {
										listItem.addAll(0, newListItem);
									}
									adapter.setData(listItem);
									listview_copy_dele.setAdapter(adapter);
								}
							});
						}
					});

				}
				ishaschanged = true;
				setActionBarText();
				break;
			case 2:
				int j = data.getIntExtra("position", 0);
				DefDocItemCG defdocitemCG3 = (DefDocItemCG) data.getSerializableExtra("docitem");
				adapter.getData().set(j, defdocitemCG3);
				listview_copy_dele.setAdapter(adapter);
				if (Utils.isCombination()) {
					combinationItem();
				}
				ishaschanged = true;
				break;
			case 4:
				DefDocItemCG docitem = (DefDocItemCG) data.getExtras().get("docitem");
				if (Utils.isCombination()) {
					listItem.add(docitem);
					combinationItem();
				} else {
					listItem.add(0, docitem);
				}
				adapter.setData(listItem);
				listview_copy_dele.setAdapter(adapter);
				ishaschanged = true;
				setActionBarText();
				break;
			case 5:
				// 收款方式
				listPayType = (List<DefDocPayType>) data.getExtras().getSerializable("listpaytype");
				preference = data.getDoubleExtra("preference", 0.0D);
				received = data.getDoubleExtra("received", 0.0D);// 已收金额
				receiveable = data.getDoubleExtra("receiveable", 0.0D);
				doccg.setPreference(preference);
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
							// 获取数据 解析 进行拼接
							List<DingDocCGitem> listGoods = JSONUtil.parseArray(str, DingDocCGitem.class);
							for (DingDocCGitem item : listGoods) {
								DefDocItemCG itemXS = getDefDocItemCG(item);
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
		};

	};

	// inorderdocid、inorderitemid、inorderdocshowid
	private DefDocItemCG getDefDocItemCG(DingDocCGitem item) {
		DefDocItemCG itemxs = new DefDocItemCG();
		itemxs.setBarcode(item.getBarcode());
		itemxs.setGoodsid(item.getGoodsid());
		itemxs.setGoodsname(item.getGoodsname());
		itemxs.setSpecification(item.getSpecification());
		itemxs.setUnitid(item.getUnitid());
		itemxs.setUnitname(item.getUnitname());
		itemxs.setNum(item.getNum() - item.getArrivalnum());
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
		itemxs.setWarehouseid(doccg.getWarehouseid());
		itemxs.setWarehousename(doccg.getWarehousename());
		itemxs.setInorderdocid(""+item.getDocid());
		itemxs.setInorderitemid(""+item.getItemid());
		// itemxs.setInorderdocshowid(item.gets);
		return itemxs;
	}

	// 设置商品件数
	protected void setBigNum(DefDocItemCG defDocItemCG) {
		String bigNum = mGoodsUnitDAO.getBigNum(defDocItemCG.getGoodsid(), defDocItemCG.getUnitid(),
				defDocItemCG.getNum());
		defDocItemCG.setBignum(bigNum);
	}

	private Button bt_sumNumber;
	private Button bt_totalSum;
	private ServiceStore serviceStore;
	private List<Long> listItemDelete;
	// private BarcodeManager bm;
	private double received;// 已付金额
	private double preference;// 优惠金额
	private double receiveable;// 优惠后应收
	private ArrayList<DefDocItemCG> localArrayList;
	private Scaner scaner;
	private Button btnGoodClass;

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

	private String validateDoc() {
		String message = null;
		if (TextUtils.isEmptyS(doccg.getBuildtime())) {
			doccg.setBuildtime(Utils.formatDate(Utils.getCurrentTime(false), "yyyy-MM-dd HH:mm:ss"));
		}
		if (TextUtils.isEmptyS(doccg.getBuilderid())) {
			doccg.setBuilderid(SystemState.getUser().getId());
		}
		if (!TextUtils.isEmptyS(doccg.getCustomerid())) {
			return "没有选择供应商!";
		}
		if (!TextUtils.isEmptyS(doccg.getDepartmentid())) {
			return "部门不能为空";
		}
		if ((doccg.getDiscountratio() <= 0.0D) || (doccg.getDiscountratio() > 1.0D)) {
			return "整单折扣必须大于0且小于等于1";
		}
		if (!TextUtils.isEmptyS(doccg.getDeliverytime())) {
			return "交货日期不能为空";
		}
		if (!TextUtils.isEmptyS(doccg.getSettletime())) {
			return "结算日期不能为空";
		}
		// 应该不需要
		if (!TextUtils.isEmptyS(doccg.getPrinttemplate())) {
			return "打印模板不能为空";
		}
		List<DefDocItemCG> data = adapter.getData();
		if (data != null && data.size() > 0) {
			for (int k = 0; k < data.size(); k++) {
				DefDocItemCG defdocitemcg = data.get(k);
				if (defdocitemcg.getNum() == 0.0D) {
					message = "【" + defdocitemcg.getGoodsname() + "】数量为0";
				}
				if (!TextUtils.isEmptyS(defdocitemcg.getWarehouseid())) {
					message = "【" + defdocitemcg.getGoodsname() + "】没有选择仓库";
				}

				if (!TextUtils.isEmptyS(defdocitemcg.getUnitid())) {
					message = "【" + defdocitemcg.getGoodsname() + "】没有选择单位";
				}
			}
		}

		return message;

	}

	/* 商品列表onItemClick */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		DefDocItemCG defdocitemCG = (DefDocItemCG) parent.getItemAtPosition(position);
		atvSearch.setText("");
		int i = -1;
		Intent localIntent = new Intent(this, InPurchaseDocAddGoodAct.class);
		localIntent.putExtra("customerid", doccg.getCustomerid());
		localIntent.putExtra("positiongive", i);
		localIntent.putExtra("position", position);
		localIntent.putExtra("docitem", defdocitemCG);
		localIntent.putExtra("doc", doccg);
		startActivityForResult(localIntent, 2);
	}

	@Override
	public void sum() {
		List<DefDocItemCG> data = adapter.getData();
		double sumMoney = 0.00d;
		int sumNum = 0;
		listItemDelete = adapter.getListItemDelete();
		Map<String, Object> sumGoodsClassMap = new HashMap<>();
		for (int i = 0; i < data.size(); i++) {
			DefDocItemCG itemCG = data.get(i);
			sumMoney += itemCG.getDiscountsubtotal();
			sumNum += itemCG.getNum();
			sumGoodsClassMap.put(itemCG.getGoodsid(), itemCG);
		}
		btnGoodClass.setText("品种:" + sumGoodsClassMap.size());
		bt_sumNumber.setText("数量:" + sumNum);
		bt_totalSum.setText("总价:\n" + Utils.normalizePrice(sumMoney) + "元");
		discountsubtotal = Utils.normalizePrice(sumMoney);
	}

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
			localActionBar.setTitle("*");
		} else {
			localActionBar.setTitle("");
		}
	}

}
