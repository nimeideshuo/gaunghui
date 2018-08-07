package com.ahjswy.cn.ui;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.CustomerDAO;
import com.ahjswy.cn.model.SupplierThin;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.AutoTextViews;
import com.ahjswy.cn.views.AutoTextViews.OnTextChangeListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SupplerInpurchaseAct extends BaseActivity implements OnItemClickListener, OnTextChangeListener {
	private ListView lvSuppler;
	private AutoTextViews etSearch;

	private List<SupplierThin> customers;
	List<SupplierThin> temp;
	private ArrayList<SupplierThin> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_suppler);
		initView();
		loadData();
	}

	private void initView() {
		etSearch = ((AutoTextViews) findViewById(R.id.etSearch));
		lvSuppler = (ListView) findViewById(R.id.lvSuppler);
		lvSuppler.setOnItemClickListener(this);
		etSearch.setOnTextChangeListener(this);
	}

	private void loadData() {
		list = new ArrayList<SupplierThin>();
		temp = new ArrayList<SupplierThin>();
		customers = new CustomerDAO().queryAllSupplier();

		this.temp.addAll(this.customers);

		if ((this.customers == null) || (this.customers.size() == 0)) {
			return;
		}
		String[] arrayOfString = new String[customers.size()];
		for (int i = 0; i < customers.size(); i++) {
			arrayOfString[i] = ((SupplierThin) this.customers.get(i)).getName();
		}
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				arrayOfString);
		lvSuppler.setAdapter(localArrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			break;
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		SupplierThin supplierThin = null;
		if (list.size() == 0) {
			supplierThin = temp.get(position);
		} else {
			supplierThin = list.get(position);
		}
		Intent localIntent = new Intent();
		localIntent.putExtra("suppler", supplierThin);
		setResult(RESULT_OK, localIntent);
		finish();

	}

	@Override
	public void onChanged(View paramView, String paramString) {
		temp.clear();
		list.clear();
		if (paramString.length() == 0) {
			loadData();
		}
		String[] arrayOfString2 = null;
		if (TextUtils.isEmptyS(paramString)) {
			this.temp.addAll(customers);
			for (int k = 0; k < temp.size(); k++) {

				String localString = "";
				int i = 0;
				String[] arrayOfString1 = null;
				if (i < this.customers.size()) {
					arrayOfString1 = Utils.CUSTOMER_CHECK_SELECT.split(",");
				}
				for (int j = 0; j < arrayOfString1.length; j++) {
					if (arrayOfString1[j].equals("id")) {
						localString = (customers.get(k)).getId();
					}
					if (arrayOfString1[j].equals("name")) {
						localString = (customers.get(k)).getName();
					} else if (arrayOfString1[j].equals("pinyin")) {
						localString = (customers.get(k)).getPinyin();
					}
					boolean contains = localString.contains(paramString);
					boolean contains2 = (localString.toLowerCase()).contains(paramString);
					if (contains && contains2) {
						list.add(customers.get(k));
					}
				}
			}
			arrayOfString2 = new String[list.size()];
			if (list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {
					arrayOfString2[i] = list.get(i).getName();
				}
			}
			ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
					arrayOfString2);
			lvSuppler.setAdapter(localArrayAdapter);
			localArrayAdapter.notifyDataSetChanged();

		}

	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("供应商");
	}
}
