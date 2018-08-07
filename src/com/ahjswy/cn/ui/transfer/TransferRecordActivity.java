package com.ahjswy.cn.ui.transfer;

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
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.XListView;
import com.ahjswy.cn.views.XListView.IXListViewListener;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class TransferRecordActivity extends BaseActivity {
	private XListView listView;
	private List<RespStrDocThinEntity> listDoc = new ArrayList<RespStrDocThinEntity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_doc_records);
		listView = ((XListView) findViewById(R.id.listView));
		initCondition();
		adapter = new TransferRecordAdapter(this);
		this.listView.setOnItemClickListener(itemClickListener);
		this.listView.setXListViewListener(listener);
		SwipeMenuCreator paramBundle = new SwipeMenuCreator() {
			public void create(SwipeMenu paramAnonymousSwipeMenu) {
				SwipeMenuItem localSwipeMenuItem = new SwipeMenuItem(
						TransferRecordActivity.this.getApplicationContext());
				localSwipeMenuItem.setTitle("打印");
				localSwipeMenuItem.setTitleSize(14);
				localSwipeMenuItem.setTitleColor(-16777216);
				localSwipeMenuItem.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
				localSwipeMenuItem.setWidth(100);
				paramAnonymousSwipeMenu.addMenuItem(localSwipeMenuItem);
			}
		};
		this.listView.setMenuCreator(paramBundle);
		this.listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, final int index) {
				switch (index) {
				case 0:
					if (!listDoc.get(index).getIsposted()) {
						PDH.showMessage("单据未过账，不能打印");
						return false;
					}
					PDH.show(TransferRecordActivity.this, new PDH.ProgressCallBack() {
						public void action() {
							String str = new ServiceStore().str_PrintDoc(listDoc.get(index).getDoctypeid(),
									listDoc.get(index).getDocid());
							handlerPrint.sendMessage(handler.obtainMessage(0, str));
						}
					});

					break;

				default:
					break;
				}

				return false;
			}
		});
		loadData();
	}

	private ReqStrSearchDoc condition;
	private TransferRecordAdapter adapter;

	private void initCondition() {
		this.condition = SystemState.conditionDB;
		if (this.condition == null) {
			this.condition = new ReqStrSearchDoc();
			this.condition.setDoctype(null);
			this.condition.setDepartmentID(null);
			this.condition.setBuilderID(SystemState.getUser().getId());
			this.condition.setInWarehouseID(null);
			this.condition.setOutWarehouseID(null);
			this.condition.setRemarkSummary("");
			this.condition.setShowID("");
			this.condition.setDoctypeName("全部");
			this.condition.setDepartmentName("全部");
			this.condition.setBuilderName(SystemState.getUser().getName());
			this.condition.setInWarehouseName("全部");
			this.condition.setOutWarehouseName("全部");
			this.condition.setDateBeginTime(Utils.formatDate(Utils.getStartTime().longValue(), "yyyy-MM-dd HH:mm:ss"));
			this.condition.setDateEndTime(Utils.formatDate(Utils.getEndTime().longValue(), "yyyy-MM-dd HH:mm:ss"));
			this.condition.setOnlyShowNoSettleUp(false);
			this.condition.setCustomerID(null);
			this.condition.setWarehouseID(null);
			this.condition.setCustomerName(null);
			this.condition.setWarehouseName(null);
			this.condition.setPagesize(20);
			this.condition.setPageindex(1);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "筛选").setShowAsAction(2);
		menu.add(0, 1, 0, "刷新").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
		case 0:
			Intent localIntent = new Intent();
			localIntent.putExtra("condition", this.condition);
			startActivityForResult(localIntent.setClass(this, TransferDocSearchAct.class), 0);
			break;
		case 1:
			loadData();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(menu);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				this.condition = ((ReqStrSearchDoc) data.getSerializableExtra("condition"));
				loadData();
				SystemState.conditionDB = new ReqStrSearchDoc(this.condition);
			}
		}

	}

	private Handler handlerPrint = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			if (RequestHelper.isSuccess(result)) {
				PDH.showSuccess("打印成功");
				return;
			}
			RequestHelper.showError(result);
		};
	};

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
			PDH.show(TransferRecordActivity.this, new PDH.ProgressCallBack() {
				public void action() {
					String str = new ServiceStore().str_GetDBDocDetail((listDoc.get(position)).getDocid());
					handlerEdit.sendMessage(handlerEdit.obtainMessage(0, str));
				}
			});

		}

	};
	IXListViewListener listener = new IXListViewListener() {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub

		}
	};

	private void loadData() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				condition.setPageindex(1);
				String str = new ServiceStore().str_SearchDBDoc(TransferRecordActivity.this.condition);
				handler.sendMessage(handler.obtainMessage(0, str));
			}
		});
	}

	private Handler handlerEdit = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			if (RequestHelper.isSuccess(result)) {
				DocContainerEntity doccontainer = (DocContainerEntity) JSONUtil.fromJson(result,
						DocContainerEntity.class);
				startActivityForResult(new Intent().setClass(TransferRecordActivity.this, TransferEditActivity.class)
						.putExtra("docContainer", doccontainer).putExtra("ishaschanged", false), 1);
				return;
			} else {
				PDH.showFail(result);
			}
		};
	};

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			List<RespStrDocThinEntity> str2list = null;
			String sMessage = msg.obj.toString();
			listView.stopLoadMore();
			if (RequestHelper.isSuccess(sMessage)) {
				if (msg.what == 0) {
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

		};
	};

	@Override
	public void setActionBarText() {
		setTitle("我的调拨");
	}
}
