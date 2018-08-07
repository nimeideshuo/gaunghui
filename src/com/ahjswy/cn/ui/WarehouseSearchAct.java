package com.ahjswy.cn.ui;

import java.io.Serializable;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.WarehouseDAO;
import com.ahjswy.cn.model.Warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WarehouseSearchAct extends BaseActivity implements OnItemClickListener {
	private List<Warehouse> warehouses;
	private ListView lvWarehouse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_warehouse);
		lvWarehouse = (ListView) findViewById(R.id.lvWarehouse);
		lvWarehouse.setOnItemClickListener(this);
		warehouses = new WarehouseDAO().getAllWarehouses();
		if ((warehouses == null) || (warehouses.size() == 0)) {
			return;
		} else {
			String[] arrayOfString = new String[warehouses.size()];
			for (int i = 0; i <= warehouses.size(); i++) {
				if (i >= this.warehouses.size()) {
					ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(this,
							android.R.layout.simple_list_item_1, arrayOfString);
					lvWarehouse.setAdapter(localArrayAdapter);
					return;
				}
				arrayOfString[i] = ((Warehouse) this.warehouses.get(i)).getName();
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent localIntent = new Intent();
		localIntent.putExtra("warehouse", (Serializable) this.warehouses.get(position));
		setResult(RESULT_OK, localIntent);
		finish();

	}

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
		getActionBar().setTitle("仓库");
	}

}
