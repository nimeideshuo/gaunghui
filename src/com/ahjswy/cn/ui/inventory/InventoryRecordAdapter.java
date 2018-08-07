package com.ahjswy.cn.ui.inventory;

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

public class InventoryRecordAdapter extends BaseAdapter {
	Context context;
	private List<RespStrDocThinEntity> listItems;

	public InventoryRecordAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if (listItems == null) {
			return 0;
		}
		return listItems.size();

	}

	@Override
	public Object getItem(int position) {
		if (this.listItems == null) {
			return null;
		}
		return this.listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<RespStrDocThinEntity> getListItems() {
		return this.listItems;
	}

	public void setData(List<RespStrDocThinEntity> listItems) {
		this.listItems = listItems;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RemoteLocalItem paramViewGroup;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_inventory_record, null);
			paramViewGroup = new RemoteLocalItem(convertView);
			convertView.setTag(paramViewGroup);
		} else {
			paramViewGroup = (RemoteLocalItem) convertView.getTag();
		}
		paramViewGroup.tvId.setText(String.valueOf(position + 1));
		paramViewGroup.setValue(listItems.get(position));
		return convertView;
	}

	public class RemoteLocalItem {
		public TextView tvBuilder;
		public TextView tvDate;
		public TextView tvId;
		public TextView tvShowId;
		public TextView tvStatus;
		public TextView tvWarehouse;

		public RemoteLocalItem(View v) {
			this.tvId = ((TextView) v.findViewById(R.id.tvId));
			this.tvShowId = ((TextView) v.findViewById(R.id.tvShowId));
			this.tvBuilder = ((TextView) v.findViewById(R.id.tvBuilder));
			this.tvDate = ((TextView) v.findViewById(R.id.tvDate));
			this.tvWarehouse = ((TextView) v.findViewById(R.id.tvWarehouse));
			this.tvStatus = ((TextView) v.findViewById(R.id.tvStatus));
		}

		public void setValue(RespStrDocThinEntity docThinEntity) {
			this.tvShowId.setText(docThinEntity.getShowid());
			this.tvBuilder.setText(docThinEntity.getBuildername());
			this.tvDate.setText(Utils.formatDate(docThinEntity.getBuildtime(), "yyyy-MM-dd HH:mm"));
			this.tvWarehouse.setText("仓库：" + docThinEntity.getWarehousename());
			if ((docThinEntity.getIsavailable()) && (docThinEntity.getIsposted())) {
				this.tvStatus.setText("已审核");
				return;
			}
			this.tvStatus.setText("未审核");
		}
	}

}
