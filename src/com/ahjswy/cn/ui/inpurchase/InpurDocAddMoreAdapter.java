package com.ahjswy.cn.ui.inpurchase;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.bean.Def_Doc;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.DefDocItemCG;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.response.RespGoodsPriceEntity;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.PDH;
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

/**
 * 采购入库adapter
 * 
 * @author Administrator
 *
 */
public class InpurDocAddMoreAdapter extends BaseAdapter {
	private Context context;
	private List<DefDocItemCG> listItems;
	// InpurDocAddMoreAdapter
	private int selectPosition = -1;// 记住选中的edtitext
	private List<String> listBigUnit;
	private GoodsUnitDAO dao;
	private Def_Doc doc;

	public InpurDocAddMoreAdapter(Context context) {
		this.context = context;
		if (listItems == null) {
			this.listItems = new ArrayList<DefDocItemCG>();
		}
		if (listBigUnit == null) {
			listBigUnit = new ArrayList<String>();
		}
		if (dao == null) {
			dao = new GoodsUnitDAO();
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

	public List<DefDocItemCG> getData() {
		return this.listItems;
	}

	public void setDoc(Def_Doc doc) {
		this.doc = doc;
	}

	public void setData(List<DefDocItemCG> paramList) {
		this.listItems.clear();
		this.listItems.addAll(paramList);
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.act_out_doc_add_moreadapter_item, null);
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
		final TextView tv_dicPrice = (TextView) convertView.findViewById(R.id.tv_dicPrice);
		TextView tv_specification = (TextView) convertView.findViewById(R.id.tv_specification);
		DefDocItemCG docItemCG = listItems.get(position);
		// 显示上次 采购
		btnUnit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
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
					public void onClick(DialogInterface dialoginterface, int which) {
						// 设置单位
						((TextView) v).setText(arrayOfString[which].toString());
						// 保存 选择
						DefDocItemCG docItem = listItems.get(position);
						GoodsUnit goodsUnit = list_goodsunit.get(which);
						docItem.setUnitid(goodsUnit.getUnitid());
						docItem.setUnitname(goodsUnit.getUnitname());
						String stockUnit = DocUtils.setItemStockUnit(docItem.stock, goodsUnit);
						docItem.bigstocknumber = stockUnit;
						tv_Bfci.setText("库存:" + stockUnit);
						// 根据商品比例计算商品单价
						double goodsPrice = DocUtils.getGoodsCGPrice(docItem.getGoodsid(), docItem.getUnitid(),
								docItem.getBaseprice());
						// 设置商品单价
						tv_dicPrice.setText("单价: "+ goodsPrice);
						// 设置item 单价
						docItem.setPrice(goodsPrice);
					}
				});
				localBuilder.create().show();
			}

		});
		tvName.setText(docItemCG.getGoodsname());
		// tvBarcode.setText(docItemCG.getBarcode());
		btnUnit.setText(docItemCG.getUnitname());
		tv_specification.setText(docItemCG.getSpecification() == null ? "" : "规格:" + docItemCG.getSpecification());
		tv_dicPrice.setText("单价: " + docItemCG.getPrice() + " 元");
		if (docItemCG.getNum() != 0.0d) {
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

		if (docItemCG.bigstocknumber == null) {
			tv_Bfci.setVisibility(8);
		}

		tv_Bfci.setText("库存:" + docItemCG.bigstocknumber);
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

	public class Item {
		TextView tvName;
		TextView tvBarcode;
		Button btnUnit;
		TextView tv_Bfci;

		public Item(View v) {
			tvName = (TextView) v.findViewById(R.id.tvName);
			tvName = (TextView) v.findViewById(R.id.tvBarcode);
			btnUnit = (Button) v.findViewById(R.id.btnUnit);
			tv_Bfci = (TextView) v.findViewById(R.id.tv_Bfci);
		}

		void setValue(DefDocItemCG cg) {

		}
	}
}
