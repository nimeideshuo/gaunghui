package com.ahjswy.cn.ui.outgoods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.DocContainerEntity;
import com.ahjswy.cn.request.ReqStrSearchDoc;
import com.ahjswy.cn.response.RespStrDocThinEntity;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.ingoods.InDocEditActivity;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.XListView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

/*
 * 
 * 我的销售
 */
public class SaleRecordActivity extends BaseActivity {
	protected SaleRecordAdapter adapter;
	private ReqStrSearchDoc condition;
	protected XListView listView;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_doc_records);
		listView = ((XListView) findViewById(R.id.listView));
		initCondition();

		adapter = new SaleRecordAdapter(this);
		listView.setOnItemClickListener(itemClickListener);
		listView.setXListViewListener(listener);
		SwipeMenuCreator local6 = new SwipeMenuCreator() {
			public void create(SwipeMenu paramAnonymousSwipeMenu) {
				SwipeMenuItem localSwipeMenuItem = new SwipeMenuItem(getApplicationContext());
				localSwipeMenuItem.setTitle("打印");
				localSwipeMenuItem.setTitleSize(14);
				localSwipeMenuItem.setTitleColor(-16777216);
				localSwipeMenuItem.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
				localSwipeMenuItem.setWidth(100);
				paramAnonymousSwipeMenu.addMenuItem(localSwipeMenuItem);
			}
		};
		listView.setMenuCreator(local6);
		listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
