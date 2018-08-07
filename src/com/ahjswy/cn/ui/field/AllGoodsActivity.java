package com.ahjswy.cn.ui.field;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.MLog;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.AutoTextViews;
import com.ahjswy.cn.views.AutoTextViews.OnTextChangeListener;
import com.ahjswy.cn.views.BounceListView;
import com.ahjswy.cn.views.MyLetterListView;
import com.ahjswy.cn.views.MyLetterListView.OnTouchingLetterChangedListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AllGoodsActivity extends BaseActivity implements OnItemClickListener {
	private static AllGoodsActivity activity;
	private AutoTextViews atvSearch;
	private OnTextChangeListener changeListener;
	private List<GoodsThin> listGoodsThin;
	private BounceListView bounceListView;// main goods detail
	private MyLetterListView myLetterListView;// right vertical
	private ProgressDialog progressDialog;
	private Toast toast;
	private TextView toastView;
	private AllGoodsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_field_all_goods);
		this.bounceListView = (BounceListView) this.findViewById(R.id.listView);
		this.myLetterListView = (MyLetterListView) this.findViewById(R.id.myLetterListView);
		this.myLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener(this, null));
		this.myLetterListView.setChooseChar("#");
		this.atvSearch = (AutoTextViews) this.findViewById(R.id.atvSearch);
		this.progressDialog = new ProgressDialog(this);
		this.progressDialog.setProgressStyle(1);
		this.progressDialog.setCancelable(false);
		this.loadData();
		this.initOverLay();
		activity = this;
		this.getWindow().setSoftInputMode(3);
		this.bounceListView.setOnItemClickListener(this);
		this.bounceListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				myLetterListView.setChooseChar(new StringBuilder(
						String.valueOf((listGoodsThin.get(bounceListView.getFirstVisiblePosition())).getPinyin()))
								.toString().substring(0, 1).toUpperCase(Locale.CHINA));
			}
		});

		this.changeListener = new OnTextChangeListener() {

			@Override
			public void onChanged(View v, String text) {
				if (TextUtils.isEmptyS(text)) {
					ArrayList<GoodsThin> listThin = new ArrayList<GoodsThin>();
					String[] goods_check = Utils.GOODS_CHECK_SELECT.split(",");
					for (int j = 0; j < listGoodsThin.size(); j++) {
						for (int i = 0; i < goods_check.length; i++) {
							String v4 = "";
							if (goods_check[i].equals("pinyin")) {
								v4 = listGoodsThin.get(j).getPinyin();
							} else if (goods_check[i].equals("name")) {
								v4 = listGoodsThin.get(j).getName();
							} else if (goods_check[i].equals("barcode")) {
								v4 = listGoodsThin.get(j).getBarcode();
							} else if (goods_check[i].equals("id")) {
								v4 = listGoodsThin.get(j).getId();
							}
							if (v4 == null) {
								continue;
							}
							if (v4.contains(text) && (v4.toLowerCase()).contains(text)) {
								listThin.add(listGoodsThin.get(j));
								break;
							}
						}
					}
					adapter.setDate(listThin);
				}

			}
		};
		atvSearch.setOnTextChangeListener(changeListener);
		scaner = Scaner.factory(getApplicationContext());
		scaner.setBarcodeListener(barcodeListener);
		// dialogPrice = new AlertDialog.Builder(AllGoodsActivity.this);
	}
	protected void onDestroy() {
		scaner.removeListener();
		scaner=null;
		super.onDestroy();
	};

	ScanerBarcodeListener barcodeListener = new ScanerBarcodeListener() {

		@Override
		public void setBarcode(String barcode) {
			readPriceDialog(barcode);
		}

	};

	private void readPriceDialog(String barcode) {
		ArrayList<GoodsThin> goodsThinList = new GoodsDAO().getGoodsThinList(barcode);
		if (goodsThinList.isEmpty()) {
			showError("没有找到商品！");
			return;
		}
		GoodsThin goodsThin = goodsThinList.get(0);
		Intent intent = new Intent(this, GoodDetailAct.class);
		intent.putExtra("goodsid", goodsThin.getId());
		intent.putExtra("showPrice", true);
		startActivity(intent);
	}

	private void initOverLay() {
		this.toast = new Toast(this);
		this.toastView = ((TextView) getLayoutInflater().inflate(R.layout.toast_overlay, null));
		this.toast.setView(toastView);
		this.toast.setGravity(17, 0, 0);
	}

	public static AllGoodsActivity getInstance() {
		if (activity != null) {
			return activity;
		}
		return null;
	}

	private void loadData() {
		PDH.show(this, "正在刷新...", new ProgressCallBack() {
			public void action() {
				listGoodsThin = new GoodsDAO().queryGoodsThin();
				handler.sendEmptyMessage(-1);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -1) {
				refresh();
			} else if (msg.what == 0) {
				adapter.notifyDataSetChanged();
			} else {
				MLog.d("select:" + msg.what);
				bounceListView.setSelection(msg.what);
			}
		};
	};
	// private AlertDialog.Builder dialogPrice;
	private Scaner scaner;

	private void refresh() {
		MLog.d("refresh:" + listGoodsThin.toString());
		this.adapter = new AllGoodsAdapter(this, listGoodsThin);
		this.bounceListView.setAdapter(adapter);
	}

	public void resetItem(int positon) {
		this.adapter.notifyDataSetChanged();
	}

	private void showOverLay(String text) {
		this.toastView.setText(text);
		this.toast.show();
	}

	class LetterListViewListener implements OnTouchingLetterChangedListener {
		AllGoodsActivity allGoodsActivity;

		public LetterListViewListener(AllGoodsActivity allGoodsActivity, Object object) {
			this.allGoodsActivity = allGoodsActivity;
		}

		public void onTouchingLetterChanged(String s) {
			String v1;
			atvSearch.setText("");
			adapter.setDate(listGoodsThin);
			int v0 = 0;
			if (Character.isLetter(s.toCharArray()[0])) {
				v0 = new GoodsDAO().queryGoodsIndexByPinyin(s);
			}
			bounceListView.setSelection(v0);
			if (Character.isLetter(s.toCharArray()[0])) {
				v1 = s.toUpperCase(Locale.CHINA);
			} else {
				v1 = "#";
			}
			allGoodsActivity.showOverLay(v1);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		GoodsThin goodsthin = (GoodsThin) parent.getItemAtPosition(position);
		Intent intent = new Intent(this, GoodDetailAct.class);
		intent.putExtra("goodsid", goodsthin.getId());
		startActivity(intent);
	}

	public void setActionBarText() {
		getActionBar().setTitle("产品手册");
	}
}
