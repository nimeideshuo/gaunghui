package com.ahjswy.cn.ui.out_in_goods;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.bean.DefDocItemDD;
import com.ahjswy.cn.utils.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 销售订单 Adapter
 * 
 * @author Administrator
 *
 */
public class OutInDocAdaprer extends BaseAdapter {
	List<DefDocItemDD> items;
	
	
	@Override
	public int getCount() {
		return items.size();
	}

	public void setItems(List<DefDocItemDD> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	public List<DefDocItemDD> getItems() {
		return items;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {// item_indoc_goods //item_outdoc_goods灰色
			convertView = LayoutInflater.from(Utils.getContext()).inflate(R.layout.item_outdoc_goods, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setValue(items.get(position));
		holder.tvSerialid.setText((position + 1) + "");
		return convertView;
	}

	public class ViewHolder {
		// 【赠】
		public TextView tvIsGive;
		// 名称
		public TextView tvName;
		// 条码
		public TextView tvBarcode;
		// 单价
		public TextView tvPrice;
		// 仓库
		public TextView tvWarehouse;

		// 折扣比例
		public TextView tvDiscountRatio;
		// 数量
		public TextView tvNum;
		// 折扣小计
		public TextView tvDiscountSubtotal;
		// 序列号
		public TextView tvSerialid;
		// 单价
		public LinearLayout ll_Price;

		public ViewHolder(View v) {
			tvSerialid = (TextView) v.findViewById(R.id.tvSerialid);
			tvIsGive = (TextView) v.findViewById(R.id.tvIsGive);
			ll_Price = (LinearLayout) v.findViewById(R.id.ll_Price);
			tvName = (TextView) v.findViewById(R.id.tvGoodsName);
			tvBarcode = (TextView) v.findViewById(R.id.tvBarcode);
			tvPrice = (TextView) v.findViewById(R.id.tvPrice);
			tvWarehouse = (TextView) v.findViewById(R.id.tvWarehouse);
			tvDiscountRatio = (TextView) v.findViewById(R.id.tvDiscountRatio);
			tvDiscountSubtotal = (TextView) v.findViewById(R.id.tvDiscountSubtotal);
			tvNum = (TextView) v.findViewById(R.id.tvNum);
		}

		public void setValue(DefDocItemDD items) {
			// 【赠】不显示 单价 如果是赠品 Isgift true
			if ((items.isIsgift())) {
				this.tvIsGive.setVisibility(0);
				this.ll_Price.setVisibility(8);
			} else {
				this.tvIsGive.setVisibility(8);
				this.ll_Price.setVisibility(0);
			}
			// 商品名字
			tvName.setText(items.getGoodsname());
			tvBarcode.setText(items.getBarcode());
			// 设置数量
			this.tvNum.setText(Utils.getNumber(items.getNum()) + items.getUnitname());
			// 设置单价
			this.tvPrice.setText(Utils.getPrice(items.getPrice()) + "元/" + items.getUnitname());
			// 折扣比
			this.tvDiscountRatio.setText(Utils.getNumber(items.getDiscountratio()));
			// 总额
			this.tvDiscountSubtotal.setText(Utils.getSubtotalMoney(items.getDiscountsubtotal()) + "元");
		}

	}

}
