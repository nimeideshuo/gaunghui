package com.ahjswy.cn.ui.ingoods;

import java.util.ArrayList;
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
import android.widget.Button;
import android.widget.TextView;

public class InDocItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<DefDocItemXS> items;
	ArrayList<Long> listItemDelete;

	public InDocItemAdapter(Context context) {
		this.context = context;
		if (items == null) {
			this.items = new ArrayList<DefDocItemXS>();
		}
		if (listItemDelete == null) {
			listItemDelete = new ArrayList<Long>();
		}
	}

	@Override
	public int getCount() {
		if (this.items == null) {
			return 0;
		}
		return this.items.size();
	}

	public List<DefDocItemXS> getData() {
		return this.items;
	}

	public void setData(List<DefDocItemXS> item) {
		this.items.clear();
		this.items.addAll(item);
		notifyDataSetChanged();
	}

	public void remove(DefDocItemXS DefDocItem) {
		this.items.remove(DefDocItem);
		notifyDataSetChanged();
	}

	public void addItem(DefDocItemXS DefDocItem) {
		this.items.add(DefDocItem);
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
	}

	public ArrayList<Long> getListItemDelete() {
		return listItemDelete;
	}

	@Override
	public long getItemId(int position) {
		return (items.get(position)).getItemid();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_indoc_goods, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (sum != null) {
			sum.sum();
		}
		holder.setValue(items.get(position));
		holder.tvSerialid.setText(position + 1 + "");
		return convertView;
	}

	public class Holder {
		// 条码
		public TextView tvBarcode;
		// 批次
		public TextView tvBatch;
		// 折扣
		public TextView tvDiscountRatio;
		// 折扣单价
		public TextView tvDiscountSubtotal;
		// 商品名称
		public TextView tvName;
		// 商品数量
		public TextView tvNum;
		// 商品单价
		public TextView tvPrice;
		// 数列号码
		public TextView tvSerialid;
		// 仓库
		public TextView tvWarehouse;
		// 滑动布局
		public Button copy;
		public Button dele;

		public Holder(View view) {
			tvSerialid = ((TextView) view.findViewById(R.id.tvSerialid));
			tvName = (TextView) view.findViewById(R.id.tvName);
			tvBarcode = ((TextView) view.findViewById(R.id.tvBarcode));
			tvWarehouse = ((TextView) view.findViewById(R.id.tvWarehouse));
			tvBatch = ((TextView) view.findViewById(R.id.tvBatch));
			tvNum = ((TextView) view.findViewById(R.id.tvNum));
			tvPrice = ((TextView) view.findViewById(R.id.tvPrice));
			tvDiscountRatio = ((TextView) view.findViewById(R.id.tvDiscountRatio));
			tvDiscountSubtotal = ((TextView) view.findViewById(R.id.tvDiscountSubtotal));
		}

		public void setValue(DefDocItemXS paramDefDocItem) {
			this.tvName.setText(paramDefDocItem.getGoodsname());
			if (TextUtils.isEmptyS(paramDefDocItem.getBarcode())) {
				this.tvBarcode.setText(paramDefDocItem.getBarcode());
			} else {
				this.tvBarcode.setText(null);
			}
			// 代码待补全
			this.tvNum.setText(Utils.getNumber(paramDefDocItem.getNum()) + paramDefDocItem.getUnitname());
			// TODO bigNum
			// paramDefDocItem.setBignum(paramDefDocItem.getNum() +
			// paramDefDocItem.getUnitname());
			// paramDefDocItem.setBignum(Utils.cutLastZero(paramDefDocItem.getNum()
			// + ""));
			this.tvPrice.setText(Utils.getPrice(paramDefDocItem.getPrice()) + "元/" + paramDefDocItem.getUnitname());
			this.tvDiscountRatio.setText(Utils.getNumber(paramDefDocItem.getDiscountratio()));
			this.tvDiscountSubtotal.setText(Utils.getSubtotalMoney(paramDefDocItem.getDiscountsubtotal()) + "元");
			if (TextUtils.isEmptyS(paramDefDocItem.getWarehousename())) {
				this.tvWarehouse.setText(paramDefDocItem.getWarehousename());
			} else {
				this.tvWarehouse.setText("无仓库");
			}
			if (TextUtils.isEmptyS(paramDefDocItem.getBatch())) {
				this.tvBatch.setText(paramDefDocItem.getBatch());
			} else {
				this.tvBatch.setText("");
			}

		}
	}

	private Sum sum;

	public void setSum(Sum sum) {
		this.sum = sum;
	}

	public interface Sum {
		public void sum();
	}
}
