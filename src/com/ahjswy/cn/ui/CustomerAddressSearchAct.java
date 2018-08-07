package com.ahjswy.cn.ui;

import java.util.HashMap;
import java.util.List;

import com.ahjswy.cn.utils.JSONUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CustomerAddressSearchAct extends BaseActivity {
	private String customername;
	private List<HashMap<String, String>> listAddress;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listview = new ListView(this);
		setContentView(listview);
		this.listAddress = JSONUtil.parse2ListMap(getIntent().getStringExtra("listaddress"));
		this.customername = getIntent().getStringExtra("customername");
		String[] arrayOfString = new String[listAddress.size()];
		for (int i = 0; i < listAddress.size(); i++) {
			arrayOfString[i] = listAddress.get(i).get("address");
		}
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				arrayOfString);
		this.listview.setAdapter(localArrayAdapter);
		listview.setOnItemClickListener(onItemClickListener);
	}

	AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent localIntent = new Intent();
			localIntent.putExtra("address", (listAddress.get(position)).get("address"));
			setResult(RESULT_OK, localIntent);
			finish();
		}
	};

	public void setActionBarText() {
		getActionBar().setTitle("配送地址-【" + this.customername + "】");
	}

}
