package com.ahjswy.cn.ui;

import java.io.Serializable;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.dao.WarehouseDAO;
import com.ahjswy.cn.response.RespGoodsBatchEntity;
import com.ahjswy.cn.service.ServiceGoods;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.TextUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class GoodsBatchSearchAct extends BaseActivity implements OnItemClickListener {
	private String warehouseid;
	private String warehousename;
	public boolean all = false;
	private ListView listview;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.listview);
		String localString = getIntent().getStringExtra("goodsid");
		warehouseid = getIntent().getStringExtra("warehouseid");
		listview = (ListView) findViewById(R.id.listview);
		all = getIntent().getBooleanExtra("all", false);
		if (TextUtils.isEmptyS(warehouseid)) {
			warehousename = new WarehouseDAO().getWarehouse(warehouseid).getName();
		}
		loadData(localString);
		listview.setOnItemClickListener(this);
	}

	private void loadData(final String paramString) {
		PDH.show(this, new PDH.ProgressCallBack() {

			@Override
			public void action() {
				// if (all) {
				String localString = new ServiceGoods().gds_GetAllGoodsBatchPD(warehouseid, paramString);
				// String localString = new
				// ServiceGoods().gds_GetGoodsBatch(warehouseid, paramString);
				handler.sendMessage(handler.obtainMessage(0, localString));
				return;
				// }

			}
		});
	}

	private List<RespGoodsBatchEntity> goodsBatchs;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String localString = msg.obj.toString();
			if (RequestHelper.isSuccess(localString)) {
				goodsBatchs = JSONUtil.str2list(localString, RespGoodsBatchEntity.class);
				if ((goodsBatchs == null) || (goodsBatchs.size() == 0)) {
					PDH.showMessage("指定仓库无可用商品批次");
					return;
				}
				GoodsBatchSearchAdapter localGoodsBatchSearchAdapter = new GoodsBatchSearchAdapter(
						GoodsBatchSearchAct.this);
				localGoodsBatchSearchAdapter.setData(goodsBatchs);
				listview.setAdapter(localGoodsBatchSearchAdapter);
				return;
			}
			PDH.showError(localString);
		};
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		}

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent localIntent = new Intent();
		localIntent.putExtra("goodsbatch", (Serializable) this.goodsBatchs.get(position));
		setResult(RESULT_OK, localIntent);
		finish();
	}

	@Override
	public void setActionBarText() {
		if (TextUtils.isEmpty(warehousename)) {
			getActionBar().setTitle("商品批次");
			return;
		}
		getActionBar().setTitle("商品批次【" + this.warehousename + "】");
	}

}
