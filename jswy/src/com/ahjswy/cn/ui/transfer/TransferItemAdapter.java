package com.ahjswy.cn.ui.transfer;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocItem;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransferItemAdapter extends BaseAdapter {
	private Context context;
	private List<DefDocItem> items;

	public TransferItemAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return items == null ? 0 : items.size();
	}

	public List<DefDocItem> getData() {
		return this.items;
	}

	public void setData(List<DefDocItem> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	@Override
	public DefDocItem getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getItemid();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TransferGoodsItem item = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_transfer_goods, null);
			item = new TransferGoodsItem(convertView);
			convertView.setTag(item);
		} else {
			item = (TransferGoodsItem) convertView.getTag();
		}
		item.tvSerialid.setText(String.valueOf(position + 1));
		item.setValue(items.get(position));
		return convertView;
	}

	public class TransferGoodsItem {
		public TextView tvBarcode;
		public TextView tvBatch;
		public TextView tvName;
		public TextView tvNum;
		public TextView tvSerialid;
		public TextView tvWarehouse;

		public TransferGoodsItem(View v) {
			this.tvSerialid = ((TextView) v.findViewById(R.id.tvSerialid));
			this.tvName = ((TextView) v.findViewById(R.id.tvGoodsName));
			this.tvBarcode = ((TextView) v.findViewById(R.id.tvBarcode));
			this.tvNum = ((TextView) v.findViewById(R.id.tvNum));
			this.tvWarehouse = ((TextView) v.findViewById(R.id.tvWarehouse));
			this.tvBatch = ((TextView) v.findViewById(R.id.tvBatch));
		}

		public void setValue(DefDocItem item) {
			this.tvName.setText(item.getGoodsname());
			this.tvNum.setText(Utils.getNumber(item.getNum()) + item.getUnitname());
			if (TextUtils.isEmptyS(item.getBarcode())) {
				this.tvBarcode.setText(item.getBarcode());
			} else {
				this.tvBarcode.setText(null);
			}
			if (TextUtils.isEmptyS(item.getWarehousename())) {
				this.tvWarehouse.setText(item.getWarehousename());
			} else {
				this.tvWarehouse.setText("无仓库");
			}
			if (TextUtils.isEmptyS(item.getBatch())) {
				this.tvBatch.setText(item.getBatch());
			} else {
				this.tvBatch.setText(null);
			}
		}
	}
}
