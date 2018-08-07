package com.ahjswy.cn.ui.outgoods;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.utils.TextUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsWarehouseSearchAdapter extends BaseAdapter {
	private Context context;
	private boolean isShowStockNum = false;
	private List<RespGoodsWarehouse> items;

	public GoodsWarehouseSearchAdapter(Context context) {
		this.context = context;

		if (!"1".equals(new AccountPreference().getValue("ViewKCStockBrowse", "0"))) {
			isShowStockNum = false;
		} else {
			isShowStockNum = true;
		}
	}

	public int getCount() {
		if (this.items == null) {
			return 0;
		}
		return this.items.size();
	}

	public List<RespGoodsWarehouse> getData() {
		return this.items;
	}

	public Object getItem(int paramInt) {
		return this.items.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		Holder localHolder = null;
		if (paramView == null) {
			paramView = LayoutInflater.from(this.context).inflate(R.layout.item_goodsbatchsearch, null);
			localHolder = new Holder(paramView);
			paramView.setTag(localHolder);
		} else {
			localHolder = (Holder) paramView.getTag();
		}
		localHolder.setValue(items.get(paramInt));
		return paramView;
	}

	public void setData(List<RespGoodsWarehouse> paramList) {
		this.items = paramList;
		notifyDataSetChanged();
	}

	public class Holder {
		public TextView tvStockNum;
		public TextView tvWarehouse;

		public Holder(View arg2) {
			this.tvWarehouse = ((TextView) arg2.findViewById(R.id.tvBatch));
			this.tvStockNum = ((TextView) arg2.findViewById(R.id.tvStockNum));
		}

		public void setValue(RespGoodsWarehouse rgWarehouse) {
			this.tvWarehouse.setText(rgWarehouse.getWarehousename());

			if (!TextUtils.isEmptyS(rgWarehouse.getBigstocknum())) {
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
