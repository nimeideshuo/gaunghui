package com.ahjswy.cn.ui.outpurchase;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.EditTextWithDel;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OutpurPayAdapter extends BaseAdapter {
	Context context;
	private List<DefDocPayType> payTypes;

	public OutpurPayAdapter(Context context) {
		this.context = context;
		if (payTypes == null) {
			payTypes = new ArrayList<DefDocPayType>();
		}
	}

	@Override
	public int getCount() {
		return payTypes.size();
	}

	public List<DefDocPayType> getData() {
		return this.payTypes;
	}

	public void setData(List<DefDocPayType> payTypes) {
		this.payTypes = payTypes;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return this.payTypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	ViewHolder holder = null;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.act_outdoc_pay_item, null);
			holder.init(convertView);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		holder.setValue(payTypes.get(position));
		holder.edittext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				payTypes.get(position).setAmount(Utils.getDouble(s.toString()).doubleValue());
				if (amount != null) {
					amount.AmountSetPrice();
				}
			}
		});
		return convertView;
	}

	public class ViewHolder {
		TextView textView;
		EditTextWithDel edittext;

		public ViewHolder() {
		}

		public void init(View v) {
			textView = (TextView) v.findViewById(R.id.tvPaytypeName);
			edittext = (EditTextWithDel) v.findViewById(R.id.etPayAmount);
		}

		public void setValue(DefDocPayType payType) {
			textView.setText(payType.getPaytypename());
			edittext.setText(payType.getAmount() + "");
		}
	}

	Amount amount;

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public interface Amount {
		public void AmountSetPrice();
	}

}
