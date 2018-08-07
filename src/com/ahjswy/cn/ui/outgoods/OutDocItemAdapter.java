package com.ahjswy.cn.ui.outgoods;

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
import android.widget.LinearLayout;
import android.widget.TextView;

public class OutDocItemAdapter extends BaseAdapter {
	Context mContext;
	List<DefDocItemXS> items;
	ArrayList<Long> listItemDelete;

	public OutDocItemAdapter(Context context) {
		this.mContext = context;
		if (listItemDelete == null) {
			listItemDelete = new ArrayList<Long>();
		}
		if (items == null) {
			items = new ArrayList<DefDocItemXS>();
		}
	}

	public void addItem(DefDocItemXS paramDefDocItemXS) {
		this.items.add(paramDefDocItemXS);
		notifyDataSetChanged();
	}

	public void addItem(int paramInt, DefDocItemXS paramDefDocItemXS) {
		this.items.add(paramInt, paramDefDocItemXS);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public List<DefDocItemXS> getData() {
		return this.items;
	}

	public void setData(List<DefDocItemXS> items) {
		this.items.clear();
		this.items.addAll(items);
	}

	public ArrayList<Long> getListItemDelete() {
		return listItemDelete;
	}

	@Override
	public DefDocItemXS getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return ((DefDocItemXS) this.items.get(position)).getItemid();
	}

	// public void remove(DefDocItemXS paramDefDocItemXS) {
	// this.items.remove(paramDefDocItemXS);
	// notifyDataSetChanged();
	// }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_outdoc_goods, null);
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
//		// 【促】
//		public TextView tvIsPromotion;
//		// 【搭】
//		public TextView tvIsPromotionGive;
//		// 【列】
//		public TextView tvIsExhibition;
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

		// 批次
		public TextView tvBatch;
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
		// 滑动布局
		public Button copy;
		public Button dele;

		public ViewHolder(View v) {
			tvSerialid = (TextView) v.findViewById(R.id.tvSerialid);
			ll_Price = (LinearLayout) v.findViewById(R.id.ll_Price);
			// 促销
//			tvIsPromotion = (TextView) v.findViewById(R.id.tvIsPromotion);
//			tvIsPromotionGive = (TextView) v.findViewById(R.id.tvIsPromotionGive);
//			tvIsExhibition = (TextView) v.findViewById(R.id.tvIsExhibition);
			tvIsGive = (TextView) v.findViewById(R.id.tvIsGive);

			tvName = (TextView) v.findViewById(R.id.tvGoodsName);
			tvBarcode = (TextView) v.findViewById(R.id.tvBarcode);
			tvPrice = (TextView) v.findViewById(R.id.tvPrice);
			tvWarehouse = (TextView) v.findViewById(R.id.tvWarehouse);
			tvBatch = (TextView) v.findViewById(R.id.tvBatch);
			tvDiscountRatio = (TextView) v.findViewById(R.id.tvDiscountRatio);
			tvDiscountSubtotal = (TextView) v.findViewById(R.id.tvDiscountSubtotal);
			tvNum = (TextView) v.findViewById(R.id.tvNum);
		}

		public void setValue(DefDocItemXS paramDefDocItemXS) {
			if (paramDefDocItemXS.isIspromotion()) {
			}
//			if ((paramDefDocItemXS.getPromotiontype() == 0)
//					|| ((paramDefDocItemXS.getPromotiontype() == 1) && (paramDefDocItemXS.getParentitemid() <= 0L))) {
//				// 【促】 不显示 搭
//				this.tvIsPromotion.setVisibility(0);
//				this.tvIsPromotionGive.setVisibility(8);
//			}
			// 【赠】不显示 单价 如果是赠品 Isgift true
			if ((paramDefDocItemXS.isIsgift()) || (paramDefDocItemXS.isIspromotion())) {
				this.tvIsGive.setVisibility(0);
				this.ll_Price.setVisibility(8);
			} else {
				this.tvIsGive.setVisibility(8);
				this.ll_Price.setVisibility(0);
			}
			// 【列】 不显示 【赠】
//			if (paramDefDocItemXS.isIsexhibition()) {
//				this.tvIsExhibition.setVisibility(0);
//				this.tvIsGive.setVisibility(8);
//			}
			// 商品名字
			tvName.setText(paramDefDocItemXS.getGoodsname());
			// 商品 的条码
			if (TextUtils.isEmptyS(paramDefDocItemXS.getBarcode())) {
				this.tvBarcode.setText(paramDefDocItemXS.getBarcode());
			}
			// 设置数量
			this.tvNum.setText(Utils.getNumber(paramDefDocItemXS.getNum()) + paramDefDocItemXS.getUnitname());
			// 设置单价
			this.tvPrice.setText(Utils.getPrice(paramDefDocItemXS.getPrice()) + "元/" + paramDefDocItemXS.getUnitname());
			// 折扣比
			this.tvDiscountRatio.setText(Utils.getNumber(paramDefDocItemXS.getDiscountratio()));
			// 折扣小计
			this.tvDiscountSubtotal.setText(Utils.getSubtotalMoney(paramDefDocItemXS.getDiscountsubtotal()) + "元");
			// 仓库
			if (TextUtils.isEmptyS(paramDefDocItemXS.getWarehousename())) {
				this.tvWarehouse.setText(paramDefDocItemXS.getWarehousename());
			} else {
				this.tvWarehouse.setText("无仓库");
			}
			// 批次
			if (TextUtils.isEmptyS(paramDefDocItemXS.getBatch())) {
				this.tvBatch.setText(paramDefDocItemXS.getBatch());
			}
		}
	}

}
