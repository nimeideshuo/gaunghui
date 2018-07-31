package com.ahjswy.cn.ui.outgoods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.DefDocXS;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.response.RespGoodsPriceEntity;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OutDocAddMoreAdapter extends BaseAdapter {
	private Context context;
	private List<DefDocItemXS> listItems;

	public OutDocAddMoreAdapter(Context context) {
		this.context = context;
		if (this.listItems == null) {
			this.listItems = new ArrayList<DefDocItemXS>();
		}
	}

	@Override
	public int getCount() {
		return this.listItems.size();
	}

	@Override
	public DefDocItemXS getItem(int position) {
		return this.listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public List<DefDocItemXS> getData() {
		return this.listItems;
	}

	public void setItem(List<DefDocItemXS> listItems) {
		this.listItems.clear();
		this.listItems.addAll(listItems);
	}

	public void addItem(DefDocItemXS item) {
		listItems.add(item);
	}

	public DefDocXS doc;

	public void setDoc(DefDocXS doc) {
		this.doc = doc;
	}

	// int selectPosition = -1;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Item item = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.act_out_doc_add_moreadapter_item, null);
			item = new Item(convertView);
			convertView.setTag(item);
		} else {
			item = (Item) convertView.getTag();
		}
		item.tv_dicPrice.setTag(Integer.valueOf(position));
		item.etNum.setTag(Integer.valueOf(position));
		item.btnUnit.setTag(Integer.valueOf(position));
		item.etNum.addTextChangedListener(new NumWatcher(item));
		item.btnUnit.setOnClickListener(unitOnClickListener);
		item.setValue(getItem(position));
		return convertView;
		// // 商品 name
		// convertView =
		// LayoutInflater.from(context).inflate(R.layout.act_out_doc_add_moreadapter_item,
		// null);
		// TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		// // 二维码
		// TextView tv_specification = (TextView)
		// convertView.findViewById(R.id.tv_specification);
		// // 数量
		// final EditText etNum = (EditText)
		// convertView.findViewById(R.id.etNum);
		// // 单位
		// Button btnUnit = (Button) convertView.findViewById(R.id.btnUnit);
		// // 本次库存
		// final TextView tv_Bfci = (TextView)
		// convertView.findViewById(R.id.tv_Bfci);
		// final TextView tv_dicPrice = (TextView)
		// convertView.findViewById(R.id.tv_dicPrice);
		// final TextView tvSumStock = (TextView)
		// convertView.findViewById(R.id.tvSumStock);
		// final DefDocItemXS itemXS = listItems.get(position);
		// // 显示上次 采购
		// tvName.setText(itemXS.getGoodsname());
		// tv_specification.setText(itemXS.getSpecification() == null ? "" :
		// "规格:" + itemXS.getSpecification());
		// btnUnit.setText(itemXS.getUnitname());
		// tv_dicPrice.setText("单价:" + itemXS.getPrice() + "元");
		// tv_dicPrice.setOnClickListener(priceOnClick);
		// tv_dicPrice.setTag(Integer.valueOf(position));
		// tv_Bfci.setText("当前库:" + (TextUtils.isEmpty(itemXS.goodStock) ? "?" :
		// itemXS.goodStock));
		// tvSumStock.setText("总库存:" + (TextUtils.isEmpty(itemXS.goodSumStock) ?
		// "?" : itemXS.goodSumStock));
		// etNum.setText(itemXS.getNum() == 0 ? "" : itemXS.getNum() + "");
		// etNum.setTag(Integer.valueOf(position));
		// btnUnit.setTag(Integer.valueOf(position));
		// etNum.setFocusable(true);
		// etNum.setFilterTouchesWhenObscured(true);
		// if (selectPosition == position) {
		// etNum.requestFocus();
		// }
		// etNum.setOnFocusChangeListener(new OnFocusChangeListener() {
		//
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		// selectPosition = position;
		// }
		// });
		// etNum.addTextChangedListener(new TextWatcher() {
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before, int
		// count) {
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// int i = ((Integer) etNum.getTag()).intValue();
		// listItems.get(i).setNum(Utils.normalize(Utils.getDouble(s.toString()).doubleValue(),
		// 2));
		// }
		// });
		// btnUnit.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(final View v) {
		// GoodsUnitDAO goodsUnitDAO = new GoodsUnitDAO();
		// final List<GoodsUnit> listGoodsunit =
		// goodsUnitDAO.queryGoodsUnits(itemXS.getGoodsid());
		// final String[] arrayOfString = new String[listGoodsunit.size()];
		// for (int i = 0; i < arrayOfString.length; i++) {
		// arrayOfString[i] = (listGoodsunit.get(i)).getUnitname();
		// }
		// AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		// localBuilder.setTitle("单位选择");
		// localBuilder.setItems(arrayOfString, new
		// DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialoginterface, int items) {
		// // 设置单位
		// ((TextView) v).setText(arrayOfString[items].toString());
		// GoodsUnit goodsUnit = listGoodsunit.get(items);
		// // 设置 选择单位
		// itemXS.setUnitid(goodsUnit.getUnitid());
		// itemXS.setUnitname(goodsUnit.getUnitname());
		// // 设置当前库存
		// String stocknum = DocUtils.Stocknum(itemXS.stocknum, goodsUnit);
		// String stocksumnum = DocUtils.Stocknum(itemXS.stocksumnum,
		// goodsUnit);
		// itemXS.goodStock = stocknum;
		// itemXS.goodSumStock = stocksumnum;
		// itemXS.unit = goodsUnit;
		// tv_Bfci.setText("库存:" + (TextUtils.isEmpty(itemXS.goodStock) ? "?" :
		// itemXS.goodStock));
		// tvSumStock
		// .setText("总库存:" + (TextUtils.isEmpty(itemXS.goodSumStock) ? "?" :
		// itemXS.goodSumStock));
		// itemXS.setPrice(DocUtils.getGoodsPrice(doc.getCustomerid(), itemXS));
		// tv_dicPrice.setText("单价:" + itemXS.getPrice() + "元");
		// }
		// });
		// localBuilder.create().show();
		// }
		// });
		// return convertView;
	}

	public class Item {
		private TextView tvName;
		private TextView tv_specification;
		private EditText etNum;
		private Button btnUnit;
		private TextView tv_Bfci;
		private TextView tv_dicPrice;
		private TextView tvSumStock;

		Item(View v) {
			tvName = (TextView) v.findViewById(R.id.tvName);
			tv_specification = (TextView) v.findViewById(R.id.tv_specification);
			etNum = (EditText) v.findViewById(R.id.etNum);
			btnUnit = (Button) v.findViewById(R.id.btnUnit);
			tv_Bfci = (TextView) v.findViewById(R.id.tv_Bfci);
			tv_dicPrice = (TextView) v.findViewById(R.id.tv_dicPrice);
			tvSumStock = (TextView) v.findViewById(R.id.tvSumStock);
		}

		public void setValue(DefDocItemXS item) {
			tvName.setText(item.getGoodsname());
			tv_specification.setText(item.getSpecification() == null ? "" : "规格:" + item.getSpecification());
			btnUnit.setText(item.getUnitname());
			tv_dicPrice.setText("单价:" + item.getPrice() + "元");
			tv_dicPrice.setOnClickListener(priceOnClick);
			tv_Bfci.setText("当前库:" + (TextUtils.isEmpty(item.goodStock) ? "?" : item.goodStock));
			tvSumStock.setText("总库存:" + (TextUtils.isEmpty(item.goodSumStock) ? "?" : item.goodSumStock));
			etNum.setText(item.getNum() == 0 ? "" : Utils.cleanZero(item.getNum()));
		}

	}

	private class NumWatcher implements TextWatcher {
		Item item;

		public NumWatcher(Item item) {
			this.item = item;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			int position = ((Integer) item.etNum.getTag()).intValue();
			listItems.get(position).setNum(Utils.normalize(Utils.getDouble(s.toString()).doubleValue(), 2));
		}

	}
	//商品价格列表
	OnClickListener priceOnClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			int i = ((Integer) v.getTag()).intValue();
			final TextView price = (TextView) v;
			final DefDocItemXS itemXS = listItems.get(i);
			final List<RespGoodsPriceEntity> listGoodPrice = DocUtils.queryGoodsPriceList(itemXS.getGoodsid());
			if (listGoodPrice.isEmpty()) {
				PDH.showFail("没有查询到价格!");
				return;
			}
			String arrayPrice[] = new String[listGoodPrice.size()];
			for (int j = 0; j < listGoodPrice.size(); j++) {
				RespGoodsPriceEntity priceEntity = listGoodPrice.get(j);
				arrayPrice[j] = priceEntity.getPricesystemname() + ":" + priceEntity.getPrice();
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("商品价格");
			builder.setItems(arrayPrice, new AlertDialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					double price2 = listGoodPrice.get(which).getPrice();
					itemXS.setPrice(price2);
					price.setText("单价:" + price2 + "元");
				}
			});
			builder.show();
		}
	};
	View.OnClickListener unitOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(final View v) {
			int position = ((Integer) v.getTag()).intValue();
			GoodsUnitDAO goodsUnitDAO = new GoodsUnitDAO();
			final DefDocItemXS itemXS = listItems.get(position);
			final List<GoodsUnit> listGoodsunit = goodsUnitDAO.queryGoodsUnits(itemXS.getGoodsid());
			final String[] arrayOfString = new String[listGoodsunit.size()];
			for (int i = 0; i < arrayOfString.length; i++) {
				arrayOfString[i] = (listGoodsunit.get(i)).getUnitname();
			}
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
			localBuilder.setTitle("单位选择");
			localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					((TextView) v).setText(arrayOfString[which].toString());
					GoodsUnit goodsUnit = listGoodsunit.get(which);
					// 设置 选择单位
					itemXS.setUnitid(goodsUnit.getUnitid());
					itemXS.setUnitname(goodsUnit.getUnitname());
					// 设置当前库存
					String stocknum = DocUtils.Stocknum(itemXS.stocknum, goodsUnit);
					String stocksumnum = DocUtils.Stocknum(itemXS.stocksumnum, goodsUnit);
					itemXS.goodStock = stocknum;
					itemXS.goodSumStock = stocksumnum;
					itemXS.unit = goodsUnit;
					itemXS.setPrice(DocUtils.getGoodsPrice(doc.getCustomerid(), itemXS));
					notifyDataSetChanged();
				}
			});
			localBuilder.create().show();
		}

	};
}
