package com.ahjswy.cn.views;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.ui.SearchGoodsAdpater;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Dialog_listCheckBox extends Dialog implements android.view.View.OnClickListener {
	Context context;
	private ListView listview;
	private SearchGoodsAdpater adapter;
	private Button btn_ensure;
	private Button btn_cancel;

	public Dialog_listCheckBox(Context context, int theme) {
		super(context, R.style.MyDialog_NoTitle);
		this.context = context;
		init();
	}

	public Dialog_listCheckBox(Context context) {
		super(context, R.style.MyDialog_NoTitle);
		this.context = context;
		init();
	}

	private void init() {
		setContentView(R.layout.dialog_listcheckbox);
		adapter = new SearchGoodsAdpater(context);
		listview = (ListView) findViewById(R.id.listview);
		btn_ensure = (Button) findViewById(R.id.btn_ensure);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
	}

	public void setGoods(ArrayList<GoodsThin> goods) {
		adapter.setGoods(goods);
	}

	public void setTempGoods(ArrayList<GoodsThin> paramList) {
		adapter.setTempGoods(paramList);
	}

	public List<GoodsThin> getSelect() {
		return adapter.getSelect();
	}

	public void ensure(android.view.View.OnClickListener onclicklistener) {
		btn_ensure.setOnClickListener(onclicklistener);
	}

	public void ShowMe() {
		listview.setAdapter(adapter);
		show();
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

}
