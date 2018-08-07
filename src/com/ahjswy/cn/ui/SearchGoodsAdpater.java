package com.ahjswy.cn.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class SearchGoodsAdpater extends BaseAdapter implements Filterable {
	private Context context;
	private ArrayFilter filter;
	private ArrayList<GoodsThin> goods;
	private final Object mLock = new Object();
	SparseBooleanArray selected = new SparseBooleanArray();
	private ArrayList<GoodsThin> tempGoods;

	public SearchGoodsAdpater(Context paramContext) {
		this.context = paramContext;
	}

	public int getCount() {
		if (this.tempGoods == null) {
			return 0;
		}
		return this.tempGoods.size();
	}

	public Object getItem(int paramInt) {
		return this.tempGoods.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
	}

	// 获取已经选择 的 item
	public List<GoodsThin> getSelect() {
		ArrayList<GoodsThin> localArrayList = new ArrayList<GoodsThin>();
		if (selected.size() <= 0) {
			return localArrayList;
		}
		for (int i = 0; i < selected.size(); i++) {
			int j = this.selected.keyAt(i);
			if (this.selected.get(j)) {
				localArrayList.add(tempGoods.get(j));
			}
		}
		return localArrayList;
	}

	public List<GoodsThin> getTempGoods() {
		return this.tempGoods;
	}

	public View getView(int position, View convertView, ViewGroup paren) {
		localItem = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.auto_add_shop_item, null);
			localItem = new Item();
			localItem.textView = ((TextView) convertView.findViewById(R.id.tv_addShop));
			localItem.checkBox = ((CheckBox) convertView.findViewById(R.id.cb_add_open));
			convertView.setTag(localItem);
		} else {
			localItem = (Item) convertView.getTag();
		}
		localItem.textView.setText(tempGoods.get(position).getName());
		localItem.checkBox.setOnCheckedChangeListener(this.changeListener);
		localItem.checkBox.setTag(Integer.valueOf(position));
		localItem.checkBox.setChecked(selected.get(position));
		return convertView;
	}

	// 商品 查找 过滤 adapter
	private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean) {
			int i = Integer.parseInt(paramAnonymousCompoundButton.getTag().toString());
			if (paramAnonymousBoolean) {
				selected.put(i, true);
				return;
			}
			selected.put(i, false);
		}
	};

	public void setGoods(ArrayList<GoodsThin> goods) {
		this.goods = goods;
	}

	public void setIsLoaded(boolean paramBoolean) {
		if (this.filter == null) {
			this.filter = new ArrayFilter();
		}
		this.filter.setIsLoaded(paramBoolean);
	}

	public void setIsUseFull(boolean paramBoolean) {
		this.filter.setIsUseFull(paramBoolean);
	}

	public void setTempGoods(ArrayList<GoodsThin> paramList) {
		this.tempGoods = paramList;
		this.selected.clear();
	}

	private Item localItem;
	String localString1;

	// 过滤器 对 SQL 查询出来 的 数据 进行过滤
	@Override
	public Filter getFilter() {
		if (this.filter == null)
			this.filter = new ArrayFilter();
		return this.filter;
	}

	private class ArrayFilter extends Filter {
		private boolean isLoaded = true;
		private boolean isUseFull = true;

		private ArrayFilter() {
			
		}

		protected Filter.FilterResults performFiltering(CharSequence paramCharSequence) {
			ArrayList<GoodsThin> localArrayList = null;
			Filter.FilterResults localFilterResults = new Filter.FilterResults();
			if ((paramCharSequence == null) || (paramCharSequence.length() == 0)) {
				synchronized (SearchGoodsAdpater.this.mLock) {
					if (SearchGoodsAdpater.this.goods == null) {
						localFilterResults.count = 0;
						return localFilterResults;
					} else {
						localFilterResults.values = SearchGoodsAdpater.this.goods;
						localFilterResults.count = SearchGoodsAdpater.this.goods.size();
					}
				}
			} else {
				localString1 = paramCharSequence.toString();
			}
			do {
				try {
					Thread.sleep(5L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!this.isLoaded);
			if (isUseFull) {
				localArrayList = SearchGoodsAdpater.this.goods;
			} else {
				localArrayList = SearchGoodsAdpater.this.tempGoods;
			}
			String[] arrayOfString;

			ArrayList<GoodsThin> arrayList = new ArrayList<GoodsThin>();
			System.out.println("i>>>" + localArrayList.size());
			for (int i = 0; i < localArrayList.size(); i++) {
				String localString2 = null;
				arrayOfString = Utils.GOODS_CHECK_SELECT.split(",");
				for (int k = 0; k < arrayOfString.length; k++) {
					if (arrayOfString[k].equals("pinyin")) {
						localString2 = ((GoodsThin) localArrayList.get(i)).getPinyin();
					} else if (arrayOfString[k].equals("name")) {
						localString2 = ((GoodsThin) localArrayList.get(i)).getName();
					} else if (arrayOfString[k].equals("id")) {
						localString2 = ((GoodsThin) localArrayList.get(i)).getId();
					} else if (arrayOfString[k].equals("barcode")) {
						localString2 = ((GoodsThin) localArrayList.get(i)).getBarcode();
					}
					if (TextUtils.isEmptyS(localString2)
							&& (localString2.toLowerCase(Locale.CHINA).contains(localString1))) {
						arrayList.add(localArrayList.get(i));
						break;
					}
				}
			}
			localFilterResults.values = arrayList;
			localFilterResults.count = arrayList.size();
			return localFilterResults;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults) {
			selected.clear();
			tempGoods = (ArrayList<GoodsThin>) paramFilterResults.values;
			if (paramFilterResults.count > 0) {
				notifyDataSetChanged();
				return;
			}
			notifyDataSetInvalidated();
		}

		public void setIsLoaded(boolean isLoaded) {
			this.isLoaded = isLoaded;
		}

		public void setIsUseFull(boolean isUseFull) {
			this.isUseFull = isUseFull;
		}
	}

	private class Item {
		public CheckBox checkBox;
		public TextView textView;

		private Item() {
		}
	}
}