//				switch (index) {
//				case 0:
//					if (!listDoc.get(position).getIsposted()) {
//						PDH.showMessage("单据未过账，不能打印");
//						return false;
//					}
//					break;
//				}

				PDH.show(SaleRecordActivity.this, new PDH.ProgressCallBack() {
					public void action() {
						String localString = new ServiceStore().str_PrintDoc((listDoc.get(position)).getDoctypeid(),
								(listDoc.get(position)).getDocid());
						handlerPrint.sendMessage(handler.obtainMessage(0, localString));
					}
				});
				return false;
			}
		});
		loadData();
	}

	List<RespStrDocThinEntity> listDoc = new ArrayList<RespStrDocThinEntity>();

	private void initCondition() {
		condition = SystemState.conditionXS;
		if (this.condition == null) {
			this.condition = new ReqStrSearchDoc();
			this.condition.setDoctype(null);
			this.condition.setDepartmentID(null);
			this.condition.setCustomerID(null);
			this.condition.setWarehouseID(null);
			this.condition.setBuilderID(SystemState.getUser().getId());
			this.condition.setRemarkSummary("");
			this.condition.setShowID("");
			this.condition.setDoctypeName("全部");
			this.condition.setDepartmentName("全部");
			this.condition.setCustomerName("全部");
			this.condition.setWarehouseName("全部");
			this.condition.setBuilderName(SystemState.getUser().getName());
			this.condition.setDateBeginTime(SystemState.defaultTime + " 00:00:00");
			// this.condition.setDateBeginTime(Utils.formatDate(Utils.getStartTime().longValue(),
			// "yyyy-MM-dd HH:mm:ss"));
			this.condition.setDateEndTime(Utils.formatDate(Utils.getEndTime().longValue(), "yyyy-MM-dd HH:mm:ss"));
			this.condition.setOnlyShowNoSettleUp(false);
			this.condition.setInWarehouseID(null);
			this.condition.setOutWarehouseID(null);
			this.condition.setInWarehouseName(null);
			this.condition.setOutWarehouseName(null);
			this.condition.setPagesize(20);
			this.condition.setPageindex(1);
		}
	}

	private void loadData() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				condition.setPageindex(1);
				String str = new ServiceStore().str_SearchXSDoc(condition);
				handler.sendMessage(handler.obtainMessage(0, str));
			}
		});
	}

	private Handler handlerPrint = new Handler() {
		public void handleMessage(Message message) {
			String localString = message.obj.toString();
			if (RequestHelper.isSuccess(localString)) {
				PDH.showSuccess("打印成功");
				return;
			}
			RequestHelper.showError(localString);
		}
	};
	public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			try {
				final RespStrDocThinEntity respStrDocThinEntity = listDoc.get(position);
				PDH.show(SaleRecordActivity.this, new PDH.ProgressCallBack() {
					public void action() {
						if ("13".equals(respStrDocThinEntity.getDoctypeid())) {
							String str = new ServiceStore().str_GetXSDocDetail(respStrDocThinEntity.getDocid());
							handlerEdit.sendMessage(handlerEdit.obtainMessage(0, str));
							return;
						}

						if ("14".equals(respStrDocThinEntity.getDoctypeid())) {
							String str = new ServiceStore().str_GetXTDocDetail(respStrDocThinEntity.getDocid());
							handlerEdit.sendMessage(handlerEdit.obtainMessage(1, str));
							return;
						}
						if ("15".equals(respStrDocThinEntity.getDoctypeid())) {
							String str = new ServiceStore().str_GetXHDocDetail(respStrDocThinEntity.getDocid());
							handlerEdit.sendMessage(handlerEdit.obtainMessage(2, str));
							return;
						}

					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private XListView.IXListViewListener listener = new XListView.IXListViewListener() {
		public void onLoadMore() {
			if (((listDoc != null) || (listDoc.size() > 20)) && (listDoc.size() % 20 == 0)) {
				new Thread() {
					public void run() {
						condition.setPageindex(listDoc.size() / 20 + 1);
						String str = new ServiceStore().str_SearchXSDoc(condition);
						handler.sendMessage(handler.obtainMessage(1, str));
					}
				}.start();
			}
		}

		public void onRefresh() {

		}
	};
	// 返回值 来进行判断 view的显示
	private Handler handler = new Handler() {

		public void handleMessage(Message message) {

			List<RespStrDocThinEntity> str2list = null;
			String sMessage = message.obj.toString();
			listView.stopLoadMore();
			if (RequestHelper.isSuccess(sMessage)) {
				if (message.what == 0) {
					listDoc.clear();
				}
				str2list = JSONUtil.str2list(sMessage, RespStrDocThinEntity.class);
				if (str2list == null) {
					return;
				}
				if ((str2list.size() == 0) || (str2list.size() < 20)) {
					listView.setPullLoadEnable(false);
				} else {
					listView.setPullLoadEnable(true);
				}
				listDoc.addAll(str2list);
				adapter.setData(listDoc);
				listView.setAdapter(adapter);
				int pageindex = condition.getPageindex();
				if (pageindex > 1) {// 第2页开始跳
					listView.setSelection((pageindex - 1) * 20);// 跳到上次单据的结尾
				}
			} else {
				PDH.showMessage(sMessage);
			}
		}
	};
	private Handler handlerEdit = new Handler() {
		public void handleMessage(Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				DocContainerEntity localObject = (DocContainerEntity) JSONUtil.fromJson(message,
						DocContainerEntity.class);
				switch (msg.what) {
				case 0:
					startActivityForResult(new Intent().setClass(SaleRecordActivity.this, OutDocEditActivity.class)
							.putExtra("docContainer", (Serializable) localObject).putExtra("ishaschanged", false), 1);
					return;
				case 1:
					startActivityForResult(new Intent(SaleRecordActivity.this, InDocEditActivity.class)
							.putExtra("docContainer", (Serializable) localObject).putExtra("ishaschanged", false), 1);
					return;
				case 2:
					// startActivityForResult(new Intent()
					// .setClass(SaleRecordActivity.this,
					// OutExchangeDocEditActivity.class)
					// .putExtra("docContainer", (Serializable)
					// localObject).putExtra("ishaschanged", false), 1);
					// return;
				}
			}else{
				PDH.showFail(message);
			}
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "筛选").setShowAsAction(2);
		paramMenu.add(0, 1, 0, "刷新").setShowAsAction(2);
		return super.onCreateOptionsMenu(paramMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		case 0:
			Intent localIntent = new Intent(this, SaleDocSearchAct.class);
			localIntent.putExtra("condition", condition);
			startActivityForResult(localIntent, 0);
			break;
		case 1:
			loadData();
			break;

		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			condition = (ReqStrSearchDoc) data.getSerializableExtra("condition");
			loadData();
			SystemState.conditionXS = new ReqStrSearchDoc(condition);
		}
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("我的销售");
	}
}
