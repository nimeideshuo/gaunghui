package com.ahjswy.cn.ui;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.response.RespGoodsBatchEntity;
import com.ahjswy.cn.utils.TextUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsBatchSearchAdapter extends BaseAdapter {
	private Context context;
	private boolean isShowStockNum = true;
	private List<RespGoodsBatchEntity> items;

	public GoodsBatchSearchAdapter(Context context) {
		this.context = context;
		if (!"1".equals(new AccountPreference().getValue("ViewKCStockBrowse", "0"))) {
			this.isShowStockNum = false;
		} else {
			this.isShowStockNum = true;
		}

	}

	@Override
	public int getCount() {
		if (this.items == null) {
			return 0;
		}
		return this.items.size();
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<RespGoodsBatchEntity> getData() {
		return this.items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder localHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_goodsbatchsearch, null);
			localHolder = new Holder(convertView);
			convertView.setTag(localHolder);
		}
		localHolder.setValue(items.get(position));
		localHolder = (Holder) convertView.getTag();
		return convertView;
	}

	public void setData(List<RespGoodsBatchEntity> paramList) {
		this.items = paramList;
		notifyDataSetChanged();
	}

	public class Holder {
		public TextView tvBatch;
		public TextView tvStockNum;

		public Holder(View view) {
			this.tvBatch = ((TextView) view.findViewById(R.id.tvBatch));
			this.tvStockNum = ((TextView) view.findViewById(R.id.tvStockNum));
		}

		public void setValue(RespGoodsBatchEntity rgWarehouse) {
			this.tvBatch.setText(rgWarehouse.getBatch());
			// 库存是否显示
			if (TextUtils.isEmpty(rgWarehouse.getBigstocknum())) {
				this.tvStockNum.setText("无库存");
				return;
			}
			this.tvStockNum.setText(rgWarehouse.getBigstocknum());
			if (isShowStockNum) {
				this.tvStockNum.setVisibility(View.VISIBLE);
			} else {
				this.tvStockNum.setVisibility(View.GONE);
			}

		}
	}
}
