package com.ahjswy.cn.ui.transfer;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.model.DefDocItem;
import com.ahjswy.cn.model.DefDocItemPD;
import com.ahjswy.cn.model.DefDocTransfer;
import com.ahjswy.cn.model.DocContainerEntity;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.popupmenu.TransferEditMenuPopup;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.ui.SearchHelper;
import com.ahjswy.cn.ui.inventory.InventoryAddMoreGoodsAct;
import com.ahjswy.cn.ui.inventory.InventoryEditActivity;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.InfoDialog;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.AutoTextView;
import com.ahjswy.cn.views.Dialog_listCheckBox;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import android.app.Activity;
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
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 调拨开单
 * 
 * @author Administrator
 *
 */
public class TransferEditActivity extends BaseActivity implements OnTouchListener, ScanerBarcodeListener {

	private ServiceStore serviceStore;
	private RelativeLayout root;
	private Button btnAdd;
	private DefDocTransfer doc;
	private List<DefDocItem> listItem;
	private List<Long> listItemDelete;
	boolean ishaschanged;
	private SwipeMenuListView listView;
	private TransferItemAdapter adapter;
	private AutoTextView atvSearch;
	private SearchHelper searchHelper;
	private DocContainerEntity doccontainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_transfer);
		initListView();
		initView();
		refreshUI();
		factory = Scaner.factory(getApplicationContext());
		factory.setBarcodeListener(this);
	}

	private void initView() {
		LinearLayout linearSearch = ((LinearLayout) findViewById(R.id.lieSearch));
		atvSearch = ((AutoTextView) findViewById(R.id.atvSearch));
		searchHelper = new SearchHelper(this, linearSearch);
		atvSearch.setOnItemClickListener(onSearchItemClickListener);
		btnAdd = ((Button) findViewById(R.id.btnAdd));
		this.root.setOnTouchListener(this);
		this.btnAdd.setOnClickListener(this.onAddMoreListener);
		this.listItemDelete = new ArrayList<Long>();
		docUtils = new DocUtils();
		if (listItem == null) {
			listItem = new ArrayList<>();
		}
		dialog = new Dialog_listCheckBox(this);
		utils = new DocUtils();
	}

	private void initListView() {
		serviceStore = new ServiceStore();
		root = (RelativeLayout) findViewById(R.id.root);
		this.ishaschanged = getIntent().getBooleanExtra("ishaschanged", true);
		doccontainer = (DocContainerEntity) getIntent().getSerializableExtra("docContainer");
		this.doc = ((DefDocTransfer) JSONUtil.fromJson(doccontainer.getDoc(), DefDocTransfer.class));
		this.listItem = JSONUtil.parseArray(doccontainer.getItem(), DefDocItem.class);
		listView = ((SwipeMenuListView) findViewById(R.id.listView));
		listView.setOnTouchListener(this);
		adapter = new TransferItemAdapter(this);
		adapter.setData(listItem);
		listView.setAdapter(adapter);
		SwipeMenuCreator local5 = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem localSwipeMenuItem = new SwipeMenuItem(getApplicationContext());
				localSwipeMenuItem.setTitle("复制");
				localSwipeMenuItem.setTitleSize(14);
				localSwipeMenuItem.setTitleColor(-16777216);
				localSwipeMenuItem.setWidth(100);
				localSwipeMenuItem.setBackground(new ColorDrawable(Color.rgb(48, 177, 245)));
				menu.addMenuItem(localSwipeMenuItem);
				localSwipeMenuItem = new SwipeMenuItem(getApplicationContext());
				localSwipeMenuItem.setTitle("删除");
				localSwipeMenuItem.setTitleSize(14);
				localSwipeMenuItem.setTitleColor(-16777216);
				localSwipeMenuItem.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
				localSwipeMenuItem.setWidth(100);
				menu.addMenuItem(localSwipeMenuItem);
			}

		};

		this.listView.setMenuCreator(local5);
		this.listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					DefDocItem item = new DefDocItem(listItem.get(position));
					item.setItemid(0L);
					listItem.add(item);
					mHandler.postDelayed(new Runnable() {
						public void run() {
							adapter.setData(listItem);
							refreshUI();
							ishaschanged = true;
							setActionBarText();
						}
					}, 180L);

					break;
				case 1:
					long l = listItem.get(position).getItemid();
					if (l > 0L) {
						listItemDelete.add(Long.valueOf(l));
					}
					listItem.remove(position);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							adapter.setData(listItem);
							refreshUI();
							ishaschanged = true;
							setActionBarText();
						}
					}, 180L);
					break;
				default:
					break;
				}
				return false;
			}

		});
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if ((doc.isIsavailable()) && (doc.isIsposted())) {
					return;
				}
				if ((menuPopup != null) && (menuPopup.isShowing())) {
					menuPopup.dismiss();
					LayoutParams attributes = getWindow().getAttributes();
					attributes.alpha = 1.0F;
					getWindow().setAttributes(attributes);
				}
				Intent intent = new Intent();
				intent.putExtra("position", position);
				intent.putExtra("docitem", listItem.get(position));
				startActivityForResult(intent.setClass(TransferEditActivity.this, TransferAddGoodAct.class), 3);
			}
		});
	}

	private View.OnClickListener onAddMoreListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if ((menuPopup != null) && (menuPopup.isShowing())) {
				menuPopup.dismiss();
				LayoutParams attributes = getWindow().getAttributes();
				attributes.alpha = 1.0F;
				getWindow().setAttributes(attributes);
			}
			List<GoodsThin> select = searchHelper.getAdapter().getSelect();
			if (select.isEmpty()) {
				return;
			}
			atvSearch.setText("");
			ArrayList<DefDocItem> localArrayList = new ArrayList<DefDocItem>();
			for (int i = 0; i < select.size(); i++) {
				GoodsThin goodsthin = (GoodsThin) select.get(i);
				localArrayList.add(docUtils.fillItem(doc, goodsthin, DocUtils.getDefaultNum(), 0.0D));
			}
			startActivityForResult(new Intent().setClass(TransferEditActivity.this, TransferAddMoreGoodsAct.class)
					.putExtra("items", JSONUtil.toJSONString(localArrayList)), 2);
		}

	};

	AdapterView.OnItemClickListener onSearchItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			final GoodsThin goodsThin = searchHelper.getAdapter().getTempGoods().get(position);
			atvSearch.setText("");
			PDH.show(TransferEditActivity.this, new PDH.ProgressCallBack() {

				@Override
				public void action() {
					// SP_GetGoodsCostprice 查询商品成本价格
					DefDocItem docitem = docUtils.fillItem(doc, goodsThin, DocUtils.getDefaultNum(), 0.0D);
					Warehouse warehouse = docUtils.getDBOutWarehouse(doc.getInwarehouseid(), doc.getOutwarehouseid());
					docitem.setWarehouseid(warehouse.getId());
					docitem.setWarehousename(warehouse.getName());
					docUtils.setDBAddItem(doc, docitem);
					Intent intent = new Intent(TransferEditActivity.this, TransferAddGoodAct.class);
					intent.putExtra("docitem", docitem);
					startActivityForResult(intent, 1);
				}

			});

		}

	};

	private String validateDoc() {
		if (!TextUtils.isEmptyS(this.doc.getBuildtime())) {
			this.doc.setBuildtime(Utils.formatDate(Utils.getCurrentTime(false), "yyyy-MM-dd HH:mm:ss"));
		}
		if (!TextUtils.isEmptyS(this.doc.getBuilderid())) {
			this.doc.setBuilderid(SystemState.getUser().getId());
		}
		if (!TextUtils.isEmptyS(this.doc.getDepartmentid())) {
			return "部门不能为空";
		}
		if (!TextUtils.isEmptyS(this.doc.getInwarehouseid())) {
			return "调入仓库不能为空";
		}
		if (!TextUtils.isEmptyS(this.doc.getPrinttemplate())) {
			return "打印模板不能为空";
		}
		for (int i = 0; i < listItem.size(); i++) {
			DefDocItem docItem = listItem.get(i);
			if (docItem.getNum() == 0.0D) {
				return "【" + docItem.getGoodsname() + "】数量为0";
			}
			if (!TextUtils.isEmptyS(docItem.getWarehouseid())) {
				return "【" + docItem.getGoodsname() + "】没有选择仓库";
			}
			if (!TextUtils.isEmptyS(docItem.getUnitid())) {
				return "【" + docItem.getGoodsname() + "】没有选择单位";
			}
			if (docItem.getWarehouseid().equals(this.doc.getInwarehouseid())) {
				return "【" + docItem.getGoodsname() + "】调出仓库与调入仓库相同";
			}
			if (!TextUtils.isEmptyS(docItem.getBatch()) && docItem.isIsusebatch()) {
				return "【" + docItem.getGoodsname() + "】没有选择批次";
			}
		}
		return null;
	}

	public void refreshUI() {
		if ((this.doc.isIsavailable()) && (this.doc.isIsposted())) {
			findViewById(R.id.lieSearch).setVisibility(View.GONE);
			findViewById(R.id.top).setVisibility(View.GONE);
			this.listView.setItemSwipe(false);
		} else {
			findViewById(R.id.lieSearch).setVisibility(View.VISIBLE);
			findViewById(R.id.top).setVisibility(View.VISIBLE);
			this.listView.setItemSwipe(true);
		}
	}

	public void check(final boolean isPrint) {
		String validateDoc = validateDoc();
		if (validateDoc != null) {
			InfoDialog.showError(this, validateDoc);
			return;
		}
		if ((this.listItem == null) || (this.listItem.size() == 0)) {
			InfoDialog.showError(this, "空单不能过账");
			return;
		}
		MAlertDialog dialog = new MAlertDialog(this);
		dialog.setMessage("过账后的单据不能修改。\n确定过账？");
		dialog.setNeutralButton(new MAlertDialog.OnClickListener() {

			@Override
			public void onClick(MAlertDialog dialog) {
				dialog.dismiss();
				PDH.show(TransferEditActivity.this, new PDH.ProgressCallBack() {
					public void action() {
						String str = serviceStore.str_CheckDBDoc(doc, listItem, listItemDelete, isPrint);
						handler.sendMessage(handler.obtainMessage(1, str));
					}
				});
			}
		});
		dialog.show();
	}

	public void docProperty() {
		Intent localIntent = new Intent(this, TransferDocOpenActivity.class);
		localIntent.putExtra("doc", this.doc);
		startActivityForResult(localIntent, 0);
	}

	public void delete() {
		if (this.doc.getDocid() > 0L) {
			MAlertDialog dialog = new MAlertDialog(this);
			dialog.setMessage("确定删除？");
			dialog.setNeutralButton(new MAlertDialog.OnClickListener() {

				@Override
				public void onClick(MAlertDialog dialog) {
					PDH.show(TransferEditActivity.this, new PDH.ProgressCallBack() {

						@Override
						public void action() {
							String str = serviceStore.str_DeleteDoc(doc.getDoctypeid(), doc.getDocid());
							handler.sendMessage(handler.obtainMessage(2, str));
						}

					});
				}
			});
			dialog.show();
			return;
		}
		finish();
	}

	public void save() {
		String str = validateDoc();
		if (str != null) {
			InfoDialog.showError(this, str);
			return;
		}
		String result = serviceStore.str_SaveDBDoc(doc, listItem, listItemDelete);
		handler.sendMessage(handler.obtainMessage(0, result));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "单击显示菜单").setIcon(getResources().getDrawable(R.drawable.btn_submenu)).setShowAsAction(1);
		return super.onCreateOptionsMenu(menu);
	}

	TransferEditMenuPopup menuPopup;

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		if (menu.getItemId() == 0) {
			LayoutParams attributes = getWindow().getAttributes();
			attributes.alpha = 0.8f;
			getWindow().setAttributes(attributes);
			if (menuPopup == null) {
				menuPopup = new TransferEditMenuPopup(this);
			}
			menuPopup.showAtLocation(listView, Gravity.BOTTOM, 0, 0);
		}
		if (menu.getItemId() == android.R.id.home) {
			if (ishaschanged) {
				MAlertDialog dialog = new MAlertDialog(this);
				dialog.setMessage("是否保存当前单据？");
				dialog.setNeutralButton(new MAlertDialog.OnClickListener() {

					@Override
					public void onClick(MAlertDialog dialog) {
						dialog.dismiss();
						save();
					}
				});
				dialog.setNegativeButton(new MAlertDialog.OnClickListener() {

					@Override
					public void onClick(MAlertDialog dialog) {
						dialog.dismiss();
						finish();
					}
				});
				dialog.show();
			} else {
				setResult(RESULT_FIRST_USER);
				finish();
			}
		}
		return true;
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if ((this.menuPopup != null) && (this.menuPopup.isShowing())) {
			this.menuPopup.dismiss();
			LayoutParams attributes = getWindow().getAttributes();
			attributes.alpha = 1.0F;
			getWindow().setAttributes(attributes);
			return true;
		}
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String localObject = msg.obj.toString();
			if (RequestHelper.isSuccess(localObject)) {
				if (msg.what == 2) {
					PDH.showSuccess("删除成功");
					setResult(Activity.RESULT_OK);
					finish();
					return;
				}
				DocContainerEntity doccontainer = (DocContainerEntity) JSONUtil.fromJson(localObject,
						DocContainerEntity.class);
				doc = ((DefDocTransfer) JSONUtil.fromJson(doccontainer.getDoc(), DefDocTransfer.class));
				listItem = JSONUtil.parseArray(doccontainer.getItem(), DefDocItem.class);
				listItemDelete = new ArrayList<Long>();
				switch (msg.what) {
				case 0:
					adapter.setData(listItem);
					refreshUI();
					PDH.showSuccess("保存成功");
					ishaschanged = false;
					setActionBarText();
					return;
				case 1:
					if ((doc.isIsavailable()) && (doc.isIsposted())) {
						PDH.showSuccess("过账成功");
						setResult(Activity.RESULT_OK);
						finish();
						return;
					}
					adapter.setData(TransferEditActivity.this.listItem);
					refreshUI();
					InfoDialog.showError(TransferEditActivity.this, doccontainer.getInfo());
					break;

				default:
					break;
				}
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 0:
				this.doc = (DefDocTransfer) data.getSerializableExtra("doc");
				break;
			case 1:
				DefDocItem defitem = (DefDocItem) data.getSerializableExtra("docitem");
				listItem.add(defitem);
				adapter.setData(listItem);
				refreshUI();
				ishaschanged = true;
				setActionBarText();
				break;
			case 2:
				List<DefDocItem> newListItem = JSONUtil.parseArray(data.getStringExtra("items"), DefDocItem.class);
				for (int i = 0; i < newListItem.size(); i++) {
					DefDocItem docItem = newListItem.get(i);
					docUtils.setDBAddItem(doc, docItem);
				}
				listItem.addAll(newListItem);
				adapter.setData(listItem);
				refreshUI();
				ishaschanged = true;
				setActionBarText();
				break;
			case 3:

				int position = data.getIntExtra("position", 0);
				DefDocItem item = (DefDocItem) data.getSerializableExtra("docitem");
				this.listItem.set(position, item);
				this.adapter.setData(this.listItem);
				refreshUI();
				break;

			default:
				break;
			}
		}
	}

	private Scaner factory;
	private DocUtils docUtils;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if ((this.menuPopup != null) && (this.menuPopup.isShowing())) {
			this.menuPopup.dismiss();
			LayoutParams attributes = getWindow().getAttributes();
			attributes.alpha = 1.0F;
			getWindow().setAttributes(attributes);
		}
		return false;
	}

	public DefDocTransfer getDoc() {
		return doc;
	}

	@Override
	public void setBarcode(String barcode) {
		atvSearch.setText("");
		if (listItem.size() > DocUtils.MAXITEM) {
			showError("商品已经开满!");
			return;
		}
		readBarcode(barcode);
	}

	private Dialog_listCheckBox dialog;
	private DocUtils utils;

	private void readBarcode(String barcode) {
		final ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcode);
		if (goodsThinList.size() == 1) {
			PDH.show(TransferEditActivity.this, new PDH.ProgressCallBack() {

				@Override
				public void action() {
					DefDocItem docitem = docUtils.fillItem(doc, goodsThinList.get(0), DocUtils.getDefaultNum(), 0.0D);
					docUtils.setDBAddItem(doc, docitem);
					Intent intent = new Intent(TransferEditActivity.this, TransferAddGoodAct.class);
					intent.putExtra("docitem", docitem);
					startActivityForResult(intent, 1);
				}

			});
		} else {
			dialog.setGoods(goodsThinList);
			dialog.setTempGoods(goodsThinList);
			dialog.ShowMe();
			dialog.ensure(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					List<GoodsThin> select = dialog.getSelect();
					for (int i = 0; i < select.size(); i++) {
						DefDocItem fillItem = utils.fillItem(doc, select.get(i), DocUtils.getDefaultNum(), 0.0D);
						Intent intent = new Intent(TransferEditActivity.this, TransferAddGoodAct.class);
						intent.putExtra("docitem", fillItem);
						startActivityForResult(intent, 1);
					}

				}
			});

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		factory.removeListener();
		factory = null;
	}

	@Override
	public void setActionBarText() {
		if (this.ishaschanged) {
			setTitle(doc.getShowid() + "*");
		} else {
			setTitle(doc.getShowid());
		}
	}
}
