package com.ahjswy.cn.ui;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsClassDAO;
import com.ahjswy.cn.model.GoodsClass;
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
 * 商品类别检索
 * 
 * @author Administrator
 *
 */
public class SearchGoodsClassAct extends BaseActivity implements OnTextChangeListener, OnItemClickListener {
	private AutoTextViews etSearch;
	private ListView listview;
	private List<GoodsClass> list;
	private List<GoodsClass> temp;
	private List<GoodsClass> listGoodsClass;
	private GoodsClassDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_searchact);
		initView();
		loadData();
	}

	private void initView() {
		etSearch = ((AutoTextViews) findViewById(R.id.etSearch));
		listview = ((ListView) findViewById(R.id.listview));
		this.etSearch.setOnTextChangeListener(this);
		this.listview.setOnItemClickListener(this);
		dao = new GoodsClassDAO();
	}

	private void loadData() {
		list = new ArrayList<GoodsClass>();
		temp = new ArrayList<GoodsClass>();
		listGoodsClass = dao.queryAll();
		if (listGoodsClass.isEmpty()) {
			return;
		}
		this.temp.addAll(listGoodsClass);
		String[] arrayGoods = new String[listGoodsClass.size()];
		for (int i = 0; i < listGoodsClass.size(); i++) {
			arrayGoods[i] = listGoodsClass.get(i).getName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayGoods);
		listview.setAdapter(adapter);
	}

	@Override
	public void onChanged(View v, String str) {
		temp.clear();
		list.clear();
		if (str.isEmpty()) {
			loadData();
		}
		if (TextUtils.isEmptyS(str)) {
			this.temp.addAll(listGoodsClass);
			for (int k = 0; k < temp.size(); k++) {

				String localString = "";
				int i = 0;
				String[] arrayOfString1 = null;
				if (i < listGoodsClass.size()) {
					arrayOfString1 = Utils.CUSTOMER_CHECK_SELECT.split(",");
				}
				for (int j = 0; j < arrayOfString1.length; j++) {
					if (arrayOfString1[j].equals("id")) {
						localString = (listGoodsClass.get(k)).getId();
					} else if (arrayOfString1[j].equals("name")) {
						localString = (listGoodsClass.get(k)).getName();
					} else if (arrayOfString1[j].equals("pinyin")) {
						localString = (listGoodsClass.get(k)).getPinyin();
					}
					boolean contains = localString.contains(str);
					boolean contains2 = (localString.toLowerCase()).contains(str);
					if (contains && contains2) {
						list.add(listGoodsClass.get(k));
					}
				}
			}
			String[] arrayOfString2 = new String[list.size()];
			if (list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {
					arrayOfString2[i] = list.get(i).getName();
				}
			}
			ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(SearchGoodsClassAct.this,
					android.R.layout.simple_list_item_1, arrayOfString2);
			listview.setAdapter(localArrayAdapter);
			localArrayAdapter.notifyDataSetChanged();

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		GoodsClass goodsClassThin = null;
		if (list.size() == 0) {
			goodsClassThin = temp.get(position);
		} else {
			goodsClassThin = list.get(position);
		}
		Intent localIntent = new Intent();
		localIntent.putExtra("goodclass", goodsClassThin);
		setResult(RESULT_OK, localIntent);
		finish();
	}
}
