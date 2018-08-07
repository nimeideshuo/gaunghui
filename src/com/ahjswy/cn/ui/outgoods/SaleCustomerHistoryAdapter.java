package com.ahjswy.cn.ui.outgoods;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.response.RespStrDocThinEntity;
import com.ahjswy.cn.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SaleCustomerHistoryAdapter extends BaseAdapter {
	Context context;
	private List<RespStrDocThinEntity> listItems;

	public SaleCustomerHistoryAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if (this.listItems != null) {
			return listItems.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (this.listItems != null) {
			return listItems.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public List<RespStrDocThinEntity> getListItems() {
		return this.listItems;
	}

	public void setData(List<RespStrDocThinEntity> paramList) {
		this.listItems = paramList;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RemoteLocalItem localItem;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_salecustomerhistory_record, null);
			localItem = new RemoteLocalItem(convertView);
			convertView.setTag(localItem);
		} else {
			localItem = (RemoteLocalItem) convertView.getTag();
		}
		localItem.tvId.setText(String.valueOf(position + 1));
		localItem.setValue(listItems.get(position));
		return convertView;
	}

	public class RemoteLocalItem {
		public TextView tvBuilder;
		public TextView tvDate;
		public TextView tvId;
		public TextView tvShowId;

		public RemoteLocalItem(View view) {
			this.tvId = ((TextView) view.findViewById(R.id.tvId));
			this.tvShowId = ((TextView) view.findViewById(R.id.tvShowId));
			this.tvBuilder = ((TextView) view.findViewById(R.id.tvBuilder));
			this.tvDate = ((TextView) view.findViewById(R.id.tvDate));
		}

		public void setValue(RespStrDocThinEntity respstrdocthinentity) {
			this.tvShowId.setText(respstrdocthinentity.getShowid());
			this.tvBuilder.setText(respstrdocthinentity.getBuildername());
			this.tvDate.setText(Utils.formatDate(respstrdocthinentity.getBuildtime(), "yyyy-MM-dd HH:mm"));
		}
	}
}
