package com.ahjswy.cn.ui.field;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.dao.GoodsDAO;
import com.ahjswy.cn.dao.GoodsImageDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.model.Goods;
import com.ahjswy.cn.model.GoodsImage;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.response.RespGoodsPriceEntity;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.Swy_splash;
import com.ahjswy.cn.utils.BitmapUtils;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.views.Dialog_message;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodDetailAct extends BaseActivity {
	private Goods goods;
	private ImageView imageView;
	private TextView tvBarcode;
	private TextView tvGoodsID;
	private TextView tvModel;
	// private TextView tvSaleCue;
	private TextView tvSpecificaion;
	private TextView tvStockNum;
	private ViewPager viewPager;
	private List<GoodsImage> goodsImages;
	private BitmapUtils bitmapUtils;
	private MyPagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_stockiteminfo_details);
		this.goods = new GoodsDAO().getGoods(getIntent().getStringExtra("goodsid"));
		this.bitmapUtils = new BitmapUtils();
		initView();
		initData();
	}

	private void initView() {
		this.imageView = (ImageView) findViewById(R.id.imageView);
		this.tvGoodsID = (TextView) findViewById(R.id.tvGoodsID);
		this.tvBarcode = (TextView) findViewById(R.id.tvBarcode);
		this.tvSpecificaion = (TextView) findViewById(R.id.tvSpecification);
		this.tvModel = (TextView) findViewById(R.id.tvModel);
		this.tvStockNum = (TextView) findViewById(R.id.tvStockNum);
		dialogPrices = new Dialog_message(this);
	}

	private void initData() {
		if (!"1".equals(new AccountPreference().getValue("ViewKCStockBrowse", "0"))) {
			this.tvStockNum.setVisibility(View.GONE);
		}
		this.tvGoodsID.setText("商品编号：" + this.goods.getId());
		this.tvBarcode.setText("商品条码：" + TextUtils.out(this.goods.getBarcode()));
		this.tvSpecificaion.setText("规         格：" + TextUtils.out(this.goods.getSpecification()));
		this.tvModel.setText("型         号：" + TextUtils.out(this.goods.getModel()));
		tvStockNum.setText("库存数量：");
		this.viewPager = ((ViewPager) findViewById(R.id.viewPager));
		LayoutParams params = viewPager.getLayoutParams();
		params.height = (Swy_splash.height / 2 - 20);
		this.imageView.setLayoutParams(params);
		goodsImages = new GoodsImageDAO().get(goods.getId());
		if (this.goodsImages == null || this.goodsImages.size() == 0) {
			this.imageView.setVisibility(0);
			ViewGroup.LayoutParams ivParams = this.imageView.getLayoutParams();
			ivParams.height = Swy_splash.height / 2 - 40;
			this.imageView.setLayoutParams(ivParams);
		} else {
			this.imageView.setVisibility(View.GONE);
			this.pagerAdapter = new MyPagerAdapter(this, this.goodsImages);
			this.viewPager.setAdapter(this.pagerAdapter);
		}
		boolean isShowPrice = getIntent().getBooleanExtra("showPrice", false);
		if (isShowPrice) {
			List<RespGoodsPriceEntity> priceList = DocUtils.queryGoodsPriceList(goods.getId());
			if (!priceList.isEmpty()) {
				showPriceDialog(priceList);
			} else {
				PDH.showMessage("无价格信息");
			}
		}
		queryGoodsStock(DocUtils.queryStockwarnAll(goods.getId()));
	}

	private void queryGoodsStock(List<RespGoodsWarehouse> stockwarnAll) {
		List<RespGoodsWarehouse> goodsWarehouse = new ArrayList<RespGoodsWarehouse>();
		if (stockwarnAll == null || stockwarnAll.isEmpty()) {
			return;
		}
		List<Warehouse> allWarehouses = DocUtils.getAllWarehouses();
		for (int i = 0; i < allWarehouses.size(); i++) {
			for (int j = 0; j < stockwarnAll.size(); j++) {
				if (allWarehouses.get(i).getId().equals(stockwarnAll.get(j).getWarehouseid())) {
					Warehouse warehouse = DocUtils.getWarehouse(stockwarnAll.get(j).warehouseid);
					if (warehouse != null) {
						stockwarnAll.get(j).setWarehousename(warehouse.getName());
					}
					goodsWarehouse.add(stockwarnAll.get(j));
					break;
				}
			}
		}
		GoodsUnit bigUnit = DocUtils.queryBigUnit(goodsWarehouse.get(0).getGoodsid());
		StringBuilder sb = new StringBuilder();
		int stocknum = 0;
		for (RespGoodsWarehouse warehouse : goodsWarehouse) {
			stocknum += warehouse.getStocknum();
			sb.append(warehouse.getWarehousename() + respSpaceStr(warehouse.getWarehousename()) + ":  "
					+ DocUtils.Stocknum((int) warehouse.getStocknum(), bigUnit)).append("\n\n");
		}
		sb.append("库存数量 :  " + DocUtils.Stocknum(stocknum, bigUnit));
		tvStockNum.setText(sb.toString());
	}

	private void showPriceDialog(List<RespGoodsPriceEntity> listGoodPrice) {
		if (dialogPrices.isShowing()) {
			dialogPrices.dismiss();
		}
		String dialogMessage = "\n";
		for (int i = 0; i < listGoodPrice.size(); ++i) {
			RespGoodsPriceEntity rp = listGoodPrice.get(i);
			dialogMessage = dialogMessage + rp.getPricesystemname() + "：" + rp.getPrice() + "元/" + rp.getUnitname()
					+ "\n";
		}

		dialogPrices.setRootPadding(10, 50, 10, 0);
		dialogPrices.setVisibilityBottom(View.GONE);
		dialogPrices.setGravity(Gravity.TOP);
		dialogPrices.show();
		dialogPrices.setTitle(goods.getName());
		dialogPrices.setMessage(dialogMessage);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		if ("1".equals(new AccountPreference().getValue(AccountPreference.ViewChangeprice, "0"))) {
			menu.add(0, 0, 0, "价格").setShowAsAction(2);
		}
		// if ("1".equals(new
		// AccountPreference().getValue(AccountPreference.ViewKCStockBrowse,
		// "0"))) {
		// menu.add(0, 1, 0, "分库").setShowAsAction(2);
		// }
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		if (menu.getItemId() == 0) {
			List<RespGoodsPriceEntity> priceList = DocUtils.queryGoodsPriceList(goods.getId());
			if (!priceList.isEmpty()) {
				showPriceDialog(priceList);
			} else {
				PDH.showMessage("无价格信息");
			}
		}

		return super.onOptionsItemSelected(menu);
	}

	// 计算需要添加的空格
	private String respSpaceStr(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		String spacestr = "";
		if (str.length() < 5) {
			for (int i = 0; i < 5 - str.length(); i++) {
				spacestr += "   ";
			}
		}
		return spacestr;
	}

	private Dialog_message dialogPrices;

	class MyPagerAdapter extends PagerAdapter {
		public Context context;
		public List<GoodsImage> jobImageInfos;

		public MyPagerAdapter(Context context, List<GoodsImage> jobImageInfos) {
			super();
			this.context = context;
			this.jobImageInfos = jobImageInfos;
		}

		private void setViewData(View view, List<GoodsImage> paramList, int p_index) {
			if (p_index < 0) {
				finish();
			}
			ViewHolder viewHolder = (ViewHolder) view.getTag();
			String v1 = String.valueOf(bitmapUtils.getPicDir()) + "/" + paramList.get(p_index).getImagePath();
			viewHolder.bmp = BitmapFactory.decodeFile(v1);
			if (viewHolder.bmp == null) {
				viewHolder.bmp = BitmapFactory.decodeResource(getResources(), R.drawable.swylogoimage);
			}
			viewHolder.imageView.setImageBitmap(viewHolder.bmp);
			viewHolder.tvPageNo.setText(String.valueOf(p_index + 1) + "/" + this.getCount());
		}

		private View constructView() {
			View localView = getLayoutInflater().inflate(R.layout.item_field_picture, null);
			ViewHolder localViewHolder = new ViewHolder();
			localViewHolder.tvPageNo = ((TextView) localView.findViewById(R.id.tvPageNo));
			localViewHolder.imageView = ((ImageView) localView.findViewById(R.id.album_imgview));
			localView.setTag(localViewHolder);
			return localView;
		}

		@Override
		public int getCount() {
			return this.jobImageInfos.size();
		}

		public int getItemPosition(Object object) {
			return -2;
		}

		@Override
		public boolean isViewFromObject(View view, Object arg1) {
			return view == arg1;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			View view = this.constructView();
			setViewData(view, this.jobImageInfos, position);
			((ViewPager) container).addView(view, 0);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	class ViewHolder {
		public Bitmap bmp;
		public ImageView imageView;
		public TextView tvPageNo;

		public void clear() {
			if (this.bmp != null && !this.bmp.isRecycled()) {
				this.bmp.recycle();
				this.bmp = null;
				System.gc();
			}
		}
	}

	public void setActionBarText() {
		this.getActionBar().setTitle(this.goods.getName());
	}

}
