package com.ahjswy.cn.ui.inpurchase;

import java.io.Serializable;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.request.ReqCustomerdebt;
import com.ahjswy.cn.service.ServiceCustomer;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.inpurchase.InpurPayAdapter.Amount;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.EditTextWithDel;
import com.ahjswy.cn.views.EditTextWithDel.Clean;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class InpurDocPayAct extends BaseActivity implements Clean, OnClickListener, OnFocusChangeListener, Amount {
	// 折后合计
	private TextView tvDiscountSubtotal;
	// 优惠
	private EditTextWithDel etPreference;
	// InpurDocPayAct
	// 优惠后应付
	private TextView tvReceiveable;
	// 已付
	private TextView tvReceived;
	// 待付
	private TextView tvLeft;
	// 确定
	private Button btnSave;

	private ListView listView;
	private double discountsubtotal;
	private List<DefDocPayType> listPayType;
	private double preference;
	private InpurPayAdapter adapter;
	private TextView tvReceiveableLabel;
	private TextView tvReceivedLabel;
	private TextView tvLeftLabel;
	private TextView tvInBuy;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_input_doc_pay);
		initView();
		initDate();
		initListener();
	}

	private void initView() {
		tvDiscountSubtotal = (TextView) findViewById(R.id.tvDiscountSubtotal);
		etPreference = (EditTextWithDel) findViewById(R.id.etPreference);
		tvReceiveable = (TextView) findViewById(R.id.tvReceiveable);
		tvReceived = (TextView) findViewById(R.id.tvReceived);
		tvLeft = (TextView) findViewById(R.id.tvLeft);
		btnSave = (Button) findViewById(R.id.btnSave);
		listView = (ListView) findViewById(R.id.listView);
		tvReceiveableLabel = (TextView) findViewById(R.id.tvReceiveableLabel);
		tvReceivedLabel = (TextView) findViewById(R.id.tvReceivedLabel);
		tvLeftLabel = (TextView) findViewById(R.id.tvLeftLabel);
		tvInBuy = (TextView) findViewById(R.id.tvInBuy);
	}

	private void initDate() {
		// 折扣合计
		discountsubtotal = getIntent().getDoubleExtra("discountsubtotal", 0.0D);
		// 优惠
		preference = getIntent().getDoubleExtra("preference", 0.0D);
		listPayType = JSONUtil.str2list(getIntent().getStringExtra("listpaytype"), DefDocPayType.class);
		// listPayType = (List<DefDocPayType>)
		// getIntent().getExtras().getSerializable("listpaytype");
		// 支付类型集合
		double d2 = 0.0D;
		for (int i = 0; i < listPayType.size(); i++) {
			d2 += listPayType.get(i).getAmount();
		}
		// 折扣合计
		tvDiscountSubtotal.setText(discountsubtotal + "");
		// 优惠
		etPreference.setText(preference + "");
		etPreference.setTag(etPreference.getText());
		etPreference.setOnFocusChangeListener(this);
		// 总金额 -- 优惠
		double d1 = Utils.normalizeReceivable(this.discountsubtotal - preference);
		// 优惠后应收
		this.tvReceiveable.setText(d1 + "");
		double d3 = Utils.normalize(d2, 2);
		double d4 = Utils.normalize(d1 - d3, 2);
		// 已收
		this.tvReceived.setText(d3 + "");
		// 待收
		this.tvLeft.setText(d4 + "");
		adapter = new InpurPayAdapter(this);
		adapter.setData(listPayType);
		listView.setAdapter(adapter);
		adapter.setAmount(this);
		// =========================
		tvReceiveableLabel.setText("优惠后应付：");
		tvReceivedLabel.setText("已付：");
		tvLeftLabel.setText("待付：");
		setHeight(listView, adapter);
		getNet();
	}

	// 查询用的可冲抵预售款
	private void getNet() {
		final String customerid = getIntent().getStringExtra("customerid");
		if (TextUtils.isEmpty(customerid)) {
			return;
		}
		PDH.show(this, new PDH.ProgressCallBack() {

			public void action() {
				String localString = new ServiceCustomer().cu_queryCustomer(new ReqCustomerdebt().setCustomerid(customerid).setIscustomer(false));
				handler.sendMessage(handler.obtainMessage(2, localString));
			}

		});
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:
				if (RequestHelper.isSuccess(msg.obj.toString())) {
					// 显示查询用的可冲抵预售款
					ReqCustomerdebt parseObject = JSONUtil.parseObject(msg.obj.toString(), ReqCustomerdebt.class);
					tvInBuy.setText("" + parseObject.getAmount());
				}
				break;

			}

		};
	};

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

	private void initListener() {
		// etPreference.setCleanDrawable(false);
		etPreference.setClean(this);
		btnSave.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(paramMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			break;
		case 0:
			RESULT_OK();
			break;
		}
		return true;
	}

	/**
	 * 
	 */
	private void RESULT_OK() {
		Intent intent = new Intent();
		// 优惠
		intent.putExtra("preference",
				Utils.normalize(Utils.getDouble(etPreference.getText().toString()).doubleValue(), 2));
		// 收款方式集合
		intent.putExtra("listpaytype", (Serializable) listPayType);
		// 优惠应收
		intent.putExtra("receiveable", Utils.getDouble(tvReceiveable.getText().toString()));
		intent.putExtra("received", Utils.getDouble(tvReceived.getText().toString()));
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void clean() {
		this.tvReceiveable.setText(discountsubtotal + "");
		setPrice();
	}

	private void setPrice() {
		double d1 = 0.0D;
		double d2 = 0.0D;
		if (tvReceiveable.getText().toString().length() > 0) {
			d1 = Utils.normalizeReceivable(Utils.getDouble(this.tvReceiveable.getText().toString()).doubleValue());
		}
		List<DefDocPayType> data = adapter.getData();
		// 获取修改后的金额，总额
		for (int i = 0; i < data.size(); i++) {
			d2 += data.get(i).getAmount();
		}

		double d3 = Utils.normalize(d2, 2);
		double d4 = Utils.normalize(d1 - d3, 2);
		// 已收
		this.tvReceived.setText(d3 + "");
		// 待收
		this.tvLeft.setText(d4 + "");
	}

	@Override
	public void onClick(View v) {
		RESULT_OK();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		double d1 = 0.0D;
		double d2 = 0.0D;
		double d3 = 0.0D;
		if (!hasFocus) {
			// 优惠
			if (etPreference.getText().toString().length() > 0) {
				d1 = Utils.getDouble(etPreference.getText().toString()).doubleValue();
			}
			d2 = Utils.normalizeReceivable(this.discountsubtotal - d1);
			List<DefDocPayType> data = adapter.getData();
			for (int i = 0; i < data.size(); i++) {
				d3 += data.get(i).getAmount();
			}
			double d4 = Utils.normalize(d3, 2);
			double d5 = Utils.normalize(d2 - d4, 2);
			// 优惠后应收
			this.tvReceiveable.setText(d2 + "");
			// 已收
			this.tvReceived.setText(d4 + "");
			// 待收
			this.tvLeft.setText(d5 + "");
			this.etPreference.setTag(this.etPreference.getText());
			return;
		}
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("付款");
	}

	@Override
	public void AmountSetPrice() {
		setPrice();
	}
}
