package com.ahjswy.cn.ui.inventory;

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
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

/**
 * 我 的盘点
 * 
 * @author Administrator
 *
 */
public class InventoryRecordActivity extends BaseActivity {
	private XListView listView;
	private InventoryRecordAdapter adapter;
	private ReqStrSearchDoc condition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_doc_records);
		listView = (XListView) findViewById(R.id.listView);
		initCondition();
		adapter = new InventoryRecordAdapter(this);
		this.listView.setOnItemClickListener(this.itemClickListener);
		this.listView.setXListViewListener(this.listener);
		SwipeMenuCreator paramBundle = new SwipeMenuCreator() {
			public void create(SwipeMenu menu) {
				SwipeMenuItem item = new SwipeMenuItem(getApplicationContext());
				item.setTitle("打印");
				item.setTitleSize(14);
				item.setTitleColor(-16777216);
				item.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
				item.setWidth(100);
				menu.addMenuItem(item);
			}
		};
		this.listView.setMenuCreator(paramBundle);

		this.listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, final int index) {

				switch (index) {
				case 0:
					if (!(listDoc.get(index)).getIsposted()) {
						PDH.showMessage("单据未审核，不能打印");
						return false;
					}
					PDH.show(InventoryRecordActivity.this, new PDH.ProgressCallBack() {
						public void action() {
							String str = new ServiceStore().str_PrintDoc(listDoc.get(index).getDoctypeid(),
									listDoc.get(index).getDocid());
							handlerPrint.sendMessage(handler.obtainMessage(0, str));
						}
					});
					break;
				}
				return false;
			}

		});
		loadData();

	}

	private List<RespStrDocThinEntity> listDoc = new ArrayList<RespStrDocThinEntity>();

	private void initCondition() {
		condition = SystemState.conditionPD;
		if (this.condition == null) {
			this.condition = new ReqStrSearchDoc();
			this.condition.setDoctype(null);
			this.condition.setDepartmentID(null);
			this.condition.setWarehouseID(null);
			this.condition.setBuilderID(SystemState.getUser().getId());
			this.condition.setRemarkSummary("");
			this.condition.setShowID("");
			this.condition.setDoctypeName("全部");
			this.condition.setDepartmentName("全部");
			this.condition.setWarehouseName("全部");
			this.condition.setBuilderName(SystemState.getUser().getName());
			this.condition.setDateBeginTime(Utils.formatDate(Utils.getStartTime().longValue(), "yyyy-MM-dd HH:mm:ss"));
			this.condition.setDateEndTime(Utils.formatDate(Utils.getEndTime().longValue(), "yyyy-MM-dd HH:mm:ss"));
			this.condition.setOnlyShowNoSettleUp(false);
			this.condition.setCustomerID(null);
			this.condition.setCustomerName(null);
			this.condition.setInWarehouseID(null);
			this.condition.setOutWarehouseID(null);
			this.condition.setInWarehouseName(null);
			this.condition.setOutWarehouseName(null);
			this.condition.setPagesize(20);
			this.condition.setPageindex(1);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "筛选").setShowAsAction(2);
		menu.add(0, 1, 0, "刷新").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
		case 0:
			Intent intent = new Intent(this, InventoryDocSearchAct.class);
			intent.putExtra("condition", this.condition);
			startActivityForResult(intent, 0);
			break;
		case 1:
			loadData();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(menu);
	}

	private void loadData() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				condition.setPageindex(1);
				String str = new ServiceStore().str_SearchPDDoc(condition);
				handler.sendMessage(handler.obtainMessage(0, str));
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			String sMessage = msg.obj.toString();
			listView.stopLoadMore();
			if (RequestHelper.isSuccess(sMessage)) {
				if (msg.what == 0) {
					listDoc.clear();
				}
				List<RespStrDocThinEntity> str2list = JSONUtil.str2list(sMessage, RespStrDocThinEntity.class);
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
	private Handler handlerEdit = new Handler() {
		public void handleMessage(Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				DocContainerEntity docContainer = (DocContainerEntity) JSONUtil.fromJson(message,
						DocContainerEntity.class);
				// TODO
				startActivityForResult(new Intent(InventoryRecordActivity.this, InventoryEditActivity.class)
						.putExtra("docContainer", docContainer).putExtra("ishaschanged", false), 1);
				return;
			} else {
				PDH.showFail(message);
			}
		};
	};
	public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
			PDH.show(InventoryRecordActivity.this, new PDH.ProgressCallBack() {
				public void action() {
					String str = new ServiceStore().str_GetPDDocDetail(listDoc.get(position).getDocid());
					handlerEdit.sendMessage(handlerEdit.obtainMessage(0, str));
				}
			});
		}

	};

	private XListView.IXListViewListener listener = new XListView.IXListViewListener() {

		@Override
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

		@Override
		public void onRefresh() {

		}

	};
	private Handler handlerPrint = new Handler() {
		public void handleMessage(Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				PDH.showSuccess("打印成功");
				return;
			}
			RequestHelper.showError(message);
		};

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 0:
				this.condition = ((ReqStrSearchDoc) data.getSerializableExtra("condition"));
				loadData();
				SystemState.conditionPD = new ReqStrSearchDoc(this.condition);
				break;
			case 1:
				loadData();
				break;
			default:
				break;

			}
		}

	}

	@Override
	public void setActionBarText() {
		setTitle("我的盘点");
	}
}
