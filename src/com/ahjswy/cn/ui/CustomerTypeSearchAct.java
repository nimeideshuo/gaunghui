package com.ahjswy.cn.ui;

import java.io.Serializable;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.CustomerTypeDAO;
import com.ahjswy.cn.model.Customertype;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CustomerTypeSearchAct extends BaseActivity implements OnItemClickListener {
	private List<Customertype> types;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_search);
		types = new CustomerTypeDAO().queryAllcuCustomertypes();
		listview = (ListView) findViewById(R.id.listview);
		this.listview.setOnItemClickListener(this);
		if ((this.types == null) || (this.types.size() == 0)) {
			return;
		}
		String[] arrayOfString = new String[this.types.size()];
		for (int i = 0; i < types.size(); i++) {
			arrayOfString[i] = types.get(i).getName();
		}
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				arrayOfString);
		listview.setAdapter(localArrayAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(paramMenuItem);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent localIntent = new Intent();
		localIntent.putExtra("customertype", (Serializable) this.types.get(position));
		setResult(RESULT_OK, localIntent);
		finish();
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("客户分类");
	}
}
