package com.ahjswy.cn.ui.outgoods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.JSONUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;

public class SaleCustomerHistoryItemActivity extends BaseActivity {
	private SaleCustomerHistoryItemAdapter adapter;
	private List<DefDocItemXS> listItem;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_salecustomerhistoryitem);
		initView();
	}

	private void initView() {
		// if (TextUtils.isEmpty(SaleCustomerHistoryActivity.listitem)) {
		// return;
		// }
		this.listView = (ListView) findViewById(R.id.listView);
		listItem = JSONUtil.str2list(getIntent().getStringExtra("listitem"), DefDocItemXS.class);
		this.adapter = new SaleCustomerHistoryItemAdapter(this);
		this.adapter.setData(listItem);
		this.listView.setAdapter(adapter);
		this.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		this.listView.setMultiChoiceModeListener(muliChoiceModeLisener);
	}

	MultiChoiceModeListener muliChoiceModeLisener = new MultiChoiceModeListener() {

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			adapter.setMultiChoice(false);
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			adapter.setMultiChoice(true);
			mode.setTitle("选择");
			mode.getMenuInflater().inflate(R.menu.menu_selectitem, menu);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.btnAdd:
				ArrayList<DefDocItemXS> itemxs = new ArrayList<DefDocItemXS>();
				for (int i = 0; i < adapter.getCount(); i++) {
					if (listView.isItemChecked(i)) {
						itemxs.add(listItem.get(i));
					}
				}
				Intent intent = new Intent();
				intent.putExtra("selecteditem", JSONUtil.toJSONString(itemxs));
				setResult(Activity.RESULT_OK, intent);
				finish();
				mode.finish();
				break;
			case R.id.btnSelectAll:
				for (int j = 0; j < adapter.getCount(); j++) {
					if (!listView.isItemChecked(j)) {
						listView.setItemChecked(j, true);
					}
				}
				break;
			default:

				break;
			}
			return true;
		}

		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
			adapter.setCheckePosition(position);
			mode.setSubtitle("选中" + listView.getCheckedItemCount() + "条");
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				setResult(Activity.RESULT_OK);
				finish();
				break;
			case 1:
				Intent intent = new Intent();
				intent.putExtra("selecteditem", JSONUtil.toJSONString(listItem));
				setResult(Activity.RESULT_OK, intent);
				finish();
				break;
			default:
				break;
			}

		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("添加全部").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent intent = new Intent();
			intent.putExtra("selecteditem", JSONUtil.toJSONString(this.listItem));
			this.setResult(Activity.RESULT_OK, intent);
			this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult(Activity.RESULT_FIRST_USER);
			finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void setActionBarText() {
		setTitle("客史明细");
	}
}
