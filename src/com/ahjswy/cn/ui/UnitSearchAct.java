package com.ahjswy.cn.ui;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.Unit;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.AutoTextViews;
import com.ahjswy.cn.views.AutoTextViews.OnTextChangeListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 单位检索
 * 
 * @author Administrator
 *
 */
public class UnitSearchAct extends BaseActivity implements OnTextChangeListener, OnItemClickListener {
	private AutoTextViews etSearch;
	private List<Unit> list;
	private List<Unit> temp;
	private List<Unit> listUnit;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_unitsearch);
		initView();
		loadData();
	}

	// 商品计量单位本地没有等待 更新
	private void initView() {
		etSearch = ((AutoTextViews) findViewById(R.id.etSearch));
		listview = ((ListView) findViewById(R.id.listview));
		etSearch.setOnTextChangeListener(this);
		listview.setOnItemClickListener(this);
	}

	private void loadData() {
		list = new ArrayList<Unit>();
		temp = new ArrayList<Unit>();
		listUnit = new GoodsUnitDAO().queryAll();

		temp.addAll(listUnit);

		if (listUnit.isEmpty()) {
			return;
		}
		String[] arrayUnit = new String[listUnit.size()];
		for (int i = 0; i < listUnit.size(); i++) {
			arrayUnit[i] = listUnit.get(i).getName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayUnit);
		listview.setAdapter(adapter);
	}

	@Override
	public void onChanged(View v, String str) {
		temp.clear();
		list.clear();
		if (str.length() == 0) {
			loadData();
		}
		if (TextUtils.isEmptyS(str)) {
			this.temp.addAll(listUnit);
			for (int k = 0; k < temp.size(); k++) {

				String localString = "";
				int i = 0;
				String[] arrayOfString1 = null;
				if (i < this.listUnit.size()) {
					arrayOfString1 = Utils.CUSTOMER_CHECK_SELECT.split(",");
				}
				for (int j = 0; j < arrayOfString1.length; j++) {
					if (arrayOfString1[j].equals("id")) {
						localString = (listUnit.get(k)).getId();
					} else if (arrayOfString1[j].equals("name")) {
						localString = (listUnit.get(k)).getName();
					}
					boolean contains = localString.contains(str);
					boolean contains2 = (localString.toLowerCase()).contains(str);
					if (contains && contains2) {
						list.add(listUnit.get(k));
					}

				}
			}
			String[] arrayOfString2 = new String[list.size()];
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					arrayOfString2[i] = list.get(i).getName();
				}
			}
			ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(UnitSearchAct.this,
					android.R.layout.simple_list_item_1, arrayOfString2);
			listview.setAdapter(localArrayAdapter);
			localArrayAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Unit Unit = null;
		if (list.size() == 0) {
			Unit = temp.get(position);
		} else {
			Unit = list.get(position);
		}
		Intent localIntent = new Intent();
		localIntent.putExtra("customer", Unit);
		setResult(RESULT_OK, localIntent);
		finish();
	}
}
