package com.ahjswy.cn.ui.out_in_goods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.bean.DefDocItemDD;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.request.ReqStrGetGoodsPrice;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;

import android.app.Activity;
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

/*
 * 销售采购订单添加商品
 */
public class OutInDocAddMoreAdapter extends BaseAdapter {
	List<DefDocItemDD> items;
	private int selectPosition = -1;// 记住选中的edtitext
	private Activity context;
	Def_Doc doc;

	public OutInDocAddMoreAdapter(Activity context) {
		this.context = context;
		if (items == null) {
			items = new ArrayList<DefDocItemDD>();
		}
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<DefDocItemDD> getData() {
		return items;
	}

	public void setData(List<DefDocItemDD> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	public void addData(DefDocItemDD item) {
		items.add(item);
	}

	public void setDoc(Def_Doc doc) {
		this.doc = doc;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_outin_more, null);
		TextView tvName = ((TextView) convertView.findViewById(R.id.tvName));
		// TextView tvBarcode = ((TextView)
		// convertView.findViewById(R.id.tvBarcode));
		Button btnUnit = ((Button) convertView.findViewById(R.id.btnUnit));
		final EditText etNum = ((EditText) convertView.findViewById(R.id.etNum));
		// final EditText etBatch = ((EditText)
		// convertView.findViewById(R.id.etBatch));
		final TextView tv_dicPrice = (TextView) convertView.findViewById(R.id.tv_dicPrice);
		final TextView tv_Bfci = (TextView) convertView.findViewById(R.id.tv_Bfci);
		final DefDocItemDD itemDD = items.get(position);
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

		btnUnit.setTag(position);
		etNum.setTag(Integer.valueOf(position));
		// etBatch.setTag(Integer.valueOf(position));
		tv_dicPrice.setText("单价: " + itemDD.getPrice() + " 元");
		tv_Bfci.setText("   /" + itemDD.showStock);
		tvName.setText(items.get(position).getGoodsname());
		// tvBarcode.setText(items.get(position).getBarcode());
		btnUnit.setText(items.get(position).getUnitname());
		if ((items.get(position).getNum() + "".length()) > 0 && items.get(position).getNum() > 0.0D) {
			String num = Utils.removeZero(items.get(position).getNum() + "");
			etNum.setText(num);
		}
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
				if (s.toString().length() > 0 && Double.parseDouble(s.toString()) > 0.0D) {
					(items.get(i)).setNum(Utils.normalize(Utils.getDouble(s.toString()).doubleValue(), 2));
				}
			}
		});
		btnUnit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final int i = ((Integer) v.getTag()).intValue();
				final List<GoodsUnit> localList = new GoodsUnitDAO().queryGoodsUnits(itemDD.getGoodsid());
				final String[] arrayOfString = new String[localList.size()];
				for (int j = 0; j < localList.size(); j++) {
					arrayOfString[j] = (localList.get(j)).getUnitname();
				}
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
				localBuilder.setTitle("单位选择");
				localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						((Button) v).setText(arrayOfString[which]);
						GoodsUnit goodsUnit = localList.get(which);
						final DefDocItemDD item = (DefDocItemDD) items.get(i);
						item.setUnitid(goodsUnit.getUnitid());
						item.setUnitname(goodsUnit.getUnitname());
						// ====查询库存======
						String stocknum = DocUtils.Stocknum(Integer.parseInt(item.stocknum), goodsUnit);
						item.showStock = stocknum;
						tv_Bfci.setText(" /" + stocknum);
						// TODO 单价转换
						PDH.show(context, new PDH.ProgressCallBack() {

							@Override
							public void action() {
								if (doc == null) {
									return;
								}
								final ReqStrGetGoodsPrice goodsPrice = DocUtils.GetMultiGoodsPrice(doc.getCustomerid(),
										doc.getWarehouseid(), item.getGoodsid(), item.getUnitid());
								if (goodsPrice == null) {
									return;
								}
								item.setPrice(goodsPrice.getPrice());
								context.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										tv_dicPrice.setText("单价:" + goodsPrice.getPrice() + "元");
									}
								});

							}
						});

					}
				});
				localBuilder.create().show();
			}
		});
		return convertView;
	}

	// 设置单位
	// private View.OnClickListener onClickListener = new View.OnClickListener()
	// {
	// public void onClick(final View v) {
	// final int i = ((Integer) v.getTag()).intValue();
	// final List<GoodsUnit> localList = new
	// GoodsUnitDAO().queryGoodsUnits(items.get(i).getGoodsid());
	// final String[] arrayOfString = new String[localList.size()];
	// for (int j = 0; j < localList.size(); j++) {
	// arrayOfString[j] = (localList.get(j)).getUnitname();
	// }
	// AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
	// localBuilder.setTitle("单位选择");
	// localBuilder.setItems(arrayOfString, new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int which) {
	// ((Button) v).setText(arrayOfString[which]);
	// DefDocItemDD localDefDocItem = (DefDocItemDD) items.get(i);
	// localDefDocItem.setUnitid(localList.get(which).getUnitid());
	// localDefDocItem.setUnitname(localList.get(which).getUnitname());
	// }
	// });
	// localBuilder.create().show();
	// return;
	// }
	// };

}
