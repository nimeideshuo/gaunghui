package com.ahjswy.cn.ui.transfer;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocItem;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.JSONUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class TransferAddMoreGoodsAct extends BaseActivity {
	private List<DefDocItem> items;
	private ListView listView;
	private TransferAddMoreAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_inventory_add_more_goods);
		items = JSONUtil.parseArray(getIntent().getStringExtra("items"), DefDocItem.class);
		listView = ((ListView) findViewById(R.id.listView));
		adapter = new TransferAddMoreAdapter(this);
		this.listView.setAdapter(adapter);
		this.adapter.setData(items);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "确定").setShowAsAction(2);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		if (menu.getItemId() == 1) {
			ArrayList<DefDocItem> localArrayList = new ArrayList<DefDocItem>();
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getNum() > 0.0D) {
					localArrayList.add(items.get(i));
				}
			}
			if (localArrayList.size() == 0) {
				showError("必需至少有一条商品数量大于0");
				return false;
			}
			Intent intent = new Intent();
			intent.putExtra("items", JSONUtil.toJSONString(localArrayList));
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		return super.onOptionsItemSelected(menu);
	}

	public void setActionBarText() {
		setTitle("商品添加");
	}
}
