package com.ahjswy.cn.ui.transfer;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.response.RespStrDocThinEntity;
import com.ahjswy.cn.utils.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransferRecordAdapter extends BaseAdapter {
	Context context;
	private List<RespStrDocThinEntity> listItems;

	public TransferRecordAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return listItems == null ? 0 : listItems.size();
	}

	@Override
	public RespStrDocThinEntity getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listItems.get(position).getDocid();
	}

	public List<RespStrDocThinEntity> getListItems() {
		return this.listItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RemoteLocalItem item;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_transfer_record, null);
			item = new RemoteLocalItem(convertView);
			convertView.setTag(item);
		} else {
			item = (RemoteLocalItem) convertView.getTag();
		}
		item.tvId.setText(String.valueOf(position + 1));
		item.setValue(listItems.get(position));
		return convertView;
	}

	public void setData(List<RespStrDocThinEntity> listItems) {
		this.listItems = listItems;
		notifyDataSetChanged();
	}

	public class RemoteLocalItem {
		public TextView tvBuilder;
		public TextView tvDate;
		public TextView tvId;
		public TextView tvInWarehouse;
		public TextView tvShowId;
		public TextView tvStatus;

		public RemoteLocalItem(View paramView) {
			this.tvId = ((TextView) paramView.findViewById(R.id.tvId));
			this.tvShowId = ((TextView) paramView.findViewById(R.id.tvShowId));
			this.tvBuilder = ((TextView) paramView.findViewById(R.id.tvBuilder));
			this.tvDate = ((TextView) paramView.findViewById(R.id.tvDate));
			this.tvInWarehouse = ((TextView) paramView.findViewById(R.id.tvInWarehouse));
			this.tvStatus = ((TextView) paramView.findViewById(R.id.tvStatus));
		}

		public void setValue(RespStrDocThinEntity paramRespStrDocThinEntity) {
			this.tvShowId.setText(paramRespStrDocThinEntity.getShowid());
			this.tvBuilder.setText(paramRespStrDocThinEntity.getBuildername());
			this.tvDate.setText(Utils.formatDate(paramRespStrDocThinEntity.getBuildtime(), "yyyy-MM-dd HH:mm"));
			this.tvInWarehouse.setText("调入仓库：" + paramRespStrDocThinEntity.getWarehousename());
			if ((paramRespStrDocThinEntity.getIsavailable()) && (paramRespStrDocThinEntity.getIsposted())) {
				this.tvStatus.setText("已过账");
				return;
			}
			this.tvStatus.setText("未过账");
		}

	}

}
