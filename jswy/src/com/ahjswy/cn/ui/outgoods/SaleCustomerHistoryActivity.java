package com.ahjswy.cn.ui.outgoods;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.response.RespStrDocThinEntity;
import com.ahjswy.cn.service.ServiceStore;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.MLog;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.views.XListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SaleCustomerHistoryActivity extends BaseActivity implements OnItemClickListener {
	private String customerid;
	private String customername;
	protected SaleCustomerHistoryAdapter adapter;
	protected XListView listView;
	List<RespStrDocThinEntity> listDoc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_salecustomerhistory_records);
		initView();
		loadData();
	}

	private void initView() {
		listView = (XListView) findViewById(R.id.listView);
		this.customerid = getIntent().getStringExtra("customerid");
		this.customername = getIntent().getStringExtra("customername");
		adapter = new SaleCustomerHistoryAdapter(this);
		listView.setOnItemClickListener(this);
		listView.setXListViewListener(listener);
		listView.setFootViewVisible(false);
	}

	public void loadData() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				String localString = new ServiceStore().str_GetCustomerHistory(customerid);
				handler.sendMessage(handler.obtainMessage(0, localString));
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				listDoc = JSONUtil.str2list(message, RespStrDocThinEntity.class);
				adapter.setData(listDoc);
				listView.setAdapter(adapter);
				return;

			}
			PDH.showMessage(message);

		};
	};
	// public static String listitem;
	Handler handlerGetItem = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String message = msg.obj.toString();
			if (RequestHelper.isSuccess(message)) {
				Intent intent = new Intent(SaleCustomerHistoryActivity.this, SaleCustomerHistoryItemActivity.class);
				intent.putExtra("listitem", message);
				startActivityForResult(intent, 0);
				// listitem = message;
				return;
			}
			PDH.showFail(message);
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("刷新").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			break;
		case 0:
			loadData();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final RespStrDocThinEntity thinEntity = listDoc.get(position);
		PDH.show(SaleCustomerHistoryActivity.this, new PDH.ProgressCallBack() {
			public void action() {
				String result = new ServiceStore().str_GetDocItemXS(thinEntity.getDocid());
				handlerGetItem.sendMessage(handlerGetItem.obtainMessage(0, result));
			}
		});

	}

	XListView.IXListViewListener listener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			MLog.d("onRefresh");
		}

		@Override
		public void onLoadMore() {
			MLog.d("onLoadMore");
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("客史-【" + customername + "】");
	}
}
