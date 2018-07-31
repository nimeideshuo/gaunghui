package com.ahjswy.cn.ui;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.CustomerDAO;
import com.ahjswy.cn.model.CustomerThin;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.AutoTextViews;
import com.ahjswy.cn.views.AutoTextViews.OnTextChangeListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CustomerSearchAct extends BaseActivity implements OnTextChangeListener, OnItemClickListener {

	private AutoTextViews etSearch;
	private ListView listview;
	private List<CustomerThin> customers;
	List<CustomerThin> temp;
	private ArrayList<CustomerThin> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_search);
		initView();
		loadData();
	}

	private void initView() {
		etSearch = ((AutoTextViews) findViewById(R.id.etSearch));
		etSearch.setVisibility(0);
		listview = ((ListView) findViewById(R.id.listview));
		this.etSearch.setOnTextChangeListener(this);
		this.listview.setOnItemClickListener(this);
	}

	private void loadData() {
		list = new ArrayList<CustomerThin>();
		temp = new ArrayList<CustomerThin>();
		customers = new CustomerDAO().queryAllCustomer();

		this.temp.addAll(this.customers);

		if ((this.customers == null) || (this.customers.size() == 0)) {
			return;
		}
		String[] arrayOfString = new String[customers.size()];
		for (int i = 0; i < customers.size(); i++) {
			arrayOfString[i] = ((CustomerThin) this.customers.get(i)).getName();
		}
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(CustomerSearchAct.this,
				android.R.layout.simple_list_item_1, arrayOfString);
		listview.setAdapter(localArrayAdapter);
	}

	@Override
	public void onChanged(View paramView, String paramString) {
		temp.clear();
		list.clear();
		if (paramString.length() == 0) {
			loadData();
		}
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
					} else if (arrayOfString1[j].equals("name")) {
						localString = (customers.get(k)).getName();
					} else if (arrayOfString1[j].equals("pinyin")) {
						localString = (customers.get(k)).getPinyin();
					}
					boolean contains = localString.contains(paramString);
					boolean contains2 = (localString.toLowerCase()).contains(paramString);
					if (contains && contains2) {
						list.add(customers.get(k));
						break;
					}
				}
			}
			String[] arrayOfString2 = new String[list.size()];
			if (list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {
					arrayOfString2[i] = list.get(i).getName();
				}
			}
			ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(CustomerSearchAct.this,
					android.R.layout.simple_list_item_1, arrayOfString2);
			listview.setAdapter(localArrayAdapter);
			localArrayAdapter.notifyDataSetChanged();

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		CustomerThin customerThin = null;
		if (list.size() == 0) {
			customerThin = temp.get(position);
		} else {
			customerThin = list.get(position);
		}
		Intent localIntent = new Intent();
		localIntent.putExtra("customer", customerThin);
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
		getActionBar().setTitle("客户");
	}
}
