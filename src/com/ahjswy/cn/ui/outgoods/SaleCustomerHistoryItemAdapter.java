package com.ahjswy.cn.ui.outgoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class SaleCustomerHistoryItemAdapter extends BaseAdapter {
	Context context;
	private List<DefDocItemXS> items;
	private boolean multichoice = false;
	private HashMap<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();

	public SaleCustomerHistoryItemAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		if (this.items != null) {
			return this.items.size();
		}
		return 0;
	}

	public List<DefDocItemXS> getData() {
		return this.items;
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int paramInt) {
		return items.get(paramInt).getItemid();
	}

	public List<DefDocItemXS> getSelectList() {
		ArrayList<DefDocItemXS> itemXS = new ArrayList<DefDocItemXS>();
		for (int i = 0; i < this.items.size(); ++i) {
			if (this.statusMap.get(i) != null && (this.statusMap.get(i))) {
				itemXS.add(items.get(i));
			}
		}
		return itemXS;
	}

	public void setData(List<DefDocItemXS> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	public void setMultiChoice(boolean multichoice) {
		this.multichoice = multichoice;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TransferGoodsItem goodsItem = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_salecustomerhistory_goods, null);
			goodsItem = new TransferGoodsItem(convertView);
			convertView.setTag(goodsItem);
		} else {
			goodsItem = (TransferGoodsItem) convertView.getTag();
		}
		if (statusMap.get(position) == null) {
			goodsItem.cbTV.setChecked(false);
		} else {
			goodsItem.cbTV.setChecked(statusMap.get(position));
		}
		goodsItem.setValue(items.get(position));
		goodsItem.tvSerialid.setText(position + 1 + "");

		return convertView;
	}

	public void setCheckePosition(int position) {
		if (this.statusMap.get(position) == null) {
			this.statusMap.put(position, true);
		} else {
			statusMap.put(position, !statusMap.get(position));
		}
		this.notifyDataSetChanged();
	}

	public class TransferGoodsItem {
		private CheckedTextView cbTV;
		public TextView tvBarcode;
		public TextView tvDiscountRatio;
		public TextView tvDiscountSubtotal;
		public TextView tvName;
		public TextView tvNum;
		public TextView tvPrice;
		public TextView tvSerialid;

		public TransferGoodsItem(View view) {
			this.tvSerialid = ((TextView) view.findViewById(R.id.tvSerialid));
			this.tvName = ((TextView) view.findViewById(R.id.tvName));
			this.tvBarcode = ((TextView) view.findViewById(R.id.tvBarcode));
			this.tvNum = ((TextView) view.findViewById(R.id.tvNum));
			this.tvPrice = ((TextView) view.findViewById(R.id.tvPrice));
			this.tvDiscountRatio = ((TextView) view.findViewById(R.id.tvDiscountRatio));
			this.tvDiscountSubtotal = ((TextView) view.findViewById(R.id.tvDiscountSubtotal));
			this.cbTV = ((CheckedTextView) view.findViewById(R.id.cbTV));
		}

		public void setValue(DefDocItemXS itemXS) {
			this.tvName.setText(itemXS.getGoodsname());
			if (TextUtils.isEmptyS(itemXS.getBarcode())) {
				this.tvBarcode.setText(itemXS.getBarcode());
			}
			this.tvNum.setText(Utils.getNumber(itemXS.getNum()) + itemXS.getUnitname());
			this.tvPrice.setText(Utils.getPrice(itemXS.getPrice()) + "元/" + itemXS.getUnitname());
			this.tvDiscountRatio.setText(Utils.getNumber(itemXS.getDiscountratio()));
			this.tvDiscountSubtotal.setText(Utils.getSubtotalMoney(itemXS.getDiscountsubtotal()) + "元");
			if (multichoice) {
				this.cbTV.setVisibility(View.VISIBLE);
			} else {
				this.cbTV.setVisibility(View.GONE);
				statusMap.clear();
			}

		}
	}
}
