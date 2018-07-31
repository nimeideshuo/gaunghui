package com.ahjswy.cn.ui.outgoods;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.EditTextWithDel;
import com.ahjswy.cn.views.EditTextWithDel.Clean;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OutDocPayAdapter extends BaseAdapter {
	private OutDocPayAct context;
	private List<DefDocPayType> payTypes;
	private boolean isReadOnly;

	public OutDocPayAdapter(OutDocPayAct context, Boolean isreadonly) {
		this.context = context;
		this.isReadOnly = isreadonly;
	}

	public int getCount() {
		if (this.payTypes != null) {
			return payTypes.size();
		}
		return 0;
	}

	public List<DefDocPayType> getData() {
		return this.payTypes;
	}

	public void setData(List<DefDocPayType> paramList) {
		this.payTypes = paramList;
		notifyDataSetChanged();
	}

	public Object getItem(int paramInt) {
		return this.payTypes.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.act_outdoc_pay_item, null);
		TextView tvPaytypeName = (TextView) convertView.findViewById(R.id.tvPaytypeName);
		final EditTextWithDel etPayAmount = (EditTextWithDel) convertView.findViewById(R.id.etPayAmount);
		if (!isReadOnly) {
			etPayAmount.setCursorVisible(false);
			etPayAmount.setFocusable(false);
			etPayAmount.setFocusableInTouchMode(false);
		} else {
			etPayAmount.setClean(new Clean() {

				@Override
				public void clean() {
					// 获取位置
					int i = ((Integer) etPayAmount.getTag()).intValue();
					payTypes.get(i).setAmount(0);
					context.setPrice();
				}
			});

			// 右边的图标隐藏
			etPayAmount.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					int i = ((Integer) etPayAmount.getTag()).intValue();
					payTypes.get(i).setAmount(Utils.getDouble(s.toString()).doubleValue());
					context.setPrice();
				}
			});

		}
		// 保存位置
		etPayAmount.setTag(Integer.valueOf(position));
		DefDocPayType defDocPayType = payTypes.get(position);
		tvPaytypeName.setText(defDocPayType.getPaytypename());
		// 初始支付方式
		etPayAmount.setText(defDocPayType.getAmount() + "");

		// etPayAmount.setOnFocusChangeListener(new OnFocusChangeListener() {
		//
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		// if (hasFocus) {
		//
		// if (context.getWindow()
		// .getAttributes().softInputMode ==
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
		// // 隐藏软键盘
		// context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// } else {
		// context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
		// }
		// }
		// int i = ((Integer) etPayAmount.getTag()).intValue();
		// etPayAmount.setCleanDrawable(hasFocus);
		// }
		// });
		return convertView;
	}

	public class ViewHolder {
	}
}