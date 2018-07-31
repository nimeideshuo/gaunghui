package com.ahjswy.cn.ui.field;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.GoodsThin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AllGoodsAdapter extends BaseAdapter {
	private Context context;
	private List<GoodsThin> sourceData;

	public AllGoodsAdapter(Context context, List<GoodsThin> sourceData) {
		super();
		this.context = context;
		setDate(sourceData);
	}

	private String getAlpha(String str) {
		String v2;
		if (str == null) {
			v2 = "#";
		} else if (str.trim().length() == 0) {
			v2 = "#";
		} else {
			char v0 = str.trim().substring(0, 1).charAt(0);
			if (Pattern.compile("^[A-Za-z]+$").matcher(new StringBuilder(String.valueOf(v0)).toString()).matches()) {
				v2 = new StringBuilder(String.valueOf(v0)).toString().toUpperCase(Locale.CHINA);
			} else {
				v2 = "#";
			}
		}
		return v2;
	}

	@Override
	public int getCount() {
		if (this.sourceData == null) {
			return 0;
		}
		return this.sourceData.size();
	}

	public List<GoodsThin> getDate() {
		return this.sourceData;
	}

	@Override
	public GoodsThin getItem(int position) {
		return sourceData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String v2;
		Goods goods = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_field_all_goods, null);
			goods = new Goods(convertView);
			convertView.setTag(goods);
		} else {
			goods = (Goods) convertView.getTag();
		}
		goods.setValue(position, this.sourceData.get(position));
		String v0 = this.getAlpha(this.sourceData.get(position).getPinyin());
		if (position - 1 >= 0) {
			v2 = this.getAlpha(this.sourceData.get(position - 1).getPinyin());
		} else {
			v2 = " ";
		}
		if (!v2.equals(v0)) {
			goods.alpha.setVisibility(View.VISIBLE);
			goods.alpha.setText(v0);
			goods.alpha.setPadding(18, 0, 0, 0);
		} else {
			goods.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}

	private class Goods {
		private TextView alpha;
		private TextView tvGoodsBarcode;
		private TextView tvGoodsName;
		private TextView tvGoodsSpecifical;

		public Goods(View holder) {
			this.alpha = ((TextView) holder.findViewById(R.id.alpha));
			this.tvGoodsName = ((TextView) holder.findViewById(R.id.tvGoodsName));
			this.tvGoodsBarcode = ((TextView) holder.findViewById(R.id.tvGoodsBarcode));
			this.tvGoodsSpecifical = ((TextView) holder.findViewById(R.id.tvSpecifical));
		}

		public void setValue(int position, GoodsThin item) {
			this.tvGoodsName.setText(item.getName());
			this.tvGoodsBarcode.setText(item.getBarcode());
			this.tvGoodsSpecifical.setText(item.getSpecification());
		}
	}

	public void setDate(List<GoodsThin> sourceData) {
		this.sourceData = sourceData;
		this.notifyDataSetChanged();
	}
}
