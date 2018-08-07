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

public class SaleRecordAdapter extends BaseAdapter {
	private Context context;
	private List<RespStrDocThinEntity> listItems;

	public SaleRecordAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		if (this.listItems == null) {
			return 0;
		}
		return this.listItems.size();
	}

	public Object getItem(int paramInt) {
		if (this.listItems == null) {
			return null;
		}
		return this.listItems.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
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
		RemoteLocalItem vGroup = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_sale_record, null);
			vGroup = new RemoteLocalItem(convertView);
			convertView.setTag(vGroup);
		} else {
			vGroup = (RemoteLocalItem) convertView.getTag();
		}
		vGroup.tvId.setText(String.valueOf(position + 1));
		vGroup.setValue((RespStrDocThinEntity) this.listItems.get(position));
		return convertView;
	}

	public class RemoteLocalItem {
		public TextView tvBuilder;
		public TextView tvCustomer;
		public TextView tvDate;
		public TextView tvDocType;
		public TextView tvId;
		public TextView tvShowId;
		public TextView tvStatus;

		public RemoteLocalItem(View v) {
			this.tvId = (TextView) v.findViewById(R.id.tvId);
			this.tvShowId = (TextView) v.findViewById(R.id.tvShowId);
			this.tvDocType = (TextView) v.findViewById(R.id.tvDocType);
			this.tvBuilder = (TextView) v.findViewById(R.id.tvBuilder);
			this.tvDate = (TextView) v.findViewById(R.id.tvDate);
			this.tvCustomer = (TextView) v.findViewById(R.id.tvCustomer);
			this.tvStatus = (TextView) v.findViewById(R.id.tvStatus);
		}

		public void setValue(RespStrDocThinEntity respstrdocthinentity) {
			this.tvShowId.setText(respstrdocthinentity.getShowid());
			this.tvBuilder.setText(respstrdocthinentity.getBuildername());
			this.tvDate.setText(Utils.formatDate(respstrdocthinentity.getBuildtime(), "yyyy-MM-dd HH:mm"));
			this.tvCustomer.setText("客户：" + respstrdocthinentity.getCustomername());
			if ((!respstrdocthinentity.getIsavailable()) || (!respstrdocthinentity.getIsposted())) {
				this.tvStatus.setText("未过账");
			} else {
				this.tvStatus.setText("已过账");
			}
			if ("13".equals(respstrdocthinentity.getDoctypeid())) {
				this.tvDocType.setText("销售");
			} else if ("14".equals(respstrdocthinentity.getDoctypeid())) {
				this.tvDocType.setText("退货");
			} else if ("15".equals(respstrdocthinentity.getDoctypeid())) {
				this.tvDocType.setText("换货");
			}
		}

	}
}
