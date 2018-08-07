package com.ahjswy.cn.ui.outpurchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemTH;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.utils.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OutpurDocAddMoreAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<DefDocItemTH> listItems;
	// InpurDocAddMoreAdapter
	ArrayList<RespGoodsWarehouse> listStocknum;
	private int selectPosition = -1;// 记住选中的edtitext
	private List<String> listBigUnit;
	private GoodsUnitDAO dao;
	private RespGoodsWarehouse respGoodsWarehouse;

	private Map<Integer, String> map;

	public OutpurDocAddMoreAdapter(Context context) {
		this.context = context;
		if (listItems == null) {
			this.listItems = new ArrayList<DefDocItemTH>();
		}
		if (listStocknum == null) {
			listStocknum = new ArrayList<RespGoodsWarehouse>();
		}
		if (listBigUnit == null) {
			listBigUnit = new ArrayList<String>();
		}
		if (dao == null) {
			dao = new GoodsUnitDAO();
		}
		if (map == null) {
			map = new HashMap<Integer, String>();
		}
	}

	@Override
	public int getCount() {
		return this.listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return this.listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public List<DefDocItemTH> getData() {
		return this.listItems;
	}

	public void setData(List<DefDocItemTH> paramList) {
		this.listItems.clear();
		this.listItems.addAll(paramList);
		notifyDataSetChanged();
	}

	public void setlistStocknum(List<RespGoodsWarehouse> stocknum) {
		this.listStocknum.clear();
		this.listStocknum.addAll(stocknum);
		notifyDataSetChanged();

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// final Item localItem;
		convertView = LayoutInflater.from(context).inflate(R.layout.act_out_doc_add_moreadapter_item, null);
		// localItem = new Item(convertView);
		// 商品 name
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		// 二维码
		// TextView tvBarcode = (TextView)
		// convertView.findViewById(R.id.tvBarcode);
		// 数量
		final EditText etNum = (EditText) convertView.findViewById(R.id.etNum);
		// 单位
		Button btnUnit = (Button) convertView.findViewById(R.id.btnUnit);
		// 本次库存
		final TextView tv_Bfci = (TextView) convertView.findViewById(R.id.tv_Bfci);
		respGoodsWarehouse = listStocknum.get(position);
		if (map.get(position) == null) {
			if (Utils.DEFAULT_OutDocUNIT == 0) {
				tv_Bfci.setText(respGoodsWarehouse.getStocknum() + listItems.get(position).getUnitname());
			} else {
				tv_Bfci.setText(respGoodsWarehouse.getBigstocknum());
			}
		} else {
			tv_Bfci.setText(map.get(position));
		}
		// 显示上次 采购
		btnUnit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// 单位换算有问题 需要修改
				final GoodsUnitDAO goodsUnitDAO = new GoodsUnitDAO();
				final List<GoodsUnit> list_goodsunit = goodsUnitDAO
						.queryGoodsUnits((listItems.get(position)).getGoodsid());
				final String[] arrayOfString = new String[list_goodsunit.size()];
				for (int i = 0; i < arrayOfString.length; i++) {
					arrayOfString[i] = (list_goodsunit.get(i)).getUnitname();
				}
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
				localBuilder.setTitle("单位选择");
				localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int items) {
						// 设置单位
						((TextView) v).setText(arrayOfString[items].toString());
						// 保存 选择
						DefDocItemTH localDefDocItemTH = listItems.get(position);
						GoodsUnit goodsUnit = list_goodsunit.get(items);
						localDefDocItemTH.setUnitid(goodsUnit.getUnitid());
						localDefDocItemTH.setUnitname(goodsUnit.getUnitname());
						respGoodsWarehouse = listStocknum.get(position);
						String stocknum = "";
						GoodsUnit goodsRatio = dao.queryBigUnitRatio(goodsUnit.getGoodsid(), goodsUnit.getUnitid());
						GoodsUnit queryBaseUnit = dao.queryBaseUnit(goodsUnit.getGoodsid());
						int zs = (int) (respGoodsWarehouse.getStocknum() / goodsRatio.getRatio());
						double xs = Utils.normalizePrice(respGoodsWarehouse.getStocknum() % goodsRatio.getRatio());
						if (zs != 0) {
							stocknum = stocknum + zs + goodsUnit.getUnitname();
						}
						if (xs != 0) {
							stocknum = stocknum + xs + queryBaseUnit.getUnitname();
						}
						if (stocknum.length() == 0) {
							stocknum = "0";
						}
						tv_Bfci.setText(stocknum);
						map.put(position, stocknum);
					}
				});
				localBuilder.create().show();
			}

		});
		tvName.setText(listItems.get(position).getGoodsname());
		// tvBarcode.setText(listItems.get(position).getBarcode());
		btnUnit.setText(listItems.get(position).getUnitname());
		if (listItems.get(position).getNum() != 0.0d) {
			String num = Utils.removeZero(listItems.get(position).getNum() + "");
			etNum.setText(num);
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

		etNum.setTag(Integer.valueOf(position));
		btnUnit.setTag(Integer.valueOf(position));

		etNum.setFocusable(true);
		etNum.setFilterTouchesWhenObscured(true);
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
				if (s.toString().length() > 0) {
					if (Double.parseDouble(s.toString()) > 0.0D) {
						(listItems.get(i)).setNum(Utils.normalize(Utils.getDouble(s.toString()).doubleValue(), 2));
					}
				}
			}
		});
		return convertView;
	}

}
