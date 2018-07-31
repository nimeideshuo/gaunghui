package com.ahjswy.cn.ui.transfer;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItem;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.utils.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TransferAddMoreAdapter extends BaseAdapter {
	private List<DefDocItem> listItems;
	TransferAddMoreGoodsAct context;

	public TransferAddMoreAdapter(TransferAddMoreGoodsAct context) {
		this.context = context;
		listItems = new ArrayList<DefDocItem>();

	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	public List<DefDocItem> getData() {
		return this.listItems;
	}

	@Override
	public DefDocItem getItem(int position) {
		return this.listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	int selectPosition;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Item item = null;
		// if (convertView == null) {
		// convertView =
		// LayoutInflater.from(this.context).inflate(R.layout.item_inventory_add_more_goods,
		// null);
		// item = new Item(convertView);
		// convertView.setTag(item);
		// } else {
		// item = (Item) convertView.getTag();
		// }
		// item.btnUnit.setTag(Integer.valueOf(position));
		// item.etNum.setTag(Integer.valueOf(position));
		// item.btnUnit.setOnClickListener(this.onClickListener);
		// item.etNum.addTextChangedListener(new NumWatcher(item));
		// item.setValue(this.listItems.get(position));
		View view = View.inflate(context, R.layout.item_inventory_add_more_goods, null);
		TextView tvName = ((TextView) view.findViewById(R.id.tvName));
		TextView tvBarcode = ((TextView) view.findViewById(R.id.tvBarcode));
		Button btnUnit = ((Button) view.findViewById(R.id.btnUnit));
		EditText etNum = ((EditText) view.findViewById(R.id.etNum));
		btnUnit.setTag(Integer.valueOf(position));
		etNum.setTag(Integer.valueOf(position));
		DefDocItem item = listItems.get(position);
		btnUnit.setOnClickListener(this.onClickListener);
		tvName.setText(item.getGoodsname());
		tvBarcode.setText(item.getBarcode());
		btnUnit.setText(item.getUnitname());
		if (item.getNum() == 0.0D) {
			etNum.setText("");
		} else {
			etNum.setText(String.valueOf(item.getNum()));
		}
		if (selectPosition == position) {
			etNum.requestFocus();
		}
		etNum.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				selectPosition = position;
			}
		});
		etNum.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				(listItems.get(position)).setNum(Utils.normalize(Utils.getDouble(s.toString()).doubleValue(), 2));
			}
		});

		return view;
	}

	public void setData(List<DefDocItem> items) {
		this.listItems.clear();
		this.listItems.addAll(items);
		notifyDataSetChanged();
	}

	public class Item {
		private Button btnUnit;
		private EditText etNum;
		private TextView tvBarcode;
		private TextView tvName;

		public Item(View view) {
			this.tvName = ((TextView) view.findViewById(R.id.tvName));
			this.tvBarcode = ((TextView) view.findViewById(R.id.tvBarcode));
			this.btnUnit = ((Button) view.findViewById(R.id.btnUnit));
			this.etNum = ((EditText) view.findViewById(R.id.etNum));
		}

		public void setValue(DefDocItem item) {
			this.tvName.setText(item.getGoodsname());
			this.tvBarcode.setText(item.getBarcode());
			this.btnUnit.setText(item.getUnitname());
			if (item.getNum() == 0.0D) {
				etNum.setText("");
			} else {
				etNum.setText(String.valueOf(item.getNum()));
			}

		}
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(final View v) {
			final int j = ((Integer) v.getTag()).intValue();
			final List<GoodsUnit> localList = new GoodsUnitDAO().queryGoodsUnits(listItems.get(j).getGoodsid());
			final String[] items = new String[localList.size()];
			for (int i = 0; i < items.length; i++) {
				items[i] = localList.get(i).getUnitname();

			}
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("单位选择");
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					((Button) v).setText(items[which]);
					DefDocItem docItem = listItems.get(j);
					docItem.setUnitid(localList.get(which).getUnitid());
					docItem.setUnitname(localList.get(which).getUnitname());
				}
			});
			builder.create().show();
		}
	};

	// private class NumWatcher implements TextWatcher {
	// private TransferAddMoreAdapter.Item item;
	//
	// public NumWatcher(Item item) {
	// this.item = item;
	// }
	//
	// @Override
	// public void beforeTextChanged(CharSequence s, int start, int count, int
	// after) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onTextChanged(CharSequence s, int start, int before, int
	// count) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void afterTextChanged(Editable s) {
	// int i = ((Integer) item.etNum.getTag()).intValue();
	// (listItems.get(i)).setNum(Utils.normalize(Utils.getDouble(s.toString()).doubleValue(),
	// 2));
	// }
	//
	// }

}
