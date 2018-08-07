package com.ahjswy.cn.ui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocItemPD;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InventoryItemAdapter extends BaseAdapter {
	private Context context;
	private List<DefDocItemPD> items;

	public InventoryItemAdapter(Context context) {
		this.context = context;
		if (items == null) {
			items = new ArrayList<DefDocItemPD>();
		}
	}

	public List<DefDocItemPD> getData() {
		return this.items;
	}

	@Override
	public int getCount() {
		return items == null ? 0 : this.items.size();
	}

	@Override
	public DefDocItemPD getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getItemid();
	}

	public void setData(List<DefDocItemPD> itemsList) {
		this.items.clear();
		this.items.addAll(itemsList);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		InventoryGoodsItem viewGroup;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_inventory_goods, null);
			viewGroup = new InventoryGoodsItem(convertView);
			convertView.setTag(viewGroup);
		} else {
			viewGroup = (InventoryGoodsItem) convertView.getTag();
		}
		viewGroup.setValue(items.get(position));
		viewGroup.tvSerialid.setText((position + 1) + "");
		return convertView;
	}

	public class InventoryGoodsItem {
		public LinearLayout llBatch;
		public TextView tvBarcode;
		public TextView tvBatch;
		public TextView tvName;
		public TextView tvNetNum;
		public TextView tvNum;
		public TextView tvSerialid;
		public TextView tvStockNum;

		public InventoryGoodsItem(View view) {
			this.tvSerialid = ((TextView) view.findViewById(R.id.tvSerialid));
			this.tvName = ((TextView) view.findViewById(R.id.tvGoodsName));
			this.tvBarcode = ((TextView) view.findViewById(R.id.tvBarcode));
			this.tvStockNum = ((TextView) view.findViewById(R.id.tvStockNum));
			this.tvNum = ((TextView) view.findViewById(R.id.tvNum));
			this.tvNetNum = ((TextView) view.findViewById(R.id.tvNetNum));
			this.tvBatch = ((TextView) view.findViewById(R.id.tvBatch));
			this.llBatch = ((LinearLayout) view.findViewById(R.id.llBatch));
		}

		public void setValue(DefDocItemPD item) {
			if (TextUtils.isEmptyS(item.getBarcode())) {
				this.tvBarcode.setText(item.getBarcode());
			} else {
				this.tvBarcode.setText(null);
			}
			if (TextUtils.isEmpty(item.getBatch())) {
				this.llBatch.setVisibility(View.GONE);
				this.tvBatch.setText(null);
			} else {
				this.tvBatch.setText(item.getBatch());
				this.llBatch.setVisibility(0);
			}
			this.tvName.setText(item.getGoodsname());
			this.tvStockNum.setText("账面：" + Utils.getNumber(item.getStocknum()) + item.getUnitname());
			this.tvNum.setText("盘点：" + Utils.getNumber(item.getNum()) + item.getUnitname());
			this.tvNetNum.setText("盈亏：" + Utils.getNumber(item.getNetnum()) + item.getUnitname());

		}

	}

}
