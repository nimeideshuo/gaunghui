package com.ahjswy.cn.ui.ingoods;

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
import android.view.WindowManager;
import android.view.View.OnFocusChangeListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InDocPayAdapter extends BaseAdapter {
	private InDocPayAct context;
	private List<DefDocPayType> payTypes;
	private boolean isReadOnly;

	public InDocPayAdapter(InDocPayAct context, Boolean isreadonly) {
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

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.act_outdoc_pay_item, null);
			holder.tvPaytypeName = (TextView) convertView.findViewById(R.id.tvPaytypeName);
			holder.etPayAmount = (EditTextWithDel) convertView.findViewById(R.id.etPayAmount);
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		// 保存位置
		holder.etPayAmount.setTag(Integer.valueOf(position));
		DefDocPayType defDocPayType = payTypes.get(position);
		holder.tvPaytypeName.setText(defDocPayType.getPaytypename());
		// 初始支付方式
		holder.etPayAmount.setText(defDocPayType.getAmount() + "");
		holder.etPayAmount.setClean(new Clean() {

			@Override
			public void clean() {
				// 获取位置
				int i = ((Integer) holder.etPayAmount.getTag()).intValue();
				payTypes.get(i).setAmount(0.0);
				context.setPrice();
				return;
			}
		});
		// 右边的图标隐藏
		// holder.etPayAmount.setCleanDrawable(false);
		holder.etPayAmount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				int i = ((Integer) holder.etPayAmount.getTag()).intValue();
				payTypes.get(i).setAmount(Utils.getDouble(s.toString()).doubleValue());
				context.setPrice();
			}
		});

		holder.etPayAmount.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {

					if (context.getWindow()
							.getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
						// 隐藏软键盘
						context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
					} else {
						context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
					}
				}
			}
		});
		return convertView;
	}

	public class ViewHolder {
		private TextView tvPaytypeName;
		private EditTextWithDel etPayAmount;
	}
}
