package com.ahjswy.cn.ui.addgoods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsClassDAO;
import com.ahjswy.cn.model.GoodsClass;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.views.AutoTextViews;
import com.ahjswy.cn.views.AutoTextViews.OnTextChangeListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GoodsClassActivity extends BaseActivity implements OnTextChangeListener, OnItemClickListener {
	private AutoTextViews etSearch;
	private ListView listview;
	private List<GoodsClass> list;
	private List<GoodsClass> temp;
	private List<GoodsClass> listGoodsClass;
	private GoodsClassDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_goodsclass);
		initView();
		initData();
	}

	private void initView() {
		etSearch = ((AutoTextViews) findViewById(R.id.etSearch));
		listview = ((ListView) findViewById(R.id.listview));
		this.etSearch.setOnTextChangeListener(this);
		this.listview.setOnItemClickListener(this);
		dao = new GoodsClassDAO();
	}

	private void initData() {
		list = new ArrayList<GoodsClass>();
		temp = new ArrayList<GoodsClass>();
		listGoodsClass = dao.queryAll();
		if ((listGoodsClass == null) || (listGoodsClass.size() == 0)) {
			return;
		}
		temp.addAll(listGoodsClass);
		String[] arrayOfString = new String[listGoodsClass.size()];
		for (int i = 0; i < listGoodsClass.size(); i++) {
			arrayOfString[i] = listGoodsClass.get(i).getName();
		}
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(GoodsClassActivity.this,
				android.R.layout.simple_list_item_1, arrayOfString);
		listview.setAdapter(localArrayAdapter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		}
		return super.onOptionsItemSelected(menu);
	}

	@Override
	public void onChanged(View paramView, String paramString) {
		temp.clear();
		list.clear();
		if (paramString.length() == 0) {
			initData();
		}
		String[] arrayOfString1 = new String[] { "name", "pinyin" };
		String[] arrayOfString2 = null;
		if (TextUtils.isEmptyS(paramString)) {
			// 检索 调试
			temp.addAll(listGoodsClass);
			for (int i = 0; i < temp.size(); i++) {
				String localString = "";
				for (int j = 0; j < arrayOfString1.length; j++) {
					if (arrayOfString1[j].equals("name")) {
						localString = listGoodsClass.get(i).getName();
					} else if (arrayOfString1[j].equals("pinyin")) {
						localString = (listGoodsClass.get(i)).getPinyin();
					}
					boolean contains = localString.contains(paramString);
					boolean contains2 = (localString.toLowerCase()).contains(paramString);
					if (contains && contains2) {
						list.add(listGoodsClass.get(i));
					}
				}

			}
			arrayOfString2 = new String[list.size()];
			if (list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {
					arrayOfString2[i] = list.get(i).getName();
				}
			}

			ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(GoodsClassActivity.this,
					android.R.layout.simple_list_item_1, arrayOfString2);
			listview.setAdapter(localArrayAdapter);
			localArrayAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		GoodsClass goodsClass;
		if (list.size() == 0) {
			goodsClass = listGoodsClass.get(position);
		} else {
			goodsClass = list.get(position);
		}
		intent.putExtra("goodsClass", goodsClass);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult(RESULT_FIRST_USER);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void setActionBarText() {
		setTitle("商品类别");
	}
}
