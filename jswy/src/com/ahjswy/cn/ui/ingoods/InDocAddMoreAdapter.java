package com.ahjswy.cn.ui.ingoods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.utils.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InDocAddMoreAdapter extends BaseAdapter {
	// private Calendar cal;
	private Context context;
	private List<DefDocItemXS> listItems;
	private int selectPosition = -1;// 记住选中的edtitext

	public InDocAddMoreAdapter(Context context) {
		this.context = context;
		if (this.listItems == null) {
			this.listItems = new ArrayList<DefDocItemXS>();
		}
		// cal = Calendar.getInstance();
	}

	@Override
	public int getCount() {
		return this.listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return this.listItems.get(position);
	}

	public List<DefDocItemXS> getData() {
		return this.listItems;
	}

	// 添加一个
	// public void addData(DefDocItemXS item) {
	// listItems.add(item);
	// }

	// public void addItemDate(ArrayList<DefDocItemXS> listItem) {
	// listItems.addAll(listItem);
	// notifyDataSetChanged();
	// }

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_indoc_add_more_goods, null);
		TextView tvName = ((TextView) convertView.findViewById(R.id.tvName));
		Button btnUnit = ((Button) convertView.findViewById(R.id.btnUnit));
		final EditText etNum = ((EditText) convertView.findViewById(R.id.etNum));
		DefDocItemXS itemXS = listItems.get(position);
		etNum.setFocusable(true);
		etNum.setFilterTouchesWhenObscured(true);
		if (selectPosition == position) {
			etNum.requestFocus();
		}
		etNum.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				selectPosition = position;
			}
		});
		btnUnit.setTag(Integer.valueOf(position));
		etNum.setTag(Integer.valueOf(position));
		btnUnit.setOnClickListener(onClickListener);
		etNum.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int i = ((Integer) etNum.getTag()).intValue();
				listItems.get(i).setNum(Utils.normalize(Utils.getDouble(s.toString()).doubleValue(), 2));
			}
		});
		tvName.setText(itemXS.getGoodsname());
		btnUnit.setText(itemXS.getUnitname());
		etNum.setText(itemXS.getNum() == 0 ? "" : itemXS.getNum() + "");
		return convertView;
	}

	public void setData(List<DefDocItemXS> listItems) {
		this.listItems.clear();
		this.listItems.addAll(listItems);
	}

	// 设置单位
	private View.OnClickListener onClickListener = new View.OnClickListener() {
		public void onClick(final View paramAnonymousView) {
			final int i = ((Integer) paramAnonymousView.getTag()).intValue();
			final List<GoodsUnit> localList = new GoodsUnitDAO().queryGoodsUnits(listItems.get(i).getGoodsid());
			final String[] arrayOfString = new String[localList.size()];
			for (int j = 0; j < localList.size(); j++) {
				arrayOfString[j] = (localList.get(j)).getUnitname();
			}
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(InDocAddMoreAdapter.this.context);
			localBuilder.setTitle("单位选择");
			localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
					((Button) paramAnonymousView).setText(arrayOfString[paramAnonymous2Int]);
					DefDocItemXS localDefDocItem = (DefDocItemXS) InDocAddMoreAdapter.this.listItems.get(i);
					localDefDocItem.setUnitid(((GoodsUnit) localList.get(paramAnonymous2Int)).getUnitid());
					localDefDocItem.setUnitname(((GoodsUnit) localList.get(paramAnonymous2Int)).getUnitname());
				}
			});
			localBuilder.create().show();
			return;
		}
	};

}
