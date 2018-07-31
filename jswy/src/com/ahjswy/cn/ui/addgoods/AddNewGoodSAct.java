package com.ahjswy.cn.ui.addgoods;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.bean.GoodEntity;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsPriceDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.dao.PricesystemDAO;
import com.ahjswy.cn.model.Goods;
import com.ahjswy.cn.model.GoodsClass;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.model.Pricesystem;
import com.ahjswy.cn.model.Unit;
import com.ahjswy.cn.response.RespGoodsPriceEntity;
import com.ahjswy.cn.scaner.Scaner;
import com.ahjswy.cn.scaner.Scaner.ScanerBarcodeListener;
import com.ahjswy.cn.service.ServiceGoods;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.ui.SearchGoodsClassAct;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.utils.InfoDialog;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PinYin4j;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 增加 商品
 * 
 * @author Administrator
 *
 */
public class AddNewGoodSAct extends BaseActivity implements OnClickListener, ScanerBarcodeListener {
	private LinearLayout unitRoot;
	private Button btnAddUnit;

	private CheckBox cbBaseUnit1;
	private CheckBox cbBigUnit1;
	private CheckBox cbBaseUnit2;
	private CheckBox cbBigUnit2;
	private LinearLayout linUnit2;
	private CheckBox cbIsDiscount;
	private CheckBox cbIsusebatch;
	LinearLayout linUnit3;
	private LinearLayout linUnit1;
	private Button btnGoodsClass;
	private ListView lvPrices;
	private List<Pricesystem> listAdapterPrice;
	private ScrollView svRoot;
	private PriceAdapter priceAdapter;
	private EditText etName;
	private EditText etBarcode;
	private EditText etSpecification;
	private EditText etModel;
	private EditText etSalecue;
	private EditText etRemark;
	// private EditText etPinYin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_addnewgoodst);
		initView();
		initUnit1();
		factory = Scaner.factory(getApplicationContext());
		factory.setBarcodeListener(this);
	}

	private void initView() {
		etName = (EditText) findViewById(R.id.etName);
		// etPinYin = (EditText) findViewById(R.id.etPinYin);
		etBarcode = (EditText) findViewById(R.id.etBarcode);
		etSpecification = (EditText) findViewById(R.id.etSpecification);
		etModel = (EditText) findViewById(R.id.etModel);
		cbIsDiscount = (CheckBox) findViewById(R.id.cbIsDiscount);
		cbIsusebatch = (CheckBox) findViewById(R.id.cbIsusebatch);
		etSalecue = (EditText) findViewById(R.id.etSalecue);
		etRemark = (EditText) findViewById(R.id.etRemark);
		guaranteeperiod = (EditText) findViewById(R.id.guaranteeperiod);
		guaranteeearlier = (EditText) findViewById(R.id.guaranteeearlier);
		btnGoodsClass = (Button) findViewById(R.id.btn_goodsClass);
		btnGoodsClass.setOnClickListener(this);
		Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnAddUnit = (Button) findViewById(R.id.btnAddUnit);
		unitRoot = (LinearLayout) findViewById(R.id.unitRoot);
		svRoot = (ScrollView) findViewById(R.id.svRoot);
		lvPrices = (ListView) findViewById(R.id.lvPrices);
		btnAddUnit.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		dao = new PricesystemDAO();
		listGoodUnit = new ArrayList<GoodsUnit>();
		listPrice = new ArrayList<Pricesystem>();
		cbIsusebatch.setClickable(false);
		goodsDAO = new GoodsDAO();
	}

	private void initUnit1() {
		linUnit1 = (LinearLayout) View.inflate(this, R.layout.act_addgoods_unit, null);
		unitRoot.addView(linUnit1);
		cbBaseUnit1 = (CheckBox) linUnit1.findViewById(R.id.cbBaseUnit);
		cbBigUnit1 = (CheckBox) linUnit1.findViewById(R.id.cbBigUnit);
		ratio1 = (EditText) linUnit1.findViewById(R.id.ratio);
		spUnit = (Spinner) linUnit1.findViewById(R.id.spUnit);

		cbIsDiscount.setChecked(true);
		cbBaseUnit1.setChecked(true);
		cbBigUnit1.setChecked(true);
		cbBaseUnit1.setFocusable(false);
		cbBigUnit1.setFocusable(false);
		ratio1.setText(String.valueOf(1.0));
		ratio1.setEnabled(false);
		cbBigUnit1.setClickable(false);
		cbBaseUnit1.setClickable(false);
		priceAdapter = new PriceAdapter();
		listAdapterPrice = dao.queryAll();
		lvPrices.setAdapter(priceAdapter);
		// 请求 ScrollView 不要拦截 滑动事件
		// 单位查询展示
		// =========单位示例 展示
		// initUnit(spUnit);
		setHeight(lvPrices, priceAdapter);
		svRoot.setOnTouchListener(svTouchListener);
		listUnit = new GoodsUnitDAO().queryAll();
		if (listUnit.isEmpty()) {
			showError("没有查询到单位! 请重试!");
			return;
		}
		unit1 = new GoodsUnit();
		unit1.setUnitid(listUnit.get(0).getId());
		unit1.setUnitname(listUnit.get(0).getName());
		arrayUnits = new String[listUnit.size()];
		for (int i = 0; i < listUnit.size(); i++) {
			arrayUnits[i] = listUnit.get(i).getName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayUnits);
		spUnit.setAdapter(adapter);
		spUnit.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Unit unit = listUnit.get(position);
				unit1.setUnitid(unit.getId());
				unit1.setUnitname(unit.getName());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	// 重新绘制 item高度
	public void setHeight(ListView listView, Adapter adapter) {
		int height = 0;
		int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			View temp = adapter.getView(i, null, listView);
			temp.measure(0, 0);
			height += temp.getMeasuredHeight();
		}
		LayoutParams params = (LayoutParams) listView.getLayoutParams();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = height + 20;
		listView.setLayoutParams(params);
	}

	// ScrollView 触摸事件处理
	View.OnTouchListener svTouchListener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (lvPrices != null) {// 获取子View
									// 清除焦点
				int childCount = lvPrices.getChildCount();
				for (int i = 0; i < childCount; i++) {
					lvPrices.getChildAt(i).clearFocus();
				}
			}
			return false;
		}
	};
	// private CheckBox cbBaseUnit3;
	// private CheckBox cbBigUnit3;
	private PricesystemDAO dao;
	private List<GoodsUnit> listGoodUnit;
	private GoodsUnit unit1;
	private GoodsUnit unit2;
	private EditText ratio1;
	private Spinner spUnit;
	private String[] arrayUnits;
	private List<Unit> listUnit;
	private GoodsUnit unit3;
	private Button btnDelete2;
	private Button btnDelete3;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_goodsClass:
			startActivityForResult(new Intent(this, SearchGoodsClassAct.class), 1);
			break;
		case R.id.btnAddUnit:
			if (linUnit2 == null) {
				unit2 = new GoodsUnit();
				unit2.setUnitid(listUnit.get(0).getId());
				unit2.setUnitname(listUnit.get(0).getName());
				cbBigUnit1.setChecked(false);
				cbBigUnit1.setVisibility(View.INVISIBLE);
				linUnit2 = (LinearLayout) View.inflate(this, R.layout.act_addgoods_unit, null);
				unitRoot.addView(linUnit2);
				btnDelete2 = (Button) linUnit2.findViewById(R.id.btnDelete);
				cbBaseUnit2 = (CheckBox) linUnit2.findViewById(R.id.cbBaseUnit);
				cbBigUnit2 = (CheckBox) linUnit2.findViewById(R.id.cbBigUnit);
				ratio2 = (EditText) linUnit2.findViewById(R.id.ratio);
				btnDelete2.setVisibility(View.VISIBLE);
				cbBigUnit2.setChecked(true);
				cbBigUnit2.setClickable(false);
				btnDelete2.setOnClickListener(deleteItem2);
				Spinner spUnit2 = (Spinner) linUnit2.findViewById(R.id.spUnit);
				cbBaseUnit2.setVisibility(View.INVISIBLE);
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddNewGoodSAct.this,
						android.R.layout.simple_list_item_1, arrayUnits);
				spUnit2.setAdapter(adapter2);
				spUnit2.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						Unit unit = listUnit.get(position);
						unit2.setUnitid(unit.getId());
						unit2.setUnitname(unit.getName());
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
				return;
			}
			if (linUnit3 == null) {
				unit3 = new GoodsUnit();
				unit3.setUnitid(listUnit.get(0).getId());
				unit3.setUnitname(listUnit.get(0).getName());
				linUnit3 = (LinearLayout) View.inflate(this, R.layout.act_addgoods_unit, null);
				unitRoot.addView(linUnit3);
				linUnit3.findViewById(R.id.rgRoot).setVisibility(View.GONE);
				ratio3 = (EditText) linUnit3.findViewById(R.id.ratio);
				btnDelete3 = (Button) linUnit3.findViewById(R.id.btnDelete);
				btnDelete3.setVisibility(View.VISIBLE);
				btnDelete3.setOnClickListener(deleteItem3);
				btnDelete3.setTag(linUnit3);
				Spinner spUnit3 = (Spinner) linUnit3.findViewById(R.id.spUnit);
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddNewGoodSAct.this,
						android.R.layout.simple_list_item_1, arrayUnits);
				spUnit3.setAdapter(adapter2);
				spUnit3.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						Unit unit = listUnit.get(position);
						unit3.setUnitid(unit.getId());
						unit3.setUnitname(unit.getName());
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
				btnAddUnit.setVisibility(View.GONE);
				return;
			}
			break;
		case R.id.btnSubmit:
			submit();
			break;

		default:
			break;
		}
	}

	View.OnClickListener deleteItem2 = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			cbBigUnit1.setChecked(true);
			cbBigUnit1.setVisibility(View.VISIBLE);
			unitRoot.removeView(linUnit2);
			linUnit2 = null;
			unit2 = null;
			btnAddUnit.setVisibility(View.VISIBLE);
		}
	};
	View.OnClickListener deleteItem3 = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			unitRoot.removeView(linUnit3);
			linUnit3 = null;
			unit3 = null;
			btnAddUnit.setVisibility(View.VISIBLE);
		}
	};
	private EditText ratio2;
	private EditText ratio3;
	private Scaner factory;
	private ArrayList<Pricesystem> listPrice;
	private EditText guaranteeearlier;
	private EditText guaranteeperiod;
	private GoodsDAO goodsDAO;

	private void submit() {
		listGoodUnit.clear();
		listPrice.clear();
		String validateDoc = validateDoc();
		if (validateDoc != null) {
			InfoDialog.showError(this, validateDoc);
			return;
		}

		for (Pricesystem price : listAdapterPrice) {
			price.setUnitid(unit1.getUnitid());
			price.setPricesystemid(price.getPsid());
			listPrice.add(price);
		}

		unit1.setIsbasic(cbBaseUnit1.isChecked());
		unit1.setIsshow(cbBigUnit1.isChecked());
		unit1.setRatio(Utils.getDouble(ratio1.getText().toString()));
		listGoodUnit.add(unit1);
		if (linUnit2 != null) {
			for (Pricesystem pricesystem : dao.queryAll()) {
				pricesystem.setUnitid(unit2.getUnitid());
				// 本身不需要 系统 那边需要添加
				pricesystem.setPricesystemid(pricesystem.getPsid());
				listPrice.add(pricesystem);
			}
			unit2.setIsbasic(cbBaseUnit2.isChecked());
			unit2.setIsshow(cbBigUnit2.isChecked());
			unit2.setRatio(Utils.getDouble(ratio2.getText().toString()));
			listGoodUnit.add(unit2);
		}
		if (linUnit3 != null) {
			for (Pricesystem pricesystem : dao.queryAll()) {
				pricesystem.setUnitid(unit3.getUnitid());
				// 本身不需要 系统 那边需要添加
				pricesystem.setPricesystemid(pricesystem.getPsid());
				listPrice.add(pricesystem);
			}

			unit3.setIsbasic(false);
			unit3.setIsshow(false);
			unit3.setRatio(Utils.getDouble(ratio3.getText().toString()));
			listGoodUnit.add(unit3);
		}
		// 1、商品编号不能重复；2、商品名称 + 规格不能重复
		GoodsThin isCheckNameSpecification = goodsDAO.queryGoodsNameANDSpecification(etName.getText().toString(),
				etSpecification.getText().toString());
		if (isCheckNameSpecification != null) {
			showError("当前商品名称、规格已存在，请修改商品名称或规格后再试！");
			return;
		}
		// 条码 为 空 不判断 直接提交 ，不为 空 进行本地查询 检查本地 商品条码是否存在
		if (!etBarcode.getText().toString().isEmpty()) {
			Goods goods = goodsDAO.getGoods(etBarcode.getText().toString());
			if (goods != null) {
				MAlertDialog maler = new MAlertDialog(this);
				maler.btn_dialg_back.setVisibility(View.GONE);
				maler.setMessage("该商品条码已经存在是否继续?");
				maler.setNeutralButton(new MAlertDialog.OnClickListener() {

					@Override
					public void onClick(MAlertDialog dialog) {
						dialog.dismiss();
						submitGood();
					}
				});
				maler.setNegativeButton(new MAlertDialog.OnClickListener() {

					@Override
					public void onClick(MAlertDialog dialog) {
						dialog.dismiss();
					}
				});
				maler.show();
				return;
			}

		}
		submitGood();
		

	}

	protected void submitGood() {
		// 提交
		Goods goods = new Goods();
		goods.name = etName.getText().toString();
		goods.pinyin = new PinYin4j().getPinyin(goods.name).iterator().next();
		goods.barcode = etBarcode.getText().toString();
		goods.model = etModel.getText().toString();
		goods.remark = etRemark.getText().toString();
		goods.salecue = etSalecue.getText().toString();
		goods.specification = etSpecification.getText().toString();
		goods.isdiscount = cbIsDiscount.isChecked();
		goods.isusebatch = cbIsusebatch.isChecked();
		goods.goodsclassid = btnGoodsClass.getTag().toString();
		// 保质期
		if (TextUtils.isEmptyS(guaranteeperiod.getText().toString())) {
			goods.guaranteeperiod = Integer.parseInt(guaranteeperiod.getText().toString());
		}
		// 临期报警
		if (TextUtils.isEmptyS(guaranteeearlier.getText().toString())) {
			goods.guaranteeearlier = Integer.parseInt(guaranteeearlier.getText().toString());
		}
		String addGood = new ServiceGoods().gds_AddGood(goods, listPrice, listGoodUnit);
		if (RequestHelper.isSuccess(addGood)) {
			GoodEntity respGood = JSONUtil.fromJson(addGood, GoodEntity.class);
			if (respGood != null) {
				// private ArrayList<Pricesystem> listPrice;

				// sql insert addgood

				boolean insertAddGood = goodsDAO.insertAddGood(JSONUtil.parseObject(respGood.getGoods(), Goods.class));
				if (!insertAddGood) {
					showError("商品本地写入失败!");
				}
				List<GoodsUnit> parseArray = JSONUtil.parseArray(respGood.getGoodsunit(), GoodsUnit.class);
				GoodsUnitDAO goodsUnitDAO = new GoodsUnitDAO();
				for (GoodsUnit goodsUnit : parseArray) {
					if (!goodsUnitDAO.insetAddGoodUnit(goodsUnit)) {
						showError("商品单位本地写入失败!");
					}
				}
				List<RespGoodsPriceEntity> array = JSONUtil.parseArray(respGood.getGoodsPrice(),
						RespGoodsPriceEntity.class);
				GoodsPriceDAO priceDAO = new GoodsPriceDAO();
				for (RespGoodsPriceEntity entity : array) {
					if (!priceDAO.insert(entity)) {
						showError("商品价格体系本地写入失败!");
					}
				}
			}
			showSuccess("添加商品成功!");
			startActivity(new Intent(this, SwyMain.class));
			finish();
		} else {
			showError(TextUtils.isEmpty(addGood) ? "添加商品失败!" : addGood);
		}

	}

	private String validateDoc() {
		if (TextUtils.isEmpty(etName.getText().toString())) {
			return "请输入商品名称";
		}
		if (btnGoodsClass.getTag() == null) {
			return "所属类别没有选择!";
		}
		// if (etBarcode.getText().toString().isEmpty()) {
		// return "请输入条码!";
		// }
		if (unit2 != null) {
			if (unit1.getUnitid().equals(unit2.getUnitid())) {
				return "该计量单位已经选择";
			}
			if (TextUtils.isEmpty(ratio2.getText().toString())
					|| Utils.getDouble(ratio2.getText().toString()).doubleValue() <= 0) {
				return "换算比例必须大于0";
			}

		}
		if (unit3 != null) {
			if (unit1.getUnitid().equals(unit3.getUnitid())) {
				return "该计量单位已经选择";
			}
			if (TextUtils.isEmpty(ratio3.getText().toString())
					|| Utils.getDouble(ratio3.getText().toString()).doubleValue() <= 0) {
				return "换算比例必须大于0";
			}

		}
		if (unit2 != null && unit3 != null) {
			if (unit2.getUnitid().equals(unit3.getUnitid())) {
				return "该计量单位已经选择";
			}
		}
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 1:
				// 商品类别检索 返回
				GoodsClass goodsClass = (GoodsClass) data.getSerializableExtra("goodclass");
				btnGoodsClass.setText(goodsClass.getName());
				btnGoodsClass.setTag(goodsClass.getId());
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void setBarcode(String barcode) {

		if (TextUtils.isEmpty(etBarcode.getText().toString())) {
			etBarcode.setText(barcode);
			return;
		}
		if (TextUtils.isEmpty(etModel.getText().toString())) {
			etModel.setText(barcode);
			return;
		}
	}

	public class PriceAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listAdapterPrice.size();
		}

		@Override
		public Pricesystem getItem(int position) {
			return listAdapterPrice.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		int selectPosition;

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = View.inflate(AddNewGoodSAct.this, R.layout.item_addgoods, null);
			TextView tvName = (TextView) view.findViewById(R.id.tvName);
			EditText edNumber = (EditText) view.findViewById(R.id.edNumber);
			Pricesystem pricesystem = listAdapterPrice.get(position);
			String psname = pricesystem.getPsname();
			tvName.setText(psname);
			edNumber.setText(pricesystem == null || pricesystem.getPrice() == 0 ? "" : pricesystem.getPrice() + "");
			edNumber.setTag(Integer.valueOf(position));
			edNumber.addTextChangedListener(new MyWatcher(edNumber));
			return view;
		}

		public class MyWatcher implements TextWatcher {
			View v;

			public MyWatcher(View v) {
				this.v = v;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int i = ((Integer) v.getTag()).intValue();
				listAdapterPrice.get(i).setPrice(Utils.getDouble(s.toString()));
			}

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		factory.removeListener();
		factory = null;
	}

	@Override
	public void setActionBarText() {
		setTitle("新增商品");
	}

}
