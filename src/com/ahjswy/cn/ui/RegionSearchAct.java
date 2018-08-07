package com.ahjswy.cn.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.RegionDAO;
import com.ahjswy.cn.model.Region;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.AutoTextViews;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RegionSearchAct extends BaseActivity
		implements OnItemClickListener, com.ahjswy.cn.views.AutoTextViews.OnTextChangeListener {

	private ListView listview;
	private AutoTextViews etSearch;
	private List<Region> types;
	private List<Region> showGoods = new ArrayList<Region>();
	private List<Region> temp;
	private List<Region> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_search);
		listview = ((ListView) findViewById(R.id.listview));
		listview.setOnItemClickListener(this);
		etSearch = ((AutoTextViews) findViewById(R.id.etSearch));
		etSearch.setVisibility(0);
		etSearch.setOnTextChangeListener(this);
		etSearch.setHint("地区检索");
		types = new RegionDAO().getAllRegions();
		if ((types == null) || (types.size() == 0)) {
			finish();
			return;
		}
		showGoods = types;
		String[] arrayOfString = new String[types.size()];
		for (int i = 0; i < types.size(); i++) {
			arrayOfString[i] = types.get(i).getName();
		}
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(RegionSearchAct.this,
				android.R.layout.simple_list_item_1, arrayOfString);
		listview.setAdapter(localArrayAdapter);
		list = new ArrayList<Region>();
		loadData();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Region region = null;
		if (list.size() == 0) {
			region = temp.get(position);
		} else {
			region = list.get(position);
		}
		Intent localIntent = new Intent();
		localIntent.putExtra("region", (Serializable) region);
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
			this.temp.addAll(showGoods);

			for (int k = 0; k < temp.size(); k++) {

				String localString = "";
				int i = 0;
				String[] arrayOfString1 = null;
				if (i < showGoods.size()) {
					arrayOfString1 = Utils.CUSTOMER_CHECK_SELECT.split(",");
				}
				for (int j = 0; j < arrayOfString1.length; j++) {
					if (arrayOfString1[j].equals("id")) {
						localString = (showGoods.get(k)).getId();
					}
					if (arrayOfString1[j].equals("name")) {
						localString = (showGoods.get(k)).getName();
					} else if (arrayOfString1[j].equals("pinyin")) {
						localString = (showGoods.get(k)).getPinyin();
					}
					boolean contains = localString.contains(paramString);
					boolean contains2 = (localString.toLowerCase()).contains(paramString);
					if (contains && contains2) {
						list.add(showGoods.get(k));
					}
				}
			}
			arrayOfString2 = new String[list.size()];
			if (list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {
					arrayOfString2[i] = list.get(i).getName();
				}
			}
			ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(RegionSearchAct.this,
					android.R.layout.simple_list_item_1, arrayOfString2);
			listview.setAdapter(localArrayAdapter);
			localArrayAdapter.notifyDataSetChanged();

		}

	}

	private void loadData() {
		temp = new ArrayList<Region>();
		types = new RegionDAO().getAllRegions();

		this.temp.addAll(types);

		if ((types == null) || (types.size() == 0)) {
			return;
		}
		String[] arrayOfString = new String[types.size()];
		for (int i = 0; i < types.size(); i++) {
			arrayOfString[i] = types.get(i).getName();
		}
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(RegionSearchAct.this,
				android.R.layout.simple_list_item_1, arrayOfString);
		listview.setAdapter(localArrayAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	public void setActionBarText() {
		getActionBar().setTitle("地区");
	}

}
