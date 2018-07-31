package com.ahjswy.cn.ui.inpurchase;

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

public class InpurPayAdapter extends BaseAdapter {
	Context context;
	private List<DefDocPayType> payTypes;

	public InpurPayAdapter(Context context) {
		this.context = context;
		// if (payTypes == null) {
		// payTypes = new ArrayList<Data>();
		// }
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
	public DefDocPayType getItem(int position) {
		return this.payTypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.act_outdoc_pay_item, null);
		TextView textView = (TextView) convertView.findViewById(R.id.tvPaytypeName);
		EditTextWithDel edittext = (EditTextWithDel) convertView.findViewById(R.id.etPayAmount);
		DefDocPayType item = getItem(position);
		textView.setText(item.getPaytypename());
		edittext.setText(item.getAmount() + "");
		edittext.addTextChangedListener(new TextWatcher() {

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

	Amount amount;

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public interface Amount {
		public void AmountSetPrice();
	}

}
