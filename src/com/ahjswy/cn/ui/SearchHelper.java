package com.ahjswy.cn.ui;

import java.util.ArrayList;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.in.ActionDo;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.views.AutoTextView;
import com.ahjswy.cn.views.AutoTextView.OnTextChangeListener;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;

public class SearchHelper implements View.OnClickListener, OnTextChangeListener {
	private static ArrayList<GoodsThin> listGoods;
	// private final int DOUBLE_CLICK = 2;
	private int LOADING = 1;
	private int SEARCH = 0;
	// private final int SIMPLE_CLICK = 1;
	private ActionDo actionDo;
	private SearchGoodsAdpater adapter;
	private AutoTextView autoTextView;
	private Button btnAdd;
	private Handler clickHandler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			// int i = SearchHelper.this.operation;
			// SearchHelper.this.operation = 0;
			// switch (i) {
			// default:
			// case 2:
			// case 1:
			// }
			// do {
			// SearchHelper.this.resetSearch();
			// } while (SearchHelper.this.autoTextView.isPopupShowing());
			// SearchHelper.this.autoTextView.showDropDown();
		}
	};
	private int delaytimes = 300;
	private Drawable[] drawables;
	private Activity mBaseGoodsAct;
	private View.OnClickListener onClickListener;
	private int operation = 0;
	private Handler searchGoodsHandler = new Handler() {
		public void handleMessage(Message message) {
			// 关闭进度条
			// setItemAnimShow(SearchHelper.this.SEARCH);
			if (SearchHelper.listGoods.size() == 0) {// 没查询到不显示，查询到显示
				PDH.showFail("未查到任何数据");
				autoTextView.dismissDropDown();
				return;
			}
			// 把值 放到 adapter 中
			adapter.setGoods(listGoods);
			adapter.setTempGoods(listGoods);
			adapter.setIsLoaded(true);
			autoTextView.showDropDown();
		}
	};

	public SearchHelper(Activity mBaseGoodsAct, View paramView) {
		this.mBaseGoodsAct = mBaseGoodsAct;
		autoTextView = ((AutoTextView) paramView.findViewById(R.id.atvSearch));
		btnAdd = ((Button) paramView.findViewById(R.id.btnAdd));
		listGoods = new ArrayList<GoodsThin>();
		this.autoTextView.setOnClickListener(this);
		this.autoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int position,
					long paramAnonymousLong) {
				if (actionDo != null) {
					actionDo.doAction(SearchHelper.this.adapter.getTempGoods().get(position));
					autoTextView.setText("");
				}
			}
		});
		this.autoTextView.setOnTextChangeListener(this);
		this.autoTextView.setReplace(false);
		adapter = new SearchGoodsAdpater(this.mBaseGoodsAct);
		this.autoTextView.setAdapter(adapter);
		this.btnAdd.setOnClickListener(this.onClickListener);
		this.drawables = new Drawable[2];
		this.drawables[0] = paramView.getResources().getDrawable(R.drawable.search);
		this.drawables[1] = paramView.getResources().getDrawable(R.drawable.loading);
	}

	private void searchGoods(final String paramString) {
		// 查找时候 左边 进度
		// setItemAnimShow(LOADING);
		new Thread() {
			public void run() {
				// 名字相同 Specification 有一个null 则为null
				listGoods = new GoodsDAO().queryGoods(paramString);
				searchGoodsHandler.sendEmptyMessage(0);
			}
		}.start();
	}

	// private void setItemAnimShow(int paramInt) {
	// autoTextView.setCompoundDrawablesWithIntrinsicBounds(drawables[paramInt],
	// null, null, null);
	// if (paramInt == this.LOADING) {
	// AnimationDrawable drawable = (AnimationDrawable)
	// autoTextView.getCompoundDrawables()[0];
	// if (drawable != null) {
	// drawable.stop();
	// drawable.start();
	// }
	// }
	// }

	public SearchGoodsAdpater getAdapter() {
		return this.adapter;
	}

	@Override
	public void onChanged(View paramView, String paramString) {
		if ((!TextUtils.isEmpty(paramString)) && (!TextUtils.isEmptyS(this.autoTextView.getBeforeTextChange()))
				&& (paramString.startsWith(this.autoTextView.getBeforeTextChange()))) {
			this.adapter.setIsUseFull(false);
		} else {
			this.adapter.setIsUseFull(true);
		}
		// 输入 为NULL
		if (TextUtils.isEmpty(paramString)) {
			System.out.println("clear");
			listGoods.clear();
			this.adapter.setGoods(null);
			this.adapter.setTempGoods(null);
			return;
		}
		if ((autoTextView.getBeforeTextChange().isEmpty())
				|| (!paramString.substring(0, 1).equals(this.autoTextView.getBeforeTextChange().substring(0, 1)))) {
			this.adapter.setIsLoaded(false);
			System.out.println("new search");
			searchGoods(paramString);
			return;
		}
		autoTextView.showDropDown();
	}

	public void onClick(View paramView) {
		this.operation = (1 + this.operation);
		if (this.operation > 1)
			return;
		this.clickHandler.sendEmptyMessageDelayed(0, this.delaytimes);
	}

	public void reset() {
		this.autoTextView.clearFocus();
		((InputMethodManager) this.mBaseGoodsAct.getSystemService("input_method"))
				.hideSoftInputFromWindow(this.autoTextView.getWindowToken(), 2);
	}

	public void resetSearch() {
		this.adapter.setGoods(null);
		this.autoTextView.setText("");
	}

	public void setActionDo(ActionDo actionDo) {
		this.actionDo = actionDo;
	}

	public void setOnAddListener(View.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
}