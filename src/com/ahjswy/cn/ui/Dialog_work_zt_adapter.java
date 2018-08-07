package com.ahjswy.cn.ui;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.response.AccountSetEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Dialog_work_zt_adapter extends BaseAdapter {
	Context context;
	private List<AccountSetEntity> listText;

	public Dialog_work_zt_adapter(Context context) {
		this.context = context;
		if (listText == null) {
			listText = new ArrayList<AccountSetEntity>();
		}
	}

	@Override
	public int getCount() {
		if (listText != null) {
			return listText.size();
		}
		return 0;
	}

	public void setDate(List<AccountSetEntity> listText) {
		this.listText = listText;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.dialog_work_item_text, null);
			holder = new ViewHolder();
			holder.textview = (TextView) convertView.findViewById(R.id.tv_work_zt_item);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		convertView.setBackgroundResource(R.drawable.selector);
		AccountSetEntity account = listText.get(position);
		holder.textview.setText(account.getName());
		return convertView;
	}

	class ViewHolder {
		TextView textview;
	}

}
